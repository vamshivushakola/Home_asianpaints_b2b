/**
 *
 */
package com.braintree.transaction.service.impl;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_PROVIDER_NAME;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.PaymentService;
import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;
import de.hybris.platform.payment.enums.PaymentTransactionType;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.command.request.BrainTreeAuthorizationRequest;
import com.braintree.command.request.BrainTreeCreatePaymentMethodRequest;
import com.braintree.command.request.BrainTreeFindMerchantAccountRequest;
import com.braintree.command.result.BrainTreeAuthorizationResult;
import com.braintree.command.result.BrainTreeCreatePaymentMethodResult;
import com.braintree.command.result.BrainTreeRefundTransactionResult;
import com.braintree.command.result.BrainTreeVoidResult;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.paypal.converters.impl.BillingAddressConverter;
import com.braintree.transaction.service.BrainTreeTransactionService;
import com.google.common.collect.Lists;


public class BrainTreeTransactionServiceImpl implements BrainTreeTransactionService
{
	private static final Logger LOG = Logger.getLogger(BrainTreeTransactionServiceImpl.class);
	private final static int DEFAULT_CURRENCY_DIGIT = 2;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "brainTreePaymentService")
	private BrainTreePaymentService brainTreePaymentService;

	@Resource(name = "paymentService")
	private PaymentService paymentService;

	@Resource(name = "billingAddressConverter")
	private BillingAddressConverter billingAddressConverter;

	@Resource(name = "checkoutCustomerStrategy")
	private CheckoutCustomerStrategy checkoutCustomerStrategy;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Override
	public boolean createAuthorizationTransaction()
	{
		final CartModel cart = cartService.getSessionCart();
		final BrainTreeAuthorizationResult result = brainTreeAuthorise(cart);
		PaymentTransactionEntryModel paymentTransactionEntry = null;

		if (result.isSuccess())
		{
			paymentTransactionEntry = createTransactionEntry(PaymentTransactionType.AUTHORIZATION, cart, result);
			savePaymentTransaction(paymentTransactionEntry);
			LOG.info("[BT AUTHTORIZE] Transaction with code : " + paymentTransactionEntry.getCode() + " was created with status "
					+ TransactionStatusDetails.SUCCESFULL.name());
			return true;
		}
		else
		{
			LOG.error("[BT AUTHTORIZE] Failed!");
			return false;
		}

	}

	private BrainTreeAuthorizationResult brainTreeAuthorise(final CartModel cart)
	{
		final PaymentInfoModel paymentInfo = cart.getPaymentInfo();
		final String braintreeCustomerId = checkoutCustomerStrategy.getCurrentUserForCheckout().getBraintreeCustomerId();
		validateParameterNotNullStandardMessage("paymentInfo", paymentInfo);
		String methodNonce = null;
		String deviceData = null;
		String paymentType = null;
		Boolean liabilityShifted = null;
		Boolean usePaymentMethodToken = null;
		String paymentMethodToken = null;
		Boolean threeDSecureConfiguration = null;
		Boolean advancedFraudTools = null;
		Boolean isSkip3dSecureLiabilityResult = null;
		String creditCardStatementName = null;
		String merchantAccountIdForCurrentSite = null;
		String brainTreeChannel = null;
		boolean storeInVault = false;

		if (paymentInfo instanceof BrainTreePaymentInfoModel)
		{
			methodNonce = ((BrainTreePaymentInfoModel) paymentInfo).getNonce();
			deviceData = ((BrainTreePaymentInfoModel) paymentInfo).getDeviceData();
			paymentType = ((BrainTreePaymentInfoModel) paymentInfo).getPaymentProvider();
			usePaymentMethodToken = ((BrainTreePaymentInfoModel) paymentInfo).getUsePaymentMethodToken();
			paymentMethodToken = ((BrainTreePaymentInfoModel) paymentInfo).getPaymentMethodToken();
			storeInVault = ((BrainTreePaymentInfoModel) paymentInfo).isSaved();

			if (((BrainTreePaymentInfoModel) paymentInfo).getLiabilityShifted() != null)
			{
				liabilityShifted = ((BrainTreePaymentInfoModel) paymentInfo).getLiabilityShifted();
			}
			threeDSecureConfiguration = ((BrainTreePaymentInfoModel) paymentInfo).getThreeDSecureConfiguration();
			advancedFraudTools = ((BrainTreePaymentInfoModel) paymentInfo).getAdvancedFraudTools();
			isSkip3dSecureLiabilityResult = ((BrainTreePaymentInfoModel) paymentInfo).getIsSkip3dSecureLiabilityResult();
			creditCardStatementName = ((BrainTreePaymentInfoModel) paymentInfo).getCreditCardStatementName();
			merchantAccountIdForCurrentSite = ((BrainTreePaymentInfoModel) paymentInfo).getMerchantAccountIdForCurrentSite();
			brainTreeChannel = ((BrainTreePaymentInfoModel) paymentInfo).getBrainTreeChannel();
		}

		final AddressModel shippingAddress;
		if (cart.getPaymentInfo().getBillingAddress() != null
				&& StringUtils.isNotEmpty(cart.getPaymentInfo().getBillingAddress().getBrainTreeAddressId()))
		{
			shippingAddress = cart.getPaymentInfo().getBillingAddress();
		}
		else
		{
			shippingAddress = cart.getDeliveryAddress();
		}
		final BillingInfo shippingInfo = billingAddressConverter.convert(shippingAddress);

		final BrainTreeAuthorizationRequest authorizationRequest = new BrainTreeAuthorizationRequest(paymentInfo.getCode(), null,
				getCurrencyInstanceByCart(cart), BigDecimal.valueOf(cart.getTotalPrice().doubleValue()), shippingInfo);
		authorizationRequest.setMethodNonce(methodNonce);
		authorizationRequest.setDeviceData(deviceData);
		authorizationRequest.setPaymentType(paymentType);
		authorizationRequest.setUsePaymentMethodToken(usePaymentMethodToken);
		authorizationRequest.setPaymentMethodToken(paymentMethodToken);
		authorizationRequest.setLiabilityShifted(liabilityShifted);
		authorizationRequest.setCustomerId(braintreeCustomerId);
		authorizationRequest.setThreeDSecureConfiguration(threeDSecureConfiguration);
		authorizationRequest.setAdvancedFraudTools(advancedFraudTools);
		authorizationRequest.setIsSkip3dSecureLiabilityResult(isSkip3dSecureLiabilityResult);
		authorizationRequest.setCreditCardStatementName(creditCardStatementName);
		authorizationRequest.setBrainTreeAddressId(shippingAddress.getBrainTreeAddressId());
		if (StringUtils.isNotEmpty(merchantAccountIdForCurrentSite))
		{
			final BrainTreeFindMerchantAccountRequest brainTreeFindMerchantAccountRequest = new BrainTreeFindMerchantAccountRequest(
					StringUtils.EMPTY);
			brainTreeFindMerchantAccountRequest.setMerchantAccount(merchantAccountIdForCurrentSite);
			if (brainTreePaymentService.findMerchantAccount(brainTreeFindMerchantAccountRequest).isMerchantAccountExist())
			{
				authorizationRequest.setMerchantAccountIdForCurrentSite(merchantAccountIdForCurrentSite);
			}
		}
		authorizationRequest.setBrainTreeChannel(brainTreeChannel);
		authorizationRequest.setStoreInVault(storeInVault);

		return (BrainTreeAuthorizationResult) brainTreePaymentService.authorize(authorizationRequest);
	}

	@Override
	public boolean createPaymentMethodTokenForOrderReplenishment()
	{
		final CustomerModel customer = checkoutCustomerStrategy.getCurrentUserForCheckout();
		final PaymentInfoModel paymentInfo = cartService.getSessionCart().getPaymentInfo();
		if (paymentInfo instanceof BrainTreePaymentInfoModel)
		{
			if (!((BrainTreePaymentInfoModel) paymentInfo).getUsePaymentMethodToken().booleanValue())
			{
				final BrainTreeCreatePaymentMethodRequest request = new BrainTreeCreatePaymentMethodRequest(null,
						((BrainTreePaymentInfoModel) paymentInfo).getNonce(), customer.getBraintreeCustomerId());

				final BrainTreeCreatePaymentMethodResult result = brainTreePaymentService.createPaymentMethod(request);
				if (result != null)
				{
					((BrainTreePaymentInfoModel) paymentInfo).setPaymentMethodToken(result.getPaymentMethodToken());
					((BrainTreePaymentInfoModel) paymentInfo).setUsePaymentMethodToken(Boolean.TRUE);
					modelService.save(paymentInfo);
				}

			}
		}
		else
		{
			throw new AdapterException("Error during creation payment method for replenishment.");
		}

		return createAuthorizationTransaction();

	}

	@Override
	public PaymentTransactionEntryModel createAuthorizationTransaction(final CartModel cart)
	{
		final BrainTreeAuthorizationResult result = brainTreeAuthorise(cart);

		final PaymentTransactionEntryModel transactionEntry = createTransactionEntry(PaymentTransactionType.AUTHORIZATION, cart,
				result);

		if (!result.isSuccess())
		{
			transactionEntry.setTransactionStatus(TransactionStatus.REJECTED.name());
			transactionEntry.setTransactionStatusDetails(TransactionStatusDetails.BANK_DECLINE.name());
		}
		savePaymentTransaction(transactionEntry, cart);
		return transactionEntry;
	}

	@Override
	public PaymentTransactionEntryModel createCancelTransaction(final PaymentTransactionModel transaction,
			final BrainTreeVoidResult voidResult)
	{
		final PaymentTransactionType transactionType = PaymentTransactionType.CANCEL;
		final String newEntryCode = paymentService.getNewPaymentTransactionEntryCode(transaction, transactionType);

		final PaymentTransactionEntryModel entry = modelService.create(PaymentTransactionEntryModel.class);
		entry.setType(transactionType);
		entry.setCode(newEntryCode);
		entry.setRequestId(voidResult.getRequestId());
		entry.setPaymentTransaction(transaction);
		entry.setCurrency(transaction.getCurrency());
		entry.setAmount(formatAmount(voidResult.getAmount()));
		entry.setTransactionStatus(voidResult.getTransactionStatus().toString());
		entry.setTransactionStatusDetails(voidResult.getTransactionStatusDetails().toString());
		entry.setTime(new Date());
		modelService.save(entry);

		return entry;
	}

	@Override
	public PaymentTransactionEntryModel createRefundTransaction(final PaymentTransactionModel transaction,
			final BrainTreeRefundTransactionResult result)
	{
		final PaymentTransactionType transactionType = PaymentTransactionType.REFUND_STANDALONE;
		final String newEntryCode = paymentService.getNewPaymentTransactionEntryCode(transaction, transactionType);

		final PaymentTransactionEntryModel entry = modelService.create(PaymentTransactionEntryModel.class);
		entry.setType(transactionType);
		entry.setCode(newEntryCode);
		entry.setRequestId(result.getTransactionId());
		entry.setPaymentTransaction(transaction);
		entry.setCurrency(transaction.getCurrency());
		entry.setAmount(formatAmount(result.getAmount()));
		entry.setTransactionStatus(result.getTransactionStatus().toString());
		entry.setTransactionStatusDetails(result.getTransactionStatusDetails().toString());
		entry.setTime(new Date());
		modelService.save(entry);
		return entry;
	}

	protected Currency getCurrencyInstanceByCart(final CartModel cart)
	{
		return Currency.getInstance(cart.getCurrency().getIsocode());
	}

	private BigDecimal formatAmount(final BigDecimal amount)
	{
		return amount.setScale(getCurrencyDigit(), RoundingMode.HALF_EVEN);
	}

	private void savePaymentTransaction(final PaymentTransactionEntryModel paymentTransactionEntry)
	{
		final CartModel cart = cartService.getSessionCart();
		savePaymentTransaction(paymentTransactionEntry, cart);
	}

	private void savePaymentTransaction(final PaymentTransactionEntryModel paymentTransactionEntry, final CartModel cart)
	{

		final List<PaymentTransactionEntryModel> paymentTransactionEntries = Lists.newArrayList();

		paymentTransactionEntries.add(paymentTransactionEntry);

		final List<PaymentTransactionModel> paymentTransactions = Lists.newArrayList();

		final PaymentTransactionModel paymentTransaction = modelService.create(PaymentTransactionModel.class);
		paymentTransaction.setRequestId(paymentTransactionEntry.getRequestId());
		paymentTransaction.setEntries(paymentTransactionEntries);
		paymentTransaction.setRequestToken(((BrainTreePaymentInfoModel) cart.getPaymentInfo()).getNonce());
		paymentTransaction.setPaymentProvider(BRAINTREE_PROVIDER_NAME);

		paymentTransactions.add(paymentTransaction);

		cart.setPaymentTransactions(paymentTransactions);
		modelService.saveAll(cart);

	}


	private PaymentTransactionEntryModel createTransactionEntry(final PaymentTransactionType type, final CartModel cart,
			final BrainTreeAuthorizationResult result)
	{
		final PaymentTransactionEntryModel paymentTransactionEntry = modelService.create(PaymentTransactionEntryModel.class);

		final PaymentInfoModel paymentInfo = cart.getPaymentInfo();
		if (paymentInfo instanceof BrainTreePaymentInfoModel)
		{
			paymentTransactionEntry.setRequestToken(((BrainTreePaymentInfoModel) paymentInfo).getNonce());
		}

		paymentTransactionEntry.setType(type);
		paymentTransactionEntry.setTransactionStatus(result.getTransactionStatus().toString());
		paymentTransactionEntry.setTransactionStatusDetails(result.getTransactionStatusDetails().toString());
		paymentTransactionEntry.setRequestId(result.getRequestId());
		paymentTransactionEntry.setCurrency(cart.getCurrency());


		final String code = BRAINTREE_PROVIDER_NAME + "_cart_" + cart.getCode() + "_stamp_" + System.currentTimeMillis();
		paymentTransactionEntry.setCode(code);

		final BigDecimal transactionAmount = BigDecimal.valueOf(cart.getTotalPrice().doubleValue());
		paymentTransactionEntry.setAmount(transactionAmount);

		paymentTransactionEntry.setTime(cart.getPaymentInfo().getCreationtime());

		return paymentTransactionEntry;
	}

	private int getCurrencyDigit()
	{
		final CurrencyModel currency = commonI18NService.getCurrentCurrency();
		if (currency != null)
		{
			final Integer digits = currency.getDigits();
			return digits != null ? digits.intValue() : DEFAULT_CURRENCY_DIGIT;
		}
		return DEFAULT_CURRENCY_DIGIT;
	}

}