<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="">
    <div class="page-header">
        <h2>
            <spring:message code="allApartments.addApartment" />
        </h2>
    </div>
    <div class="row col-md-8 col-lg-8 col-sm-8">
        <c:url var="urlAddApartment" value="/renter/addApartment" />
        <form:form action="${urlAddApartment}" modelAttribute="apartment" method="post" class="form-signin frmApInfo">
            <form:hidden path="id" />
            <form:label path="name" for="inputName">
                <spring:message code="apartment.name" />
            </form:label>
            <div class="divInputContainer">
                <form:input path="name" id="inputName" class="form-control" />
            </div>
            <form:errors path="name" cssClass="error" />
            <br>
            <form:label path="city" for="inputCity">
                <spring:message code="apartment.city" />
            </form:label>
            <div class="divInputContainer">
                <form:input path="city" id="inputCity" class="form-control" />
            </div>
            <form:errors path="city" cssClass="error" />
            <form:hidden path="" name="selected" value="0" id="selected" />
            <br>
            <form:label path="address" for="inputAddress">
                <spring:message code="apartment.address" />
            </form:label>
            <div class="divInputContainer">
                <form:input path="address" id="inputAddress" class="form-control" />
            </div>
            <form:errors path="address" cssClass="error" />
            <br>
            <form:label path="description" for="inputDescription">
                <spring:message code="apartment.description" />
            </form:label>
            <form:textarea rows="10" path="description" id="inputDescription" class="form-control" />
            <form:hidden path="renter.id" id="inputRenter" value="${userID}" />
            <br>
            <form:button type="submit" class="btn   btn-primary" name="add">
                <spring:message code="allApartments.addApartment" />
            </form:button>
            <a class="btn btn-primary" href="<c:url value="/renter/allApartments"/>">
                <spring:message code="cancel" />
            </a>
        </form:form>
    </div>
</div>
