<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="hasAuthority('USER') || hasAuthority('RENTER') || hasAuthority('MODERATOR')">
    <sec:authentication property="principal.id" var="principalId" />
</sec:authorize>
<input type="hidden" id="apartmentTitle" value="${apartment.name}">
<input type="hidden" id="longitude" value="${apartment.longitude}">
<input type="hidden" id="latitude" value="${apartment.latitude}">
<!--  
  Modal windows
 -->
<c:if test="${reservedState eq 'SUCCESS'}">
    <script>
                    $(document).ready(function() {
                        console.log("Sucess");
                        $("#modalBoxGood").modal('show');
                    });
                </script>
</c:if>
<c:if test="${reservedState eq 'ERROR'}">
    <script>
                    $(document).ready(function() {
                        console.log("Error");
                        $("#modalBoxError").modal('show');
                    });
                </script>
</c:if>
<div class="modal fade" tabindex="-1" role="dialog" id="modalBoxGood">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="bookingApartment.bookSuccess" />
                </h4>
            </div>
            <div class="modal-body">
                <p>
                    <spring:message code="bookingApartment.bookSuccessMessage" />
                    ${bookingPeriod}
                </p>
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
<div class="modal fade" tabindex="-1" role="dialog" id="modalBoxError">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header-red modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="bookingApartment.bookError" />
                </h4>
            </div>
            <div class="modal-body">
                <p>
                    <spring:message code="bookingApartment.bookErrorMessage" />
                    ${bookingPeriod}
                </p>
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
<!--  
  Modal windows
 -->
<!--  
  Title
 -->
<div class="container-fluid">
    <h1>${apartment.name}</h1>
    <sec:authorize access="hasAuthority('MODERATOR')">
        <c:if test="${apartment.moderator==null}">
            <a href="<c:url value='/moderator/assignApartment/${apartment.id}'/>" class="btn btn-warning btn-right">
                <spring:message code="allApartments.assign" />
            </a>
        </c:if>
        <c:if test="${apartment.moderator!=null && apartment.moderator.id==moderator.id && apartment.aproved==false}">
            <a href="<c:url value='/moderator/approveApartment/${apartment.id}'/>" class="btn btn-warning btn-right">
                <spring:message code="moderator.approve" />
            </a>
        </c:if>

    </sec:authorize>
    <c:set var="glyphicon" value="glyphicon-thumbs-down text-danger" />
    <c:if test="${apartment.raiting >= 2.5}">
        <c:set var="glyphicon" value="glyphicon-thumbs-up text-success " />
    </c:if>
    <div class="apartment-rating rating">
        <div class="posted-rating-wrapper">
            <div class="posted-rating-value"></div>
            <div></div>
        </div>
        <span class="glyphicon ${glyphicon}"> <fmt:formatNumber type="number" maxFractionDigits="2"
                value="${apartment.raiting}" /> 
        </span>
        <c:if test="${apartment.aproved == true}">
            <img src="<c:url value='/img/approved.png'/>" class="btn-right">
        </c:if>
        
    </div>
        
</div>
<!--  
  Title
 -->
