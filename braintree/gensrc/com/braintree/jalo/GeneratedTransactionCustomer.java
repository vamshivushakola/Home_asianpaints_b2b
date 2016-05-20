/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 20, 2016 4:10:43 PM                     ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem TransactionCustomer}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTransactionCustomer extends GenericItem
{
	/** Qualifier of the <code>TransactionCustomer.customerID</code> attribute **/
	public static final String CUSTOMERID = "customerID";
	/** Qualifier of the <code>TransactionCustomer.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>TransactionCustomer.email</code> attribute **/
	public static final String EMAIL = "email";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CUSTOMERID, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(EMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.customerID</code> attribute.
	 * @return the customerID
	 */
	public String getCustomerID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.customerID</code> attribute.
	 * @return the customerID
	 */
	public String getCustomerID()
	{
		return getCustomerID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.customerID</code> attribute. 
	 * @param value the customerID
	 */
	public void setCustomerID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.customerID</code> attribute. 
	 * @param value the customerID
	 */
	public void setCustomerID(final String value)
	{
		setCustomerID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.email</code> attribute.
	 * @return the email
	 */
	public String getEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.email</code> attribute.
	 * @return the email
	 */
	public String getEmail()
	{
		return getEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final String value)
	{
		setEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransactionCustomer.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransactionCustomer.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
}
