var HTML = {
			DIV : "<div>",
			BR : "<br/>",
			IMG : "<img/>",
			INPUT : "<input>",
			FORM : "<form>",
			CLASS : "class",
			SRC : "src",
			ALT : "alt",
			SCRIPT : "script",
			TYPE_TEXT : "text/javascript",
			HEAD : "head",
			CURSOR : "cursor",
			POINTER : "pointer",
			CHECKED : ":checked",
			TYPE : "type",
			
			
			
			
			
};
var CONST = { 
		  ADD_TO_CART_BUTTON : ".addToCartButton",
		  ADD_TO_CART_LAYER : "#addToCartLayer",
		  PAYPAL_CHECKOUT_IMAGE :".payPalCheckoutAddToCartImage",
		  SINGLE_USE_CHECKBOX : "#singleUseCheckboxAddToCart",
		  MINI_CART : ".miniCart",
		  MINI_CART_LAYER : "#miniCartLayer",
		  PAYPAL_CHECKOUT_MINI_IMAGE : ".payPalCheckoutMiniCartImage",
		  SINGLE_USE_MINI_CHECKBOX : "#singleUseCheckboxMiniCart",
		  FOOTER : ".footer",
		  PAYMENT_OPTIONS : "paymentOptions",
		  PAYMENT_OPTIONS_CLASS : ".paymentOptions",
		  FOOTER_IMG_SRC : "https://www.paypalobjects.com/webstatic/en_US/i/buttons/cc-badges-ppmcvdam.png",
		  PAYMENT_OPTIONS_TEXT : "Payment options",
		  GLOBAL_MESSAGES : "#globalMessages",
		  VALIDATION_CLASS : ".bt_validation",
		  ERROR_DIV : "<div class='alert negative bt_validation'>",
		  PAYMENT_MEHTOD_SECTION : "[name=paymentMethodSelection]",
		  BT_LIB_SRC : "https://js.braintreegateway.com/js/braintree-2.18.0.min.js",
		  HOSTED_FIELDS : ".hostedFields",
		  CREADIT_CARD_LABEL_ID : "#creditCardLabelId",
		  HEADLINE : 'headline',
		  DISABLE_CLICK : 'disableClick',
		  PAYPAL : 'paypal',
		  SELECT_PAYMENT : '#select_payment',
		  PAYMENT_METHOD_PAYPAL : '#paymentMethodPayPal',
		  PROP_CHECKED : 'checked',
		  PAYMENT_TYPE : "payment_type",
		  CARD_TYPE : "card_type",
		  CARD_DETAILS : "card_details",
		  PAYMENT_METHOD_NONCE : "bt_payment_method_nonce",
		  ATTR_ACTION : "action",
		  ATTR_METHOD : "method",
		  ATTR_NAME : "name",
		  PAYPAL_CMS_IMAGE_SELECTOR : '.paypalimage .cmsimage img',
		  PAYPAL_CHECKOUT_IMAGE_SELECTOR : '.payPalCheckoutImage .cmsimage img',
		  LIABILITY_SHIFTED : "liability_shifted",
		  ERROR_ALERT_NEGATIVE_CSS_CLASS : 'init_error alert negative',
		  FORM_METHOD_POST : "POST",
		  BRAINTREE_PAYMENT_FORM_ID : 'braintree-payment-form',
		  SUBMIT_CILENT_ORDER_POST_FORM_ID : '#submit_silentOrderPostForm',
		  SAVE_PAYMENT_INFO_ID : '#savePaymentInfo',
		  SINGLE_USE_CHECKBOX_ID : '#singleUseCheckbox',
		  ALERT_POSITIVE_BT_VALIDATION_CSS_CLASS : 'alert positive bt_validation',
		  NUMBER_ID : "#number",
		  EXPIRATION_DATE_ID : "#expiration-date",
		  CVV_ID : "#cvv",
		  PAYMENT_METHOD_BT_ID : '#paymentMethodBT',
		  USE_DELIVERY_ADDRESS_ID : '#useDeliveryAddress',
		  PAYPAL_EXPRESS_FORM_NAME : "payPalExpress",
		  CUSTOM_SINGLE_USE_CHECKBOX_ID : '#customSingleUseCheckbox',
		  PAYPAL_IS_VALID_ID : '#paypal_is_valid',
		  PAYPAL_EMAIL_ID : '#paypal_email',
		  PAYPAL_EXPRESS_ERROR_ID : '#paypal_express_error',
};
var EVENTS = {
		HOVER : "hover",
		CLICK : "click",
		CHANGE : "change",
		
		
}
jQuery(document).ready(function($) {

	var deviceData;
	var client;
	var paymentMethodResponse;
	
	
	
	

	// add payment method images to footer section for all pages
	addPaymentMethodImagesToFooter();

	configurePayPalSingleUse();

	// PayPal along with Hosted Fields checkout configuration
	if ((typeof paymentMethodsPage != 'undefined')) {
		configurePayPalAlongWithHostedFields();
		if ((typeof secure3d != 'undefined' && JSON.parse(secure3d))) {
			configure3DSecure();
		}
	}
	
	// PayPal Shopping Cart Shortcut checkout configuration
	if (typeof shoppingCart != 'undefined' && shoppingCart != '') {
		configurePayPalShoppingCartCheckout();
	}

	// PayPal Add to Cart Shortcut checkout configuration
	if ($(CONST.ADD_TO_CART_BUTTON).length) {
		configurePaypalMiniCartShortcut(CONST.ADD_TO_CART_LAYER, CONST.PAYPAL_CHECKOUT_IMAGE,CONST.SINGLE_USE_CHECKBOX);
	}

	// PayPal Mini Cart Shortcut checkout configuration
	if ($(CONST.MINI_CART).length) {
		configurePaypalMiniCartShortcut(CONST.MINI_CART_LAYER, CONST.PAYPAL_CHECKOUT_MINI_IMAGE, CONST.SINGLE_USE_MINI_CHECKBOX);
	}
});

