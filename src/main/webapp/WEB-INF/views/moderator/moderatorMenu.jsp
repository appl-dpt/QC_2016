<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list-unstyled menu-list">
    <li><a href="<c:url value='/moderator/enabledApartments' />">
            <spring:message code="allApartments.enabledApartments" />
        </a></li>
    <li><a href="<c:url value='/moderator/disabledApartments' />">
            <spring:message code="allApartments.disabledApartments" />
        </a></li>
    <li><a href="<c:url value='/moderator/freeApartments' />">
            <spring:message code="allApartments.freeApartments" />
        </a></li>
    <li><a href="<c:url value='/moderator/moderatorMap' />">
            <spring:message code="moderator.map" />
        </a></li>
    <li><a href="<c:url value = "/userSettings"/>">
            <spring:message code="userMenu.settings" />
        </a></li>
</ul>