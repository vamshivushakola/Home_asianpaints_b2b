package com.braintree.cscockpit.converters;

import de.hybris.platform.converters.impl.AbstractPopulatingConverter;

import com.braintree.hybris.data.BraintreeTransactionEntryCustomerData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintree.hybris.data.BraintreeTransactionEntryDetailData;
import com.braintree.hybris.data.BraintreeTransactionEntryPayPalData;
import com.braintree.hybris.data.BraintreeTransactionEntryPaymentData;
import com.braintree.hybris.data.BraintreeTransactionOriginEntryData;
import com.braintree.model.BrainTreeTransactionDetailModel;
import com.braintree.model.TransactionCreditCardInfoModel;
import com.braintree.model.TransactionCustomerModel;
import com.braintree.model.TransactionPayPalInfoModel;



public class BrainTreeTransactionDetailConverter extends
		AbstractPopulatingConverter<BraintreeTransactionEntryData, BrainTreeTransactionDetailModel>
{

	@Override
	public void populate(final BraintreeTransactionEntryData entryData, final BrainTreeTransactionDetailModel detailModel)
	{
		if (entryData != null)
		{
			final BraintreeTransactionEntryDetailData details = entryData.getDetails();
			if (details != null)
			{
				detailModel.setId(entryData.getId());
				detailModel.setType(entryData.getType());

				detailModel.setPaymentType(formedType(details));
				populateTransactionInfo(detailModel, details.getTransactionInfo());
				detailModel.setCreditCartInfo(populateCreditCardInfo(details.getPaymentInfo()));
				detailModel.setPayPalInfo(populatePayPalIndo(details.getPaymentPayPalInfo()));
				detailModel.setCustomer(populateCustomer(details.getCustomerInfo()));
			}
		}
	}

	private void populateTransactionInfo(final BrainTreeTransactionDetailModel detailModel,
			final BraintreeTransactionOriginEntryData transactionInfo)
	{
		detailModel.setMerchant(transactionInfo.getMerchant());
		detailModel.setMerchantAccount(transactionInfo.getMerchantAccount());
		detailModel.setStatus(transactionInfo.getStatus());
		detailModel.setTransactionDate(transactionInfo.getTransactionDate());
		detailModel.setSettlementBatch(transactionInfo.getSettlementBatch());
		detailModel.setAvsResponse(transactionInfo.getAvsResponse());
		detailModel.setCvvResponse(transactionInfo.getCvvResponse());
		detailModel.setProcessorAuthorizationCode(transactionInfo.getProcessorAuthorizationCode());
		detailModel.setAmount(transactionInfo.getAmount());
		detailModel.setRefund(transactionInfo.getRefund());
	}

	private TransactionCreditCardInfoModel populateCreditCardInfo(final BraintreeTransactionEntryPaymentData paymentInfo)
	{
		final TransactionCreditCardInfoModel creditCard = new TransactionCreditCardInfoModel();
		if (paymentInfo != null)
		{
			creditCard.setCardholderName(paymentInfo.getCardholderName());
			creditCard.setCardType(paymentInfo.getCardType());
			creditCard.setCreditCardNumber(paymentInfo.getCreditCardNumber());
			creditCard.setExpirationDate(paymentInfo.getExpirationDate());
			creditCard.setToken(paymentInfo.getToken());
			creditCard.setUniqueNumberIdentifier(paymentInfo.getUniqueNumberIdentifier());
		}
		return creditCard;
	}

	private TransactionPayPalInfoModel populatePayPalIndo(final BraintreeTransactionEntryPayPalData paymentPayPalInfo)
	{
		final TransactionPayPalInfoModel payPal = new TransactionPayPalInfoModel();
		if (paymentPayPalInfo != null)
		{
			payPal.setAuthorizationUniqueTransactionID(paymentPayPalInfo.getAuthorizationUniqueTransactionID());
			payPal.setPayeeEmail(paymentPayPalInfo.getPayeeEmail());
			payPal.setPayerEmail(paymentPayPalInfo.getPayerEmail());
			payPal.setPayerFirstName(paymentPayPalInfo.getPayerFirstName());
			payPal.setPayerLastName(paymentPayPalInfo.getPayerLastName());
			payPal.setPaymentID(paymentPayPalInfo.getPaymentID());
			payPal.setPayPalCaptureID(paymentPayPalInfo.getPayPalCaptureID());
			payPal.setPayPalCustomField(paymentPayPalInfo.getPayPalCustomField());
			payPal.setPayPalDebugID(paymentPayPalInfo.getPayPalDebugID());
			payPal.setPayPalRefundID(paymentPayPalInfo.getPayPalRefundID());
			payPal.setPayPalSellerProtection(paymentPayPalInfo.getPayPalSellerProtectionStatus());
			payPal.setToken(paymentPayPalInfo.getToken());
		}
		return payPal;
	}

	private TransactionCustomerModel populateCustomer(final BraintreeTransactionEntryCustomerData customer)
	{
		final TransactionCustomerModel transactionCustomerModel = new TransactionCustomerModel();
		if (customer != null)
		{
			transactionCustomerModel.setEmail(customer.getEmail());
			transactionCustomerModel.setCustomerID(customer.getCustomerID());
			transactionCustomerModel.setName(customer.getName());
		}

		return transactionCustomerModel;
	}

	private String formedType(final BraintreeTransactionEntryDetailData details)
	{
		if (details.getPaymentPayPalInfo() != null)
		{
			return details.getPaymentPayPalInfo().getPaymentType();
		}
		else if (details.getPaymentInfo() != null)
		{
			return details.getPaymentInfo().getPaymentType();
		}
		return null;
	}
}
