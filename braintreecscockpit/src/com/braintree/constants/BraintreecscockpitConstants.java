/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.braintree.constants;


/**
 * Global class for all braintreecscockpit constants. You can add global constants for your extension into this class.
 */
public final class BraintreecscockpitConstants extends GeneratedBraintreecscockpitConstants
{
	public static final String EXTENSIONNAME = "braintreecscockpit";

	public static final String TRANSACTION_SEARCH_DATE_FORMAT = "MM/dd/yy h:mm a";
	public static final String PAYPAL_PAYMENT_INSTRUMENT_TYPE = "paypal_account";
	public static final String CREDIT_CARD_PAYMENT_INSTRUMENT_TYPE = "credit_card";
	public static final String PAYPAL_PAYMENT_TYPE_NAME = "PayPal Account";
	public static final String CREDIT_CARD_PAYMENT_TYPE = "Credit Card";

	private BraintreecscockpitConstants()
	{
	}

	public interface TransactionManagementActionsWidgetRenderer
	{
		static final String WIDGET_ICON_SUCCESS = "z-msgbox z-msgbox-information";
		static final String WIDGET_ICON_ERROR = "z-msgbox z-msgbox-error";
		static final String WIDGET_VOID_TITLE = "cscockpit.widget.transaction.transactionmanagement.voidTransaction.title";
		static final String WIDGET_MESSAGE_VOID_ERROR = "cscockpit.widget.transaction.transactionmanagement.voidTransaction.message.error";
		static final String WIDGET_MESSAGE_VOID_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.voidTransaction.message.success";

		static final String WIDGET_CLONE_TITLE = "cscockpit.widget.transaction.transactionmanagement.cloneTransaction.title";
		static final String WIDGET_MESSAGE_CLONE_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.cloneTransaction.message.success";
		static final String WIDGET_MESSAGE_CLONE_ERROR = "cscockpit.widget.transaction.transactionmanagement.cloneTransaction.message.error";

		static final String WIDGET_VOID_ASK_TITLE = "cscockpit.widget.transaction.transactionmanagement.voidTransaction.ask.title";
		static final String WIDGET_VOID_ASK_MESSAGE = "cscockpit.widget.transaction.transactionmanagement.voidTransaction.ask.message";

		static final String WIDGET_REFUND_TITLE = "cscockpit.widget.transaction.transactionmanagement.refundTransaction.title";
		static final String WIDGET_MESSAGE_REFUND_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.refundTransaction.message.success";
		static final String WIDGET_MESSAGE_REFUND_ERROR = "cscockpit.widget.transaction.transactionmanagement.refundTransaction.message.error";

		static final String WIDGET_MESSAGE_CUSTOMER_UPDATE_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.updateCustomer.message.success";
		static final String WIDGET_MESSAGE_CUSTOMER_UPDATE_ERROR = "cscockpit.widget.transaction.transactionmanagement.updateCustomer.message.error";
		static final String WIDGET_CUSTOMER_UPDATE_TITLE = "cscockpit.widget.transaction.transactionmanagement.updateCustomer.title";

		static final String WIDGET_MESSAGE_VOID_DURING_REFUND_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.refundVoidTransaction.message.info";

		static final String WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.newTransaction.message.success";
		static final String WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS_POSTFIX = "cscockpit.widget.transaction.transactionmanagement.newTransaction.message.success.postfix";
		static final String WIDGET_MESSAGE_TRANSACTION_CREATE_ERROR = "cscockpit.widget.transaction.transactionmanagement.newTransaction.message.error";
		static final String WIDGET_TRANSACTION_CREATE_TITLE = "cscockpit.widget.transaction.transactionmanagement.newTransaction.title";

		static final String WIDGET_MESSAGE_PAYMENT_METHOD_DELETE_SUCCESS = "cscockpit.widget.payment.method.delete.success";
		static final String WIDGET_MESSAGE_PAYMENT_METHOD_DELETE_ERROR = "cscockpit.widget.payment.method.delete.error";
		static final String WIDGET_PAYMENT_METHOD_DELETE_TITLE = "cscockpit.widget.payment.method.delete.title";

