package com.braintree.commands.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.List;

import com.braintree.command.request.BrainTreeSubmitForSettlementTransactionRequest;
import com.braintree.command.result.BrainTreeSubmitForSettlementTransactionResult;
import com.braintree.commands.BrainTreeSubmitForSettlementCommand;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;


public class SubmitForSettlementTransactionCommandImpl extends AbstractCommand implements BrainTreeSubmitForSettlementCommand
{

	@Override
	public BrainTreeSubmitForSettlementTransactionResult perform(final BrainTreeSubmitForSettlementTransactionRequest request)
	{
		validateParameterNotNullStandardMessage("Submit For Settlement transaction Request", request);
		try
		{
			final Result<Transaction> result = submitForSettlement(request);

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

	private Result<Transaction> submitForSettlement(final BrainTreeSubmitForSettlementTransactionRequest request)
	{
		if (request.getAmount() == null)
		{
			return getBraintreeGateway().transaction().submitForSettlement(request.getTransactionId());
		}
		else
		{
			return getBraintreeGateway().transaction().submitForSettlement(request.getTransactionId(), request.getAmount());
		}
	}

	private BrainTreeSubmitForSettlementTransactionResult translateErrorResponse(final String transactionId,
			final Result<Transaction> result)
	{
		final BrainTreeSubmitForSettlementTransactionResult response = new BrainTreeSubmitForSettlementTransactionResult(
				result.isSuccess());
		if (result.getErrors() != null)
		{

			final List<ValidationError> allDeepValidationErrors = result.getErrors().getAllDeepValidationErrors();
			if (allDeepValidationErrors != null && allDeepValidationErrors.size() > 0)
			{
				final ValidationError validationError = allDeepValidationErrors.get(0);
				getLoggingHandler().getLogger().info(
						String.format("BT transaction id(%s) submit for settlement with error: %s %s", transactionId,
								validationError.getCode(), validationError.getMessage()));

				if (validationError.getCode() != null)
				{
					response.setErrorCode(validationError.getCode().toString());
				}
				response.setErrorMessage(validationError.getMessage());
			}
		}
		return response;
	}

	private BrainTreeSubmitForSettlementTransactionResult translateResponse(final Transaction target, final boolean success)
	{
		final BrainTreeSubmitForSettlementTransactionResult result = new BrainTreeSubmitForSettlementTransactionResult(success);
		if (target != null)
		{
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
