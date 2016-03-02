<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<input type="hidden" id="path" value="<c:url value='/'/>">
<img src="<c:url value='/img/myApartments.png' />">
<spring:message code="moderatorMap.yourApartments" />
<img src="<c:url value='/img/freeApartments.png' />">
<spring:message code="moderatorMap.freeApartments" />
<img src="<c:url value='/img/otherApartments.png' />">
<spring:message code="moderatorMap.otherApartments" />
<select class="btn-right" id="filterMarkers">
    <option value="myApartments">
        <spring:message code="moderatorMap.yourApartments" />
    </option>
    <option value="freeApartments">
        <spring:message code="moderatorMap.freeApartments" />
    </option>
    <option value="otherApartments">
        <spring:message code="moderatorMap.otherApartments" />
    </option>
    <option value="allApartments">
        <spring:message code="moderatorMap.allApartments" />
    </option>
</select>
<div id="map_container_moderator"></div>
