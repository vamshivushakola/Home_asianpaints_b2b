package com.braintree.cscockpit.widgets.renderers.impl.transaction.actions;

import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS_POSTFIX;
import static org.zkoss.util.resource.Labels.getLabel;

import de.hybris.platform.cockpit.widgets.Widget;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.braintree.constants.BraintreecscockpitConstants;
import com.braintree.cscockpit.widgets.controllers.TransactionController;
import com.braintree.cscockpit.widgets.models.impl.TransactionItemWidgetModel;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.model.BrainTreeTransactionDetailModel;


public class TransactionRefundWidgetRenderer extends AbstractTransactionActionsWidgetRenderer
{
	@Override
	protected void renderCustomFields(final BrainTreeTransactionDetailModel currentTransaction, final Div content,
			final Widget<TransactionItemWidgetModel, TransactionController> widget)
	{
		final Textbox amountField = createAmountField(widget, createDiv(content), currentTransaction.getAmount());
		createButton(widget, getButtonBox(content), "refundButton",
				createRefundEventListener(widget, currentTransaction, amountField));
	}

	private EventListener createRefundEventListener(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final BrainTreeTransactionDetailModel currentTransaction, final Textbox amountField)
	{
		return new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				TransactionRefundWidgetRenderer.this.handleRefundButtonClickEvent(widget, event, currentTransaction, amountField);
			}
		};
	}

	private void handleRefundButtonClickEvent(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final Event event, final BrainTreeTransactionDetailModel currentTransaction, final Textbox amountField)
	{
		final BrainTreeResponseResultData resendResult = getCsBrainTreeFacade().refundTransaction(currentTransaction,
				amountField.getValue());
		processResult(widget, resendResult);
	}

	@Override
	protected void showErrorMessage(final BrainTreeResponseResultData result) throws InterruptedException
	{
		String errorMessage;
		if (StringUtils.isNotBlank(result.getErrorMessage()))
		{
			errorMessage = result.getErrorMessage();
		}
		else
		{
			errorMessage = getLabel(BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_REFUND_ERROR);
		}
		Messagebox.show(errorMessage,
				getLabel(BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_REFUND_TITLE), Messagebox.OK,
				Messagebox.ERROR);
	}

	@Override
	protected void showSuccessMessage(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final BrainTreeResponseResultData result) throws InterruptedException
	{
		Messagebox.show(createSuccessMessage(result),
				getLabel(BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_REFUND_TITLE), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	private String createSuccessMessage(final BrainTreeResponseResultData resendResult)
	{
		final String message = getLabel(BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_REFUND_SUCCESS);
		final String messagePostfix = getLabel(WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS_POSTFIX);
		if (StringUtils.isNotBlank(resendResult.getTransactionId()))
		{
			return String.format("%s %s: %s", message, messagePostfix, resendResult.getTransactionId());
		}
		return message;
	}
}
