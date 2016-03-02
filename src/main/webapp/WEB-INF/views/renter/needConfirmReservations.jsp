<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
            <c:url var="urlRemoveReservationByRenter" value='/renter/removeReservationByRenter' />
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
<div class="modal fade" tabindex="-1" role="dialog" id="modalBoxInfo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="apartmentDetail.modalTitle" />
                </h4>
            </div>
            <div class="modal-body">
                <c:if test="${not empty info && info.event == 'deleteReservation'}">
                    <spring:message code="apartmentDetail.messageDeleteReservation" />
                </c:if>
                <c:if test="${not empty info && info.event == 'confirmReservation'}">
                    <spring:message code="apartmentDetail.messageConfirmReservation" />
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <spring:message code="close" />
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div id="futureReservations">
    <c:if test="${not empty info}">
        <input type="hidden" name="infoEvent" id="infoEvent" value="${info.event}">
    </c:if>
    <c:if test="${empty info}">
        <input type="hidden" name="infoEvent" id="infoEvent" value="">
    </c:if>
    <div class="page-header">
        <h3>
            <spring:message code="apartmentDetail.needConfirmReservation" />
            <strong id="apartmentName">${apartment.name}</strong>
        </h3>
    </div>
    <c:if test="${empty needConfirmReserved}">
        <spring:message code="apartmentDetail.noReserved" />
    </c:if>
    <c:if test="${not empty needConfirmReserved}">
        <table class="table">
            <tr>
                <th><spring:message code="apartmentDetail.Reservation.Start" /></th>
                <th><spring:message code="apartmentDetail.Reservation.End" /></th>
                <th><spring:message code="apartmentDetail.Reservation.User" /></th>
                <th><spring:message code="confirm" /></th>
                <th><spring:message code="cancel" /></th>
            </tr>
            <c:forEach items="${needConfirmReserved}" var="reserv">
                <tr>
                    <td>${reserv.dateStartReservation}</td>
                    <td>${reserv.dateEndReservation}</td>
                    <td><a href="<c:url value='/renter/userDeatil/${reserv.tenant.id}/${apartment.id}'/>">
                            ${reserv.tenant.nickname} </a></td>
                    <td><a href="<c:url value='/renter/confirmReservation/${reserv.id}'/>"
                            class="btn btn-primary confirmReservation">
                            <spring:message code="confirm" />
                        </a></td>
                    <td><a href="#" appId="${reserv.id}" class="btn btn-warning badDealReservation">
                            <spring:message code="cancel" />
                        </a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>