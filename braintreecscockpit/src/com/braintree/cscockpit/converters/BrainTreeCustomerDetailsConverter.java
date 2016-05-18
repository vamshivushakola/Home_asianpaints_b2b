/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.braintree.cscockpit.converters;

import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.ArrayList;
import java.util.List;

import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.model.BraintreeCustomerDetailsModel;
import com.braintreegateway.Address;
import com.braintreegateway.Customer;
import com.braintreegateway.PaymentMethod;


public class BrainTreeCustomerDetailsConverter extends AbstractPopulatingConverter<Customer, BraintreeCustomerDetailsModel>
{
	private BrainTreeAddressConverter brainTreeAddressConverter;
	private BrainTreePaymentMethodConverter paymentMethodConverter;

	@Override
	public void populate(final Customer customer, final BraintreeCustomerDetailsModel model)
	{
		if (customer != null && model != null)
		{
			model.setId(customer.getId());
			model.setCompany(customer.getCompany());
			model.setCreatedAt(customer.getCreatedAt().getTime());
			model.setEmail(customer.getEmail());
			model.setFax(customer.getFax());
			model.setFirstName(customer.getFirstName());
			model.setLastName(customer.getLastName());
			if (customer.getUpdatedAt() != null)
			{
				model.setModifiedtime(customer.getUpdatedAt().getTime());
			}
			model.setPhone(customer.getPhone());
			model.setWebsite(customer.getWebsite());
			if (null != customer.getAddresses())
			{
				final List<AddressModel> addresses = new ArrayList<>(customer.getAddresses().size());
				for (final Address address : customer.getAddresses())
				{
					addresses.add(getBrainTreeAddressConverter().convert(address));
				}
				model.setAddresses(addresses);
			}
			populateCustomerPaymentMethods(customer, model);
		}
	}

	private void populateCustomerPaymentMethods(final Customer customer, final BraintreeCustomerDetailsModel model)
	{
		final List<BrainTreePaymentInfoModel> paymentMethodList = new ArrayList<>();
		paymentMethodList.addAll(getBraintreePaymentMethodsFromPaymentMethodsList(customer.getCreditCards()));
		paymentMethodList.addAll(getBraintreePaymentMethodsFromPaymentMethodsList(customer.getPayPalAccounts()));
		paymentMethodList.addAll(getBraintreePaymentMethodsFromPaymentMethodsList(customer.getApplePayCards()));
		paymentMethodList.addAll(getBraintreePaymentMethodsFromPaymentMethodsList(customer.getAndroidPayCards()));
		paymentMethodList.addAll(getBraintreePaymentMethodsFromPaymentMethodsList(customer.getAmexExpressCheckoutCards()));
		model.setPaymentMethods(paymentMethodList);
	}

	private List<BrainTreePaymentInfoModel> getBraintreePaymentMethodsFromPaymentMethodsList(
			final List<? extends PaymentMethod> paymentMethods)
	{
		final List<BrainTreePaymentInfoModel> btPaymentMethods = new ArrayList<>((null != paymentMethods) ? paymentMethods.size()
				: 0);
		if (null != paymentMethods)
		{
			for (final PaymentMethod pm : paymentMethods)
			{
				btPaymentMethods.add(getPaymentMethodConverter().convert(pm));
			}
		}
		return btPaymentMethods;
	}

	/**
	 * @return the addressPopulator
	 */
	public BrainTreeAddressConverter getBrainTreeAddressConverter()
	{
		return brainTreeAddressConverter;
	}

	/**
	 * @param brainTreeAddressConverter
	 *           the addressPopulator to set
	 */
	public void setBrainTreeAddressConverter(final BrainTreeAddressConverter brainTreeAddressConverter)
	{
		this.brainTreeAddressConverter = brainTreeAddressConverter;
	}

	public BrainTreePaymentMethodConverter getPaymentMethodConverter()
	{
		return paymentMethodConverter;
	}

	public void setPaymentMethodConverter(final BrainTreePaymentMethodConverter paymentMethodConverter)
	{
		this.paymentMethodConverter = paymentMethodConverter;
	}
}
