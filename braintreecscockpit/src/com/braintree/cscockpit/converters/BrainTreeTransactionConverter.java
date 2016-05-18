package com.braintree.cscockpit.converters;

import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

import com.braintree.cscockpit.converters.utils.BrainTreeTransactionConverterUtils;
import com.braintree.command.result.BrainTreeFindTransactionResult;
import com.braintree.hybris.data.BraintreeTransactionData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintreegateway.ResourceCollection;
import com.braintreegateway.Transaction;


public class BrainTreeTransactionConverter extends
		AbstractPopulatingConverter<BrainTreeFindTransactionResult, BraintreeTransactionData>
{
	@Override
	public void populate(final BrainTreeFindTransactionResult brainTreeFindTransactionResult,
			final BraintreeTransactionData braintreeTransactionData) throws ConversionException
	{
		final ResourceCollection<Transaction> transactions = brainTreeFindTransactionResult.getTransactions();

		final List<BraintreeTransactionEntryData> transactionEntries = new ArrayList<>();

		for (final Transaction transaction : transactions)
		{
			if (transaction != null)
			{
				final BraintreeTransactionEntryData braintreeTransactionEntryData = new BraintreeTransactionEntryData();
				braintreeTransactionEntryData.setId(transaction.getId());
				braintreeTransactionEntryData.setDate(BrainTreeTransactionConverterUtils.formedDate(transaction));
				braintreeTransactionEntryData.setPaymentInfo(BrainTreeTransactionConverterUtils.formedPaymentInfo(transaction));
				braintreeTransactionEntryData.setAmount(BrainTreeTransactionConverterUtils.formedAmount(transaction));

				if (transaction.getCustomer() != null)
				{
					braintreeTransactionEntryData
							.setCustomer(BrainTreeTransactionConverterUtils.formedName(transaction.getCustomer()));
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
				transactionEntries.add(braintreeTransactionEntryData);
			}
		}
		braintreeTransactionData.setTransactionEntries(transactionEntries);
	}

}
