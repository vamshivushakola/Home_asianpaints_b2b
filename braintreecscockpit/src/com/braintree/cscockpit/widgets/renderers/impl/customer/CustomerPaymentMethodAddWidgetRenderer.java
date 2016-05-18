/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.braintree.cscockpit.widgets.renderers.impl.customer;

import de.hybris.platform.cockpit.model.meta.TypedObject;
import de.hybris.platform.cockpit.widgets.Widget;
import de.hybris.platform.cscockpit.utils.LabelUtils;
import de.hybris.platform.cscockpit.widgets.controllers.dispatcher.ItemAppender;
import de.hybris.platform.payment.AdapterException;

import java.io.InputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.api.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

import com.braintree.cscockpit.facade.CsBrainTreeFacade;
import com.braintree.cscockpit.strategy.UpdateCustomerStrategy;
import com.braintree.command.result.BrainTreePaymentMethodResult;
import com.braintree.cscockpit.widgets.controllers.CustomerController;
import com.braintree.cscockpit.widgets.models.impl.CustomerItemWidgetModel;
import com.braintree.cscockpit.widgets.renderers.utils.UIElementUtils;
import com.braintree.cscockpit.widgets.renderers.utils.MessageShowUtils;
import com.braintree.model.BraintreeCustomerDetailsModel;


public class CustomerPaymentMethodAddWidgetRenderer extends AbstractPaymentMethodWidgetRenderer
{
	private static final Logger LOG = Logger.getLogger(CustomerPaymentMethodAddWidgetRenderer.class);
	private static final String COOKIE_CLIENT_TOKEN = "clientToken";
	private static final String COOKIE_PAYMENT_METHOD_NONCE = "pm_nonce";
	private static final String INNER_HTML_FILE_PATH = "braintreecscockpit/cscockpit/iframe/paymentMethodNonce.html";
	private CsBrainTreeFacade csBrainTreeFacade;
	private ItemAppender<TypedObject> itemAppender;
	private UpdateCustomerStrategy defaultUpdateCustomerStrategy;

	@Override
	protected HtmlBasedComponent createContentInternal(final Widget<CustomerItemWidgetModel, CustomerController> widget,
			final HtmlBasedComponent component)
	{
		final Div content = new Div();
		final TypedObject currentCustomer = widget.getWidgetController().getCurrentCustomer();
		if (currentCustomer != null && currentCustomer.getObject() instanceof BraintreeCustomerDetailsModel)
		{
			preparedClientCookie();
			addPrettyTitle(widget, content, "creditCardValidationHostedFields");
			addIFrame(content);

			final Listbox addresses = UIElementUtils.createCustomerAddressesListBox(widget, content, "addresses",
					(BraintreeCustomerDetailsModel) currentCustomer.getObject());

			final Checkbox defaultPaymentMethod = createCheckBoxField(widget, content, "defaultPaymentMethod", false);

			createButton(widget, content, "createButton",
					getEventListener(defaultPaymentMethod, addresses, (BraintreeCustomerDetailsModel) currentCustomer.getObject()));
		}
		else
		{
			final Label dummyLabel = new Label(LabelUtils.getLabel(widget, "noCustomerSelected", new Object[0]));
			dummyLabel.setParent(content);
		}
		return content;
	}

	private void preparedClientCookie()
	{
		final String clientToken = getCsBrainTreeFacade().generateClientToken();
		final HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		response.addCookie(new Cookie(COOKIE_CLIENT_TOKEN, clientToken));
	}

	private void addIFrame(final Div content)
	{
		final Iframe iframe = new Iframe();
		final ResourceLoader resourceLoader = new ResourceLoader(INNER_HTML_FILE_PATH);

		final AMedia amedia = new AMedia("paymentMethodNonce.html", "html", "text/HTML", resourceLoader.getResource());
		iframe.setContent(amedia);
		iframe.setWidth("450px");
		iframe.setHeight("200px");
		iframe.setParent(content);
	}

