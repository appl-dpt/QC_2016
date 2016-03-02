<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<c:if test="${resultPayment == 'PAYMENT_SUCCESS'}">
    <div class="alert alert-success">
        <!-- <strong>Success!</strong> -->
        <spring:message code="paymentResult.${resultPayment}" /> 
    </div>
    <div >
    <input class="form-control image-cst-smile" src="<c:url value="/img/happy_smile.png" />" type="image">
    </div>
</c:if>

<c:if test="${resultPayment == 'PAYMENT_FAIL'}">
    <div class="alert alert-warning">
        <!-- <strong>Error!</strong> Payment failed! -->
        <spring:message code="paymentResult.${resultPayment}" />
        <input class="form-control image-cst-smile" src="<c:url value="/img/sad_smile.png" />" type="image"> 
    </div>
</c:if>

<c:if test="${resultPayment == 'PAYMENT_OLD'}">
    <div class="alert alert-danger">
        <!-- <strong>Error!</strong> Page is outdated! -->
        <spring:message code="paymentResult.${resultPayment}" />
        <input class="form-control image-cst-smile" src="<c:url value="/img/dead_smile.png" />" type="image"> 
    </div>
</c:if>

<a href="<c:url value="/" />"> <spring:message code="paymentResult.backToMain" /> </a>