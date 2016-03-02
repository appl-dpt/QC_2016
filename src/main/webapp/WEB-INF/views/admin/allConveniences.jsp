<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url var="urlUpdateSettings" value="/admin/deleteConvenience" />
<c:url var="urlAddSettings" value="/admin/addConvenience" />

<c:if test="${empty conveniences}">
    <h3><b><spring:message code="allConveniences.noConveniences" /></b></h3>
    <table class="table table-striped admin-table">
                <tr class="col-md-3">
                            <td><form:form name="allConveniences" action="${urlAddSettings}" modelAttribute="addConvenience"
                    method="POST" onsubmit="return validate();" class="allConveniences">
                    <div>
                        <form:label path="name">
                            <spring:message code="allConveniences.addConveniences" />
                        </form:label>
                    </div>
                    <div class="form-group">
                        <div class="divInputContainer">
                            <form:input path="name" class="form-control" id="convenience"></form:input>
                        </div>
                        <form:errors path="name" cssClass="error" />
                    </div>
                     <tr>
                        <td><button type="submit" class="btn btn-primary" id="imageSelectBtn">
                                <spring:message code="save" />
                            </button></td>
                    </tr>
                </form:form></td>
                </tr>
    </table>
</c:if>
<c:if test="${!empty conveniences}">
    <table class="table table-striped admin-table">
        <tr>
            <th class="col-md-3"><spring:message code="allConveniences.conveniences" /></th>
        </tr>
        <c:forEach var="conv" items="${conveniences}">
            <tr>
                <td>${conv.name}</td>

                <td><form:form name="allConveniences" action="${urlUpdateSettings}"
                        modelAttribute="deleteConvenience" method="POST">
                        <form:hidden path="id" value="${conv.id}" />
                        <form:hidden path="name" value="${conv.name}" />
                        <button type="submit" class="btn btn-danger" id="imageSelectBtn">
                            <spring:message code="delete" />
                        </button>
                    </form:form></td>
            </tr>
        </c:forEach>

        <tr>
            <td><form:form name="allConveniences" action="${urlAddSettings}" modelAttribute="addConvenience"
                    method="POST" onsubmit="return validate();" class="allConveniences">
                    <div>
                        <form:label path="name">
                            <spring:message code="allConveniences.addConveniences" />
                        </form:label>
                    </div>
                    <div class="form-group">
                        <div class="divInputContainer">
                            <form:input path="name" class="form-control" id="convenience"></form:input>
                        </div>
                        <form:errors path="name" cssClass="error" />
                    </div>
                     <tr>
                        <td><button type="submit" class="btn btn-primary" id="imageSelectBtn">
                                <spring:message code="save" />
                            </button></td>
                    </tr>
                </form:form></td>
        </tr>

    </table>
</c:if>