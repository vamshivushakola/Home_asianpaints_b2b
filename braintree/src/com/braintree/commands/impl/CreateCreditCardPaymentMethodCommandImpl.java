/**
 *
 */
package com.braintree.commands.impl;

import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.List;

import com.braintree.command.request.BrainTreeCreateCreditCardPaymentMethodRequest;
import com.braintree.command.result.BrainTreePaymentMethodResult;
import com.braintree.commands.BrainTreeCreateCreditCardPaymentMethodCommand;
import com.braintreegateway.PaymentMethod;
import com.braintreegateway.PaymentMethodRequest;
import com.braintreegateway.Result;
import com.braintreegateway.ValidationError;


public class CreateCreditCardPaymentMethodCommandImpl extends
		AbstractCommand<BrainTreeCreateCreditCardPaymentMethodRequest, BrainTreePaymentMethodResult> implements
		BrainTreeCreateCreditCardPaymentMethodCommand
{
	@Override
	public BrainTreePaymentMethodResult perform(final BrainTreeCreateCreditCardPaymentMethodRequest request)
	{
		final Result<? extends PaymentMethod> result = getBraintreeGateway().paymentMethod().create(request.getRequest());

		if (result.isSuccess())
		{
			return translateResponse(result.getTarget(), result.isSuccess());
		}
		else
		{
			return translateErrorResponse(request.getRequest(), result);
		}
	}

	private BrainTreePaymentMethodResult translateResponse(final PaymentMethod target, final boolean success)
	{
		final BrainTreePaymentMethodResult result = new BrainTreePaymentMethodResult();
		if (target != null)
		{
			result.setPaymentMethod(target);
			result.setSuccess(success);
			if (success)
			{
				result.setTransactionStatus(TransactionStatus.ACCEPTED);
				result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);
			}
		}
		return result;
	}

	private BrainTreePaymentMethodResult translateErrorResponse(final PaymentMethodRequest request,
			final Result<? extends PaymentMethod> result)
	{
		final BrainTreePaymentMethodResult response = new BrainTreePaymentMethodResult();
		response.setSuccess(result.isSuccess());
		if (result.getErrors() != null)
		{
			final List<ValidationError> allDeepValidationErrors = result.getErrors().getAllDeepValidationErrors();
			if (allDeepValidationErrors != null && allDeepValidationErrors.size() > 0)
			{
				final ValidationError validationError = allDeepValidationErrors.get(0);
				getLoggingHandler().getLogger().info(
						String.format("Cannot create Payment method for user(%s) with error: %s %s", request.getCustomerId(),
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

}
