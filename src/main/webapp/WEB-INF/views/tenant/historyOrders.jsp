<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel-primary filterable">
    <div class="panel-heading">
        <h3 class="panel-title">
            <spring:message code="userOrders.title" />
        </h3>
        <div class="pull-right">
            <button class="btn btn-default btn-xs btn-filter">
                <span class="glyphicon glyphicon-filter"></span>
                <spring:message code="userOrders.filter" />
            </button>
        </div>
    </div>
    <table class="table">
        <thead>
            <tr class="filters" id="filterPanel">
                <c:url var="filterLink" value="/tenant/historyOrders/1" />

                <form:form modelAttribute="filterTenant" method="post" id="filterReserved" action="${filterLink}">
                    <spring:message var="name" code="userOrders.name" />
                    <spring:message var="startDate" code="userOrders.startDate" />
                    <spring:message var="endDate" code="userOrders.endDate" />
                    <spring:message var="Status" code="userOrders.status" />
                    <th><form:input class="form-control filter" placeholder="${name}" path="name" id="nameFilter" /></th>
                    <th><form:input class="form-control filter" placeholder="${startDate}" path="startDate"
                            id="startDateFilter" /></th>
                    <th><form:input class="form-control filter" placeholder="${endDate}" path="endDate"
                            id="endDateFilter" /></th>

                    <th><input class="form-control filter" placeholder="Status" id="statusLabel" /> <form:select
                            class="form-control filter" path="actionStatus" id="statusFilter">
                    
                            <c:forEach items="${oredrStatusList}" var="ordersStatus">
                                <c:if test="${ordersStatus == filterTenant.actionStatus}">
                                    <option value="${ordersStatus}" selected>
                                        <spring:message code="ActionStatus.${ordersStatus}" />
                                    </option>
                                </c:if>

                                <c:if test="${ordersStatus != filterTenant.actionStatus}">
                                    <option value="${ordersStatus}">
                                        <spring:message code="ActionStatus.${ordersStatus}" />
                                    </option>
                                </c:if>
                            </c:forEach>
                        </form:select></th>
                    <th>
                        <button type="submit" class="btn btn-primary" id="SearchBtn">
                            <spring:message code="userOrders.search" />
                        </button>
                    </th>
                </form:form>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty reservedList}">
                <c:url var="unbookingLink" value="/tenant/unbookApartment" />
                <c:forEach items="${reservedList}" var="reserved">
                    <form:form modelAttribute="deleteReserved" method="post" id="test" action="${unbookingLink}">
                        <tr>
                            <form:hidden path="id" value="${reserved.id}" />
                            <td>${reserved.apartment.name}</td>
                            <td>${reserved.dateStartReservation}</td>
                            <td>${reserved.dateEndReservation}</td>
                            <td><spring:message code="ActionStatus.${reserved.status }" /></td>
                            <td> <a href="<c:url value='/tenant/reservationDetail/${reserved.id}'/>">
                            	<spring:message code="apartmentDetail.Reservation.Detail" />
                            </a></td>
                        </tr>
                    </form:form>
                </c:forEach>
            </c:if>
            <c:if test="${empty reservedList}">
                <tr>
                    <td></td>
                    <td align="center"><spring:message code="userOrders.nullSearchResult" /></td>
                    <td></td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>




<c:url var="paginationLink" value="/tenant/historyOrders/" />
<form:form id="paginationForm" action="${paginationLink}" modelAttribute="filterTenant" method="post"
    style="display: none">
    <form:hidden path="name" value="" id="pgName" />
    <form:hidden path="startDate" value="" id="pgStartDate" />
    <form:hidden path="endDate" value="" id="pgEndDate" />
    <form:hidden path="actionStatus" value="" id="pgStatus" />
</form:form>


<!-- 
   Pagination previous
 -->

<ul class="pagination">

    <c:if test="${filterTenant.startPage > filterTenant.pagesRange}">
        <li><a href="#" btnIndex="${filterTenant.startPage - 1}" class="submitLink">&laquo;</a></li>
    </c:if>


    <c:forEach begin="${filterTenant.startPage}" end="${filterTenant.endPage}" var="i" step="1">
        <c:if test="${i == filterTenant.currentPage}">
            <li class="active"><a href="#" btnIndex="${i}" class="submitLink">${i}</a></li>
        </c:if>
        <c:if test="${i != filterTenant.currentPage}">
            <li><a href="#" btnIndex="${i}" class="submitLink">${i}</a></li>
        </c:if>
    </c:forEach>

    <!-- 
   Pagination next
 -->
    <c:if test="${filterTenant.endPage < filterTenant.pageCount}">
        <li><a href="#" btnIndex="${filterTenant.endPage + 1}" class="submitLink">&raquo;</a></li>
    </c:if>

</ul>