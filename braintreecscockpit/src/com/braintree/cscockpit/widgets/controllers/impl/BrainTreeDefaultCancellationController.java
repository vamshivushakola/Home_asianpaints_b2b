package com.braintree.cscockpit.widgets.controllers.impl;

import de.hybris.platform.basecommerce.enums.OrderCancelEntryStatus;
import de.hybris.platform.cockpit.model.meta.TypedObject;
import de.hybris.platform.cockpit.services.values.ObjectValueContainer;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cscockpit.exceptions.ValidationException;
import de.hybris.platform.cscockpit.widgets.controllers.impl.DefaultCancellationController;
import de.hybris.platform.ordercancel.OrderCancelException;
import de.hybris.platform.ordercancel.OrderCancelRequest;
import de.hybris.platform.ordercancel.model.OrderCancelRecordEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;

import java.util.List;

import com.braintree.cscockpit.facade.CsBrainTreeFacade;
import com.braintree.cscockpit.widgets.controllers.BrainTreeCancellationController;


public class BrainTreeDefaultCancellationController extends DefaultCancellationController implements
		BrainTreeCancellationController
{
	private CsBrainTreeFacade csBrainTreeFacade;

	@Override
	public TypedObject createOrderCancellationRequest(final ObjectValueContainer cancelRequest) throws OrderCancelException,
			ValidationException
	{
		final TypedObject order = getOrder();
		if ((order != null) && (order.getObject() instanceof OrderModel))
		{
			final OrderModel orderModel = (OrderModel) order.getObject();
			return createOrderCancellationRequest(cancelRequest, orderModel);
		}
		return null;
	}

	@Override
	public TypedObject createOrderCancellationRequest(final ObjectValueContainer cancelRequest, final OrderModel orderModel)
			throws ValidationException, OrderCancelException
	{
		if (orderModel != null)
		{
			if (validateCreateCancellationRequest(orderModel, cancelRequest))
			{
				try
				{
					final OrderCancelRequest request = buildCancelRequest(orderModel, cancelRequest);
					if (request != null)
					{
						final OrderCancelRecordEntryModel orderRequestRecord = getOrderCancelService().requestOrderCancel(request,
								getUserService().getCurrentUser());
						if (orderRequestRecord != null && (!OrderCancelEntryStatus.DENIED.equals(orderRequestRecord.getCancelResult())))
						{
							final List<PaymentTransactionModel> txns = orderModel.getPaymentTransactions();
							if (txns.size() == 1)
							{
								final PaymentTransactionModel txn = txns.iterator().next();
								final boolean voidSuccess = getCsBrainTreeFacade().voidTransaction(txn);
								if (!voidSuccess)
								{
									throw new OrderCancelException("Order cant be canceled on BT side");
								}
							}
						}

						return getCockpitTypeService().wrapItem(orderRequestRecord);
					}
				}
				catch (final OrderCancelException ocEx)
				{
					throw ocEx;
				}
				catch (final Exception e)
				{
					throw new OrderCancelException("Failed to cancel", e);
				}
			}
		}
		return null;
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
