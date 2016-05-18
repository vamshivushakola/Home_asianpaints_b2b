<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>




{"cartData": {
"total": "${cartData.totalPrice.value}",
"products": [
<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
	{
		"sku":		"${cartEntry.product.code}",
		"name": 	"<c:out value='${cartEntry.product.name}' />",
		"qty": 		"${cartEntry.quantity}",
		"price": 	"${cartEntry.basePrice.value}",
		"categories": [
		<c:forEach items="${cartEntry.product.categories}" var="category" varStatus="categoryStatus">
			"<c:out value='${category.name}' />"<c:if test="${not categoryStatus.last}">,</c:if>
		</c:forEach>
		]
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
},
"addToCartLayer":"<spring:escapeBody javaScriptEscape="true">
<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="/cart" var="cartUrl"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>
<div id="addToCartLayer">
<div class="cart_popup_error_msg"><spring:theme code="${errorMsg}" /></div>
<div class="legend"><spring:theme code="basket.added.to.basket" /></div>
<div class="popupCartItem">
	<div class="itemThumb"><product:productPrimaryImage product="${product}" format="cartIcon"/></div>
	<div class="itemDesc">
		<div class="itemName"><c:out value="${product.name}" /></div>
		<div class="itemQuantity"><span class="label"><spring:theme code="popup.cart.quantity.added"/></span>${quantity}</div>

			<c:forEach items="${product.baseOptions}" var="baseOptions">
				<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
					<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
						<div class="itemColor">
							<span class="label"><spring:theme code="product.variants.colour"/></span>
							<img src="${baseOptionQualifier.image.url}"  alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
						</div>
					</c:if>
					<c:if test="${baseOptionQualifier.qualifier eq 'size'}">
						<div class="itemSize">
							<span class="label"><spring:theme code="product.variants.size"/></span>
							${baseOptionQualifier.value}
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>
		
		<div class="itemPrice"><format:price priceData="${entry.basePrice}"/></div>
	</div>
</div>

<script src="https://js.braintreegateway.com/js/braintree-2.18.0.min.js"></script>
<script>
	var clientTokenForPaypal = "${client_token}";
	var singleUse = "${payPalCheckoutData.singleUse}";
	var amount = "${payPalCheckoutData.amount}";
	var locale = "${payPalCheckoutData.locale}";
	var currency = "${payPalCheckoutData.currency}";
	var advancedFraudToolsEnabled = "${payPalCheckoutData.advancedFraudTools}";
	var dbaName = "${payPalCheckoutData.dbaName}";
</script>
<div id="wrapper">
	<div id="row">
	<div id="paypal_express_error"></div>
		<div id="paypayShortcut">
				<img
				src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-medium.png"
				alt="Check out with PayPal" class="payPalCheckoutAddToCartImage" />
				<div>
						<c:choose>
							<c:when test="${payPalCheckoutData.singleUse}">
								<c:set var="checked" scope="session" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="checked" scope="session" value="checked" />
							</c:otherwise>
						</c:choose>
						<input type="checkbox" class="singleUseCheckboxAddToCart"
							id="singleUseCheckboxAddToCart" checked="${checked}" /> <span>
							<spring:theme code="braintree.text.save.funding.source"/></span>
				</div>
		</div>
		<div id="orSeparator"><spring:theme code="braintree.cart.or"/></div>
		<div id="standardCheckout">
			<a href="${cartUrl}" class="button positive buttonMini"><spring:theme
					code="checkout.checkout" /></a>
		</div>
	</div>
</div>

</div>


</spring:escapeBody>"
}
