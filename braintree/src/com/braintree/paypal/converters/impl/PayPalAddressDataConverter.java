/**
 *
 */
package com.braintree.paypal.converters.impl;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.braintree.hybris.data.PayPalAddressData;
import com.braintree.paypal.validator.PayPalAddressDetailValidator;


public class PayPalAddressDataConverter implements Converter<AddressModel, PayPalAddressData>
{
	PayPalAddressDetailValidator payPalAddressDetailValidator;

	@Override
	public PayPalAddressData convert(final AddressModel hybrisAddress) throws ConversionException
	{
		final PayPalAddressData payPalAddressData = new PayPalAddressData();
		return convert(hybrisAddress, payPalAddressData);
	}

	@Override
	public PayPalAddressData convert(final AddressModel hybrisAddress, final PayPalAddressData paypalAddress)
			throws ConversionException
	{
		paypalAddress.setLocality(hybrisAddress.getTown());
		paypalAddress.setPhone(hybrisAddress.getPhone1());
		paypalAddress.setPostalCode(hybrisAddress.getPostalcode());
		paypalAddress.setRecipientName(hybrisAddress.getFirstname() + " " + hybrisAddress.getLastname());
		paypalAddress.setStreetAddress(hybrisAddress.getLine1());
		paypalAddress.setExtendedAddress(hybrisAddress.getLine2());
		paypalAddress.setType("Personal");
		if (hybrisAddress.getRegion() != null)
		{
			paypalAddress.setRegion(hybrisAddress.getRegion().getIsocodeShort());
		}
		if (hybrisAddress.getCountry() != null)
		{
			paypalAddress.setCountryCodeAlpha2(hybrisAddress.getCountry().getIsocode());
		}
		return paypalAddress;
	}

	public AddressData convert(final PayPalAddressData address)
	{
		final AddressData addressData = new AddressData();
		final boolean isCountryIsoCodeValid = payPalAddressDetailValidator
				.validatePayPalCountryCode(address.getCountryCodeAlpha2());
		final boolean isRegionIsoCodeValid = payPalAddressDetailValidator.validatePayPalRegionCode(address.getCountryCodeAlpha2(),
				address.getRegion());
		if (isCountryIsoCodeValid)
		{
			final CountryData country = new CountryData();
			country.setIsocode(address.getCountryCodeAlpha2());
			addressData.setCountry(country);
		}

		if (isRegionIsoCodeValid)
		{
			final RegionData region = new RegionData();
			region.setIsocode(address.getCountryCodeAlpha2() + "-" + address.getRegion());
			addressData.setRegion(region);
		}

		addressData.setLine1(address.getStreetAddress());
		addressData.setLine2(address.getExtendedAddress());
		addressData.setPhone(address.getPhone());
		addressData.setPostalCode(address.getPostalCode());
		addressData.setFirstName(address.getRecipientName());
		addressData.setTitle(address.getLocality());

		return addressData;
	}


	public PayPalAddressDetailValidator getPayPalAddressDetailValidator()
	{
		return payPalAddressDetailValidator;
	}


	public void setPayPalAddressDetailValidator(final PayPalAddressDetailValidator payPalAddressDetailValidator)
	{
		this.payPalAddressDetailValidator = payPalAddressDetailValidator;
	}

}
