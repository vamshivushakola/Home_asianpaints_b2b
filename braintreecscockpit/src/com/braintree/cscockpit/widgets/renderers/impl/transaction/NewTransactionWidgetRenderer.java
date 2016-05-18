package com.braintree.cscockpit.widgets.renderers.impl.transaction;

import de.hybris.platform.cockpit.widgets.Widget;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.api.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import com.braintree.cscockpit.constraint.EmailConstraint;
import com.braintree.cscockpit.constraint.RequiredAmountConstraint;
import com.braintree.cscockpit.data.BrainTreeInfo;
import com.braintree.cscockpit.widgets.controllers.TransactionController;
import com.braintree.cscockpit.widgets.models.impl.TransactionItemWidgetModel;
import com.braintree.hybris.data.BrainTreeResponseResultData;


public class NewTransactionWidgetRenderer extends AbstractNewTransactionWidgetRenderer
{
	private CommonI18NService commonI18NService;

	@Override
	protected HtmlBasedComponent createContentInternal(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final HtmlBasedComponent htmlBasedComponent)
	{
		final Div content = new Div();

		addPrettyTitle(widget, content, "paymentDetails");

		final Div paymentDetailsContent = new Div();
		paymentDetailsContent.setParent(content);

		final Textbox currency = createTextField(widget, paymentDetailsContent, "currency");
		setCurrentCurrency(currency);

		final Textbox amount = createTextField(widget, paymentDetailsContent, "amount", "required");
		amount.setConstraint(new RequiredAmountConstraint());

		final Textbox tax = createTextField(widget, paymentDetailsContent, "tax");
		final Textbox custom = createTextField(widget, paymentDetailsContent, "custom", "exampleCustom");
		custom.setMultiline(true);

		final Textbox cardHolder = createTextField(widget, paymentDetailsContent, "cardHolder");
		final Textbox cardNumber = createTextField(widget, paymentDetailsContent, "cardNumber", "required");
		cardNumber.setConstraint(new CreditCardConstraint());
		final Textbox cvv = createTextField(widget, paymentDetailsContent, "cvv");
		cvv.setConstraint(new CVVConstraint());
		final Textbox expirationDate = createTextField(widget, paymentDetailsContent, "expirationDate", "required");
		expirationDate.setConstraint(new ExpirationDateConstraint());

		addPrettyTitle(widget, content, "customerDetails");

		final Textbox firstName = createTextField(widget, content, "firstName");
		final Textbox lastName = createTextField(widget, content, "lastName");
		final Textbox email = createTextField(widget, content, "email");
		email.setConstraint(new EmailConstraint());

		final Textbox billingPostCode = createTextField(widget, content, "billingPostCode");
		final Textbox billingAddress = createTextField(widget, content, "billingAddress");

		final Textbox shippingPostCode = createTextField(widget, content, "shippingPostCode");
		final Textbox shippingAddress = createTextField(widget, content, "shippingAddress");

		createButton(widget, content, "saveButton", new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				NewTransactionWidgetRenderer.this.handleSaveButtonClickEvent(widget, currency, amount, tax, custom, cardHolder,
						cardNumber, cvv, expirationDate, firstName, lastName, email, billingAddress, billingPostCode, shippingAddress,
						shippingPostCode);
			}
		});

		return content;
	}

	private void handleSaveButtonClickEvent(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final Textbox currency, final Textbox amount, final Textbox tax, final Textbox custom, final Textbox cardHolder,
			final Textbox cardNumber, final Textbox cvv, final Textbox expirationDate, final Textbox firstName,
			final Textbox lastName, final Textbox email, final Textbox billingAddress, final Textbox billingPostCode,
			final Textbox shippingAddress, final Textbox shippingPostCode)
	{
		final BrainTreeInfo brainTreeInfo = new BrainTreeInfo();
		brainTreeInfo.setAmount(amount.getValue()).setTax(tax.getValue()).setCurrency(currency.getValue());
		setCustomFields(brainTreeInfo, custom);
		brainTreeInfo.setFirstName(firstName.getValue()).setLastName(lastName.getValue()).setEmail(email.getValue());
		brainTreeInfo.setBillingAddress(billingAddress.getValue()).setBillingPostCode(billingPostCode.getValue());
		brainTreeInfo.setShippingAddress(shippingAddress.getValue()).setShippingPostCode(shippingPostCode.getValue());
		brainTreeInfo.setExpirationDate(expirationDate.getValue()).setCardHolder(cardHolder.getValue())
				.setCardNumber(cardNumber.getValue()).setCvv(cvv.getValue());

		final BrainTreeResponseResultData transaction = getCsBrainTreeFacade().createTransaction(brainTreeInfo);
		processResult(widget, transaction);
	}

	private void setCurrentCurrency(final Textbox currency)
	{
		final CurrencyModel currentCurrency = getCommonI18NService().getCurrentCurrency();
		if (currentCurrency != null)
		{
			currency.setValue(currentCurrency.getIsocode());
		}
		currency.setReadonly(true);
	}

	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	private static class CVVConstraint implements Constraint
	{

		@Override
		public void validate(final Component paramComponent, final Object paramObject) throws WrongValueException
		{
			if (paramObject instanceof String)
			{
				final String cvv = (String) paramObject;
				{
					if (StringUtils.isNotBlank(cvv))
					{
						if (!cvv.matches("^([0-9]{3,4})$"))
						{
							throw new WrongValueException(paramComponent, Labels.getLabel("validation.incorrect.cvv"));
						}
					}
				}
			}
		}
	}

	private static class ExpirationDateConstraint implements Constraint
	{

		@Override
		public void validate(final Component paramComponent, final Object paramObject) throws WrongValueException
		{
			if (paramObject instanceof String)
			{
				final String expirationDate = (String) paramObject;
				{
					if (!expirationDate.matches("^((0[1-9])|(1[0-2]))\\/(\\d{2})$"))
					{
						throw new WrongValueException(paramComponent, Labels.getLabel("validation.incorrect.expirationDate"));
					}
				}
			}
		}
	}

	private static class CreditCardConstraint implements Constraint
	{

		@Override
		public void validate(final Component paramComponent, final Object paramObject) throws WrongValueException
		{
			if (paramObject instanceof String)
			{
				final String expirationDate = (String) paramObject;
				{
					if (!expirationDate.matches("^([0-9]{12,19})$"))
					{
						throw new WrongValueException(paramComponent, Labels.getLabel("validation.incorrect.creditCard"));
					}
				}
			}
		}
	}
}
