/**
 *
 */
package com.braintree.facade.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.acceleratorfacades.order.impl.DefaultAcceleratorCheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.hybris.data.PayPalAddressData;
import com.braintree.hybris.data.PayPalCheckoutData;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.paypal.converters.impl.PayPalAddressDataConverter;
import com.braintree.paypal.converters.impl.PayPalCardDataConverter;
import com.braintree.transaction.service.BrainTreeTransactionService;


/**
 * @author Roman_Chupa
 *
 */
public class BrainTreeCheckoutFacade extends DefaultAcceleratorCheckoutFacade
{

	private static final Logger LOG = Logger.getLogger(BrainTreeCheckoutFacade.class);

	private Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> brainTreePaymentInfoConverter;
	private BrainTreePaymentService brainTreePaymentService;
	private BrainTreeTransactionService brainTreeTransactionService;
	private CartService cartService;
	private UserService userService;
	private PayPalAddressDataConverter payPalAddressDataConverter;
	private PayPalCardDataConverter payPalCardDataConverter;
	private BrainTreeConfigService brainTreeConfigService;

	@Override
	public boolean authorizePayment(final String securityCode)
	{
		if (Boolean.FALSE.equals(brainTreeConfigService.getPayPalStandardEnabled())
				&& Boolean.FALSE.equals(brainTreeConfigService.getHostedFieldEnabled()))
		{
			LOG.info("Use default accelerator checkout flow.");
			return super.authorizePayment(securityCode);
		}

		return brainTreeTransactionService.createAuthorizationTransaction();
	}

	@Override
	public CCPaymentInfoData getPaymentDetails()
	{
		final CartModel cart = getCart();
		if (cart != null)
		{
			final PaymentInfoModel paymentInfo = cart.getPaymentInfo();
			if (paymentInfo instanceof BrainTreePaymentInfoModel)
			{
				return brainTreePaymentInfoConverter.convert((BrainTreePaymentInfoModel) paymentInfo);
			}
			else
			{
				return super.getPaymentDetails();
			}
		}

		return null;
	}


	@Override
	public boolean setPaymentDetails(final String paymentInfoId)
	{
		validateParameterNotNullStandardMessage("paymentInfoId", paymentInfoId);

		if (checkIfCurrentUserIsTheCartUser())
		{
			final CustomerModel currentUserForCheckout = getCurrentUserForCheckout();
			if (StringUtils.isNotBlank(paymentInfoId))
			{
				final BrainTreePaymentInfoModel paymentInfo = brainTreePaymentService
						.completeCreateSubscription(currentUserForCheckout, paymentInfoId);
				if (paymentInfo != null)
				{
					return true;
				}
				else
				{
					super.setPaymentDetails(paymentInfoId);
				}

			}
		}

		return false;
	}

	public boolean authorizePaymentForReplenishment()
	{
		return brainTreeTransactionService.createPaymentMethodTokenForOrderReplenishment();
	}

	public String generateClientToken()
	{
		final String clientToken = brainTreePaymentService.generateClientToken();
		return clientToken;
	}

	public PayPalCheckoutData getPayPalCheckoutData()
	{
		final PayPalCheckoutData payPalCheckoutData = payPalCardDataConverter.convert(cartService.getSessionCart());

		if (cartService.getSessionCart().getDeliveryAddress() != null)
		{
			final PayPalAddressData payPalAddress = payPalAddressDataConverter
					.convert(cartService.getSessionCart().getDeliveryAddress());
			payPalCheckoutData.setShippingAddressOverride(payPalAddress);
			payPalCheckoutData.setEnvironment(brainTreeConfigService.getEnvironmentTypeName());
			payPalCheckoutData.setSecure3d(brainTreeConfigService.get3dSecureConfiguration());
			payPalCheckoutData.setSkip3dSecureLiabilityResult(brainTreeConfigService.getIsSkip3dSecureLiabilityResult());
			payPalCheckoutData.setKountId(brainTreeConfigService.getBrainTreeKountId());
		}
		return payPalCheckoutData;
	}



	public void setBrainTreePaymentInfoConverter(
			final Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> brainTreePaymentInfoConverter)
	{
		this.brainTreePaymentInfoConverter = brainTreePaymentInfoConverter;
	}

	public Map<String, String> getAcceptedPaymentMethodImages()
	{
		final Map<String, String> acceptedPaymentMethodImages = brainTreeConfigService.getAcceptedPaymentMethodImages();
		return acceptedPaymentMethodImages;
	}

	/**
	 * @return the brainTreePaymentService
	 */
	public BrainTreePaymentService getBrainTreePaymentService()
	{
		return brainTreePaymentService;
	}

	/**
	 * @param brainTreePaymentService
	 *           the brainTreePaymentService to set
	 */
	public void setBrainTreePaymentService(final BrainTreePaymentService brainTreePaymentService)
	{
		this.brainTreePaymentService = brainTreePaymentService;
	}

	/**
	 * @return the brainTreeTransactionService
	 */
	public BrainTreeTransactionService getBrainTreeTransactionService()
	{
		return brainTreeTransactionService;
	}

	/**
	 * @param brainTreeTransactionService
	 *           the brainTreeTransactionService to set
	 */
	public void setBrainTreeTransactionService(final BrainTreeTransactionService brainTreeTransactionService)
	{
		this.brainTreeTransactionService = brainTreeTransactionService;
	}

	/**
	 * @return the cartService
	 */
	@Override
	public CartService getCartService()
	{
		return cartService;
	}

	/**
	 * @param cartService
	 *           the cartService to set
	 */
	@Override
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	/**
	 * @return the userService
	 */
	@Override
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	@Override
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the payPalAddressDataConverter
	 */
	public PayPalAddressDataConverter getPayPalAddressDataConverter()
	{
		return payPalAddressDataConverter;
	}

	/**
	 * @param payPalAddressDataConverter
	 *           the payPalAddressDataConverter to set
	 */
	public void setPayPalAddressDataConverter(final PayPalAddressDataConverter payPalAddressDataConverter)
	{
		this.payPalAddressDataConverter = payPalAddressDataConverter;
	}

	/**
	 * @return the payPalCardDataConverter
	 */
	public PayPalCardDataConverter getPayPalCardDataConverter()
	{
		return payPalCardDataConverter;
	}

	/**
	 * @param payPalCardDataConverter
	 *           the payPalCardDataConverter to set
	 */
	public void setPayPalCardDataConverter(final PayPalCardDataConverter payPalCardDataConverter)
	{
		this.payPalCardDataConverter = payPalCardDataConverter;
	}

	/**
	 * @return the brainTreeConfigService
	 */
	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	/**
	 * @param brainTreeConfigService
	 *           the brainTreeConfigService to set
	 */
	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}

	/**
	 * @return the brainTreePaymentInfoConverter
	 */
	public Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> getBrainTreePaymentInfoConverter()
	{
		return brainTreePaymentInfoConverter;
	}


}
