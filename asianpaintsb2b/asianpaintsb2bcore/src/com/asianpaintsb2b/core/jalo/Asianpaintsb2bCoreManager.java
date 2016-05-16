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
package com.asianpaintsb2b.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.asianpaintsb2b.core.constants.Asianpaintsb2bCoreConstants;
import com.asianpaintsb2b.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
@SuppressWarnings("PMD")
public class Asianpaintsb2bCoreManager extends GeneratedAsianpaintsb2bCoreManager
{
	public static final Asianpaintsb2bCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (Asianpaintsb2bCoreManager) em.getExtension(Asianpaintsb2bCoreConstants.EXTENSIONNAME);
	}
}
