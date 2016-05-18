/**
 *
 */
package com.braintree.method;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.commands.request.AuthorizationRequest;
import de.hybris.platform.payment.commands.request.CreateSubscriptionRequest;
import de.hybris.platform.payment.commands.request.VoidRequest;
import de.hybris.platform.payment.commands.result.AuthorizationResult;
import de.hybris.platform.payment.commands.result.SubscriptionResult;
import de.hybris.platform.payment.methods.PaymentMethod;

import com.braintree.command.request.BrainTreeAddressRequest;
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
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.model.BrainTreePaymentInfoModel;


public interface BrainTreePaymentService extends PaymentMethod
{

	public SubscriptionResult createCustomerSubscription(final CreateSubscriptionRequest subscriptionRequest)
			throws AdapterException;

	public BrainTreeGenerateClientTokenResult generateClientToken(final BrainTreeGenerateClientTokenRequest clientTokenRequest)
			throws AdapterException;

	public BrainTreeFindCustomerResult findCustomer(final BrainTreeCustomerRequest findCustomerRequest) throws AdapterException;

	public String generateClientToken();

	public void completeCreateSubscription(final BrainTreeSubscriptionInfoData brainTreeSubscriptionInfoData,
			final CustomerModel customer);

	public AuthorizationResult authorize(AuthorizationRequest authorizationRequest);

	/**
	 * void transaction on braintree side
	 *
	 * @param voidRequest
	 *           request
	 * @return result of void operation
	 */
	BrainTreeVoidResult voidTransaction(VoidRequest voidRequest);

	/**
	 * clone transaction on braintree side
	 *
	 * @param request
	 *           simple request with transaction id, amount and submitForSettlement sign
	 * @return result of clone operation
	 */
	BrainTreeCloneTransactionResult cloneTransaction(BrainTreeCloneTransactionRequest request);

	/**
	 * @param customer
	 * @param paymentInfoId
	 * @return
	 */
	BrainTreePaymentInfoModel completeCreateSubscription(CustomerModel customer, String paymentInfoId);

	/**
	 * @param request
	 * @return
	 */
	BrainTreeCreatePaymentMethodResult createPaymentMethod(BrainTreeCreatePaymentMethodRequest request);

	/**
	 * @param request
	 * @return
	 */
	BrainTreePaymentMethodResult createCreditCardPaymentMethod(BrainTreeCreateCreditCardPaymentMethodRequest request);


	/**
	 * refund transaction with specific amount
	 *
	 * @param request
	 *           wih transaction id and amount
	 * @return result of refund operation
	 */
	BrainTreeRefundTransactionResult refundTransaction(BrainTreeRefundTransactionRequest request);

	/**
	 * update braintree customer
	 *
	 * @param request
	 *           wih updated customer fields
	 * @return result of update operation
	 */
	BrainTreeUpdateCustomerResult updateCustomer(BrainTreeUpdateCustomerRequest request);

	/**
	 * @param brainTreeFindMerchantAccountRequest
	 *           BrainTreeFindMerchantAccountRequest
	 * @return BrainTreeFindMerchantAccountResult
	 */
	BrainTreeFindMerchantAccountResult findMerchantAccount(
			final BrainTreeFindMerchantAccountRequest brainTreeFindMerchantAccountRequest);

	/**
	 * create new transaction
	 *
	 * @param request
	 *           BrainTreeSaleTransactionRequest
	 * @return BrainTreeSaleTransactionResult
	 */
	BrainTreeSaleTransactionResult saleTransaction(BrainTreeSaleTransactionRequest request);

	BrainTreePaymentMethodResult deletePaymentMethod(BrainTreeDeletePaymentMethodRequest request);

	/**
	 * remove customer from vault by customer id
	 *
	 * @param request
	 *           BrainTreeCustomerRequest
	 * @return BrainTreeCustomerResult
	 */
	BrainTreeCustomerResult removeCustomer(BrainTreeCustomerRequest request);

	/**
	 * update payment method
	 *
	 * @param request
	 *           BrainTreeUpdatePaymentMethodRequest
	 * @return BrainTreeUpdatePaymentMethodResult
	 */
	BrainTreeUpdatePaymentMethodResult updatePaymentMethod(BrainTreeUpdatePaymentMethodRequest request);


	/**
	 * Submit For Settlement transaction with specific amount
	 *
	 * @param request
	 *           wih transaction id and amount
	 * @return result of submit operation
	 */
	BrainTreeSubmitForSettlementTransactionResult submitForSettlementTransaction(
			BrainTreeSubmitForSettlementTransactionRequest request);

	/**
	 * @param addressRequest
	 * @return
	 */
	BrainTreeAddressResult createAddress(BrainTreeAddressRequest addressRequest);

	/**
	 * @param addressRequest
	 */
	BrainTreeAddressResult updateAddress(BrainTreeAddressRequest addressRequest);

	/**
	 * @param addressRequest
	 */
	BrainTreeAddressResult removeAddress(BrainTreeAddressRequest addressRequest);
}
