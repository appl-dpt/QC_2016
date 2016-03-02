<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <c:if test="${success eq 'registration'}">
        <spring:message code="success.registration" />
    </c:if>
    <br /> <br />
    <spring:message code="success.goBack" />
    <a href="<c:url value='/' />">
        <spring:message code="success.mainPage" />
        .
    </a>
</div>
