<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<tiles:importAttribute name="javascripts" />
<tiles:importAttribute name="stylesheets" />
<!DOCTYPE html>
<html>
<head>
<sec:csrfMetaTags />
<link rel="shortcut icon" href="<c:url value="/img/favicon.ico"/>" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html;" charset="UTF-8">
<c:forEach var="cssValue" items="${stylesheets}">
    <link rel="stylesheet" href="<c:url value="${cssValue}" />" />
</c:forEach>
<c:forEach var="jsValue" items="${javascripts}">
    <script src="<c:url value="${jsValue}"/>"></script>
</c:forEach>
<script>
    function getMessages(codes) {
        var result = new Map();
        var newCodes = new Array();
        codes.forEach(function (value, i) {
            if(sessionStorage.getItem("locale." + value)) {
                result[value] = sessionStorage.getItem("locale." + value);
            } else {
                newCodes.push(value); 
            }
        });
        if(newCodes.length != 0) {
            $.ajax({
                type : "GET",
                url : "<c:url value="/locale"/>",
                dataType : "text",
                data : {
                    "codes" : newCodes
                },
                success : function(msg) {
                    var messagesMap = JSON.parse(msg);
                    $.each(messagesMap, function(key, value){
                        sessionStorage["locale." + key] = value;
                        result[key] = value;
                    });
                },
                async : false
            });
        }
        return result;
    }

    function getExtentions() {
        var result;
        $.ajax({
            type : "GET",
            url : "<c:url value="/renter/extentions"/>",
            dataType : "text",
            success : function(msg) {
                result = msg
            },
            async : false
        });
        return JSON.parse(result);
    }
    
    function getApartmentsLocation() {
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "<c:url value="/moderator/allApartments" />",
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				read(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
</script>
<title><tiles:insertAttribute name="title" /></title>
</head>
<body>
    <div class="page">
        <tiles:insertAttribute name="header" />
        <div class="content container">
            <div class="col-md-2">
                <tiles:insertAttribute name="menu" />
            </div>
            <div class="col-md-10">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
        <tiles:insertAttribute name="footer" />
    </div>
</body>
</html>