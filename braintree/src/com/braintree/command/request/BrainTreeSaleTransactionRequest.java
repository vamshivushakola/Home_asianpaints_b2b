package com.braintree.command.request;

import de.hybris.platform.payment.dto.BillingInfo;
import de.hybris.platform.payment.dto.CardInfo;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

import com.braintree.enums.StoreInVault;


public class BrainTreeSaleTransactionRequest extends BrainTreeAuthorizationRequest
{
	private BigDecimal taxAmount;
	private Map<String, String> customFields;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private StoreInVault storeInVault;


	public BrainTreeSaleTransactionRequest(String merchantTransactionCode, CardInfo card, Currency currency,
			BigDecimal totalAmount, BillingInfo shippingInfo)
	{
		super(merchantTransactionCode, card, currency, totalAmount, shippingInfo);
	}

	public BigDecimal getTaxAmount()
	{
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount)
	{
		this.taxAmount = taxAmount;
	}

	public Map<String, String> getCustomFields()
	{
		return customFields;
	}

	public void setCustomFields(Map<String, String> customFields)
	{
		this.customFields = customFields;
	}

	public String getCustomerFirstName()
	{
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName)
	{
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName()
	{
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName)
	{
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail()
	{
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail)
	{
		this.customerEmail = customerEmail;
	}

	public void setStoreInVault(StoreInVault storeInVault)
	{
		this.storeInVault = storeInVault;
	}

	public StoreInVault getStoreInVault()
	{
		return storeInVault;
	}
}
