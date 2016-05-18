package com.braintree.cscockpit.widgets.renderers.impl.transaction.actions;

import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS_POSTFIX;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_TRANSACTION_SFS_ERROR;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_TRANSACTION_SFS_SUCCESS;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_TRANSACTION_SFS_TITLE;
import static org.zkoss.util.resource.Labels.getLabel;

import de.hybris.platform.cockpit.widgets.Widget;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.braintree.cscockpit.widgets.controllers.TransactionController;
import com.braintree.cscockpit.widgets.models.impl.TransactionItemWidgetModel;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.model.BrainTreeTransactionDetailModel;


public class TransactionSubmitForSettlementWidgetRenderer extends AbstractTransactionActionsWidgetRenderer
{

	@Override
	protected void renderCustomFields(final BrainTreeTransactionDetailModel currentTransaction, final Div content,
			final Widget<TransactionItemWidgetModel, TransactionController> widget)
	{
		final Textbox amountField = createAmountField(widget, createDiv(content), currentTransaction.getAmount());

		createButton(widget, getButtonBox(content), "submitForSettlementButton",
				createSubmitForSettlementEventListener(widget, currentTransaction, amountField));
	}

	private EventListener createSubmitForSettlementEventListener(
			final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final BrainTreeTransactionDetailModel currentTransaction, final Textbox amountField)
	{
		return new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				TransactionSubmitForSettlementWidgetRenderer.this.handleSfSButtonClickEvent(widget, event, currentTransaction,
						amountField);
			}
		};
	}

	private void handleSfSButtonClickEvent(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final Event event, final BrainTreeTransactionDetailModel currentTransaction, final Textbox amountField)
	{
		final BrainTreeResponseResultData resendResult = getCsBrainTreeFacade().submitForSettlementTransaction(currentTransaction,
				amountField.getValue());
		processResult(widget, resendResult);
	}

	@Override
	protected void showSuccessMessage(final Widget<TransactionItemWidgetModel, TransactionController> widget,
			final BrainTreeResponseResultData result) throws InterruptedException
	{
		Messagebox.show(getLabel(WIDGET_MESSAGE_TRANSACTION_SFS_SUCCESS) + " "+
				getLabel(WIDGET_MESSAGE_TRANSACTION_CREATE_SUCCESS_POSTFIX)+ ": " + result.getTransactionId(),
				getLabel(WIDGET_TRANSACTION_SFS_TITLE), Messagebox.OK, Messagebox.INFORMATION);
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
			errorMessage = getLabel(WIDGET_MESSAGE_TRANSACTION_SFS_ERROR);
		}
		Messagebox.show(errorMessage, getLabel(WIDGET_TRANSACTION_SFS_TITLE), Messagebox.OK, Messagebox.ERROR);
	}

}
