/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 18, 2016 2:17:03 PM                     ---
 * ----------------------------------------------------------------
 */
package com.braintree.jalo;

import com.braintree.constants.BraintreeConstants;
import com.braintree.jalo.TransactionCreditCardInfo;
import com.braintree.jalo.TransactionCustomer;
import com.braintree.jalo.TransactionPayPalInfo;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.order.Order;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BrainTreeTransactionDetail}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBrainTreeTransactionDetail extends GenericItem
{
	/** Qualifier of the <code>BrainTreeTransactionDetail.id</code> attribute **/
	public static final String ID = "id";
	/** Qualifier of the <code>BrainTreeTransactionDetail.merchant</code> attribute **/
	public static final String MERCHANT = "merchant";
	/** Qualifier of the <code>BrainTreeTransactionDetail.merchantAccount</code> attribute **/
	public static final String MERCHANTACCOUNT = "merchantAccount";
	/** Qualifier of the <code>BrainTreeTransactionDetail.amount</code> attribute **/
	public static final String AMOUNT = "amount";
	/** Qualifier of the <code>BrainTreeTransactionDetail.transactionDate</code> attribute **/
	public static final String TRANSACTIONDATE = "transactionDate";
	/** Qualifier of the <code>BrainTreeTransactionDetail.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>BrainTreeTransactionDetail.type</code> attribute **/
	public static final String TYPE = "type";
	/** Qualifier of the <code>BrainTreeTransactionDetail.settlementBatch</code> attribute **/
	public static final String SETTLEMENTBATCH = "settlementBatch";
	/** Qualifier of the <code>BrainTreeTransactionDetail.processorAuthorizationCode</code> attribute **/
	public static final String PROCESSORAUTHORIZATIONCODE = "processorAuthorizationCode";
	/** Qualifier of the <code>BrainTreeTransactionDetail.cvvResponse</code> attribute **/
	public static final String CVVRESPONSE = "cvvResponse";
	/** Qualifier of the <code>BrainTreeTransactionDetail.avsResponse</code> attribute **/
	public static final String AVSRESPONSE = "avsResponse";
	/** Qualifier of the <code>BrainTreeTransactionDetail.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/** Qualifier of the <code>BrainTreeTransactionDetail.paymentType</code> attribute **/
	public static final String PAYMENTTYPE = "paymentType";
	/** Qualifier of the <code>BrainTreeTransactionDetail.creditCartInfo</code> attribute **/
	public static final String CREDITCARTINFO = "creditCartInfo";
	/** Qualifier of the <code>BrainTreeTransactionDetail.payPalInfo</code> attribute **/
	public static final String PAYPALINFO = "payPalInfo";
	/** Qualifier of the <code>BrainTreeTransactionDetail.linkedOrder</code> attribute **/
	public static final String LINKEDORDER = "linkedOrder";
	/** Qualifier of the <code>BrainTreeTransactionDetail.refund</code> attribute **/
	public static final String REFUND = "refund";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ID, AttributeMode.INITIAL);
		tmp.put(MERCHANT, AttributeMode.INITIAL);
		tmp.put(MERCHANTACCOUNT, AttributeMode.INITIAL);
		tmp.put(AMOUNT, AttributeMode.INITIAL);
		tmp.put(TRANSACTIONDATE, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(TYPE, AttributeMode.INITIAL);
		tmp.put(SETTLEMENTBATCH, AttributeMode.INITIAL);
		tmp.put(PROCESSORAUTHORIZATIONCODE, AttributeMode.INITIAL);
		tmp.put(CVVRESPONSE, AttributeMode.INITIAL);
		tmp.put(AVSRESPONSE, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		tmp.put(PAYMENTTYPE, AttributeMode.INITIAL);
		tmp.put(CREDITCARTINFO, AttributeMode.INITIAL);
		tmp.put(PAYPALINFO, AttributeMode.INITIAL);
		tmp.put(LINKEDORDER, AttributeMode.INITIAL);
		tmp.put(REFUND, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.amount</code> attribute.
	 * @return the amount
	 */
	public String getAmount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.amount</code> attribute.
	 * @return the amount
	 */
	public String getAmount()
	{
		return getAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.amount</code> attribute. 
	 * @param value the amount
	 */
	public void setAmount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.amount</code> attribute. 
	 * @param value the amount
	 */
	public void setAmount(final String value)
	{
		setAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.avsResponse</code> attribute.
	 * @return the avsResponse
	 */
	public String getAvsResponse(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AVSRESPONSE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.avsResponse</code> attribute.
	 * @return the avsResponse
	 */
	public String getAvsResponse()
	{
		return getAvsResponse( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.avsResponse</code> attribute. 
	 * @param value the avsResponse
	 */
	public void setAvsResponse(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AVSRESPONSE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.avsResponse</code> attribute. 
	 * @param value the avsResponse
	 */
	public void setAvsResponse(final String value)
	{
		setAvsResponse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.creditCartInfo</code> attribute.
	 * @return the creditCartInfo
	 */
	public TransactionCreditCardInfo getCreditCartInfo(final SessionContext ctx)
	{
		return (TransactionCreditCardInfo)getProperty( ctx, CREDITCARTINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.creditCartInfo</code> attribute.
	 * @return the creditCartInfo
	 */
	public TransactionCreditCardInfo getCreditCartInfo()
	{
		return getCreditCartInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.creditCartInfo</code> attribute. 
	 * @param value the creditCartInfo
	 */
	public void setCreditCartInfo(final SessionContext ctx, final TransactionCreditCardInfo value)
	{
		setProperty(ctx, CREDITCARTINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.creditCartInfo</code> attribute. 
	 * @param value the creditCartInfo
	 */
	public void setCreditCartInfo(final TransactionCreditCardInfo value)
	{
		setCreditCartInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.customer</code> attribute.
	 * @return the customer
	 */
	public TransactionCustomer getCustomer(final SessionContext ctx)
	{
		return (TransactionCustomer)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.customer</code> attribute.
	 * @return the customer
	 */
	public TransactionCustomer getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final TransactionCustomer value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final TransactionCustomer value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.cvvResponse</code> attribute.
	 * @return the cvvResponse
	 */
	public String getCvvResponse(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CVVRESPONSE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.cvvResponse</code> attribute.
	 * @return the cvvResponse
	 */
	public String getCvvResponse()
	{
		return getCvvResponse( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.cvvResponse</code> attribute. 
	 * @param value the cvvResponse
	 */
	public void setCvvResponse(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CVVRESPONSE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.cvvResponse</code> attribute. 
	 * @param value the cvvResponse
	 */
	public void setCvvResponse(final String value)
	{
		setCvvResponse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.id</code> attribute.
	 * @return the id
	 */
	public String getId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.id</code> attribute.
	 * @return the id
	 */
	public String getId()
	{
		return getId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final String value)
	{
		setId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.linkedOrder</code> attribute.
	 * @return the linkedOrder
	 */
	public Order getLinkedOrder(final SessionContext ctx)
	{
		return (Order)getProperty( ctx, LINKEDORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.linkedOrder</code> attribute.
	 * @return the linkedOrder
	 */
	public Order getLinkedOrder()
	{
		return getLinkedOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.linkedOrder</code> attribute. 
	 * @param value the linkedOrder
	 */
	public void setLinkedOrder(final SessionContext ctx, final Order value)
	{
		setProperty(ctx, LINKEDORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.linkedOrder</code> attribute. 
	 * @param value the linkedOrder
	 */
	public void setLinkedOrder(final Order value)
	{
		setLinkedOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.merchant</code> attribute.
	 * @return the merchant
	 */
	public String getMerchant(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MERCHANT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.merchant</code> attribute.
	 * @return the merchant
	 */
	public String getMerchant()
	{
		return getMerchant( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.merchant</code> attribute. 
	 * @param value the merchant
	 */
	public void setMerchant(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MERCHANT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.merchant</code> attribute. 
	 * @param value the merchant
	 */
	public void setMerchant(final String value)
	{
		setMerchant( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.merchantAccount</code> attribute.
	 * @return the merchantAccount
	 */
	public String getMerchantAccount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MERCHANTACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.merchantAccount</code> attribute.
	 * @return the merchantAccount
	 */
	public String getMerchantAccount()
	{
		return getMerchantAccount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.merchantAccount</code> attribute. 
	 * @param value the merchantAccount
	 */
	public void setMerchantAccount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MERCHANTACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.merchantAccount</code> attribute. 
	 * @param value the merchantAccount
	 */
	public void setMerchantAccount(final String value)
	{
		setMerchantAccount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.paymentType</code> attribute.
	 * @return the paymentType
	 */
	public String getPaymentType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.paymentType</code> attribute.
	 * @return the paymentType
	 */
	public String getPaymentType()
	{
		return getPaymentType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.paymentType</code> attribute. 
	 * @param value the paymentType
	 */
	public void setPaymentType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.paymentType</code> attribute. 
	 * @param value the paymentType
	 */
	public void setPaymentType(final String value)
	{
		setPaymentType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.payPalInfo</code> attribute.
	 * @return the payPalInfo
	 */
	public TransactionPayPalInfo getPayPalInfo(final SessionContext ctx)
	{
		return (TransactionPayPalInfo)getProperty( ctx, PAYPALINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.payPalInfo</code> attribute.
	 * @return the payPalInfo
	 */
	public TransactionPayPalInfo getPayPalInfo()
	{
		return getPayPalInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.payPalInfo</code> attribute. 
	 * @param value the payPalInfo
	 */
	public void setPayPalInfo(final SessionContext ctx, final TransactionPayPalInfo value)
	{
		setProperty(ctx, PAYPALINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.payPalInfo</code> attribute. 
	 * @param value the payPalInfo
	 */
	public void setPayPalInfo(final TransactionPayPalInfo value)
	{
		setPayPalInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.processorAuthorizationCode</code> attribute.
	 * @return the processorAuthorizationCode
	 */
	public String getProcessorAuthorizationCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PROCESSORAUTHORIZATIONCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.processorAuthorizationCode</code> attribute.
	 * @return the processorAuthorizationCode
	 */
	public String getProcessorAuthorizationCode()
	{
		return getProcessorAuthorizationCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.processorAuthorizationCode</code> attribute. 
	 * @param value the processorAuthorizationCode
	 */
	public void setProcessorAuthorizationCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PROCESSORAUTHORIZATIONCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.processorAuthorizationCode</code> attribute. 
	 * @param value the processorAuthorizationCode
	 */
	public void setProcessorAuthorizationCode(final String value)
	{
		setProcessorAuthorizationCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.refund</code> attribute.
	 * @return the refund
	 */
	public String getRefund(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REFUND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.refund</code> attribute.
	 * @return the refund
	 */
	public String getRefund()
	{
		return getRefund( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.refund</code> attribute. 
	 * @param value the refund
	 */
	public void setRefund(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REFUND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.refund</code> attribute. 
	 * @param value the refund
	 */
	public void setRefund(final String value)
	{
		setRefund( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.settlementBatch</code> attribute.
	 * @return the settlementBatch
	 */
	public String getSettlementBatch(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SETTLEMENTBATCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.settlementBatch</code> attribute.
	 * @return the settlementBatch
	 */
	public String getSettlementBatch()
	{
		return getSettlementBatch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.settlementBatch</code> attribute. 
	 * @param value the settlementBatch
	 */
	public void setSettlementBatch(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SETTLEMENTBATCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.settlementBatch</code> attribute. 
	 * @param value the settlementBatch
	 */
	public void setSettlementBatch(final String value)
	{
		setSettlementBatch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.status</code> attribute.
	 * @return the status
	 */
	public String getStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.status</code> attribute.
	 * @return the status
	 */
	public String getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final String value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.transactionDate</code> attribute.
	 * @return the transactionDate
	 */
	public String getTransactionDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRANSACTIONDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.transactionDate</code> attribute.
	 * @return the transactionDate
	 */
	public String getTransactionDate()
	{
		return getTransactionDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.transactionDate</code> attribute. 
	 * @param value the transactionDate
	 */
	public void setTransactionDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRANSACTIONDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.transactionDate</code> attribute. 
	 * @param value the transactionDate
	 */
	public void setTransactionDate(final String value)
	{
		setTransactionDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.type</code> attribute.
	 * @return the type
	 */
	public String getType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreeTransactionDetail.type</code> attribute.
	 * @return the type
	 */
	public String getType()
	{
		return getType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreeTransactionDetail.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final String value)
	{
		setType( getSession().getSessionContext(), value );
	}
	
}
