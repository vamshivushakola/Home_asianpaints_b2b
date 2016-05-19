/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 19, 2016 3:58:04 PM                     ---
 * ----------------------------------------------------------------
 */
package com.braintree.jalo;

import com.braintree.constants.BraintreeConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem TransactionPayPalInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTransactionPayPalInfo extends GenericItem
{
	/** Qualifier of the <code>TransactionPayPalInfo.payerEmail</code> attribute **/
	public static final String PAYEREMAIL = "payerEmail";
	/** Qualifier of the <code>TransactionPayPalInfo.payerID</code> attribute **/
	public static final String PAYERID = "payerID";
	/** Qualifier of the <code>TransactionPayPalInfo.payerFirstName</code> attribute **/
	public static final String PAYERFIRSTNAME = "payerFirstName";
	/** Qualifier of the <code>TransactionPayPalInfo.payerLastName</code> attribute **/
	public static final String PAYERLASTNAME = "payerLastName";
	/** Qualifier of the <code>TransactionPayPalInfo.payeeEmail</code> attribute **/
	public static final String PAYEEEMAIL = "payeeEmail";
	/** Qualifier of the <code>TransactionPayPalInfo.paymentID</code> attribute **/
	public static final String PAYMENTID = "paymentID";
	/** Qualifier of the <code>TransactionPayPalInfo.authorizationUniqueTransactionID</code> attribute **/
	public static final String AUTHORIZATIONUNIQUETRANSACTIONID = "authorizationUniqueTransactionID";
	/** Qualifier of the <code>TransactionPayPalInfo.payPalDebugID</code> attribute **/
	public static final String PAYPALDEBUGID = "payPalDebugID";
	/** Qualifier of the <code>TransactionPayPalInfo.payPalCustomField</code> attribute **/
	public static final String PAYPALCUSTOMFIELD = "payPalCustomField";
	/** Qualifier of the <code>TransactionPayPalInfo.payPalSellerProtection</code> attribute **/
	public static final String PAYPALSELLERPROTECTION = "payPalSellerProtection";
	/** Qualifier of the <code>TransactionPayPalInfo.payPalCaptureID</code> attribute **/
	public static final String PAYPALCAPTUREID = "payPalCaptureID";
	/** Qualifier of the <code>TransactionPayPalInfo.payPalRefundID</code> attribute **/
	public static final String PAYPALREFUNDID = "payPalRefundID";
	/** Qualifier of the <code>TransactionPayPalInfo.token</code> attribute **/
	public static final String TOKEN = "token";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PAYEREMAIL, AttributeMode.INITIAL);
		tmp.put(PAYERID, AttributeMode.INITIAL);
		tmp.put(PAYERFIRSTNAME, AttributeMode.INITIAL);
		tmp.put(PAYERLASTNAME, AttributeMode.INITIAL);
		tmp.put(PAYEEEMAIL, AttributeMode.INITIAL);
		tmp.put(PAYMENTID, AttributeMode.INITIAL);
		tmp.put(AUTHORIZATIONUNIQUETRANSACTIONID, AttributeMode.INITIAL);
		tmp.put(PAYPALDEBUGID, AttributeMode.INITIAL);
		tmp.put(PAYPALCUSTOMFIELD, AttributeMode.INITIAL);
		tmp.put(PAYPALSELLERPROTECTION, AttributeMode.INITIAL);
		tmp.put(PAYPALCAPTUREID, AttributeMode.INITIAL);
		tmp.put(PAYPALREFUNDID, AttributeMode.INITIAL);
		tmp.put(TOKEN, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.authorizationUniqueTransactionID</code> attribute.
	 * @return the authorizationUniqueTransactionID
	 */
	public String getAuthorizationUniqueTransactionID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AUTHORIZATIONUNIQUETRANSACTIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.authorizationUniqueTransactionID</code> attribute.
	 * @return the authorizationUniqueTransactionID
	 */
	public String getAuthorizationUniqueTransactionID()
	{
		return getAuthorizationUniqueTransactionID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.authorizationUniqueTransactionID</code> attribute. 
	 * @param value the authorizationUniqueTransactionID
	 */
	public void setAuthorizationUniqueTransactionID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AUTHORIZATIONUNIQUETRANSACTIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.authorizationUniqueTransactionID</code> attribute. 
	 * @param value the authorizationUniqueTransactionID
	 */
	public void setAuthorizationUniqueTransactionID(final String value)
	{
		setAuthorizationUniqueTransactionID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payeeEmail</code> attribute.
	 * @return the payeeEmail
	 */
	public String getPayeeEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYEEEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payeeEmail</code> attribute.
	 * @return the payeeEmail
	 */
	public String getPayeeEmail()
	{
		return getPayeeEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payeeEmail</code> attribute. 
	 * @param value the payeeEmail
	 */
	public void setPayeeEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYEEEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payeeEmail</code> attribute. 
	 * @param value the payeeEmail
	 */
	public void setPayeeEmail(final String value)
	{
		setPayeeEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerEmail</code> attribute.
	 * @return the payerEmail
	 */
	public String getPayerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerEmail</code> attribute.
	 * @return the payerEmail
	 */
	public String getPayerEmail()
	{
		return getPayerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerEmail</code> attribute. 
	 * @param value the payerEmail
	 */
	public void setPayerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerEmail</code> attribute. 
	 * @param value the payerEmail
	 */
	public void setPayerEmail(final String value)
	{
		setPayerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerFirstName</code> attribute.
	 * @return the payerFirstName
	 */
	public String getPayerFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYERFIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerFirstName</code> attribute.
	 * @return the payerFirstName
	 */
	public String getPayerFirstName()
	{
		return getPayerFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerFirstName</code> attribute. 
	 * @param value the payerFirstName
	 */
	public void setPayerFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYERFIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerFirstName</code> attribute. 
	 * @param value the payerFirstName
	 */
	public void setPayerFirstName(final String value)
	{
		setPayerFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerID</code> attribute.
	 * @return the payerID
	 */
	public String getPayerID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerID</code> attribute.
	 * @return the payerID
	 */
	public String getPayerID()
	{
		return getPayerID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerID</code> attribute. 
	 * @param value the payerID
	 */
	public void setPayerID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerID</code> attribute. 
	 * @param value the payerID
	 */
	public void setPayerID(final String value)
	{
		setPayerID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerLastName</code> attribute.
	 * @return the payerLastName
	 */
	public String getPayerLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYERLASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payerLastName</code> attribute.
	 * @return the payerLastName
	 */
	public String getPayerLastName()
	{
		return getPayerLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerLastName</code> attribute. 
	 * @param value the payerLastName
	 */
	public void setPayerLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYERLASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payerLastName</code> attribute. 
	 * @param value the payerLastName
	 */
	public void setPayerLastName(final String value)
	{
		setPayerLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.paymentID</code> attribute.
	 * @return the paymentID
	 */
	public String getPaymentID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.paymentID</code> attribute.
	 * @return the paymentID
	 */
	public String getPaymentID()
	{
		return getPaymentID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.paymentID</code> attribute. 
	 * @param value the paymentID
	 */
	public void setPaymentID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.paymentID</code> attribute. 
	 * @param value the paymentID
	 */
	public void setPaymentID(final String value)
	{
		setPaymentID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalCaptureID</code> attribute.
	 * @return the payPalCaptureID
	 */
	public String getPayPalCaptureID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYPALCAPTUREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalCaptureID</code> attribute.
	 * @return the payPalCaptureID
	 */
	public String getPayPalCaptureID()
	{
		return getPayPalCaptureID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalCaptureID</code> attribute. 
	 * @param value the payPalCaptureID
	 */
	public void setPayPalCaptureID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYPALCAPTUREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalCaptureID</code> attribute. 
	 * @param value the payPalCaptureID
	 */
	public void setPayPalCaptureID(final String value)
	{
		setPayPalCaptureID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalCustomField</code> attribute.
	 * @return the payPalCustomField
	 */
	public String getPayPalCustomField(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYPALCUSTOMFIELD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalCustomField</code> attribute.
	 * @return the payPalCustomField
	 */
	public String getPayPalCustomField()
	{
		return getPayPalCustomField( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalCustomField</code> attribute. 
	 * @param value the payPalCustomField
	 */
	public void setPayPalCustomField(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYPALCUSTOMFIELD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalCustomField</code> attribute. 
	 * @param value the payPalCustomField
	 */
	public void setPayPalCustomField(final String value)
	{
		setPayPalCustomField( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalDebugID</code> attribute.
	 * @return the payPalDebugID
	 */
	public String getPayPalDebugID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYPALDEBUGID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalDebugID</code> attribute.
	 * @return the payPalDebugID
	 */
	public String getPayPalDebugID()
	{
		return getPayPalDebugID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalDebugID</code> attribute. 
	 * @param value the payPalDebugID
	 */
	public void setPayPalDebugID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYPALDEBUGID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalDebugID</code> attribute. 
	 * @param value the payPalDebugID
	 */
	public void setPayPalDebugID(final String value)
	{
		setPayPalDebugID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalRefundID</code> attribute.
	 * @return the payPalRefundID
	 */
	public String getPayPalRefundID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYPALREFUNDID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalRefundID</code> attribute.
	 * @return the payPalRefundID
	 */
	public String getPayPalRefundID()
	{
		return getPayPalRefundID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalRefundID</code> attribute. 
	 * @param value the payPalRefundID
	 */
	public void setPayPalRefundID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYPALREFUNDID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalRefundID</code> attribute. 
	 * @param value the payPalRefundID
	 */
	public void setPayPalRefundID(final String value)
	{
		setPayPalRefundID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalSellerProtection</code> attribute.
	 * @return the payPalSellerProtection
	 */
	public String getPayPalSellerProtection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYPALSELLERPROTECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.payPalSellerProtection</code> attribute.
	 * @return the payPalSellerProtection
	 */
	public String getPayPalSellerProtection()
	{
		return getPayPalSellerProtection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalSellerProtection</code> attribute. 
	 * @param value the payPalSellerProtection
	 */
	public void setPayPalSellerProtection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYPALSELLERPROTECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.payPalSellerProtection</code> attribute. 
	 * @param value the payPalSellerProtection
	 */
	public void setPayPalSellerProtection(final String value)
	{
		setPayPalSellerProtection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.token</code> attribute.
	 * @return the token
	 */
	public String getToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionPayPalInfo.token</code> attribute.
	 * @return the token
	 */
	public String getToken()
	{
		return getToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionPayPalInfo.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final String value)
	{
		setToken( getSession().getSessionContext(), value );
	}
	
}
