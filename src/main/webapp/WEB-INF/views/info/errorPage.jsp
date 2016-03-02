<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<h2>
    <spring:message code="errorPage.title" />
    <a href="<c:url value="/" />">
        <spring:message code="errorPage.mainPage" />
    </a>
    .
</h2>
<h3>
    <spring:message code="errorPage.errorCode" />
    : ${errorCode.value()} ${errorCode}
</h3>
