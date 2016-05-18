/**
 *
 */
package com.braintree.commands.impl;

import org.apache.commons.lang.StringUtils;

import com.braintree.command.request.BrainTreeCreatePaymentMethodRequest;
import com.braintree.command.result.BrainTreeCreatePaymentMethodResult;
import com.braintree.commands.BrainTreeCreatePaymentMethodCommand;
import com.braintreegateway.CreditCard;
import com.braintreegateway.PayPalAccount;
import com.braintreegateway.PaymentMethodRequest;
import com.braintreegateway.Result;


public class CreatePaymentMethodCommandImpl extends
		AbstractCommand<BrainTreeCreatePaymentMethodRequest, BrainTreeCreatePaymentMethodResult> implements
		BrainTreeCreatePaymentMethodCommand
{
	@Override
	public BrainTreeCreatePaymentMethodResult perform(final BrainTreeCreatePaymentMethodRequest request)
	{
		final PaymentMethodRequest translateRequest = translateRequest(request);

		final Result brainTreeResult = getBraintreeGateway().paymentMethod().create(translateRequest);

		final BrainTreeCreatePaymentMethodResult result = translateResult(brainTreeResult);

		return result;
	}

	private BrainTreeCreatePaymentMethodResult translateResult(final Result brainTreeResult)
	{
		if (brainTreeResult.getErrors() != null)
		{
			getLoggingHandler().handleErrors(brainTreeResult.getErrors().getAllDeepValidationErrors());
			getLoggingHandler().handleErrors(brainTreeResult.getErrors().getAllValidationErrors());
		}

		final BrainTreeCreatePaymentMethodResult result = new BrainTreeCreatePaymentMethodResult();
		PayPalAccount payPal = null;
		CreditCard card = null;
		if (brainTreeResult.getTarget() instanceof PayPalAccount)
		{
			payPal = (PayPalAccount) brainTreeResult.getTarget();
			result.setPaymentMethodToken(payPal.getToken());
			result.setImageSource(payPal.getImageUrl());
			result.setEmail(payPal.getEmail());
			result.setCardType(StringUtils.EMPTY);
			result.setExpirationMonth(StringUtils.EMPTY);
			result.setExpirationYear(StringUtils.EMPTY);
			result.setCardNumber(StringUtils.EMPTY);
			getLoggingHandler().handleResult("[PAYPAL ACCOUNT]", payPal);
		}
		else if (brainTreeResult.getTarget() instanceof CreditCard)
		{
			card = (CreditCard) brainTreeResult.getTarget();
			result.setPaymentMethodToken(card.getToken());
			result.setCardType(card.getCardType());
			result.setExpirationMonth(card.getExpirationMonth());
			result.setExpirationYear(card.getExpirationYear());
			result.setCardNumber(card.getMaskedNumber());
			result.setImageSource(card.getImageUrl());
			getLoggingHandler().handleResult("[PAYMENT METHOD]", card);
		}

		return result;
	}

	private PaymentMethodRequest translateRequest(final BrainTreeCreatePaymentMethodRequest request)
	{
		getLoggingHandler().handleCreatePaymentMethodRequest(request);
		final PaymentMethodRequest brainTreeRequest = new PaymentMethodRequest().customerId(request.getCustomerId())
				.paymentMethodNonce(request.getMethodNonce());
		return brainTreeRequest;
	}



}
