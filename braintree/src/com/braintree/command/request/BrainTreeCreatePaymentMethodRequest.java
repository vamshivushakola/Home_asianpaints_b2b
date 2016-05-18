/**
 *
 */
package com.braintree.command.request;

public class BrainTreeCreatePaymentMethodRequest extends BrainTreeAbstractRequest
{
	private String methodNonce;
	private String customerId;

	protected BrainTreeCreatePaymentMethodRequest(final String merchantTransactionCode)
	{
		super(merchantTransactionCode);
	}

	public BrainTreeCreatePaymentMethodRequest(final String merchantTransactionCode, final String methodNonce,
			final String customerId)
	{
		super(merchantTransactionCode);
		this.methodNonce = methodNonce;
		this.customerId = customerId;
	}

	public String getMethodNonce()
	{
		return methodNonce;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(final String customerId)
	{
		this.customerId = customerId;
	}

	public void setMethodNonce(final String methodNonce)
	{
		this.methodNonce = methodNonce;
	}

}