function addPaymentMethodImagesToFooter() {
	var paymentOptionsTextDiv = $(HTML.DIV).attr(HTML.CLASS, CONST.PAYMENT_OPTIONS);
	var br = $(HTML.BR);
	$(CONST.FOOTER).prepend(br);
	$(CONST.FOOTER).prepend(br);
	$(CONST.FOOTER).prepend(paymentOptionsTextDiv);
	var paymentOptionsImage = $(HTML.IMG);
	paymentOptionsImage
			.attr(
					HTML.SRC,
					CONST.FOOTER_IMG_SRC);
	paymentOptionsImage.attr(HTML.ALT,CONST.PAYMENT_OPTIONS_TEXT);
	$(CONST.PAYMENT_OPTIONS_CLASS).append(paymentOptionsImage);
}

function configure3DSecure() {
	client = new braintree.api.Client({
		clientToken : clientToken
	});
}

function verify3DSecure(paymentResponse) {
	client.verify3DS(
		{
			amount : amount,
			creditCard : paymentResponse.nonce
		},
		function(error, response) {
			if (!error) {
				// 3DSecure finished
				// add 3DSecure returned nonce
				paymentResponse.nonce = response.nonce;
				var liabilityShifted = response.verificationDetails.liabilityShifted;
				// allow process card if 3dSecureLiabilityResult is
				// skipped by merchant
				if (liabilityShifted
						|| JSON.parse(skip3dSecureLiabilityResult)) {
					paymentResponse.liabilityShifted = liabilityShifted;
					processResponce(paymentResponse);
				} else {
					show3DSecureMessage(ACC.addons.braintreeb2baddon['braintree.message.unsecured.card']);
				}
			} else {
				show3DSecureMessage(error);
				console.error(error);
			}
		});
}

function show3DSecureMessage(message) {
	$(CONST.GLOBAL_MESSAGES).children(CONST.VALIDATION_CLASS).remove();
	var errorComponent = CONST.ERROR_DIV + message
			+ HTML.DIV;
	$(CONST.GLOBAL_MESSAGES).append(errorComponent);
}

function configurePayPalAlongWithHostedFields() {
	
	// remove paypal validation messages
	$(CONST.GLOBAL_MESSAGES).children(CONST.VALIDATION_CLASS).remove();

	// add select payment method event
	$(CONST.PAYMENT_MEHTOD_SECTION).change(function() {
		paymentMethod();
	});

	// render appropriate payment method
	selectPaymentMethod();

}

function addBrainTreeLibrary() {
	var script = document.createElement(HTML.SCRIPT);
	script.type = HTML.TYPE_TEXT;
	script.src = CONST.BT_LIB_SRC;
	document.getElementsByTagName(HTML.HEAD)[0].appendChild(script);
}

