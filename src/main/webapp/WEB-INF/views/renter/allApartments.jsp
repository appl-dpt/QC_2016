<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div>
    <div class="page-header">
        <h3>
            <spring:message code="allApartments.apartmentsList" />
        </h3>
    </div>
    <c:if test="${empty listApartments}">
        <spring:message code="allApartments.noApartments" />
    </c:if>
    <c:if test="${!empty listApartments}">
        <table class="table table-striped">
            <tr>
                <th width="120"><spring:message code="apartment.name" /></th>
                <th width="60"><spring:message code="allApartments.detail" /></th>
                <th width="60"><spring:message code="allApartments.disable" /></th>
            </tr>
            <c:forEach items="${listApartments}" var="apartment">
                <tr>
                    <td><a href="<c:url value='/renter/preview/${apartment.getId()}' />">${apartment.getName()}</a></td>
                    <td><a href="<c:url value='/renter/apartmentDetail/${apartment.getId()}' />">
                            <spring:message code="allApartments.detail" />
                        </a></td>
                    <td><a href="<c:url value='/renter/disableApartment/${apartment.getId()}' />">
                            <spring:message code="allApartments.disable" />
                        </a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <hr>
</div>
