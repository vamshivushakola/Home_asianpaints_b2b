package com.braintree.commands.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.List;

import com.braintree.command.request.BrainTreeRefundTransactionRequest;
import com.braintree.command.result.BrainTreeRefundTransactionResult;
import com.braintree.commands.BrainTreeRefundCommand;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;


public class RefundTransactionCommandImpl extends AbstractCommand implements BrainTreeRefundCommand
{
	@Override
	public BrainTreeRefundTransactionResult perform(final BrainTreeRefundTransactionRequest request)
	{
		validateParameterNotNullStandardMessage("Refund transaction Request", request);
		try
		{
			final Result<Transaction> result = getBraintreeGateway().transaction().refund(request.getTransactionId(),
					request.getAmount());
			if (result.isSuccess())
			{
				return translateResponse(result.getTarget(), result.isSuccess());
			}
			else
			{
				return translateErrorResponse(request.getTransactionId(), result);
			}
		}
		catch (final Exception exception)
		{
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	private BrainTreeRefundTransactionResult translateErrorResponse(final String transactionId, final Result<Transaction> result)
	{
		final BrainTreeRefundTransactionResult response = new BrainTreeRefundTransactionResult(result.isSuccess());
		if (result.getErrors() != null)
		{

			final List<ValidationError> allDeepValidationErrors = result.getErrors().getAllDeepValidationErrors();
			if (allDeepValidationErrors != null && allDeepValidationErrors.size() > 0)
			{
				final ValidationError validationError = allDeepValidationErrors.get(0);
				getLoggingHandler().getLogger().info(
						String.format("BT transaction id(%s) refund with error: %s %s", transactionId, validationError.getCode(),
								validationError.getMessage()));

				if (validationError.getCode() != null)
				{
					response.setErrorCode(validationError.getCode().toString());
				}
				response.setErrorMessage(validationError.getMessage());
			}
		}
		return response;
	}

	private BrainTreeRefundTransactionResult translateResponse(final Transaction target, final boolean success)
	{
		final BrainTreeRefundTransactionResult result = new BrainTreeRefundTransactionResult(success);
		if (target != null)
		{
			result.setAmount(target.getAmount());
			result.setTransactionId(target.getId());
			result.setTransaction(target);
			if (success)
			{
				result.setTransactionStatus(TransactionStatus.ACCEPTED);
				result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);
			}
		}
		return result;
	}
}
