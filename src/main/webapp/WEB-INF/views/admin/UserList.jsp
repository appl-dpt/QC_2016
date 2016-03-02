<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div align="center"></div>
<div class="row">
    <div class="col-md-12">
        <div class="clearfix">
            <form:form action="allUsers" method="GET"
                class="form-inline admin-search-form">
                <div class="form-group">
                    <spring:message code="UserList.email"
                        var="emailLocale" />
                    <div class="input-group">
                        <input class="form-control" id="email"
                            placeholder="${emailLocale}" name="email"
                            value="${email}" />
                    </div>
                    <select name="userRole"
                        class="form-control filter-change">
                        <option value="">
                            <spring:message code="UserList.usersList" />
                        </option>
                        <c:forEach var="role" items="${roleList}">
                            <option value="${role}"
                                <c:if test="${ role eq selectedRole }">selected</c:if>>
                                <spring:message code="${role}" />
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"
                            aria-hidden="true"></span>
                    </button>
                    <a class="btn btn-default"
                        href="<c:url value="allUsers" />">
                        <span class="glyphicon glyphicon-remove"
                            aria-hidden="true"></span>
                    </a>
                </div>
            </form:form>
            <a class="btn btn-primary pull-right"
                href="<c:url value="/admin/User"/>">
                <span class="glyphicon glyphicon-plus"></span>
                <spring:message code="UserList.newUser" />
            </a>
        </div>
        <c:if test="${empty userList}">
            <spring:message code="UserList.noUser" />
        </c:if>
        <c:if test="${!empty userList}">
        <div class="admin-table-wrapper">
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
            </div>
            <c:set var="leftBorder" value="${currentPage div 5}" />
            <c:set var="leftBorder" value="${leftBorder - (leftBorder % 1)}" />
            <fmt:formatNumber var="leftBorder" pattern="##" value="${leftBorder * 5}"/>
            <c:if test="${leftBorder < 1}">
                 <c:set var="leftBorder" value="1" />
            </c:if>
            <c:set var="rightBorder" value="${leftBorder + 4}" />
            <c:if test="${rightBorder > lastPageIndex}">
                <c:set var="rightBorder" value="${lastPageIndex}" />
            </c:if>
            <c:if test="${lastPageIndex != 1}">
                <nav>
                    <ul class="pagination">
                        <c:if test="${leftBorder > 1}">
                            <li><a href="${leftBorder - 1}">&laquo;</a></li>
                        </c:if>
                        <c:forEach begin="${leftBorder}" end="${rightBorder}"
                            varStatus="loop">
                            <c:if test="${loop.index eq currentPage}">
                                <li class="active"><a
                                        href="${currentPage}">${currentPage}</a></li>
                            </c:if>
                            <c:if test="${loop.index != currentPage}">
                                <li><a href="${loop.index}">${loop.index}</a></li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${rightBorder < lastPageIndex}">
                            <li><a href="${rightBorder + 1}">&raquo;</a></li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </c:if>
    </div>
</div>
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
    $(".filter-change").change(
            function() {
                var email = $("input#email").val();
                if (email == undefined) {
                    email = "";
                }
                var role = $(this).val();
                var requestParams = "?email=" + email + "&userRole=" + role;
                window.location = "<c:url value="/admin/allUsers" />"
                        + requestParams;
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
    $(".pagination a").click(function(event) {
        event.preventDefault();
        var $this = $(this);

        var page = $this.attr("href");
        setGetParameter("page", page);
    });
    function paginationAligment() {
        var pageSize = getParameter("size");
        if(!pageSize) {
            pageSize = 5;
        }
        var $adminTable = $(".admin-table").closest(".admin-table-wrapper");
        var headerHeight = $adminTable.find("tr:first").height();
        var rowHeight = $adminTable.find("tr").eq(1).height();
        var minTableHeight = headerHeight + rowHeight*pageSize;
        $adminTable.css("min-height", minTableHeight);
    }
    function getParameter(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
           return decodeURIComponent(name[1]);
     }
    $(document).ready(function() {
        paginationAligment();
    });
</script>
