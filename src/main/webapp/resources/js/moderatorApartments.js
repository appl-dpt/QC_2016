$(document).ready(function() {
    
    function disableApartmentClicked() {
        $("#frmIdApartment").val($(this).attr("appId"));
        $("#modalConfirmDisablingApartment").modal('show');
    }
    $(".disableApartment").click(disableApartmentClicked);
    
    function enableApartmentClicked() {
        $("#frmIdApartment").val($(this).attr("appId"));
        $("#modalConfirmEnablingApartment").modal('show');
    }
    $(".enableApartment").click(enableApartmentClicked);
    
    function assignApartmentClicked() {
        $("#frmA").attr('href', ($("#frmA").attr('href') + $(this).attr("appId")));
        $("#modalConfirmAssigningApartment").modal('show');
    }
    $(".assignApartment").click(assignApartmentClicked);
    
    function approveApartmentClicked() {
        $("#frmA").attr('href', ($("#frmA").attr('href') + $(this).attr("appId")));
        $("#modalConfirmApprovingApartment").modal('show');
    }
    $(".approveApartment").click(approveApartmentClicked);
    
    if ($("#infoEvent").val() != '') {
        $("#modalBoxInfo").modal('show');
    }
    
})