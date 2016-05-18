/**
 *
 */
package com.braintree.controllers.handler;

import static com.braintree.constants.Braintreeb2baddonWebConstants.ACCEPTED_PAYMENTS_METHODS_IMAGES_URL;
import static com.braintree.constants.Braintreeb2baddonWebConstants.HOSTED_FIELDS_ENABLE;
import static com.braintree.constants.Braintreeb2baddonWebConstants.PAYMENT_INFOS;
import static com.braintree.constants.Braintreeb2baddonWebConstants.PAY_PAL_EXPRESS_ENABLE;
import static com.braintree.constants.Braintreeb2baddonWebConstants.PAY_PAL_STANDARD_ENABLE;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.CLIENT_TOKEN;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.PAY_PAL_CHECKOUT_DATA;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.Views.Pages.Checkout.CheckoutConfirmationPage;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.Views.Pages.Checkout.ReplenishmentCheckoutConfirmationPage;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.Views.Pages.MultiStepCheckout.CheckoutSummaryPage;
import static com.braintree.controllers.Braintreeb2baddonControllerConstants.Views.Pages.MultiStepCheckout.SilentOrderPostPage;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import de.hybris.platform.addonsupport.interceptors.BeforeViewHandlerAdaptee;
import de.hybris.platform.b2bacceleratorfacades.order.data.ScheduledCartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.Registry;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.util.config.ConfigIntf;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.braintree.configuration.BrainTreeConfigurationListener;
import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.constants.ControllerConstants;
import com.braintree.controllers.Braintreeb2baddonControllerConstants;
import com.braintree.facade.impl.BrainTreeCheckoutFacade;
import com.braintree.facade.impl.BrainTreePaymentFacadeImpl;
import com.braintree.facade.impl.BrainTreeUserFacade;
import com.braintree.hybris.data.BrainTreePaymentInfoData;
import com.braintree.hybris.data.PayPalCheckoutData;



public class BrainTreeBeforeViewHandler implements BeforeViewHandlerAdaptee

{
	private final static Logger LOG = Logger.getLogger(BrainTreeBeforeViewHandler.class);

	private static final String CART_PAGE = "pages/cart/cartPage";
	private static final String BRAIN_TREE_PAYMENT_DATA = "brainTreePaymentInfoData";
	private static final String ORDER_DATA = "orderData";
	private static final String SCHEDULE_DATA = "scheduleData";
	private static final String B2C_ADD_TO_CART_POPUP_PAGE = "fragments/cart/addToCartPopup";
	private static final String B2C__CART_POPUP_PAGE = "fragments/cart/cartPopup";
	private static final String AccountLayoutPage = "pages/account/accountLayoutPage";

	@Resource(name = "brainTreeCheckoutFacade")
	private BrainTreeCheckoutFacade brainTreeCheckoutFacade;
	@Resource(name = "brainTreePaymentFacadeImpl")
	private BrainTreePaymentFacadeImpl brainTreePaymentFacade;
	@Resource(name = "brainTreeUserFacade")
	private BrainTreeUserFacade brainTreeUserFacade;
	@Resource(name = "brainTreeConfigService")
	private BrainTreeConfigService brainTreeConfigService;

	private ConfigIntf.ConfigChangeListener configurationChangeListener;