		static final String WIDGET_MESSAGE_CUSTOMER_REMOVE_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.removeCustomer.message.success";
		static final String WIDGET_MESSAGE_CUSTOMER_REMOVE_ERROR = "cscockpit.widget.transaction.transactionmanagement.removeCustomer.message.error";
		static final String WIDGET_CUSTOMER_REMOVE_TITLE = "cscockpit.widget.transaction.transactionmanagement.removeCustomer.title";

		static final String WIDGET_REMOVE_CUSTOMER_ASK_TITLE = "cscockpit.widget.transaction.transactionmanagement.removeCustomer.ask.title";
		static final String WIDGET_REMOVE_CUSTOMER_ASK_MESSAGE = "cscockpit.widget.transaction.transactionmanagement.removeCustomer.ask.message";

		static final String WIDGET_MESSAGE_EDIT_PAYMENT_METHOD_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.updatePaymentMethod.message.success";
		static final String WIDGET_MESSAGE_EDIT_PAYMENT_METHOD_ERROR = "cscockpit.widget.transaction.transactionmanagement.updatePaymentMethod.message.error";
		static final String WIDGET_EDIT_PAYMENT_METHOD_TITLE = "cscockpit.widget.transaction.transactionmanagement.updatePaymentMethod.title";

		static final String WIDGET_REMOVE_PAYMENT_METHOD_ASK_TITLE = "cscockpit.widget.transaction.transactionmanagement.removePaymentMethod.ask.title";
		static final String WIDGET_REMOVE_PAYMENT_METHOD_ASK_MESSAGE = "cscockpit.widget.transaction.transactionmanagement.removePaymentMethod.ask.message";

		static final String WIDGET_MESSAGE_TRANSACTION_SFS_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.sfsTransaction.message.success";
		static final String WIDGET_MESSAGE_TRANSACTION_SFS_ERROR = "cscockpit.widget.transaction.transactionmanagement.sfsTransaction.message.error";
		static final String WIDGET_TRANSACTION_SFS_TITLE = "cscockpit.widget.transaction.transactionmanagement.sfsTransaction.title";

		static final String WIDGET_MESSAGE_PAYMENT_METHOD_NONCE_ERROR = "cscockpit.widget.transaction.transactionmanagement.nonce.message.error";
		static final String WIDGET_PAYMENT_METHOD_NONCE_TITLE = "cscockpit.widget.transaction.transactionmanagement.nonce.title";

		static final String WIDGET_MESSAGE_PAYMENT_METHOD_CREATE_SUCCESS = "cscockpit.widget.transaction.transactionmanagement.createPaymentMethod.message.success";
		static final String WIDGET_MESSAGE_PAYMENT_METHOD_CREATE_ERROR = "cscockpit.widget.transaction.transactionmanagement.createPaymentMethod.message.error";
		static final String WIDGET_PAYMENT_METHOD_CREATE_TITLE = "cscockpit.widget.transaction.transactionmanagement.createPaymentMethod.title";

		static final String WIDGET_MESSAGE_CUSTOMER_ADDRESS_CREATE_SUCCESS = "cscockpit.widget.customer.address.create.message.success";
		static final String WIDGET_CUSTOMER_ADDRESS_CREATE_TITLE = "cscockpit.widget.customer.address.create.title";
		static final String WIDGET_MESSAGE_CUSTOMER_ADDRESS_CREATE_ERROR = "cscockpit.widget.customer.address.create.message.error";

		static final String WIDGET_MESSAGE_ADDRESS_REMOVE_SUCCESS = "cscockpit.widget.customer.address.remove.message.success";
		static final String WIDGET_MESSAGE_ADDRESS_REMOVE_ERROR = "cscockpit.widget.customer.address.remove.message.error";
		static final String WIDGET_ADDRESS_REMOVE_TITLE = "cscockpit.widget.customer.address.remove.title";

		static final String WIDGET_REMOVE_ADDRESS_ASK_TITLE = "cscockpit.widget.transaction.transactionmanagement.removeAddress.ask.title";
		static final String WIDGET_REMOVE_ADDRESS_ASK_MESSAGE = "cscockpit.widget.transaction.transactionmanagement.removeAddress.ask.message";
	}

}
