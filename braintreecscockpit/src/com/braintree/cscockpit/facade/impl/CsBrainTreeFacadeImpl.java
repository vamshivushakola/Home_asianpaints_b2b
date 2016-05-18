package com.braintree.cscockpit.facade.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.commands.request.VoidRequest;
import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.payment.dto.CardInfo;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.cscockpit.facade.CsBrainTreeFacade;
import com.braintree.cscockpit.data.BrainTreeInfo;
import com.braintree.cscockpit.data.BrainTreePaymentMethodInfo;
import com.braintree.cscockpit.data.BraintreePaymentMethodData;
import com.braintree.cscockpit.converters.BrainTreeCustomerDetailsConverter;
import com.braintree.cscockpit.converters.BrainTreeDefaultResponseConverter;
import com.braintree.cscockpit.converters.BrainTreePaymentMethodConverter;
import com.braintree.cscockpit.converters.BrainTreePaymentMethodResponseConverter;
import com.braintree.cscockpit.converters.BrainTreeResponseConverter;
import com.braintree.cscockpit.converters.BrainTreeTransactionConverter;
import com.braintree.cscockpit.services.BrainTreeAddressService;
import com.braintree.cscockpit.services.CustomerSearchService;
import com.braintree.cscockpit.services.TransactionSearchService;
import com.braintree.command.request.BrainTreeCloneTransactionRequest;
import com.braintree.command.request.BrainTreeCreateCreditCardPaymentMethodRequest;
import com.braintree.command.request.BrainTreeCustomerRequest;
import com.braintree.command.request.BrainTreeDeletePaymentMethodRequest;
import com.braintree.command.request.BrainTreeFindMerchantAccountRequest;
import com.braintree.command.request.BrainTreeFindTransactionRequest;
import com.braintree.command.request.BrainTreeRefundTransactionRequest;
import com.braintree.command.request.BrainTreeSaleTransactionRequest;
import com.braintree.command.request.BrainTreeSubmitForSettlementTransactionRequest;
import com.braintree.command.request.BrainTreeUpdateCustomerRequest;
import com.braintree.command.request.BrainTreeUpdatePaymentMethodRequest;
import com.braintree.command.result.BrainTreeAddressResult;
import com.braintree.command.result.BrainTreeCloneTransactionResult;
import com.braintree.command.result.BrainTreeCustomerResult;
import com.braintree.command.result.BrainTreeFindCustomersResult;
import com.braintree.command.result.BrainTreeFindTransactionResult;
import com.braintree.command.result.BrainTreePaymentMethodResult;
import com.braintree.command.result.BrainTreeRefundTransactionResult;
import com.braintree.command.result.BrainTreeSaleTransactionResult;
import com.braintree.command.result.BrainTreeSubmitForSettlementTransactionResult;
import com.braintree.command.result.BrainTreeUpdateCustomerResult;
import com.braintree.command.result.BrainTreeUpdatePaymentMethodResult;
import com.braintree.command.result.BrainTreeVoidResult;
import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.hybris.data.BraintreeTransactionData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.model.BrainTreeTransactionDetailModel;
import com.braintree.payment.info.service.PaymentInfoService;
import com.braintree.transaction.service.BrainTreeTransactionService;
import com.braintreegateway.Customer;
import com.braintreegateway.PaymentMethodRequest;


public class CsBrainTreeFacadeImpl implements CsBrainTreeFacade
{
	private final static Logger LOG = Logger.getLogger(CsBrainTreeFacadeImpl.class);

	private BrainTreeAddressService brainTreeAddressService;
	private TransactionSearchService transactionSearchService;
	private UserService userService;
	private BrainTreeTransactionConverter brainTreeTransactionConverter;
	private BrainTreeResponseConverter brainTreeResponseConverter;
	private BrainTreeDefaultResponseConverter defaultResponseConverter;
	private BrainTreeCustomerDetailsConverter brainTreeCustomerDetailsConverter;
	private BrainTreePaymentMethodResponseConverter paymentMethodResponseConverter;
	private ModelService modelService;
	private BrainTreeTransactionService brainTreeTransactionService;
	private EnumerationService enumerationService;
	private BrainTreePaymentService brainTreePaymentService;
	private CustomerSearchService customerSearchService;
	private BrainTreeConfigService brainTreeConfigService;
	private PaymentInfoService paymentInfoService;
	private BrainTreePaymentMethodConverter paymentMethodConverter;