	private EventListener getEventListener(final Checkbox defaultPaymentMethod, final Listbox addresses,
			final BraintreeCustomerDetailsModel currentCustomer)
	{
		return new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				CustomerPaymentMethodAddWidgetRenderer.this.handleCreateEventClickListener(defaultPaymentMethod, currentCustomer,
						addresses);
			}
		};
	}

	private void handleCreateEventClickListener(final Checkbox defaultPaymentMethod,
			final BraintreeCustomerDetailsModel currentCustomer, final Listbox addresses)
	{
		final String nonce = getNonceFromCookie();
		final boolean checked = defaultPaymentMethod.isChecked();
		final String customerId = currentCustomer.getId();
		LOG.info(String.format("BT payment Method creation fields: nonce=%s isDefault=%s customerId=%s", nonce,
				Boolean.valueOf(checked), customerId));

		if (StringUtils.isBlank(nonce))
		{
			MessageShowUtils.showErrorPaymentMethodNonceRetrieveMessage();
		}
		else
		{
			final String billingAddressId = getSelectedAddress(addresses);
			final BrainTreePaymentMethodResult result = getCsBrainTreeFacade().createCreditCardPaymentMethod(customerId, nonce,
					checked, billingAddressId);

			if (result.isSuccess() && result.getPaymentMethod() != null)
			{
				MessageShowUtils.showSuccessCreatePaymentMethodMessage();
				getPopupWidgetHelper().dismissCurrentPopup();
				final BraintreeCustomerDetailsModel updatedCustomer = getDefaultUpdateCustomerStrategy().update(currentCustomer,
						result.getPaymentMethod());
				final TypedObject itemTypedObject = getCockpitTypeService().wrapItem(updatedCustomer);
				getItemAppender().add(itemTypedObject, 1L);
			}
			else
			{
				MessageShowUtils.showErrorCreatePaymentMethodMessage(result);
			}
		}
	}

	private String getSelectedAddress(final Listbox addresses)
	{
		if (addresses != null && addresses.getSelectedItem() != null)
		{
			return (String) addresses.getSelectedItem().getValue();
		}
		return null;
	}

	private String getNonceFromCookie()
	{
		final String nonce = null;
		final Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();

		for (final Cookie cookie : cookies)
		{
			if (COOKIE_PAYMENT_METHOD_NONCE.equals(cookie.getName()))
			{
				LOG.info("payment method nonce received from virtual client: " + nonce);
				return cookie.getValue();
			}
		}
		return nonce;
	}

	protected void createButton(final Widget<CustomerItemWidgetModel, CustomerController> widget,
			final HtmlBasedComponent component, final String buttonLabelName, final EventListener eventListener)
	{
		final Div saveButtonBox = new Div();
		saveButtonBox.setClass("btCustomerDiv");
		saveButtonBox.setParent(component);

		final Button button = new Button();
		button.setLabel(LabelUtils.getLabel(widget, buttonLabelName, new Object[0]));
		button.setParent(saveButtonBox);
		button.addEventListener(Events.ON_CLICK, eventListener);
	}

	private Checkbox createCheckBoxField(final Widget<CustomerItemWidgetModel, CustomerController> widget, final Div content,
			final String label, final boolean isDefault)
	{
		final Checkbox checkbox = new Checkbox(LabelUtils.getLabel(widget, label, new Object[0]));
		checkbox.setClass("btCustomerCheckbox");
		checkbox.setChecked(isDefault);
		content.appendChild(checkbox);
		return checkbox;
	}

	public CsBrainTreeFacade getCsBrainTreeFacade()
	{
		return csBrainTreeFacade;
	}

	public void setCsBrainTreeFacade(final CsBrainTreeFacade csBrainTreeFacade)
	{
		this.csBrainTreeFacade = csBrainTreeFacade;
	}

	public ItemAppender<TypedObject> getItemAppender()
	{
		return itemAppender;
	}

	public void setItemAppender(final ItemAppender<TypedObject> itemAppender)
	{
		this.itemAppender = itemAppender;
	}

	public UpdateCustomerStrategy getDefaultUpdateCustomerStrategy()
	{
		return defaultUpdateCustomerStrategy;
	}

	public void setDefaultUpdateCustomerStrategy(final UpdateCustomerStrategy defaultUpdateCustomerStrategy)
	{
		this.defaultUpdateCustomerStrategy = defaultUpdateCustomerStrategy;
	}

	class ResourceLoader
	{
		private final String filePath;

		public ResourceLoader(final String filePath)
		{
			this.filePath = filePath;
		}

		public InputStream getResource()
		{
			final ClassLoader classLoader = this.getClass().getClassLoader();

			final InputStream inputStream = classLoader.getResourceAsStream(filePath);

			if (inputStream == null)
			{
				throw new AdapterException("Not found");
			}

			return inputStream;
		}
	}
}
