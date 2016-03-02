<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="modal fade" tabindex="-1" role="dialog" id="modalConfirmDeleteReservation">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="apartmentDetail.modalTitleDeleteRegistration" />
                </h4>
            </div>
            <c:url var="urlRemoveReservationByRenter" value='/tenant/unbookApartment' />
            <form:form action="${urlRemoveReservationByRenter}" id="frmDeleteReservation" method="post"
                modelAttribute="canceledReservation">
                <div class="modal-body">
                    <form:hidden name="idReserv" id="frmIdReserv" path="idReservation" />
                    <form:hidden name="type" id="frmType" path="type" />
                    <spring:message code="apartmentDetail.deletingReservationComment" />
                    <form:textarea rows="3" cols="" maxlength="150" id="frmComment" name="comment" class="form-control"
                        path="comment" />
                </div>
                <div class="modal-footer">
                    <form:button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="apartmentDetail.dontConfirm" />
                    </form:button>
                    <form:button type="submit" class="btn btn-primary" id="confirmDeleteRegistration">
                        <spring:message code="apartmentDetail.confirm" />
                    </form:button>
                </div>
            </form:form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="panel-primary filterable table-cst">
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
                <c:url var="filterLink" value="/tenant/activeOrders/1" />

                <form:form modelAttribute="filterTenant" method="post" id="filterReserved" action="${filterLink}">
                    <spring:message var="name" code="userOrders.name" />
                    <spring:message var="startDate" code="userOrders.startDate" />
                    <spring:message var="endDate" code="userOrders.endDate" />
                    <spring:message var="Status" code="userOrders.status" />
                    <th><form:input class="form-control filter" placeholder="${name}" path="name" id="nameFilter" /></th>
                    <th><form:input class="form-control filter" placeholder="${startDate}" path="startDate"
                            id="startDateFilter" /></th>
                    <th><form:input class="form-control filter datepicker" placeholder="${endDate}" path="endDate"
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
                    <tr>
                        <td><a href="<c:url value='/tenant/reservationDetail/${reserved.id}'/>">
                                ${reserved.apartment.name}</a></td>
                        <td>${reserved.dateStartReservation}</td>
                        <td>${reserved.dateEndReservation}</td>
                        <td><spring:message code="ActionStatus.${reserved.status }" /></td>
                        <td><a href="#" appId="${reserved.id}" class="btn btn-warning delReservation">
                                <spring:message code="userOrders.unbook" />
                            </a></td>
                    </tr>
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


<!-- 
   Pagination previous
 -->
<c:url var="paginationLink" value="/tenant/activeOrders/" />
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
        <li><a href="#" btnIndex="${filterTenant.endPage+1}" class="submitLink">&raquo;</a></li>
    </c:if>

</ul>

<script type="text/javascript">
	function delReservationClicked() {
		$("#frmIdReserv").val($(this).attr("appId"));
		$("#frmType").val(0);
		$("#modalConfirmDeleteReservation").modal('show');
	}
	$(".delReservation").click(delReservationClicked);
</script>