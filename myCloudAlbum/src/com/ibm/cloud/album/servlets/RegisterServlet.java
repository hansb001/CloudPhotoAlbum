package com.ibm.cloud.album.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cloud.ablum.exceptions.UserRegisteredException;
import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.dao.UserDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/RegisterServlet" })

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Redirect it to weather.jsp
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("system join the doPOST() method of RegisterServlet");
		
		String userName = request.getParameter("name");
		String userEmail = request.getParameter("email");
		String userHomePhone = request.getParameter("userHomePhone");
		String userMobile = request.getParameter("userMobile");
		String userSelfDescription = request.getParameter("userSelfDescription");
		String userNote = request.getParameter("userNote");
		String userPassword = request.getParameter("password");
		String userQQ = request.getParameter("userQQ");
		String userBluePageImgUrl = "https://w3-connections.ibm.com/profiles/photo.do?email=" + userEmail;
		
		System.out.println("userName->" + userName);
		
		UserBean user = new UserBean();
		user.setEmail(userEmail);
		user.setHomePhoneNum(userHomePhone);
		user.setMobile(userMobile);
		user.setNote(userNote);
		user.setPassword(userPassword);
		user.setQQ(userQQ);
		user.setSelfIntroduction(userSelfDescription);
		user.setUserName(userName);
		user.setImageBluePgUrl(userBluePageImgUrl);
		
		UserDAO userDao = new UserDAO();
		int code = -100;
		try{
			code = userDao.registerUser(user);
		}catch(UserRegisteredException e){
			request.getSession().setAttribute("error_msg", e.getExceptionInfo());
			response.sendRedirect("index.jsp");
			return;
		}
		System.out.println("user register code is " + code);
		
		if(code == 1){
			request.getSession().setAttribute("success_msg", 
					"Congratuals! Your have successfully registered as user of cloud photo album, enjoy the moment with your friends!");
			response.sendRedirect("home.jsp");
		}else{
			request.getSession().setAttribute("error_msg", "Your input has something wrong, please try it again later for registration!");
			response.sendRedirect("index.jsp");
		}
	
		/*UserResource ur = new UserResource();
		javax.ws.rs.core.Response rep = ur.create(userName, userEmail, userHomePhone, userMobile, userSelfDescription, userNote, userPassword, userQQ);
		
		if(rep != null){
			System.out.println("repstatus:"+ rep.getStatus());
		}*/
	}

}
