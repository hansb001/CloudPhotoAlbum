package com.ibm.cloud.album.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

import com.ibm.cloud.album.util.FileUploadHelper;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploaderServlet", urlPatterns = { "/FileUploaderServlet" })
public class FileUploaderServlet extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploaderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map map = request.getParameterMap();
		Iterator iter = map.keySet().iterator();
		System.out.println("request->" + request.getRequestURL());
		while(iter.hasNext()){
			String key = (String)iter.next();
			String value =(String) map.get(key);
			System.out.println("key->" + key + ", value->" + value);
		}
		
		String userAct = request.getParameter("user_action");
		System.out.println("userAct0->" + userAct);
		userAct = (String)request.getAttribute("user_action");
		System.out.println("userAct1->" + userAct);
		userAct =(String)request.getSession().getAttribute("user_action");
		System.out.println("userAct2->" + userAct);
		if(userAct != null && !userAct.trim().equals("")){
			if(userAct.equals("file_upd")){
				FileUploadHelper.upload(request);
			}else if(userAct.equals("file_del")){
				String[] file = request.getParameterValues("fileName");
				FileUploadHelper.deleteFile(file);
			}
		}
		
	}

}
