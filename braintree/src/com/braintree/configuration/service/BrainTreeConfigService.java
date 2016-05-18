/**
 *
 */
package com.braintree.configuration.service;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_3D_SECURE;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_ACCEPTED_PAYMENT_METHODS;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_ADVANCED_FRAUD_TOOLS_ENABLED;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_CHANNEL;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_CREDIT_CARD_STATEMENT_NAME;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_ENVIRONMENT;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_KOUNT_ID;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_MERCHANT_ACCOUNT_IDS;
import static com.braintree.constants.BraintreeConstants.ENVIRONMENT_PRODACTION;
import static com.braintree.constants.BraintreeConstants.ENVIRONMENT_SANDBOX;
import static com.braintree.constants.BraintreeConstants.HOSTED_FIELDS_ENABLED;
import static com.braintree.constants.BraintreeConstants.IS_SKIP_3D_SECURE_LIABILITY_RESULT;
import static com.braintree.constants.BraintreeConstants.PAYPAL_EXPRESS_ENABLED;
import static com.braintree.constants.BraintreeConstants.PAYPAL_STANDARD_ENABLED;
import static com.braintree.constants.BraintreeConstants.SETTLEMENT_DALAY;
import static com.braintree.constants.BraintreeConstants.SINGLE_USE_PARAMETER;
import static com.braintree.constants.BraintreeConstants.STORE_IN_VAULT;

import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.braintreegateway.Environment;


public class BrainTreeConfigService
{
	private final static Logger LOG = Logger.getLogger(BrainTreeConfigService.class);

	public static final String MERCHANT_ACCOUNT_SITE_MAP_DELIMETER = ";";
	public static final String MERCHANT_ACCOUNT_SITE_DELIMETER = "=";
	public static final String BRAINTREE_ACCEPTED_PAYMENT_METHODS_DELIMETER = ";";
	public static final String BRAINTREE_IMAGES_PREFIX = "braintree.image.";
	public static final String MERCHANT_ACCOUNT_SITE_CURRENCY_DELIMETER = "_";

	private BaseSiteService baseSiteService;
	private CommonI18NService commonI18NService;
	private ConfigurationService configurationService;

	public Environment getEnvironmentType()
	{

		if (ENV_TYPE_MAP.containsKey(getConfiguration().getString(BRAINTREE_ENVIRONMENT)))
		{
			return ENV_TYPE_MAP.get(getConfiguration().getString(BRAINTREE_ENVIRONMENT));
		}
		LOG.error("Configuration environment property name is incorrect! Set default prodaction environment...");
		return Environment.PRODUCTION;
	}

	/**
	 * @return Configuration
	 */
	private Configuration getConfiguration()
	{
		return getConfigurationService().getConfiguration();
	}

