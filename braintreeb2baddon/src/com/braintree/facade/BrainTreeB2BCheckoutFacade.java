/**
 *
 */
package com.braintree.facade;

import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BAcceleratorCheckoutFacade;
import de.hybris.platform.b2bacceleratorservices.enums.CheckoutPaymentType;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.core.model.order.CartModel;

import javax.annotation.Resource;

import com.braintree.facade.impl.BrainTreeCheckoutFacade;


/**
 * @author Roman_Chupa
 *
 */
public class BrainTreeB2BCheckoutFacade extends DefaultB2BAcceleratorCheckoutFacade
{
	@Resource(name = "brainTreeCheckoutFacade")
	private BrainTreeCheckoutFacade brainTreeCheckoutFacade;

	@Override
	public boolean authorizePayment(final String securityCode)
	{
		final CartModel cart = getCartService().getSessionCart();
		if (CheckoutPaymentType.ACCOUNT.equals(cart.getPaymentType()))
		{
			return super.authorizePayment(securityCode);
		}
		return brainTreeCheckoutFacade.authorizePayment(securityCode);
	}

	@Override
	protected CCPaymentInfoData getPaymentDetails()
	{
		return brainTreeCheckoutFacade.getPaymentDetails();
	}

	@Override
	public boolean setPaymentDetails(final String paymentInfoId)
	{
		return brainTreeCheckoutFacade.setPaymentDetails(paymentInfoId);
	}

	public void setCardPaymentType()
	{
		final CartModel cart = getCart();
		cart.setPaymentType(CheckoutPaymentType.CARD);
		getModelService().save(cart);
	}

}
