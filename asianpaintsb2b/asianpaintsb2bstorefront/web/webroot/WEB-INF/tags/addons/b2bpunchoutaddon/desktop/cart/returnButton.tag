<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<%@ attribute name="url" required="true" type="java.lang.String"%>

<spring:theme code="punchout.return" var="returnText"/>
<a class="button positive right" href="${url}"><spring:theme text="${returnText}" code="${returnText}"/></a>