	public Boolean getSettlementConfigParameter()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(SETTLEMENT_DALAY, true));
	}

	public String getEnvironmentTypeName()
	{
		return getEnvironmentType().getEnvironmentName();
	}

	public Boolean get3dSecureConfiguration()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(BRAINTREE_3D_SECURE, false));
	}

	public String getBrainTreeKountId()
	{
		return getConfiguration().getString(BRAINTREE_KOUNT_ID);
	}

	public Boolean getAdvancedFraudTools()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(BRAINTREE_ADVANCED_FRAUD_TOOLS_ENABLED, true));
	}

	public Boolean getSingleUse()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(SINGLE_USE_PARAMETER, true));
	}

	private final Map<String, Environment> ENV_TYPE_MAP = new HashMap()
	{
		{
			put(ENVIRONMENT_SANDBOX, Environment.SANDBOX);
			put(ENVIRONMENT_PRODACTION, Environment.SANDBOX);
		}
	};

	public Boolean getHostedFieldEnabled()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(HOSTED_FIELDS_ENABLED, true));
	}

	public Boolean getPayPalExpressEnabled()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(PAYPAL_EXPRESS_ENABLED, true));
	}

	public Boolean getPayPalStandardEnabled()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(PAYPAL_STANDARD_ENABLED, true));
	}

	public Boolean getIsSkip3dSecureLiabilityResult()
	{
		return Boolean.valueOf(getConfiguration().getBoolean(IS_SKIP_3D_SECURE_LIABILITY_RESULT, false));
	}

	public String getCreditCardStatementName()
	{
		return getConfiguration().getString(BRAINTREE_CREDIT_CARD_STATEMENT_NAME);
	}

	public String getBrainTreeChannel()
	{
		return BRAINTREE_CHANNEL;
	}

	public String getMerchantAccountIds()
	{
		return getConfiguration().getString(BRAINTREE_MERCHANT_ACCOUNT_IDS);
	}

	public String getMerchantAccountIdForCurrentSiteAndCurrency()
	{
		final String currentSiteAndCurrency = getBaseSiteService().getCurrentBaseSite().getUid()
				+ MERCHANT_ACCOUNT_SITE_CURRENCY_DELIMETER + getCommonI18NService().getCurrentCurrency().getIsocode().toUpperCase();
		return getMerchantAccountIdByCurrentSiteNameAndCurrency(currentSiteAndCurrency);
	}

	public String getMerchantAccountIdForCurrentSiteAndCurrencyIsoCode(final String isoCode)
	{
		final String currentSiteAndCurrency = getBaseSiteService().getCurrentBaseSite().getUid()
				+ MERCHANT_ACCOUNT_SITE_CURRENCY_DELIMETER + isoCode.toUpperCase();
		return getMerchantAccountIdByCurrentSiteNameAndCurrency(currentSiteAndCurrency);
	}

	public String getAcceptedPaymentMethods()
	{
		return getConfiguration().getString(BRAINTREE_ACCEPTED_PAYMENT_METHODS);
	}

	public String getStoreInVault()
	{
		return getConfiguration().getString(STORE_IN_VAULT);
	}

	public Map<String, String> getAcceptedPaymentMethodImages()
	{
		final Map<String, String> acceptedPaymentMethodImages = new HashMap<>();
		final String acceptedPaymentMethods = getAcceptedPaymentMethods();
		if (StringUtils.isNotBlank(acceptedPaymentMethods))
		{
			final String paymentMethods = StringUtils.deleteWhitespace(acceptedPaymentMethods);
			if (paymentMethods.indexOf(BRAINTREE_ACCEPTED_PAYMENT_METHODS_DELIMETER) > 0)
			{
				final String segments[] = paymentMethods.split(BRAINTREE_ACCEPTED_PAYMENT_METHODS_DELIMETER);
				for (int i = 0; i < segments.length; i++)
				{
					final String imageAltText = segments[i];
					final String imageLink = getConfiguration().getString(BRAINTREE_IMAGES_PREFIX + imageAltText);
					if (StringUtils.isNotBlank(imageLink))
					{
						acceptedPaymentMethodImages.put(imageAltText, imageLink);
					}
				}
				return acceptedPaymentMethodImages;
			}
			else
			{
				final String imageAltText = paymentMethods;
				final String imageLink = getConfiguration().getString(BRAINTREE_IMAGES_PREFIX + imageAltText);
				if (StringUtils.isNotBlank(imageLink))
				{
					acceptedPaymentMethodImages.put(imageAltText, imageLink);
				}
				return acceptedPaymentMethodImages;
			}
		}
		return acceptedPaymentMethodImages;
	}

	public String getMerchantAccountIdByCurrentSiteNameAndCurrency(final String currentSiteAndCurrency)
	{
		final String merchantAccountId = null;
		final String merchantAccountId2SiteMap = getMerchantAccountIds();
		if (StringUtils.isNotBlank(merchantAccountId2SiteMap))
		{
			final boolean isFounded = isContainExactWord(merchantAccountId2SiteMap, currentSiteAndCurrency);
			if (isFounded)
			{
				if (merchantAccountId2SiteMap.indexOf(MERCHANT_ACCOUNT_SITE_MAP_DELIMETER) > 0)
				{
					final String segments[] = merchantAccountId2SiteMap.split(MERCHANT_ACCOUNT_SITE_MAP_DELIMETER);
					for (int i = 0; i < segments.length; i++)
					{
						final String siteName = segments[i].substring(0, segments[i].indexOf(MERCHANT_ACCOUNT_SITE_DELIMETER));
						if (currentSiteAndCurrency.equals(siteName))
						{
							return segments[i].substring(segments[i].indexOf(MERCHANT_ACCOUNT_SITE_DELIMETER)
									+ MERCHANT_ACCOUNT_SITE_DELIMETER.length());
						}
					}
				}
				else
				{
					final String siteName = merchantAccountId2SiteMap.substring(0,
							merchantAccountId2SiteMap.indexOf(MERCHANT_ACCOUNT_SITE_DELIMETER));
					if (currentSiteAndCurrency.equals(siteName))
					{
						return merchantAccountId2SiteMap.substring(merchantAccountId2SiteMap.indexOf(MERCHANT_ACCOUNT_SITE_DELIMETER)
								+ MERCHANT_ACCOUNT_SITE_DELIMETER.length());
					}
				}
			}
		}
		return merchantAccountId;
	}

	private boolean isContainExactWord(final String fullString, final String partWord)
	{
		final String pattern = "\\b" + partWord + MERCHANT_ACCOUNT_SITE_DELIMETER + "\\b";
		final Pattern p = Pattern.compile(pattern);
		final Matcher m = p.matcher(fullString);
		return m.find();
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService
	 *           the configurationService to set
	 */
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

}
