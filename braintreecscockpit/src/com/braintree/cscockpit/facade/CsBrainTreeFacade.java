package com.braintree.cscockpit.facade;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.model.PaymentTransactionModel;

import java.util.Calendar;
import java.util.List;

import com.braintree.cscockpit.data.BrainTreeInfo;
import com.braintree.cscockpit.data.BrainTreePaymentMethodInfo;
import com.braintree.cscockpit.data.BraintreePaymentMethodData;
import com.braintree.command.result.BrainTreeAddressResult;
import com.braintree.command.result.BrainTreePaymentMethodResult;
import com.braintree.command.result.BrainTreeUpdateCustomerResult;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.model.BrainTreeTransactionDetailModel;
import com.braintreegateway.Customer;


public interface CsBrainTreeFacade
{
	/**
	 * Find transaction by several search fields
	 *
	 * @return List of BraintreeTransactionEntry
	 * @throws AdapterException
	 *            if something went wrong
	 */
	List<BraintreeTransactionEntryData> findBrainTreeTransactions(Calendar startDate, Calendar endDate, String customer,
			String customerEmail, String transactionId, String transactionStatus) throws AdapterException;

	/**
	 * Find transaction by several search fields
	 *
	 * @return List of BraintreeTransactionEntry
	 * @throws AdapterException
	 *            if something went wrong
	 */
	BraintreeTransactionEntryData findBrainTreeTransaction(String transactionId) throws AdapterException;

	/**
	 * void BT transaction from cscockpit.
	 *
	 * @return result of void operation true/false with order linked inside
	 */
	BrainTreeResponseResultData voidTransaction(BrainTreeTransactionDetailModel transaction);

	/**
	 * void BT transaction by id only for default Hybris scenario
	 *
	 * @return result of void operation true/false
	 */
	boolean voidTransaction(PaymentTransactionModel transaction);

	/**
	 * void BT transaction by id only for default Hybris scenario
	 *
	 * @param createCancelTransaction
	 *           - true if need create new cancel Transaction under the order
	 * @return result of void operation true/false
	 */
	boolean voidTransaction(PaymentTransactionModel transaction, boolean createCancelTransaction);

	/**
	 * Clone Transaction by id and with amount and submitForSettlement
	 */
	BrainTreeResponseResultData cloneTransaction(BrainTreeTransactionDetailModel currentTransaction, String amount,
			boolean submitForSettlement);

	/**
	 * Refund transaction by id and with amount - important options
	 */
	BrainTreeResponseResultData refundTransaction(BrainTreeTransactionDetailModel currentTransaction, String amount);

	/**
	 * Refund BT transaction by id only for default Hybris scenario
	 */
	BrainTreeResponseResultData refundTransaction(String transactionId, String amount);

	/**
	 * find all customers
	 */
	List<Customer> findCustomers(String customer, String customerEmail) throws AdapterException;

	/**
	 * find customer by id
	 */
	Customer findCustomer(String customerId) throws AdapterException;

	/**
	 * update customer
	 */
	BrainTreeUpdateCustomerResult updateCustomer(String id, String customerIdField, String firstNameField, String lastNameField,
			String emailField, String phoneField, String faxField, String websiteField, String companyField);

	/**
	 * add payment method
	 */
	BrainTreePaymentMethodResult createCreditCardPaymentMethod(final String customerId, final String paymentMethodNonce,
			final boolean isDefault, String billingAddressId);

	/**
	 * delete payment method
	 *
	 * @param token
	 */
	BrainTreePaymentMethodResult deletePaymentMethod(BrainTreePaymentInfoModel token);

	/**
	 * create new transaction
	 */
	BrainTreeResponseResultData createTransaction(BrainTreeInfo brainTreeInfo);

	/**
	 * remove custom from vault by id
	 */
	BrainTreeResponseResultData removeCustomer(String customerId);

	/**
	 * update payment Method by token
	 */
	BraintreePaymentMethodData updatePaymentMethod(String paymentMethodToken, BrainTreePaymentMethodInfo braintreeInfo);

	/**
	 * Submit For Settlement transaction
	 */
	BrainTreeResponseResultData submitForSettlementTransaction(BrainTreeTransactionDetailModel currentTransaction, String value);

	/**
	 * generate client Token
	 */
	String generateClientToken();

	/**
	 * add new braintree address
	 */
	BrainTreeAddressResult addAddress(final String customerId, final String firstName, final String lastName,
			final String company, final String streetAddress, final String extendedAddress, final String cityLocality,
			final String stateProvinceRegion, final String postalCode, final String countryIsoCode);

	/**
	 * update existing braintree address
	 */
	BrainTreeAddressResult updateAddress(final String customerId, final String addressId, final String firstName,
			final String lastName, final String company, final String streetAddress, final String extendedAddress,
			final String cityLocality, final String stateProvinceRegion, final String postalCode, final String countryName);

	/**
	 * remove address by id
	 */
	BrainTreeAddressResult removeAddress(String addressId, String customerId);
}