<div class="row">
    <div class="col-md-8">
        <!--  
      Carousel
     -->
        <div id="jssor_1"
            style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 800px; height: 456px; overflow: hidden; visibility: hidden; background-color: #24262e;">
            <!-- Loading Screen -->
            <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
                <div
                    style="filter: alpha(opacity = 70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
                <div
                    style="position: absolute; display: block; background: url('/img/loading.gif') no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
            </div>
            <div data-u="slides"
                style="cursor: default; position: relative; top: 0px; left: 0px; width: 800px; height: 356px; overflow: hidden;">

                <c:set var="hasPhotoActive" value="item active" />
                <c:if test="${apartment.links.isEmpty()==false}">
                    <c:forEach items="${apartment.links}" var="Photo">

                        <div data-p="144.50" style="display: none;">
                            <img data-u="image" src="<c:url value="/Apartments/photo/${Photo.id}" />" /> <img
                                data-u="thumb" src="<c:url value="/Apartments/photo/${Photo.id}" />" />
                        </div>
                        <c:set var="hasPhotoActive" value="item" />

                    </c:forEach>
                </c:if>
                <c:if test="${apartment.links.isEmpty()==true}">
                    <div data-p="144.50" style="display: none;">
                        <img data-u="image" src="<c:url value="/img/defaultApartment.jpg" />" /> <img data-u="thumb"
                            src="<c:url value="/img/defaultApartment.jpg" />" />
                    </div>
                    <c:set var="hasPhotoActive" value="item" />
                </c:if>
            </div>
            <!-- Thumbnail Navigator -->
            <div data-u="thumbnavigator" class="jssort01"
                style="position: absolute; left: 0px; bottom: 0px; width: 800px; height: 100px;" data-autocenter="1">
                <!-- Thumbnail Item Skin Begin -->
                <div data-u="slides" style="cursor: default;">
                    <div data-u="prototype" class="p">
                        <div class="w">
                            <div data-u="thumbnailtemplate" class="t"></div>
                        </div>
                        <div class="c"></div>
                    </div>
                </div>
                <!-- Thumbnail Item Skin End -->
            </div>
            <!-- Arrow Navigator -->
            <span data-u="arrowleft" class="jssora05l" style="top: 158px; left: 8px; width: 40px; height: 40px;"></span>
            <span data-u="arrowright" class="jssora05r" style="top: 158px; right: 8px; width: 40px; height: 40px;"></span>
            <!-- <a href="http://www.jssor.com" style="display:none">Slideshow Maker</a> -->
        </div>
    </div>
    <!--  
  Carousel
 -->
    <!--  
  Right part, DatePicker and Button for booking
 -->
    <div class="col-md-3 center-block">
    <div class="row">
    <span id="pricePerDay" class="tag tag-normal">Price: ${apartment.price} UAH</span>
    </div>
    <p/>
    <div class="row">
    <span id="priceForOrder" class="tag tag-order">Order: 0 UAH</span>
    </div>
    <p/>
        <sec:authorize access="hasAuthority('USER')">
            <c:url var="bookingLink" value="/tenant/bookApartment" />
            <form:form modelAttribute="newReserved" method="post" id="test" action="${bookingLink}">
                <div class="row">
                    <div id="datepicker"></div>
                    <form:hidden path="dateStartReservation" id="input1" />
                    <form:hidden path="dateEndReservation" id="input2" />
                </div>
                <p />
                <div class="row">
                    <form:hidden path="apartment.id" value="${apartment.id}" />
                    <button type="submit" class="btn btn-block btn-primary" id="bookButton" disabled>
                        <spring:message code="bookingApartment.apartmentBook" />
                    </button>
                </div>
            </form:form>
        </sec:authorize>
        <div class="row">
            <a href="<c:url value="/tenant/bookApartment"/>"> </a>
        </div>
    </div>
    <!--  
  Right part, DatePicker and Button for booking
 -->
</div>
<p />

<div class="row">
    <div class="panel-group col-md-8" id="accordion">
        <div class="panel panel-default ">
            <div class="panel-heading"  data-toggle="collapse" data-target="#collapseOne">
                <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                        <spring:message code="bookingApartment.decription" />
                    </a><i class="indicator glyphicon glyphicon-chevron-down  pull-right"></i>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body">${apartment.description}</div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" data-toggle="collapse" data-target="#collapseTwo">
                <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                        <spring:message code="bookingApartment.convenience" />
                    </a><i class="indicator glyphicon glyphicon-chevron-up  pull-right"></i>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">
                    <c:forEach items="${apartment.apartmentConveniences}" var="convenience">
                    <c:if test="${convenience.exists == true }">
                       <span class="badge badge-cst"> "${convenience.convenience.name}" </span>
                    </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <c:if test="${apartment.latitude!=0 && apartment.longitude!=0 }">
            <div class="panel panel-default">
                <div id="mapAccordion" class="panel-heading" data-toggle="collapse" data-target="#collapseThree">
                    <h4 class="panel-title">
                        <a class="accordion-toggle" id="showMap" data-toggle="collapse" data-parent="#accordion"
                            href="#collapseThree"> <spring:message code="bookingApartment.showMap" />
                        </a><i class="indicator glyphicon glyphicon-chevron-up pull-right"></i>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div id="map_container"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="panel panel-default">
            <div class="panel-heading" data-toggle="collapse" data-target="#collapseFour">
                <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                        <spring:message code="UserList.rentars" />
                    </a><i class="indicator glyphicon glyphicon-chevron-up  pull-right"></i>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse">
                <div class="panel-body">
                        <span class="badge badge-cst">${apartment.renter.firstname}</span><br>
                        <span class="badge badge-cst">${apartment.renter.lastname}</span><br>
                        <span class="badge badge-cst">${apartment.renter.phonenumber}</span><br>
                        <span class="badge badge-cst">${apartment.renter.email}</span><br> 
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-8">
        <div class="detailBox">
            <div class="titleBox">
                <label> <spring:message code="bookingApartment.feedbacks" /> : ${feedbacks.size()}
                </label>
            </div>
            <div class="actionBox">
                <c:forEach items="${feedbacks}" var="item">
                    <c:if test="${item.enabled}">
                        <c:set var="background" value="bg-danger " />
                        <c:set var="glyphicon" value="glyphicon-thumbs-down text-danger " />
                        <c:if test="${item.rating >= 2.5}">
                            <c:set var="background" value="bg-success " />
                            <c:set var="glyphicon" value="glyphicon-thumbs-up text-success " />
                        </c:if>
                        <div class="${background} well row ">
                            <div class="">
                                <div class="rating col-md-4 ">

                                    <div class="posted-rating-wrapper">
                                        <div class="posted-rating-value"></div>
                                        <div></div>
                                    </div>
                                    <span class=" ${glyphicon} glyphicon"></span> <span>${item.rating}</span>
                                    <div>
                                        <span class="glyphicon glyphicon-user"></span> ${item.feedbacker.nickname}:
                                    </div>
                                </div>
                                <div class="feedback-info-wrapper">
                                    <div>${item.description}</div>
                                </div>
                            </div>
                            <sec:authorize access="hasAuthority('MODERATOR')">
                                <div class="btn-right">
                                    <c:if test="${complainedFeedbacks.contains(item.id)==true}">
                                        <img src="<c:url value='/img/complained.png'/>"
                                            title="<spring:message code='apartmentDetail.complained'/>">
                                    </c:if>
                                    <c:if test="${principalId == apartment.moderator.id}">
                                    <a href="<c:url value='/moderator/deleteFeedback/${item.id}'/>"
                                        class="btn btn-danger"> <spring:message code="delete" />
                                    </a>
                                    </c:if>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="hasAuthority('RENTER')">
                                <div class="btn-right">
                                    <c:if test="${complainedFeedbacks.contains(item.id)==false}">
                                        <a href="<c:url value="/renter/complainFeedback/${item.id}"/>"
                                            class="btn btn-danger"> <spring:message
                                                code="apartmentDetail.complainFeedback" />
                                        </a>
                                    </c:if>
                                    <c:if test="${complainedFeedbacks.contains(item.id)==true}">
                                        <img src="<c:url value='/img/complained.png'/>"
                                            title="<spring:message code='apartmentDetail.complained'/>">
                                    </c:if>
                                </div>
                            </sec:authorize>
                        </div>
                    </c:if>
                </c:forEach>
                <sec:authorize access="isAuthenticated() && hasAuthority('USER') ">
                    <c:if test="${canLeaveFeedback eq true}">
                        <c:url var="actionUrl" value="/tenant/${apartment.id}/feedback" />
                        <form:form modelAttribute="feedback" method="post" action="${actionUrl}" class="feedback-form">
                            <form:hidden path="id" />
                            <div class="form-group">
                                <div class="rating-wrapper">
                                    <div class="current-rating"></div>
                                    <div></div>
                                </div>
                                <form:input path="rating" class="rating-value form-control" value="0"></form:input>
                            </div>
                            <div class="form-group">
                                <spring:message code='bookingApartment.yourFeedback' var="writeFeedback" />
                                <form:input path="description" id="inputDescription" class="form-control"
                                    placeholder="${writeFeedback}..." />
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn   btn-primary" name="add">
                                    <spring:message code="bookingApartment.addFeedback" />
                                </button>
                            </div>
                        </form:form>
                    </c:if>
                </sec:authorize>
            </div>
        </div>
    </div>
    <p id = "days"> </p>
