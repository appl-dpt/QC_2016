<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:url var="urlPayment" value='/tenant/paymentDetails' />
<form:form action="${urlPayment}" id="frmPayment" method="post" modelAttribute="paymentReservation">
    <form:hidden name="idReserv" id="frmIdReserv" path="id" />
</form:form>




<c:if test="${not empty ordersToPayList}">

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
    <table class="table table-striped orders-to-pay-table">
        <thead>
            <tr class="filters" id="filterPanel">
                <c:url var="filterLink" value="/tenant/ordersToPay/1" />

                <form:form modelAttribute="filterTenant" method="post" id="filterReserved" action="${filterLink}">
                    <spring:message var="name" code="userOrders.name" />
                    <spring:message var="startDate" code="userOrders.startDate" />
                    <spring:message var="endDate" code="userOrders.endDate" />
                    <th><form:input class="form-control filter" placeholder="${name}" path="name" id="nameFilter" /></th>
                    <th><form:input class="form-control filter" placeholder="${startDate}" path="startDate"
                            id="startDateFilter" /></th>
                    <th><form:input class="form-control filter" placeholder="${endDate}" path="endDate"
                            id="endDateFilter" /></th>

                    <th>
                        <button type="submit" class="btn btn-primary" id="SearchBtn">
                            <spring:message code="userOrders.search" />
                        </button>
                    </th>
                </form:form>
            </tr>
        </thead>
        <%-- <tr>
            <th><spring:message code="ordersToPay.apartmentName" /></th>
            <th><spring:message code="ordersToPay.arrivalDate" /></th>
            <th><spring:message code="ordersToPay.departureDate" /></th>
            <th><spring:message code="ordersToPay.paymentApproveLink" /></th>
        </tr> --%>
        <tbody>
        <c:forEach items="${ordersToPayList}" var="item">
            <tr>
                <td>${item.apartment.name}</td>
                <td>${item.dateStartReservation}</td>
                <td>${item.dateEndReservation}</td>
                <td><a href="#" appId="${item.id}" class="btn btn-primary paymentChecking"> <span
                        class="glyphicon glyphicon-usd"></span> <spring:message code="ordersToPay.click" />
                </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
 </div>
</c:if>
<c:if test="${empty ordersToPayList}">
    <h3>
        <spring:message code="ordersToPay.empty" />
        !
    </h3>
</c:if>



<c:url var="paginationLink" value="/tenant/ordersToPay/" />
<form:form id="paginationForm" action="${paginationLink}" modelAttribute="filterTenant" method="post"
    style="display: none">
    <form:hidden path="name" value="" id="pgName" />
    <form:hidden path="startDate" value="" id="pgStartDate" />
    <form:hidden path="endDate" value="" id="pgEndDate" />
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




<script type="text/javascript">
    function payClicked() {
        var $form = $("#frmPayment");
        $("#frmIdReserv").val($(this).attr("appId"));
        $form.submit();
    }
    $(".paymentChecking").click(payClicked);
</script>