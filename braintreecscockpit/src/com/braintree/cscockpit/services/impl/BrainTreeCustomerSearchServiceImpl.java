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
package com.braintree.cscockpit.services.impl;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_PROVIDER_NAME;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.commands.factory.CommandFactory;
import de.hybris.platform.payment.commands.factory.CommandFactoryRegistry;

import org.apache.log4j.Logger;

import com.braintree.cscockpit.services.CustomerSearchService;
import com.braintree.command.request.BrainTreeCustomerRequest;
import com.braintree.command.result.BrainTreeFindCustomersResult;
import com.braintree.commands.BrainTreeFindCustomerCommand;


public class BrainTreeCustomerSearchServiceImpl implements CustomerSearchService
{

	private final static Logger LOG = Logger.getLogger(BrainTreeTransactionSearchServiceImpl.class);
	private CommandFactoryRegistry commandFactoryRegistry;

	private CommandFactory getCommandFactory()
	{
		return commandFactoryRegistry.getFactory(BRAINTREE_PROVIDER_NAME);
	}


	public void setCommandFactoryRegistry(final CommandFactoryRegistry commandFactoryRegistry)
	{
		this.commandFactoryRegistry = commandFactoryRegistry;
	}

	@Override
	public BrainTreeFindCustomersResult findCustomers(final BrainTreeCustomerRequest findCustomerRequest) throws AdapterException
	{
		try
		{
			final BrainTreeFindCustomerCommand command = getCommandFactory().createCommand(BrainTreeFindCustomerCommand.class);
			final BrainTreeFindCustomersResult result = command.process(findCustomerRequest);
			return result;
		}
		catch (final Exception exception)
		{
			LOG.error("[BT Payment Service] Errors during trying to fin customer generation: " + exception.getMessage());
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

}
