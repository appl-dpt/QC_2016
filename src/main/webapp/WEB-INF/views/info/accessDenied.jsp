<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<h1>
    <spring:message code="accessDenied.title" />
    <a href="<c:url value='/' />">
        <spring:message code="accessDenied.mainPage" />
    </a>
    .
</h1>