<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>BlueMix Cloud Photo Album</title>
	<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css"  type="text/css"/>
	<link rel="stylesheet" href="css/demos.css"  type="text/css"/>
	<link rel="stylesheet" href="css/mystyle.css" type="text/css"/>
	<link rel="stylesheet" href="css/jquery.dataTables.css" type="text/css"/>
	<link rel="stylesheet" href="css/dataTables.autoFill.css" type="text/css"/>
	
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
	<script src="js/ui/jquery.ui.sortable.js"></script>
	<script src="js/ui/jquery.ui.accordion.js"></script>
	<script src="js/ui/jquery.ui.effect.js"></script>
	<script src="js/ui/jquery.ui.sortable.js"></script>
	<script src="js/ui/jquery.ui.accordion.js"></script>
	<script src="js/jquery.dataTables.js"></script>
	<script src="js/dataTables.autoFill.js"></script>
	
	
	
	<style>
	/* IE has layout issues when sorting (see #5413) */
	.group { zoom: 1 }
	</style>
	<script>
	$(function() {
		$( "#accordion" )
			.accordion({
				header: "> div > h3"
			})
			.sortable({
				axis: "y",
				handle: "h3",
				stop: function( event, ui ) {
					// IE doesn't register the blur when sorting
					// so trigger focusout handlers to remove .ui-state-focus
					ui.item.children( "h3" ).triggerHandler( "focusout" );
				}
			});
	});
	
	$(document).ready(function() {
	    var table = $('#example').DataTable();
	    new $.fn.dataTable.AutoFill( table );
	} );
	$(function(){  
	    var btnUpload=$('#upload');  
	    var status=$('#status');  
	    new AjaxUpload(btnUpload, {  
	        action: 'upload-file.php',  
	        //Name of the file input box  
	        name: 'uploadfile',  
	        onSubmit: function(file, ext){  
	            if (! (ext && /^(jpg|png|jpeg|gif)$/.test(ext))){   
	                  // check for valid file extension   
	                status.text('Only JPG, PNG or GIF files are allowed');  
	                return false;  
	            }  
	            status.text('Uploading...');  
	        },  
	        onComplete: function(file, response){  
	            //On completion clear the status  
	            status.text('');  
	            //Add uploaded file to list  
	            if(response==="success"){  
	                $('<li></li>').appendTo('#files').html('<img src="./uploads/'+file+'" alt="" /><br />'+file).addClass('success');  
	            } else{  
	                $('<li></li>').appendTo('#files').text(file).addClass('error');  
	            }  
	        }  
	    });  
	});
	</script>
