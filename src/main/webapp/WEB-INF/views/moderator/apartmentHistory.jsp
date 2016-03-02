<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-header">
    <h3>
        <spring:message code="apartmentHistory.info" />
        <a href="<c:url value='/moderator/preview/${apartment.getId()}'/>">
            <strong id="apartmentName">${apartment.name}</strong>
        </a>
    </h3>
    <table>
        <tr>
            <td width="150"><spring:message code="apartmentHistory.created" /></td>
            <td>
                ${apartment.getFirstAction().getDateTimeActionS()}
            </td>
        </tr>
        <tr>
            <td width="150"><spring:message code="MODERATOR" /></td>
            <td>
	            <c:if test="${apartment.moderator != null}">
	             	${apartment.moderator.nickname} 
	            </c:if>
	            <c:if test="${apartment.moderator == null}">
	             	<spring:message code="apartmentDetail.noModerator"/> 
	            </c:if>
            </td>
        </tr>
    </table>
</div>
<div id="visitor">
    <h4>
        <spring:message code="RENTER" />
    </h4>
    <a href="<c:url value='/moderator/userDetails/${apartment.renter.id }' />">
        <img src="${apartment.renter.imageLink}" alt="${apartment.renter.nickname}" class="img-thumbnail" id="avatar">
        ${apartment.renter.nickname}
    </a>
</div>
<div id="actions">
    <h4>
        <spring:message code="apartmentHistory.events" />
    </h4>
    <table class="table table-striped">
        <tr>
            <th width="100"><spring:message code="reservationDetail.time" /></th>
            <th width="200"><spring:message code="reservationDetail.event" /></th>
            <th width="200"><spring:message code="MODERATOR" /></th>
            <th><spring:message code="reservationDetail.comment" /></th>
        </tr>
        <c:forEach items="${apartment.apartmentActions}" var="action">
            <tr height="80">
                <td>${action.getDateTimeActionS()}</td>
                <td><spring:message code="ApartmentStatus.${action.status}" /></td>
                <td><a href="<c:url value='/moderator/userDetails/${action.moderator.id}'/>">
                	${action.moderator.nickname}</a></td>
                <td>${action.comment}</td>
            </tr>
        </c:forEach>
    </table>
</div>