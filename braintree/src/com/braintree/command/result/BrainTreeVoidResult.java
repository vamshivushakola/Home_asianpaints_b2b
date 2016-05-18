package com.braintree.command.result;

import java.math.BigDecimal;
import java.util.Currency;


public class BrainTreeVoidResult extends BrainTreeAbstractTransactionResult
{
	private BigDecimal amount;
	private Currency currency;

	public BrainTreeVoidResult(final boolean success)
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

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(final Currency currency)
	{
		this.currency = currency;
	}
}
