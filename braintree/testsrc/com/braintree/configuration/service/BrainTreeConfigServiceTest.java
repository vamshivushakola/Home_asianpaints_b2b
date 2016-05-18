/**
 *
 */
package com.braintree.configuration.service;

import static com.braintree.constants.BraintreeConstants.BRAINTREE_3D_SECURE;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_ACCEPTED_PAYMENT_METHODS;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_KOUNT_ID;
import static com.braintree.constants.BraintreeConstants.BRAINTREE_MERCHANT_ACCOUNT_IDS;
import static com.braintree.constants.BraintreeConstants.SETTLEMENT_DALAY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * @author Anton_Kasianchuk
 *
 */
@UnitTest
public class BrainTreeConfigServiceTest
{
	private static final String PAYMENT_METHOD1 = "PAYMENT_METHOD1";
	private static final String IMAGE_LINK1 = "IMAGE_LINK1";
	private static final String PAYMENT_METHOD2 = "PAYMENT_METHOD2";
	private static final String IMAGE_LINK2 = "IMAGE_LINK2";
	private static final String PAYMENT_METHODS_STRING_ONE_PAYMENT = PAYMENT_METHOD1;
	private static final String PAYMENT_METHODS_STRING_MORE_THAN_ONE_PAYMENT = PAYMENT_METHOD1 + ";" + PAYMENT_METHOD2;

	private static final String SITE_NAME1 = "SITE_NAME1";
	private static final String MERCHANT_ACCOUNT_ID1 = "MERCHANT_ACCOUNT_ID1";
	private static final String MERCHANT_ACCOUNT_ID_TO_SITE_STR_ONE_ACCOUNT = SITE_NAME1 + "=" + MERCHANT_ACCOUNT_ID1;
	private static final String SITE_NAME2 = "SITE_NAME2";
	private static final String MERCHANT_ACCOUNT_ID2 = "MERCHANT_ACCOUNT_ID2";
	private static final String MERCHANT_ACCOUNT_ID_TO_SITE_STR_MORE_THAN_ONE_ACCOUNT = SITE_NAME1 + "=" + MERCHANT_ACCOUNT_ID1
			+ ";" + SITE_NAME2 + "=" + MERCHANT_ACCOUNT_ID2;

	private static final String SITE_NAME_UNKNOWN = "SITE_NAME_UNKNOWN";
	private static final String MERCHANT_ACCOUNT_ID_UNKNOWN = "MERCHANT_ACCOUNT_ID_UNKNOWN";
	private static final String MERCHANT_ACCOUNT_ID_TO_SITE_STR_ONE_UNKNOWN_ACCOUNT = SITE_NAME_UNKNOWN + "="
			+ MERCHANT_ACCOUNT_ID_UNKNOWN;

	private static final String CURRENT_SITE_NAME = SITE_NAME1;


	@Mock
	private ConfigurationService configurationService;

	@InjectMocks
	private final BrainTreeConfigService brainTreeCustomerAccountService = new BrainTreeConfigService();

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetSettlementConfigParameter()
	{
		final Boolean expected = Boolean.TRUE;
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(Boolean.valueOf(configuration.getBoolean(SETTLEMENT_DALAY, true))).thenReturn(expected);

		final Boolean actual = brainTreeCustomerAccountService.getSettlementConfigParameter();

		assertEquals(expected, actual);
	}

	@Test
	public void shouldGet3dSecureConfiguration()
	{
		final Boolean expected = Boolean.TRUE;
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(Boolean.valueOf(configuration.getBoolean(BRAINTREE_3D_SECURE, false))).thenReturn(expected);

		final Boolean actual = brainTreeCustomerAccountService.get3dSecureConfiguration();

		assertEquals(expected, actual);
	}

