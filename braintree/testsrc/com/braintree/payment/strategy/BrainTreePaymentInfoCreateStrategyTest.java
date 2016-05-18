/**
 *
 */
package com.braintree.payment.strategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.customer.CustomerEmailResolutionService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.braintree.configuration.service.BrainTreeConfigService;
import com.braintree.enums.BrainTreeCardType;
import com.braintree.hybris.data.BrainTreeSubscriptionInfoData;
import com.braintree.model.BrainTreePaymentInfoModel;


/**
 * @author Anton_Kasianchuk
 *
 */
@UnitTest
public class BrainTreePaymentInfoCreateStrategyTest
{
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String LINE1 = "LINE1";
	public static final String LINE2 = "LINE2";
	public static final String TOWN = "TOWN";
	public static final String POSTAL_CODE = "POSTAL_CODE";
	public static final String EMAIL = "EMAIL";
	public static final CountryData COUNTRY = mock(CountryData.class);
	public static final RegionData REGION = mock(RegionData.class);

	public static final String ISO_CODE = "ISO_CODE";

	public static final String UID = "UID123";
	public static final String PAYMENT_METHOD_TOKEN = "PAYMENT_METHOD_TOKEN";
	public static final String NONCE = "NONCE";
	public static final String DEVICE_DATA = "DEVICE_DATA";
	public static final String IMAGE_SOURCE = "IMAGE_SOURCE";
	public static final String EXPIRATION_MONTH = "EXPIRATION_MONTH";
	public static final String EXPIRATION_YEAR = "EXPIRATION_YEAR";
	public static final Boolean LIABILITY_SHIFTED = Boolean.TRUE;
	public static final String PAYMENT_PROVIDER = "PAYMENT_PROVIDER";
	public static final String PAYMENT_INFO = "PAYMENT_INFO";
	public static final boolean SAVE_PAYMENT_INFO = true;
	public static final String CARD_NUMBER = "CARD_NUMBER";
	public static final String CARD_TYPE = "CARD_TYPE";
	public static final Boolean THREED_SECURE_CONFIGURATION = Boolean.TRUE;
	public static final Boolean ADVANCED_FRAUD_TOOLS = Boolean.TRUE;
	public static final Boolean SKIP_3D_SECURE_LIABILITY_RESULT = Boolean.TRUE;
	public static final String CREDIT_CARD_STATEMENT_NAME = "CREDIT_CARD_STATEMENT_NAME";
	public static final String MERCHANT_ACCOUNT_ID_FOR_CURRENT_SITE_AND_CURRENCY = "MERCHANT_ACCOUNT_ID_FOR_CURRENT_SITE_AND_CURRENCY";
	public static final String BRAINTREE_CHANNEL = "BRAINTREE_CHANNEL";

	public static final String BRAINTREE_CUSTOMER_ID = "BRAINTREE_CUSTOMER_ID";

	private final AddressModel billingAddress = new AddressModel();
	private final UserModel userModel = mock(UserModel.class);
	private final CustomerModel customerModel = mock(CustomerModel.class);
	private final BrainTreePaymentInfoModel brainTreePaymentInfoModel = new BrainTreePaymentInfoModel();

	private final AddressData addressData = new AddressData();
	private final BrainTreeSubscriptionInfoData brainTreeSubscriptionInfoData = new BrainTreeSubscriptionInfoData();

	private final AddressModel billingAddressExpected = new AddressModel();
	private final BrainTreePaymentInfoModel brainTreePaymentInfoModelExpected = new BrainTreePaymentInfoModel();

	@Mock
	private BrainTreeConfigService brainTreeConfigService;
	@Mock
	private ModelService modelService;
	@Mock
	private CommonI18NService commonI18NService;
	@Mock
	private CustomerEmailResolutionService customerEmailResolutionService;
	@Mock
	private UserService userService;

