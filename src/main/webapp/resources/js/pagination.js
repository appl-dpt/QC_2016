/**
 * Pagination, make submit from <a> </a> as button
 */

$(document).ready(function(){
 	
	var dateFormat = "yy-mm-dd";
	
	/*$("a.submitLink").click(function(){
		var form = $(this).closest("form");
		console.log($(form).attr('action'));
		console.log($(form).attr('modelAttribute'));
		$("#pgName").val($("#nameFilter").val());
		$("#pgStartDate").val($("#startDateFilter").val());
		$("#pgEndDate").val($("#endDateFilter").val());
		form.submit();
	});*/
	
	var dblClick = 0;
	
	
	$("a[btnIndex]").click(function() {
        var index = $(this).attr("btnIndex");
        var $form = $("#paginationForm");
        
        $("#pgName").val($("#nameFilter").val());
        $("#pgStartDate").val($("#startDateFilter").val());
        $("#pgEndDate").val($("#endDateFilter").val());
              
        console.log($form.attr("action") + index);
       if(dblClick++ == 0) $form.attr("action", $form.attr("action") + index);
        $form.submit();
    })
    
    $(function() {
        var spring_message = getMessages(["datePicker.localiztion"]);
        var local = spring_message["datePicker.localiztion"];
        console.log(local);
        
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


/*
    Please consider that the JS part isn't production ready at all, I just code it to show the concept of merging filters and titles together !
    */
    $(document).ready(function(){
        if($("#nameFilter").val() === "" && $("#startDateFilter").val() === "" && $("#endDateFilter").val() === "" &&
                ($("#statusFilter").val() === "ALL_INACTIVE" || $("#statusFilter").val() === "ALL_ACTIVE")) {
            $("#SearchBtn").hide();
            $("input.filter").prop("disabled", true);
            $("#statusLabel").show();
            $("#statusFilter").hide();
        }
        else {
            $("#SearchBtn").show();
            $("#statusLabel").hide();
            $("#statusFilter").show();
        }
        $('.filterable .btn-filter').click(function(){
            var $panel = $(this).parents('.filterable'),
            $filters = $panel.find('.filters input'),
            $tbody = $panel.find('.table tbody');
            if ($filters.prop('disabled') === true) {
                $filters.prop('disabled', false);
                $("#SearchBtn").show();

                $("#statusLabel").hide();
                $("#statusLabel").prop('hidden', true);

                $("#statusFilter").show();
                $filters.first().focus();
            } else {
                var $form = $("#filterReserved");
                $filters.val('').prop('disabled', true);

                $("#SearchBtn").hide();
                $('#SearchBtn').prop('hidden', true);

                $("#statusFilter").hide();
                $("#statusFilter").prop('hidden', true);
                $("#statusFilter").val($("#statusFilter option:first").val());

                $("#statusLabel").show();
                $form.submit();

                $tbody.find('.no-result').remove();
                $tbody.find('tr').show();
            }
        });
    })