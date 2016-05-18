package com.braintree.command.result;

import java.math.BigDecimal;


public class BrainTreeRefundTransactionResult extends BrainTreeAbstractTransactionResult
{
	private BigDecimal amount;

	public BrainTreeRefundTransactionResult(final boolean success)
	{
		setSuccess(success);
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
