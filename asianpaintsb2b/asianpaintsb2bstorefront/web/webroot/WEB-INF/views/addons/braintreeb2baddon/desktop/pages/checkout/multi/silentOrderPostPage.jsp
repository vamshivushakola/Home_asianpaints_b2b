<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/formElement" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/desktop/checkout/multi" %>
<%@ taglib prefix="b2b-multi-checkout" tagdir="/WEB-INF/tags/addons/b2bacceleratoraddon/desktop/checkout/multi" %>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/desktop/address" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:url value="${currentStepUrl}" var="choosePaymentMethodUrl"/>
<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">

	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	<multi-checkout:checkoutProgressBar steps="${checkoutSteps}" progressBarId="${progressBarId}"/>

	<c:if test="${not empty paymentFormUrl}">
		<div class="span-14 append-1">
			<div id="checkoutContentPanel" class="clearfix">
			<c:choose>
			<c:when test="${payPalStandardEnabled}">
				<div style="overflow: auto;" id="paypal">
				<c:choose>
				<c:when test="${hostedFieldsEnable}">
					<input id="paymentMethodPayPal" type="radio"
						name="paymentMethodSelection" value="paypal"
						class="paypalselection" checked="true" />
						</c:when>
						<c:otherwise>
						<input type="hidden" name="onlyPayPalSelected" value="true"/>
						</c:otherwise>
						</c:choose>
							<div class="paypalimage" id="paypal-container">
								<div class="cmsimage">
									<img
										src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/pp-acceptance-medium.png"
										alt="Buy now with PayPal" />
								</div>
							</div>
							<div id="text" class="paypalurl">
								<a style="padding-left: 10px;"
									href="https://www.paypal.com/webapps/mpp/paypal-popup"
									title="How PayPal Works"
									onclick="javascript:window.open('https://www.paypal.com/webapps/mpp/paypal-popup','WIPaypal','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1060, height=700'); return false;"><spring:theme
										code="braintree.text.what.is.paypal" />?</a>
							</div>
				</div>
				</c:when>
				<c:otherwise>
					<div style="overflow: auto;"></div>
				</c:otherwise>
				</c:choose>
				<br>
				<c:choose>
				<c:when test="${hostedFieldsEnable}">
				<div style="overflow: auto;" id="braintree">
				<c:if test="${payPalStandardEnabled}">
					<input id="paymentMethodBT" type="radio"
						name="paymentMethodSelection" value="bt" class="paypalselection"/>
						</c:if>
					<c:if test="${not empty paymentsImagesURL}">
						<c:forEach items="${paymentsImagesURL}" var="url">
							<img src="${url.value}" alt="${url.key}" />
						</c:forEach>
					</c:if>
					<input type="hidden"
						value="false"
						class="text" name="paypal_is_valid" id="paypal_is_valid">
				</div>
				</c:when>
				<c:otherwise>
					<div style="overflow: auto;"></div>
				</c:otherwise>
				</c:choose>
				<ycommerce:testId code="paymentDetailsForm">
					<div class="payWithCardSection">
						<form:form id="braintree-payment-form" name="silentOrderPostForm"
							commandName="sopPaymentDetailsForm"
							class="create_update_payment_form"
							action="${request.contextPath}/braintree/checkout/hop/response"
							method="POST">
							<div class="hostedFields">
								<div class="headline">
									<spring:theme
										code="checkout.multi.paymentMethod.addPaymentDetails.paymentCard" />
								</div>
								<div class="description">
									<spring:theme
										code="checkout.multi.paymentMethod.addPaymentDetails.enterYourCardDetails" />
								</div>
								<div class="control-group cardForm" style="dispaly: none;" id="cardForn">
									<label for="number" class="control-label "><spring:theme
											code="braintree.text.cc.number" /></label>
									<div id="number" class="controls"></div>
									<label for="cvv" class="control-label "><spring:theme
											code="braintree.text.cc.cvv" /></label>
									<div id="cvv" class="controls"></div>
									<label for="expiration-date" class="control-label "><spring:theme
											code="braintree.text.cc.expiration.date" /></label>
									<div id="expiration-date" class="controls"></div>
								</div>
							</div>
							<br/>
							<div id="savePaymentButton">
							<c:if test="${not empty braintreePaymentInfos}">
								<button type="button" class="positive clear view-saved-payments" id="viewSavedPayments">
									<spring:theme code="checkout.multi.paymentMethod.viewSavedPayments" text="View Saved Payments"/>
								</button>
							</c:if>
							</div>
								<div class="form-additionals" id="savePaymentInfoComponent">
								<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
									<formElement:formCheckbox idKey="savePaymentInfo"
										labelKey="checkout.multi.sop.savePaymentInfo"
										path="savePaymentInfo" inputCSS="" labelCSS=""
										mandatory="false" />
								</sec:authorize>
							</div>
						
							
							<div class="headline clear">
								<spring:theme
									code="checkout.multi.paymentMethod.addPaymentDetails.billingAddress" />
							</div>

							<c:if test="${cartData.deliveryItemsQuantity > 0}">
								<form:checkbox path="useDeliveryAddress" id="useDeliveryAddress"
									data-firstname="${deliveryAddress.firstName}"
									data-lastname="${deliveryAddress.lastName}"
									data-line1="${deliveryAddress.line1}"
									data-line2="${deliveryAddress.line2}"
									data-town="${deliveryAddress.town}"
									data-postalcode="${deliveryAddress.postalCode}"
									data-countryisocode="${deliveryAddress.country.isocode}"
									data-regionisocode="${deliveryAddress.region.isocodeShort}"
									data-address-id="${deliveryAddress.id}" tabindex="11" />
								<spring:theme code="checkout.multi.sop.useMyDeliveryAddress" />
							</c:if>
							<input type="hidden" name="paypal_email" id="paypal_email"/>
							<input type="hidden"
								value="${silentOrderPageData.parameters['billTo_email']}"
								class="text" name="billTo_email" id="billTo_email">
							<address:billAddressFormSelector
								supportedCountries="${countries}" regions="${regions}"
								tabindex="12" />
							<div class="form-additionals"></div>

							<div class="form-actions">
								<c:url value="/checkout/multi/delivery-method/choose"
									var="chooseDeliveryMethodUrl" />
								<a class="button" href="${chooseDeliveryMethodUrl}"><spring:theme
										code="checkout.multi.cancel" text="Cancel" /></a>
								<button class="positive right"
									tabindex="20" id="submit_silentOrderPostForm">
									<spring:theme code="checkout.multi.paymentMethod.continue"
										text="Continue" />
								</button>
							</div>
						</form:form>
					</div>

			</div>
			</ycommerce:testId>


			
		<c:if test="${not empty braintreePaymentInfos}">
				<div id="savedPaymentListHolder">
					<div id="savedPaymentList" class="summaryOverlay clearfix">
						<div class="headline"><spring:theme code="checkout.summary.paymentMethod.savedCards.header"/></div>
						<div class="description"><spring:theme code="checkout.summary.paymentMethod.savedCards.selectSavedCardOrEnterNew"/></div>

						<div class="paymentList">
							<c:forEach items="${braintreePaymentInfos}" var="paymentInfo" varStatus="status">
								<div class="paymentEntry">
								<form
									action="${request.contextPath}/checkout/multi/payment-method/choose"
									method="GET">
									<input type="hidden" name="selectedPaymentMethodId"
										value="${paymentInfo.id}" />
									<ul>
										<c:choose>
											<c:when
												test="${paymentInfo.subscriptionId eq 'BrainTreePayPalExpress' or paymentInfo.subscriptionId eq 'PayPalAccount'}">
												<li>PayPal</li>
												<li>${fn:escapeXml(paymentInfo.billingAddress.email)}</li>
												<li><img height="38" width="56"
													src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/pp-acceptance-small.png"
													alt="PayPal icon" style="margin-top: 18px;"/></li>
											</c:when>
											<c:otherwise>
												<li>${fn:escapeXml(paymentInfo.cardType)}</li>
												<li>${fn:escapeXml(paymentInfo.cardNumber)}</li>
												<c:if test="${not empty paymentInfo.expiryMonth}">
												<li><spring:theme
												code="checkout.multi.paymentMethod.paymentDetails.expires"
												arguments="${fn:escapeXml(paymentInfo.expiryMonth)},${fn:escapeXml(paymentInfo.expiryYear)}" /></li>
												</c:if>
												<c:if test="${not empty paymentInfo.accountHolderName}">
													<li><img
														src="${fn:escapeXml(paymentInfo.accountHolderName)}"
														alt="Card Type" /></li>
												</c:if>
											</c:otherwise>
										</c:choose>
										<li>${fn:escapeXml(paymentInfo.billingAddress.firstName)}&nbsp;
											${fn:escapeXml(paymentInfo.billingAddress.lastName)}</li>
										<li>${fn:escapeXml(paymentInfo.billingAddress.line1)}</li>
										<li>${fn:escapeXml(paymentInfo.billingAddress.region.isocodeShort)}&nbsp;
											${fn:escapeXml(paymentInfo.billingAddress.town)}</li>
										<li>${fn:escapeXml(paymentInfo.billingAddress.postalCode)}</li>
									</ul>
									<button type="submit" class="positive right"
										tabindex="${status.count + 21}">
										<spring:theme code="checkout.multi.sop.useThisPaymentInfo"
											text="Use this Payment Info" />
									</button>
								</form>
								<form:form action="${request.contextPath}/checkout/multi/payment-method/remove" method="POST">
										<input type="hidden" name="paymentInfoId" value="${paymentInfo.id}"/>
										<button type="submit" class="negative remove-payment-item right" tabindex="${status.count + 22}">
											<spring:theme code="checkout.multi.sop.remove" text="Remove"/>
										</button>
									</form:form>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<b2b-multi-checkout:checkoutOrderDetails cartData="${cartData}" showShipDeliveryEntries="true" showPickupDeliveryEntries="true" showTax="true"/>
	</c:if>


	<cms:pageSlot position="SideContent" var="feature" element="div" class="span-24 side-content-slot cms_disp-img_slot">
		<cms:component component="${feature}"/>
	</cms:pageSlot>
	<script src="https://js.braintreegateway.com/js/braintree-2.18.0.min.js"></script>
	<script>
		var paymentMethodsPage = "paymentMethodsPage";

		var clientToken = "${client_token}";
		var isCreditCardSelect = "${is_credit_card_select}";
		var isSingleUseSelect = "${is_single_use_select}";

		var advancedFraudToolsEnabled = "${payPalCheckoutData.advancedFraudTools}";
		var environment = "${payPalCheckoutData.environment}";
		var kountId = "${payPalCheckoutData.kountId}";
		var secure3d = "${payPalCheckoutData.secure3d}";
		var skip3dSecureLiabilityResult = "${payPalCheckoutData.skip3dSecureLiabilityResult}";
		var dbaName = "${payPalCheckoutData.dbaName}";

		// only paypal specific configuration options
		var singleUse = "${payPalCheckoutData.singleUse}";
		var amount = "${payPalCheckoutData.amount}";
		var locale = "${payPalCheckoutData.locale}";
		var currency = "${payPalCheckoutData.currency}";
		var recipientName = "${payPalCheckoutData.shippingAddressOverride.recipientName}";
		var streetAddress = "${payPalCheckoutData.shippingAddressOverride.streetAddress}";
		var extendedAddress = "${payPalCheckoutData.shippingAddressOverride.extendedAddress}";
		var locality = "${payPalCheckoutData.shippingAddressOverride.locality}";
		var countryCodeAlpha2 = "${payPalCheckoutData.shippingAddressOverride.countryCodeAlpha2}";
		var postalCode = "${payPalCheckoutData.shippingAddressOverride.postalCode}";
		var region = "${payPalCheckoutData.shippingAddressOverride.region}";
		var phone = "${payPalCheckoutData.shippingAddressOverride.phone}";
	</script>

</template:page>