</head>
<body>

 
<div class="main">
  <div class="header">
  	<div class="header_resize">
      <div class="logo"><h1><a href="index.jsp"><img src="css/images/App_logo.png"></img></h1></div>
      <div class="menu_nav">
      	  <ul>
          <li class="active"><a href="index.html"><span><span>Home</span></span></a></li>
          <li><a href="DemoHubServlet"><span><span>Application demos hub</span></span></a></li>
          <li><a href="contact.html"><span><span>Contact Us</span></span></a></li>
          <li><a name="create-user"><span><span><sm>Register</sm></span></span></li>
          <li><a name="sign-in"><span><span><sn>Sign In</sn></span></span></a></li>
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
         
     <div id="accordion">
		<div class="group">
		<h3>Add friends</h3>
		<div>
			<table id="example" class="display" cellspacing="0" width="100%">
    		<thead>
     		<tr>
	            <th>Name</th>
	            <th>Friend email</th>
	            <th>Mobile</th>
	            <th>action</th>
	            
        	</tr>
    		</thead>
   		    <tfoot>
      	    <tr>
	            <th>Name</th>
	            <th>Friend email</th>
	            <th>mobile</th>
	            <th>action</th>
            </tr>
   		   </tfoot>
 		 <tbody>
        <tr>
            <td>aTiger Ni</td>
            <td>tiger@cn.ibm.com</td>
            <td>12345555</td>
            <td><input type="button" name="add" value="add"></input></td>
        </tr>
        <tr>
            <td>aTGarrett Winters</td>
            <td>Accountant</td>
            <td>Tokyo</td>
            <td><input type="button" name="add" value="add"></input></td>
            
        </tr>
        <tr>
            <td>aAshton Cox</td>
            <td>Junior Technical Author</td>
            <td>San Francisco</td>
            <td><input type="button" name="remove" value="remove"></input></td>
           
        </tr>
        <tr>
            <td>aCedric Kelly</td>
            <td>Senior Javascript Developer</td>
            <td>Edinburgh</td>
            <td><input type="button" name="remove" value="remove"></input></td>
            
        </tr>
        <tr>
            <td>Airi Satou</td>
            <td>Accountant</td>
            <td>Tokyo</td>
            <td><input type="button" name="remove" value="remove"></input></td>
       
        </tr>
        <tr>
            <td>aBrielle Williamson</td>
            <td>Integration Specialist</td>
            <td>New York</td>
          	<td><input type="button" name="remove" value="remove"></input></td>
       
          
        </tr>
        <tr>
            <td>aHerrod Chandler</td>
            <td>Sales Assistant</td>
            <td>San Francisco</td>
            <td><input type="button" name="remove" value="remove"></input></td>
        </tr>
        <tr>
            <td>aRhona Davidson</td>
            <td>Integration Specialist</td>
            <td>Tokyo</td>
            <td><input type="button" name="remove" value="remove"></input></td>
        </tr>
        <tr>
            <td>aColleen Hurst</td>
            <td>Javascript Developer</td>
            <td>San Francisco</td>
      		<td><input type="button" name="remove" value="remove"></input></td>
        </tr>
        <tr>
            <td>Sonya Frost</td>
            <td>Software Engineer</td>
            <td>Edinburgh</td>
            <td>23</td>
            <td>2008/12/13</td>
            <td>$103,600</td>
        </tr>
        <tr>
            <td>Jena Gaines</td>
            <td>Office Manager</td>
            <td>London</td>
            <td>30</td>
            <td>2008/12/19</td>
            <td>$90,560</td>
        </tr>
        <tr>
            <td>Quinn Flynn</td>
            <td>Support Lead</td>
            <td>Edinburgh</td>
            <td>22</td>
            <td>2013/03/03</td>
            <td>$342,000</td>
        </tr>
        <tr>
            <td>Charde Marshall</td>
            <td>Regional Director</td>
            <td>San Francisco</td>
            <td>36</td>
            <td>2008/10/16</td>
            <td>$470,600</td>
        </tr>
        <tr>
            <td>Haley Kennedy</td>
            <td>Senior Marketing Designer</td>
            <td>London</td>
            <td>43</td>
            <td>2012/12/18</td>
            <td>$313,500</td>
        </tr>
        <tr>
            <td>Tatyana Fitzpatrick</td>
            <td>Regional Director</td>
            <td>London</td>
            <td>19</td>
            <td>2010/03/17</td>
            <td>$385,750</td>
        </tr>
        <tr>
            <td>Michael Silva</td>
            <td>Marketing Designer</td>
            <td>London</td>
            <td>66</td>
            <td>2012/11/27</td>
            <td>$198,500</td>
        </tr>
        <tr>
            <td>Paul Byrd</td>
            <td>Chief Financial Officer (CFO)</td>
            <td>New York</td>
            <td>64</td>
            <td>2010/06/09</td>
            <td>$725,000</td>
        </tr>
        <tr>
            <td>Gloria Little</td>
            <td>Systems Administrator</td>
            <td>New York</td>
            <td>59</td>
            <td>2009/04/10</td>
            <td>$237,500</td>
        </tr>
        <tr>
            <td>Bradley Greer</td>
            <td>Software Engineer</td>
            <td>London</td>
            <td>41</td>
            <td>2012/10/13</td>
            <td>$132,000</td>
        </tr>
        <tr>
            <td>Dai Rios</td>
            <td>Personnel Lead</td>
            <td>Edinburgh</td>
            <td>35</td>
            <td>2012/09/26</td>
            <td>$217,500</td>
        </tr>
        <tr>
            <td>Jenette Caldwell</td>
            <td>Development Lead</td>
            <td>New York</td>
            <td>30</td>
            <td>2011/09/03</td>
            <td>$345,000</td>
        </tr>
        <tr>
            <td>Yuri Berry</td>
            <td>Chief Marketing Officer (CMO)</td>
            <td>New York</td>
            <td>40</td>
            <td>2009/06/25</td>
            <td>$675,000</td>
        </tr>
        <tr>
            <td>Caesar Vance</td>
            <td>Pre-Sales Support</td>
            <td>New York</td>
            <td>21</td>
            <td>2011/12/12</td>
            <td>$106,450</td>
        </tr>
        <tr>
            <td>Doris Wilder</td>
            <td>Sales Assistant</td>
            <td>Sidney</td>
            <td>23</td>
            <td>2010/09/20</td>
            <td>$85,600</td>
        </tr>
        <tr>
            <td>Angelica Ramos</td>
            <td>Chief Executive Officer (CEO)</td>
            <td>London</td>
            <td><input type="button" name="remove" value="remove"></input></td>
          
        </tr>
        <tr>
            <td>Gavin Joyce</td>
            <td>Developer</td>
            <td>Edinburgh</td>
            <td>42</td>
            <td>2010/12/22</td>
            <td>$92,575</td>
        </tr>
        <tr>
            <td>Jennifer Chang</td>
            <td>Regional Director</td>
            <td>Singapore</td>
            <td>28</td>
            <td>2010/11/14</td>
            <td>$357,650</td>
        </tr>
        <tr>
            <td>Brenden Wagner</td>
            <td>Software Engineer</td>
            <td>San Francisco</td>
            <td>28</td>
            <td>2011/06/07</td>
            <td>$206,850</td>
        </tr>
        <tr>
            <td>Fiona Green</td>
            <td>Chief Operating Officer (COO)</td>
            <td>San Francisco</td>
            <td>48</td>
            <td>2010/03/11</td>
            <td>$850,000</td>
        </tr>
        <tr>
            <td>Shou Itou</td>
            <td>Regional Marketing</td>
            <td>Tokyo</td>
            <td>20</td>
            <td>2011/08/14</td>
            <td>$163,000</td>
        </tr>
        <tr>
            <td>Michelle House</td>
            <td>Integration Specialist</td>
            <td>Sidney</td>
            <td>37</td>
            <td>2011/06/02</td>
            <td>$95,400</td>
        </tr>
        <tr>
            <td>Suki Burks</td>
            <td>Developer</td>
            <td>London</td>
            <td>53</td>
            <td>2009/10/22</td>
            <td>$114,500</td>
        </tr>
        <tr>
            <td>Prescott Bartlett</td>
            <td>Technical Author</td>
            <td>London</td>
            <td>27</td>
            <td>2011/05/07</td>
            <td>$145,000</td>
        </tr>
        <tr>
            <td>Gavin Cortez</td>
            <td>Team Leader</td>
            <td>San Francisco</td>
            <td>22</td>
            <td>2008/10/26</td>
            <td>$235,500</td>
        </tr>
        <tr>
            <td>Martena Mccray</td>
            <td>Post-Sales support</td>
            <td>Edinburgh</td>
            <td>46</td>
            <td>2011/03/09</td>
            <td>$324,050</td>
        </tr>
        <tr>
            <td>Unity Butler</td>
            <td>Marketing Designer</td>
            <td>San Francisco</td>
            <td>47</td>
            <td>2009/12/09</td>
            <td>$85,675</td>
        </tr>
        <tr>
            <td>Howard Hatfield</td>
            <td>Office Manager</td>
            <td>San Francisco</td>
            <td>51</td>
            <td>2008/12/16</td>
            <td>$164,500</td>
        </tr>
        <tr>
            <td>Hope Fuentes</td>
            <td>Secretary</td>
            <td>San Francisco</td>
            <td>41</td>
            <td>2010/02/12</td>
            <td>$109,850</td>
        </tr>
        <tr>
            <td>Vivian Harrell</td>
            <td>Financial Controller</td>
            <td>San Francisco</td>
            <td>62</td>
            <td>2009/02/14</td>
            <td>$452,500</td>
        </tr>
        <tr>
            <td>Timothy Mooney</td>
            <td>Office Manager</td>
            <td>London</td>
            <td>37</td>
            <td>2008/12/11</td>
            <td>$136,200</td>
        </tr>
        <tr>
            <td>Jackson Bradshaw</td>
            <td>Director</td>
            <td>New York</td>
            <td>65</td>
            <td>2008/09/26</td>
            <td>$645,750</td>
        </tr>
        <tr>
            <td>Olivia Liang</td>
            <td>Support Engineer</td>
            <td>Singapore</td>
            <td>64</td>
            <td>2011/02/03</td>
            <td>$234,500</td>
        </tr>
        <tr>
            <td>Bruno Nash</td>
            <td>Software Engineer</td>
            <td>London</td>
            <td>38</td>
            <td>2011/05/03</td>
            <td>$163,500</td>
        </tr>
        <tr>
            <td>Sakura Yamamoto</td>
            <td>Support Engineer</td>
            <td>Tokyo</td>
            <td>37</td>
            <td>2009/08/19</td>
            <td>$139,575</td>
        </tr>
        <tr>
            <td>Thor Walton</td>
            <td>Developer</td>
            <td>New York</td>
            <td>61</td>
            <td>2013/08/11</td>
            <td>$98,540</td>
        </tr>
        <tr>
            <td>Finn Camacho</td>
            <td>Support Engineer</td>
            <td>San Francisco</td>
            <td>47</td>
            <td>2009/07/07</td>
            <td>$87,500</td>
        </tr>
        <tr>
            <td>Serge Baldwin</td>
            <td>Data Coordinator</td>
            <td>Singapore</td>
            <td>64</td>
            <td>2012/04/09</td>
            <td>$138,575</td>
        </tr>
        <tr>
            <td>Zenaida Frank</td>
            <td>Software Engineer</td>
            <td>New York</td>
            <td>63</td>
            <td>2010/01/04</td>
            <td>$125,250</td>
        </tr>
        <tr>
            <td>Zorita Serrano</td>
            <td>Software Engineer</td>
            <td>San Francisco</td>
            <td>56</td>
            <td>2012/06/01</td>
            <td>$115,000</td>
        </tr>
        <tr>
            <td>Jennifer Acosta</td>
            <td>Junior Javascript Developer</td>
            <td>Edinburgh</td>
            <td>43</td>
            <td>2013/02/01</td>
            <td>$75,650</td>
        </tr>
        <tr>
            <td>Cara Stevens</td>
            <td>Sales Assistant</td>
            <td>New York</td>
            <td>46</td>
            <td>2011/12/06</td>
            <td>$145,600</td>
        </tr>
        <tr>
            <td>Hermione Butler</td>
            <td>Regional Director</td>
            <td>London</td>
            <td>47</td>
            <td>2011/03/21</td>
            <td>$356,250</td>
        </tr>
        <tr>
            <td>Lael Greer</td>
            <td>Systems Administrator</td>
            <td>London</td>
            <td>21</td>
            <td>2009/02/27</td>
            <td>$103,500</td>
        </tr>
        <tr>
            <td>Jonas Alexander</td>
            <td>Developer</td>
            <td>San Francisco</td>
            <td>30</td>
            <td>2010/07/14</td>
            <td>$86,500</td>
        </tr>
        <tr>
            <td>Shad Decker</td>
            <td>Regional Director</td>
            <td>Edinburgh</td>
            <td>51</td>
            <td>2008/11/13</td>
            <td>$183,000</td>
        </tr>
        <tr>
            <td>Michael Bruce</td>
            <td>Javascript Developer</td>
            <td>Singapore</td>
            <td>29</td>
            <td>2011/06/27</td>
            <td>$183,000</td>
        </tr>
        <tr>
            <td>Donna Snider</td>
            <td>Customer Support</td>
            <td>New York</td>
            <td>27</td>
            <td>2011/01/25</td>
            <td>$112,000</td>
        </tr>
    	</tbody>
		</table>
		</div>
	</div>
	<div class="group">
		<h3>Add photos</h3>
		<div>
			    <!-- Upload Button-->  
    			<div id="upload" >Upload File</div><span id="status" ></span>  
   				 <!--List Files-->  
    			 <ul id="files" ></ul>  
		</div>
	</div>
	<div class="group">
		<h3>Share/view photos</h3>
		<div>
			<p>Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui. </p>
			<ul>
				<li>List item one</li>
				<li>List item two</li>
				<li>List item three</li>
			</ul>
		</div>
	</div>
	
</div>
        
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
