<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel-primary filtetableApart table-cst">
	<div class="panel-heading">
		<h3 class="panel-title">
			<spring:message code="apartmentOrders.filter" />
		</h3>
	</div>
	<table class="table">
		<thead>
			<c:url var="filterLink" value="/1" />
			<form:form modelAttribute="filterApartment" method="post"
				id="filterApartment" action="${filterLink}">
				<spring:message var="city" code="apartmentOrders.city" />
				<spring:message var="startDate" code="apartmentOrders.startDate" />
				<spring:message var="endDate" code="apartmentOrders.endDate" />
				<spring:message var="maxCountGuests"
					code="apartmentOrders.maxCountGuests" />
				<spring:message var="name" code="apartmentOrders.name" />
				<tr class="filtersTr"><br>
					<form:input class="form-control filter" placeholder="${city}"
						path="city" id="cityFilter" />
				</tr>
				<br>
				<tr class="filtersTr">
					<form:input class="form-control filter" placeholder="${startDate}"
						path="startDate" id="startDateFilter" />
				</tr><br>
				<tr class="filtersTr">
					<form:input class="form-control filter datepicker"
						placeholder="${endDate}" path="endDate" id="endDateFilter" />
				</tr><br>
				<tr class="filtersTr">
					<section class="range-slider">
						<spring:message code="apartmentOrders.price" />
						<span class="rangeValues"></span>
						<form:input path="startPrice" value="0" min="0" max="1500"
							step="50" type="range" id="startPriceFilter" />
						<form:input path="endPrice" value="1500" min="0" max="1500"
							step="50" type="range" id="endPriceFilter" />
					</section>
				</tr><br>
				<tr class="filtersTr">
					<section class="range-slider">
						<spring:message code="apartmentOrders.rating" />
						<span class="rangeValues"></span>
						<form:input path="startRaiting" value="0" min="0" max="5"
							step="0.5" type="range" id="startRaitingFilter" />
						<form:input path="endRaiting" value="5" min="0" max="5" step="0.5"
							type="range" id="endRaitingFilter" />
					</section>
				</tr><br>
				<tr class="filtersTr">
					<form:input class="form-control filter"
						placeholder="${maxCountGuests}" path="maxCountGuests"
						id="maxCountGuestsFilter" />
				</tr><br>
				<tr class="filtersTr">
					<form:input class="form-control filter" placeholder="${name}"
						path="name" id="nameFilter" />
				</tr><br>
				<tr class="filtersTr">
					<spring:message code="apartmentOrders.orderBy" />
					<form:select class="form-control filter" path="sortBy"
						id="sortByFilter">
							<option value="sortByCity" selected>
								<spring:message code="apartmentOrders.orderBy.city" />
							</option>
							<option value="sortByPrice" >
								<spring:message code="apartmentOrders.orderBy.price" />
							</option>
							<option value="sortByRating" >
								<spring:message code="apartmentOrders.orderBy.rating" />
							</option>
					</form:select>
				</tr><br>
				<tr class="filtersTr">
					<button type="submit" class="btn btn-primary" id="SearchBtn" style="width: 160px;">
						<spring:message code="apartmentOrders.search" />
					</button>
				</tr>
			</form:form>
		</thead>
	</table>
</div>