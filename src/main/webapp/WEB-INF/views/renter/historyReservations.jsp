<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="history">
    <div class="page-header">
        <h3>
            <spring:message code="apartmentDetail.history" />
            <strong id="apartmentName">${apartment.name}</strong>
        </h3>
    </div>
    <c:if test="${empty pastReserved}">
        <spring:message code="apartmentDetail.noHistory" />
    </c:if>
    <c:if test="${not empty pastReserved}">
        <table class="table">
            <tr>
                <th><spring:message code="apartmentDetail.Reservation.Start" /></th>
                <th><spring:message code="apartmentDetail.Reservation.End" /></th>
                <th><spring:message code="apartmentDetail.Reservation.Status" /></th>
                <th><spring:message code="apartmentDetail.Reservation.Detail" /></th>
            </tr>
            <c:forEach items="${pastReserved}" var="reserv">
                <tr>
                    <td>${reserv.dateStartReservation}</td>
                    <td>${reserv.dateEndReservation}</td>
                    <td><spring:message code="ActionStatus.${reserv.status}" /></td>
                    <td><a href='<c:url value="/renter/reservationDetail/${reserv.id}"/>'>
                            <spring:message code="apartmentDetail.Reservation.Detail" />
                        </a></td>
                </tr>
            </c:forEach>
        </table>
        
            <c:if test="${lastPageIndex != 1}">
                <nav>
                    <ul class="pagination">
                        <c:forEach begin="1" end="${lastPageIndex}"
                            varStatus="loop">
                            <c:if test="${loop.index eq currentPage}">
                                <li class="active"><a
                                        href="${currentPage}">${currentPage}</a></li>
                            </c:if>
                            <c:if test="${loop.index != currentPage}">
                                <li><a href="${loop.index}">${loop.index}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
            
    </c:if>
</div>

<script type="text/javascript">
 $(".pagination a").click(function(event) {
        event.preventDefault();
        var $this = $(this);
        
        var page = $this.attr("href");
        setGetParameter("page", page);
    });
</script>