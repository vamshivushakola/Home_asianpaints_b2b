/**
 *
 */
package com.braintree.controllers.pages;

import static com.braintree.controllers.Braintreeb2baddonControllerConstants.PAY_PAL_ADDRESS_ERROR;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.PAY_PAL_HAED_ERROR;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.Views.Pages.MultiStepCheckout.CheckoutOrderPageErrorPage;
import static de.hybris.platform.util.localization.Localization.getLocalizedString;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2bacceleratoraddon.controllers.pages.checkout.steps.SummaryCheckoutStepController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.payment.AdapterException;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintree.constants.BraintreeConstants;
import com.braintree.controllers.handler.PayPalResponseExpressCheckoutHandler;
import com.braintree.controllers.handler.PayPalUserLoginHandler;
import com.braintree.facade.BrainTreeB2BCheckoutFacade;
import com.braintree.facade.impl.BrainTreeCheckoutFacade;
import com.braintree.facade.impl.BrainTreePaymentFacadeImpl;
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.hybris.data.PayPalAddressData;
import com.braintree.hybris.data.PayPalCheckoutData;
import com.braintree.hybris.data.PayPalExpressResponse;
import com.braintree.hybris.data.PayPalMiniCartResponse;


@Controller
@RequireHardLogIn
@RequestMapping(value = "/braintree/paypal/checkout")
public class PayPalPaymentController extends SummaryCheckoutStepController
{

	private static final Logger LOG = Logger.getLogger(PayPalPaymentController.class);

	@Resource(name = "payPalUserLoginHandler")
	PayPalUserLoginHandler payPalUserLoginHandler;

	@Resource(name = "payPalResponseExpressCheckoutHandler")
	PayPalResponseExpressCheckoutHandler payPalResponseExpressCheckoutHandler;

	@Resource(name = "brainTreePaymentFacadeImpl")
	private BrainTreePaymentFacadeImpl brainTreePaymentFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "brainTreeCheckoutFacade")
	private BrainTreeCheckoutFacade brainTreeCheckoutFacade;

	@Resource(name = "brainTreeB2BCheckoutFacade")
	private BrainTreeB2BCheckoutFacade brainTreeB2BCheckoutFacade;

	@RequestMapping(value = "/express", method = RequestMethod.POST)
	public String doHandleHopResponse(final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest request, final HttpServletResponse response)
					throws CMSItemNotFoundException, CommerceCartModificationException
	{

		PayPalExpressResponse payPalExpressResponse = null;
		try
		{
			payPalExpressResponse = payPalResponseExpressCheckoutHandler.handlePayPalResponse(request);
		}
		catch (final IllegalArgumentException exeption)
		{
			handleErrors(exeption.getMessage(), model);
			return CheckoutOrderPageErrorPage;
		}

		final BrainTreeSubscriptionInfoData subscriptionInfo = buildSubscriptionInfo(payPalExpressResponse.getNonce(),
				BraintreeConstants.PAY_PAL_EXPRESS_CHECKOUT, null, null);

		final PayPalAddressData payPalShippingAddress = payPalExpressResponse.getDetails().getShippingAddress();
		AddressData hybrisShippingAddress = null;
		if (payPalShippingAddress != null)
		{
			hybrisShippingAddress = payPalResponseExpressCheckoutHandler.getPayPalBillingAddress(payPalShippingAddress);
			hybrisShippingAddress.setEmail(payPalExpressResponse.getDetails().getEmail());
			subscriptionInfo.setAddressData(hybrisShippingAddress);
		}
		else
		{
			LOG.error("Shipping address from pay pal is empty!");
			final String errorMessage = getLocalizedString(PAY_PAL_ADDRESS_ERROR);
			handleErrors(errorMessage, model);
			return CheckoutOrderPageErrorPage;
		}

		final PayPalAddressData payPalBillingAddress = payPalExpressResponse.getDetails().getBillingAddress();
		if (payPalBillingAddress != null)
		{
			final AddressData hybrisBillingAddress = payPalResponseExpressCheckoutHandler
					.getPayPalBillingAddress(payPalBillingAddress);
			hybrisBillingAddress.setEmail(payPalExpressResponse.getDetails().getEmail());
			userFacade.addAddress(hybrisBillingAddress);
			brainTreeCheckoutFacade.setDeliveryAddress(hybrisBillingAddress);
		}
		else
		{
			LOG.warn("No billing address provide by Pay Pal. Use billing address as shipping...");
			userFacade.addAddress(hybrisShippingAddress);
			brainTreeCheckoutFacade.setDeliveryAddress(hybrisShippingAddress);
		}

		brainTreeCheckoutFacade.setCheapestDeliveryModeForCheckout();

		try
		{
			brainTreePaymentFacade.completeCreateSubscription(subscriptionInfo);
			brainTreeB2BCheckoutFacade.setCardPaymentType();
		}
		catch (final Exception exception)
		{
			final String errorMessage = getLocalizedString("braintree.billing.general.error");
			handleErrors(errorMessage, model);
			return CheckoutOrderPageErrorPage;
		}

		return super.enterStep(model, redirectAttributes);
	}

	@RequestMapping(value = "/mini/express", method = RequestMethod.GET)
	@RequireHardLogIn
	@ResponseBody
	public String doInitializeMiniCartPaypalShortcut()
			throws CMSItemNotFoundException, JsonGenerationException, JsonMappingException, IOException
	{
		final String jsonInString = buildPayPalMiniCartResponse();
		return jsonInString;
	}

	private String buildPayPalMiniCartResponse() throws JsonGenerationException, JsonMappingException, IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		final PayPalMiniCartResponse payPalMiniCartResponse = new PayPalMiniCartResponse();

		String clientToken = StringUtils.EMPTY;

		try
		{
			clientToken = brainTreeCheckoutFacade.generateClientToken();
		}
		catch (final AdapterException exception)
		{
			LOG.error("[Brain Tree Controller] Error during token generation!");
		}
		final PayPalCheckoutData payPalCheckoutData = brainTreeCheckoutFacade.getPayPalCheckoutData();
		payPalMiniCartResponse.setCheckoutData(payPalCheckoutData);
		payPalMiniCartResponse.setClientToken(clientToken);
		return mapper.writeValueAsString(payPalMiniCartResponse);
	}

	private BrainTreeSubscriptionInfoData buildSubscriptionInfo(final String nonce, final String paymentProvider,
			final String cardDetails, final String cardType)
	{
		final BrainTreeSubscriptionInfoData subscriptionInfo = new BrainTreeSubscriptionInfoData();
		subscriptionInfo.setPaymentProvider(paymentProvider);
		subscriptionInfo.setCardNumber(cardDetails);
		subscriptionInfo.setCardType(cardType);
		subscriptionInfo.setNonce(nonce);

		return subscriptionInfo;
	}

	private void handleErrors(final String errorsDetail, final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("errorsDetail", errorsDetail);
		final String redirectUrl = REDIRECT_URL_CART;
		model.addAttribute("redirectUrl", redirectUrl.replace(REDIRECT_PREFIX, ""));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.hostedOrderPageError.breadcrumb"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));

		GlobalMessages.addErrorMessage(model, getLocalizedString(PAY_PAL_HAED_ERROR));
	}

}