function configurePaypalMiniCartShortcut(popupDivId, payPalImageClass,
		checkboxId) {
	addBrainTreeLibrary();
	$(document).on(EVENTS.HOVER,popupDivId, function() {
		$(this).css(HTML.CURSOR, HTML.POINTER);
		checkPayPalVaultInForCart(payPalImageClass, checkboxId);
	});
	$(document).on(EVENTS.CHANGE,checkboxId, function() {
		checkPayPalVaultInForCart(payPalImageClass, checkboxId);
	});
}

function checkPayPalVaultInForCart(payPalImageClass, checkboxId) {
	if ($(checkboxId).is(HTML.CHECKED)) {
		initialisePaypal(false, payPalImageClass);
	} else {
		initialisePaypal(true, payPalImageClass);
	}
}

function paymentMethod() {

		if ($('input[name=paymentMethodSelection]:radio:checked').val() == CONST.PAYPAL || $('input[name=onlyPayPalSelected]').val() == 'true') {
			$(CONST.HOSTED_FIELDS).hide();
			$(CONST.CREADIT_CARD_LABEL_ID).removeClass(CONST.HEADLINE);
			$(CONST.PAYPAL_CMS_IMAGE_SELECTOR).removeClass(CONST.DISABLE_CLICK);
			$(CONST.SELECT_PAYMENT).val(CONST.PAYPAL);
			$(CONST.GLOBAL_MESSAGES).children('.bt_validation').remove();
			
		} else {
			$(CONST.HOSTED_FIELDS).show();
			$(CONST.CREADIT_CARD_LABEL_ID).addClass(CONST.HEADLINE);
			$(CONST.PAYPAL_CMS_IMAGE_SELECTOR).addClass(CONST.DISABLE_CLICK);
			$(CONST.SELECT_PAYMENT).val("bt");
		}
}


function selectPaymentMethod() {
	if (typeof isCreditCardSelect != 'undefined' && isCreditCardSelect != ''
			&& isCreditCardSelect == "true") {
		$(CONST.PAYMENT_METHOD_BT_ID).prop(CONST.PROP_CHECKED, true);
		initializeBTclientSDK(true);
	} else if (typeof isSingleUseSelect != 'undefined' && isSingleUseSelect != '') {
		$(CONST.PAYMENT_METHOD_PAYPAL).prop(CONST.PROP_CHECKED, true);
		// select paypal as default
	} else {
		$(CONST.PAYMENT_METHOD_PAYPAL).prop(CONST.PROP_CHECKED, true);
		initializeBTclientSDK(true);
	}
	paymentMethod();
}

function processResponce(responce) {

	var submitForm = $('#' + CONST.BRAINTREE_PAYMENT_FORM_ID);

	var useDelivery = createHiddenParameter("use_delivery_address", $(
			CONST.USE_DELIVERY_ADDRESS_ID).is(HTML.CHECKED));
	var paymentType = createHiddenParameter(CONST.PAYMENT_TYPE, responce.type);
	var cardType = createHiddenParameter(CONST.CARD_TYPE, responce.details.cardType);
	var cardDetails = createHiddenParameter(CONST.CARD_DETAILS,
			responce.details.lastTwo);

	var isLiabilityShifted = '';

	var paymentNonce = createHiddenParameter(CONST.PAYMENT_METHOD_NONCE,
			responce.nonce);
	submitForm.append($(paymentNonce));
	if (typeof responce.liabilityShifted != 'undefined') {
		isLiabilityShifted = responce.liabilityShifted;
	}

	var liabilityShifted = createHiddenParameter(CONST.LIABILITY_SHIFTED,
			isLiabilityShifted);
	submitForm.append($(liabilityShifted));

	// collect device data for advanced fraud tools
	var deviceData = createHiddenParameter("device_data", this.deviceData);

	submitForm.append($(deviceData));
	submitForm.append($(useDelivery));
	submitForm.append($(paymentType));
	submitForm.append($(cardType));
	submitForm.append($(cardDetails));

	submitForm.submit()
}