</div>
<style>
.ui-datepicker {
    font-size: 14px;
}

.dp-highlight .ui-state-default {
    background: #0091FF;
    color: #FFF;
}
</style>
<sec:authorize access="isAuthenticated() && hasAuthority('USER') ">
  
 <script>
    calendarFunction("${apartment.id}", "${apartment.price}");
</script>
</sec:authorize>
<script>
	var map;
	var marker;
	var first;
    function loadMapUser(latitude, longitude, myZoom) {
        var latlng = new google.maps.LatLng(latitude, longitude);
        var myOptions = {
            zoom : myZoom,
            center : latlng,
            mapTypeId : google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map($("#map_container")[0], myOptions);
        marker = new google.maps.Marker({
            position : latlng,
            map : map,
            title : $("#apartmentTitle").val()
        });
    }

    $(document).ready(function() {
        first = 1;
        function showMapClicked() {
            if (first == 1) {
                loadMapUser($("#latitude").val(), $("#longitude").val(), 15);
                first = 0;
            } else {
           		map.setCenter(marker.getPosition());
            }
        }

        $("#showMap").click(showMapClicked);
        validFeedback();
    });
    function validFeedback() {
        var messagesMap = getMessages([ "registration.required", "bookingApartment.maxRatingError",
                "bookingApartment.minRatingError", "bookingApartment.validNumber" ]);
        $(".feedback-form").validate({
            rules : {
                rating : {
                    number : true,
                    max : 5,
                    min : 0,
                    required : true
                },

            },
            messages : {
                rating : {
                    required : messagesMap["registration.required"],
                    max : messagesMap["bookingApartment.maxRatingError"],
                    min : messagesMap["bookingApartment.minRatingError"],
                    number : messagesMap["bookingApartment.validNumber"]
                }
            },
            highlight : function(element) {
                $(element).closest('.form-group').addClass("has-error");
            },
            unhighlight : function(element) {
                $(element).closest('.form-group').removeClass("has-error");
            }
        });
    }

    function toggleChevron(e) {
        $(e.target).prev('.panel-heading').find("i.indicator").toggleClass(
                'glyphicon-chevron-down glyphicon-chevron-up');
    }
    

    $('#accordion').on('hidden.bs.collapse', toggleChevron);
    $('#accordion').on('shown.bs.collapse', toggleChevron);
</script>
