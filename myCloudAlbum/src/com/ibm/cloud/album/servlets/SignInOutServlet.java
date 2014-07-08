package com.ibm.cloud.album.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.dao.UserDAO;
import com.ibm.cloud.album.exceptions.UserLoginFailedException;
import com.ibm.cloud.album.exceptions.UserRegisteredException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "SignInOutServlet", urlPatterns = { "/SignInOutServlet" })

public class SignInOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInOutServlet() {
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
		
		//sign-out
		if (request.getParameter("sign_out_flg") != null) {
			request.getSession().removeAttribute("current_user");
			response.sendRedirect("home.jsp");
			return;
		}
		
		//sign-in
		String email = request.getParameter("sign_in_mail");
		String password = request.getParameter("sign_in_pwd");
		
		UserDAO userDao = new UserDAO();
		UserBean user = null;
		try{
			user = userDao.login(email, password);
		}catch(UserLoginFailedException e){
			request.getSession().setAttribute("error_msg", e.getExceptionInfo());
			response.sendRedirect("index.jsp");
			System.out.println("user sign failed.");
			return;
		}catch(Exception e) {
			request.getSession().setAttribute("error_msg", "Your input has something wrong, please try again later for register!");
			response.sendRedirect("index.jsp");
			return;
		}

		request.getSession().setAttribute("current_user", user);
		request.getSession().setAttribute("success_msg", "Welcome, " + user.getUserName() + "!");
		response.sendRedirect("home.jsp");
		System.out.println("user sign ok.");
	}

}
