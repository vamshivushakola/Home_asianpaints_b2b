package com.braintree.commands.impl;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.commands.AuthorizationCommand;
import de.hybris.platform.payment.commands.request.AuthorizationRequest;
import de.hybris.platform.payment.commands.result.AuthorizationResult;
import de.hybris.platform.payment.dto.AvsStatus;
import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.payment.dto.CvnStatus;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.Currency;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.braintree.command.request.BrainTreeAuthorizationRequest;
import com.braintree.command.result.BrainTreeAuthorizationResult;
import com.braintree.constants.BraintreeConstants;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;


public class AuthorizationCommandImpl extends AbstractCommand<AuthorizationRequest, AuthorizationResult>
		implements AuthorizationCommand
{
	private static final String AUTHORIZATION_TRANSACTION = "[AUTHORIZATION TRANSACTION] ";

	@Override
	public AuthorizationResult perform(final AuthorizationRequest authorizationRequest)
	{
		final TransactionRequest translateRequest = translateRequest(authorizationRequest);

		final Result<Transaction> braintreeReply = getBraintreeGateway().transaction().sale(translateRequest);

		final AuthorizationResult translateResponse = translateResponse(braintreeReply);
		return translateResponse;

	}

	private TransactionRequest translateRequest(final AuthorizationRequest authorizationRequest)
	{
		TransactionRequest request = null;

		if (authorizationRequest instanceof BrainTreeAuthorizationRequest)
		{
			final BrainTreeAuthorizationRequest brainTreeAuthorizationRequest = (BrainTreeAuthorizationRequest) authorizationRequest;

			request = new TransactionRequest().customerId(brainTreeAuthorizationRequest.getCustomerId())
					.amount(authorizationRequest.getTotalAmount()).options().submitForSettlement(Boolean.FALSE).done();

			setAdditionalParameters(brainTreeAuthorizationRequest, request);

			getLoggingHandler().handleAuthorizationRequest(brainTreeAuthorizationRequest);

		}
		else
		{
			final String errorMessage = "[BT Authorization Error] Authorization Request must be Brain Tree type!";
			getLoggingHandler().getLogger().error(errorMessage);
			throw new AdapterException(errorMessage);
		}
		return request;
	}

	private void setAdditionalParameters(final BrainTreeAuthorizationRequest brainTreeAuthorizationRequest,
			final TransactionRequest request)
	{

		if (brainTreeAuthorizationRequest.getUsePaymentMethodToken().booleanValue())
		{
			if (brainTreeAuthorizationRequest.getPaymentMethodToken() == null)
			{
				getLoggingHandler().getLogger().error("Error: PaymentMethodToken is null!");
				throw new IllegalArgumentException("Error during using existing payment.");
			}
			request.paymentMethodToken(brainTreeAuthorizationRequest.getPaymentMethodToken());
		}
		else
		{
			request.paymentMethodNonce(brainTreeAuthorizationRequest.getMethodNonce());
		}

		request.options().storeInVault(Boolean.valueOf(brainTreeAuthorizationRequest.isStoreInVault())).done();

		if ((BraintreeConstants.PAY_PAL_EXPRESS_CHECKOUT.equals(brainTreeAuthorizationRequest.getPaymentType())
				|| BraintreeConstants.PAYPAL_PAYMENT.equals(brainTreeAuthorizationRequest.getPaymentType()))
				&& Boolean.FALSE.equals(Boolean.valueOf(brainTreeAuthorizationRequest.isStoreInVault()))
				&& Boolean.TRUE.equals(brainTreeAuthorizationRequest.getAdvancedFraudTools()))
		{
			request.deviceData(brainTreeAuthorizationRequest.getDeviceData());
		}
		if (BraintreeConstants.BRAINTREE_PAYMENT.equals(brainTreeAuthorizationRequest.getPaymentType()))
		{
			if (brainTreeAuthorizationRequest.getThreeDSecureConfiguration().booleanValue())
			{
				boolean threeDSecureRequired = true;
				if (brainTreeAuthorizationRequest.getIsSkip3dSecureLiabilityResult().booleanValue()
						&& !brainTreeAuthorizationRequest.getLiabilityShifted().booleanValue())
				{
					threeDSecureRequired = false;
				}
				request.options().threeDSecure().required(threeDSecureRequired);
			}
			if (Boolean.TRUE.equals(brainTreeAuthorizationRequest.getAdvancedFraudTools()))
			{
				request.deviceData(brainTreeAuthorizationRequest.getDeviceData());
			}
		}

		if ((StringUtils.isNotBlank(brainTreeAuthorizationRequest.getCreditCardStatementName())))
		{
			request.descriptor().name(brainTreeAuthorizationRequest.getCreditCardStatementName()).done();
		}

		final BillingInfo shippingInfo = brainTreeAuthorizationRequest.getShippingInfo();

		if (StringUtils.isNotEmpty(brainTreeAuthorizationRequest.getBrainTreeAddressId()))
		{
			request.billingAddressId(brainTreeAuthorizationRequest.getBrainTreeAddressId());
		}
		else
		{
			request.shippingAddress().countryCodeAlpha2(shippingInfo.getCountry()).region(shippingInfo.getState())
					.firstName(shippingInfo.getFirstName()).lastName(shippingInfo.getLastName())
					.streetAddress(shippingInfo.getStreet1()).extendedAddress(shippingInfo.getStreet2())
					.locality(shippingInfo.getCity()).postalCode(shippingInfo.getPostalCode());
		}

		request.channel(brainTreeAuthorizationRequest.getBrainTreeChannel());
		request.merchantAccountId(brainTreeAuthorizationRequest.getMerchantAccountIdForCurrentSite());

	}

	private AuthorizationResult translateResponse(final Result<Transaction> braintreeReply)
	{
		final BrainTreeAuthorizationResult result = new BrainTreeAuthorizationResult();
		result.setTransactionStatus(TransactionStatus.REJECTED);
		if (braintreeReply != null)
		{
			result.setSuccess(braintreeReply.isSuccess());

			Transaction transaction = null;
			List<ValidationError> errors = null;

			transaction = braintreeReply.getTarget();

			if (braintreeReply.isSuccess())
			{
				if (transaction != null)
				{
					result.setAuthorizationCode(transaction.getProcessorAuthorizationCode());
					result.setAvsStatus(AvsStatus.MATCHED);
					result.setCvnStatus(CvnStatus.MATCHED);

					if (transaction.getAmount() != null)
					{
						result.setTotalAmount(transaction.getAmount());
					}

					result.setAuthorizationTime(transaction.getCreatedAt().getTime());
					result.setCurrency(Currency.getInstance(transaction.getCurrencyIsoCode()));
					result.setMerchantTransactionCode(transaction.getMerchantAccountId());
					result.setRequestId(transaction.getId());
					result.setRequestToken(transaction.getId());
					result.setTransactionStatus(TransactionStatus.ACCEPTED);
					result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);
				}
				else
				{
					result.setTransactionStatusDetails(TransactionStatusDetails.BANK_DECLINE);
				}
			}
			else if (braintreeReply.getErrors() != null)
			{
				final StringBuilder errorMessage = new StringBuilder("[ERROR AUTHORIZATION] ");
				final StringBuilder errorMessageReason = new StringBuilder();
				if (braintreeReply.getErrors().getAllDeepValidationErrors() != null
						&& braintreeReply.getErrors().getAllDeepValidationErrors().size() > 0)
				{

					result.setTransactionStatusDetails(getCodeTranslator()
							.translateReasonCode(braintreeReply.getErrors().getAllDeepValidationErrors().get(0).getCode().code));

					errors = braintreeReply.getErrors().getAllDeepValidationErrors();
					errorMessage.append(getLoggingHandler().handleErrors(errors));
					errorMessageReason
							.append(getErrorTranslator().getMessage(braintreeReply.getErrors().getAllDeepValidationErrors().get(0)));
				}
				if (result.getTransactionStatusDetails() == null)
				{
					result.setTransactionStatusDetails(TransactionStatusDetails.NO_AUTHORIZATION_FOR_SETTLEMENT);
					errorMessage.append(braintreeReply.getMessage());
					errorMessageReason.append("(");
					errorMessageReason.append(braintreeReply.getMessage());
					errorMessageReason.append(")");
				}
				throw new IllegalArgumentException(errorMessageReason.toString());
			}

			getLoggingHandler().handleResult(AUTHORIZATION_TRANSACTION, transaction);
		}

		return result;
	}

}
