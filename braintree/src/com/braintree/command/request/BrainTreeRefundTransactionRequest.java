package com.braintree.command.request;

import java.math.BigDecimal;


public class BrainTreeRefundTransactionRequest extends BrainTreeAbstractRequest
{
	private String transactionId;
	private BigDecimal amount;

	public BrainTreeRefundTransactionRequest(final String merchantTransactionCode)
	{
		super(merchantTransactionCode);
	}

	public String getTransactionId()
	{
		return transactionId;
	}

	public void setTransactionId(final String transactionId)
	{
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(final BigDecimal amount)
	{
		this.amount = amount;
	}
}
