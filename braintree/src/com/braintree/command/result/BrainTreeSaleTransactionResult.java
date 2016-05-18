package com.braintree.command.result;


public class BrainTreeSaleTransactionResult extends BrainTreeAbstractTransactionResult
{

	public BrainTreeSaleTransactionResult(final boolean success)
	{
		this.success = success;
	}

	@Override
	public String getTransactionId()
	{
		if (super.getTransactionId() == null && getTransaction() != null)
		{
			return getTransaction().getId();
		}
		return super.getTransactionId();
	}
}
