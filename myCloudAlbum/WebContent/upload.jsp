<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>"></base>
<title>My JSP 'index.jsp' starting page</title>
</head>
  <body>
	   <form name="myform" action="FileUploadServlet" method="post" enctype="multipart/form-data">
	   <br></br>
	   <br></br>
       File:
       <input type="file" name="myfile"></input>
       <input type="hidden" name="user_action" value="file_upd"></input>
       <br></br>
       <br></br>
       <input type="submit" name="submit" value="Commit"></input>
    </form>
  </body>
</html>