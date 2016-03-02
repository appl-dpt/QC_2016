<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url var="urlUpdatePayment" value="/admin/adminPayment/update" />

<c:if test="${empty paymentMethods}">
    <h3><b><spring:message code="adminExtention.noExtention" /></b></h3>
</c:if>

<c:if test="${!empty paymentMethods}">
    <table class="table table-striped admin-table">
        <tr>
            <th class="col-md-3"><spring:message code="adminConfiguration.payment" /></th>
            <th class="col-md-3"><spring:message code="adminExtention.allExtentions" /></th>
            <th class="col-md-3"><spring:message code="allApartments.enable" /></th>
        </tr>
        <c:forEach var="payment" items="${paymentMethods}">
            <tr>
                <td>${payment.name}</td>

                <td><input id="inputImage" name="imageLink" class="form-control image-cst" src="${payment.icon}" type="image"></td>
                
                <td>
                <c:choose>
                                <c:when test="${payment.enabled}">
                                    <input type="checkbox" checked
                                        name="enabled" payName = "${payment.name}" />
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox"
                                        name="enabled" payName = "${payment.name}" />
                                </c:otherwise>
                            </c:choose>
                            </td>
                 
<%--                    <td><form:form action="${urlUpdatePayment}"
                        modelAttribute="updatePayment" method="POST">
                        <form:hidden path="id" value="${payment.id}" />
                        <form:hidden path="name" value="${payment.name}" />
                        <form:hidden path="enabled" value="${payment.enabled}" />
                        <form:hidden path="icon" value="${payment.icon}" />

                    <button type="submit" class="btn btn-primary" name="edit">
                        <spring:message code="save" />
                    </button>
                    </form:form></td> --%>
            </tr>
         </c:forEach>
         </table>
         </c:if>

<script type="text/javascript">
    var successColor = "#dff0d8";
    var errorColor = "#f2dede";
    $(':checkbox').change(function() {

        var $this = $(this);
/*         var email = $this.parent().siblings(".email").text(); */
        var token = $("meta[name='_csrf']").attr("content");
        var csrfheader = $("meta[name='_csrf_header']").attr("content");
        console.log($this.attr('payName'));
        $this.prop("disabled", true);
        $.ajax({
            url : "<c:url value="/admin/adminPayment/update"/>",
            method : "POST",
            data : {
                "_csrf" : token,
                /* "email" : email, */
                "enabled" : $this.is(':checked'),
                "payName" : $this.attr('payName')                
            }

        }).always(function() {
            $this.prop("disabled", false);
        }).success(function() {
            blink($this.closest("tr"), successColor);
        }).fail(function() {
            blink($this.closest("tr"), errorColor);
        });

    });
/*     $(".user-change").change(function(event) {
        var $this = $(this);
        var selected = $this.val();
        /* var email = $this.parent().siblings(".email").text();
        var token = $("meta[name='_csrf']").attr("content");
        var csrfheader = $("meta[name='_csrf_header']").attr("content");
        $this.prop("disabled", true);
        $.ajax({
            url : "<c:url value="/admin/adminPayment/update"/>",
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
    });*/
    
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