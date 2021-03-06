<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" tabindex="-1" role="dialog" id="modalConfirmEnablingApartment">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="moderator.modalTitleEnablingApartment" />
                </h4>
            </div>
            <c:url var="urlEnableApartment" value='/moderator/enableApartment' />
            <form:form action="${urlEnableApartment}" id="frmEnableApartment" method="post"
                modelAttribute="actionApartment">
                <div class="modal-body">
                    <form:hidden name="idApartment" id="frmIdApartment" path="apartment.id" />
                    <spring:message code="moderator.reason" />
                    <form:textarea rows="3" cols="" maxlength="150" id="frmComment" name="comment" class="form-control"
                        path="comment" />
                </div>
                <div class="modal-footer">
                    <form:button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="apartmentDetail.dontConfirm" />
                    </form:button>
                    <form:button type="submit" class="btn btn-primary">
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
                <c:if test="${not empty info && info.event eq 'enableApartment'}">
                    <spring:message code="moderator.messageEnableApartment" />
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
                <th width="60"><spring:message code="RENTER" /></th>
                <th width="60"><spring:message code="allApartments.enable" /></th>
                <th width="60"><spring:message code="apartmentDetail.history" /></th>
            </tr>
            <c:forEach items="${listApartments}" var="apartment">
                <tr>
                    <td><a href="<c:url value='/moderator/preview/${apartment.getId()}' />">${apartment.getName()}</a></td>
                    <td><a href="<c:url value='/moderator/userDetails/${apartment.renter.id}' />">
                            <span class="glyphicon glyphicon-user"></span> ${apartment.renter.nickname}</a></td>
                    <td><a href="#" appId="${apartment.id}" class="enableApartment">
                            <span class="glyphicon glyphicon-ok-sign"></span>
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