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
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import org.apache.commons.lang.StringUtils;

import com.braintreegateway.Address;


public class BrainTreeAddressConverter extends AbstractPopulatingConverter<Address, AddressModel>
{
	private CommonI18NService commonI18NService;

	@Override
	public void populate(final Address address, final AddressModel model)
	{
		if (address != null && model != null)
		{
			final CountryModel country = findCountry(address.getCountryCodeAlpha2(), address.getCountryCodeAlpha3());
			model.setCountry(country);
			model.setRegion(findRegion(country, address.getRegion()));
			model.setZone(address.getRegion());
			model.setStreetname(address.getStreetAddress());
			model.setStreetnumber(address.getExtendedAddress());
			model.setBrainTreeAddressId(address.getId());
			model.setCompany(address.getCompany());
			model.setFirstname(address.getFirstName());
			model.setLastname(address.getLastName());
			model.setTown(address.getLocality());
			model.setPostalcode(address.getPostalCode());
		}
	}

	private CountryModel findCountry(final String countryCodeAlpha2, final String countryCodeAlpha3)
	{
		CountryModel country = getCommonI18NService().getCountry(countryCodeAlpha2);
		if (country == null)
		{
			country = getCommonI18NService().getCountry(countryCodeAlpha3);
		}
		return country;
	}

	private RegionModel findRegion(final CountryModel countryModel, final String regionName)
	{
		if (countryModel != null && StringUtils.isNotBlank(regionName))
		{
			try
			{
				return getCommonI18NService().getRegion(countryModel, regionName);
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				return findRegionByCanonicalIsoCode(countryModel, regionName);
			}
		}
		return findRegionByName(countryModel, regionName);
	}


	private RegionModel findRegionByCanonicalIsoCode(final CountryModel countryModel, final String regionName)
	{
		try
		{
			final String canonicalIsoCode = countryModel.getIsocode() + "-" + regionName;
			return getCommonI18NService().getRegion(countryModel, canonicalIsoCode);
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			return findRegionByName(countryModel, regionName);
		}
	}

	private RegionModel findRegionByName(final CountryModel countryModel, final String regionName)
	{
		if (StringUtils.isNotBlank(regionName))
		{
			for (final RegionModel regionModel : countryModel.getRegions())
			{
				if (regionModel != null && regionName.equals(regionModel.getName()))
				{
					return regionModel;
				}
			}
		}
		return null;
	}


	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
}
