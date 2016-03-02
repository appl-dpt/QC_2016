/**
 * Modifications for jquery-ui datepicker
 */

function calendarFunction(apartment_id, price) {
	var arrayDates = [];//save dates array for disabling
	var arrayDatePicker = [];
	var dateFormat = "yy-mm-dd";
	var maxDate = null;
    console.log(apartment_id);

	var today = new Date(); // Get today's date
	var lastDate = new Date(today.getFullYear() + 1, today.getMonth() + 1,
			today.getDate); // To get the 31st Dec of next year
	
    var spring_message = getMessages(["datePicker.localiztion"]);
    var local = spring_message["datePicker.localiztion"];

    $.datepicker.setDefaults( $.datepicker.regional[ local ] );
    
    arrayDates = searchDatesForMonth(apartment_id,moment(moment(new Date(today.getFullYear(), today.getMonth(), 1))).format("YYYY-MM-DD"), 
            moment(moment(new Date(today.getFullYear(), today.getMonth()+1, 0))).format("YYYY-MM-DD"));
     arrayDatePicker = arrayDates;//set read dates, in spite of disable them on datepicker
    
	$("#datepicker")
			.datepicker(
					{
						dateFormat : dateFormat,
						minDate : '0',
						yearRange : '-0:+1',
						maxDate : lastDate,
						onChangeMonthYear : function(year, month) {
							var monthBegin = moment(new Date(year, month-1, 1));
							var monthEnd = moment(new Date(year, month, 0));	
							arrayDates = searchDatesForMonth(apartment_id,moment(monthBegin).format("YYYY-MM-DD"), moment(monthEnd).format("YYYY-MM-DD"));
							if(maxDate == null) {
							arrayDatePicker = arrayDates;//set read dates, in spite of disable them on datepicker
							}
							else {
							    arrayDatePicker = [];  
							}
						},
						beforeShowDay : function(date) {						    
							var string = jQuery.datepicker.formatDate(
									dateFormat, date);
							var date1 = $.datepicker.parseDate(dateFormat, $(
									"#input1").val());
							var date2 = $.datepicker.parseDate(dateFormat, $(
									"#input2").val());

							return [
									arrayDatePicker.indexOf(string) == -1,
									date1
											&& ((date.getTime() == date1
													.getTime()) || (date2
													&& date >= date1 && date <= date2)) ? "dp-highlight"
											: "" ];
						},
						onSelect : function(dateText, inst) {
							var date1 = $.datepicker.parseDate(dateFormat, $(
									"#input1").val());
							var date2 = $.datepicker.parseDate(dateFormat, $(
									"#input2").val());
							
							if (!date1 || date2) {
								$("#input1").val(dateText);
								$("#input2").val("");
								$(this).datepicker("option", "minDate",
										dateText);
								maxDate = searchNearestDay(apartment_id, $("#input1").val());
								console.log("MAX DATE: " + maxDate);
								if(maxDate != null) {
									$(this).datepicker("option", "maxDate",	maxDate);
									arrayDatePicker = [];//enable all dates
									
								}
								$(this).datepicker("option", "minDate",	dateText);
							}  
							
							else {
								$("#input2").val(dateText);
								$(this).datepicker("option", "minDate", today);
								$(this).datepicker("option", "maxDate",	lastDate);
								arrayDatePicker = arrayDates;//disable specific dates again
             					var day1 = moment($("#input1").val());
								var day2 = moment($("#input2").val());
								console.log(day2.diff(day1, 'days'));
								document.getElementById("priceForOrder").innerHTML = "Order: " + price * (day2.diff(day1, 'days'))  + " UAH";
								maxDate = null;//clear max date after choosing endpoint
								
							}
							$("#bookButton").prop("disabled",
									$("td.dp-highlight").length < 2);
						}
					});
}

function searchDatesForMonth(apartment_id, startDate, endDate) {
	 var array1 = [];
	  var token = $("meta[name='_csrf']").attr("content");
      var csrfheader = $("meta[name='_csrf_header']").attr("content");
        
      var URL = /[/][A-z]+[/]/.exec(window.location.pathname) + "tenant/monthDates/" + apartment_id;
      
	 $.ajax({
         url : URL,
         method : "POST",
         data : {
             "_csrf" : token,
             "startDate" : startDate,
             "endDate" : endDate
         },
         async: false

     }).success(function(e) {
    	 array1 = e;
     }).fail(function(e) {
    	 console.log(e); 
	 });
	 return array1;
}

function searchNearestDay(apartment_id, date) {
	var fndDate = null;
	 var token = $("meta[name='_csrf']").attr("content");
     var csrfheader = $("meta[name='_csrf_header']").attr("content");
     var URL = /[/][A-z]+[/]/.exec(window.location.pathname) + "tenant/searchNext/" + apartment_id;

	 $.ajax({
         url : URL,
         method : "POST",
         data : {
             "_csrf" : token,
             "startDate" : date,
         },
         async: false

     }).success(function(e) {
         fndDate = e;
         console.log(e); 
     }).fail(function(e) {
         console.log(e); 
     });
	
	return fndDate;
}
