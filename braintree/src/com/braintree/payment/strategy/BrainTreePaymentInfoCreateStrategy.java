package com.braintree.payment.strategy;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.CustomerEmailResolutionService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.enums.BrainTreeCardType;
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.model.BrainTreePaymentInfoModel;


public class BrainTreePaymentInfoCreateStrategy
{
	private final static Logger LOG = Logger.getLogger(BrainTreePaymentInfoCreateStrategy.class);

	private ModelService modelService;
	private CommonI18NService commonI18NService;
	private CustomerEmailResolutionService customerEmailResolutionService;
	private UserService userService;
	private BrainTreeConfigService brainTreeConfigService;

	public BrainTreePaymentInfoModel saveSubscription(final AddressData addressData, final CustomerModel customer,
			final BrainTreeSubscriptionInfoData brainTreeSubscriptionInfoData)
	{
		validateParameterNotNull(brainTreeSubscriptionInfoData.getNonce(), "nonce cannot be null");
		validateParameterNotNull(addressData, "addressData cannot be null");
		validateParameterNotNull(customer, "customer cannot be null");

		final AddressModel billingAddress = modelService.create(AddressModel.class);
		billingAddress.setFirstname(addressData.getFirstName());
		billingAddress.setLastname(addressData.getLastName());
		billingAddress.setLine1(addressData.getLine1());
		billingAddress.setLine2(addressData.getLine2());
		billingAddress.setTown(addressData.getTown());
		billingAddress.setPostalcode(addressData.getPostalCode());
		billingAddress.setBrainTreeAddressId(addressData.getBrainTreeAddressId());
		if (isNotEmpty(addressData.getEmail()))
		{
			billingAddress.setEmail(addressData.getEmail());
		}
		else
		{
			billingAddress.setEmail(customerEmailResolutionService.getEmailForCustomer(customer));
		}

		if (addressData.getCountry() != null)
		{
			final CountryModel country = commonI18NService.getCountry(addressData.getCountry().getIsocode());
			billingAddress.setCountry(country);
			if (addressData.getRegion() != null)
			{
				final RegionModel region = commonI18NService.getRegion(country, addressData.getRegion().getIsocode());
				billingAddress.setRegion(region);
			}
		}

		final BrainTreePaymentInfoModel cardPaymentInfoModel = createCreditCardPaymentInfo(billingAddress, customer,
				brainTreeSubscriptionInfoData);

		billingAddress.setOwner(cardPaymentInfoModel);

		modelService.saveAll(cardPaymentInfoModel, billingAddress);
		modelService.refresh(customer);

		LOG.info("[BT PaymentInfo] Created card payment info with id: " + cardPaymentInfoModel.getCode());
		LOG.info("[BT PaymentInfo] Created billing address with id: " + billingAddress.getPk());

		final List<PaymentInfoModel> paymentInfoModels = new ArrayList<PaymentInfoModel>(customer.getPaymentInfos());
		if (!paymentInfoModels.contains(cardPaymentInfoModel))
		{
			paymentInfoModels.add(cardPaymentInfoModel);
			if (brainTreeSubscriptionInfoData.isSavePaymentInfo())
			{
				customer.setPaymentInfos(paymentInfoModels);
				getModelService().save(customer);
			}

			getModelService().save(cardPaymentInfoModel);
			getModelService().refresh(customer);
		}

		return cardPaymentInfoModel;
	}

