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
 * Global class for all Braintree constants. You can add global constants for your extension into this class.
 */
public final class BraintreeConstants extends GeneratedBraintreeConstants
{
	public static final String EXTENSIONNAME = "braintree";

	private BraintreeConstants()
	{
		//empty to avoid instantiating this constant class
	}

	public static final String BRAINTREE_PRIVATE_KEY = "braintree.private_key";

	public static final String BRAINTREE_PUBLIC_KEY = "braintree.public_key";

	public static final String BRAINTREE_MERCHANT_ID = "braintree.merchant_id";

	public static final String SINGLE_USE_PARAMETER = "braintree.single.use";

	public static final String SETTLEMENT_DALAY = "braintree.settlement.delay";

	public static final String LOG_ALL_ENABLE = "braintree.log.all.enable";

	public static final String LOG_PACKAGE_PATH = "com.braintree";

	public static final String BRAINTREE_PROVIDER_NAME = "BrainTree";

	public static final String CARD_NUMBER_MASK = "**************%s";

	public static final String BRAINTREE_ADVANCED_FRAUD_TOOLS_ENABLED = "braintree.advanced.fraud.tools.enabled";

	public static final String BRAINTREE_ENVIRONMENT = "braintree.environment.type";

	public static final String ENVIRONMENT_SANDBOX = "sandbox";

	public static final String ENVIRONMENT_PRODACTION = "prodaction";

	public static final String BRAINTREE_3D_SECURE = "braintree.3d.secure";

	public static final String BRAINTREE_KOUNT_ID = "braintree.kount.id";

	public static final String BRAINTREE_PAYMENT = "CreditCard";

	public static final String PAYPAL_PAYMENT = "PayPalAccount";

	public static final String PAY_PAL_EXPRESS_CHECKOUT = "BrainTreePayPalExpress";

	public static final String ON_SUCCESS = "onSuccess";

	public static final String HOSTED_FIELDS_ENABLED = "braintree.hosted.fields.enabled";

	public static final String PAYPAL_EXPRESS_ENABLED = "braintree.express.paypal.enabled";

	public static final String PAYPAL_STANDARD_ENABLED = "braintree.standard.paypal.enabled";

	public static final String IS_SKIP_3D_SECURE_LIABILITY_RESULT = "braintree.skip.3dsecure.liability.result";

	public static final String BRAINTREE_CREDIT_CARD_STATEMENT_NAME = "braintree.credit.card.statement.name";

	public static final String BRAINTREE_CHANNEL = "Hybris_BT";

	public static final String BRAINTREE_MERCHANT_ACCOUNT_IDS = "braintree.merchant.account.ids";

	public static final String BRAINTREE_ACCEPTED_PAYMENT_METHODS = "braintree.accepted.payment.methods";

	public static final String STORE_IN_VAULT = "braintree.store.in.vault";

}
