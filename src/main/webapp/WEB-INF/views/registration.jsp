<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">
                    <spring:message code="registration.userRegistration" />
                </h4>
            </div>
            <div class="modal-body">
                <p>
                    <spring:message code="registration.passwordMismatch" />
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    <spring:message code="registration.close" />
                </button>
            </div>
        </div>
    </div>
</div>
<div>
    <h2>
        <spring:message code="registration.userRegistrationForm" />
    </h2>
    <%-- <form class="form-horizontal"> --%>
    <form:form method="POST" modelAttribute="user" class="form-horizontal registrationForm"
        onsubmit="return validate();">
        <form:input type="hidden" path="id" id="id" />
        <div class="form-group">
            <form:label path="nickname" for="nickname" class="col-sm-2 control-label">
                <spring:message code="UserList.nick" />
            </form:label>
            <div class="col-xs-2" class="control-group">
                <form:input path="nickname" id="nickname" class="form-control" />
            </div>
            <form:errors path="nickname" cssClass="error" />
        </div>
        <div class="form-group">
            <form:label path="email" for="Email" class="col-sm-2 control-label">
                <spring:message code="UserList.email" />
            </form:label>
            <div class="col-xs-2">
                <form:input path="email" id="email" class="form-control" style="text-transform: lowercase;" />
            </div>
            <form:errors path="email" cssClass="error" />
        </div>
        <div class="form-group">
            <form:label path="password" for="password" class="col-sm-2 control-label">
                <spring:message code="registration.password" />
            </form:label>
            <div class="col-xs-2">
                <form:password path="password" id="password" class="form-control" />
            </div>
            <form:errors path="password" cssClass="error" />
        </div>
        <div class="form-group">
            <form:label path="" for="confirm" class="col-sm-2 control-label">
                <spring:message code="registration.confirm" />
            </form:label>
            <div class="col-xs-2">
                <form:password path="" id="confirm" name="confirm" class="form-control" />
            </div>
        </div>
        <div>
            <form:input path="role" type="hidden" value="USER" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <input type="checkbox" name="myCheckbox" />
                    <form:label path="">
                        <spring:message code="registration.renter" />
                    </form:label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary" name="add">
                    <spring:message code="registration.register" />
                </button>
            </div>
        </div>
    </form:form>
</div>