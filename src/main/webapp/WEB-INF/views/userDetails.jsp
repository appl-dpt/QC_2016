<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
    <h1>
        <spring:message code="userDetails.InformationAboutUser" />
    </h1>

    <form:form modelAttribute="user" class="form-horizontal userImageDetails col-xs-3">
        <div class="form-inline">
            <form:input type="hidden" path="id" id="id" />
            <div class="col-xs-2 control-group">
                <div class="form-group">
                    <form:input class="form-control" path="imageLink" type="image" id="inputImage"
                        style="height: 250px; width: 250px; margin-left: 20px;" disabled="true"
                        src="${ user.getImageLink() }" />
                </div>
            </div>
        </div>
    </form:form>

    <form:form modelAttribute="user" class="form-horizontal col-xs-8 userDetails">
        <div class="form-horizontal">
            <form:input type="hidden" path="id" id="id" />
            <div class="form-group">
                <form:label path="firstname" for="inputFirstName" class="col-sm-5 control-label">
                    <spring:message code="userSettings.firstname" />
                </form:label>
                <div class="col-xs-4 control-group">
                    <form:input path="firstname" id="inputFirstName" class="form-control" readonly="true" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="lastname" for="inputLastName" class="col-sm-5 control-label">
                    <spring:message code="userSettings.lastname" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="lastname" id="inputLastName" class="form-control" readonly="true" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="nickname" for="inputNickname" class="col-sm-5 control-label">
                    <spring:message code="userSettings.nickname" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="nickname" id="inputNickname" class="form-control" readonly="true" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="email" for="inputEmail" id="labelEmail" class="col-sm-5 control-label">
                    <spring:message code="userSettings.email" />
                </form:label>
                <div class="col-xs-4">
                    <form:input path="email" id="inputEmail" class="form-control" readonly="true" />
                </div>
            </div>
            <c:if test="${user.visibility == true}">
                <div class="form-group">
                    <form:label path="phonenumber" for="inputPhonenumber" id="labelPhonenumber"
                        class="col-sm-5 control-label" style="margin-right: 15px;">
                        <spring:message code="userSettings.phonenumber" />
                    </form:label>
                    <div class="col-xs-4 input-group">
                        <div class="input-group-addon">+38</div>
                        <form:input path="phonenumber" id="inputPhonenumber" class="form-control" style="width: 130px;"
                            readonly="true" />
                    </div>
                </div>
            </c:if>
        </div>
    </form:form>
</div>