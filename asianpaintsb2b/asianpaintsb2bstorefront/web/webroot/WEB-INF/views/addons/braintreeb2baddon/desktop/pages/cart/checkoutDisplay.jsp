<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<cart:cartExpressCheckoutEnabled />
<c:choose>
	<c:when test="${payPalExpressEnabled}">

		<script>
			var shoppingCart = "shoppingCart";
			var clientTokenForPaypal = "${client_token}";
			var singleUse = "${payPalCheckoutData.singleUse}";
			var amount = "${payPalCheckoutData.amount}";
			var locale = "${payPalCheckoutData.locale}";
			var currency = "${payPalCheckoutData.currency}";
			var advancedFraudToolsEnabled = "${payPalCheckoutData.advancedFraudTools}";
			var dbaName = "${payPalCheckoutData.dbaName}";
		</script>
		<script src="https://js.braintreegateway.com/js/braintree-2.18.0.min.js"></script>
		<script>
			var contextPath = "${request.contextPath}";
		</script>

		<div class="left">
			<a class="button" href="${continueShoppingUrl}"> <spring:theme
					text="Continue Shopping" code="cart.page.continue" />
			</a>
		</div>
	</c:when>
	<c:otherwise>
		<a class="button" href="${continueShoppingUrl}"> <spring:theme
				text="Continue Shopping" code="cart.page.continue" />
		</a>
		<button id="checkoutButtonBottom" class="doCheckoutBut positive right"
			type="button" data-checkout-url="${checkoutUrl}">
			<spring:theme code="checkout.checkout" />
		</button>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${payPalExpressEnabled}">
		<div class="right cartButtonsSection">
		<div id="paypal_express_error"></div>
			<div class="payPalCheckoutImage right">
				<div class="cmsimage">
					<img
						src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-medium.png"
						alt="Check out with PayPal" />
				</div>
			</div>
			<div>
				<input type="checkbox" class="singleUseCheckbox"
					id="singleUseCheckbox" /> <span> <spring:theme code="braintree.text.save.funding.source"/></span>
			</div>
			<div class="orSeparator"><spring:theme code="braintree.cart.or"/></div>
			<div>
				<button id="checkoutButtonBottom"
					class="doCheckoutBut positive right continueCheckout noMarginTop buttonCustom"
					type="button" data-checkout-url="${checkoutUrl}">
					<spring:theme code="checkout.checkout" />
				</button>
			</div>
		</div>

		<div class="clearfix"></div>
	</c:when>

</c:choose>
<c:if test="${showCheckoutStrategies && not empty cartData.entries}">
	<div class="span-24">
		<div class="right">
			<input type="hidden" name="flow" id="flow" /> <input type="hidden"
				name="pci" id="pci" /> <select id="selectAltCheckoutFlow"
				class="doFlowSelectedChange">
				<option value="multistep"><spring:theme
						code="checkout.checkout.flow.select" /></option>
				<option value="multistep"><spring:theme
						code="checkout.checkout.multi" /></option>
				<option value="multistep-pci"><spring:theme
						code="checkout.checkout.multi.pci" /></option>
			</select> <select id="selectPciOption"
				style="margin-left: 10px; display: none;">
				<option value=""><spring:theme
						code="checkout.checkout.multi.pci.select" /></option>
				<c:if test="${!isOmsEnabled}">
					<option value="hop"><spring:theme
							code="checkout.checkout.multi.pci-hop" /></option>
				</c:if>
				<option value="sop"><spring:theme
						code="checkout.checkout.multi.pci-sop" text="PCI-SOP" /></option>
			</select>
		</div>
	</div>
</c:if>