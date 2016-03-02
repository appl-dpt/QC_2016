<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<ul class="list-unstyled menu-list">
    <sec:authorize access="hasAuthority('USER')">
        <li><a href="<c:url value = "/tenant/activeOrders"/>">
                <spring:message code="userMenu.ordersActive" />
            </a></li>
        <li><a href="<c:url value = "/tenant/ordersToPay/"/>">
                <spring:message code="userMenu.ordersToPay" />
            </a></li>
        <li><a href="<c:url value = "/tenant/historyOrders"/>">
                <spring:message code="userMenu.ordersInactive" />
            </a></li>
    </sec:authorize>
    <sec:authorize access="hasAuthority('RENTER')">
        <li><a href="<c:url value='/renter/enabledApartments' />">
                <spring:message code="allApartments.enabledApartments" />
            </a></li>
        <li><a href="<c:url value='/renter/disabledApartments' />">
                <spring:message code="allApartments.disabledApartments" />
            </a></li>
        <li><a href="<c:url value='/renter/formApartment' />">
                <spring:message code="allApartments.addApartment" />
            </a></li>
    </sec:authorize>
    <sec:authorize access="hasAuthority('MODERATOR')">
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
    </sec:authorize>
    <sec:authorize access="hasAuthority('ADMIN')">
        <li><a href="<c:url value='/admin/allUsers' />">
                <spring:message code="adminConfiguration.users" />
            </a></li>
        <li><a href="<c:url value='/admin/adminConfiguration' />">
                <spring:message code="adminConfiguration.photo" />
            </a></li>
        <li><a href="<c:url value='/admin/adminExtention' />">
                <spring:message code="adminConfiguration.extention" />
            </a></li>
        <li><a href="<c:url value='/admin/adminConvenience' />">
                <spring:message code="adminConfiguration.convenience" />
            </a></li>
        <li <c:if test="${count > 0}"> class="blink-animation" </c:if>><a href="<c:url value='/admin/adminRenterInfo' />">
            <spring:message code="adminConfiguration.renter" />
            <span class="badge">${count}</span>
        </a></li>
        <li><a href="<c:url value='/admin/backup' />">
                <spring:message code="adminConfiguration.backup" />
            </a></li>
        <li><a href="<c:url value='/admin/adminPayment' />">
                <spring:message code="adminConfiguration.payment" />
            </a></li>
    </sec:authorize>
    <li><a href="<c:url value = "/userSettings"/>">
            <spring:message code="userMenu.settings" />
        </a></li>
</ul>
