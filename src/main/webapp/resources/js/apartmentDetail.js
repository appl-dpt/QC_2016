var arrayOfExtentions;

function loadMap(latitude, longitude , myZoom) {
    var latlng = new google.maps.LatLng(latitude, longitude);
    var myOptions = {
      zoom: myZoom,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map($("#map_container")[0], myOptions);
    var marker;

	marker = new google.maps.Marker({
		position: latlng,
	    map: map,
	    title:$("#inputName").val()
	});

	if ($("#position").val() == 0) {
		marker.setMap(null);
	}

	function placeMarker(location) {
	    marker.setMap(null);
	    marker = new google.maps.Marker({
	        position: location,
	        map: map
	    });
	    $("#latitude").val(location.lat());
	    $("#longitude").val(location.lng());
	    $("#btnSaveMap").attr("disabled", false);
	}

	google.maps.event.addListener(map, 'click', function(event) {
        placeMarker(event.latLng);
    });
}

function includeExtention() {
    var fileUrl = $("#newPhotoUrl").val(),
    parts, ext = ( parts = fileUrl.split("/").pop().split(".") ).length > 1 ? parts.pop() : "";

    for (var key in arrayOfExtentions) {
        if (ext === arrayOfExtentions[key]) {
            return true;
        }
    }
    return false;
}

function initializeAutocompleteCities() {
    var input = $('#inputCity')[0];
    var options = {
      types: ['(regions)'],
      minLength: 2
    };

    var autocomplete = new google.maps.places.Autocomplete(input, options);

    google.maps.event.addListener(autocomplete, 'place_changed', function() {
      var place = autocomplete.getPlace(); 
      $("#selected").val(1);
      $("#inputCity").focus();
    });

  }
  google.maps.event.addDomListener(window, 'load', initializeAutocompleteCities);
  
$(document).ready( function() {
    arrayOfExtentions = getExtentions();
	if ($("#position").val() != 0) {
	  	  	$('#tabMap').on('shown.bs.tab', function(event){
				loadMap($("#latitude").val(), $("#longitude").val(), 15);
		  	});   	
	} else {
		$("#btnSaveMap").attr("disabled", true);
		$('#tabMap').on('shown.bs.tab', function(event){
		    loadMap($("#latitude").val(), $("#longitude").val(), 12);
		});
	}
	
	function bigImageClicked() {
	    $("#titleBigImage").text($(this).attr("title"));
	    var el=document.getElementById($(this).attr('appId'));
	    $('#imagepreview').attr('src', $(el).attr('src'));
		$('#modalImage').modal('show');
		}
	
	$(".bigImage").click(bigImageClicked);
	
	function delPhotoClicked() {
		$("#frmIdPhoto").val($(this).attr("appId"));
		$("#modalConfirmDeletePhoto").modal('show');
	}
	$(".delPhoto").click(delPhotoClicked);
	
	function saveMapClicked() {
		$("#infoSavingCoord").text('');
		$("#position").val(1);
	}
	$("#btnSaveMap").click(saveMapClicked);
	
	if ($("#infoTab").val() == 'Photos') {
		$("li").removeClass('active');
	    $("#liPhotos").addClass('active');
		$("#apartmentInfo").removeClass('active in');
		$("#photos").addClass('active in');
	}
	
	if ($("#infoTab").val() == 'Map') {
		$("li").removeClass('active');
	    $("#liMap").addClass('active');	
		$("#apartmentInfo").removeClass('active in');
		$("#map").addClass('active in');
		loadMap($("#latitude").val(), $("#longitude").val(), 15);
	}
	
	if ($("#infoTab").val() == 'Conveniences') {
		$("li").removeClass('active');
	    $("#liConveniences").addClass('active');
		$("#apartmentInfo").removeClass('active in');
		$("#conveniences").addClass('active in');
	}
	
	if ($("#infoTab").val() == 'PaymentSettings') {
        $("li").removeClass('active');
        $("#liPaymentSettings").addClass('active');
        $("#apartmentInfo").removeClass('active in');
        $("#paymentSettings").addClass('active in');
    }
	
	if ($("#infoEvent").val() != '') {
		$("#modalBoxInfo").modal('show');
	}
    
    $("#inputCity").keyup(function() {
    	$("#selected").val(0);
    });
    
    if ($("#inputCity").val() != '')
    	$("#selected").val(1);
    
    $(".form-control").keyup(function() {
    	$("#idReset").attr("disabled", false);
    });
    
    //validator add apartment
    var messages = getMessages(["apartmentDetail.emptyName", 
                                "apartmentDetail.emptyCity",
                                "apartmentDetail.emptyAddress",
                                "apartmentDetail.cityFromList",
                                "file.illegal.extention", 
                                "file.name.cantempty",
                                "apartmentDetail.emptyPrice",
                                "apartmentDetail.negativePrice",
                                "apartmentDetail.priceWrongFormat",
                                "apartmentDetail.notChosenPayments"]);
    $.validator.addMethod("validateName",
            function(value, element) {
                return value != "";
            },
            messages["apartmentDetail.emptyName"]);

    $.validator.addMethod("validateCity",
            function(value, element) {
                return value != "";
            },
            messages["apartmentDetail.emptyCity"]);

    $.validator.addMethod("validateCityChoose",
            function(value, element) {
                if ($("#selected").val() == 0)
                    return false;
                else
                    return true;
            },
            messages["apartmentDetail.cityFromList"]);

    $.validator.addMethod("validateAddress",
            function(value, element) {
                return value != "";
            }, 
            messages["apartmentDetail.emptyAddress"]);

    $(".frmApInfo").validate( {
                rules: {
                    name: {
                        validateName : true
                    },
                    city: {
                        validateCity : true,
                        validateCityChoose : true
                    },
                    address: {
                        validateAddress : true
                    }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
           },
           unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
           },
           errorPlacement: function(error, element) {
            error.insertAfter($(element).parents('.divInputContainer'));
           }
     });

  //validator add photo
    $.validator.addMethod("validateEmptyName", 
            function(value, element) {   
                return value != ""; 
            }, 
            messages["file.name.cantempty"]);

    $.validator.addMethod("validateExtention", 
            function(value, element) {
                return includeExtention(); 
            },
            messages["file.illegal.extention"]);

    $(".uploadPhoto").validate( {
                rules: {
                    name: {
                        validateEmptyName : true
                    },
                    file: {
                        validateExtention : true
                    }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
           },
           unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');  
           },
           errorPlacement: function(error, element) {
            error.insertAfter($(element).parents('.divInputContainer'));
           }
     });

    /// for upload localize
    var wrapper = $( ".file_upload" ),
    inp = wrapper.find( "input" ),
    btn = wrapper.find( "button" ),
    lbl = wrapper.find( "div" );
    btn.focus(function(){
        inp.focus();
    });

    inp.focus(function(){
        wrapper.addClass( "focus" );
    }).blur(function(){
        wrapper.removeClass( "focus" );
    });

    btn.add( lbl ).click(function(){
        inp.click();
    });

    btn.focus(function(){
        wrapper.addClass( "focus" );
    }).blur(function(){
        wrapper.removeClass( "focus" );
    });

    var file_api = ( window.File && window.FileReader && window.FileList && window.Blob ) ? true : false;

    inp.change(function(){
        var file_name;
        if( file_api && inp[ 0 ].files[ 0 ] )
            file_name = inp[ 0 ].files[ 0 ].name;
        else
            file_name = inp.val().replace( "C:\\fakepath\\", '' );

        if( ! file_name.length )
            return;

        if( lbl.is( ":visible" ) ){
            lbl.text( file_name );
        }else
            btn.text( file_name );
    }).change();

    $("#inputName").focus();

    (function ($) {
    	  $('.spinner .btn:first-of-type').on('click', function() {
    	    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    	  });
    	  $('.spinner .btn:last-of-type').on('click', function() {
    		  if (parseInt($('.spinner input').val(), 10) > 1)
    			  $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
    	  });
    	})(jQuery);

    $("input.conviniance-unchecked").prop("checked", false);
    $("input.payment-unchecked").prop("checked", false);

  //validator payment settings
    $.validator.addMethod("validateEmptyPrice",
            function(value, element) {
                return value != "";
            },
            messages["apartmentDetail.emptyPrice"]);

    $.validator.addMethod("validateWrongFormatPrice",
            function(value, element) {
                return /^\d+(.\d{2})?$/.test(value);
            },
            messages["apartmentDetail.priceWrongFormat"]);
    
    $.validator.addMethod("validatePayments",
            function(value, element) {
                var chk = false;
                $("#paymentList").find('input').each(function(i,elem) {
                    if ($(elem).prop('checked'))
                        chk = true;
                });
                if (chk == false)
                    return false;
                else
                    return true;
                
            },
            messages["apartmentDetail.notChosenPayments"]);

    $(".updatePaymentSettings").validate( {
        rules: {
            price : {
                validateEmptyPrice : true,
                validateWrongFormatPrice : true
            },
            payments : {
                validatePayments : true
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
           },
           unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
           },
           errorPlacement: function(error, element) {
            error.insertAfter($(element).parents('.divInputContainer'));
           }
     });

});