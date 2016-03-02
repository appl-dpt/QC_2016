<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="action" value="book" />
<sec:authorize access="hasAuthority('RENTER')">
	<sec:authentication property="principal.id" var="principalId" />
</sec:authorize>

<div class="panel-primary table-cst">
	<table class="table">
		<c:if test="${not empty apartmentList}">
			<c:forEach items="${apartmentList}" var="apartment">
				<tr>
					<c:set var="action" value="book" /> <sec:authorize
						access="hasAuthority('RENTER')">
						<c:if test="${apartment.renter.id eq principalId}">
							<c:set var="action" value="renter/apartmentDetail" />
						</c:if>
					</sec:authorize>
					<div class="well row index-well">
						<a href="<c:url value='/${action}/${apartment.getId()}' />">
							<div class="index-apartment-image-wrapper col-md-2">
								<c:if test="${not empty apartment.links}">
									<img class="img-rounded"
										src="<c:url value="Apartments/photo/${apartment.links.iterator().next().id}" />" />
								</c:if>
							</div>
							<div class="index-apartment-info-wrapper col-md-10">
								<div class="apartment-rating rating row">
									<div class="posted-rating-wrapper">
										<div class="posted-rating-value"></div>
										<div></div>
									</div>
									<span> <fmt:formatNumber type="number"
											maxFractionDigits="2" value="${apartment.raiting}" />
									</span>
								</div>
								<h4>${apartment.name}</h4>
								<div class="description">
									<span>${apartment.description}</span>
								</div>
								<div>
									<spring:message code="city" />
									: ${apartment.city}
								</div>
								<div>
									<spring:message code="address" />
									: ${apartment.address}
								</div>
								<div>
									<spring:message code="price" />
									: ${apartment.price}
								</div>
								<div>
									<spring:message code="maxCountGuests" />
									: ${apartment.maxCountGuests}
								</div>
							</div>
						</a>
					</div>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty apartmentList}">
			<tr>
				<td></td>
				<td align="center"><spring:message
						code="apartmentList.nullSearchResult" /></td>
				<td></td>
			</tr>
		</c:if>
	</table>
</div>

<c:url var="paginationLink" value="/" />
<form:form id="paginationIndexForm" action="${paginationLink}"
	modelAttribute="filterApartment" method="post" style="display: none">
	<form:hidden path="city" value="" id="pgCity" />
	<form:hidden path="startDate" value="" id="pgStartDate" />
    <form:hidden path="endDate" value="" id="pgEndDate" />
	<form:hidden path="startPrice" value="" id="pgStartPrice" />
    <form:hidden path="endPrice" value="" id="pgEndPrice" />
    <form:hidden path="startRaiting" value="" id="startRatingFilter" />
    <form:hidden path="endRaiting" value="" id="endRatingFilter" />
    <form:hidden path="maxCountGuests" value="" id="pgMaxCountGuests" />
   	<form:hidden path="name" value="" id="pgName" />
   	<form:hidden path="sortBy" value="" id="pgSortBy" />
</form:form>

<ul class="pagination">
	<c:if test="${filterApartment.startPage > filterApartment.pagesRange}">
		<li><a href="#" btnIndex="${filterApartment.startPage - 1}"
			class="submitLink">&laquo;</a></li>
	</c:if>
	<c:forEach begin="${filterApartment.startPage}"
		end="${filterApartment.endPage}" var="i" step="1">
		<c:if test="${i == filterApartment.currentPage}">
			<li class="active"><a href="#" btnIndex="${i}"
				class="submitLink">${i}</a></li>
		</c:if>
		<c:if test="${i != filterApartment.currentPage}">
			<li><a href="#" btnIndex="${i}" class="submitLink">${i}</a></li>
		</c:if>
	</c:forEach>
	<c:if test="${filterApartment.endPage < filterApartment.pageCount}">
		<li><a href="#" btnIndex="${filterApartment.endPage+1}"
			class="submitLink">&raquo;</a></li>
	</c:if>
</ul>
