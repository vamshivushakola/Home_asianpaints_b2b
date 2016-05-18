package com.braintree.cscockpit.data;


public class BrainTreePaymentMethodInfo extends BrainTreeInfo
{
	private boolean isDefaultPaymentMethod;
	private String newPaymentMethodToken;

	public boolean isDefaultPaymentMethod()
	{
		return isDefaultPaymentMethod;
	}

	public BrainTreePaymentMethodInfo setDefaultPaymentMethod(final boolean defaultPaymentMethod)
	{
		isDefaultPaymentMethod = defaultPaymentMethod;
		return this;
	}

	public String getNewPaymentMethodToken()
	{
		return newPaymentMethodToken;
	}

	public BrainTreePaymentMethodInfo setNewPaymentMethodToken(final String newPaymentMethodToken)
	{
		this.newPaymentMethodToken = newPaymentMethodToken;
		return this;
	}
}
