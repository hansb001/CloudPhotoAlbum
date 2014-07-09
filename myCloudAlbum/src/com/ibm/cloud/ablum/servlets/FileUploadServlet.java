package com.ibm.cloud.ablum.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cloud.ablum.util.FileUploadHelper;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = { "/FileUploadServlet" })
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		System.out.println("user action is " + userAct );
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
