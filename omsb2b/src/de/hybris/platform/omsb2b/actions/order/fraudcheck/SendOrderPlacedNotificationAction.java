/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package de.hybris.platform.omsb2b.actions.order.fraudcheck;

import de.hybris.platform.orderprocessing.events.OrderPlacedEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.event.EventService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * Send a notification that an order is being placed.
 */
public class SendOrderPlacedNotificationAction extends AbstractProceduralAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SendOrderPlacedNotificationAction.class);

	private EventService eventService;

	@Override
	public void executeAction(final OrderProcessModel process)
	{
		LOG.info("Process: " + process.getCode() + " in step " + getClass().getSimpleName());
		getEventService().publishEvent(new OrderPlacedEvent(process));
	}

	protected EventService getEventService()
	{
		return eventService;
	}

	@Required
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}
}
