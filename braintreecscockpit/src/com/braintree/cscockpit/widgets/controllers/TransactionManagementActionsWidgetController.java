package com.braintree.cscockpit.widgets.controllers;

import de.hybris.platform.cockpit.model.meta.TypedObject;
import de.hybris.platform.cockpit.widgets.controllers.WidgetController;


public interface TransactionManagementActionsWidgetController extends WidgetController
{
	boolean isVoidPossible();

	boolean isClonePossible();

	boolean isRefundPossible();

	boolean isSubmitForSettlementPossible();

	TypedObject getTransaction();
}
