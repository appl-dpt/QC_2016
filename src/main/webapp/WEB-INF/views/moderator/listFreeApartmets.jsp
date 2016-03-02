<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="modal fade" tabindex="-1" role="dialog" id="modalConfirmAssigningApartment">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="moderator.modalTitleAssigningApartment" />
                </h4>
            </div>
            <div class="modal-body">
                <spring:message code="moderator.modalBodyAssigningApartment" />
            </div>
            <div class="modal-footer">
                <c:url var="urlAssignApartment" value='/moderator/assignApartment/' />
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <spring:message code="apartmentDetail.dontConfirm" />
                </button>
                <a id="frmA" href="${urlAssignApartment}" class="btn btn-primary">
                    <spring:message code="apartmentDetail.confirm" />
                </a>
            </div>
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
                <c:if test="${not empty info && info.event eq 'assignApartment'}">
                    <spring:message code="moderator.messageAssignApartment" />
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

<div>
    <input type="hidden" name="infoEvent" id="infoEvent" <c:if test="${not empty info}"> value="${info.event}" </c:if>>
    <div class="page-header">
        <h3>
            <spring:message code="allApartments.freeApartments" />
        </h3>
    </div>
    <c:if test="${empty listApartments}">
        <spring:message code="allApartments.noApartments" />
    </c:if>
    <c:if test="${!empty listApartments}">
        <table class="table table-striped status-apartments-table">
            <tr>
                <th width="120"><spring:message code="apartment.name" /></th>
                <th width="60"><spring:message code="RENTER" /></th>
                <th width="60"><spring:message code="allApartments.assign" /></th>
                <th width="60"><spring:message code="apartmentDetail.history" /></th>
            </tr>
            <c:forEach items="${listApartments}" var="apartment">
                <tr>
                    <td><a href="<c:url value='/moderator/preview/${apartment.getId()}' />">${apartment.getName()}</a></td>
                    <td><a href="<c:url value='/moderator/userDetails/${apartment.renter.id}' />">
                             <span class="glyphicon glyphicon-user"></span> ${apartment.renter.nickname}</a></td>
                    <td><a href="#" appId="${apartment.id}" class="assignApartment">
                            <span class="glyphicon glyphicon-plus-sign"></span>
                        </a></td>
                    <td><a href="<c:url value='/moderator/apartmentHistory/${apartment.getId()}' />">
                            <span class="glyphicon glyphicon-time"></span>
                        </a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <hr>
</div>