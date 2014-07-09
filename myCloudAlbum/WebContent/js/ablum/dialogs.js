$(function() {
	var name = $( "#name" ),
		email = $( "#email" ),
		password = $( "#password" ),
		profile_pwd1 = $( "#profile_pwd1" ),
		profile_pwd2 = $( "#profile_pwd2" ),
		sign_in_mail = $( "#sign_in_mail" ),
		sign_in_pwd = $( "#sign_in_pwd" ),
		allFields = $( [] ).add( name ).add( email ).add( password ).add(profile_pwd1).add(profile_pwd2).add(sign_in_mail).add(sign_in_pwd),
		tips = $( ".validateTips" );

	function updateTips( t ) {
		tips.text( t ).addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}

	function checkRequired( o, n ) {
		if ( o.val().trim() == "" ) {
			o.addClass( "ui-state-error" );
			updateTips( n + " is required." );
			return false;
		} else {
			return true;
		}
	}

	function checkSame( o1, o2 ) {
		if ( o1.val() != o2.val() ) {
			o2.addClass( "ui-state-error" );
			updateTips( "The twice passwords are not same." );
			return false;
		} else {
			return true;
		}
	}

	function checkLength( o, n, min, max ) {
		if ( o.val().length > max || o.val().length < min ) {
			o.addClass( "ui-state-error" );
			updateTips( "Length of " + n + " must be between " + min + " and " + max + "." );
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			updateTips( n );
			return false;
		} else {
			return true;
		}
	}
	
	$( "#register_div" ).dialog({
		autoOpen: false,
		height: 500,
		width: 350,
		modal: true,
		buttons: {
			"Create an account": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );

				bValid = bValid && checkLength( name, "username", 3, 16 );
				bValid = bValid && checkLength( email, "email", 6, 80 );
				bValid = bValid && checkLength( password, "password", 5, 16 );
				
				bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
				// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
				bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
				bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
				
		
				if ( bValid ) {
					//$( this ).dialog( "close" );
					var form = document.getElementById("register_form");
					form.submit();
				}
			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			tips.text( "" );
			allFields.val( "" ).removeClass( "ui-state-error" );
			
		}
	});

	$( "#profile_div" ).dialog({
		autoOpen: false,
		height: 500,
		width: 350,
		modal: true,
		buttons: {
			"Save": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );
				bValid = bValid && checkLength( profile_pwd1, "Password", 5, 16 );
				bValid = bValid && checkRegexp( profile_pwd1, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

				bValid = bValid && checkLength( profile_pwd2, "Password", 5, 16 );
				bValid = bValid && checkRegexp( profile_pwd2, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
				
				bValid = bValid && checkSame(profile_pwd1, profile_pwd2);
		
				if ( bValid ) {
					//$( this ).dialog( "close" );
					$("#profile_pwd").val($("#profile_pwd1").val());
					var form = document.getElementById("profile_form");
					form.submit();
				}
			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			tips.text( "" );
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	$( "#sign_out_div" ).ready(function (){
		$(this).css("visible", "false");
	});
	$( "#sign_in_div" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			"Sign in": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );

				bValid = bValid && checkRequired( sign_in_mail, "email" );
				bValid = bValid && checkRequired( sign_in_pwd, "password" );
				
				if ( bValid ) {
					//$( this ).dialog( "close" );
					var form = document.getElementById("sign_in_form");
					form.submit();
				}
			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			tips.text( "" );
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});

	$("a[id='create-user']").click(function(){
		$("#register_div" ).dialog( "open" );
	});
	$("a[id='sign-in']").click(function(){
		$("#sign_in_div" ).dialog( "open" );
	});
	$("a[id='profile']").click(function(){
		$("#profile_div" ).dialog( "open" );
	});
	$("a[id='sign-out']").click(function(){
		$("#sign_out_flg" ).val("1");
		$("#sign_out_form" ).submit();
	});
});