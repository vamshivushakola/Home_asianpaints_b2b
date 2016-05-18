/**
 *
 */
package com.braintree.customer.service;

import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.customer.dao.BrainTreeCustomerAccountDao;
import com.braintree.model.BrainTreePaymentInfoModel;


public class BrainTreeCustomerAccountService extends DefaultCustomerAccountService
{
	private BrainTreeCustomerAccountDao brainTreeCustomerAccountDao;
	private BrainTreeConfigService brainTreeConfigService;

	public BrainTreePaymentInfoModel getBrainTreePaymentInfoForCode(final CustomerModel customer, final String code)
	{
		return brainTreeCustomerAccountDao.findBrainTreePaymentInfoByCustomer(customer, code);
	}

	public List<BrainTreePaymentInfoModel> getBrainTreePaymentInfos(final CustomerModel customerModel, final boolean saved)
	{
		return brainTreeCustomerAccountDao.findBrainTreePaymentInfosByCustomer(customerModel, saved,
				getBrainTreeConfigService().getMerchantAccountIdForCurrentSiteAndCurrency());
	}

	public void unlinkCCPaymentInfo(final CustomerModel customerModel, final BrainTreePaymentInfoModel brainTreePaymentInfo)
	{
		ServicesUtil.validateParameterNotNull(customerModel, "Customer model cannot be null");
		ServicesUtil.validateParameterNotNull(brainTreePaymentInfo, "BrainTreePaymentInfo model cannot be null");

		if (customerModel.getPaymentInfos().contains(brainTreePaymentInfo))
		{
			final Collection paymentInfoList = new ArrayList(customerModel.getPaymentInfos());
			paymentInfoList.remove(brainTreePaymentInfo);
			customerModel.setPaymentInfos(paymentInfoList);
			getModelService().save(customerModel);
			getModelService().refresh(customerModel);
		}
		else
		{
			throw new IllegalArgumentException("Credit Card Payment Info " + brainTreePaymentInfo
					+ " does not belong to the customer " + customerModel + " and will not be removed.");
		}
	}

	public BrainTreeCustomerAccountDao getBrainTreeCustomerAccountDao()
	{
		return brainTreeCustomerAccountDao;
	}

	public void setBrainTreeCustomerAccountDao(final BrainTreeCustomerAccountDao brainTreeCustomerAccountDao)
	{
		this.brainTreeCustomerAccountDao = brainTreeCustomerAccountDao;
	}

	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}

}
