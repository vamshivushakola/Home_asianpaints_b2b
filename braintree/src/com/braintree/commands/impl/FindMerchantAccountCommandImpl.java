/**
 *
 */
package com.braintree.commands.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import org.apache.log4j.Logger;

import com.braintree.command.request.BrainTreeFindMerchantAccountRequest;
import com.braintree.command.result.BrainTreeFindMerchantAccountResult;
import com.braintree.commands.BrainTreeFindMerchantAccountCommand;
import com.braintreegateway.MerchantAccount;
import com.braintreegateway.exceptions.NotFoundException;


public class FindMerchantAccountCommandImpl extends
		AbstractCommand<BrainTreeFindMerchantAccountRequest, BrainTreeFindMerchantAccountResult> implements
		BrainTreeFindMerchantAccountCommand
{
	private final static Logger LOG = Logger.getLogger(FindMerchantAccountCommandImpl.class);

	@Override
	public BrainTreeFindMerchantAccountResult perform(final BrainTreeFindMerchantAccountRequest request)
	{
		validateParameterNotNullStandardMessage("Find User Request", request);
		final String merchantAccountId = request.getMerchantAccount();
		validateParameterNotNullStandardMessage("merchantAccount", merchantAccountId);
		try
		{
			final MerchantAccount merchantAccount = getBraintreeGateway().merchantAccount().find(merchantAccountId);
			boolean isMerchantAccountExist = false;
			if (merchantAccount != null)
			{
				isMerchantAccountExist = true;
			}
			return new BrainTreeFindMerchantAccountResult(isMerchantAccountExist);
		}
		catch (final Exception exception)
		{
			if (exception instanceof NotFoundException)
			{
				LOG.error("[BT Find Customer] Can't find BrainTree merchanAccount with id: " + merchantAccountId);
				return new BrainTreeFindMerchantAccountResult(false);
			}
			else
			{
				LOG.error("[BT Find Customer] Error during try to find merchanAccount: " + merchantAccountId);
				throw new IllegalArgumentException(exception.getMessage());
			}
		}
	}

}
