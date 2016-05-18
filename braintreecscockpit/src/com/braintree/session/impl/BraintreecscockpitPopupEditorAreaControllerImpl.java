/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.braintree.session.impl;

import de.hybris.platform.cockpit.session.BrowserModel;
import de.hybris.platform.cockpit.session.PopupEditorAreaController;
import de.hybris.platform.cockpit.session.impl.DefaultEditorAreaController;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Braintreecscockpit popup editor area controller.
 */
public class BraintreecscockpitPopupEditorAreaControllerImpl extends DefaultEditorAreaController implements PopupEditorAreaController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(BraintreecscockpitPopupEditorAreaControllerImpl.class);

	private BrowserModel contextEditorBrowser = null;
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	public Map<String, Object> getAttributesMap()
	{
		return attributes;
	}

	@Override
	public void setContextEditorBrowser(final BrowserModel model)
	{
		this.contextEditorBrowser = model;
	}

	public BrowserModel getContextEditorBrowserModel()
	{
		return contextEditorBrowser;
	}
}
