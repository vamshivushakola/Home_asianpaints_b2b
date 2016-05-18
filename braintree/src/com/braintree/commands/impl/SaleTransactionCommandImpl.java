package com.braintree.commands.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.payment.dto.CardInfo;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.dto.TransactionStatusDetails;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.braintree.command.request.BrainTreeSaleTransactionRequest;
import com.braintree.command.result.BrainTreeSaleTransactionResult;
import com.braintree.commands.BrainTreeSaleCommand;
import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.constants.BraintreeConstants;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionCreditCardRequest;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;


public class SaleTransactionCommandImpl extends AbstractCommand implements BrainTreeSaleCommand
{

	private BrainTreeConfigService brainTreeConfigService;

	@Override
	public BrainTreeSaleTransactionResult perform(final BrainTreeSaleTransactionRequest saleRequest)
	{
		validateParameterNotNullStandardMessage("Refund transaction Request", saleRequest);
		try
		{
			final Result<Transaction> result = getBraintreeGateway().transaction().sale(translateRequest(saleRequest));
			if (result.isSuccess())
			{
				return translateResponse(result.getTarget(), result.isSuccess());
			}
			else
			{
				return translateErrorResponse(result);
			}
		}
		catch (final Exception exception)
		{
			throw new AdapterException(exception.getMessage(), exception);
		}
	}

	private TransactionRequest translateRequest(final BrainTreeSaleTransactionRequest saleRequest)
	{
		final TransactionRequest request = new TransactionRequest();

		request.amount(saleRequest.getTotalAmount());
		request.taxAmount(saleRequest.getTaxAmount());
		request.options().submitForSettlement(getBrainTreeConfigService().getSettlementConfigParameter()).done();
		request.customerId(saleRequest.getCustomerId());
		request.channel(getBrainTreeConfigService().getBrainTreeChannel());

		final String merchantAccountIdForCurrentSite = saleRequest.getMerchantAccountIdForCurrentSite();
		if (merchantAccountIdForCurrentSite != null)
		{
			request.merchantAccountId(merchantAccountIdForCurrentSite);
		}

		addInVaultProperty(request);
		assCustomFields(request, saleRequest);

		setAdditionalParameters(saleRequest, request);

		addShippingInfo(saleRequest, request);
		addBillingInfo(saleRequest, request);
		addCustomerInfo(saleRequest, request);

		return request;
	}

	private void addCustomerInfo(final BrainTreeSaleTransactionRequest saleRequest, final TransactionRequest request)
	{
		request.customer().firstName(saleRequest.getCustomerFirstName()).lastName(saleRequest.getCustomerLastName())
				.email(saleRequest.getCustomerEmail());
	}

	private void assCustomFields(final TransactionRequest request, final BrainTreeSaleTransactionRequest saleRequest)
	{
		final Map<String, String> customFields = saleRequest.getCustomFields();
		if (customFields != null)
		{
			for (final Map.Entry<String, String> entry : customFields.entrySet())
			{
				request.customField(entry.getKey(), entry.getValue());
			}
		}
	}

	private void setAdditionalParameters(final BrainTreeSaleTransactionRequest saleRequest, final TransactionRequest request)
	{
		if (saleRequest.getUsePaymentMethodToken().booleanValue())
		{
			if (saleRequest.getPaymentMethodToken() == null)
			{
				getLoggingHandler().getLogger().error("Error: PaymentMethodToken is null!");
				throw new IllegalArgumentException("Error during using existing payment.");
			}
			request.paymentMethodToken(saleRequest.getPaymentMethodToken());
		}
		else
		{
			addCardInfo(saleRequest, request);
		}

		if ((StringUtils.isNotBlank(getBrainTreeConfigService().getCreditCardStatementName())))
		{
			request.descriptor().name(getBrainTreeConfigService().getCreditCardStatementName()).done();
		}
	}

	private void addCardInfo(final BrainTreeSaleTransactionRequest saleRequest, final TransactionRequest request)
	{
		final CardInfo card = saleRequest.getCard();
		final TransactionCreditCardRequest transactionCreditCardRequest = request.creditCard();
		if (card != null)
		{
			transactionCreditCardRequest.cardholderName(card.getCardHolderFullName()).number(card.getCardNumber())
					.cvv(card.getCv2Number());
			if (card.getExpirationMonth() != null)
			{
				transactionCreditCardRequest.expirationMonth(card.getExpirationMonth().toString());
			}
			if (card.getExpirationYear() != null)
			{
				transactionCreditCardRequest.expirationYear(card.getExpirationYear().toString());
			}
		}
	}

