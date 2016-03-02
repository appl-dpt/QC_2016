<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<header class="bg-info">
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <h1>
                    <a href="<c:url value='/' />" class="logo">
                        <span class="glyphicon glyphicon-map-marker"></span>
                        Booking
                    </a>
                    <small><em><spring:message code="header.motto" /></em></small>
                </h1>
            </div>
            <div class="col-md-4 text-right user-control-wrapper">
                <sec:authorize access="isAnonymous() ">
                    <a class="btn btn-info btn-xs" href="<c:url value="/login" />">
                        <span class="glyphicon glyphicon-log-in"> </span>
                        <spring:message code="login" />
                    </a>
                    <a class="btn btn-info btn-xs" href="<c:url value="/registration" />">
                        <span class="glyphicon glyphicon-download"></span>
                        <spring:message code="header.registration" />
                    </a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated() ">
                    <span>
                        <spring:message code="header.hello"/>,
                        <a href="<c:url value='/userSettings' />" class="user-settings">
                            <sec:authentication property="principal.username" />
                        </a>
                    </span>
                    <c:url var="logoutUrl" value="/logout" />
                    <form:form action="${logoutUrl}" method="post">
                        <button class="btn btn-danger btn-xs" type="submit">
                            <span class="glyphicon glyphicon-log-out"> </span>
                            <spring:message code="header.logout" />
                        </button>
                    </form:form>
                </sec:authorize>
            </div>
            <div class="col-md-12 languages">
                <a href="en">
                    <div class="flag flag-gb" alt="English"></div>
                </a>
                |
                <a href="ua">
                    <div class="flag flag-ua" alt="Ukrainian"></div>
                </a>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function(){
            $(".languages a").click(function(event){
                event.preventDefault();
                $.each(sessionStorage, function(key, value) {
                    if(key.startsWith("locale.")) {
                        sessionStorage.removeItem(key);
                    }
                }); 
                var parameterName = "language";
                var parameterValue = $(this).attr("href");
                setGetParameter(parameterName, parameterValue);
                
            });
        });
    </script>
</header>