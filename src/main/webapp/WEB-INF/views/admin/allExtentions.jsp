<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url var="urlUpdateSettings" value="/admin/deleteExtention" />
<c:url var="urlAddSettings" value="/admin/addExtention" />


<c:if test="${empty photoExtentions}">
    <h3><b><spring:message code="adminExtention.noExtention" /></b></h3>
    <table class="table table-striped admin-table">
            <tr class="col-md-3">
            <td><form:form name="allExtentions" action="${urlAddSettings}" modelAttribute="addExt"
                    class="allExtentions" method="POST" onsubmit="return validate();">
                    <div class="form-group">
                    <form:label path="extention" class="col-sm-2 control-label">
                        <spring:message code="adminExtention.createExtention" />
                    </form:label>                   
                        <div class="divInput">
                            <form:input path="extention" class="form-control" id="extention" />
                        </div>
                        <form:errors path="extention" cssClass="error" />
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
<c:if test="${!empty photoExtentions}">
    <table class="table table-striped admin-table">
        <tr>
            <th class="col-md-3"><spring:message code="adminExtention.allExtentions" /></th>
        </tr>
        <c:forEach var="photo" items="${photoExtentions}">
            <tr>
                <td>${photo.extention}</td>

                <td><form:form name="allExtentions" action="${urlUpdateSettings}" modelAttribute="deleteExt"
                        method="POST">
                        <form:hidden path="id" value="${photo.id}" />
                        <form:hidden path="extention" value="${photo.extention}" />
                        <button type="submit" class="btn btn-danger" id="imageSelectBtn">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            <%-- <spring:message code="delete" /> --%>
                        </button>
                    </form:form></td>
            </tr>

        </c:forEach>
        <tr>
            <td><form:form name="allExtentions" action="${urlAddSettings}" modelAttribute="addExt"
                    class="allExtentions" method="POST" onsubmit="return validate();">
                    <form:label path="extention">
                        <spring:message code="adminExtention.createExtention" />
                    </form:label>
                    <div class="form-group">
                        <div class="divInput">
                            <form:input path="extention" class="form-control" id="extention"></form:input>
                        </div>
                        <form:errors path="extention" cssClass="error" />
                    </div>
                    <a href="#" data-toggle="tooltip" title="messagesL">
                    <button type="submit" class="btn btn-primary" id="imageSelectBtn">
                                <spring:message code="save" />
                            </button>
                            </a>
                </form:form></td>
        </tr>
    </table>
</c:if>
<!-- <script>

    
      $(function () { $("[data-toggle = 'tooltip']").tooltip(); });

</script> -->

<script>
 
 $(document).ready(function(){
    var messagesL = getMessages([ "userSettings.tooltip"]);
      $("#").tooltip({placement: 'top', title: messagesL[ "userSettings.tooltip" ]})

    
      $(function () { $("[data-toggle = 'tooltip']").tooltip(); });
})
</script>