/**
 *
 */
package com.braintree.transaction.service;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;

import com.braintree.command.result.BrainTreeRefundTransactionResult;
import com.braintree.command.result.BrainTreeVoidResult;


public interface BrainTreeTransactionService
{
	boolean createAuthorizationTransaction();

	boolean createPaymentMethodTokenForOrderReplenishment();

	PaymentTransactionEntryModel createCancelTransaction(PaymentTransactionModel transaction, BrainTreeVoidResult voidResult);

	PaymentTransactionEntryModel createRefundTransaction(PaymentTransactionModel transaction,
			BrainTreeRefundTransactionResult result);

	PaymentTransactionEntryModel createAuthorizationTransaction(CartModel cart);

}
