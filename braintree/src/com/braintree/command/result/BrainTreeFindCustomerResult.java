/**
 *
 */
package com.braintree.command.result;

public class BrainTreeFindCustomerResult extends BrainTreeAbstractResult
{
	private boolean isCustomerExist;


	public BrainTreeFindCustomerResult()
	{
		super();
	}

	public BrainTreeFindCustomerResult(final boolean isCustomerExist)
	{
		super();
		this.isCustomerExist = isCustomerExist;
	}

	public boolean isCustomerExist()
	{
		return isCustomerExist;
	}

	public void setCustomerExist(final boolean isCustomerExist)
	{
		this.isCustomerExist = isCustomerExist;
	}

}
