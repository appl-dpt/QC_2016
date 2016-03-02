/**
 * Pagination, make submit from <a> </a> as button
 */

$(document).ready(function(){

    var dateFormat = "yy-mm-dd";
    
	$("a[btnIndex]").click(function() {
        var index = $(this).attr("btnIndex");
        var $form = $("#paginationIndexForm");
        
        $("#pgCity").val($("#cityFilter").val());
        $("#pgStartDate").val($("#startDateFilter").val());
        $("#pgEndDate").val($("#endDateFilter").val());
        $("#pgStartPrice").val($("#startPriceFilter").val());
        $("#pgEndPrice").val($("#endPriceFilter").val());
        $("#pgStartRating").val($("#startRatingFilter").val());
        $("#pgEndRating").val($("#endRatingFilter").val());
        $("#pgName").val($("#nameFilter").val());
        $("#pgSortBy").val($("#sortByFilter").val());
        
        console.log($form.attr("action") + index);
        $form.attr("action", $form.attr("action") + index);
        $form.submit();
    })
    
    $(function() {
        var spring_message = getMessages(["datePicker.localiztion"]);
        var local = spring_message["datePicker.localiztion"];
        
        $.datepicker.setDefaults( $.datepicker.regional[ local ] );
        
        $("#startDateFilter").datepicker(
                {
                    dateFormat : dateFormat
                    }
        );
        $("#endDateFilter").datepicker(
                {
                    dateFormat : dateFormat
                    }
        );
      });
})

function initializeAutocompleteCities() {
    var input = $('#cityFilter')[0];
    var options = {
      types: ['(regions)'],
      minLength: 2
    };

    var autocomplete = new google.maps.places.Autocomplete(input, options);

    google.maps.event.addListener(autocomplete, 'place_changed', function() {
      var place = autocomplete.getPlace(); 
      $("#selected").val(1);
      $("#cityFilter").focus();
    });

  }
  google.maps.event.addDomListener(window, 'load', initializeAutocompleteCities);

  function getVals(){
      // Get slider values
      var parent = this.parentNode;
      var slides = parent.getElementsByTagName("input");
        var slide1 = parseFloat( slides[0].value );
        var slide2 = parseFloat( slides[1].value );
        
      // Neither slider will clip the other, so make sure we determine which is larger
      if( slide1 > slide2 ){ var tmp = slide2; slide2 = slide1; slide1 = tmp; }
      
      var displayElement = parent.getElementsByClassName("rangeValues")[0];
          displayElement.innerHTML = slide1 + " - " + slide2;
    }

    window.onload = function(){
      // Initialize Sliders
      var sliderSections = document.getElementsByClassName("range-slider");
          for( var x = 0; x < sliderSections.length; x++ ){
            var sliders = sliderSections[x].getElementsByTagName("input");
            for( var y = 0; y < sliders.length; y++ ){
              if( sliders[y].type ==="range" ){
                sliders[y].oninput = getVals;
                // Manually trigger event first time to display values
                sliders[y].oninput();
              }
            }
          }
    }