<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div class="alert alert-success">
 <!-- <strong>Success!</strong> -->
 <spring:message code="orderPayment.infoMessage"
  arguments=";${paymentReserved.apartment.name};${paymentReserved.dateStartReservation};${paymentReserved.dateEndReservation}" 
  htmlEscape="false"
  argumentSeparator=";"
  />
</div>

<div class="alert alert-warning">
 <!-- <strong>Error!</strong> Payment failed! -->
 <spring:message code="orderPayment.warningMassage" />
</div>

<c:url var="paymentLink" value="/tenant/paymentMaking/" />
<form:form id="paymentForm" action="${paymentLink}" modelAttribute="paymentReserved" method="post" style="display: none">
 <form:hidden path="id" value="" id="reservedId" />
</form:form>

<div class="container">
 <div class="tooltip-group">
  <c:forEach items="${availablePayments}" var="payment">
   <a href="#" class="image-cst tooltip-cst" payIndex="${payment.paymentMethod.id}" data-toggle="tooltip"
    data-placement="top" title=" <spring:message code="orderPayment.${payment.paymentMethod.name}" />"> <input
    id="inputImage" name="imageLink" class="form-control image-cst" src="${payment.paymentMethod.icon}" type="image">
    <%-- ${payment.paymentMethod.name} --%>
   </a>
   <%--     ${payment.paymentMethod.name} --%>
  </c:forEach>
 </div>
</div>

<script>
    $(document).ready(function() {
        $("a[payIndex]").click(function() {
            $("#reservedId").val("${paymentReserved.id}");
            var index = $(this).attr("payIndex");
            var $form = $("#paymentForm");
            console.log("Payment: " + index);
            $form.attr("action", $form.attr("action") + index);
            $form.submit();
        });

        $('[data-toggle="tooltip"]').tooltip();

    })
</script>