function showSuccessPaypalMessage(nonce, email) {
	if (nonce != null) {
		$(CONST.PAYPAL_IS_VALID_ID).val("true");
		var errorComponent = $(HTML.DIV).attr(HTML.CLASS, CONST.ALERT_POSITIVE_BT_VALIDATION_CSS_CLASS);
		errorComponent.prepend(ACC.addons.braintreeb2baddon['braintree.message.paypal.error'] + email);

		$(CONST.GLOBAL_MESSAGES).append(errorComponent);
		$(CONST.PAYPAL_EMAIL_ID).val(email);
	}
	$(CONST.SUBMIT_CILENT_ORDER_POST_FORM_ID).trigger( EVENTS.CLICK,[true] );
}

function resetHostedFields() {
	$(CONST.NUMBER_ID).empty();
	$(CONST.EXPIRATION_DATE_ID).empty();
	$(CONST.CVV_ID).empty();
}

function initializeBTclientSDK(singleUse) {
	var checkout;
	var paypal;

	// reset hosted fields in case repeated initialization
	resetHostedFields();

	// configure advance fraud tools
	var dataCollector;
	if (JSON.parse(advancedFraudToolsEnabled)) {
		dataCollector = {
			kount: {environment: environment}
		};

		// add custom merchant kount id if exists
		if(typeof kountId != 'undefined' && kountId != '') {
			dataCollector.merchantId = kountId;
		}
	}

	// configure paypal connections
	if (singleUse) {
		paypal = {
			singleUse : singleUse,
			enableShippingAddress : true,
			enableBillingAddress : true,
			headless : true,
			amount : amount,
			currency : currency,
			locale : locale,
			shippingAddressOverride : {
				recipientName : recipientName,
				streetAddress : streetAddress,
				extendedAddress : extendedAddress,
				locality : locality,
				countryCodeAlpha2 : countryCodeAlpha2,
				postalCode : postalCode,
				region : region,
				phone : phone,
				editable : true
			},
			onSuccess : showSuccessPaypalMessage
		};
	} else {
		paypal = {
			singleUse : singleUse,
			headless : true,
			enableShippingAddress : true,
			enableBillingAddress : true,
			onSuccess : showSuccessPaypalMessage,
			locale : locale
		};

		// configure advanced fraud tools only for vaulted PayPal
		if (JSON.parse(advancedFraudToolsEnabled)) {
			dataCollector.paypal = true;
		}

		// configure display name for paypal connection
		if (typeof dbaName != 'undefined' && dbaName != '') {
			if ( dbaName.indexOf('*') > -1) {
				paypal.displayName = dbaName.substr(0, dbaName.indexOf('*'));
			}
		}
	}

	// Configure braintree client SDK
	if ((typeof clientToken != 'undefined' && clientToken != '')
			&& (typeof braintree != 'undefined' && braintree != '')) {
		var b = braintree.setup(clientToken, "custom", {
			id : CONST.BRAINTREE_PAYMENT_FORM_ID,
			dataCollector : dataCollector,
			onReady : function(integration) {
				// enable custom paypal integration
				checkout = integration;
				// get device data if exists
				if (typeof integration.deviceData != 'undefined' && integration.deviceData != '') {
					deviceData = integration.deviceData;
				} else {
					console.log("Device data is not returned");
				}
			},

			onError : function(error) {
				handleClientError(error);
			},

			// custom credit fields configuration
			hostedFields : {
				styles : {
					// Styling element state
					":focus" : {
						"color" : "blue"
					},
					".valid" : {
						"color" : "green"
					},
					".invalid" : {
						"color" : "red"
					}
				},
				number : {
					selector : CONST.NUMBER_ID
				},
				expirationDate : {
					selector : CONST.EXPIRATION_DATE_ID,
					placeholder : "MM/YY"
				},
				cvv : {
					selector : CONST.CVV_ID
				}
			},

			// paypal configuration
			paypal : paypal,

			onPaymentMethodReceived : function(response) {
				paymentMethodResponse = response;
				console.log("paymentMethodResponse = " + paymentMethodResponse);
				console.log("BT nonce = " + response.nonce);
				if ((typeof secure3d != 'undefined' && JSON.parse(secure3d)) && ("CreditCard" == response.type)) {
					verify3DSecure(response);
				} else {
					processResponce(response);
				}
			}
		});
	} else {
		var globalErrorsComponent = $(CONST.GLOBAL_MESSAGES);
		globalErrorsComponent.empty();
		globalErrorsComponent.append(createErrorDiv(ACC.addons.braintreeb2baddon['braintree.message.use.saved.payments']));
	}

	// Add a click event listener to PayPal image
	$(CONST.SUBMIT_CILENT_ORDER_POST_FORM_ID).click(function(event,goToSummary) {
		if (typeof checkout !== 'undefined') {
			// initialize paypal authorization
			if($(CONST.PAYMENT_METHOD_PAYPAL).prop(CONST.PROP_CHECKED) && goToSummary != true){
				checkout.paypal.initAuthFlow();
			}
		}
	});
}

