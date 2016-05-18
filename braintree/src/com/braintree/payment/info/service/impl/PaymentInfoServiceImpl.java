package com.braintree.payment.info.service.impl;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.UUID;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.customer.dao.BrainTreeCustomerAccountDao;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.braintree.payment.info.dao.BrainTreePaymentInfoDao;
import com.braintree.payment.info.service.PaymentInfoService;


public class PaymentInfoServiceImpl implements PaymentInfoService
{
	private BrainTreePaymentInfoDao brainTreePaymentInfoDao;
	private BrainTreeCustomerAccountDao brainTreeCustomerAccountDao;
	private ModelService modelService;
	private BrainTreeConfigService brainTreeConfigService;

	@Override
	public void remove(final String customerId, final String paymentMethodToken)
	{
		final BrainTreePaymentInfoModel brainTreePaymentInfoModel = getBrainTreePaymentInfoDao().find(customerId,
				paymentMethodToken);
		if (brainTreePaymentInfoModel != null)
		{
			getModelService().remove(brainTreePaymentInfoModel);
		}
	}

	@Override
	public void addToCustomer(final BrainTreePaymentInfoModel paymentInfo)
	{
		ServicesUtil.validateParameterNotNull(paymentInfo, "paymentInfo must not be null");
		final CustomerModel customerModel = getBrainTreeCustomerAccountDao().findCustomerByBrainTreeCustomerId(
				paymentInfo.getCustomerId());
		if (customerModel != null)
		{
			paymentInfo.setUser(customerModel);
			paymentInfo.setCode(customerModel.getUid() + "_" + UUID.randomUUID());
			paymentInfo.setSaved(Boolean.TRUE.booleanValue());
			paymentInfo.setUsePaymentMethodToken(Boolean.TRUE);
			paymentInfo.setThreeDSecureConfiguration(getBrainTreeConfigService().get3dSecureConfiguration());
			paymentInfo.setAdvancedFraudTools(getBrainTreeConfigService().getAdvancedFraudTools());
			paymentInfo.setIsSkip3dSecureLiabilityResult(getBrainTreeConfigService().getIsSkip3dSecureLiabilityResult());
			paymentInfo.setCreditCardStatementName(getBrainTreeConfigService().getCreditCardStatementName());
			paymentInfo.setMerchantAccountIdForCurrentSite(getBrainTreeConfigService()
					.getMerchantAccountIdForCurrentSiteAndCurrency());
			paymentInfo.setBrainTreeChannel(getBrainTreeConfigService().getBrainTreeChannel());

			if (paymentInfo.getBillingAddress() != null)
			{
				final AddressModel billingAddress = paymentInfo.getBillingAddress();
				billingAddress.setOwner(paymentInfo);
				getModelService().save(billingAddress);
			}
			getModelService().save(paymentInfo);
			getModelService().refresh(customerModel);
		}
	}

	@Override
	public void update(final String paymentMethodToken, final BrainTreePaymentInfoModel paymentInfo)
	{
		ServicesUtil.validateParameterNotNull(paymentInfo, "paymentInfo must not be null");
		final BrainTreePaymentInfoModel brainTreePaymentInfoModel = getBrainTreePaymentInfoDao().find(paymentInfo.getCustomerId(),
				paymentMethodToken);

		if (brainTreePaymentInfoModel != null)
		{
			brainTreePaymentInfoModel.setPaymentMethodToken(paymentInfo.getPaymentMethodToken());
			brainTreePaymentInfoModel.setCardholderName(paymentInfo.getCardholderName());
			brainTreePaymentInfoModel.setCardNumber(paymentInfo.getCardNumber());
			brainTreePaymentInfoModel.setExpirationMonth(paymentInfo.getExpirationMonth());
			brainTreePaymentInfoModel.setExpirationYear(paymentInfo.getExpirationYear());
			if (paymentInfo.getBillingAddress() != null)
			{
				final AddressModel billingAddress = paymentInfo.getBillingAddress();
				billingAddress.setOwner(brainTreePaymentInfoModel);
				brainTreePaymentInfoModel.setBillingAddress(billingAddress);
				getModelService().save(billingAddress);
			}
			getModelService().save(brainTreePaymentInfoModel);
			getModelService().refresh(brainTreePaymentInfoModel);
		}
	}

	public BrainTreePaymentInfoDao getBrainTreePaymentInfoDao()
	{
		return brainTreePaymentInfoDao;
	}

	public void setBrainTreePaymentInfoDao(final BrainTreePaymentInfoDao brainTreePaymentInfoDao)
	{
		this.brainTreePaymentInfoDao = brainTreePaymentInfoDao;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
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
