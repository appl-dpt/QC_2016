function setGetParameter(paramName, paramValue)
{
    var url = window.location.href;
    var hash = location.hash;
    url = url.replace(hash, '');
    if (url.indexOf(paramName + "=") >= 0)
    {
        var prefix = url.substring(0, url.indexOf(paramName));
        var suffix = url.substring(url.indexOf(paramName));
        suffix = suffix.substring(suffix.indexOf("=") + 1);
        suffix = (suffix.indexOf("&") >= 0) ? suffix.substring(suffix.indexOf("&")) : "";
        url = prefix + paramName + "=" + paramValue + suffix;
        console.log(url)
    }
    else
    {
    if (url.indexOf("?") < 0)
        url += "?" + paramName + "=" + paramValue;
    else
        url += "&" + paramName + "=" + paramValue;
    }
    
    window.location.href = url + hash;
}
function footerAligment() {
    $('.footer-placeholder').height($("footer").outerHeight());
}
function cutDescriptionOnIndex() {
    var symbolsToCut = 100;
    var divToCut = $(".index-apartment-info-wrapper .description span");
    divToCut.each(function() {
        $(this).text($(this).text().substring(0, symbolsToCut));
    });
}
function tabHeight() {
    var maxHeight = 0;
    var index = $(".tab-pane.active").index();
    $(".tab-content .tab-pane").each(function() {
        $(this).addClass("active");
        var height = $(this).height();
        maxHeight = height > maxHeight ? height : maxHeight;
        $(this).removeClass("active");
    });
    $(".tab-content .tab-pane:eq(" + index + ")").addClass("active");
    $(".tab-pane").height(maxHeight);
}
function commentRating() {
    var ratingAccuracy = 1;
    var maxMark = 5.0;
    var maxWidth = $(".rating-wrapper").width();
    $(".rating-wrapper").click(function(event) {
        var ratingBarWidth = $(this).width();
        var ratingValueWidth = event.pageX - $(this).offset().left;
        var maxMark = 5.0;
        var realRating = ratingValueWidth * maxMark / ratingBarWidth;
        $(".current-rating").width(ratingValueWidth);
        $(".rating-value").val(realRating.toFixed(ratingAccuracy));
        $(".feedback-form").valid();
    });
    $(".rating-value").change(function() {

        var mark = $(".rating-value").val();
        if(mark<=5 && mark >= 0) {
            var ratingValueWidth = mark / maxMark * maxWidth;
            $(".current-rating").width(ratingValueWidth);
        }
    });
}
function calculateRatings() {
    var maxMark = 5.0;
    $(".rating").each(function() {
        var maxWidth = $(this).find(".posted-rating-wrapper").width();
        var ratingValue = $(this).find("span").text();
        var thisWidth = ratingValue/maxMark * maxWidth;
        $(this).find(".posted-rating-value").width(thisWidth);
    });
}
function menuHighlight(){
    $(".menu-list a").each(function(){
        var href = $(this).attr("href");
        if(window.location.pathname.indexOf(href) !== -1) {
            $(this).closest("li").addClass("active");
        }
    });
}
function initAndResize() {
    footerAligment();
    tabHeight();
    calculateRatings();
}

$(document).ready(function() {
    menuHighlight();
    cutDescriptionOnIndex();
    commentRating();
});
$(window).load(function() {
    initAndResize();
});
$(window).resize(function() {
    initAndResize();
});
function validate111()
{
var pass1 = document.getElementById('password');
var pass2 = document.getElementById('confirm');

if(pass1.value !==" " && pass1.value !== pass2.value){
    $("#myModal").modal();
    return false;
}
}
$(document).ready(function() {
    var messagesL = getMessages([ "registration.required", "registration.maxlength", "registration.minlength",
                                  "registration.email","registration.equalTo","registration.extendPassword",
                                  "registration.whitespaces", "registration.ban"]);
    $.validator.addMethod("extendPassword",function(value,element)
            {
    	        return this.optional(element) || /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{5,}$/i.test(value);
    	    });
    $.validator.addMethod("whitespaces",function(value,element)
    	    {
    	        return this.optional(element) || /^\S+$/i.test(value);
    	    });
    $.validator.addMethod("banDog",function(value,element)
    	    {
    	        return this.optional(element) || /^[А-ЯA-Z]{1}[а-яa-z0-9/s]{2,20}$/i.test(value);
    	    });
    $.validator.addMethod("additionalEmail",function(value,element)
    	    {
    	        return this.optional(element) || /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i.test(value);
    	    });
	$(".registrationForm").validate(
		{
			rules: {
			nickname: {
			required : true,
			minlength : 3,
			maxlength : 50,
			whitespaces : "Y",
			banDog : "Y"
		},
		email: {
			required : true,
			email : true,
			whitespaces : "Y",
			additionalEmail : "Y"
		},
		password: {
			required : true,
			minlength : 5,
			maxlength : 50,
			extendPassword : "Y"
		},
		confirm: {
			required : true,
			minlength : 5,
			equalTo : "#password"
		}
	}, messages: {
		nickname:{
			required : messagesL[ "registration.required"],
			minlength: messagesL[ "registration.minlength" ],
			maxlength: messagesL[ "registration.maxlength" ],
			whitespaces : messagesL[ "registration.whitespaces" ],
			banDog : messagesL[ "registration.ban" ],
		},
		email:{
			required : messagesL[ "registration.required"],
			email : messagesL[ "registration.email"],
			whitespaces : messagesL[ "registration.whitespaces" ],
			additionalEmail : messagesL[ "registration.email"],
		},
		password: {
			required : messagesL[ "registration.required"],
			minlength: messagesL[ "registration.minlength" ],
			maxlength: messagesL[ "registration.maxlength" ],
			extendPassword : messagesL[ "registration.extendPassword" ],
		},
		confirm: {
			required : messagesL[ "registration.required"],
			minlength: messagesL[ "registration.minlength" ],
			equalTo: messagesL[ "registration.equalTo" ]
		}
	},
    highlight: function (element) {
        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
       },
       unhighlight: function (element) {
        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
       },
       errorPlacement: function(error, element) {
        error.insertAfter($(element).parents('.col-xs-2'));
       }
    });
});

