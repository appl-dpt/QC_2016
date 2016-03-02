<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list-unstyled menu-list">
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
</ul>
