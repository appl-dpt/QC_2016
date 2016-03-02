<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list-unstyled menu-list">
    <li><a href="<c:url value='/renter/enabledApartments' />">
            <spring:message code="allApartments.enabledApartments" />
        </a></li>
    <li><a href="<c:url value='/renter/disabledApartments' />">
            <spring:message code="allApartments.disabledApartments" />
        </a></li>
    <li><a href="<c:url value='/renter/unpublishedApartments' />">
            <spring:message code="allApartments.unpublishedApartments" />
        </a></li>
    <li><a href="<c:url value='/renter/formApartment' />">
            <spring:message code="allApartments.addApartment" />
        </a></li>
    <li><a href="<c:url value = "/userSettings"/>">
            <spring:message code="userMenu.settings" />
        </a></li>
</ul>