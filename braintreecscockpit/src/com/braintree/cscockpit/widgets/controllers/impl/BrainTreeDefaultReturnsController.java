package com.braintree.cscockpit.widgets.controllers.impl;

import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_ICON_SUCCESS;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_VOID_DURING_REFUND_SUCCESS;
import static com.braintree.constants.BraintreecscockpitConstants.TransactionManagementActionsWidgetRenderer.WIDGET_MESSAGE_VOID_ERROR;
import static org.zkoss.util.resource.Labels.getLabel;

import de.hybris.platform.commerceservices.impersonation.ImpersonationContext;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cscockpit.utils.SafeUnbox;
import de.hybris.platform.cscockpit.widgets.controllers.impl.DefaultReturnsController;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.returns.model.RefundEntryModel;
import de.hybris.platform.returns.model.ReturnRequestModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.zkoss.zul.Messagebox;

import com.braintree.cscockpit.facade.CsBrainTreeFacade;
import com.braintree.hybris.data.BrainTreeResponseResultData;
import com.braintree.hybris.data.BraintreeTransactionEntryData;
import com.braintreegateway.Transaction;


public class BrainTreeDefaultReturnsController extends DefaultReturnsController
{
	private static final Logger LOG = Logger.getLogger(BrainTreeDefaultReturnsController.class);
	private CsBrainTreeFacade csBrainTreeFacade;

	/**
	 * copy method applyRefunds from DefaultReturnsController for adding BT refund external command
	 */
	@Override
	protected OrderModel applyRefunds(final OrderModel orderModel, final ReturnRequestModel request,
			final Map<Long, RefundDetails> refundDetailsList, final boolean history)
	{
		final ArrayList refundEntries = new ArrayList();
		final Iterator var7 = refundDetailsList.keySet().iterator();

		while (var7.hasNext())
		{
			final Long context = (Long) var7.next();
			final AbstractOrderEntryModel orderEntryModel = this.getOrderEntryByEntryNumber(orderModel, SafeUnbox.toLong(context));
			if (orderEntryModel != null)
			{
				final DefaultReturnsController.RefundDetails refundDetails = refundDetailsList.get(context);
				final RefundEntryModel refundEntry = this.getReturnService().createRefund(request, orderEntryModel,
						refundDetails.getNotes(), Long.valueOf(refundDetails.getExpectedQuantity()), refundDetails.getAction(),
						refundDetails.getReason());
				refundEntries.add(refundEntry);
			}
			else
			{
				LOG.error("Failed to find orderEntry with entry number [" + context + "]");
			}
		}

		if (!refundEntries.isEmpty() && !history)
		{
			final ImpersonationContext context1 = this.createImpersonationContext(orderModel);
			try
			{
				this.getImpersonationService().executeInContext(context1, new ImpersonationService.Executor()
				{
					public Object execute()
					{
						final Double orderAmountBeforeRefund = orderModel.getTotalPrice();
						getRefundService().apply(refundEntries, orderModel);
						btRefund(orderAmountBeforeRefund, orderModel);
						return null;
					}
				});
			}
			catch (final Throwable throwable)
			{
				throw new AdapterException(throwable);
			}
		}

		return orderModel;
	}

	/**
	 * TODO: extract to services
	 */
	private void btRefund(final Double orderAmountBeforeRefund, final OrderModel order)
	{
		final Double orderAmountAfterRefund = order.getTotalPrice();

		if (!orderAmountAfterRefund.equals(orderAmountBeforeRefund))
		{
			final BigDecimal refundAmount = new BigDecimal(orderAmountBeforeRefund.doubleValue()).subtract(
					new BigDecimal(orderAmountAfterRefund.doubleValue())).setScale(getCurrencyDigit(order), RoundingMode.HALF_EVEN);

			final List<PaymentTransactionModel> paymentTransactions = order.getPaymentTransactions();
			if (paymentTransactions.size() > 0)
			{
				processBTRefund(refundAmount, paymentTransactions.iterator().next());
			}
		}
	}

	/**
	 * A transaction can be refunded if it is settled or settling. If the transaction has not yet begun settlement, use
	 * Transaction.void() instead.
	 */
	private void processBTRefund(final BigDecimal refundAmount, final PaymentTransactionModel transaction)
	{
		final BraintreeTransactionEntryData brainTreeTransaction = getCsBrainTreeFacade().findBrainTreeTransaction(
				transaction.getRequestId());
		if (brainTreeTransaction == null)
		{
			throw new AdapterException("BT No transaction with id:" + transaction.getRequestId() + " in BrainTree");
		}
		final String status = brainTreeTransaction.getStatus();

		if (Transaction.Status.SETTLED.toString().equals(status) || Transaction.Status.SETTLING.toString().equals(status))
		{
			processBtRefund(refundAmount, transaction);
		}
		else
		{
			processBtVoid(transaction);
		}
	}

	private void processBtRefund(final BigDecimal refundAmount, final PaymentTransactionModel transaction)
	{
		final BrainTreeResponseResultData result = getCsBrainTreeFacade().refundTransaction(transaction.getRequestId(),
				refundAmount.toString());
		if (!result.isSuccess())
		{
			showMessage(result.getErrorMessage(), "Refund transaction Error", Messagebox.ERROR);
		}
	}

	private void processBtVoid(final PaymentTransactionModel transaction)
	{
		final boolean result = getCsBrainTreeFacade().voidTransaction(transaction, false);
		if (result)
		{
			showMessage(getLabel(WIDGET_MESSAGE_VOID_DURING_REFUND_SUCCESS), "Refund transaction Detail", WIDGET_ICON_SUCCESS);
		}
		else
		{
			showMessage(getLabel(WIDGET_MESSAGE_VOID_ERROR), "Refund transaction Detail", Messagebox.ERROR);
		}
	}


	private void showMessage(final String errMessage, final String title, final String icon)
	{
		try
		{
			Messagebox.show(errMessage, title, Messagebox.OK, icon);
		}
		catch (final InterruptedException e)
		{
			LOG.debug("Errors occurred while showing message box!", e);
		}
	}

	private int getCurrencyDigit(final OrderModel order)
	{
		final CurrencyModel currency = order.getCurrency();
		Assert.notNull(currency, "Order " + order.getCode() + " has got a null currency. Cannot calculate refund");
		final Integer digits = currency.getDigits();
		Assert.notNull(digits, "Order " + order.getCode()
				+ " has got a currency without decimal digits defined. Cannot calculate refund");
		return digits.intValue();
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
