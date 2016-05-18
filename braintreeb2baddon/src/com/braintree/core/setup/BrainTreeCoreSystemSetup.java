package com.braintree.core.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;

import java.util.List;

import com.braintree.constants.Braintreeb2baddonConstants;
import com.google.common.collect.Lists;


@SystemSetup(extension = Braintreeb2baddonConstants.EXTENSIONNAME)
public class BrainTreeCoreSystemSetup extends AbstractSystemSetup
{

	private static final String IMPORT_PAY_PAL_PARAMETER_VALUE = "ImportPayPalCheckoutButton";
	private static final String IMPORT_PAY_PAL_POWERTOOLS_FILE_PATH = "/braintreeb2baddon/import/contentCatalogs/powertoolsContentCatalog/cms-content.impex";

	private static final String IMPORT_PAY_PAL_PARAMETER_NAME = "Import Pay Pal content";

	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = Lists.newArrayList();
		params.add(createBooleanSystemSetupParameter(IMPORT_PAY_PAL_PARAMETER_VALUE, IMPORT_PAY_PAL_PARAMETER_NAME, false));
		return params;
	}

	@SystemSetup(type = Type.ALL, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		final boolean importPayPalContent = getBooleanSystemSetupParameter(context, IMPORT_PAY_PAL_PARAMETER_VALUE);

		if (importPayPalContent)
		{
			importImpexFile(context, IMPORT_PAY_PAL_POWERTOOLS_FILE_PATH);
		}
	}

}