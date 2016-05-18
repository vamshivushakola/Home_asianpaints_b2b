package com.braintree.cscockpit.widgets.renderers.impl.transaction.actions;

import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_ICON_ERROR;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_ICON_SUCCESS;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_VOID_ERROR;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_VOID_SUCCESS;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_VOID_ASK_MESSAGE;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_VOID_ASK_TITLE;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_VOID_TITLE;
import static org.zkoss.util.resource.Labels.getLabel;

import de.hybris.platform.cockpit.widgets.Widget;
import de.hybris.platform.cscockpit.utils.LabelUtils;
import de.hybris.platform.cscockpit.widgets.renderers.impl.AbstractCsWidgetRenderer;
import de.hybris.platform.cscockpit.widgets.renderers.utils.PopupWidgetHelper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.zkoss.zk.ui.api.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;

import com.braintree.cscockpit.facade.CsBrainTreeFacade;
import com.braintree.cscockpit.widgets.controllers.TransactionManagementActionsWidgetController;
import com.braintree.cscockpit.widgets.models.impl.TransactionItemWidgetModel;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.model.BrainTreeTransactionDetailModel;
import com.braintreegateway.Transaction;


public class TransactionManagementActionsWidgetRenderer extends
		AbstractCsWidgetRenderer<Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController>>
{
	private static final Logger LOG = Logger.getLogger(TransactionManagementActionsWidgetRenderer.class);
	private PopupWidgetHelper popupWidgetHelper;
	private CsBrainTreeFacade csBrainTreeFacade;

	@Override
	protected HtmlBasedComponent createContentInternal(
			final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget,
			final HtmlBasedComponent rootContainer)
	{
		final Div component = new Div();
		component.setSclass("orderManagementActionsWidget");

		this.createButton(widget, component, "voidTransaction", createVoidEventListener(widget), !widget.getWidgetController()
				.isVoidPossible());
		this.createButton(widget, component, "cloneTransaction", "defaultCsCloneTransactionWidgetConfig",
				"csCloneTransaction-Popup", "csCloneTransaction", "popup.cloneTransactionCreate", !widget.getWidgetController()
						.isClonePossible());
		this.createButton(widget, component, "refundTransaction", "defaultCsRefundTransactionWidgetConfig",
				"csRefundTransaction-Popup", "csRefundTransaction", "popup.refundTransactionCreate", !widget.getWidgetController()
						.isRefundPossible());
		this.createButton(widget, component, "submitForSettlementTransaction",
				"defaultCsSubmitForSettlementTransactionWidgetConfig", "csSubmitForSettlementTransaction-Popup",
				"csSubmitForSettlementTransaction", "popup.submitForSettlementCreate", !widget.getWidgetController()
						.isSubmitForSettlementPossible());

		return component;
	}

	private EventListener createVoidEventListener(
			final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget)
	{
		return new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				TransactionManagementActionsWidgetRenderer.this.handleVoidButtonClickEvent(widget, event);
			}
		};
	}

	private void handleVoidButtonClickEvent(
			final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget, final Event event)
	{
		try
		{
			Messagebox.show(getLabel(WIDGET_VOID_ASK_MESSAGE), getLabel(WIDGET_VOID_ASK_TITLE), Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener()
					{
						public void onEvent(final Event evt) throws InterruptedException
						{
							if ("onOK".equals(evt.getName()))
							{
								showVoidResult(widget);
							}
						}
					});
		}
		catch (final InterruptedException exception)
		{
			LOG.debug("Errors occurred while showing message box!", exception);
		}
	}

	private void showVoidResult(final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget)
	{
		final BrainTreeTransactionDetailModel transactionDetail = (BrainTreeTransactionDetailModel) widget.getWidgetController()
				.getTransaction().getObject();
		final BrainTreeResponseResultData resendResult = getCsBrainTreeFacade().voidTransaction(transactionDetail);
		String result;
		String icon;

		if (resendResult.isSuccess())
		{
			transactionDetail.setStatus(Transaction.Status.VOIDED.toString());
			result = getLabel(WIDGET_MESSAGE_VOID_SUCCESS);
			icon = WIDGET_ICON_SUCCESS;
		}
		else
		{
			if (StringUtils.isNotBlank(resendResult.getErrorMessage()))
			{
				result = resendResult.getErrorMessage();
			}
			else
			{
				result = getLabel(WIDGET_MESSAGE_VOID_ERROR);
			}
			icon = WIDGET_ICON_ERROR;
		}
		try
		{
			Messagebox.show(result, getLabel(WIDGET_VOID_TITLE), 1, icon);
			widget.getWidgetController().dispatchEvent((String) null, widget, (Map) null);
		}
		catch (final InterruptedException exception)
		{
			LOG.debug("Errors occurred while showing message box!", exception);
		}
	}

	protected void createButton(final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget,
			final Div container, final String buttonLabelName, final String springWidgetName, final String popupCode,
			final String cssClass, final String popupTitleLabelName, final boolean disabled)
	{
		final EventListener eventListener = new EventListener()
		{
			public void onEvent(final Event event) throws Exception
			{
				TransactionManagementActionsWidgetRenderer.this.handleButtonClickEvent(widget, event, container, springWidgetName,
						popupCode, cssClass, popupTitleLabelName);
			}
		};
		this.createButton(widget, container, buttonLabelName, eventListener, disabled);
	}

	private void createButton(final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget,
			final HtmlBasedComponent component, final String buttonLabelName, final EventListener eventListener,
			final boolean disabled)
	{
		final Button button = new Button();
		button.setLabel(LabelUtils.getLabel(widget, buttonLabelName, new Object[0]));
		button.setParent(component);
		button.setDisabled(disabled);
		if (eventListener != null)
		{
			button.addEventListener(Events.ON_CLICK, eventListener);
		}
	}

	protected void handleButtonClickEvent(
			final Widget<TransactionItemWidgetModel, TransactionManagementActionsWidgetController> widget, final Event event,
			final Div container, final String springWidgetName, final String popupCode, final String cssClass,
			final String popupTitleLabelName)
	{
		this.getPopupWidgetHelper().createPopupWidget(container, springWidgetName, popupCode, cssClass,
				LabelUtils.getLabel(widget, popupTitleLabelName, new Object[0]), 400);
	}

	protected PopupWidgetHelper getPopupWidgetHelper()
	{
		return this.popupWidgetHelper;
	}

	@Required
	public void setPopupWidgetHelper(final PopupWidgetHelper popupWidgetHelper)
	{
		this.popupWidgetHelper = popupWidgetHelper;
	}

	public CsBrainTreeFacade getCsBrainTreeFacade()
	{
		return csBrainTreeFacade;
	}

	public void setCsBrainTreeFacade(final CsBrainTreeFacade csBrainTreeFacade)
	{
		this.csBrainTreeFacade = csBrainTreeFacade;
	}
}