	@Test
	public void getBrainTreeKountId()
	{
		final String btKountIdExpected = "bt";
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_KOUNT_ID)).thenReturn(btKountIdExpected);

		final String btKountIdActual = brainTreeCustomerAccountService.getBrainTreeKountId();

		assertEquals(btKountIdExpected, btKountIdActual);
	}

	@Test
	public void shouldGetAcceptedPaymentMethodImagesNoAcceptedPaymentMethods()
	{
		final Map<String, String> acceptedPaymentMethodImagesExpected = new HashMap<>();
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_ACCEPTED_PAYMENT_METHODS)).thenReturn("");

		final Map<String, String> acceptedPaymentMethodImagesActual = brainTreeCustomerAccountService
				.getAcceptedPaymentMethodImages();

		assertEquals(acceptedPaymentMethodImagesExpected, acceptedPaymentMethodImagesActual);
	}

	@Test
	public void shouldGetAcceptedPaymentMethodImagesOneAcceptedPaymentMethods()
	{
		final Map<String, String> acceptedPaymentMethodImagesExpected = new HashMap<>();
		acceptedPaymentMethodImagesExpected.put(PAYMENT_METHOD1, IMAGE_LINK1);
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_ACCEPTED_PAYMENT_METHODS)).thenReturn(PAYMENT_METHODS_STRING_ONE_PAYMENT);
		when(configuration.getString(BrainTreeConfigService.BRAINTREE_IMAGES_PREFIX + PAYMENT_METHOD1)).thenReturn(IMAGE_LINK1);

		final Map<String, String> acceptedPaymentMethodImagesActual = brainTreeCustomerAccountService
				.getAcceptedPaymentMethodImages();

		assertEquals(acceptedPaymentMethodImagesExpected, acceptedPaymentMethodImagesActual);
	}

	@Test
	public void shouldGetAcceptedPaymentMethodImagesMoreThanOneAcceptedPaymentMethods()
	{
		final Map<String, String> acceptedPaymentMethodImagesExpected = new HashMap<>();
		acceptedPaymentMethodImagesExpected.put(PAYMENT_METHOD1, IMAGE_LINK1);
		acceptedPaymentMethodImagesExpected.put(PAYMENT_METHOD2, IMAGE_LINK2);
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_ACCEPTED_PAYMENT_METHODS)).thenReturn(PAYMENT_METHODS_STRING_MORE_THAN_ONE_PAYMENT);
		when(configuration.getString(BrainTreeConfigService.BRAINTREE_IMAGES_PREFIX + PAYMENT_METHOD1)).thenReturn(IMAGE_LINK1);
		when(configuration.getString(BrainTreeConfigService.BRAINTREE_IMAGES_PREFIX + PAYMENT_METHOD2)).thenReturn(IMAGE_LINK2);

		final Map<String, String> acceptedPaymentMethodImagesActual = brainTreeCustomerAccountService
				.getAcceptedPaymentMethodImages();

		assertEquals(acceptedPaymentMethodImagesExpected, acceptedPaymentMethodImagesActual);
	}

	@Test
	public void shouldGetMerchantAccountIdByCurrentSiteNameAndCurrencyNoMerchantAccounts()
	{
		final String merchantAccountIdExpected = null;
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_MERCHANT_ACCOUNT_IDS)).thenReturn("");

		final String merchantAccountIdActual = brainTreeCustomerAccountService
				.getMerchantAccountIdByCurrentSiteNameAndCurrency(CURRENT_SITE_NAME);

		assertEquals(merchantAccountIdExpected, merchantAccountIdActual);
	}

	@Test
	public void shouldGetMerchantAccountIdByCurrentSiteNameAndCurrencyNoProperMerchantAccounts()
	{
		final String merchantAccountIdExpected = null;
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_MERCHANT_ACCOUNT_IDS))
				.thenReturn(MERCHANT_ACCOUNT_ID_TO_SITE_STR_ONE_UNKNOWN_ACCOUNT);

		final String merchantAccountIdActual = brainTreeCustomerAccountService
				.getMerchantAccountIdByCurrentSiteNameAndCurrency(CURRENT_SITE_NAME);

		assertEquals(merchantAccountIdExpected, merchantAccountIdActual);
	}

	@Test
	public void shouldGetMerchantAccountIdByCurrentSiteNameAndCurrencyOneMerchantAccount()
	{
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_MERCHANT_ACCOUNT_IDS)).thenReturn(MERCHANT_ACCOUNT_ID_TO_SITE_STR_ONE_ACCOUNT);

		final String merchantAccountIdActual = brainTreeCustomerAccountService
				.getMerchantAccountIdByCurrentSiteNameAndCurrency(CURRENT_SITE_NAME);

		assertEquals(MERCHANT_ACCOUNT_ID1, merchantAccountIdActual);
	}

	@Test
	public void shouldGetMerchantAccountIdByCurrentSiteNameAndCurrencyMoreThanOneMerchantAccount()
	{
		final Configuration configuration = mock(Configuration.class);
		when(configurationService.getConfiguration()).thenReturn(configuration);
		when(configuration.getString(BRAINTREE_MERCHANT_ACCOUNT_IDS))
				.thenReturn(MERCHANT_ACCOUNT_ID_TO_SITE_STR_MORE_THAN_ONE_ACCOUNT);

		final String merchantAccountIdActual = brainTreeCustomerAccountService
				.getMerchantAccountIdByCurrentSiteNameAndCurrency(CURRENT_SITE_NAME);

		assertEquals(MERCHANT_ACCOUNT_ID1, merchantAccountIdActual);
	}
}
