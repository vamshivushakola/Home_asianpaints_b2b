package com.braintree.cscockpit.widgets.controllers.impl;

import de.hybris.platform.cockpit.model.meta.TypedObject;
import de.hybris.platform.cscockpit.widgets.controllers.impl.DefaultCallContextController;

import com.braintree.cscockpit.widgets.controllers.BTCallContextController;


public class BTDefaultCallContextController extends DefaultCallContextController implements BTCallContextController
{
	private TypedObject activeTransaction;
	private TypedObject activeCustomer;
	private TypedObject activePaymentMethod;
	private TypedObject activeAddress;

	@Override
	public TypedObject getCurrentTransaction()
	{
		return this.activeTransaction;
	}

	@Override
	public boolean setCurrentTransaction(final TypedObject transaction)
	{
		boolean changed = false;
		if (!this.equals(this.activeTransaction, transaction))
		{
			this.activeTransaction = transaction;
			this.clearCurrentOrder();
			this.clearCurrentTicket();
			this.setImpersonationContextChanged();
			changed = true;
		}
		return changed;
	}

	@Override
	public void setImpersonationContextChanged()
	{
		super.setImpersonationContextChanged();
	}

	@Override
	public TypedObject getCurrentBTCustomer()
	{
		return activeCustomer;
	}

	@Override
	public boolean setCurrentBTCustomer(final TypedObject customer)
	{
		if (null == this.activeCustomer || !this.activeCustomer.equals(customer))
		{
			this.activeCustomer = customer;
		}
		this.setImpersonationContextChanged();
		return true;
	}

	@Override
	public TypedObject getCurrentPaymentMethod()
	{
		return activePaymentMethod;
	}

	@Override
	public boolean setCurrentPaymentMethod(final TypedObject paymentMethod)
	{
		boolean changed = false;
		if (null == this.activePaymentMethod || !this.activePaymentMethod.equals(paymentMethod))
		{
			this.activePaymentMethod = paymentMethod;
			this.setImpersonationContextChanged();
			changed = true;
		}
		return changed;
	}

	@Override
	public TypedObject getCurrentAddress()
	{
		return activeAddress;
	}

	@Override
	public boolean setCurrentAddress(final TypedObject address)
	{
		boolean changed = false;
		if (null == this.activeAddress || !this.activeAddress.equals(address))
		{
			this.activeAddress = address;
			this.setImpersonationContextChanged();
			changed = true;
		}
		return changed;
	}
}
