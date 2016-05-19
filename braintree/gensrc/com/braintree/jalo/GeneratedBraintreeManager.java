/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at May 19, 2016 3:58:04 PM                     ---
 * ----------------------------------------------------------------
 */
package com.braintree.jalo;

import com.braintree.constants.BraintreeConstants;
import com.braintree.jalo.BrainTreePaymentInfo;
import com.braintree.jalo.BrainTreeTransactionDetail;
import com.braintree.jalo.BraintreeCustomerDetails;
import com.braintree.jalo.TransactionCreditCardInfo;
import com.braintree.jalo.TransactionCustomer;
import com.braintree.jalo.TransactionPayPalInfo;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>BraintreeManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBraintreeManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("braintreeCustomerId", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.Customer", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("brainTreeAddressId", AttributeMode.INITIAL);
		tmp.put("zone", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.Address", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.brainTreeAddressId</code> attribute.
	 * @return the brainTreeAddressId - Attribute is used for mapping hybris address to braintree address
	 */
	public String getBrainTreeAddressId(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, BraintreeConstants.Attributes.Address.BRAINTREEADDRESSID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.brainTreeAddressId</code> attribute.
	 * @return the brainTreeAddressId - Attribute is used for mapping hybris address to braintree address
	 */
	public String getBrainTreeAddressId(final Address item)
	{
		return getBrainTreeAddressId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.brainTreeAddressId</code> attribute. 
	 * @param value the brainTreeAddressId - Attribute is used for mapping hybris address to braintree address
	 */
	public void setBrainTreeAddressId(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, BraintreeConstants.Attributes.Address.BRAINTREEADDRESSID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.brainTreeAddressId</code> attribute. 
	 * @param value the brainTreeAddressId - Attribute is used for mapping hybris address to braintree address
	 */
	public void setBrainTreeAddressId(final Address item, final String value)
	{
		setBrainTreeAddressId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.braintreeCustomerId</code> attribute.
	 * @return the braintreeCustomerId - Attribute is used for mapping customer to Brain Tree vault
	 */
	public String getBraintreeCustomerId(final SessionContext ctx, final Customer item)
	{
		return (String)item.getProperty( ctx, BraintreeConstants.Attributes.Customer.BRAINTREECUSTOMERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.braintreeCustomerId</code> attribute.
	 * @return the braintreeCustomerId - Attribute is used for mapping customer to Brain Tree vault
	 */
	public String getBraintreeCustomerId(final Customer item)
	{
		return getBraintreeCustomerId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.braintreeCustomerId</code> attribute. 
	 * @param value the braintreeCustomerId - Attribute is used for mapping customer to Brain Tree vault
	 */
	public void setBraintreeCustomerId(final SessionContext ctx, final Customer item, final String value)
	{
		item.setProperty(ctx, BraintreeConstants.Attributes.Customer.BRAINTREECUSTOMERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.braintreeCustomerId</code> attribute. 
	 * @param value the braintreeCustomerId - Attribute is used for mapping customer to Brain Tree vault
	 */
	public void setBraintreeCustomerId(final Customer item, final String value)
	{
		setBraintreeCustomerId( getSession().getSessionContext(), item, value );
	}
	
	public BraintreeCustomerDetails createBraintreeCustomerDetails(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.BRAINTREECUSTOMERDETAILS );
			return (BraintreeCustomerDetails)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BraintreeCustomerDetails : "+e.getMessage(), 0 );
		}
	}
	
	public BraintreeCustomerDetails createBraintreeCustomerDetails(final Map attributeValues)
	{
		return createBraintreeCustomerDetails( getSession().getSessionContext(), attributeValues );
	}
	
	public BrainTreePaymentInfo createBrainTreePaymentInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.BRAINTREEPAYMENTINFO );
			return (BrainTreePaymentInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BrainTreePaymentInfo : "+e.getMessage(), 0 );
		}
	}
	
	public BrainTreePaymentInfo createBrainTreePaymentInfo(final Map attributeValues)
	{
		return createBrainTreePaymentInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public BrainTreeTransactionDetail createBrainTreeTransactionDetail(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.BRAINTREETRANSACTIONDETAIL );
			return (BrainTreeTransactionDetail)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BrainTreeTransactionDetail : "+e.getMessage(), 0 );
		}
	}
	
	public BrainTreeTransactionDetail createBrainTreeTransactionDetail(final Map attributeValues)
	{
		return createBrainTreeTransactionDetail( getSession().getSessionContext(), attributeValues );
	}
	
	public TransactionCreditCardInfo createTransactionCreditCardInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.TRANSACTIONCREDITCARDINFO );
			return (TransactionCreditCardInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TransactionCreditCardInfo : "+e.getMessage(), 0 );
		}
	}
	
	public TransactionCreditCardInfo createTransactionCreditCardInfo(final Map attributeValues)
	{
		return createTransactionCreditCardInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public TransactionCustomer createTransactionCustomer(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.TRANSACTIONCUSTOMER );
			return (TransactionCustomer)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TransactionCustomer : "+e.getMessage(), 0 );
		}
	}
	
	public TransactionCustomer createTransactionCustomer(final Map attributeValues)
	{
		return createTransactionCustomer( getSession().getSessionContext(), attributeValues );
	}
	
	public TransactionPayPalInfo createTransactionPayPalInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( BraintreeConstants.TC.TRANSACTIONPAYPALINFO );
			return (TransactionPayPalInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TransactionPayPalInfo : "+e.getMessage(), 0 );
		}
	}
	
	public TransactionPayPalInfo createTransactionPayPalInfo(final Map attributeValues)
	{
		return createTransactionPayPalInfo( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return BraintreeConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.zone</code> attribute.
	 * @return the zone - Attribute is used for custom region name
	 */
	public String getZone(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, BraintreeConstants.Attributes.Address.ZONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.zone</code> attribute.
	 * @return the zone - Attribute is used for custom region name
	 */
	public String getZone(final Address item)
	{
		return getZone( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.zone</code> attribute. 
	 * @param value the zone - Attribute is used for custom region name
	 */
	public void setZone(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, BraintreeConstants.Attributes.Address.ZONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.zone</code> attribute. 
	 * @param value the zone - Attribute is used for custom region name
	 */
	public void setZone(final Address item, final String value)
	{
		setZone( getSession().getSessionContext(), item, value );
	}
	
}
