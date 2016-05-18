<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="brainTreePaymentInfo" required="false"
	type="com.braintree.hybris.data.BrainTreePaymentInfoData"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="headline">
	<spring:theme code="text.paymentDetails" text="Payment Details" />
</div>
<ul>
	<c:choose>
		<c:when test="${brainTreePaymentInfo.paymentType eq 'BrainTreePayPalExpress' or brainTreePaymentInfo.paymentType eq 'PayPalAccount'}">
			<li><img
				src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/pp-acceptance-small.png"
				alt="PayPal icon" /></li>
			<li class="longWordBreak">${fn:escapeXml(brainTreePaymentInfo.email)}
			</li>
		</c:when>
		<c:otherwise>
			<li>${fn:escapeXml(brainTreePaymentInfo.cardType)}</li>
			<li>${fn:escapeXml(brainTreePaymentInfo.cardNumber)}</li>
		</c:otherwise>
	</c:choose>
</ul>