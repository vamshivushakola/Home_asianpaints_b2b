/**
 *
 */
package com.braintree.facade.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.impl.DefaultUserFacade;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.braintree.command.request.BrainTreeAddressRequest;
import com.braintree.command.result.BrainTreeAddressResult;
import com.braintree.customer.service.BrainTreeCustomerAccountService;
import com.braintree.method.BrainTreePaymentService;
import com.braintree.model.BrainTreePaymentInfoModel;
import com.google.common.collect.Lists;


public class BrainTreeUserFacade extends DefaultUserFacade
{

	private Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> brainTreePaymentInfoConverter;
	private BrainTreeCustomerAccountService brainTreeCustomerAccountService;
	private BrainTreePaymentService brainTreePaymentService;
	private CheckoutCustomerStrategy checkoutCustomerStrategy;

	@Override
	public void addAddress(final AddressData addressData)
	{
		final BrainTreeAddressRequest addressRequest = convertBrainTreeAddress(addressData);
		final BrainTreeAddressResult brainTreeAddress = brainTreePaymentService.createAddress(addressRequest);

		validateParameterNotNullStandardMessage("addressData", addressData);

		final CustomerModel currentCustomer = getCurrentUserForCheckout();

		final boolean makeThisAddressTheDefault = addressData.isDefaultAddress()
				|| (currentCustomer.getDefaultShipmentAddress() == null && addressData.isVisibleInAddressBook());

		// Create the new address model
		final AddressModel newAddress = getModelService().create(AddressModel.class);
		getAddressReversePopulator().populate(addressData, newAddress);
		if (brainTreeAddress != null)
		{
			newAddress.setBrainTreeAddressId(brainTreeAddress.getAddress().getId());
		}

		// Store the address against the user
		getCustomerAccountService().saveAddressEntry(currentCustomer, newAddress);

		// Update the address ID in the newly created address
		addressData.setId(newAddress.getPk().toString());

		if (makeThisAddressTheDefault)
		{
			getCustomerAccountService().setDefaultAddressEntry(currentCustomer, newAddress);
		}
	}

	@Override
	public void editAddress(final AddressData addressData)
	{
		final String brainTreeAddressId = getBrainTreeAddressId(addressData);

		if (StringUtils.isNotEmpty(brainTreeAddressId))
		{
			final BrainTreeAddressRequest addressRequest = convertBrainTreeAddress(addressData);
			addressRequest.setAddressId(brainTreeAddressId);
			brainTreePaymentService.updateAddress(addressRequest);
		}

		super.editAddress(addressData);
	}

	@Override
	public void removeAddress(final AddressData addressData)
	{
		final String brainTreeAddressId = getBrainTreeAddressId(addressData);

		if (StringUtils.isNotEmpty(brainTreeAddressId))
		{
			final BrainTreeAddressRequest addressRequest = convertBrainTreeAddress(addressData);
			addressRequest.setAddressId(brainTreeAddressId);
			brainTreePaymentService.removeAddress(addressRequest);
		}

		super.removeAddress(addressData);
	}

	private String getBrainTreeAddressId(final AddressData addressData)
	{
		validateParameterNotNullStandardMessage("addressData", addressData);
		final CustomerModel currentCustomer = getCurrentUserForCheckout();
		final AddressModel addressModel = getCustomerAccountService().getAddressForCode(currentCustomer, addressData.getId());
		return addressModel.getBrainTreeAddressId();
	}

	private BrainTreeAddressRequest convertBrainTreeAddress(final AddressData address)
	{
		final String customerID = checkoutCustomerStrategy.getCurrentUserForCheckout().getBraintreeCustomerId();
		final BrainTreeAddressRequest addressRequest = new BrainTreeAddressRequest(customerID);
		addressRequest.setCompany(address.getTitle());
		addressRequest.setStreetAddress(address.getLine1());
		addressRequest.setExtendedAddress(address.getLine2());
		addressRequest.setFirstName(address.getFirstName());
		addressRequest.setLastName(address.getLastName());
		addressRequest.setLocality(address.getTown());
		addressRequest.setPostalCode(address.getPostalCode());

		if (address.getCountry() != null)
		{
			addressRequest.setCountryCodeAlpha2(address.getCountry().getIsocode());
		}
		if (address.getRegion() != null)
		{
			addressRequest.setRegion(address.getRegion().getIsocode());
		}


		return addressRequest;
	}

