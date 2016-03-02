<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list-unstyled menu-list">
    <li><a href="<c:url value='/renter/enabledApartments' />">
            <spring:message code="apartmentDetail.backToAll" />
        </a></li>
    <c:if test="${apartment.status eq 'ENABLED'}">
    <li><a href="<c:url value='/renter/apartmentDetail/${apartment.getId()}'/>">
            <spring:message code="apartmentDetail.apartmentSettings" />
        </a></li>
    <li><a href="<c:url value='/renter/preview/${apartment.getId()}'/>">
            <spring:message code="preview" />
        </a></li>
    <li><a href="<c:url value='/renter/needConfirmReservations/${apartment.getId()}'/>">
            <spring:message code="apartmentDetail.needConfirmReservation" />
        </a></li>
    <li><a href="<c:url value='/renter/futureReservations/${apartment.getId()}'/>">
            <spring:message code="apartmentDetail.futureReservation" />
        </a></li>
    <li><a href="<c:url value='/renter/currentReservations/${apartment.getId()}' />">
            <spring:message code="apartmentDetail.currentReservation" />
        </a></li>
    <li><a href="<c:url value='/renter/historyReservations/${apartment.getId()}'/>">
            <spring:message code="apartmentDetail.history" />
        </a></li>
    </c:if>
</ul>