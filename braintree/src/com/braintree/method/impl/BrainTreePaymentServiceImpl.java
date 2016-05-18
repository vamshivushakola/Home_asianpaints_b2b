/**
 *
 */
package com.braintree.method.impl;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_PROVIDER_NAME;
import static com.braintree.constants.BraintreeConstants.CARD_NUMBER_MASK;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.commands.AuthorizationCommand;
import de.hybris.platform.payment.commands.CreateSubscriptionCommand;
import de.hybris.platform.payment.commands.factory.CommandFactory;
import de.hybris.platform.payment.commands.factory.CommandFactoryRegistry;
import de.hybris.platform.payment.commands.factory.CommandNotSupportedException;
import de.hybris.platform.payment.commands.request.AuthorizationRequest;
import de.hybris.platform.payment.commands.request.CreateSubscriptionRequest;
import de.hybris.platform.payment.commands.request.VoidRequest;
import de.hybris.platform.payment.commands.result.AuthorizationResult;
import de.hybris.platform.payment.commands.result.SubscriptionResult;
import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.command.request.BrainTreeAddressRequest;
import com.braintree.command.request.BrainTreeAuthorizationRequest;
import com.braintree.command.request.BrainTreeCloneTransactionRequest;
import com.braintree.command.request.BrainTreeCreateCreditCardPaymentMethodRequest;
import com.braintree.command.request.BrainTreeCreatePaymentMethodRequest;
import com.braintree.command.request.BrainTreeCustomerRequest;
import com.braintree.command.request.BrainTreeDeletePaymentMethodRequest;
import com.braintree.command.request.BrainTreeFindMerchantAccountRequest;
import com.braintree.command.request.BrainTreeGenerateClientTokenRequest;
import com.braintree.command.request.BrainTreeRefundTransactionRequest;
import com.braintree.command.request.BrainTreeSaleTransactionRequest;
import com.braintree.command.request.BrainTreeSubmitForSettlementTransactionRequest;
import com.braintree.command.request.BrainTreeUpdateCustomerRequest;
import com.braintree.command.request.BrainTreeUpdatePaymentMethodRequest;
import com.braintree.command.result.BrainTreeAddressResult;
import com.braintree.command.result.BrainTreeCloneTransactionResult;
import com.braintree.command.result.BrainTreeCreatePaymentMethodResult;
import com.braintree.command.result.BrainTreeCustomerResult;
import com.braintree.command.result.BrainTreeFindCustomerResult;
import com.braintree.command.result.BrainTreeFindMerchantAccountResult;
import com.braintree.command.result.BrainTreeGenerateClientTokenResult;
import com.braintree.command.result.BrainTreePaymentMethodResult;
import com.braintree.command.result.BrainTreeRefundTransactionResult;
import com.braintree.command.result.BrainTreeSaleTransactionResult;
import com.braintree.command.result.BrainTreeSubmitForSettlementTransactionResult;
import com.braintree.command.result.BrainTreeUpdateCustomerResult;
import com.braintree.command.result.BrainTreeUpdatePaymentMethodResult;
import com.braintree.command.result.BrainTreeVoidResult;
import com.braintree.commands.BrainTreeCloneCommand;
import com.braintree.commands.BrainTreeCreateAddressCommand;
import com.braintree.commands.BrainTreeCreateCreditCardPaymentMethodCommand;
import com.braintree.commands.BrainTreeCreatePaymentMethodCommand;
import com.braintree.commands.BrainTreeDeletePaymentMethodCommand;
import com.braintree.commands.BrainTreeFindCustomerCommand;
import com.braintree.commands.BrainTreeFindMerchantAccountCommand;
import com.braintree.commands.BrainTreeGenerateClientTokenCommand;
import com.braintree.commands.BrainTreeRefundCommand;
import com.braintree.commands.BrainTreeRemoveAddressCommand;
import com.braintree.commands.BrainTreeRemoveCustomerCommand;
import com.braintree.commands.BrainTreeSaleCommand;
import com.braintree.commands.BrainTreeSubmitForSettlementCommand;
import com.braintree.commands.BrainTreeUpdateAddressCommand;
import com.braintree.commands.BrainTreeUpdateCustomerCommand;
import com.braintree.commands.BrainTreeUpdatePaymentMethodCommand;
import com.braintree.commands.BrainTreeVoidCommand;
import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.customer.service.BrainTreeCustomerAccountService;
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.payment.strategy.BrainTreePaymentInfoCreateStrategy;
import com.braintree.paypal.converters.impl.BillingAddressConverter;
import com.braintreegateway.exceptions.NotFoundException;


