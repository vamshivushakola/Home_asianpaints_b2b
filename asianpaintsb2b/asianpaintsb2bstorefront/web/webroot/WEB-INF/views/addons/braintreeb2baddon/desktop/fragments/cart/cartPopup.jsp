<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<spring:theme code="text.addToCart" var="addToCartText"/>
<spring:theme code="text.popupCartTitle" var="popupCartTitleText"/>
<c:url value="/cart" var="cartUrl"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>


<c:if test="${numberShowing > 0 }">
	<div class="legend">
		<spring:theme code="popup.cart.showing" arguments="${numberShowing},${numberItemsInCart}"/>
		<c:if test="${numberItemsInCart > numberShowing}">
			<a href="${cartUrl}">Show All</a>
		</c:if>
	</div>
</c:if>


<c:if test="${empty numberItemsInCart or numberItemsInCart eq 0}">
	<div class="cart_modal_popup empty-popup-cart">
		<spring:theme code="popup.cart.empty"/>
	</div>
</c:if>
<c:if test="${numberShowing > 0 }">
	<ul class="itemList">
	<c:forEach items="${entries}" var="entry" end="${numberShowing - 1}">
		<c:url value="${entry.product.url}" var="entryProductUrl"/>
		<li class="popupCartItem">
			<div class="itemThumb">
				<a href="${entryProductUrl}">
					<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
				</a>
			</div>
			<div class="itemDesc">
				<a class="itemName" href="${entryProductUrl}">${entry.product.name}</a>
				<div class="itemQuantity"><span class="label"><spring:theme code="popup.cart.quantity"/></span>${entry.quantity}</div>
				
				<c:forEach items="${entry.product.baseOptions}" var="baseOptions">
					<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
						<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
							<div class="itemColor">
								<span class="label"><spring:theme code="product.variants.colour"/></span>
								<img src="${baseOptionQualifier.image.url}" alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
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
				
				<c:if test="${not empty entry.deliveryPointOfService.name}">
					<div class="itemPickup"><span class="itemPickupLabel"><spring:theme code="popup.cart.pickup"/></span>${entry.deliveryPointOfService.name}</div>
				</c:if>
				<div class="itemPrice"><format:price priceData="${entry.basePrice}"/></div>
			</div>
		</li>
	</c:forEach>
	</ul>
</c:if>

<div  class="total">
	<spring:theme code="popup.cart.total"/>&nbsp;<span class="right"><format:price priceData="${cartData.totalPrice}"/></span>
</div>

<div  class="banner">
	<c:if test="${not empty lightboxBannerComponent && lightboxBannerComponent.visible}">
			<cms:component component="${lightboxBannerComponent}" evaluateRestriction="true"  />
	</c:if>
</div>

<script>
	var clientTokenForPaypal = "${client_token}";
	var singleUse = "${payPalCheckoutData.singleUse}";
	var amount = "${payPalCheckoutData.amount}";
	var locale = "${payPalCheckoutData.locale}";
	var currency = "${payPalCheckoutData.currency}";
	var advancedFraudToolsEnabled = "${payPalCheckoutData.advancedFraudTools}";
	var dbaName = "${payPalCheckoutData.dbaName}";
	</script>
<script src="https://js.braintreegateway.com/js/braintree-2.18.0.min.js"></script>
<c:choose>
	<c:when test="${not empty cartData.entries}">
		<div id="wrapper">
			<div id="row">
			<div id="paypal_express_error"></div>
				<div id="paypayShortcut">
					<img
						src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-medium.png"
						alt="Check out with PayPal" class="payPalCheckoutMiniCartImage" />
					</br>
					<div>
						<c:choose>
							<c:when test="${payPalCheckoutData.singleUse}">
								<c:set var="checked" scope="session" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="checked" scope="session" value="checked" />
							</c:otherwise>
						</c:choose>
						<input type="checkbox" class="singleUseCheckboxMiniCart"
							id="singleUseCheckboxMiniCart"
							checked="${checked}" /> <span> <spring:theme code="braintree.text.save.funding.source"/></span>
					</div>
				</div>
				<div id="orSeparator"><spring:theme code="braintree.cart.or"/></div>
				<div id="standardCheckout">
					<a href="${cartUrl}" class="button positive buttonMini"><spring:theme
							code="checkout.checkout" /></a>
				</div>
			</div>
		</div>
	</c:when>

	<c:otherwise>
		<div class="links">
			<a href="${cartUrl}" class="button positive"><spring:theme
					code="checkout.checkout" /></a>
		</div>
	</c:otherwise>
</c:choose>




