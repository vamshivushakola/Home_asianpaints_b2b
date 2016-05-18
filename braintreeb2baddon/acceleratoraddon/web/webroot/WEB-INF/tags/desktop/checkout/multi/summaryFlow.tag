<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="deliveryAddress" required="true" type="de.hybris.platform.commercefacades.user.data.AddressData" %>
<%@ attribute name="deliveryMode" required="true" type="de.hybris.platform.commercefacades.order.data.DeliveryModeData" %>
<%@ attribute name="paymentInfo" required="true" type="de.hybris.platform.commercefacades.order.data.CCPaymentInfoData" %>
<%@ attribute name="requestSecurityCode" required="true" type="java.lang.Boolean" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="brainTreePaymentInfo" required="false" type="com.braintree.hybris.data.BrainTreePaymentInfoData" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/desktop/checkout/multi" %>
<%@ taglib prefix="multi-checkout-b2b" tagdir="/WEB-INF/tags/addons/b2bacceleratoraddon/desktop/checkout/multi" %>
<%@ taglib prefix="multi-checkout-paypal" tagdir="/WEB-INF/tags/addons/braintreeb2baddon/desktop/checkout/multi" %>


<div id="checkoutContentPanel" class="clearfix summaryFlow">
	<div class="headline"><spring:theme code="checkout.summary.reviewYourOrder" /></div>
	<multi-checkout-b2b:summaryFlowPaymentType />
	<hr>
	<multi-checkout:summaryFlowDeliveryAddress deliveryAddress="${deliveryAddress}" />
	<hr>
	<multi-checkout:summaryFlowDeliveryMode deliveryMode="${deliveryMode}" cartData="${cartData}" />
	<hr>
	<c:if test="${cartData.paymentType.code eq 'CARD'}">
		<multi-checkout-paypal:summaryFlowPayment paymentInfo="${paymentInfo}" requestSecurityCode="${requestSecurityCode}" brainTreePaymentInfo="${brainTreePaymentInfoData}"/>
	</c:if>
	<c:if test="${cartData.paymentType.code eq 'ACCOUNT'}">
		<multi-checkout-b2b:summaryFlowAccountPayment />
	</c:if>
</div>
