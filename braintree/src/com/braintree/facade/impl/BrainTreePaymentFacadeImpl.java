/**
 *
 */
package com.braintree.facade.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.acceleratorfacades.payment.impl.DefaultPaymentFacade;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import com.braintree.hybris.data.BrainTreePaymentInfoData;
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.paypal.converters.impl.BrainTreePaymentInfoDataConverter;


public class BrainTreePaymentFacadeImpl extends DefaultPaymentFacade
{

	private BrainTreePaymentService brainTreePaymentService;
	private CartService cartService;
	private BaseStoreService baseStoreService;
	private CustomerAccountService customerAccountService;
	private BrainTreePaymentInfoDataConverter brainTreePaymentInfoDataConverter;
	private CommerceCartService commerceCartService;

	public void completeCreateSubscription(final BrainTreeSubscriptionInfoData subscriptionInfo)
	{
		final CustomerModel customer = getCurrentUserForCheckout();
		getBrainTreePaymentService().completeCreateSubscription(subscriptionInfo, customer);
	}

	public BrainTreePaymentInfoData getBrainTreePaymentInfoData()
	{
		final PaymentInfoModel paymentInfo = getCartService().getSessionCart().getPaymentInfo();
		if (paymentInfo instanceof BrainTreePaymentInfoModel)
		{

			validateParameterNotNullStandardMessage("paymentInfo", paymentInfo);

			final BrainTreePaymentInfoData paymentData = getBrainTreePaymentInfoDataConverter()
					.convert((BrainTreePaymentInfoModel) paymentInfo);
			return paymentData;
		}
		return null;
	}

	public BrainTreePaymentInfoData getBrainTreePaymentInfoData(final String orderCode)
	{
		final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();
		final OrderModel orderModel = getCustomerAccountService().getOrderForCode(orderCode, baseStoreModel);
		final PaymentInfoModel paymentInfo = orderModel.getPaymentInfo();
		if (paymentInfo instanceof BrainTreePaymentInfoModel)
		{

			validateParameterNotNullStandardMessage("paymentInfo", paymentInfo);

			final BrainTreePaymentInfoData paymentData = getBrainTreePaymentInfoDataConverter()
					.convert((BrainTreePaymentInfoModel) paymentInfo);
			return paymentData;
		}
		return null;
	}

	public BrainTreePaymentInfoData getBrainTreePaymentInfoDataByCart(final String cartCode)
	{
		final CartModel cartModel = getCommerceCartService().getCartForCodeAndUser(cartCode, getUserService().getCurrentUser());
		final BrainTreePaymentInfoModel paymentInfo = (BrainTreePaymentInfoModel) cartModel.getPaymentInfo();
		validateParameterNotNullStandardMessage("paymentInfo", paymentInfo);
		final BrainTreePaymentInfoData paymentData = getBrainTreePaymentInfoDataConverter().convert(paymentInfo);
		return paymentData;
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
	 * @return the cartService
	 */
	public CartService getCartService()
	{
		return cartService;
	}

	/**
	 * @param cartService
	 *           the cartService to set
	 */
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

	/**
	 * @return the brainTreePaymentInfoDataConverter
	 */
	public BrainTreePaymentInfoDataConverter getBrainTreePaymentInfoDataConverter()
	{
		return brainTreePaymentInfoDataConverter;
	}

	/**
	 * @param brainTreePaymentInfoDataConverter
	 *           the brainTreePaymentInfoDataConverter to set
	 */
	public void setBrainTreePaymentInfoDataConverter(final BrainTreePaymentInfoDataConverter brainTreePaymentInfoDataConverter)
	{
		this.brainTreePaymentInfoDataConverter = brainTreePaymentInfoDataConverter;
	}

	/**
	 * @return the commerceCartService
	 */
	public CommerceCartService getCommerceCartService()
	{
		return commerceCartService;
	}

	/**
	 * @param commerceCartService
	 *           the commerceCartService to set
	 */
	public void setCommerceCartService(final CommerceCartService commerceCartService)
	{
		this.commerceCartService = commerceCartService;
	}

}
