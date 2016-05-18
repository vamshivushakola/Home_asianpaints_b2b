/**
 *
 */
package com.braintree.commands.impl;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_MERCHANT_ID;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_PRIVATE_KEY;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_PUBLIC_KEY;
import static de.hybris.platform.util.Config.getParameter;

import de.hybris.platform.payment.commands.request.AbstractRequest;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintreegateway.BraintreeGateway;


public abstract class AbstractCommand<RequestType, ResponseType>
{
	private BrainTreeLoggingHandler loggingHandler;
	private BraintreeCodeTranslator codeTranslator;
	private BraintreeErrorTranslator errorTranslator;
	private BrainTreeConfigService brainTreeConfigService;
	protected AbstractRequest request;

	public BraintreeGateway getBraintreeGateway()
	{

		final BraintreeGateway gateway = new BraintreeGateway(getBrainTreeConfigService().getEnvironmentType(),
				getParameter(BRAINTREE_MERCHANT_ID), getParameter(BRAINTREE_PUBLIC_KEY), getParameter(BRAINTREE_PRIVATE_KEY));

		return gateway;
	}

	/**
	 * @return the loggingHandler
	 */
	public BrainTreeLoggingHandler getLoggingHandler()
	{
		return loggingHandler;
	}

	/**
	 * @param loggingHandler
	 *           the loggingHandler to set
	 */
	public void setLoggingHandler(final BrainTreeLoggingHandler loggingHandler)
	{
		this.loggingHandler = loggingHandler;
	}

	/**
	 * @return the codeTranslator
	 */
	public BraintreeCodeTranslator getCodeTranslator()
	{
		return codeTranslator;
	}

	/**
	 * @param codeTranslator
	 *           the codeTranslator to set
	 */
	public void setCodeTranslator(final BraintreeCodeTranslator codeTranslator)
	{
		this.codeTranslator = codeTranslator;
	}

	/**
	 * @return the errorTranslator
	 */
	public BraintreeErrorTranslator getErrorTranslator()
	{
		return errorTranslator;
	}

	/**
	 * @param errorTranslator
	 *           the errorTranslator to set
	 */
	public void setErrorTranslator(final BraintreeErrorTranslator errorTranslator)
	{
		this.errorTranslator = errorTranslator;
	}

	/**
	 * @return the brainTreeConfigService
	 */
	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	/**
	 * @param brainTreeConfigService
	 *           the brainTreeConfigService to set
	 */
	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}



}
