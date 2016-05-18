package com.braintree.cscockpit.converters;

import de.hybris.platform.converters.impl.AbstractPopulatingConverter;

import com.braintree.cscockpit.converters.utils.BrainTreeTransactionConverterUtils;
import com.braintree.command.result.BrainTreeAbstractTransactionResult;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintreegateway.Transaction;


public class BrainTreeResponseConverter extends
		AbstractPopulatingConverter<BrainTreeAbstractTransactionResult, BrainTreeResponseResultData>
{

	@Override
	public void populate(final BrainTreeAbstractTransactionResult source, final BrainTreeResponseResultData target)
	{
		target.setSuccess(source.isSuccess());
		target.setErrorMessage(source.getErrorMessage());
		target.setErrorCode(source.getErrorCode());
		target.setTransactionId(source.getTransactionId());
		convertTransaction(source, target);
	}

	private void convertTransaction(final BrainTreeAbstractTransactionResult source, final BrainTreeResponseResultData target)
	{
		final Transaction transaction = source.getTransaction();
		if (transaction != null)
		{
			final BraintreeTransactionEntryData braintreeTransactionEntryData = new BraintreeTransactionEntryData();
			braintreeTransactionEntryData.setId(transaction.getId());
			braintreeTransactionEntryData.setDate(BrainTreeTransactionConverterUtils.formedDate(transaction));
			braintreeTransactionEntryData.setPaymentInfo(BrainTreeTransactionConverterUtils.formedPaymentInfo(transaction));
			braintreeTransactionEntryData.setAmount(BrainTreeTransactionConverterUtils.formedAmount(transaction));

			if (transaction.getCustomer() != null)
			{
				braintreeTransactionEntryData.setCustomer(BrainTreeTransactionConverterUtils.formedName(transaction.getCustomer()));
			}
			if (transaction.getStatus() != null)
			{
				braintreeTransactionEntryData.setStatus(transaction.getStatus().name());
			}
			if (transaction.getType() != null)
			{
				braintreeTransactionEntryData.setType(transaction.getType().name());
			}
			braintreeTransactionEntryData.setDetails(BrainTreeTransactionConverterUtils.convertDetails(transaction));
			target.setTransactionEntryData(braintreeTransactionEntryData);
		}
	}
}