	@Spy
	@InjectMocks
	private final BrainTreePaymentInfoCreateStrategy brainTreePaymentInfoCreateStrategy = new BrainTreePaymentInfoCreateStrategy();

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);

		final BrainTreeCardType brainTreeCardType = mock(BrainTreeCardType.class);
		doReturn(brainTreeCardType).when(brainTreePaymentInfoCreateStrategy)
				.getBrainTreeCardTypeByName(CARD_TYPE.replace("_", " "));

		addressData.setFirstName(FIRST_NAME);
		addressData.setLastName(LAST_NAME);
		addressData.setLine1(LINE1);
		addressData.setLine2(LINE2);
		addressData.setTown(TOWN);
		addressData.setPostalCode(POSTAL_CODE);
		addressData.setEmail(EMAIL);
		when(COUNTRY.getIsocode()).thenReturn(ISO_CODE);
		addressData.setCountry(COUNTRY);
		when(REGION.getIsocode()).thenReturn(ISO_CODE);
		addressData.setRegion(REGION);

		final CountryModel country = mock(CountryModel.class);
		when(commonI18NService.getCountry(ISO_CODE)).thenReturn(country);
		final RegionModel region = mock(RegionModel.class);
		when(commonI18NService.getRegion(country, ISO_CODE)).thenReturn(region);

		brainTreeSubscriptionInfoData.setPaymentMethodToken(PAYMENT_METHOD_TOKEN);
		brainTreeSubscriptionInfoData.setNonce(NONCE);
		brainTreeSubscriptionInfoData.setDeviceData(DEVICE_DATA);
		brainTreeSubscriptionInfoData.setImageSource(IMAGE_SOURCE);
		brainTreeSubscriptionInfoData.setExpirationMonth(EXPIRATION_MONTH);
		brainTreeSubscriptionInfoData.setExpirationYear(EXPIRATION_YEAR);
		brainTreeSubscriptionInfoData.setLiabilityShifted(LIABILITY_SHIFTED);
		brainTreeSubscriptionInfoData.setPaymentProvider(PAYMENT_PROVIDER);
		brainTreeSubscriptionInfoData.setSavePaymentInfo(SAVE_PAYMENT_INFO);
		brainTreeSubscriptionInfoData.setCardNumber(CARD_NUMBER);
		brainTreeSubscriptionInfoData.setCardType(CARD_TYPE);

		when(modelService.create(AddressModel.class)).thenReturn(billingAddress);
		when(modelService.create(BrainTreePaymentInfoModel.class)).thenReturn(brainTreePaymentInfoModel);

		when(userService.getCurrentUser()).thenReturn(userModel);
		when(Boolean.valueOf(userService.isAnonymousUser(userModel))).thenReturn(Boolean.TRUE);

		when(customerModel.getUid()).thenReturn(UID);
		when(customerModel.getBraintreeCustomerId()).thenReturn(BRAINTREE_CUSTOMER_ID);

		when(brainTreeConfigService.get3dSecureConfiguration()).thenReturn(THREED_SECURE_CONFIGURATION);
		when(brainTreeConfigService.getAdvancedFraudTools()).thenReturn(ADVANCED_FRAUD_TOOLS);
		when(brainTreeConfigService.getIsSkip3dSecureLiabilityResult()).thenReturn(SKIP_3D_SECURE_LIABILITY_RESULT);
		when(brainTreeConfigService.getCreditCardStatementName()).thenReturn(CREDIT_CARD_STATEMENT_NAME);
		when(brainTreeConfigService.getMerchantAccountIdForCurrentSiteAndCurrency())
				.thenReturn(MERCHANT_ACCOUNT_ID_FOR_CURRENT_SITE_AND_CURRENCY);
		when(brainTreeConfigService.getBrainTreeChannel()).thenReturn(BRAINTREE_CHANNEL);

		billingAddressExpected.setFirstname(FIRST_NAME);
		billingAddressExpected.setLastname(LAST_NAME);
		billingAddressExpected.setLine1(LINE1);
		billingAddressExpected.setLine2(LINE2);
		billingAddressExpected.setTown(TOWN);
		billingAddressExpected.setPostalcode(POSTAL_CODE);
		billingAddressExpected.setEmail(EMAIL);
		billingAddressExpected.setCountry(country);
		billingAddressExpected.setRegion(region);

		brainTreePaymentInfoModelExpected.setBillingAddress(billingAddress);
		brainTreePaymentInfoModelExpected.setCode(UID);
		brainTreePaymentInfoModelExpected.setUser(customerModel);
		brainTreePaymentInfoModelExpected.setPaymentMethodToken(PAYMENT_METHOD_TOKEN);
		brainTreePaymentInfoModelExpected.setNonce(NONCE);
		brainTreePaymentInfoModelExpected.setDeviceData(DEVICE_DATA);
		brainTreePaymentInfoModelExpected.setImageSource(IMAGE_SOURCE);
		brainTreePaymentInfoModelExpected.setExpirationMonth(EXPIRATION_MONTH);
		brainTreePaymentInfoModelExpected.setExpirationYear(EXPIRATION_YEAR);
		brainTreePaymentInfoModelExpected.setCustomerId(BRAINTREE_CUSTOMER_ID);
		brainTreePaymentInfoModelExpected.setLiabilityShifted(LIABILITY_SHIFTED);
		brainTreePaymentInfoModelExpected.setPaymentProvider(PAYMENT_PROVIDER);
		brainTreePaymentInfoModelExpected.setSaved(SAVE_PAYMENT_INFO);
		brainTreePaymentInfoModelExpected.setCardNumber(CARD_NUMBER);
		brainTreePaymentInfoModelExpected.setCardType(brainTreeCardType);

		brainTreePaymentInfoModelExpected.setThreeDSecureConfiguration(THREED_SECURE_CONFIGURATION);
		brainTreePaymentInfoModelExpected.setAdvancedFraudTools(ADVANCED_FRAUD_TOOLS);
		brainTreePaymentInfoModelExpected.setIsSkip3dSecureLiabilityResult(SKIP_3D_SECURE_LIABILITY_RESULT);
		brainTreePaymentInfoModelExpected.setCreditCardStatementName(CREDIT_CARD_STATEMENT_NAME);
		brainTreePaymentInfoModelExpected.setMerchantAccountIdForCurrentSite(MERCHANT_ACCOUNT_ID_FOR_CURRENT_SITE_AND_CURRENCY);
		brainTreePaymentInfoModelExpected.setBrainTreeChannel(BRAINTREE_CHANNEL);
	}

	@Test
	public void shouldSaveSubscription()
	{
		final BrainTreePaymentInfoModel brainTreePaymentInfoModelActual = brainTreePaymentInfoCreateStrategy
				.saveSubscription(addressData, customerModel, brainTreeSubscriptionInfoData);

		checkBrainTreePaymentInfoModelActual(brainTreePaymentInfoModelActual);
	}

	@Test
	public void shouldSaveSubscriptionNoEmailInAddress()
	{
		addressData.setEmail(null);
		when(customerEmailResolutionService.getEmailForCustomer(customerModel)).thenReturn(EMAIL);

		final BrainTreePaymentInfoModel brainTreePaymentInfoModelActual = brainTreePaymentInfoCreateStrategy
				.saveSubscription(addressData, customerModel, brainTreeSubscriptionInfoData);

		verify(customerEmailResolutionService).getEmailForCustomer(customerModel);

		checkBrainTreePaymentInfoModelActual(brainTreePaymentInfoModelActual);
	}

	@Test
	public void shouldSaveSubscriptionNoCountryInAddress()
	{
		addressData.setCountry(null);
		billingAddressExpected.setCountry(null);
		billingAddressExpected.setRegion(null);

		final BrainTreePaymentInfoModel brainTreePaymentInfoModelActual = brainTreePaymentInfoCreateStrategy
				.saveSubscription(addressData, customerModel, brainTreeSubscriptionInfoData);

		verify(commonI18NService, never()).getCountry(ISO_CODE);

		checkBrainTreePaymentInfoModelActual(brainTreePaymentInfoModelActual);
	}

	private void checkBrainTreePaymentInfoModelActual(final BrainTreePaymentInfoModel brainTreePaymentInfoModelActual)
	{
		assertEquals(brainTreePaymentInfoModelExpected.getCode(), brainTreePaymentInfoModelActual.getCode());
		assertEquals(brainTreePaymentInfoModelExpected.getUser(), brainTreePaymentInfoModelActual.getUser());
		assertEquals(brainTreePaymentInfoModelExpected.getPaymentMethodToken(),
				brainTreePaymentInfoModelActual.getPaymentMethodToken());
		assertEquals(brainTreePaymentInfoModelExpected.getNonce(), brainTreePaymentInfoModelActual.getNonce());
		assertEquals(brainTreePaymentInfoModelExpected.getDeviceData(), brainTreePaymentInfoModelActual.getDeviceData());
		assertEquals(brainTreePaymentInfoModelExpected.getImageSource(), brainTreePaymentInfoModelActual.getImageSource());
		assertEquals(brainTreePaymentInfoModelExpected.getExpirationMonth(), brainTreePaymentInfoModelActual.getExpirationMonth());
		assertEquals(brainTreePaymentInfoModelExpected.getExpirationYear(), brainTreePaymentInfoModelActual.getExpirationYear());
		assertEquals(brainTreePaymentInfoModelExpected.getCustomerId(), brainTreePaymentInfoModelActual.getCustomerId());
		assertEquals(brainTreePaymentInfoModelExpected.getLiabilityShifted(),
				brainTreePaymentInfoModelActual.getLiabilityShifted());
		assertEquals(brainTreePaymentInfoModelExpected.getPaymentProvider(), brainTreePaymentInfoModelActual.getPaymentProvider());
		assertEquals(Boolean.valueOf(brainTreePaymentInfoModelExpected.isSaved()),
				Boolean.valueOf(brainTreePaymentInfoModelActual.isSaved()));
		assertEquals(brainTreePaymentInfoModelExpected.getCardNumber(), brainTreePaymentInfoModelActual.getCardNumber());
		assertEquals(brainTreePaymentInfoModelExpected.getCardType(), brainTreePaymentInfoModelActual.getCardType());
		assertEquals(brainTreePaymentInfoModelExpected.getThreeDSecureConfiguration(),
				brainTreePaymentInfoModelActual.getThreeDSecureConfiguration());
		assertEquals(brainTreePaymentInfoModelExpected.getAdvancedFraudTools(),
				brainTreePaymentInfoModelActual.getAdvancedFraudTools());
		assertEquals(brainTreePaymentInfoModelExpected.getIsSkip3dSecureLiabilityResult(),
				brainTreePaymentInfoModelActual.getIsSkip3dSecureLiabilityResult());
		assertEquals(brainTreePaymentInfoModelExpected.getCreditCardStatementName(),
				brainTreePaymentInfoModelActual.getCreditCardStatementName());
		assertEquals(brainTreePaymentInfoModelExpected.getMerchantAccountIdForCurrentSite(),
				brainTreePaymentInfoModelActual.getMerchantAccountIdForCurrentSite());
		assertEquals(brainTreePaymentInfoModelExpected.getBrainTreeChannel(),
				brainTreePaymentInfoModelActual.getBrainTreeChannel());

		final AddressModel billingAddressActual = brainTreePaymentInfoModelActual.getBillingAddress();
		assertEquals(billingAddressExpected.getFirstname(), billingAddressActual.getFirstname());
		assertEquals(billingAddressExpected.getLastname(), billingAddressActual.getLastname());
		assertEquals(billingAddressExpected.getLine1(), billingAddressActual.getLine1());
		assertEquals(billingAddressExpected.getLine2(), billingAddressActual.getLine2());
		assertEquals(billingAddressExpected.getTown(), billingAddressActual.getTown());
		assertEquals(billingAddressExpected.getPostalcode(), billingAddressActual.getPostalcode());
		assertEquals(billingAddressExpected.getEmail(), billingAddressActual.getEmail());
		assertEquals(billingAddressExpected.getCountry(), billingAddressActual.getCountry());
		assertEquals(billingAddressExpected.getRegion(), billingAddressActual.getRegion());
	}

}
