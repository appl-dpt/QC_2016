<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <c:if test="${empty userList}">
            <spring:message code="adminConfiguration.noRenter" />
        </c:if>
        <c:if test="${!empty userList}">
            <table class="table table-striped admin-table">
                <tr>
                    <th class="col-md-3"><spring:message
                            code="UserList.email" /></th>
                    <th class="col-md-4"><spring:message
                            code="UserList.nick" /></th>
                    <th class="col-md-3"><spring:message
                            code="UserList.role" /></th>
                    <th class="col-md-2"><spring:message
                            code="UserList.enabled" /></th>
                </tr>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td class="email">${user.email}</td>
                        <td class="nickname">${user.nickname}</td>
                       <td><select name="userRole"
                            class="form-control user-change">
                                <c:forEach var="role"
                                    items="${roleList}">
                                    <option value="${role}"
                                        <c:if test="${ role eq user.role }">selected</c:if>>
                                        <spring:message code="${role}" />
                                    </option>
                                </c:forEach>
                        </select></td>
                        <td><c:choose>
                                <c:when test="${user.enabled}">
                                    <input type="checkbox" checked
                                        name="enabled" />
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox"
                                        name="enabled" />
                                </c:otherwise>
                            </c:choose></td>
                    </tr>
                </c:forEach>
            </table>
                    </c:if>

<script type="text/javascript">
    var successColor = "#dff0d8";
    var errorColor = "#f2dede";
    $(':checkbox').change(function() {

        var $this = $(this);
        var email = $this.parent().siblings(".email").text();
        var token = $("meta[name='_csrf']").attr("content");
        var csrfheader = $("meta[name='_csrf_header']").attr("content");

        $this.prop("disabled", true);
        $.ajax({
            url : "<c:url value="/admin/adminChange"/>",
            method : "POST",
            data : {
                "_csrf" : token,
                "email" : email,
                "enabled" : $this.is(':checked')
            }

        }).always(function() {
            $this.prop("disabled", false);
        }).success(function() {
            blink($this.closest("tr"), successColor);
        }).fail(function() {
            blink($this.closest("tr"), errorColor);
        });

    });
    $(".user-change").change(function(event) {
        var $this = $(this);
        var selected = $this.val();
        var email = $this.parent().siblings(".email").text();
        var token = $("meta[name='_csrf']").attr("content");
        var csrfheader = $("meta[name='_csrf_header']").attr("content");
        $this.prop("disabled", true);
        $.ajax({
            url : "<c:url value="/admin/adminChange"/>",
            method : "POST",
            data : {
                "_csrf" : token,
                "email" : email,
                "role" : selected
            }

        }).always(function() {
            $this.prop("disabled", false);
        }).success(function() {
            blink($this.closest("tr"), successColor);
        }).fail(function() {
            blink($this.closest("tr"), errorColor);
        });
    });
    
function blink(element, color) {
    $element = $(element);
    var background = $(element).css("background-color");
    $element.animate({
        "background-color" : color
    }, "300ms", function() {
        $element.animate({
            "background-color" : background
        }, "300ms", function() {
            $element.attr("style");
        });
    });
}
</script>
                
