<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<h3>Wait while we procceding your order...</h3>
<script>
	$(document)
			.ready(
					function() {
						var payerId = "${payment.payerId}";
						var accessToken = "${payment.accessToken}";
						var executeUrl = "${payment.executeUrl}";
						var jsonData = JSON.stringify({
							"payer_id" : payerId
						});
						event.preventDefault();
						$
								.ajax({
									type : "POST",
									url : executeUrl,
									headers : {
										"Authorization" : accessToken
									},
									data : jsonData,
									success : function() {
									},
									dataType : "json",
									contentType : "application/json"
								})
								.fail(function(data) {
									console.log(data);
								})
								.done(
										function() {
											window.location = "<c:url value="/tenant/paymentExecuted" />?token=${payment.token}";
										});
					});
</script>