package com.braintree.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2bacceleratoraddon.controllers.pages.checkout.steps.SummaryCheckoutStepController;
import de.hybris.platform.b2bacceleratoraddon.forms.PlaceOrderForm;
import de.hybris.platform.b2bacceleratorfacades.checkout.data.PlaceOrderData;
import de.hybris.platform.b2bacceleratorfacades.exception.EntityValidationException;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.util.localization.Localization;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintree.constants.Braintreeb2baddonWebConstants;
import com.braintree.facade.impl.BrainTreeCheckoutFacade;



@Controller
@RequestMapping(value = "/checkout/multi/summary")
public class BrainTreeSummaryCheckoutStepController extends SummaryCheckoutStepController
{

	private static final Logger LOG = Logger.getLogger(BrainTreeSummaryCheckoutStepController.class);

	@Resource(name = "brainTreeCheckoutFacade")
	private BrainTreeCheckoutFacade brainTreeCheckoutFacade;

	@Override
	@RequestMapping(value = "/placeOrder")
	@RequireHardLogIn
	public String placeOrder(@ModelAttribute("placeOrderForm") final PlaceOrderForm placeOrderForm, final Model model,
			final RedirectAttributes redirectModel)
					throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		if (validateOrderForm(placeOrderForm, model))
		{
			return enterStep(model, redirectModel);
		}

		boolean isPaymentAuthorized = false;
		try
		{
			if (placeOrderForm.isReplenishmentOrder())
			{
				isPaymentAuthorized = brainTreeCheckoutFacade.authorizePaymentForReplenishment();
			}
			else
			{
				isPaymentAuthorized = getCheckoutFacade().authorizePayment(placeOrderForm.getSecurityCode());
			}
		}
		catch (final AdapterException exception)
		{
			// handle a case braintree errors and display reason of error
			LOG.error(exception.getMessage(), exception);
			GlobalMessages.addErrorMessage(model,
					Localization.getLocalizedString(Braintreeb2baddonWebConstants.ERROR_MESSAGE_WITH_REASON, new Object[]
			{ exception.getMessage() }));
			return enterStep(model, redirectModel);
		}
		if (!isPaymentAuthorized)
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.authorization.failed");
			return enterStep(model, redirectModel);
		}

		final PlaceOrderData placeOrderData = new PlaceOrderData();
		placeOrderData.setNDays(placeOrderForm.getnDays());
		placeOrderData.setNDaysOfWeek(placeOrderForm.getnDaysOfWeek());
		placeOrderData.setNegotiateQuote(Boolean.valueOf(placeOrderForm.isNegotiateQuote()));
		placeOrderData.setNthDayOfMonth(placeOrderForm.getNthDayOfMonth());
		placeOrderData.setNWeeks(placeOrderForm.getnWeeks());
		placeOrderData.setQuoteRequestDescription(placeOrderForm.getQuoteRequestDescription());
		placeOrderData.setReplenishmentOrder(Boolean.valueOf(placeOrderForm.isReplenishmentOrder()));
		placeOrderData.setReplenishmentRecurrence(placeOrderForm.getReplenishmentRecurrence());
		placeOrderData.setReplenishmentStartDate(placeOrderForm.getReplenishmentStartDate());
		placeOrderData.setSecurityCode(placeOrderForm.getSecurityCode());
		placeOrderData.setTermsCheck(Boolean.valueOf(placeOrderForm.isTermsCheck()));

		final AbstractOrderData orderData;
		try
		{
			orderData = getB2BCheckoutFacade().placeOrder(placeOrderData);
		}
		catch (final EntityValidationException e)
		{
			GlobalMessages.addErrorMessage(model, e.getLocalizedMessage());

			placeOrderForm.setTermsCheck(false);
			model.addAttribute(placeOrderForm);

			return enterStep(model, redirectModel);
		}
		catch (final Exception e)
		{
			LOG.error("Failed to place Order", e);
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed");
			return enterStep(model, redirectModel);
		}

		return redirectToOrderConfirmationPage(placeOrderData, orderData);
	}

}