public class BrainTreePaymentServiceImpl implements BrainTreePaymentService
{

	private final static Logger LOG = Logger.getLogger(BrainTreePaymentServiceImpl.class);

	private CommandFactoryRegistry commandFactoryRegistry;
	private BrainTreePaymentInfoCreateStrategy brainTreePaymentInfoCreateStrategy;
	private ModelService modelService;
	private UserService userService;
	private CartService cartService;
	private CommonI18NService commonI18NService;
	private Converter<AddressModel, AddressData> addressConverter;
	private BillingAddressConverter billingAddressConverter;
	private BrainTreeCustomerAccountService brainTreeCustomerAccountService;
	private CheckoutCustomerStrategy checkoutCustomerStrategy;
	private BrainTreeConfigService brainTreeConfigService;

	@Override
	public AuthorizationResult authorize(final AuthorizationRequest authorizationRequest)
	{
		try
		{
			final CustomerModel customer = checkoutCustomerStrategy.getCurrentUserForCheckout();
			if (!checkIfBrainTreeCustomerExist(customer.getBraintreeCustomerId()))
			{
				saveBraintreeCustomerId(customer);
				((BrainTreeAuthorizationRequest) authorizationRequest).setCustomerId(customer.getBraintreeCustomerId());
			}
			final AuthorizationCommand command = getCommandFactory().createCommand(AuthorizationCommand.class);
			final AuthorizationResult result = command.perform(authorizationRequest);

			return result;
		}
		catch (final Exception exception)
		{
			if (exception instanceof NotFoundException)
			{
				LOG.error("[BT Payment Service] Errors occured not fount some item in BrainTree(throws NotFoundException) ");
				throw new AdapterException("Problem occurred in Payment Provider configuration. Please contact with store support.");
			}
			LOG.error("[BT Payment Service] Errors during authorization: " + exception.getMessage());
			throw new AdapterException(exception.getMessage());
		}
	}