	private void addShippingInfo(final BrainTreeSaleTransactionRequest saleRequest, final TransactionRequest request)
	{
		final BillingInfo shippingInfo = saleRequest.getShippingInfo();
		if (shippingInfo != null)
		{
			request.shippingAddress().countryCodeAlpha2(shippingInfo.getCountry()).region(shippingInfo.getState())
					.firstName(shippingInfo.getFirstName()).lastName(shippingInfo.getLastName())
					.streetAddress(shippingInfo.getStreet1()).locality(shippingInfo.getCity())
					.postalCode(shippingInfo.getPostalCode());
		}
	}

	private void addBillingInfo(final BrainTreeSaleTransactionRequest saleRequest, final TransactionRequest request)
	{
		final CardInfo card = saleRequest.getCard();
		if (card != null)
		{
			final BillingInfo billingInfo = card.getBillingInfo();
			if (billingInfo != null)
			{
				request.billingAddress().countryCodeAlpha2(billingInfo.getCountry()).region(billingInfo.getState())
						.firstName(billingInfo.getFirstName()).lastName(billingInfo.getLastName())
						.streetAddress(billingInfo.getStreet1()).locality(billingInfo.getCity())
						.postalCode(billingInfo.getPostalCode());
			}
		}
	}

	private BrainTreeSaleTransactionResult translateErrorResponse(final Result<Transaction> result)
	{
		final BrainTreeSaleTransactionResult response = new BrainTreeSaleTransactionResult(result.isSuccess());
		if (result.getErrors() != null)
		{

			final List<ValidationError> allDeepValidationErrors = result.getErrors().getAllDeepValidationErrors();
			if (allDeepValidationErrors != null && allDeepValidationErrors.size() > 0)
			{
				final ValidationError validationError = allDeepValidationErrors.get(0);
				getLoggingHandler().getLogger().info(
						String.format("BT sale transaction(new transaction) with error: %s %s", validationError.getCode(),
								validationError.getMessage()));

				if (validationError.getCode() != null)
				{
					response.setErrorCode(validationError.getCode().toString());
				}
				response.setErrorMessage(validationError.getMessage());
			}
		}
		return response;
	}

	private BrainTreeSaleTransactionResult translateResponse(final Transaction transaction, final boolean success)
	{
		final BrainTreeSaleTransactionResult result = new BrainTreeSaleTransactionResult(success);
		if (transaction != null)
		{
			if (success)
			{
				result.setMerchantTransactionCode(transaction.getMerchantAccountId());
				result.setRequestId(transaction.getId());
				result.setTransactionId(transaction.getId());
				result.setRequestToken(transaction.getId());
				result.setTransactionStatus(TransactionStatus.ACCEPTED);
				result.setTransactionStatusDetails(TransactionStatusDetails.SUCCESFULL);
			}
			else
			{
				result.setTransactionStatusDetails(TransactionStatusDetails.BANK_DECLINE);
			}
			result.setTransaction(transaction);
		}
		return result;
	}

	private void addInVaultProperty(final TransactionRequest request)
	{
		if (BraintreeConstants.ON_SUCCESS.equalsIgnoreCase(getBrainTreeConfigService().getStoreInVault()))
		{
			request.options().storeInVaultOnSuccess(Boolean.TRUE).done();
		}
		else
		{
			request.options().storeInVault(Boolean.valueOf(getBrainTreeConfigService().getStoreInVault())).done();
		}

	}

	/**
	 * @return the brainTreeConfigService
	 */
	@Override
	public BrainTreeConfigService getBrainTreeConfigService()
	{
		return brainTreeConfigService;
	}

	/**
	 * @param brainTreeConfigService
	 *           the brainTreeConfigService to set
	 */
	@Override
	public void setBrainTreeConfigService(final BrainTreeConfigService brainTreeConfigService)
	{
		this.brainTreeConfigService = brainTreeConfigService;
	}
}
