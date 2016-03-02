<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="page-header">
    <h3>
        <spring:message code="reservationDetail.info" />
        <strong id="apartmentName">${reservation.apartment.name}</strong>
    </h3>
    <table>
        <tr>
            <td width="150"><spring:message code="reservationDetail.startReservation" /></td>
            <td>${reservation.dateStartReservation}</td>
        </tr>
        <tr>
            <td><spring:message code="reservationDetail.endReservation" /></td>
            <td>${reservation.dateEndReservation}</td>
        </tr>
    </table>
    <a href="<c:url value='/renter/backToHistory/${reservation.id}'/>">
        <spring:message code="reservationDetail.backToHistory" />
    </a>
</div>
<div id="visitor">
    <h4>
        <spring:message code="reservationDetail.tenant" />
    </h4>
    <a href="<c:url value='/renter/userDetails/${reservation.tenant.id }/${apartment.id}' />">
        <img src="${reservation.tenant.imageLink}" alt="${reservation.tenant.nickname}" class="img-thumbnail"
            id="avatar"> ${reservation.tenant.nickname}
    </a>
</div>
<div id="actions">
    <h4>
        <spring:message code="reservationDetail.events" />
    </h4>
    <table class="table table-striped">
        <tr>
            <th width="100"><spring:message code="reservationDetail.time" /></th>
            <th width="200"><spring:message code="reservationDetail.event" /></th>
            <th><spring:message code="reservationDetail.comment" /></th>
        </tr>
        <c:forEach items="${reservation.actions}" var="action">
            <tr height="80">
                <td>${action.getDateTimeActionS()}</td>
                <td><spring:message code="ActionStatus.${action.status}" /></td>
                <td>${action.comment}</td>
            </tr>
        </c:forEach>
    </table>
</div>