	@Override
	public BrainTreeVoidResult voidTransaction(final VoidRequest voidRequest)
	{
		try
		{
			final BrainTreeVoidCommand command = getCommandFactory().createCommand(BrainTreeVoidCommand.class);
			final BrainTreeVoidResult result = command.perform(voidRequest);

			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to void transaction: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeCloneTransactionResult cloneTransaction(final BrainTreeCloneTransactionRequest request)
	{
		try
		{
			final BrainTreeCloneCommand command = getCommandFactory().createCommand(BrainTreeCloneCommand.class);
			final BrainTreeCloneTransactionResult result = command.perform(request);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to clone transaction: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeRefundTransactionResult refundTransaction(final BrainTreeRefundTransactionRequest request)
	{
		try
		{
			final BrainTreeRefundCommand command = getCommandFactory().createCommand(BrainTreeRefundCommand.class);
			final BrainTreeRefundTransactionResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to refund transaction: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeUpdateCustomerResult updateCustomer(final BrainTreeUpdateCustomerRequest request)
	{
		try
		{
			final BrainTreeUpdateCustomerCommand command = getCommandFactory().createCommand(BrainTreeUpdateCustomerCommand.class);
			final BrainTreeUpdateCustomerResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to update customer: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeSaleTransactionResult saleTransaction(final BrainTreeSaleTransactionRequest request)
	{
		try
		{
			final BrainTreeSaleCommand command = getCommandFactory().createCommand(BrainTreeSaleCommand.class);
			final BrainTreeSaleTransactionResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to sate transaction: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeCustomerResult removeCustomer(final BrainTreeCustomerRequest request)
	{
		try
		{
			final BrainTreeRemoveCustomerCommand command = getCommandFactory().createCommand(BrainTreeRemoveCustomerCommand.class);
			final BrainTreeCustomerResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to remove customer: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeUpdatePaymentMethodResult updatePaymentMethod(final BrainTreeUpdatePaymentMethodRequest request)
	{
		try
		{
			final BrainTreeUpdatePaymentMethodCommand command = getCommandFactory()
					.createCommand(BrainTreeUpdatePaymentMethodCommand.class);
			final BrainTreeUpdatePaymentMethodResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to update payment Method: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public SubscriptionResult createCustomerSubscription(final CreateSubscriptionRequest subscriptionRequest)
			throws AdapterException
	{
		try
		{
			final CreateSubscriptionCommand command = getCommandFactory().createCommand(CreateSubscriptionCommand.class);
			final SubscriptionResult result = command.perform(subscriptionRequest);

			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during customer creation: " + exception.getMessage());
			throw new AdapterException(exception.getMessage());
		}
	}

	@Override
	public BrainTreeGenerateClientTokenResult generateClientToken(final BrainTreeGenerateClientTokenRequest clientTokenRequest)
			throws AdapterException
	{
		try
		{
			final BrainTreeGenerateClientTokenCommand command = getCommandFactory()
					.createCommand(BrainTreeGenerateClientTokenCommand.class);

			final BrainTreeGenerateClientTokenResult result = command.perform(clientTokenRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during token generation: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeFindCustomerResult findCustomer(final BrainTreeCustomerRequest findCustomerRequest) throws AdapterException
	{
		try
		{
			final BrainTreeFindCustomerCommand command = getCommandFactory().createCommand(BrainTreeFindCustomerCommand.class);
			final BrainTreeFindCustomerResult result = command.perform(findCustomerRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to fin customer generation: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeSubmitForSettlementTransactionResult submitForSettlementTransaction(
			final BrainTreeSubmitForSettlementTransactionRequest request)
	{
		try
		{
			final BrainTreeSubmitForSettlementCommand command = getCommandFactory()
					.createCommand(BrainTreeSubmitForSettlementCommand.class);
			final BrainTreeSubmitForSettlementTransactionResult result = command.perform(request);

			return result;
		}
		catch (final CommandNotSupportedException exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to submit for settlement transaction: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	@Override
	public BrainTreeAddressResult createAddress(final BrainTreeAddressRequest addressRequest)
	{
		if (addressRequest.getCustomerId() == null)
		{
			final CustomerModel customer = checkoutCustomerStrategy.getCurrentUserForCheckout();
			saveBraintreeCustomerId(customer);
			addressRequest.setCustomerId(customer.getBraintreeCustomerId());
		}

		try
		{
			final BrainTreeCreateAddressCommand command = getCommandFactory().createCommand(BrainTreeCreateAddressCommand.class);
			final BrainTreeAddressResult result = command.perform(addressRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during address creation: " + exception.getMessage());
			return null;
		}
	}

	@Override
	public BrainTreeAddressResult updateAddress(final BrainTreeAddressRequest addressRequest)
	{
		try
		{
			final BrainTreeUpdateAddressCommand command = getCommandFactory().createCommand(BrainTreeUpdateAddressCommand.class);
			final BrainTreeAddressResult result = command.perform(addressRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during address[" + addressRequest.getAddressId() + "] update: "
					+ exception.getMessage());
		}
		return null;
	}

	@Override
	public BrainTreeAddressResult removeAddress(final BrainTreeAddressRequest addressRequest)
	{
		try
		{
			final BrainTreeRemoveAddressCommand command = getCommandFactory().createCommand(BrainTreeRemoveAddressCommand.class);
			final BrainTreeAddressResult result = command.perform(addressRequest);

			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during address[" + addressRequest.getAddressId() + "] creation: "
					+ exception.getMessage());
		}
		return new BrainTreeAddressResult();
	}

	@Override
	public void completeCreateSubscription(final BrainTreeSubscriptionInfoData brainTreeSubscriptionInfoData,
			final CustomerModel customer)
	{
		final CartModel card = getCart();
		BrainTreePaymentInfoModel paymentInfo = null;
		AddressData addressData = brainTreeSubscriptionInfoData.getAddressData();

		if (isNotEmpty(brainTreeSubscriptionInfoData.getCardNumber()))
		{
			brainTreeSubscriptionInfoData
					.setCardNumber(String.format(CARD_NUMBER_MASK, brainTreeSubscriptionInfoData.getCardNumber()));
		}

		if (brainTreeSubscriptionInfoData.isSavePaymentInfo())
		{
			final BrainTreeCreatePaymentMethodResult result = getPaymentMethodToken(customer,
					brainTreeSubscriptionInfoData.getNonce());
			if (result != null)
			{
				brainTreeSubscriptionInfoData.setPaymentMethodToken(result.getPaymentMethodToken());
				brainTreeSubscriptionInfoData.setExpirationMonth(result.getExpirationMonth());
				brainTreeSubscriptionInfoData.setExpirationYear(result.getExpirationYear());
				brainTreeSubscriptionInfoData.setImageSource(result.getImageSource());
				brainTreeSubscriptionInfoData.setCardNumber(result.getCardNumber());
				brainTreeSubscriptionInfoData.setCardType(result.getCardType());
				if (StringUtils.isNotBlank(result.getEmail()))
				{
					brainTreeSubscriptionInfoData.setEmail(result.getEmail());
				}
			}

		}

		if (addressData != null)
		{
			paymentInfo = brainTreePaymentInfoCreateStrategy.saveSubscription(addressData, customer, brainTreeSubscriptionInfoData);
		}
		else
		{
			addressData = addressConverter.convert(card.getDeliveryAddress());
			if (StringUtils.isNotBlank(brainTreeSubscriptionInfoData.getEmail()))
			{
				addressData.setEmail(brainTreeSubscriptionInfoData.getEmail());
			}
			addressData.setBrainTreeAddressId(card.getDeliveryAddress().getBrainTreeAddressId());

			paymentInfo = brainTreePaymentInfoCreateStrategy.saveSubscription(addressData, customer, brainTreeSubscriptionInfoData);
		}
		paymentInfo.setUsePaymentMethodToken(Boolean.valueOf(brainTreeSubscriptionInfoData.isSavePaymentInfo()));
		modelService.save(paymentInfo);
		card.setPaymentInfo(paymentInfo);
		modelService.save(card);
	}

	@Override
	public BrainTreePaymentInfoModel completeCreateSubscription(final CustomerModel customer, final String paymentInfoId)
	{
		final CartModel card = getCart();

		final BrainTreePaymentInfoModel paymentInfo = brainTreeCustomerAccountService.getBrainTreePaymentInfoForCode(customer,
				paymentInfoId);

		if (paymentInfo != null)
		{
			paymentInfo.setUsePaymentMethodToken(Boolean.TRUE);
			modelService.save(paymentInfo);
			card.setPaymentInfo(paymentInfo);
			modelService.save(card);

			return paymentInfo;
		}
		else
		{
			return null;
		}

	}

	@Override
	public String generateClientToken()
	{
		return getClientToken();
	}

	@Override
	public BrainTreeCreatePaymentMethodResult createPaymentMethod(final BrainTreeCreatePaymentMethodRequest request)
	{
		try
		{
			final BrainTreeCreatePaymentMethodCommand command = getCommandFactory()
					.createCommand(BrainTreeCreatePaymentMethodCommand.class);
			final BrainTreeCreatePaymentMethodResult result = command.perform(request);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors payment method creating: " + exception.getMessage());
			return null;
		}
	}

	@Override
	public BrainTreePaymentMethodResult createCreditCardPaymentMethod(final BrainTreeCreateCreditCardPaymentMethodRequest request)
	{
		try
		{
			final BrainTreeCreateCreditCardPaymentMethodCommand command = getCommandFactory()
					.createCommand(BrainTreeCreateCreditCardPaymentMethodCommand.class);
			final BrainTreePaymentMethodResult result = command.perform(request);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors payment method creating: " + exception.getMessage());
			return null;
		}
	}

	@Override
	public BrainTreeFindMerchantAccountResult findMerchantAccount(
			final BrainTreeFindMerchantAccountRequest brainTreeFindMerchantAccountRequest)
	{
		try
		{
			final BrainTreeFindMerchantAccountCommand command = getCommandFactory()
					.createCommand(BrainTreeFindMerchantAccountCommand.class);
			final BrainTreeFindMerchantAccountResult result = command.perform(brainTreeFindMerchantAccountRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to find merchant account: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	private CartModel getCart()
	{
		return cartService.getSessionCart();
	}

	private String getClientToken()
	{

		final String merchantAccountId = getBrainTreeConfigService().getMerchantAccountIdForCurrentSiteAndCurrency();
		if (StringUtils.isNotEmpty(merchantAccountId))
		{
			final BrainTreeFindMerchantAccountRequest brainTreeFindMerchantAccountRequest = new BrainTreeFindMerchantAccountRequest(
					StringUtils.EMPTY);
			brainTreeFindMerchantAccountRequest.setMerchantAccount(merchantAccountId);
			final boolean isMerchantAccountExist = findMerchantAccount(brainTreeFindMerchantAccountRequest).isMerchantAccountExist();
			if (isMerchantAccountExist)
			{
				brainTreeFindMerchantAccountRequest.setMerchantAccount(merchantAccountId);
			}
		}
		final BrainTreeGenerateClientTokenResult response = generateClientToken(
				new BrainTreeGenerateClientTokenRequest(StringUtils.EMPTY));
		return response.getClientToken();
	}

	private BrainTreeCreatePaymentMethodResult getPaymentMethodToken(final CustomerModel customer, final String methodNonce)
	{
		final BrainTreeCreatePaymentMethodRequest request = new BrainTreeCreatePaymentMethodRequest(null, methodNonce,
				customer.getBraintreeCustomerId());
		if (!checkIfBrainTreeCustomerExist(customer.getBraintreeCustomerId()))
		{
			final String customerId = saveBraintreeCustomerId(customer);
			request.setCustomerId(customerId);
		}

		final BrainTreeCreatePaymentMethodResult result = createPaymentMethod(request);
		return result;
	}

	private boolean checkIfBrainTreeCustomerExist(final String braintreeCustomerId)
	{
		if (StringUtils.isEmpty(braintreeCustomerId))
		{
			return Boolean.FALSE.booleanValue();
		}
		final BrainTreeCustomerRequest findCustomerRequest = new BrainTreeCustomerRequest(braintreeCustomerId);
		findCustomerRequest.setCustomerId(braintreeCustomerId);
		final BrainTreeFindCustomerResult response = findCustomer(findCustomerRequest);
		return response.isCustomerExist();
	}

	private SubscriptionResult createCustomerSubscription()
	{
		final AddressModel shippingAddress = getCart().getDeliveryAddress();
		final BillingInfo billingInfo = new BillingInfo();
		if (shippingAddress != null)
		{
			billingAddressConverter.convert(shippingAddress, billingInfo);
		}
		else
		{
			billingInfo.setEmail(((CustomerModel) userService.getCurrentUser()).getContactEmail());
		}

		final CreateSubscriptionRequest createSubscriptionRequest = new CreateSubscriptionRequest(null, billingInfo, null, null,
				null, null, null);

		final SubscriptionResult response = createCustomerSubscription(createSubscriptionRequest);
		return response;
	}


	private String saveBraintreeCustomerId(final CustomerModel customer)
	{
		final SubscriptionResult result = createCustomerSubscription();
		customer.setBraintreeCustomerId(result.getMerchantTransactionCode());
		getModelService().save(customer);
		return result.getMerchantTransactionCode();
	}

	private CommandFactory getCommandFactory()
	{
		return commandFactoryRegistry.getFactory(BRAINTREE_PROVIDER_NAME);
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
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
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the brainTreePaymentInfoCreateStrategy
	 */
	public BrainTreePaymentInfoCreateStrategy getBrainTreePaymentInfoCreateStrategy()
	{
		return brainTreePaymentInfoCreateStrategy;
	}

	/**
	 * @param brainTreePaymentInfoCreateStrategy
	 *           the brainTreePaymentInfoCreateStrategy to set
	 */
	public void setBrainTreePaymentInfoCreateStrategy(final BrainTreePaymentInfoCreateStrategy brainTreePaymentInfoCreateStrategy)
	{
		this.brainTreePaymentInfoCreateStrategy = brainTreePaymentInfoCreateStrategy;
	}

	/**
	 * @return the addressConverter
	 */
	public Converter<AddressModel, AddressData> getAddressConverter()
	{
		return addressConverter;
	}

	/**
	 * @param addressConverter
	 *           the addressConverter to set
	 */
	public void setAddressConverter(final Converter<AddressModel, AddressData> addressConverter)
	{
		this.addressConverter = addressConverter;
	}

	/**
	 * Gets command factory registry.
	 *
	 * @return the commandFactoryRegistry
	 */
	public CommandFactoryRegistry getCommandFactoryRegistry()
	{
		return commandFactoryRegistry;
	}

	/**
	 * Sets command factory registry.
	 *
	 * @param commandFactoryRegistry
	 *           the commandFactoryRegistry to set
	 */
	public void setCommandFactoryRegistry(final CommandFactoryRegistry commandFactoryRegistry)
	{
		this.commandFactoryRegistry = commandFactoryRegistry;
	}

	/**
	 * @return the billingAddressConverter
	 */
	public BillingAddressConverter getBillingAddressConverter()
	{
		return billingAddressConverter;
	}

	/**
	 * @param billingAddressConverter
	 *           the billingAddressConverter to set
	 */
	public void setBillingAddressConverter(final BillingAddressConverter billingAddressConverter)
	{
		this.billingAddressConverter = billingAddressConverter;
	}

	/**
	 * @return the brainTreeCustomerAccountService
	 */
	public BrainTreeCustomerAccountService getBrainTreeCustomerAccountService()
	{
		return brainTreeCustomerAccountService;
	}

	/**
	 * @param brainTreeCustomerAccountService
	 *           the brainTreeCustomerAccountService to set
	 */
	public void setBrainTreeCustomerAccountService(final BrainTreeCustomerAccountService brainTreeCustomerAccountService)
	{
		this.brainTreeCustomerAccountService = brainTreeCustomerAccountService;
	}

	/**
	 * @return the checkoutCustomerStrategy
	 */
	public CheckoutCustomerStrategy getCheckoutCustomerStrategy()
	{
		return checkoutCustomerStrategy;
	}

	/**
	 * @param checkoutCustomerStrategy
	 *           the checkoutCustomerStrategy to set
	 */
	public void setCheckoutCustomerStrategy(final CheckoutCustomerStrategy checkoutCustomerStrategy)
	{
		this.checkoutCustomerStrategy = checkoutCustomerStrategy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.braintree.method.BrainTreePaymentService#deletePaymentMethod(com.braintree.command.request.
	 * BrainTreeDeletePaymentMethodRequest)
	 */
	@Override
	public BrainTreePaymentMethodResult deletePaymentMethod(final BrainTreeDeletePaymentMethodRequest request)
	{
		try
		{
			final BrainTreeDeletePaymentMethodCommand command = getCommandFactory()
					.createCommand(BrainTreeDeletePaymentMethodCommand.class);
			final BrainTreePaymentMethodResult result = command.perform(request);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to delete payment method: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
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

}
