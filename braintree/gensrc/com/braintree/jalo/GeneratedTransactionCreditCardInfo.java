/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 18, 2016 2:17:03 PM                     ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem TransactionCreditCardInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTransactionCreditCardInfo extends GenericItem
{
	/** Qualifier of the <code>TransactionCreditCardInfo.cardType</code> attribute **/
	public static final String CARDTYPE = "cardType";
	/** Qualifier of the <code>TransactionCreditCardInfo.cardholderName</code> attribute **/
	public static final String CARDHOLDERNAME = "cardholderName";
	/** Qualifier of the <code>TransactionCreditCardInfo.creditCardNumber</code> attribute **/
	public static final String CREDITCARDNUMBER = "creditCardNumber";
	/** Qualifier of the <code>TransactionCreditCardInfo.expirationDate</code> attribute **/
	public static final String EXPIRATIONDATE = "expirationDate";
	/** Qualifier of the <code>TransactionCreditCardInfo.uniqueNumberIdentifier</code> attribute **/
	public static final String UNIQUENUMBERIDENTIFIER = "uniqueNumberIdentifier";
	/** Qualifier of the <code>TransactionCreditCardInfo.token</code> attribute **/
	public static final String TOKEN = "token";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CARDTYPE, AttributeMode.INITIAL);
		tmp.put(CARDHOLDERNAME, AttributeMode.INITIAL);
		tmp.put(CREDITCARDNUMBER, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONDATE, AttributeMode.INITIAL);
		tmp.put(UNIQUENUMBERIDENTIFIER, AttributeMode.INITIAL);
		tmp.put(TOKEN, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.cardholderName</code> attribute.
	 * @return the cardholderName
	 */
	public String getCardholderName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDHOLDERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.cardholderName</code> attribute.
	 * @return the cardholderName
	 */
	public String getCardholderName()
	{
		return getCardholderName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.cardholderName</code> attribute. 
	 * @param value the cardholderName
	 */
	public void setCardholderName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDHOLDERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.cardholderName</code> attribute. 
	 * @param value the cardholderName
	 */
	public void setCardholderName(final String value)
	{
		setCardholderName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.cardType</code> attribute.
	 * @return the cardType
	 */
	public String getCardType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.cardType</code> attribute.
	 * @return the cardType
	 */
	public String getCardType()
	{
		return getCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.cardType</code> attribute. 
	 * @param value the cardType
	 */
	public void setCardType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.cardType</code> attribute. 
	 * @param value the cardType
	 */
	public void setCardType(final String value)
	{
		setCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.creditCardNumber</code> attribute.
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.creditCardNumber</code> attribute.
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber()
	{
		return getCreditCardNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.creditCardNumber</code> attribute. 
	 * @param value the creditCardNumber
	 */
	public void setCreditCardNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.creditCardNumber</code> attribute. 
	 * @param value the creditCardNumber
	 */
	public void setCreditCardNumber(final String value)
	{
		setCreditCardNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.expirationDate</code> attribute.
	 * @return the expirationDate
	 */
	public String getExpirationDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPIRATIONDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.expirationDate</code> attribute.
	 * @return the expirationDate
	 */
	public String getExpirationDate()
	{
		return getExpirationDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.expirationDate</code> attribute. 
	 * @param value the expirationDate
	 */
	public void setExpirationDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPIRATIONDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.expirationDate</code> attribute. 
	 * @param value the expirationDate
	 */
	public void setExpirationDate(final String value)
	{
		setExpirationDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.token</code> attribute.
	 * @return the token
	 */
	public String getToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.token</code> attribute.
	 * @return the token
	 */
	public String getToken()
	{
		return getToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final String value)
	{
		setToken( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.uniqueNumberIdentifier</code> attribute.
	 * @return the uniqueNumberIdentifier
	 */
	public String getUniqueNumberIdentifier(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNIQUENUMBERIDENTIFIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCreditCardInfo.uniqueNumberIdentifier</code> attribute.
	 * @return the uniqueNumberIdentifier
	 */
	public String getUniqueNumberIdentifier()
	{
		return getUniqueNumberIdentifier( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.uniqueNumberIdentifier</code> attribute. 
	 * @param value the uniqueNumberIdentifier
	 */
	public void setUniqueNumberIdentifier(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNIQUENUMBERIDENTIFIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCreditCardInfo.uniqueNumberIdentifier</code> attribute. 
	 * @param value the uniqueNumberIdentifier
	 */
	public void setUniqueNumberIdentifier(final String value)
	{
		setUniqueNumberIdentifier( getSession().getSessionContext(), value );
	}
	
}
