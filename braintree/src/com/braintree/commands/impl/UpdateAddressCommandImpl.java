/**
 *
 */
package com.braintree.commands.impl;

import com.braintree.command.request.BrainTreeAddressRequest;
import com.braintree.command.result.BrainTreeAddressResult;
import com.braintree.commands.BrainTreeUpdateAddressCommand;
import com.braintreegateway.Address;
import com.braintreegateway.AddressRequest;
import com.braintreegateway.Result;


public class UpdateAddressCommandImpl extends AbstractCommand implements BrainTreeUpdateAddressCommand
{

	@Override
	public BrainTreeAddressResult perform(final BrainTreeAddressRequest addressRequest)
	{
		final AddressRequest request = translateRequest(addressRequest);
		getLoggingHandler().handleAddressRequest("[UPDATE BRAINTREE ADDRESS REQUEST]", addressRequest);
		final Result<Address> result = getBraintreeGateway().address().update(addressRequest.getCustomerId(),
				addressRequest.getAddressId(), request);

		return translateResult(result);

	}

	private AddressRequest translateRequest(final BrainTreeAddressRequest addressRequest)
	{
		final AddressRequest request = new AddressRequest().firstName(addressRequest.getFirstName())
				.lastName(addressRequest.getLastName()).company(addressRequest.getCompany())
				.streetAddress(addressRequest.getStreetAddress()).extendedAddress(addressRequest.getExtendedAddress())
				.locality(addressRequest.getLocality()).region(addressRequest.getRegion()).postalCode(addressRequest.getPostalCode())
				.countryCodeAlpha2(addressRequest.getCountryCodeAlpha2());

		return request;
	}

	private BrainTreeAddressResult translateResult(final Result<Address> result)
	{
		if (result.getErrors() != null)
		{
			getLoggingHandler().handleErrors(result.getErrors().getAllDeepValidationErrors());
			getLoggingHandler().handleErrors(result.getErrors().getAllValidationErrors());
		}
		final Address address = result.getTarget();
		getLoggingHandler().handleResult("[UPDATE BRAINTREE ADDRESS RESULT]", address);
		final BrainTreeAddressResult btaResult = new BrainTreeAddressResult(address);
		btaResult.setSuccess(result.isSuccess());
		btaResult.setErrorMessage(result.getMessage());
		return btaResult;

	}

}