	@Override
	public List<BraintreeTransactionEntryData> findBrainTreeTransactions(final Calendar startDate, final Calendar endDate,
			final String customerId, final String customerEmail, final String transactionId, final String transactionStatus)
	{
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeFindTransactionRequest findTransactionRequest = new BrainTreeFindTransactionRequest(
					merchantTransactionCode);
			findTransactionRequest.setStartDate(startDate);
			findTransactionRequest.setEndDate(endDate);
			findTransactionRequest.setTransactionId(transactionId);
			findTransactionRequest.setTransactionStatus(transactionStatus);
			findTransactionRequest.setCustomerEmail(customerEmail);
			findTransactionRequest.setCustomerId(customerId);

			final BrainTreeFindTransactionResult transactions = getTransactionSearchService().findTransactions(
					findTransactionRequest);

			final BraintreeTransactionData convert = getBrainTreeTransactionConverter().convert(transactions);

			return convert.getTransactionEntries();
		}
		LOG.error("[BT Payment Service] Error user must be Customer type!");
		return Collections.emptyList();
	}

	@Override
	public BraintreeTransactionEntryData findBrainTreeTransaction(final String transactionId) throws AdapterException
	{
		final List<BraintreeTransactionEntryData> brainTreeTransactions = findBrainTreeTransactions(null, null, null, null,
				transactionId, null);
		if (CollectionUtils.isNotEmpty(brainTreeTransactions))
		{
			return brainTreeTransactions.get(0);
		}
		return null;
	}

	@Override
	public boolean voidTransaction(final PaymentTransactionModel transaction)
	{
		return voidTransaction(transaction, true);
	}

	@Override
	public boolean voidTransaction(final PaymentTransactionModel transaction, final boolean createCancelTransaction)
	{
		validateParameterNotNullStandardMessage("transaction", transaction);
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final VoidRequest voidRequest = new VoidRequest(merchantTransactionCode, transaction.getRequestId(), StringUtils.EMPTY,
					StringUtils.EMPTY);
			final BrainTreeVoidResult voidResult = getBrainTreePaymentService().voidTransaction(voidRequest);
			if (TransactionStatus.ACCEPTED.equals(voidResult.getTransactionStatus()))
			{
				if (createCancelTransaction)
				{
					getBrainTreeTransactionService().createCancelTransaction(transaction, voidResult);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public BrainTreeResponseResultData voidTransaction(final BrainTreeTransactionDetailModel transaction)
	{
		validateParameterNotNullStandardMessage("transaction", transaction);
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final VoidRequest voidRequest = new VoidRequest(merchantTransactionCode, transaction.getId(), StringUtils.EMPTY,
					StringUtils.EMPTY);
			final BrainTreeVoidResult voidResult = getBrainTreePaymentService().voidTransaction(voidRequest);
			if (TransactionStatus.ACCEPTED.equals(voidResult.getTransactionStatus()))
			{
				forceOrderCancellation(transaction, voidResult);
			}
			return getBrainTreeResponseConverter().convert(voidResult);
		}
		return new BrainTreeResponseResultData();
	}

	private void forceOrderCancellation(final BrainTreeTransactionDetailModel transaction, final BrainTreeVoidResult voidResult)
	{
		final OrderModel linkedOrder = transaction.getLinkedOrder();
		if (linkedOrder != null)
		{
			final List<PaymentTransactionModel> paymentTransactions = linkedOrder.getPaymentTransactions();
			if (paymentTransactions.size() != 0)
			{
				final PaymentTransactionModel txn = paymentTransactions.iterator().next();
				getBrainTreeTransactionService().createCancelTransaction(txn, voidResult);
			}
			linkedOrder.setStatus(OrderStatus.CANCELLED);
			getModelService().save(linkedOrder);
		}
	}

	@Override
	public BrainTreeResponseResultData cloneTransaction(final BrainTreeTransactionDetailModel currentTransaction,
			final String amount, final boolean submitForSettlement)
	{
		validateParameterNotNullStandardMessage("currentTransaction", currentTransaction);
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeCloneTransactionRequest request = new BrainTreeCloneTransactionRequest(merchantTransactionCode);
			request.setTransactionId(currentTransaction.getId());
			if (StringUtils.isNotBlank(amount))
			{
				request.setAmount(formedAmount(amount));
			}
			request.setSubmitForSettlement(Boolean.valueOf(submitForSettlement));
			final BrainTreeCloneTransactionResult result = getBrainTreePaymentService().cloneTransaction(request);
			return getBrainTreeResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	@Override
	public BrainTreeResponseResultData refundTransaction(final BrainTreeTransactionDetailModel currentTransaction,
			final String amount)
	{
		validateParameterNotNullStandardMessage("currentTransaction", currentTransaction);
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeRefundTransactionRequest request = new BrainTreeRefundTransactionRequest(merchantTransactionCode);
			request.setTransactionId(currentTransaction.getId());
			if (StringUtils.isNotBlank(amount))
			{
				request.setAmount(formedAmount(amount));
			}
			final BrainTreeRefundTransactionResult result = getBrainTreePaymentService().refundTransaction(request);
			if (TransactionStatus.ACCEPTED.equals(result.getTransactionStatus()))
			{
				createRefundTransaction(currentTransaction, result);
			}
			return getBrainTreeResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	private void createRefundTransaction(final BrainTreeTransactionDetailModel currentTransaction,
			final BrainTreeRefundTransactionResult result)
	{
		final OrderModel linkedOrder = currentTransaction.getLinkedOrder();
		if (linkedOrder != null)
		{
			final List<PaymentTransactionModel> paymentTransactions = linkedOrder.getPaymentTransactions();
			if (paymentTransactions.size() != 0)
			{
				final PaymentTransactionModel transaction = paymentTransactions.iterator().next();
				getBrainTreeTransactionService().createRefundTransaction(transaction, result);
			}
		}
	}

	@Override
	public BrainTreeResponseResultData refundTransaction(final String transactionId, final String amount)
	{
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeRefundTransactionRequest request = new BrainTreeRefundTransactionRequest(merchantTransactionCode);
			request.setTransactionId(transactionId);
			if (StringUtils.isNotBlank(amount))
			{
				request.setAmount(formedAmount(amount));
			}
			final BrainTreeRefundTransactionResult result = getBrainTreePaymentService().refundTransaction(request);

			return getBrainTreeResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	private BigDecimal formedAmount(final String amount)
	{
		return new BigDecimal(amount);
	}

	private String getMerchantCode()
	{
		final UserModel user = getUserService().getCurrentUser();
		return user.getUid();
	}

	@Override
	public BrainTreeUpdateCustomerResult updateCustomer(final String id, final String customerId, final String firstName,
			final String lastName, final String email, final String phone, final String fax, final String website,
			final String company)
	{
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeUpdateCustomerRequest request = new BrainTreeUpdateCustomerRequest(merchantTransactionCode);
			request.setId(customerId);
			request.setCompany(company);
			request.setEmail(email);
			request.setFax(fax);
			request.setFirstName(firstName);
			request.setLastName(lastName);
			request.setPhone(phone);
			request.setWebsite(website);

			return getBrainTreePaymentService().updateCustomer(request);
		}
		return null;
	}

	@Override
	public BrainTreeResponseResultData createTransaction(final BrainTreeInfo brainTreeInfo)

	{
		validateParameterNotNullStandardMessage("brainTreeInfo", brainTreeInfo);
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeSaleTransactionRequest request = new BrainTreeSaleTransactionRequest(merchantTransactionCode,
					createCard(brainTreeInfo), brainTreeInfo.getCurrency(), createAmount(brainTreeInfo.getAmount()),
					createShippingInfo(brainTreeInfo));

			request.setUsePaymentMethodToken(Boolean.valueOf(brainTreeInfo.isPaymentMethodToken()));
			request.setPaymentMethodToken(brainTreeInfo.getPaymentMethodToken());
			request.setTaxAmount(createAmount(brainTreeInfo.getTax()));
			request.setCustomFields(brainTreeInfo.getCustom());
			request.setCustomerEmail(brainTreeInfo.getEmail());
			request.setCustomerFirstName(brainTreeInfo.getFirstName());
			request.setCustomerLastName(brainTreeInfo.getLastName());
			request.setStoreInVault(brainTreeInfo.getStoreInVault());

			request.setMerchantAccountIdForCurrentSite(getMerchantAccountId());
			final BrainTreeSaleTransactionResult result = getBrainTreePaymentService().saleTransaction(request);
			return getBrainTreeResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	@Override
	public BrainTreeResponseResultData removeCustomer(final String customerId)
	{
		final String merchantId = getMerchantCode();
		if (merchantId != null)
		{
			validateParameterNotNullStandardMessage("customerId", customerId);
			final BrainTreeCustomerRequest brainTreeCustomerRequest = new BrainTreeCustomerRequest(merchantId);
			brainTreeCustomerRequest.setCustomerId(customerId);

			final BrainTreeCustomerResult result = getBrainTreePaymentService().removeCustomer(brainTreeCustomerRequest);
			return getDefaultResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	@Override
	public BraintreePaymentMethodData updatePaymentMethod(final String originalToken,
			final BrainTreePaymentMethodInfo brainTreeInfo)
	{
		final String merchantId = getMerchantCode();
		if (merchantId != null)
		{
			final BrainTreeUpdatePaymentMethodRequest request = new BrainTreeUpdatePaymentMethodRequest(merchantId);

			request.setToken(brainTreeInfo.getPaymentMethodToken());
			request.setCreditCardNumber(brainTreeInfo.getCardNumber());
			request.setCardholderName(brainTreeInfo.getCardHolder());
			request.setCardExpirationDate(brainTreeInfo.getExpirationDate());
			request.setDefault(brainTreeInfo.isDefaultPaymentMethod());
			request.setNewToken(brainTreeInfo.getNewPaymentMethodToken());
			request.setBillingAddressId(brainTreeInfo.getBillingAddress());

			final BrainTreeUpdatePaymentMethodResult result = getBrainTreePaymentService().updatePaymentMethod(request);
			if (result.isSuccess() && result.getPaymentMethod() != null)
			{
				final BrainTreePaymentInfoModel paymentInfo = getPaymentMethodConverter().convert(result.getPaymentMethod());
				if (paymentInfo != null)
				{
					getPaymentInfoService().update(originalToken, paymentInfo);
				}
			}

			return getPaymentMethodResponseConverter().convert(result);
		}
		return new BraintreePaymentMethodData();
	}

	@Override
	public BrainTreeResponseResultData submitForSettlementTransaction(final BrainTreeTransactionDetailModel transactionId,
			final String amount)
	{
		final String merchantTransactionCode = getMerchantCode();
		if (merchantTransactionCode != null)
		{
			final BrainTreeSubmitForSettlementTransactionRequest request = new BrainTreeSubmitForSettlementTransactionRequest(
					merchantTransactionCode);
			request.setTransactionId(transactionId.getId());
			if (StringUtils.isNotBlank(amount))
			{
				request.setAmount(formedAmount(amount));
			}
			final BrainTreeSubmitForSettlementTransactionResult result = getBrainTreePaymentService()
					.submitForSettlementTransaction(request);

			return getBrainTreeResponseConverter().convert(result);
		}
		return new BrainTreeResponseResultData();
	}

	@Override
	public String generateClientToken()
	{
		return getBrainTreePaymentService().generateClientToken();
	}

	private String getMerchantAccountId()
	{
		final String merchantAccountId = getBrainTreeConfigService().getMerchantAccountIdForCurrentSiteAndCurrency();
		if (StringUtils.isNotEmpty(merchantAccountId))
		{
			final BrainTreeFindMerchantAccountRequest brainTreeFindMerchantAccountRequest = new BrainTreeFindMerchantAccountRequest(
					StringUtils.EMPTY);
			brainTreeFindMerchantAccountRequest.setMerchantAccount(merchantAccountId);
			final boolean isMerchantAccountExist = getBrainTreePaymentService().findMerchantAccount(
					brainTreeFindMerchantAccountRequest).isMerchantAccountExist();
			if (isMerchantAccountExist)
			{
				return merchantAccountId;
			}
		}
		return null;
	}

	//TODO refactoring
	private BigDecimal createAmount(final String amount)
	{
		if (StringUtils.isNotBlank(amount))
		{
			return new BigDecimal(amount);
		}
		return null;
	}

	//TODO refactoring
	private CardInfo createCard(final BrainTreeInfo transactionInfo)

	{
		final CardInfo cardInfo = new CardInfo();
		cardInfo.setCardHolderFullName(transactionInfo.getCardHolder());
		cardInfo.setCardNumber(transactionInfo.getCardNumber());
		cardInfo.setCv2Number(transactionInfo.getCvv());
		if (transactionInfo.getExpirationDate() != null)
		{
			final String[] mmyy = transactionInfo.getExpirationDate().split("/");
			if (mmyy.length == 2)
			{
				cardInfo.setExpirationMonth(Integer.valueOf(mmyy[0]));
				cardInfo.setExpirationYear(Integer.valueOf(mmyy[1]));
			}
		}
		final BillingInfo billingInfo = new BillingInfo();
		billingInfo.setFirstName(transactionInfo.getFirstName());
		billingInfo.setLastName(transactionInfo.getLastName());
		billingInfo.setEmail(transactionInfo.getEmail());
		billingInfo.setPostalCode(transactionInfo.getBillingPostCode());
		billingInfo.setStreet1(transactionInfo.getBillingAddress());
		cardInfo.setBillingInfo(billingInfo);
		return cardInfo;
	}

	//TODO refactoring
	private BillingInfo createShippingInfo(final BrainTreeInfo transactionInfo)
	{
		final BillingInfo shippingInfo = new BillingInfo();
		shippingInfo.setFirstName(transactionInfo.getFirstName());
		shippingInfo.setLastName(transactionInfo.getLastName());
		shippingInfo.setEmail(transactionInfo.getEmail());
		shippingInfo.setPostalCode(transactionInfo.getShippingPostCode());
		shippingInfo.setStreet1(transactionInfo.getShippingAddress());
		return shippingInfo;
	}

	@Override
	public List<Customer> findCustomers(final String customer, final String customerEmail) throws AdapterException
	{
		final UserModel user = getUserService().getCurrentUser();
		final String braintreeCustomerId = user.getUid();
		if (braintreeCustomerId != null)
		{
			final BrainTreeCustomerRequest findCustomerRequest = new BrainTreeCustomerRequest(braintreeCustomerId);
			findCustomerRequest.setCustomerEmail(customerEmail);
			findCustomerRequest.setCustomerId(customer);

			final BrainTreeFindCustomersResult customers = getCustomerSearchService().findCustomers(findCustomerRequest);
			final List<Customer> customerList = new ArrayList<Customer>(customers.getCustomers().getMaximumSize());
			final Iterator<Customer> cit = customers.getCustomers().iterator();
			while (cit.hasNext())
			{
				customerList.add(cit.next());
			}
			return customerList;
		}
		LOG.error("[BT Payment Service] Error user must be Customer type!");
		return Collections.emptyList();
	}

	@Override
	public Customer findCustomer(final String customerId) throws AdapterException
	{
		final UserModel user = getUserService().getCurrentUser();
		final String braintreeCustomerId = user.getUid();
		if (braintreeCustomerId != null)
		{
			final BrainTreeCustomerRequest findCustomerRequest = new BrainTreeCustomerRequest(braintreeCustomerId);
			findCustomerRequest.setCustomerId(customerId);

			final BrainTreeFindCustomersResult customers = getCustomerSearchService().findCustomers(findCustomerRequest);
			if (customers.getCustomers() != null)
			{
				if (customers.getCustomers().iterator().hasNext())
				{
					return customers.getCustomers().getFirst();
				}
			}
		}
		LOG.error("[BT Payment Service] Error user must be Customer type!");
		return null;
	}

	@Override
	public BrainTreePaymentMethodResult createCreditCardPaymentMethod(final String customerId, final String paymentMethodNonce,
			final boolean isDefault, final String billingAddressId)
	{
		final UserModel user = getUserService().getCurrentUser();
		final String braintreeCustomerId = user.getUid();
		if (braintreeCustomerId != null)
		{
			final BrainTreeCreateCreditCardPaymentMethodRequest request = new BrainTreeCreateCreditCardPaymentMethodRequest(
					braintreeCustomerId);
			final PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();

			paymentMethodRequest.paymentMethodNonce(paymentMethodNonce).customerId(customerId);
			paymentMethodRequest.options().makeDefault(Boolean.valueOf(isDefault)).done();

			if (StringUtils.isNotBlank(billingAddressId))
			{
				paymentMethodRequest.billingAddressId(billingAddressId);
			}
			request.setRequest(paymentMethodRequest);
			final BrainTreePaymentMethodResult result = getBrainTreePaymentService().createCreditCardPaymentMethod(request);
			if (result.isSuccess() && result.getPaymentMethod() != null)
			{
				final BrainTreePaymentInfoModel paymentInfo = getPaymentMethodConverter().convert(result.getPaymentMethod());
				if (paymentInfo != null)
				{
					getPaymentInfoService().addToCustomer(paymentInfo);
				}
			}
			return result;
		}
		return null;
	}


	@Override
	public BrainTreePaymentMethodResult deletePaymentMethod(final BrainTreePaymentInfoModel paymentInfo)
	{
		final UserModel user = getUserService().getCurrentUser();
		final String braintreeCustomerId = user.getUid();
		if (braintreeCustomerId != null)
		{
			final BrainTreeDeletePaymentMethodRequest request = new BrainTreeDeletePaymentMethodRequest(braintreeCustomerId,
					paymentInfo.getPaymentMethodToken());
			final BrainTreePaymentMethodResult result = getBrainTreePaymentService().deletePaymentMethod(request);
			if (result.isSuccess())
			{
				getPaymentInfoService().remove(paymentInfo.getCustomerId(), paymentInfo.getPaymentMethodToken());
			}
			return result;
		}
		return null;
	}

	@Override
	public BrainTreeAddressResult addAddress(final String customerId, final String firstName, final String lastName,
			final String company, final String streetAddress, final String extendedAddress, final String cityLocality,
			final String stateProvinceRegion, final String postalCode, final String countryIsoCode)
	{
		return getBrainTreeAddressService().addAddress(customerId, firstName, lastName, company, streetAddress, extendedAddress,
				cityLocality, stateProvinceRegion, postalCode, countryIsoCode);
	}

	@Override
	public BrainTreeAddressResult updateAddress(final String customerId, final String addressId, final String firstName,
			final String lastName, final String company, final String streetAddress, final String extendedAddress,
			final String cityLocality, final String stateProvinceRegion, final String postalCode, final String countryName)
	{
		return getBrainTreeAddressService().updateAddress(customerId, addressId, firstName, lastName, company, streetAddress,
				extendedAddress, cityLocality, stateProvinceRegion, postalCode, countryName);
	}

	@Override
	public BrainTreeAddressResult removeAddress(final String addressID, final String customerId)
	{
		return getBrainTreeAddressService().removeAddress(addressID, customerId);
	}

	public BrainTreeAddressService getBrainTreeAddressService()
	{
		return brainTreeAddressService;
	}

	public void setBrainTreeAddressService(final BrainTreeAddressService brainTreeAddressService)
	{
		this.brainTreeAddressService = brainTreeAddressService;
	}

	public TransactionSearchService getTransactionSearchService()
	{
		return transactionSearchService;
	}

	public void setTransactionSearchService(final TransactionSearchService transactionSearchService)
	{
		this.transactionSearchService = transactionSearchService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public BrainTreeTransactionConverter getBrainTreeTransactionConverter()
	{
		return brainTreeTransactionConverter;
	}

	public void setBrainTreeTransactionConverter(final BrainTreeTransactionConverter brainTreeTransactionConverter)
	{
		this.brainTreeTransactionConverter = brainTreeTransactionConverter;
	}

	public BrainTreeResponseConverter getBrainTreeResponseConverter()
	{
		return brainTreeResponseConverter;
	}

	public void setBrainTreeResponseConverter(final BrainTreeResponseConverter brainTreeResponseConverter)
	{
		this.brainTreeResponseConverter = brainTreeResponseConverter;
	}

	public BrainTreeDefaultResponseConverter getDefaultResponseConverter()
	{
		return defaultResponseConverter;
	}

	public void setDefaultResponseConverter(final BrainTreeDefaultResponseConverter defaultResponseConverter)
	{
		this.defaultResponseConverter = defaultResponseConverter;
	}

	public BrainTreeCustomerDetailsConverter getBrainTreeCustomerDetailsConverter()
	{
		return brainTreeCustomerDetailsConverter;
	}

	public void setBrainTreeCustomerDetailsConverter(final BrainTreeCustomerDetailsConverter brainTreeCustomerDetailsConverter)
	{
		this.brainTreeCustomerDetailsConverter = brainTreeCustomerDetailsConverter;
	}

	public BrainTreePaymentMethodResponseConverter getPaymentMethodResponseConverter()
	{
		return paymentMethodResponseConverter;
	}

	public void setPaymentMethodResponseConverter(final BrainTreePaymentMethodResponseConverter paymentMethodResponseConverter)
	{
		this.paymentMethodResponseConverter = paymentMethodResponseConverter;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public BrainTreeTransactionService getBrainTreeTransactionService()
	{
		return brainTreeTransactionService;
	}

	public void setBrainTreeTransactionService(final BrainTreeTransactionService brainTreeTransactionService)
	{
		this.brainTreeTransactionService = brainTreeTransactionService;
	}

	public EnumerationService getEnumerationService()
	{
		return enumerationService;
	}

	public void setEnumerationService(final EnumerationService enumerationService)
	{
		this.enumerationService = enumerationService;
	}

	public BrainTreePaymentService getBrainTreePaymentService()
	{
		return brainTreePaymentService;
	}

	public void setBrainTreePaymentService(final BrainTreePaymentService brainTreePaymentService)
	{
		this.brainTreePaymentService = brainTreePaymentService;
	}

	public CustomerSearchService getCustomerSearchService()
	{
		return customerSearchService;
	}

	public void setCustomerSearchService(final CustomerSearchService customerSearchService)
	{
		this.customerSearchService = customerSearchService;
	}

	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}

	public PaymentInfoService getPaymentInfoService()
	{
		return paymentInfoService;
	}

	public void setPaymentInfoService(final PaymentInfoService paymentInfoService)
	{
		this.paymentInfoService = paymentInfoService;
	}

	public BrainTreePaymentMethodConverter getPaymentMethodConverter()
	{
		return paymentMethodConverter;
	}

	public void setPaymentMethodConverter(final BrainTreePaymentMethodConverter paymentMethodConverter)
	{
		this.paymentMethodConverter = paymentMethodConverter;
	}
}