	@Override
	public String beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model,
			final String viewName) throws Exception
	{

		if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
				&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled())
				&& Boolean.FALSE.equals(brainTreeConfigService.getPayPalExpressEnabled()))
		{
			return viewName;
		}
		return handleBrainTreeCheckoutScenario(model, viewName);

	}

	private String handleBrainTreeCheckoutScenario(final ModelMap model, final String viewName)
	{

		if (configurationChangeListener == null)
		{
			registerConfigChangeLister();
		}

		if (CART_PAGE.equals(viewName))
		{
			setPayPalExpressEnabled(model);
			fillPaymentMethodsInfo(model);
		}

		else if (ControllerConstants.Views.Pages.MultiStepCheckout.SilentOrderPostPage.equals(viewName))
		{
			if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
					&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled()))
			{
				return viewName;
			}
			setHostedFieldEnabled(model);
			fillPaymentMethodsInfo(model);
			final Map<String, String> paymentsImagesURL = brainTreeCheckoutFacade.getAcceptedPaymentMethodImages();
			model.addAttribute(ACCEPTED_PAYMENTS_METHODS_IMAGES_URL, paymentsImagesURL);
			return SilentOrderPostPage;
		}

		else if (ControllerConstants.Views.Pages.MultiStepCheckout.CheckoutSummaryPage.equals(viewName))
		{
			if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
					&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled()))
			{
				return viewName;
			}
			final BrainTreePaymentInfoData brainTreePaymentInfoData = brainTreePaymentFacade.getBrainTreePaymentInfoData();

			if (brainTreePaymentInfoData != null)
			{
				model.addAttribute(BRAIN_TREE_PAYMENT_DATA, brainTreePaymentInfoData);
			}
			return CheckoutSummaryPage;
		}

		else if (ControllerConstants.Views.Pages.MultiStepCheckout.CheckoutConfirmationPage.equals(viewName))
		{
			if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
					&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled()))
			{
				return viewName;
			}
			final String orderCode = ((OrderData) model.get(ORDER_DATA)).getCode();
			if (isNotBlank(orderCode))
			{
				final BrainTreePaymentInfoData brainTreePaymentInfoData = brainTreePaymentFacade
						.getBrainTreePaymentInfoData(orderCode);
				if (brainTreePaymentInfoData != null)
				{
					model.addAttribute(BRAIN_TREE_PAYMENT_DATA, brainTreePaymentInfoData);
				}
			}
			return CheckoutConfirmationPage;
		}

		else if (ControllerConstants.Views.Pages.MultiStepCheckout.ReplenishmentCheckoutConfirmationPage.equals(viewName))
		{
			if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
					&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled()))
			{
				return viewName;
			}

			final String cartCode = ((ScheduledCartData) model.get(ORDER_DATA)).getCode();
			if (isNotBlank(cartCode))
			{
				model.addAttribute(BRAIN_TREE_PAYMENT_DATA, brainTreePaymentFacade.getBrainTreePaymentInfoDataByCart(cartCode));
			}
			return ReplenishmentCheckoutConfirmationPage;
		}

		else if (ControllerConstants.Views.Pages.Account.AccountLayoutPage.equals(viewName))
		{
			model.addAttribute(PAYMENT_INFOS, brainTreeUserFacade.getBrainTreeCCPaymentInfos(true));
			if (model.get(SCHEDULE_DATA) != null)
			{
				final String cartCode = ((ScheduledCartData) model.get(SCHEDULE_DATA)).getCode();
				if (isNotBlank(cartCode))
				{
					model.addAttribute(BRAIN_TREE_PAYMENT_DATA, brainTreePaymentFacade.getBrainTreePaymentInfoDataByCart(cartCode));
				}
			}
			return AccountLayoutPage;
		}

		else if (B2C_ADD_TO_CART_POPUP_PAGE.equals(viewName))
		{
			if (brainTreeConfigService.getPayPalExpressEnabled().booleanValue())
			{
				fillPaymentMethodsInfo(model);
				return Braintreeb2baddonControllerConstants.Views.Fragments.Cart.AddToCartPopup;
			}
			return viewName;
		}

		else if (B2C__CART_POPUP_PAGE.equals(viewName))
		{
			if (brainTreeConfigService.getPayPalExpressEnabled().booleanValue())
			{
				fillPaymentMethodsInfo(model);
				return Braintreeb2baddonControllerConstants.Views.Fragments.Cart.CartPopup;
			}
			return viewName;
		}
		return viewName;
	}


	private void setPayPalExpressEnabled(final ModelMap model)
	{
		model.addAttribute(PAY_PAL_EXPRESS_ENABLE, brainTreeConfigService.getPayPalExpressEnabled());
	}

	private void setHostedFieldEnabled(final ModelMap model)
	{
		model.addAttribute(HOSTED_FIELDS_ENABLE, brainTreeConfigService.getHostedFieldEnabled());
		model.addAttribute(PAY_PAL_STANDARD_ENABLE, brainTreeConfigService.getPayPalStandardEnabled());
	}

	private void fillPaymentMethodsInfo(final ModelMap model)
	{
		String clientToken = StringUtils.EMPTY;

		try
		{
			clientToken = brainTreeCheckoutFacade.generateClientToken();
		}
		catch (final AdapterException exception)
		{
			LOG.error("[View Handler] Error during token generation!");
		}
		final PayPalCheckoutData payPalCheckoutData = brainTreeCheckoutFacade.getPayPalCheckoutData();
		model.addAttribute(CLIENT_TOKEN, clientToken);
		model.addAttribute(PAY_PAL_CHECKOUT_DATA, payPalCheckoutData);
		model.addAttribute(PAYMENT_INFOS, brainTreeUserFacade.getBrainTreeCCPaymentInfos(true));
	}

	private void registerConfigChangeLister()
	{
		final ConfigIntf config = Registry.getMasterTenant().getConfig();
		configurationChangeListener = new BrainTreeConfigurationListener();
		config.registerConfigChangeListener(configurationChangeListener);
	}


}