$(document).ready(function() {
    var messages = getMessages([ "userSettings.validate.firstname",
                            "userSettings.validate.lastname",
                            "userSettings.validate.nickname",
                            "userSettings.validate.phonenumber",
                            "userSettings.validate.required" ]);
    $.validator.addMethod("firstn",function(value,element)
    {
        return this.optional(element) || /^[А-ЯA-Z]{1}[а-яa-z]{0,20}$/i.test(value);
    },messages["userSettings.validate.firstname"]);
    $.validator.addMethod("lastn",function(value,element)
    {
        return this.optional(element) || /^[А-ЯA-Z]{1}[а-яa-z]{0,20}$/i.test(value);
    },messages["userSettings.validate.lastname"]);
    $.validator.addMethod("nick",function(value,element)
    {
        return this.optional(element) || /^[А-ЯA-Z]{1}[а-яa-z0-9/s]{2,20}$/i.test(value);
    },messages["userSettings.validate.nickname"]);
    $.validator.addMethod("required_",function(value, element)
    {
        if (value==="") {
            return false;
        }
        return true;
    },messages["userSettings.validate.required"]);
    $.validator.addMethod("phoneN",function(value,element)
    {
        return this.optional(element) || /^[0-9]{10}$/i.test(value);
    },messages["userSettings.validate.phonenumber"]);
    $(".userSettings").validate(
        {
	        rules: {
		        firstname: {
		            firstn : "Y"
		        },
		        lastname: {
		            lastn : "Y"
		        },
		        nickname: {
		            required_ : "Y",
		            nick : "Y"
		        },
		        phonenumber: {
		            required_ : "Y",
		            phoneN : "Y"
		        }
    },
	    highlight: function (element) {
	        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	       },
	       unhighlight: function (element) {
	        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
	       },
	       errorPlacement: function(error, element) {
	        error.insertAfter($(element).parents('.col-xs-4'));
	       }
    });
});

$(document).ready(function() {
    var input = $('#inputPhonenumber');
    var checkbox = $('#checkboxVisibility');
    input.keyup(function() {
        checkbox.prop("disabled", $.trim(this.value) === "");
    });
});


$(document).ready(function() {
    $("body button").dblclick(function() {
        alert("Don't do that!!!!!!!!!!!!!!!");
      });
});

$(document).ready(function() {
	var messagesL = getMessages(["registration.required", "registration.whitespaces"]);
    $.validator.addMethod("whitespaces",function(value,element)
    	    {
    	        return this.optional(element) || /^\S+$/i.test(value);
    	    });
	$(".allExtentions").validate(
		{
			rules: {
				extention: {
				      required: true,
					  whitespaces : "Y"
				}
			}, messages: {
					extention: {
					required : messagesL[ "registration.required"],
				    whitespaces : messagesL[ "registration.whitespaces" ]
				}
				},
		    highlight: function (element) {
		        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
		       },
		       unhighlight: function (element) {
		        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		       },
		       errorPlacement: function(error, element) {
		        error.insertAfter($(element).parents('.divInput'));
		       }
		});
});

$(document).ready(function() {
	var messagesL = getMessages([ "registration.required"]);
	$(".allConveniences").validate(
		{
			rules: {
				name: {
					required : true
				},
					}, messages: {
				name:{
					required : messagesL[ "registration.required"],
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

