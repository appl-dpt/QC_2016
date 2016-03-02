<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url var="urlUpdateSettings" value="/admin/adminConfigurationSave" />

<table class="table table-striped admin-table">

    <c:forEach items="${dtoList}" var="valTest">
        <div class="form-horizontal">
            <form:form name="adminConfiguration" action="${urlUpdateSettings}" modelAttribute="dto" method="POST"
                class="form-horizontal col-xs-8">
                <tr>
                    <td><form:label path="features" value="${valTest.features}">
                            <spring:message code="adminConfiguration.${valTest.features}" />
                        </form:label></td>
                    <td><form:hidden path="features" value="${valTest.features}" /></td>

                    <td><form:input path="parameter" class="form-control" value="${valTest.parameter}" /></td>
                    <td><form:button type="submit" class="btn btn-primary" style="margin-left: 300px;">
                            <spring:message code="UserList.save" />
                        </form:button></td>
            </form:form>
        </div>
    </c:forEach>

</table>