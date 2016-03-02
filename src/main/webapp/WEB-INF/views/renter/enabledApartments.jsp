<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- dialogs -->
<div class="modal fade" tabindex="-1" role="dialog" id="modalBoxInfo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="apartmentDetail.modalTitle" />
                </h4>
            </div>
            <div class="modal-body">
                <c:if test="${not empty info && info.event eq 'publish'}">
                    <spring:message code="apartmentDetail.messagePublish" />
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <spring:message code="close" />
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<c:if test="${not empty info}">
    <input type="hidden" name="infoEvent" id="infoEvent" value="${info.event}">
</c:if>
<c:if test="${empty info}">
    <input type="hidden" name="infoEvent" id="infoEvent" value="">
</c:if>

<div>
    <div class="page-header">
        <h3>
            <spring:message code="allApartments.enabledApartments" />
        </h3>
    </div>
    <c:if test="${empty listApartments}">
        <spring:message code="allApartments.noApartments" />
    </c:if>
    <c:if test="${!empty listApartments}">
        <table class="table table-striped status-apartments-table">
            <tr>
                <th width="120"><spring:message code="apartment.name" /></th>
                <th width="60"><spring:message code="allApartments.detail" /></th>
                <th width="60"><spring:message code="allApartments.hide" /></th>
                <th width="60"><spring:message code="MODERATOR" /></th>
                <th width="60"><spring:message code="apartmentDetail.history" /></th>
            </tr>
            <c:forEach items="${listApartments}" var="apartment">
                <tr>
                    <td><a href="<c:url value='/renter/preview/${apartment.getId()}' />">${apartment.getName()}</a></td>
                    <td><a href="<c:url value='/renter/apartmentDetail/${apartment.id}' />">
                            <span class="glyphicon glyphicon-pencil"></span>
                        </a></td>
                    <td><a href="<c:url value='/renter/disableApartment/${apartment.id}' />">
                            <span class="glyphicon glyphicon-eye-close"></span>
                        </a></td>
                    <td><c:if test="${apartment.moderator != null}">
                            <a href="<c:url value='/renter/userDetails/${apartment.moderator.id}/${apartment.id}'/>">
                                ${apartment.moderator.nickname } </a>
                        </c:if> <c:if test="${apartment.moderator == null}">
                            <span class="glyphicon glyphicon-minus"></span>
                        </c:if> <c:if test="${apartment.aproved}">
                            <span class="glyphicon glyphicon-ok approved-ico"></span>
                        </c:if></td>
                        <td><a href="<c:url value='/renter/apartmentHistory/${apartment.id}'/>">
                    		<span class="glyphicon glyphicon-time"></span>
                    	</a> </td>
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
    <hr>
</div>
<script type="text/javascript">
 $(".pagination a").click(function(event) {
        event.preventDefault();
        var $this = $(this);
        
        var page = $this.attr("href");
        setGetParameter("page", page);
    });
    </script>