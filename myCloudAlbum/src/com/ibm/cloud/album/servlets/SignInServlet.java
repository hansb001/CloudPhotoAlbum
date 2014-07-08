package com.ibm.cloud.album.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cloud.ablum.exceptions.UserLoginFailedException;
import com.ibm.cloud.ablum.exceptions.UserRegisteredException;
import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.dao.UserDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/RegisterServlet" })

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInServlet() {
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
		
		String email = request.getParameter("sign_in_name");
		String password = request.getParameter("sign_in_pwd");
		
	
		
		UserDAO userDao = new UserDAO();
		int code = -100;
		try{
			code = userDao.login(email, password);
		}catch(UserLoginFailedException e){
			request.getSession().setAttribute("error_msg", e.getExceptionInfo());
			response.sendRedirect("index.jsp");
		}
		System.out.println("user sign in code is " + code);
		
		if(code == 1){
			request.getSession().setAttribute("success_msg", 
					"Congratuals! Your have successfully registered as user of demo hub!"
					+ "Clik sign in for login.");
			response.sendRedirect("home.jsp");
		}else{
			request.getSession().setAttribute("error_msg", "Your input has something wrong, please try again later for register!");
			response.sendRedirect("index.jsp");
		}
	
		/*UserResource ur = new UserResource();
		javax.ws.rs.core.Response rep = ur.create(userName, userEmail, userHomePhone, userMobile, userSelfDescription, userNote, userPassword, userQQ);
		
		if(rep != null){
			System.out.println("repstatus:"+ rep.getStatus());
		}*/
	}

}