	public List<CCPaymentInfoData> getBrainTreeCCPaymentInfos(final boolean saved)
	{
		final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
		final Collection<BrainTreePaymentInfoModel> paymentInfos = brainTreeCustomerAccountService
				.getBrainTreePaymentInfos(currentCustomer, saved);

		final List<BrainTreePaymentInfoModel> creditCards = Lists.newArrayList();

		for (final PaymentInfoModel paymentInfoModel : paymentInfos)
		{
			if (paymentInfoModel instanceof BrainTreePaymentInfoModel)
			{
				creditCards.add((BrainTreePaymentInfoModel) paymentInfoModel);
			}
		}

		final List<CCPaymentInfoData> ccPaymentInfos = new ArrayList<CCPaymentInfoData>();
		final PaymentInfoModel defaultPaymentInfoModel = currentCustomer.getDefaultPaymentInfo();

		for (final BrainTreePaymentInfoModel ccPaymentInfoModel : creditCards)
		{
			final CCPaymentInfoData defaultPaymentInfoData = getBrainTreePaymentInfoConverter().convert(ccPaymentInfoModel);
			if (ccPaymentInfoModel.equals(defaultPaymentInfoModel))
			{
				defaultPaymentInfoData.setDefaultPaymentInfo(true);
			}
			ccPaymentInfos.add(defaultPaymentInfoData);
		}
		return ccPaymentInfos;
	}

	@Override
	public void unlinkCCPaymentInfo(final String id)
	{
		validateParameterNotNullStandardMessage("id", id);
		final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();

		final BrainTreePaymentInfoModel brainTreePaymentInfo = brainTreeCustomerAccountService
				.getBrainTreePaymentInfoForCode(currentCustomer, id);

		brainTreeCustomerAccountService.unlinkCCPaymentInfo(currentCustomer, brainTreePaymentInfo);

	}

	@Override
	public CCPaymentInfoData getCCPaymentInfoForCode(final String code)
	{
		if (StringUtils.isNotBlank(code))
		{
			final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
			final BrainTreePaymentInfoModel ccPaymentInfoModel = brainTreeCustomerAccountService
					.getBrainTreePaymentInfoForCode(currentCustomer, code);
			if (ccPaymentInfoModel != null)
			{
				final PaymentInfoModel defaultPaymentInfoModel = currentCustomer.getDefaultPaymentInfo();
				final CCPaymentInfoData paymentInfoData = getBrainTreePaymentInfoConverter().convert(ccPaymentInfoModel);
				if (ccPaymentInfoModel.equals(defaultPaymentInfoModel))
				{
					paymentInfoData.setDefaultPaymentInfo(true);
				}
				return paymentInfoData;
			}
		}

		return null;
	}

	@Override
	public void setDefaultPaymentInfo(final CCPaymentInfoData paymentInfoData)
	{
		validateParameterNotNullStandardMessage("paymentInfoData", paymentInfoData);
		final CustomerModel currentCustomer = getCurrentUserForCheckout();
		final BrainTreePaymentInfoModel ccPaymentInfoModel = brainTreeCustomerAccountService
				.getBrainTreePaymentInfoForCode(currentCustomer, paymentInfoData.getId());
		if (ccPaymentInfoModel != null)
		{
			getCustomerAccountService().setDefaultPaymentInfo(currentCustomer, ccPaymentInfoModel);
		}
	}

	/**
	 * @return the checkoutCustomerStrategy
	 */
	@Override
	public CheckoutCustomerStrategy getCheckoutCustomerStrategy()
	{
		return checkoutCustomerStrategy;
	}

	/**
	 * @param checkoutCustomerStrategy
	 *           the checkoutCustomerStrategy to set
	 */
	@Override
	public void setCheckoutCustomerStrategy(final CheckoutCustomerStrategy checkoutCustomerStrategy)
	{
		this.checkoutCustomerStrategy = checkoutCustomerStrategy;
	}

	/**
	 * @return the brainTreePaymentService
	 */
	public BrainTreePaymentService getBrainTreePaymentService()
	{
		return brainTreePaymentService;
	}

	/**
	 * @param brainTreePaymentService
	 *           the brainTreePaymentService to set
	 */
	public void setBrainTreePaymentService(final BrainTreePaymentService brainTreePaymentService)
	{
		this.brainTreePaymentService = brainTreePaymentService;
	}

	public Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> getBrainTreePaymentInfoConverter()
	{
		return brainTreePaymentInfoConverter;
	}

	public void setBrainTreePaymentInfoConverter(
			final Converter<BrainTreePaymentInfoModel, CCPaymentInfoData> brainTreePaymentInfoConverter)
	{
		this.brainTreePaymentInfoConverter = brainTreePaymentInfoConverter;
	}

	/**
	 * @return the brainTreeCustomerAccountService
	 */
	public BrainTreeCustomerAccountService getBrainTreeCustomerAccountService()
	{
		return brainTreeCustomerAccountService;
	}

	/**
	 * @param brainTreeCustomerAccountService
	 *           the brainTreeCustomerAccountService to set
	 */
	public void setBrainTreeCustomerAccountService(final BrainTreeCustomerAccountService brainTreeCustomerAccountService)
	{
		this.brainTreeCustomerAccountService = brainTreeCustomerAccountService;
	}


}
