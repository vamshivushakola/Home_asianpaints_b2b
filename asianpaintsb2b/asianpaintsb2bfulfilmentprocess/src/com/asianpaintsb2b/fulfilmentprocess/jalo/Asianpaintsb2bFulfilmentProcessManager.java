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
package com.asianpaintsb2b.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.asianpaintsb2b.fulfilmentprocess.constants.Asianpaintsb2bFulfilmentProcessConstants;

import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class Asianpaintsb2bFulfilmentProcessManager extends GeneratedAsianpaintsb2bFulfilmentProcessManager
{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger( Asianpaintsb2bFulfilmentProcessManager.class.getName() );
	
	public static final Asianpaintsb2bFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (Asianpaintsb2bFulfilmentProcessManager) em.getExtension(Asianpaintsb2bFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
