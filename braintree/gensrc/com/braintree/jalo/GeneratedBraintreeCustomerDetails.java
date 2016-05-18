/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 18, 2016 2:17:03 PM                     ---
 * ----------------------------------------------------------------
 */
package com.braintree.jalo;

import com.braintree.constants.BraintreeConstants;
import com.braintree.jalo.BrainTreePaymentInfo;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.user.Address;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BraintreeCustomerDetails}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBraintreeCustomerDetails extends GenericItem
{
	/** Qualifier of the <code>BraintreeCustomerDetails.createdAt</code> attribute **/
	public static final String CREATEDAT = "createdAt";
	/** Qualifier of the <code>BraintreeCustomerDetails.updatedAt</code> attribute **/
	public static final String UPDATEDAT = "updatedAt";
	/** Qualifier of the <code>BraintreeCustomerDetails.company</code> attribute **/
	public static final String COMPANY = "company";
	/** Qualifier of the <code>BraintreeCustomerDetails.email</code> attribute **/
	public static final String EMAIL = "email";
	/** Qualifier of the <code>BraintreeCustomerDetails.fax</code> attribute **/
	public static final String FAX = "fax";
	/** Qualifier of the <code>BraintreeCustomerDetails.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>BraintreeCustomerDetails.id</code> attribute **/
	public static final String ID = "id";
	/** Qualifier of the <code>BraintreeCustomerDetails.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>BraintreeCustomerDetails.phone</code> attribute **/
	public static final String PHONE = "phone";
	/** Qualifier of the <code>BraintreeCustomerDetails.website</code> attribute **/
	public static final String WEBSITE = "website";
	/** Qualifier of the <code>BraintreeCustomerDetails.customFields</code> attribute **/
	public static final String CUSTOMFIELDS = "customFields";
	/** Qualifier of the <code>BraintreeCustomerDetails.addresses</code> attribute **/
	public static final String ADDRESSES = "addresses";
	/** Qualifier of the <code>BraintreeCustomerDetails.paymentMethods</code> attribute **/
	public static final String PAYMENTMETHODS = "paymentMethods";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CREATEDAT, AttributeMode.INITIAL);
		tmp.put(UPDATEDAT, AttributeMode.INITIAL);
		tmp.put(COMPANY, AttributeMode.INITIAL);
		tmp.put(EMAIL, AttributeMode.INITIAL);
		tmp.put(FAX, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(ID, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(PHONE, AttributeMode.INITIAL);
		tmp.put(WEBSITE, AttributeMode.INITIAL);
		tmp.put(CUSTOMFIELDS, AttributeMode.INITIAL);
		tmp.put(ADDRESSES, AttributeMode.INITIAL);
		tmp.put(PAYMENTMETHODS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.addresses</code> attribute.
	 * @return the addresses
	 */
	public List<Address> getAddresses(final SessionContext ctx)
	{
		List<Address> coll = (List<Address>)getProperty( ctx, ADDRESSES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.addresses</code> attribute.
	 * @return the addresses
	 */
	public List<Address> getAddresses()
	{
		return getAddresses( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.addresses</code> attribute. 
	 * @param value the addresses
	 */
	public void setAddresses(final SessionContext ctx, final List<Address> value)
	{
		setProperty(ctx, ADDRESSES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.addresses</code> attribute. 
	 * @param value the addresses
	 */
	public void setAddresses(final List<Address> value)
	{
		setAddresses( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.company</code> attribute.
	 * @return the company
	 */
	public String getCompany(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMPANY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.company</code> attribute.
	 * @return the company
	 */
	public String getCompany()
	{
		return getCompany( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.company</code> attribute. 
	 * @param value the company
	 */
	public void setCompany(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMPANY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.company</code> attribute. 
	 * @param value the company
	 */
	public void setCompany(final String value)
	{
		setCompany( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.createdAt</code> attribute.
	 * @return the createdAt
	 */
	public Date getCreatedAt(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, CREATEDAT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.createdAt</code> attribute.
	 * @return the createdAt
	 */
	public Date getCreatedAt()
	{
		return getCreatedAt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.createdAt</code> attribute. 
	 * @param value the createdAt
	 */
	public void setCreatedAt(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, CREATEDAT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.createdAt</code> attribute. 
	 * @param value the createdAt
	 */
	public void setCreatedAt(final Date value)
	{
		setCreatedAt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.customFields</code> attribute.
	 * @return the customFields
	 */
	public Map<String,String> getAllCustomFields(final SessionContext ctx)
	{
		Map<String,String> map = (Map<String,String>)getProperty( ctx, CUSTOMFIELDS);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.customFields</code> attribute.
	 * @return the customFields
	 */
	public Map<String,String> getAllCustomFields()
	{
		return getAllCustomFields( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.customFields</code> attribute. 
	 * @param value the customFields
	 */
	public void setAllCustomFields(final SessionContext ctx, final Map<String,String> value)
	{
		setProperty(ctx, CUSTOMFIELDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.customFields</code> attribute. 
	 * @param value the customFields
	 */
	public void setAllCustomFields(final Map<String,String> value)
	{
		setAllCustomFields( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.email</code> attribute.
	 * @return the email
	 */
	public String getEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.email</code> attribute.
	 * @return the email
	 */
	public String getEmail()
	{
		return getEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final String value)
	{
		setEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.fax</code> attribute.
	 * @return the fax
	 */
	public String getFax(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.fax</code> attribute.
	 * @return the fax
	 */
	public String getFax()
	{
		return getFax( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.fax</code> attribute. 
	 * @param value the fax
	 */
	public void setFax(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.fax</code> attribute. 
	 * @param value the fax
	 */
	public void setFax(final String value)
	{
		setFax( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.id</code> attribute.
	 * @return the id
	 */
	public String getId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.id</code> attribute.
	 * @return the id
	 */
	public String getId()
	{
		return getId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final String value)
	{
		setId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.paymentMethods</code> attribute.
	 * @return the paymentMethods
	 */
	public List<BrainTreePaymentInfo> getPaymentMethods(final SessionContext ctx)
	{
		List<BrainTreePaymentInfo> coll = (List<BrainTreePaymentInfo>)getProperty( ctx, PAYMENTMETHODS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.paymentMethods</code> attribute.
	 * @return the paymentMethods
	 */
	public List<BrainTreePaymentInfo> getPaymentMethods()
	{
		return getPaymentMethods( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.paymentMethods</code> attribute. 
	 * @param value the paymentMethods
	 */
	public void setPaymentMethods(final SessionContext ctx, final List<BrainTreePaymentInfo> value)
	{
		setProperty(ctx, PAYMENTMETHODS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.paymentMethods</code> attribute. 
	 * @param value the paymentMethods
	 */
	public void setPaymentMethods(final List<BrainTreePaymentInfo> value)
	{
		setPaymentMethods( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone()
	{
		return getPhone( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final String value)
	{
		setPhone( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.updatedAt</code> attribute.
	 * @return the updatedAt
	 */
	public Date getUpdatedAt(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, UPDATEDAT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.updatedAt</code> attribute.
	 * @return the updatedAt
	 */
	public Date getUpdatedAt()
	{
		return getUpdatedAt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.updatedAt</code> attribute. 
	 * @param value the updatedAt
	 */
	public void setUpdatedAt(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, UPDATEDAT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.updatedAt</code> attribute. 
	 * @param value the updatedAt
	 */
	public void setUpdatedAt(final Date value)
	{
		setUpdatedAt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.website</code> attribute.
	 * @return the website
	 */
	public String getWebsite(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WEBSITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BraintreeCustomerDetails.website</code> attribute.
	 * @return the website
	 */
	public String getWebsite()
	{
		return getWebsite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.website</code> attribute. 
	 * @param value the website
	 */
	public void setWebsite(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WEBSITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BraintreeCustomerDetails.website</code> attribute. 
	 * @param value the website
	 */
	public void setWebsite(final String value)
	{
		setWebsite( getSession().getSessionContext(), value );
	}
	
}
