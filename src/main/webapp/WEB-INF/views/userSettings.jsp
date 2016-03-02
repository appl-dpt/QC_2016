<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal fade" id="imageModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="userSettings.selectPhoto" />
                </h4>
            </div>
            <div class="modal-body text-center" id="avatarModalBody">
                <img src="" id="avatarImage" width="400" height="400">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelImage();">
                    <spring:message code="cancel" />
                </button>
                <button type="button" class="btn btn-primary" id="imageSelectBtn">
                    <spring:message code="confirm" />
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <h1>
        <spring:message code="userSettings.settings" />
    </h1>
    <c:url var="urlUploadPhoto" value="/userSettings/resetPhoto" />
    <form:form action="${urlUploadPhoto}" method="POST" modelAttribute="user"
        class="form-horizontal userImageSettings col-xs-3" enctype="multipart/form-data">
        <div class="form-inline">
            <form:input type="hidden" path="id" id="id" />
            <div class="col-xs-2 control-group">
                <input type="hidden" id="selectedImage" value="" name="selectedImage">
                <div class="hidden">
                    <input type="file" id="imageFile" accept="image/*" name="imageFile">
                    <canvas id="imageCanvas"></canvas>
                    <canvas id="bufferedCanvas" width="400" height="400"></canvas>
                </div>
                <div class="form-group">
                    <form:input class="form-control" path="imageLink" type="image" id="inputImage"
                        style="height: 200px; width: 200px; margin-left: 20px;" src="${ user.getImageLink() }" />
                    <div class="form-group">
                        <input type="button" id="showImagePopupBtn" class="btn btn-default"
                            value="<spring:message code = "userSettings.selectPhoto"/>"
                            style="margin-left: 35px; width: 200px;">
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnok" style="margin-left: 60px; margin-top: 20px; width: 125px;">
                    	<spring:message code="userSettings.resetPhoto" />
                	</button>
                 </div>
            </div>
        </div>
    </form:form>

    <c:url var="urlUpdateUser" value="/userSettings/updateUser" />
    <form:form action="${urlUpdateUser}" modelAttribute="user" method="POST"
        class="form-horizontal col-xs-8 userSettings" onsubmit="return validate();">
        <div class="form-horizontal">
            <form:input type="hidden" path="id" id="id" />
            <form:hidden path="dateRegistration" class="form-control" />
            <form:hidden path="enabled" class="form-control" />
            <form:hidden path="imageLink" class="form-control" id="inputImageSave" value="${ user.getImageLink() }"/>
            <form:hidden path="password" class="form-control" />
            <form:hidden path="role" class="form-control" />
            <div class="form-group">
                <form:label path="firstname" for="inputFirstName" class="col-sm-3 control-label">
                    <spring:message code="userSettings.firstname" />
                </form:label>
                <div class="col-xs-4 control-group">
                    <form:input path="firstname" id="inputFirstName" class="form-control" maxlength="20" />
                </div>
                <form:errors path="firstname" cssClass="error" />
            </div>
            <div class="form-group">
                <form:label path="lastname" for="inputLastName" class="col-sm-3 control-label">
                    <spring:message code="userSettings.lastname" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="lastname" id="inputLastName" class="form-control" maxlength="20" />
                </div>
                <form:errors path="lastname" cssClass="error" />
            </div>
            <div class="form-group">
                <form:label path="nickname" for="inputNickname" class="col-sm-3 control-label">
                    <spring:message code="userSettings.nickname" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="nickname" id="inputNickname" class="form-control" maxlength="20" />
                </div>
                <form:errors path="nickname" cssClass="error" />
            </div>

            <div class="form-group">
                <form:label path="email" for="inputEmail" id="labelEmail" class="col-sm-3 control-label">
                    <spring:message code="userSettings.email" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="email" id="inputEmail" class="form-control" readonly="true" />
                </div>
                <form:errors path="email" cssClass="error" />
            </div>
            <div class="form-group">            
                <form:label path="phonenumber" for="inputPhonenumber" id="labelPhonenumber"
                    class="col-sm-3 control-label">
                    <spring:message code="userSettings.phonenumber" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="phonenumber" id="inputPhonenumber" class="form-control" maxlength="10"
                        onclick="emptyPhoneNumber();"
                        onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
                </div>
                <form:errors path="phonenumber" cssClass="error" />
            </div>
            <button type="submit" class="btn btn-primary" name="save" style="margin-left: 165px; width: 125px;" >
                <spring:message code="userSettings.save" />
            </button>         
        </div>
    </form:form>
   </div>
<script>
 
 $(document).ready(function(){
    var messagesL = getMessages([ "userSettings.tooltip"]);
      $(".col-xs-5").tooltip({placement: 'top', title: messagesL[ "userSettings.tooltip" ]})

    
      $(function () { $("[data-toggle = 'tooltip']").tooltip(); });
})
</script>


