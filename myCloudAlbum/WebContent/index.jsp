<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8"/>
	<title>BlueMix Cloud Photo Album</title>
	<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css"  type="text/css"/>
	<link rel="stylesheet" href="css/demos.css"  type="text/css"/>
	<link rel="stylesheet" href="css/mystyle.css" type="text/css"/>
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script src="js/ui/jquery.ui.mouse.js"></script>
	<script src="js/ui/jquery.ui.button.js"></script>
	<script src="js/ui/jquery.ui.draggable.js"></script>
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.resizable.js"></script>
	<script src="js/ui/jquery.ui.button.js"></script>
	<script src="js/ui/jquery.ui.dialog.js"></script>
	<script src="js/ui/jquery.ui.effect.js"></script>
	
	<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
	<script>
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
			tips
				.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}

/* 		function checkSame( o1, o2 ) {
			if ( o1.val() != o2.val() ) {
				o1.addClass( "ui-state-error" );
				o2.addClass( "ui-state-error" );
				updateTips( "The twice passwords are not same." );
				return false;
			} else {
				return true;
			}
		} */

		function checkLength( o, n, min, max ) {
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-state-error" );
				updateTips( "Length of " + n + " must be between " +
					min + " and " + max + "." );
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
					bValid = bValid && checkLength( profile_pwd1, "profile_pwd1", 5, 16 );
					bValid = bValid && checkRegexp( profile_pwd1, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

					bValid = bValid && checkLength( profile_pwd2, "profile_pwd2", 5, 16 );
					bValid = bValid && checkRegexp( profile_pwd2, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
					
					bValid = bValid && checkSame(profile_pwd1, profile_pwd2);
			
					if ( bValid ) {
						//$( this ).dialog( "close" );
						var form = document.getElementById("profile_form");
						form.submit();
					}
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
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

					bValid = bValid && checkLength( sign_in_mail, "sign_in_mail", 3, 16 );
					bValid = bValid && checkLength( sign_in_pwd, "sign_in_pwd", 5, 16 );
					
					// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
					bValid = bValid && checkRegexp( sign_in_mail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
					bValid = bValid && checkRegexp( sign_in_pwd, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
			
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
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});

		$("a[name='create-user']").click(function(){
			$("#register_div" ).dialog( "open" );
		});
		$("a[name='sign-in']").click(function(){
			$("#sign_in_div" ).dialog( "open" );
		});
		$("a[name='profile']").click(function(){
			$("#profile_div" ).dialog( "open" );
		});
		$("a[name='sign-out']").click(function(){
			$("#sign_out_form" ).submit();
		});
	});
	</script>
</head>
<body>

 
<div class="main">
	<div id="register_div" title="Create new user">
		* Fields are required
		<form method ="post" action="RegisterServlet" name="user_register" id="register_form">
		<fieldset>
			<label for="name">*Name</label>
			<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" />
			<label for="email">*Email</label>
			<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
			<label for="password">*Password</label>
			<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
			<label for="userMobile">Mobile</label>
			<input type="text" name="userMobile" id="userMobile" class="text ui-widget-content ui-corner-all" />
			<label for="userHomePhone">Your Home Phone</label>
			<input type="text" name="userHomePhone" id="userHomePhone" class="text ui-widget-content ui-corner-all" />
			<label for="userNote">Leave a Note here</label>
			<input type="text" name="userNote" id="userNote" class="text ui-widget-content ui-corner-all" />
			<label for="userQQ">Your QQ</label>
			<input type="text" name="userQQ" id="userQQ" class="text ui-widget-content ui-corner-all" />
			<label for="userSelfDescription">Leave self description for your friends:</label>
			<input type="text" name="userSelfDescription" id="userSelfDescription" class="text ui-widget-content ui-corner-all" />
		</fieldset>
		</form>
	  </div>
	  <div id="sign_out_div">
		<form method ="post" action="SignInOutServlet" name="sign_out" id="sign_out_form">
			<input type="hidden" id="sign_out_flg" value="1"/>
		</form>
	  </div>
	  <div id="profile_div" title="Edit Profile">
		<form method ="post" action="ProfileServlet" name="user_profile" id="profile_form">
		<fieldset>
			<label for="name">Name</label>
			<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" readonly="true"/>
			<label for="email">Email</label>
			<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all"  readonly="true"/>
			<input type="hidden" name="password" id="password"/>
			<label for="password">Password</label>
			<input type="password" name="profile_pwd1" id="profile_pwd1" value="" class="text ui-widget-content ui-corner-all" />
			<label for="password">Password&nbsp;-&nbsp;again</label>
			<input type="password" name="profile_pwd2" id="profile_pwd2" value="" class="text ui-widget-content ui-corner-all" />
			<label for="userMobile">Mobile</label>
			<input type="text" name="userMobile" id="userMobile" class="text ui-widget-content ui-corner-all" />
			<label for="userHomePhone">Your Home Phone</label>
			<input type="text" name="userHomePhone" id="userHomePhone" class="text ui-widget-content ui-corner-all" />
			<label for="userNote">Leave a Note here</label>
			<input type="text" name="userNote" id="userNote" class="text ui-widget-content ui-corner-all" />
			<label for="userQQ">Your QQ</label>
			<input type="text" name="userQQ" id="userQQ" class="text ui-widget-content ui-corner-all" />
			<label for="userSelfDescription">Leave self description for your friends:</label>
			<input type="text" name="userSelfDescription" id="userSelfDescription" class="text ui-widget-content ui-corner-all" />
		</fieldset>
		</form>
	  </div>
	  <div id="sign_in_div" title="User sign in">
		* Fields are required
		<form method ="post" action="SignInOutServlet" name="user_sign_in" id="sign_in_form">
		<fieldset>
			<label for="sign_in_mail">*Your email</label>
			<input type="text" name="sign_in_mail" id="sign_in_mail" class="text ui-widget-content ui-corner-all" />
			<label for="sign_in_pwd">*Password</label>
			<input type="password" name="sign_in_pwd" id="sign_in_pwd" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
		</form>
	  </div>
	
  <div class="header">
  	<div class="header_resize">
      <div class="logo"><h1><a href="index.jsp"><img src="css/images/App_logo.png"></img></h1></div>
      <div class="menu_nav">
      	  <ul>
          <li class="active"><a href="index.html"><span><span>Home</span></span></a></li>
          <li><a href="DemoHubServlet"><span><span>Application demos hub</span></span></a></li>
          <li><a href="contact.html"><span><span>Contact Us</span></span></a></li>
          <%if (session.getAttribute("current_user") == null) {%>
          <li><a name="create-user" href="#"><span><span>Register</span></span></a></li>
          <li><a name="sign-in" href="#"><span><span>Sign In</span></span></a></li>
          <%} else {%>
          <li><a name="profile" href="#"><span><span>Profile</span></span></a></li>
          <li><a name="sign-out" href="#"><span><span>Sign Out</span></span></a></li>
          <%}%>
        </ul>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="content">
    <div class="content_resize">
      <div class="mainbar">
        <div class="article">
        <form method ="post" action="BlueMixCounterServlet" name="user_register" id="user_register">
		
        <% 
        	String successMsg = (String)session.getAttribute("success_msg");
        	String errorMsg = (String)session.getAttribute("error_msg");
        	if(successMsg != null){
        %>
         	 <h3><%=successMsg%></h3>
        <%
        	 	session.removeAttribute("success_msg");
        	}
        	
        	if(errorMsg != null){
        %>
        	<h3><%=errorMsg%></h3>
        <% 		
        		session.removeAttribute("error_msg");
        	}
        %>
         
         <h2>Note, supported broswer type:</h2>
         <p>Firefox 24 or above</p>
         <p>Chrome 35 or above </p>
         <p>Doesn't support IE, due to specfic javascript function limitation.</p>
         <h2>Walmly welcome you to use the cloud photo Album!</h2><div class="clr"></div>
         
         <p> What can you do from the Cloud photo Album?.</p>
         <p> 1. Register as an user of Cloud photo Album.  </p>
         <p> 2. Upload your faviorte photoes to your Cloud photo Album. </p>
         <p> 3. Reivew/check your faviorte photoes as you wish.  </p>
         <p> 4. Move your favirote photoes to your prefered Album.</p>
         <p> 5. Share your photoes to your friends with messages. </p>
        
         <h2>About the Cloud photo Album</h2><div class="clr"></div>
          <p>Posted by <a href="#">Shawn </a> &nbsp;|&nbsp; dengs@cn.ibm.com <a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communitystart?communityUuid=49e61b3c-60da-4739-a63a-fcc5a0b62fd5">China GDC AIS service line</a>, <a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communitystart?communityUuid=ee2ef5b2-f52b-424a-b955-cf34d41156a3">China GDC AIS Cloud API community</a></p>
          <img src="css/images/banner3.png" width="613" height="154" alt="image" />
          <p>This Web site is created by dengs@cn.ibm.com as a cloud album.</p>
          <p><b>The technology overview:</b></p>
          <p>(1). Using jquery validation and jquery core for front end UI.</p>
          <p>(2). Using J2ee servlet 1.3 as controller.</p>
          <p>(3). Using self created SimplerJDBCHelper as perstistence framework for basic CRUD operation as well as leveraging the BlueMix DB2 service.</p>
          <p>(4). Using 3rd party data modeling tool for creating logic/physical data model, then generated DDL, finally execute the DDL through IBM DB2 BlueMix web console for creating tables.</p>
        </div>

      </div>
      <div class="sidebar">
        <div class="gadget">
         
        </div>
        <div class="gadget">
          <h2 class="star"><span>References</span></h2><div class="clr"></div>
          <ul class="sb_menu">
            <li><a href="#" title="Ready for learning from guide">Learn more from the development guide,</a><br /> A serials guide based on blueMix to create cloud API apps</li>
            <li><a href="#" title="Want to be technical author of cloud"> China GDC AIS cloud API community.</a><br /> A platform for you to build up the truly cloud hands on skills.</li>
            <li><a href="#" title="Crowd sourcing program">What is Cloud API crowd sourcing program?</a><br />Are you ready for creating/reusing an API.</li>
          </ul>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>

  <div class="fbg">
    <div class="fbg_resize">
      <div class="col c1">
        <h2><span>About</span></h2>
        <img src="css/images/white.jpg" width="56" height="56" alt="pix" />
        <p>BlueMix and Cloud API learning is China GDC AIS service line key initiatives. Kindly learn more from China GDC AIS cloud API community <a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communitystart?communityUuid=ee2ef5b2-f52b-424a-b955-cf34d41156a3">Learn more...</a></p>
      </div>
      <div class="col c2">
        <h2><span>Learn more</span></h2>
        <ul class="sb_menu">
          <li><a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communitystart?communityUuid=06a2c4b6-7ca2-4b36-b4c2-ae83a4a63716"> China GDC AIS BlueMix Community</a></li>
          <li><a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communitystart?communityUuid=0e4495a0-c36f-4dd4-9cda-f97900baadd0"> IBM Cloud community</a></li>
          <li><a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communityview?communityUuid=d7c4cf37-a0a9-4fe3-a253-503ad90080d2"> GBS Global Cloud community</a></li>
          <li><a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communityview?communityUuid=6dc12080-88cc-48ef-81da-1fc78737075d"> IBM sales force community</a></li>
          <li><a target="blank" href="https://w3-connections.ibm.com/communities/service/html/communityview?communityUuid=8223bf5a-697d-4c73-9b0d-d63e844bf7a6">GCG smarter cloud community</a></li>
          <li><a target="blank" href="http://ibm.biz/ibmrockstar">IBM cloud rock star community</a></li>
        </ul>
      </div>
      <div class="col c3">
      <ul class="sb_menu">
        <h2>Contact</h2>
        <p>If you have any questions regarding the design/development issues, please contact us in Cloud API community.</p>
        <p>Address: China, LiaoNing, DaLian city, HuangNiChuan software park, JinYang building</p>
       </ul>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="footer">
    <div class="footer_resize">
      <p class="lf"></p>
     </div>
  </div>
 </div>
</body>
</html>
