<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <h1>
        <spring:message code="UserList.newUser" />
    </h1>
    <form:form method="POST" modelAttribute="user" class="form-horizontal registrationForm"
        onsubmit="return validate();" action="save">
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
                <form:input path="email" id="email" class="form-control" />
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
                <form:password path="" id="confirm" class="form-control" />
            </div>
        </div>
        <div>
            <form:input path="enabled" type="hidden" value="true" />
            <form:input path="role" type="hidden" value="USER" />
        </div>
        <div class="form-group">
            <form:label path="role" for="role" class="col-sm-2 control-label">
                <spring:message code="userForm.role" />
            </form:label>
            <div class="col-xs-2">
                <form:select path="role" class="form-control">
                    <c:forEach var="role" items="${roleList}">
                        <form:option value="${role}">${role}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary" name="add">
            <spring:message code="UserList.save" />
        </button>
    </form:form>
</div>