function processPaypalResponse(paypalResponse, singleUse) {
	var payPalCheckoutForm = createPayPalExpressCheckoutForm(paypalResponse, singleUse);
	payPalCheckoutForm.submit();
}

function initialisePaypal(singleUse, payPalCheckoutImage) {
	if ((typeof clientTokenForPaypal != 'undefined' && clientTokenForPaypal != '')
			&& (typeof braintree != 'undefined' && braintree != '')) {

		var dataCollector;
		var paypal;
		if (singleUse) {
			// configure paypal integration
			paypal = {
				singleUse : singleUse,
				enableShippingAddress : true,
				enableBillingAddress : true,
				headless : true,
				amount : amount,
				currency : currency,
				locale : locale
			};
		} else {
			paypal = {
				singleUse : singleUse,
				headless : true,
				enableShippingAddress : true,
				enableBillingAddress : true,
				locale : locale
			};
			// configure advanced fraud tools only for vaulted PayPal
			if (JSON.parse(advancedFraudToolsEnabled)) {
				dataCollector = {
					paypal: true  // Enables fraud prevention
				};
				console.info("dataCollector= " + dataCollector);
			}
		}

		// configure display name for paypal connection
		if (typeof dbaName != 'undefined' && dbaName != '') {
			if ( dbaName.indexOf('*') > -1) {
				paypal.displayName = dbaName.substr(0, dbaName.indexOf('*'));
			}
		}

		// configure custom UI paypal shortcut
		var checkout;

		braintree.setup(clientTokenForPaypal, "custom", {
			dataCollector : dataCollector,

			onReady : function(integration) {
				console.log(integration);
				// enable custom paypal integration
				checkout = integration;
				// get device data if returned
				if (typeof integration.deviceData != 'undefined' && integration.deviceData != '') {
					deviceData = integration.deviceData;
				} else {
					console.log("Device data is not returned");
				}
			},

			paypal : paypal,

			onError : function(error) {
				handlePayPalExpressClientError(error);
			},

			onPaymentMethodReceived : function(paypalResponse) {
				processPaypalResponse(paypalResponse, singleUse);
			}
		});

		$(payPalCheckoutImage).unbind(EVENTS.CLICK);
		// Add a click event listener to PayPal image
		$(payPalCheckoutImage).click(function() {
			if (typeof checkout !== 'undefined') {
				// initialize paypal authorization
				checkout.paypal.initAuthFlow();
			}
		});
	} else {
		var payPalErrorComponent = $(CONST.PAYPAL_EXPRESS_ERROR_ID);
		payPalErrorComponent.empty();
		payPalErrorComponent.append(createErrorDiv(ACC.addons.braintreeb2baddon['braintree.message.try.refresh']));
	}
}

function createPayPalExpressCheckoutForm(paypalResponse, singleUse){
	
	var payPalForm = createForm(CONST.PAYPAL_EXPRESS_FORM_NAME, ACC.config.encodedContextPath + "/braintree/paypal/checkout/express");
	
	var paymentType = createHiddenParameter("payPalData", JSON.stringify(paypalResponse));
	var token = $("[name='CSRFToken']").val();
	var realToken = createHiddenParameter("CSRFToken",token);

	var singleUseSelected = createHiddenParameter("is_single_use_select", singleUse);
	// collect device data for advanced fraud tools
	console.log('deviceData= ' + this.deviceData);
	var collectDeviceData = createHiddenParameter("device_data", this.deviceData);

	payPalForm.append($(singleUseSelected));
	payPalForm.append($(collectDeviceData));
	payPalForm.append($(paymentType));
	payPalForm.append($(realToken));
	payPalForm.appendTo('body');

	return payPalForm;
}

