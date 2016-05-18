/**
 *
 */
package com.braintree.paypal.converters.impl;

import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.braintree.hybris.data.BrainTreePaymentInfoData;
import com.braintree.model.BrainTreePaymentInfoModel;


public class BrainTreePaymentInfoDataConverter implements Converter<BrainTreePaymentInfoModel, BrainTreePaymentInfoData>
{

	@Override
	public BrainTreePaymentInfoData convert(final BrainTreePaymentInfoModel paymentInfo) throws ConversionException
	{
		final BrainTreePaymentInfoData paymentData = new BrainTreePaymentInfoData();
		return convert(paymentInfo, paymentData);
	}

	@Override
	public BrainTreePaymentInfoData convert(final BrainTreePaymentInfoModel paymentInfo, final BrainTreePaymentInfoData paymentData)
			throws ConversionException
	{
		paymentData.setPaymentType(paymentInfo.getPaymentProvider());
		paymentData.setCardNumber(paymentInfo.getCardNumber());
		if (paymentInfo.getCardType() != null)
		{
			paymentData.setCardType(paymentInfo.getCardType().getCode());
		}
		if (paymentInfo.getBillingAddress() != null)
		{
			paymentData.setEmail(paymentInfo.getBillingAddress().getEmail());
		}
		return paymentData;
	}

}
