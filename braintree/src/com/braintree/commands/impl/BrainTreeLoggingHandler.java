/**
 *
 */
package com.braintree.commands.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;
import static org.apache.commons.lang.StringUtils.EMPTY;

import de.hybris.platform.payment.commands.request.CreateSubscriptionRequest;
import de.hybris.platform.payment.dto.BillingInfo;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.command.request.BrainTreeAddressRequest;
import com.braintree.command.request.BrainTreeAuthorizationRequest;
import com.braintree.command.request.BrainTreeCreatePaymentMethodRequest;
import com.braintreegateway.Address;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.PayPalAccount;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;


public class BrainTreeLoggingHandler
{
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String AUTHORIZATION_REQUEST = "[AUTHORIZATION REQUEST]";
	private static final String CREATE_SUBSCRIPTION_REQUEST = "[CREATE CUSTOMER REQUEST]";

	private final static Logger LOG = Logger.getLogger(BrainTreeLoggingHandler.class);

	public Logger getLogger()
	{
		return LOG;
	}

	private StringBuilder getSB()
	{
		return new StringBuilder();
	}

	public void handleAuthorizationRequest(final BrainTreeAuthorizationRequest authorizationRequest)
	{
		validateParameterNotNullStandardMessage("captureRequest", authorizationRequest);
		validateParameterNotNullStandardMessage("customerId", authorizationRequest.getCustomerId());
		validateParameterNotNullStandardMessage("totalAmount", authorizationRequest.getTotalAmount());

		final StringBuilder sb = getSB();

		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Customer Id", authorizationRequest.getCustomerId());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Total amount", authorizationRequest.getTotalAmount().toString());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Payment type", authorizationRequest.getPaymentType());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Device data", authorizationRequest.getDeviceData());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Method nonce", authorizationRequest.getMethodNonce());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Payment type", authorizationRequest.getPaymentType());
		if (authorizationRequest.getLiabilityShifted() != null)
		{
			verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Liability shifted", authorizationRequest.getLiabilityShifted().toString());
		}
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Payment method token", authorizationRequest.getPaymentMethodToken());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "ThreeD secure config", authorizationRequest.getThreeDSecureConfiguration()
				.toString());
		if (authorizationRequest.getAdvancedFraudTools() != null)
		{
			verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Advanced fraud tools", authorizationRequest.getAdvancedFraudTools().toString());
		}
		if (authorizationRequest.getIsSkip3dSecureLiabilityResult() != null)
		{
			verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Is skip threD config", authorizationRequest.getIsSkip3dSecureLiabilityResult()
					.toString());
		}

		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Credit card statement name", authorizationRequest.getCreditCardStatementName());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "BrainTree channel", authorizationRequest.getBrainTreeChannel());
		verifyIfEmpty(sb, AUTHORIZATION_REQUEST, "Store in vault", String.valueOf(authorizationRequest.isStoreInVault()));

		LOG.info(sb.toString());
	}

	public void handleAddressRequest(final String type, final BrainTreeAddressRequest addressRequest)
	{
		final StringBuilder sb = getSB();
		verifyIfEmpty(sb, type, "Address Id", addressRequest.getAddressId());
		verifyIfEmpty(sb, type, "Company", addressRequest.getCompany());
		verifyIfEmpty(sb, type, "Country", addressRequest.getCountryCodeAlpha2());
		verifyIfEmpty(sb, type, "Customer Id", addressRequest.getCustomerId());
		verifyIfEmpty(sb, type, "Extended address", addressRequest.getExtendedAddress());
		verifyIfEmpty(sb, type, "First name", addressRequest.getFirstName());
		verifyIfEmpty(sb, type, "Last name", addressRequest.getLastName());
		verifyIfEmpty(sb, type, "City", addressRequest.getLocality());
		verifyIfEmpty(sb, type, "Postal code", addressRequest.getPostalCode());
		verifyIfEmpty(sb, type, "Region", addressRequest.getRegion());
		verifyIfEmpty(sb, type, "Street address", addressRequest.getStreetAddress());

		LOG.info(sb.toString());
	}

	public void handleResult(final String type, final Address address)
	{

		final StringBuilder sb = getSB();

		sb.append(formatAddress(type, address));

		LOG.info(sb.toString());

	}

	public void handleResult(final String type, final Transaction transaction)
	{

		final StringBuilder sb = getSB();

		sb.append(formatTransaction(type, transaction));

		LOG.info(sb.toString());

	}

	public void handleCreatePaymentMethodRequest(final BrainTreeCreatePaymentMethodRequest request)
	{
		final StringBuilder sb = getSB();
		verifyIfEmpty(sb, "[PAYMENT METHOD REQUEST]", "Customer ID", request.getCustomerId());
		verifyIfEmpty(sb, "[PAYMENT METHOD REQUEST]", "Method nonce", request.getMethodNonce());

		LOG.info(sb.toString());
	}

	public void handleResult(final String type, final CreditCard creditCard)
	{
		final StringBuilder sb = getSB();
		sb.append(type + "CREATED").append(NEW_LINE);
		formatCreditCard(type, creditCard);

		LOG.info(sb.toString());

	}

	public void handleResult(final String type, final PayPalAccount payPalAccount)
	{
		final StringBuilder sb = getSB();
		sb.append(type + "CREATED").append(NEW_LINE);
		formatPayPalAccount(type, payPalAccount);

		LOG.info(sb.toString());

	}

	private String formatPayPalAccount(String type, final PayPalAccount payPalAccount)
	{
		if (payPalAccount == null)
		{
			return EMPTY;
		}
		final StringBuilder sb = getSB();
		type = type + "[CREDIT CARD]";

		verifyIfEmpty(sb, type, "Customer ID", payPalAccount.getCustomerId());
		verifyIfEmpty(sb, type, "Image url", payPalAccount.getImageUrl());
		verifyIfEmpty(sb, type, "Token", payPalAccount.getToken());
		verifyIfEmpty(sb, type, "PayPal email", payPalAccount.getEmail());

		return sb.toString();
	}

	public String handleErrors(final List<ValidationError> errors)
	{
		if (errors != null && !errors.isEmpty())
		{
			final StringBuilder errorLog = new StringBuilder();
			for (final ValidationError error : errors)
			{
				formatError(errorLog, error);
			}
			LOG.error(errorLog.toString());

			//return first error message for error stack trace
			return formatError(new StringBuilder(), errors.get(0));
		}
		return StringUtils.EMPTY;
	}

	private String formatError(final StringBuilder errorLog, final ValidationError error)
	{
		errorLog.append("[BrainTree Validation Error] [code = ").append(error.getCode()).append("(").append(error.getCode().code)
				.append(") message = ").append(error.getMessage()).append("]").append(NEW_LINE);
		return errorLog.toString();
	}

	public void handleResult(final String type, final Customer customer)
	{
		final StringBuilder sb = getSB();
		formatCustomer(type, customer);
		LOG.info(sb.toString());
	}

	public void handleCreateSubscriptionRequest(final CreateSubscriptionRequest request)
	{
		validateParameterNotNullStandardMessage("Create User Request", request);
		final BillingInfo billingInfo = request.getBillingInfo();
		validateParameterNotNullStandardMessage("Billing Info", billingInfo);

		final StringBuilder sb = getSB();

		verifyIfEmpty(sb, CREATE_SUBSCRIPTION_REQUEST, "Customer first name", billingInfo.getFirstName());
		verifyIfEmpty(sb, CREATE_SUBSCRIPTION_REQUEST, "Customer last name", billingInfo.getLastName());
		verifyIfEmpty(sb, CREATE_SUBSCRIPTION_REQUEST, "Customer email", billingInfo.getEmail());

		LOG.info(sb.toString());

	}


	private String formatCustomer(String type, final Customer customer)
	{
		if (customer == null)
		{
			return EMPTY;
		}

		final StringBuilder sb = getSB();
		type = "[CUSTOMER]" + type;

		verifyIfEmpty(sb, type, "ID", customer.getId());
		verifyIfEmpty(sb, type, "First name", customer.getFirstName());
		verifyIfEmpty(sb, type, "Last name", customer.getLastName());
		verifyIfEmpty(sb, type, "Email", customer.getEmail());

		return sb.toString();
	}



	private String formatTransaction(final String type, final Transaction transaction)
	{

		final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		if (transaction == null)
		{
			return EMPTY;
		}

		final StringBuilder sb = new StringBuilder();
		verifyIfEmpty(sb, type, "Merchant account ID", formatMerchantAccountId(transaction.getMerchantAccountId()));
		verifyIfEmpty(sb, type, "Id", transaction.getId());
		verifyIfEmpty(sb, type, "Amount", transaction.getAmount().toString());
		verifyIfEmpty(sb, type, "Currency iso code", transaction.getCurrencyIsoCode());
		verifyIfEmpty(sb, type, "Date", df.format(transaction.getUpdatedAt().getTime()));
		verifyIfEmpty(sb, type, "Status", transaction.getStatus().toString());

		sb.append(formatCustomer(StringUtils.EMPTY, transaction.getCustomer()));

		sb.append(formatCreditCard(StringUtils.EMPTY, transaction.getCreditCard()));

		sb.append(formatAddress("[BILLING ADDRESS]", transaction.getBillingAddress()));
		sb.append(formatAddress("[SHIPPING ADDRESS]", transaction.getShippingAddress()));

		return sb.toString();
	}

	private String formatMerchantAccountId(final String merchantAccountId)
	{
		final int len = merchantAccountId.length();
		return merchantAccountId.substring(0, 2) + "****" + merchantAccountId.substring(len - 2, len);
	}

	private String formatCreditCard(String type, final CreditCard card)
	{
		final StringBuilder sb = getSB();

		if (card == null)
		{
			return EMPTY;
		}

		type = type + "[CREDIT CARD]";

		verifyIfEmpty(sb, type, "Card holder full name", card.getCardholderName());
		verifyIfEmpty(sb, type, "Customer ID", card.getCustomerId());
		verifyIfEmpty(sb, type, "Card type", card.getCardType());
		verifyIfEmpty(sb, type, "Card number", card.getMaskedNumber());
		verifyIfEmpty(sb, type, "Image url", card.getImageUrl());
		verifyIfEmpty(sb, type, "Expiration", card.getExpirationMonth() + "-" + card.getExpirationYear());
		verifyIfEmpty(sb, type, "Token", card.getToken());
		verifyIfEmpty(sb, type, "Bank issue", card.getIssuingBank());

		sb.append(formatAddress("[BILLING ADDRESS]", card.getBillingAddress()));
		return sb.toString();

	}

	private String formatAddress(final String type, final Address address)
	{
		final StringBuilder sb = getSB();
		if (address == null)
		{
			return EMPTY;
		}

		verifyIfEmpty(sb, type, "First name", address.getFirstName());
		verifyIfEmpty(sb, type, "Last name", address.getLastName());
		verifyIfEmpty(sb, type, "Street address", address.getStreetAddress());
		verifyIfEmpty(sb, type, "Extended address", address.getExtendedAddress());
		verifyIfEmpty(sb, type, "Locality", address.getLocality());
		verifyIfEmpty(sb, type, "Region", address.getRegion());
		verifyIfEmpty(sb, type, "Postal code", address.getPostalCode());
		verifyIfEmpty(sb, type, "Country name", address.getCountryName());

		return sb.toString();
	}


	private void verifyIfEmpty(final StringBuilder sb, final String type, final String name, final String value)
	{
		if (StringUtils.isNotEmpty(value))
		{
			sb.append(type).append(name).append(" = ").append(value).append(NEW_LINE);
		}
	}

}
