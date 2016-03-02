<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- dialogs -->
<div class="modal fade" tabindex="-1" role="dialog" id="modalBoxInfo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <c:if test="${not empty info && info.description == 'unpublish'}">
                        <spring:message code="apartmentDetail.messageTitleNoPublish" />
                    </c:if>
                    <c:if test="${empty info || info.description eq ''}">
                        <spring:message code="apartmentDetail.modalTitle" />
                    </c:if>
                </h4>
            </div>
            <div class="modal-body">
                <c:if test="${not empty info && info.event eq 'upload'}">
                    <spring:message code="apartmentDetail.messagePhotoUpload" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'notUpload'}">
                    <spring:message code="apartmentDetail.messagePhotoNotUpload" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'editCoord'}">
                    <spring:message code="apartmentDetail.messageCoord" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'deletePhoto'}">
                    <spring:message code="apartmentDetail.messageDeletePhoto" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'notDeletePhoto'}">
                    <spring:message code="apartmentDetail.messageNotDeletePhoto" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'updateApartment'}">
                    <spring:message code="apartmentDetail.messageUpdateApartment" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'editConveniences'}">
                    <spring:message code="apartmentDetail.messageUpdateConveniences" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'publish'}">
                    <spring:message code="apartmentDetail.messagePublish" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'noPhoto'}">
                    <spring:message code="apartmentDetail.messageNoPhoto" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'noLocation'}">
                    <spring:message code="apartmentDetail.messageNoLocation" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'noPhotoLocation'}">
                    <spring:message code="apartmentDetail.messageNoPhoto" />
                    <br>
                    <spring:message code="apartmentDetail.messageNoLocation" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'editPaymentSettings'}">
                    <spring:message code="apartmentDetail.messageEditPaymentSettings" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'notEditPaymentSettings'}">
                    <spring:message code="apartmentDetail.messageNotEditPaymentSettings" />
                </c:if>
                <c:if test="${not empty info && info.event eq 'badCoord'}">
                    <spring:message code="apartmentDetail.messageBadCoord" />
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
<div class="modal fade" tabindex="-1" role="dialog" id="modalConfirmDeletePhoto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="apartmentDetail.modalTitleDelete" />
                </h4>
            </div>
            <div class="modal-body">
                <p>
                    <spring:message code="apartmentDetail.confirmDelete" />
                </p>
            </div>
            <div class="modal-footer">
                <form action="<c:url value='/renter/removePhoto' />" method="get">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="cancel" />
                    </button>
                    <input type="hidden" name="idPhoto" id="frmIdPhoto">
                    <button type="submit" class="btn btn-primary" id="confirmDeletePhoto">
                        <spring:message code="delete" />
                    </button>
                </form>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="modalImage">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="titleBigImage"></h4>
            </div>
            <div class="modal-body">
                <img src="" id="imagepreview" style="width: 100%;">
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- document -->
<div class="">
    <c:if test="${not empty info}">
        <input type="hidden" name="infoTab" id="infoTab" value="${info.tab}">
        <input type="hidden" name="infoEvent" id="infoEvent" value="${info.event}">
    </c:if>
    <c:if test="${empty info}">
        <input type="hidden" name="infoTab" id="infoTab" value="">
        <input type="hidden" name="infoEvent" id="infoEvent" value="">
    </c:if>
    <div class="page-header">
        <h3>
            <spring:message code="apartment" />
            <strong id="apartmentName">${apartment.name}</strong>
            <c:if test="${!apartment.published}">
                <a href="<c:url value='/renter/publishApartment/${apartment.id}/2'/>" class="btn btn-warning btn-right">
                    <spring:message code="allApartments.publish" />
                </a>
            </c:if>
        </h3>
    </div>
    <div id="tabs">
        <ul id="apartments-detail-tabs" class="nav nav-tabs" role="tablist">
            <li class="active" id="liApartmentInfo"><a href="#apartmentInfo" aria-controls="apartmentInfo"
                    role="tab" data-toggle="tab" id="tabApartmentInfo">
                    <spring:message code="info" />
                </a></li>
            <li id="liPhotos"><a href="#photos" aria-controls="photos" role="tab" data-toggle="tab" id="tabPhotos">
                    <spring:message code="apartmentDetail.photos" />
                </a></li>
            <li id="liMap"><a href="#map" aria-controls="map" role="tab" data-toggle="tab" id="tabMap">
                    <spring:message code="apartmentDetail.map" />
                </a></li>
            <li id="liConveniences"><a href="#conveniences" aria-controls="conveniences" role="tab"
                    data-toggle="tab" id="tabConveniences">
                    <spring:message code="apartmentDetail.conveniences" />
                </a></li>
            <li id="liPaymentSettings"><a href="#paymentSettings" aria-controls="paymentSettings"
                    role="tab" data-toggle="tab" id="tabPaymentSettings">
                    <spring:message code="apartmentDetail.paymentSettings" />
                </a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane fade active in" id="apartmentInfo">
                <c:url var="urlUpdateApartment" value="/renter/updateApartment" />
                <form:form action="${urlUpdateApartment}" modelAttribute="apartment" method="post" class="frmApInfo">
                    <form:hidden path="id" id="apartmentID" />
                    <form:hidden path="renter.id" />
                    <form:label path="name" for="inputName">
                        <spring:message code="apartment.name" />
                    </form:label>
                    <div class="divInputContainer">
                        <form:input path="name" id="inputName" class="form-control" />
                    </div>
                    <form:errors path="name" cssClass="error" />
                    <br>
                    <form:label path="city" for="inputCity">
                        <spring:message code="apartment.city" />
                    </form:label>
                    <div class="divInputContainer">
                        <form:input path="city" id="inputCity" class="form-control" />
                    </div>
                    <form:errors path="city" cssClass="error" />
                    <form:hidden path="" name="selected" value="1" id="selected" />
                    <br>
                    <form:label path="address" for="inputAddress">
                        <spring:message code="apartment.address" />
                    </form:label>
                    <div class="divInputContainer">
                        <form:input path="address" id="inputAddress" class="form-control" />
                    </div>
                    <form:errors path="address" cssClass="error" />
                    <br>
                    <form:label path="description" for="inputDescription">
                        <spring:message code="apartment.description" />
                    </form:label>
                    <form:textarea rows="10" path="description" id="inputDescription" class="form-control" />
                    <br>
                    <form:button type="submit" class="btn btn-primary" name="edit">
                        <spring:message code="apartmentDetail.edit" />
                    </form:button>
                    <form:button type="reset" class="btn btn-primary" disabled="true" id="idReset">
                        <spring:message code="apartmentDetail.cancelChanges" />
                    </form:button>
                </form:form>
            </div>
            <div class="tab-pane fade row" id="photos">
                <c:if test="${apartment.links.isEmpty()==true}">
                    <spring:message code="apartmentDetail.noPhotos" />
                </c:if>
                <table class="table">
                    <c:forEach items="${apartment.links}" var="photo">
                        <tr>
                            <td>${photo.name}</td>
                            <td width="10"><a href="<c:url value='/renter/upPhoto/${photo.getId()}' />">
                                    <img src="<c:url value="/img/Arrow-Up.png"/>" width="20" height="20"
                                        title="<spring:message code='apartmentDetail.upPhoto'/>">
                                </a> <a href="<c:url value='/renter/downPhoto/${photo.getId()}' />">
                                    <img src="<c:url value="/img/Arrow-Down.png"/>" width="20" height="20"
                                        title="<spring:message code='apartmentDetail.downPhoto'/>">
                                </a></td>
                            <td><a class="bigImage" appId="${photo.id}" href="#" title="${photo.name}">
                                    <img id="${photo.id}" src="<c:url value='/Apartments/photo/${photo.id}' />"
                                        height="50" width="50" />
                                </a></td>
                            <td><a href="#" appId="${photo.getId()}" class="delPhoto btn btn-danger">
                                    <spring:message code="delete" />
                                </a></td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="col-md-8 col-lg-8 col-sm-8">
                    <h3>
                        <spring:message code="apartmentDetail.addingPhotos" />
                    </h3>
                    <c:url var="urlUploadPhoto" value="/renter/uploadPhoto?idApartment=${apartment.id}" />
                    <form:form action="${urlUploadPhoto}" method="post" enctype="multipart/form-data"
                        modelAttribute="photo" class="uploadPhoto">
                        <table class="table table-bordered">
                            <tr>
                                <td><spring:message code="name" /></td>
                                <td>
                                    <div class="divInputContainer">
                                        <form:input path="name" id="newPhotoName" />
                                        <form:errors path="name" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><spring:message text="URL" /></td>
                                <td>
                                    <div class="divInputContainer file_upload">
                                        <button type="button" class="btn btn-info">
                                            <spring:message code="apartmentDetail.chooseFile" />
                                        </button>
                                        <div>
                                            <spring:message code="apartmentDetail.fileNotChosen" />
                                        </div>
                                        <form:input type="file" name="file" path="" id="newPhotoUrl" title="  " />
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <form:button type="submit" class="btn btn-primary" id="btnok ">
                            <spring:message code="apartmentDetail.addPhoto" />
                        </form:button>
                    </form:form>
                </div>
            </div>
            <div class="tab-pane fade" id="map">
                <div id="map_container"></div>
                <div>
                    <c:url var="urlSetCoordinates" value="/renter/setCoordinates" />
                    <form:form action="${urlSetCoordinates}" method="post" modelAttribute="apartment">
                        <form:hidden path="id" />
                        <form:hidden path="latitude" id="latitude" />
                        <form:hidden path="longitude" id="longitude" />
                        <form:hidden path="" name="position" id="position" value="${position}" />
                        <c:if test="${position == 0}">
                            <div id="infoSavingCoord">
                                <spring:message code="apartmentDetail.savingInfo" />
                            </div>
                        </c:if>
                        <form:button type="submit" class="btn btn-primary" id="btnSaveMap">
                            <spring:message code="apartmentDetail.saveCoordinates" />
                        </form:button>
                    </form:form>
                </div>
            </div>
            <div class="tab-pane fade" id="conveniences">
                <br>
                <c:url var="urlUpdateConveniences" value="/renter/updateConveniences" />
                <form:form action="${urlUpdateConveniences}" method="post" modelAttribute="apconveniences">
                    <form:hidden path="id" />
                    <form:label path="maxCountGuests">
                        <spring:message code="apartmentDetail.maxCountGuests" />
                    </form:label>
                    <div class="input-group spinner">
                        <form:input path="maxCountGuests" class="form-control" value="${apartment.maxCountGuests}"
                            readonly="true" />
                        <div class="input-group-btn-vertical">
                            <button class="btn btn-default" type="button">
                                <i class="glyphicon glyphicon-chevron-up"></i>
                            </button>
                            <button class="btn btn-default" type="button">
                                <i class="glyphicon glyphicon-chevron-down"></i>
                            </button>
                        </div>
                    </div>
                    <br>
                    <div class="conveniences">
                        <form:label path="conveniences">
                            <spring:message code="apartmentDetail.conveniencesList" />
                        </form:label>
                        <c:forEach items="${apartment.apartmentConveniences}" var="convenience">
                            <div class="checkbox">
                                <label>
                                    <form:checkbox path="conveniences" value="${convenience.convenience.name}"
                                        cssClass="${ convenience.exists eq true ? 'conviniance-checked' : 'conviniance-unchecked' }" />
                                    ${convenience.convenience.name}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <form:button type="submit" class="btn btn-primary" name="edit">
                        <spring:message code="apartmentDetail.edit" />
                    </form:button>
                    <form:button type="reset" class="btn btn-primary" id="idReset">
                        <spring:message code="apartmentDetail.cancelChanges" />
                    </form:button>
                </form:form>
            </div>
            <div class="tab-pane fade" id="paymentSettings">
                <br>
                <c:url var="urlUpdatePaymentSettings" value="/renter/updatePaymentSettings" />
                <form:form action="${urlUpdatePaymentSettings}" method="post" 
                    modelAttribute="apartmentPayments" class="updatePaymentSettings">  
                    <div id="paymentMain">
                    <form:hidden path="id" />
                    <form:label path="price" for="inputPrice">
                        <spring:message code="apartmentDetail.price" />
                    </form:label>
                    <fmt:formatNumber var="priceFormat" type="number" 
                        minFractionDigits="2" maxFractionDigits="2" value="${apartmentPayments.price}"/>
                    <div class="divInputContainer">
                        <form:input path="price" id="inputPrice" class="form-control"
                        	value="${priceFormat}"/>
                    </div>
                    <form:errors path="price" cssClass="error" />
                    </div>
                    <br>
                    <div id="paymentList" class="divInputContainer">
                        <form:label path="payments">
                            <spring:message code="apartmentDetail.paymentsList" />
                        </form:label>
                        <c:forEach items="${apartment.apartmentPayments}" var="payment">
                            <c:if test="${payment.paymentMethod.enabled == true}">
                            <div class="checkbox">
                                <label>
                                    <form:checkbox path="payments" value="${payment.paymentMethod.name}"
                                        cssClass="${ payment.exists eq true ? 'payment-checked' : 'payment-unchecked' }"/>
                                    ${payment.paymentMethod.name}
                                </label>
                            </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <form:errors path="payments" cssClass="error"/>
                    <br>
                    <div id="paymentButtons">
                    <form:button type="submit" class="btn btn-primary" name="edit">
                        <spring:message code="apartmentDetail.edit" />
                    </form:button>
                    <form:button type="reset" class="btn btn-primary" id="idReset">
                        <spring:message code="apartmentDetail.cancelChanges" />
                    </form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <!-- tabs -->
</div>
