<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            <c:if test="${error eq 'invalidCredentials'}">
                <spring:message code="login.message.invalidCredentials" />
            </c:if>
            <c:if test="${error eq 'blocked'}">
                <spring:message code="login.message.blocked" />
            </c:if>
            <c:if test="${error eq 'notConfirmed'}">
                <spring:message code="login.message.confirm" />
            </c:if>
            <c:if test="${error eq 'invalidPassword'}">
                <spring:message code="login.message.invalidPassword" />
            </c:if>
        </div>
    </c:if>

    <c:url value="/j_spring_security_check" var="loginUrl" />
    <form:form name='loginForm' action="${loginUrl}" method='POST' >
        <div class="form-horizontal" style="margin-left: 15px;">
            <div class="form-group">
                <div class="col-xs-3 input-group margin-bottom-sm">
                    <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span> <input type="text"
                        class="form-control" name="j_username" placeholder="<spring:message code="email" />" /> 
                        <!-- required oninvalid="this.setCustomValidity('<spring:message code="login.message.emptyField" />')"
                        oninput="setCustomValidity('')" onmousemove="this.value = this.value.toLowerCase();" -->
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-3 input-group">
                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span> <input type="password"
                        class="form-control" name="j_password" placeholder="<spring:message code="password" />" />
                        <!-- required oninvalid="this.setCustomValidity('<spring:message code="login.message.emptyField" />')"
                        oninput="setCustomValidity('')"  -->
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="submit">
                    <spring:message code="login.signin" />
                </button>
            </div>
        </div>
    </form:form>

    <div class="row socials">
        <div class="col-xs-2">
            <a href="<c:url value="/auth/facebook"/> ">
                <button class="btn btn-block btn-social btn-facebook">
                    <i class="fa fa-facebook"></i> Facebook
                </button>
            </a>
        </div>
        <div class="col-xs-2">
            <a href="<c:url value="/auth/google"/> ">
                <button class="btn btn-block btn-social btn-google">
                    <i class="fa fa-google"></i> Google
                </button>
            </a>
        </div>
    </div>
</div>