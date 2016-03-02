$(document).ready(function() {
	function delReservationClicked() {
	    $("#frmIdReserv").val($(this).attr("appId"));
	    $("#frmType").val(0);
		$("#modalConfirmDeleteReservation").modal('show');
	}
	$(".delReservation").click(delReservationClicked);
	
	function delReservationFakeClicked() {
	    $("#frmIdReserv").val($(this).attr("appId"));
	    $("#frmType").val(1);
		$("#modalConfirmDeleteReservation").modal('show');
	}
	$(".fakeReservation").click(delReservationFakeClicked);
	
	function delReservationBadDealClicked() {
        $("#frmIdReserv").val($(this).attr("appId"));
        $("#frmType").val(2);
        $("#modalConfirmDeleteReservation").modal('show');
    }
    $(".badDealReservation").click(delReservationBadDealClicked);
	
	if ($("#infoEvent").val() != '') {
		$("#modalBoxInfo").modal('show');
	}
})