function configurePayPalSingleUse() {
	$(CONST.SAVE_PAYMENT_INFO_ID)
			.on(
					EVENTS.CHANGE,
					function() {
						if ($('input[name=paymentMethodSelection]:radio:checked')
								.val() == CONST.PAYPAL) {
							if ($(CONST.SAVE_PAYMENT_INFO_ID).is(HTML.CHECKED)) {
								initializeBTclientSDK(false);
							} else {
								initializeBTclientSDK(true);
							}
						}
					});
}

function configurePayPalShoppingCartCheckout() {
	selectIsSingleCheckbox();
	activatePaypalStandardImage();
	$(CONST.SINGLE_USE_CHECKBOX_ID).on(
			EVENTS.CHANGE,
		function() {
			if ($(CONST.SINGLE_USE_CHECKBOX_ID).is(HTML.CHECKED)) {
				initialisePaypal(false,
						CONST.PAYPAL_CHECKOUT_IMAGE_SELECTOR);
			} else {
				initialisePaypal(true,
						CONST.PAYPAL_CHECKOUT_IMAGE_SELECTOR);
			}
		});
}

function activatePaypalStandardImage() {
	$(CONST.PAYPAL_CHECKOUT_IMAGE_SELECTOR).hover(function() {
		$(this).css('cursor', 'pointer');
	});
}

function selectIsSingleCheckboxCustomUI() {
	if (JSON.parse(singleUse)) {
		$(CONST.CUSTOM_SINGLE_USE_CHECKBOX_ID).prop(CONST.PROP_CHECKED, false);
		initializeBTclientSDK(true);
	} else {
		$(CONST.CUSTOM_SINGLE_USE_CHECKBOX_ID).prop(CONST.PROP_CHECKED, true);
		initializeBTclientSDK(false);
	}
}

function selectIsSingleCheckbox() {
	if (JSON.parse(singleUse)) {
		$(CONST.SINGLE_USE_CHECKBOX_ID).prop(CONST.PROP_CHECKED, false);
		// initialize standard paypal by braintree
		initialisePaypal(true, CONST.PAYPAL_CHECKOUT_IMAGE_SELECTOR);
	} else {
		$(CONST.SINGLE_USE_CHECKBOX_ID).prop(CONST.PROP_CHECKED, true);
		// initialize standard paypal by braintree
		initialisePaypal(false, CONST.PAYPAL_CHECKOUT_IMAGE_SELECTOR);
	}
}


function createForm(name,action){
	var form = $(HTML.FORM).attr(CONST.ATTR_ACTION, action)
	.attr(CONST.ATTR_NAME, name).attr(CONST.ATTR_METHOD, CONST.FORM_METHOD_POST);
	return form;
}

function createHiddenParameter(name, value) {
	var input = $(HTML.INPUT).attr(HTML.TYPE, "hidden").attr(CONST.ATTR_NAME, name).val(
			value);
	return input;
}

function handleClientError(error) {
	if (typeof error != 'undefined' || error != 'undefined') {
		// skip validation error: use paypal method
		if ('User did not enter a payment method' != error.message) {
				$(CONST.GLOBAL_MESSAGES).empty();
				$(CONST.GLOBAL_MESSAGES)
						.append(
								createErrorDiv(ACC.addons.braintreeb2baddon['braintree.message.error.provider']
										+ ' ' + error.message));
				console.error("[ERROR] Code: " + error.type + " Message: "
						+ error.message);

		}
	}
}

function handlePayPalExpressClientError(error) {
	if (typeof error != 'undefined' || error != 'undefined') {
		// skip validation error: use paypal method
		if ('User did not enter a payment method' != error.message) {
			$('#paypal_express_error').empty();
			var messageText = error.message;
			if (typeof messageText === 'undefined' || messageText === 'undefined') {
				console.error("Undefined error");
			} else {
				$(CONST.PAYPAL_EXPRESS_ERROR_ID).empty();
				$(CONST.PAYPAL_EXPRESS_ERROR_ID)
						.append(
								createErrorDiv(ACC.addons.braintreeb2baddon['braintree.message.error.provider']
										+ ' ' + messageText));
				console.error("[ERROR] Code: " + error.type + " Message: "
						+ messageText);
			}
		}
	}
}

function createErrorDiv(message) {
	var errorComponent = $(HTML.DIV).attr(HTML.CLASS, CONST.ERROR_ALERT_NEGATIVE_CSS_CLASS);
	errorComponent.prepend(message);
	return errorComponent;
}
