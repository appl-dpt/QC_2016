<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div>
    <div class="page-header">
        <h3>
            <spring:message code="allApartments.disabledApartments" />
        </h3>
    </div>
    <c:if test="${empty listApartments}">
        <spring:message code="allApartments.noApartments" />
    </c:if>
    <c:if test="${!empty listApartments}">
        <table class="table table-striped status-apartments-table">
            <tr>
                <th width="120"><spring:message code="apartment.name" /></th>
                <th width="60"><spring:message code="allApartments.banner" /></th>
                <th width="60"><spring:message code="moderator.reason" /></th>
                <th width="60"><spring:message code="apartmentDetail.history" /></th>
            </tr>
            <c:forEach items="${listApartments}" var="apartment">
                <tr>
                    <td>${apartment.getName()}</td>
                    <td><a href="<c:url value='/renter/userDetails/${apartment.moderator.id}/${apartment.id}'/>">
                            ${apartment.moderator.nickname } </a></td>
                    <td>${apartment.getLastAction().comment}</td>
                    <td><a href="<c:url value='/renter/apartmentHistory/${apartment.id}'/>">
                    	<span class="glyphicon glyphicon-time"></span>
                    </a> </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <hr>
</div>