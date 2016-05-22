/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 20, 2016 8:08:29 PM                     ---
 * ----------------------------------------------------------------
 */
package com.braintree.jalo;

import com.braintree.constants.BraintreeConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.payment.PaymentInfo;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.order.payment.PaymentInfo BrainTreePaymentInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBrainTreePaymentInfo extends PaymentInfo
{
	/** Qualifier of the <code>BrainTreePaymentInfo.cardType</code> attribute **/
	public static final String CARDTYPE = "cardType";
	/** Qualifier of the <code>BrainTreePaymentInfo.cardNumber</code> attribute **/
	public static final String CARDNUMBER = "cardNumber";
	/** Qualifier of the <code>BrainTreePaymentInfo.paymentProvider</code> attribute **/
	public static final String PAYMENTPROVIDER = "paymentProvider";
	/** Qualifier of the <code>BrainTreePaymentInfo.nonce</code> attribute **/
	public static final String NONCE = "nonce";
	/** Qualifier of the <code>BrainTreePaymentInfo.customerId</code> attribute **/
	public static final String CUSTOMERID = "customerId";
	/** Qualifier of the <code>BrainTreePaymentInfo.deviceData</code> attribute **/
	public static final String DEVICEDATA = "deviceData";
	/** Qualifier of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute **/
	public static final String LIABILITYSHIFTED = "liabilityShifted";
	/** Qualifier of the <code>BrainTreePaymentInfo.paymentMethodToken</code> attribute **/
	public static final String PAYMENTMETHODTOKEN = "paymentMethodToken";
	/** Qualifier of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute **/
	public static final String USEPAYMENTMETHODTOKEN = "usePaymentMethodToken";
	/** Qualifier of the <code>BrainTreePaymentInfo.expirationMonth</code> attribute **/
	public static final String EXPIRATIONMONTH = "expirationMonth";
	/** Qualifier of the <code>BrainTreePaymentInfo.expirationYear</code> attribute **/
	public static final String EXPIRATIONYEAR = "expirationYear";
	/** Qualifier of the <code>BrainTreePaymentInfo.imageSource</code> attribute **/
	public static final String IMAGESOURCE = "imageSource";
	/** Qualifier of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute **/
	public static final String THREEDSECURECONFIGURATION = "threeDSecureConfiguration";
	/** Qualifier of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute **/
	public static final String ADVANCEDFRAUDTOOLS = "advancedFraudTools";
	/** Qualifier of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute **/
	public static final String ISSKIP3DSECURELIABILITYRESULT = "isSkip3dSecureLiabilityResult";
	/** Qualifier of the <code>BrainTreePaymentInfo.creditCardStatementName</code> attribute **/
	public static final String CREDITCARDSTATEMENTNAME = "creditCardStatementName";
	/** Qualifier of the <code>BrainTreePaymentInfo.brainTreeChannel</code> attribute **/
	public static final String BRAINTREECHANNEL = "brainTreeChannel";
	/** Qualifier of the <code>BrainTreePaymentInfo.merchantAccountIdForCurrentSite</code> attribute **/
	public static final String MERCHANTACCOUNTIDFORCURRENTSITE = "merchantAccountIdForCurrentSite";
	/** Qualifier of the <code>BrainTreePaymentInfo.paymentInfo</code> attribute **/
	public static final String PAYMENTINFO = "paymentInfo";
	/** Qualifier of the <code>BrainTreePaymentInfo.createdAt</code> attribute **/
	public static final String CREATEDAT = "createdAt";
	/** Qualifier of the <code>BrainTreePaymentInfo.updatedAt</code> attribute **/
	public static final String UPDATEDAT = "updatedAt";
	/** Qualifier of the <code>BrainTreePaymentInfo.cardholderName</code> attribute **/
	public static final String CARDHOLDERNAME = "cardholderName";
	/** Qualifier of the <code>BrainTreePaymentInfo.isDefault</code> attribute **/
	public static final String ISDEFAULT = "isDefault";
	/** Qualifier of the <code>BrainTreePaymentInfo.customerLocation</code> attribute **/
	public static final String CUSTOMERLOCATION = "customerLocation";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(PaymentInfo.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CARDTYPE, AttributeMode.INITIAL);
		tmp.put(CARDNUMBER, AttributeMode.INITIAL);
		tmp.put(PAYMENTPROVIDER, AttributeMode.INITIAL);
		tmp.put(NONCE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERID, AttributeMode.INITIAL);
		tmp.put(DEVICEDATA, AttributeMode.INITIAL);
		tmp.put(LIABILITYSHIFTED, AttributeMode.INITIAL);
		tmp.put(PAYMENTMETHODTOKEN, AttributeMode.INITIAL);
		tmp.put(USEPAYMENTMETHODTOKEN, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONMONTH, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONYEAR, AttributeMode.INITIAL);
		tmp.put(IMAGESOURCE, AttributeMode.INITIAL);
		tmp.put(THREEDSECURECONFIGURATION, AttributeMode.INITIAL);
		tmp.put(ADVANCEDFRAUDTOOLS, AttributeMode.INITIAL);
		tmp.put(ISSKIP3DSECURELIABILITYRESULT, AttributeMode.INITIAL);
		tmp.put(CREDITCARDSTATEMENTNAME, AttributeMode.INITIAL);
		tmp.put(BRAINTREECHANNEL, AttributeMode.INITIAL);
		tmp.put(MERCHANTACCOUNTIDFORCURRENTSITE, AttributeMode.INITIAL);
		tmp.put(PAYMENTINFO, AttributeMode.INITIAL);
		tmp.put(CREATEDAT, AttributeMode.INITIAL);
		tmp.put(UPDATEDAT, AttributeMode.INITIAL);
		tmp.put(CARDHOLDERNAME, AttributeMode.INITIAL);
		tmp.put(ISDEFAULT, AttributeMode.INITIAL);
		tmp.put(CUSTOMERLOCATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute.
	 * @return the advancedFraudTools - Advanced Fraud Tools
	 */
	public Boolean isAdvancedFraudTools(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ADVANCEDFRAUDTOOLS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute.
	 * @return the advancedFraudTools - Advanced Fraud Tools
	 */
	public Boolean isAdvancedFraudTools()
	{
		return isAdvancedFraudTools( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @return the advancedFraudTools - Advanced Fraud Tools
	 */
	public boolean isAdvancedFraudToolsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAdvancedFraudTools( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @return the advancedFraudTools - Advanced Fraud Tools
	 */
	public boolean isAdvancedFraudToolsAsPrimitive()
	{
		return isAdvancedFraudToolsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @param value the advancedFraudTools - Advanced Fraud Tools
	 */
	public void setAdvancedFraudTools(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ADVANCEDFRAUDTOOLS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @param value the advancedFraudTools - Advanced Fraud Tools
	 */
	public void setAdvancedFraudTools(final Boolean value)
	{
		setAdvancedFraudTools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @param value the advancedFraudTools - Advanced Fraud Tools
	 */
	public void setAdvancedFraudTools(final SessionContext ctx, final boolean value)
	{
		setAdvancedFraudTools( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.advancedFraudTools</code> attribute. 
	 * @param value the advancedFraudTools - Advanced Fraud Tools
	 */
	public void setAdvancedFraudTools(final boolean value)
	{
		setAdvancedFraudTools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.brainTreeChannel</code> attribute.
	 * @return the brainTreeChannel - BrainTree Channel
	 */
	public String getBrainTreeChannel(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRAINTREECHANNEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.brainTreeChannel</code> attribute.
	 * @return the brainTreeChannel - BrainTree Channel
	 */
	public String getBrainTreeChannel()
	{
		return getBrainTreeChannel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.brainTreeChannel</code> attribute. 
	 * @param value the brainTreeChannel - BrainTree Channel
	 */
	public void setBrainTreeChannel(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRAINTREECHANNEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.brainTreeChannel</code> attribute. 
	 * @param value the brainTreeChannel - BrainTree Channel
	 */
	public void setBrainTreeChannel(final String value)
	{
		setBrainTreeChannel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardholderName</code> attribute.
	 * @return the cardholderName
	 */
	public String getCardholderName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDHOLDERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardholderName</code> attribute.
	 * @return the cardholderName
	 */
	public String getCardholderName()
	{
		return getCardholderName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardholderName</code> attribute. 
	 * @param value the cardholderName
	 */
	public void setCardholderName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDHOLDERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardholderName</code> attribute. 
	 * @param value the cardholderName
	 */
	public void setCardholderName(final String value)
	{
		setCardholderName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardNumber</code> attribute.
	 * @return the cardNumber - Card number
	 */
	public String getCardNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardNumber</code> attribute.
	 * @return the cardNumber - Card number
	 */
	public String getCardNumber()
	{
		return getCardNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardNumber</code> attribute. 
	 * @param value the cardNumber - Card number
	 */
	public void setCardNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardNumber</code> attribute. 
	 * @param value the cardNumber - Card number
	 */
	public void setCardNumber(final String value)
	{
		setCardNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardType</code> attribute.
	 * @return the cardType - Card type
	 */
	public EnumerationValue getCardType(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, CARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.cardType</code> attribute.
	 * @return the cardType - Card type
	 */
	public EnumerationValue getCardType()
	{
		return getCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardType</code> attribute. 
	 * @param value the cardType - Card type
	 */
	public void setCardType(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, CARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.cardType</code> attribute. 
	 * @param value the cardType - Card type
	 */
	public void setCardType(final EnumerationValue value)
	{
		setCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.createdAt</code> attribute.
	 * @return the createdAt
	 */
	public Date getCreatedAt(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, CREATEDAT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.createdAt</code> attribute.
	 * @return the createdAt
	 */
	public Date getCreatedAt()
	{
		return getCreatedAt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.createdAt</code> attribute. 
	 * @param value the createdAt
	 */
	public void setCreatedAt(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, CREATEDAT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.createdAt</code> attribute. 
	 * @param value the createdAt
	 */
	public void setCreatedAt(final Date value)
	{
		setCreatedAt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.creditCardStatementName</code> attribute.
	 * @return the creditCardStatementName - CreditCard Statement Name
	 */
	public String getCreditCardStatementName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDSTATEMENTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.creditCardStatementName</code> attribute.
	 * @return the creditCardStatementName - CreditCard Statement Name
	 */
	public String getCreditCardStatementName()
	{
		return getCreditCardStatementName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.creditCardStatementName</code> attribute. 
	 * @param value the creditCardStatementName - CreditCard Statement Name
	 */
	public void setCreditCardStatementName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDSTATEMENTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.creditCardStatementName</code> attribute. 
	 * @param value the creditCardStatementName - CreditCard Statement Name
	 */
	public void setCreditCardStatementName(final String value)
	{
		setCreditCardStatementName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.customerId</code> attribute.
	 * @return the customerId - Customer Id in BrainTree Vault
	 */
	public String getCustomerId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.customerId</code> attribute.
	 * @return the customerId - Customer Id in BrainTree Vault
	 */
	public String getCustomerId()
	{
		return getCustomerId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.customerId</code> attribute. 
	 * @param value the customerId - Customer Id in BrainTree Vault
	 */
	public void setCustomerId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.customerId</code> attribute. 
	 * @param value the customerId - Customer Id in BrainTree Vault
	 */
	public void setCustomerId(final String value)
	{
		setCustomerId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.customerLocation</code> attribute.
	 * @return the customerLocation
	 */
	public String getCustomerLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERLOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.customerLocation</code> attribute.
	 * @return the customerLocation
	 */
	public String getCustomerLocation()
	{
		return getCustomerLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.customerLocation</code> attribute. 
	 * @param value the customerLocation
	 */
	public void setCustomerLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERLOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.customerLocation</code> attribute. 
	 * @param value the customerLocation
	 */
	public void setCustomerLocation(final String value)
	{
		setCustomerLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.deviceData</code> attribute.
	 * @return the deviceData - Device Data
	 */
	public String getDeviceData(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEVICEDATA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.deviceData</code> attribute.
	 * @return the deviceData - Device Data
	 */
	public String getDeviceData()
	{
		return getDeviceData( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.deviceData</code> attribute. 
	 * @param value the deviceData - Device Data
	 */
	public void setDeviceData(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEVICEDATA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.deviceData</code> attribute. 
	 * @param value the deviceData - Device Data
	 */
	public void setDeviceData(final String value)
	{
		setDeviceData( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.expirationMonth</code> attribute.
	 * @return the expirationMonth - Expires date
	 */
	public String getExpirationMonth(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPIRATIONMONTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.expirationMonth</code> attribute.
	 * @return the expirationMonth - Expires date
	 */
	public String getExpirationMonth()
	{
		return getExpirationMonth( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.expirationMonth</code> attribute. 
	 * @param value the expirationMonth - Expires date
	 */
	public void setExpirationMonth(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPIRATIONMONTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.expirationMonth</code> attribute. 
	 * @param value the expirationMonth - Expires date
	 */
	public void setExpirationMonth(final String value)
	{
		setExpirationMonth( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.expirationYear</code> attribute.
	 * @return the expirationYear - Expires date
	 */
	public String getExpirationYear(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPIRATIONYEAR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.expirationYear</code> attribute.
	 * @return the expirationYear - Expires date
	 */
	public String getExpirationYear()
	{
		return getExpirationYear( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.expirationYear</code> attribute. 
	 * @param value the expirationYear - Expires date
	 */
	public void setExpirationYear(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPIRATIONYEAR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.expirationYear</code> attribute. 
	 * @param value the expirationYear - Expires date
	 */
	public void setExpirationYear(final String value)
	{
		setExpirationYear( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.imageSource</code> attribute.
	 * @return the imageSource - Image card url
	 */
	public String getImageSource(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMAGESOURCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.imageSource</code> attribute.
	 * @return the imageSource - Image card url
	 */
	public String getImageSource()
	{
		return getImageSource( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.imageSource</code> attribute. 
	 * @param value the imageSource - Image card url
	 */
	public void setImageSource(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMAGESOURCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.imageSource</code> attribute. 
	 * @param value the imageSource - Image card url
	 */
	public void setImageSource(final String value)
	{
		setImageSource( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isDefault</code> attribute.
	 * @return the isDefault
	 */
	public Boolean isIsDefault(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISDEFAULT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isDefault</code> attribute.
	 * @return the isDefault
	 */
	public Boolean isIsDefault()
	{
		return isIsDefault( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @return the isDefault
	 */
	public boolean isIsDefaultAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsDefault( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @return the isDefault
	 */
	public boolean isIsDefaultAsPrimitive()
	{
		return isIsDefaultAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @param value the isDefault
	 */
	public void setIsDefault(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISDEFAULT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @param value the isDefault
	 */
	public void setIsDefault(final Boolean value)
	{
		setIsDefault( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @param value the isDefault
	 */
	public void setIsDefault(final SessionContext ctx, final boolean value)
	{
		setIsDefault( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isDefault</code> attribute. 
	 * @param value the isDefault
	 */
	public void setIsDefault(final boolean value)
	{
		setIsDefault( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute.
	 * @return the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public Boolean isIsSkip3dSecureLiabilityResult(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISSKIP3DSECURELIABILITYRESULT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute.
	 * @return the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public Boolean isIsSkip3dSecureLiabilityResult()
	{
		return isIsSkip3dSecureLiabilityResult( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @return the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public boolean isIsSkip3dSecureLiabilityResultAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsSkip3dSecureLiabilityResult( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @return the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public boolean isIsSkip3dSecureLiabilityResultAsPrimitive()
	{
		return isIsSkip3dSecureLiabilityResultAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @param value the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public void setIsSkip3dSecureLiabilityResult(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISSKIP3DSECURELIABILITYRESULT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @param value the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public void setIsSkip3dSecureLiabilityResult(final Boolean value)
	{
		setIsSkip3dSecureLiabilityResult( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @param value the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public void setIsSkip3dSecureLiabilityResult(final SessionContext ctx, final boolean value)
	{
		setIsSkip3dSecureLiabilityResult( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.isSkip3dSecureLiabilityResult</code> attribute. 
	 * @param value the isSkip3dSecureLiabilityResult - skip 3D Secure
	 */
	public void setIsSkip3dSecureLiabilityResult(final boolean value)
	{
		setIsSkip3dSecureLiabilityResult( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute.
	 * @return the liabilityShifted - Liability shifted on 3D Secure
	 */
	public Boolean isLiabilityShifted(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, LIABILITYSHIFTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute.
	 * @return the liabilityShifted - Liability shifted on 3D Secure
	 */
	public Boolean isLiabilityShifted()
	{
		return isLiabilityShifted( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @return the liabilityShifted - Liability shifted on 3D Secure
	 */
	public boolean isLiabilityShiftedAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isLiabilityShifted( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @return the liabilityShifted - Liability shifted on 3D Secure
	 */
	public boolean isLiabilityShiftedAsPrimitive()
	{
		return isLiabilityShiftedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @param value the liabilityShifted - Liability shifted on 3D Secure
	 */
	public void setLiabilityShifted(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, LIABILITYSHIFTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @param value the liabilityShifted - Liability shifted on 3D Secure
	 */
	public void setLiabilityShifted(final Boolean value)
	{
		setLiabilityShifted( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @param value the liabilityShifted - Liability shifted on 3D Secure
	 */
	public void setLiabilityShifted(final SessionContext ctx, final boolean value)
	{
		setLiabilityShifted( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.liabilityShifted</code> attribute. 
	 * @param value the liabilityShifted - Liability shifted on 3D Secure
	 */
	public void setLiabilityShifted(final boolean value)
	{
		setLiabilityShifted( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.merchantAccountIdForCurrentSite</code> attribute.
	 * @return the merchantAccountIdForCurrentSite - Merchant Account Id For Current Site
	 */
	public String getMerchantAccountIdForCurrentSite(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MERCHANTACCOUNTIDFORCURRENTSITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.merchantAccountIdForCurrentSite</code> attribute.
	 * @return the merchantAccountIdForCurrentSite - Merchant Account Id For Current Site
	 */
	public String getMerchantAccountIdForCurrentSite()
	{
		return getMerchantAccountIdForCurrentSite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.merchantAccountIdForCurrentSite</code> attribute. 
	 * @param value the merchantAccountIdForCurrentSite - Merchant Account Id For Current Site
	 */
	public void setMerchantAccountIdForCurrentSite(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MERCHANTACCOUNTIDFORCURRENTSITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.merchantAccountIdForCurrentSite</code> attribute. 
	 * @param value the merchantAccountIdForCurrentSite - Merchant Account Id For Current Site
	 */
	public void setMerchantAccountIdForCurrentSite(final String value)
	{
		setMerchantAccountIdForCurrentSite( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.nonce</code> attribute.
	 * @return the nonce - Nonce for authorization client credit card
	 */
	public String getNonce(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NONCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.nonce</code> attribute.
	 * @return the nonce - Nonce for authorization client credit card
	 */
	public String getNonce()
	{
		return getNonce( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.nonce</code> attribute. 
	 * @param value the nonce - Nonce for authorization client credit card
	 */
	public void setNonce(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NONCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.nonce</code> attribute. 
	 * @param value the nonce - Nonce for authorization client credit card
	 */
	public void setNonce(final String value)
	{
		setNonce( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentInfo</code> attribute.
	 * @return the paymentInfo
	 */
	public String getPaymentInfo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentInfo</code> attribute.
	 * @return the paymentInfo
	 */
	public String getPaymentInfo()
	{
		return getPaymentInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentInfo</code> attribute. 
	 * @param value the paymentInfo
	 */
	public void setPaymentInfo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentInfo</code> attribute. 
	 * @param value the paymentInfo
	 */
	public void setPaymentInfo(final String value)
	{
		setPaymentInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentMethodToken</code> attribute.
	 * @return the paymentMethodToken - Payment Method Token
	 */
	public String getPaymentMethodToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTMETHODTOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentMethodToken</code> attribute.
	 * @return the paymentMethodToken - Payment Method Token
	 */
	public String getPaymentMethodToken()
	{
		return getPaymentMethodToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentMethodToken</code> attribute. 
	 * @param value the paymentMethodToken - Payment Method Token
	 */
	public void setPaymentMethodToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTMETHODTOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentMethodToken</code> attribute. 
	 * @param value the paymentMethodToken - Payment Method Token
	 */
	public void setPaymentMethodToken(final String value)
	{
		setPaymentMethodToken( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentProvider</code> attribute.
	 * @return the paymentProvider - Payment provider
	 */
	public String getPaymentProvider(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTPROVIDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.paymentProvider</code> attribute.
	 * @return the paymentProvider - Payment provider
	 */
	public String getPaymentProvider()
	{
		return getPaymentProvider( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentProvider</code> attribute. 
	 * @param value the paymentProvider - Payment provider
	 */
	public void setPaymentProvider(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTPROVIDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.paymentProvider</code> attribute. 
	 * @param value the paymentProvider - Payment provider
	 */
	public void setPaymentProvider(final String value)
	{
		setPaymentProvider( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute.
	 * @return the threeDSecureConfiguration - 3D Secure
	 */
	public Boolean isThreeDSecureConfiguration(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, THREEDSECURECONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute.
	 * @return the threeDSecureConfiguration - 3D Secure
	 */
	public Boolean isThreeDSecureConfiguration()
	{
		return isThreeDSecureConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @return the threeDSecureConfiguration - 3D Secure
	 */
	public boolean isThreeDSecureConfigurationAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isThreeDSecureConfiguration( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @return the threeDSecureConfiguration - 3D Secure
	 */
	public boolean isThreeDSecureConfigurationAsPrimitive()
	{
		return isThreeDSecureConfigurationAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @param value the threeDSecureConfiguration - 3D Secure
	 */
	public void setThreeDSecureConfiguration(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, THREEDSECURECONFIGURATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @param value the threeDSecureConfiguration - 3D Secure
	 */
	public void setThreeDSecureConfiguration(final Boolean value)
	{
		setThreeDSecureConfiguration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @param value the threeDSecureConfiguration - 3D Secure
	 */
	public void setThreeDSecureConfiguration(final SessionContext ctx, final boolean value)
	{
		setThreeDSecureConfiguration( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.threeDSecureConfiguration</code> attribute. 
	 * @param value the threeDSecureConfiguration - 3D Secure
	 */
	public void setThreeDSecureConfiguration(final boolean value)
	{
		setThreeDSecureConfiguration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.updatedAt</code> attribute.
	 * @return the updatedAt
	 */
	public Date getUpdatedAt(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, UPDATEDAT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.updatedAt</code> attribute.
	 * @return the updatedAt
	 */
	public Date getUpdatedAt()
	{
		return getUpdatedAt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.updatedAt</code> attribute. 
	 * @param value the updatedAt
	 */
	public void setUpdatedAt(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, UPDATEDAT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.updatedAt</code> attribute. 
	 * @param value the updatedAt
	 */
	public void setUpdatedAt(final Date value)
	{
		setUpdatedAt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute.
	 * @return the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public Boolean isUsePaymentMethodToken(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USEPAYMENTMETHODTOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute.
	 * @return the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public Boolean isUsePaymentMethodToken()
	{
		return isUsePaymentMethodToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @return the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public boolean isUsePaymentMethodTokenAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUsePaymentMethodToken( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @return the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public boolean isUsePaymentMethodTokenAsPrimitive()
	{
		return isUsePaymentMethodTokenAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @param value the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public void setUsePaymentMethodToken(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USEPAYMENTMETHODTOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @param value the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public void setUsePaymentMethodToken(final Boolean value)
	{
		setUsePaymentMethodToken( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @param value the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public void setUsePaymentMethodToken(final SessionContext ctx, final boolean value)
	{
		setUsePaymentMethodToken( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BrainTreePaymentInfo.usePaymentMethodToken</code> attribute. 
	 * @param value the usePaymentMethodToken - Parameter responsible for decision what type of authorization will be choose
	 */
	public void setUsePaymentMethodToken(final boolean value)
	{
		setUsePaymentMethodToken( getSession().getSessionContext(), value );
	}
	
}