	private BrainTreePaymentInfoModel createCreditCardPaymentInfo(final AddressModel billingAddress,
			final CustomerModel customerModel, final BrainTreeSubscriptionInfoData brainTreeSubscriptionInfoData)
	{
		validateParameterNotNull(billingAddress, "billingAddress cannot be null");

		final BrainTreePaymentInfoModel cardPaymentInfoModel = modelService.create(BrainTreePaymentInfoModel.class);

		billingAddress.setOwner(cardPaymentInfoModel);
		cardPaymentInfoModel.setBillingAddress(billingAddress);
		if (userService.isAnonymousUser(userService.getCurrentUser()))
		{
			cardPaymentInfoModel.setCode(customerModel.getUid());
		}
		else
		{
			cardPaymentInfoModel.setCode(customerModel.getUid() + "_" + UUID.randomUUID());
		}
		cardPaymentInfoModel.setUser(customerModel);
		cardPaymentInfoModel.setPaymentMethodToken(brainTreeSubscriptionInfoData.getPaymentMethodToken());
		cardPaymentInfoModel.setNonce(brainTreeSubscriptionInfoData.getNonce());
		cardPaymentInfoModel.setDeviceData(brainTreeSubscriptionInfoData.getDeviceData());
		cardPaymentInfoModel.setImageSource(brainTreeSubscriptionInfoData.getImageSource());
		cardPaymentInfoModel.setExpirationMonth(brainTreeSubscriptionInfoData.getExpirationMonth());
		cardPaymentInfoModel.setExpirationYear(brainTreeSubscriptionInfoData.getExpirationYear());
		if (StringUtils.isNotEmpty(customerModel.getBraintreeCustomerId()))
		{
			cardPaymentInfoModel.setCustomerId(customerModel.getBraintreeCustomerId());
		}
		if (brainTreeSubscriptionInfoData.getLiabilityShifted() != null)
		{
			cardPaymentInfoModel.setLiabilityShifted(brainTreeSubscriptionInfoData.getLiabilityShifted());
		}
		cardPaymentInfoModel.setPaymentProvider(brainTreeSubscriptionInfoData.getPaymentProvider());
		cardPaymentInfoModel.setSaved(brainTreeSubscriptionInfoData.isSavePaymentInfo());
		cardPaymentInfoModel.setCardNumber(brainTreeSubscriptionInfoData.getCardNumber());
		if (isNotEmpty(brainTreeSubscriptionInfoData.getCardType()))
		{
			final String brainTreeCardType = brainTreeSubscriptionInfoData.getCardType().replace("_", " ");
			cardPaymentInfoModel.setCardType(getBrainTreeCardTypeByName(brainTreeCardType));
		}
		cardPaymentInfoModel.setThreeDSecureConfiguration(getBrainTreeConfigService().get3dSecureConfiguration());
		cardPaymentInfoModel.setAdvancedFraudTools(getBrainTreeConfigService().getAdvancedFraudTools());
		cardPaymentInfoModel.setIsSkip3dSecureLiabilityResult(getBrainTreeConfigService().getIsSkip3dSecureLiabilityResult());
		cardPaymentInfoModel.setCreditCardStatementName(getBrainTreeConfigService().getCreditCardStatementName());
		cardPaymentInfoModel
				.setMerchantAccountIdForCurrentSite(getBrainTreeConfigService().getMerchantAccountIdForCurrentSiteAndCurrency());
		cardPaymentInfoModel.setBrainTreeChannel(getBrainTreeConfigService().getBrainTreeChannel());

		return cardPaymentInfoModel;
	}

	protected BrainTreeCardType getBrainTreeCardTypeByName(final String brainTreeCardType)
	{
		return BrainTreeCardType.valueOf(brainTreeCardType);
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the customerEmailResolutionService
	 */
	public CustomerEmailResolutionService getCustomerEmailResolutionService()
	{
		return customerEmailResolutionService;
	}

	/**
	 * @param customerEmailResolutionService
	 *           the customerEmailResolutionService to set
	 */
	public void setCustomerEmailResolutionService(final CustomerEmailResolutionService customerEmailResolutionService)
	{
		this.customerEmailResolutionService = customerEmailResolutionService;
	}

	/**
	 * @return the brainTreeConfigService
	 */
	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	/**
	 * @param brainTreeConfigService
	 *           the brainTreeConfigService to set
	 */
	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}

}
