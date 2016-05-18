/**
 *
 */
package com.braintree.commands.impl;

import de.hybris.platform.payment.commands.CaptureCommand;
import de.hybris.platform.payment.commands.request.CaptureRequest;
import de.hybris.platform.payment.commands.result.CaptureResult;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.Currency;
import java.util.List;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;


public class CaptureCommandImpl extends AbstractCommand implements CaptureCommand
{

	private CaptureRequest request;
	private BrainTreeConfigService brainTreeConfigService;

	@Override
	public CaptureResult perform(final CaptureRequest request)
	{
		this.request = request;

		final CaptureResult result;
		final Boolean settlementConfigParameter = getBrainTreeConfigService().getSettlementConfigParameter();
		final String transactionID = request.getRequestId();
		if (settlementConfigParameter.booleanValue())
		{
			result = createDelayedSettlementResponse(request.getRequestId());
		}
		else
		{
			getLoggingHandler().getLogger().info(String.format("[CAPTURING FOR TRANSACTION CODE: %s]", transactionID));
			final Result<Transaction> braintreeReply = getBraintreeGateway().transaction().submitForSettlement(transactionID);

			result = translateResponse(braintreeReply);
		}

		return result;
	}

	private CaptureResult createDelayedSettlementResponse(final String transactionID)
	{
		getLoggingHandler().getLogger().info(String.format("[SETTLEMENT FOR TRANSACTION ID: %s IS DELAYED]", transactionID));
		final CaptureResult result = new CaptureResult();

		result.setCurrency(request.getCurrency());
		result.setMerchantTransactionCode(request.getMerchantTransactionCode());
		result.setRequestId(request.getRequestId());
		result.setRequestToken(request.getRequestToken());
		result.setTransactionStatus(TransactionStatus.ACCEPTED);
		result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);

		return result;
	}

	private CaptureResult translateResponse(final Result<Transaction> braintreeReply)
	{
		List<ValidationError> errors = null;
		Transaction transaction = null;

		final CaptureResult result = new CaptureResult();
		result.setTransactionStatus(TransactionStatus.REJECTED);
		result.setTransactionStatusDetails(TransactionStatusDetails.BANK_DECLINE);
		if (braintreeReply != null)
		{
			transaction = braintreeReply.getTarget();

			if (braintreeReply.isSuccess())
			{
				if (transaction != null)
				{

					if (transaction.getAmount() != null)
					{
						result.setTotalAmount(transaction.getAmount());
					}

					result.setCurrency(Currency.getInstance(transaction.getCurrencyIsoCode()));
					result.setMerchantTransactionCode(transaction.getMerchantAccountId());
					result.setRequestId(transaction.getId());
					result.setRequestToken(transaction.getAuthorizedTransactionId());
					result.setTransactionStatus(TransactionStatus.ACCEPTED);
					result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);
				}
			}
			else if (braintreeReply.getErrors() != null)
			{

				if (braintreeReply.getErrors().getAllDeepValidationErrors() != null
						&& braintreeReply.getErrors().getAllDeepValidationErrors().size() > 0)
				{

					result.setTransactionStatusDetails(getCodeTranslator().translateReasonCode(
							braintreeReply.getErrors().getAllDeepValidationErrors().get(0).getCode().code));

					errors = braintreeReply.getErrors().getAllDeepValidationErrors();
					final String errorMessage = getLoggingHandler().handleErrors(errors);

					throw new IllegalArgumentException(errorMessage);
				}

			}
			getLoggingHandler().handleResult("[CAPTURE TRNSACTION] ", transaction);
		}

		return result;
	}

	/**
	 * @return the brainTreeConfigService
	 */
	@Override
	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	/**
	 * @param brainTreeConfigService
	 *           the brainTreeConfigService to set
	 */
	@Override
	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}

}
