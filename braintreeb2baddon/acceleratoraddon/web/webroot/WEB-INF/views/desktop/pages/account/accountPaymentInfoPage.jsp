<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="headline">
	<spring:theme code="text.account.paymentDetails" text="Payment Details"/>
</div>
<div class="description">
	<spring:theme code="text.account.paymentDetails.managePaymentDetails" text="Manage your saved payment details."/>
</div>
<c:choose>
	<c:when test="${not empty braintreePaymentInfos}">
		<c:forEach items="${braintreePaymentInfos}" var="paymentInfo">
			<div class="paymentItem">
				<ul>
					<c:choose>
						<c:when
							test="${paymentInfo.subscriptionId eq 'BrainTreePayPalExpress' or paymentInfo.subscriptionId eq 'PayPalAccount'}">
							<li>PayPal</li>
							<li>${fn:escapeXml(paymentInfo.billingAddress.email)}</li>
							<li><img height="38" width="56"
								src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/pp-acceptance-small.png"
								alt="PayPal icon" style="margin-top: 18px;" /></li>
						</c:when>
						<c:otherwise>
							<li>${fn:escapeXml(paymentInfo.cardNumber)}</li>
							<li>${fn:escapeXml(paymentInfo.cardType)}</li>
							<li><spring:theme code="text.expires" text="Expires" />
							<c:if test="${not empty paymentInfo.expiryMonth}">
								${fn:escapeXml(paymentInfo.expiryMonth)} /
								${fn:escapeXml(paymentInfo.expiryYear)}</li>
							</c:if>
							<c:if test="${not empty paymentInfo.accountHolderName}">
								<li><img
									src="${fn:escapeXml(paymentInfo.accountHolderName)}"
									alt="Card Type" /></li>
							</c:if>
						</c:otherwise>
					</c:choose>
				</ul>
				<ul>
					<li>
						<c:out value="${fn:escapeXml(paymentInfo.billingAddress.title)} ${fn:escapeXml(paymentInfo.billingAddress.firstName)} ${fn:escapeXml(paymentInfo.billingAddress.lastName)}"/>
					</li>
					<li>${fn:escapeXml(paymentInfo.billingAddress.line1)}</li>
					<li>${fn:escapeXml(paymentInfo.billingAddress.line2)}</li>
					<li>${fn:escapeXml(paymentInfo.billingAddress.town)}</li>
					<li>${fn:escapeXml(paymentInfo.billingAddress.postalCode)}</li>
					<li>${fn:escapeXml(paymentInfo.billingAddress.country.name)}</li>
				</ul>
				<div class="buttons">
					<c:if test="${not paymentInfo.defaultPaymentInfo}">
						<c:url value="/my-account/set-default-payment-details" var="setDefaultPaymentActionUrl"/>
						<form:form id="setDefaultPaymentDetails${paymentInfo.id}" action="${setDefaultPaymentActionUrl}" method="post">
							<input type="hidden" name="paymentInfoId" value="${paymentInfo.id}"/>
							<button type="submit" class="submitSetDefault" id="${paymentInfo.id}" href="#">
								<spring:theme code="text.setDefault" text="Set as default"/>
							</button>
						</form:form>
					</c:if>
					<c:url value="/my-account/remove-payment-method" var="removePaymentActionUrl"/>
					<form:form id="removePaymentDetails${paymentInfo.id}" action="${removePaymentActionUrl}" method="post">
						<input type="hidden" name="paymentInfoId" value="${paymentInfo.id}"/>
						<button type="submit" class="submitRemove" id="${paymentInfo.id}" href="#">
							<spring:theme code="text.remove" text="Remove"/>
						</button>
					</form:form>
					<script type="text/javascript">
						var id = '${paymentInfo.id}';
					</script>
					<c:if test="${paymentInfo.defaultPaymentInfo}">
						<spring:theme code="text.default" text="Default"/>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p class="emptyMessage"><spring:theme code="text.account.paymentDetails.noPaymentInformation" text="No Saved Payment Details"/></p>
	</c:otherwise>
</c:choose>