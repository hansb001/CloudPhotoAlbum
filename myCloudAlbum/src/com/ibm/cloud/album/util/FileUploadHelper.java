package com.ibm.cloud.album.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.ibm.cloud.album.util.FileConstantHelper;

public class FileUploadHelper {
	private static String uploadPath = null;
	private static String tempPath = null;
	private static File uploadFile = null;
	private static File tempPathFile = null;
	private static int sizeThreshold = 1024;
	private static int sizeMax = 204800;
	
	static {
		sizeMax = Integer.parseInt(FileConstantHelper.getValue("sizeMax"));
		sizeThreshold = Integer.parseInt(FileConstantHelper
				.getValue("sizeThreshold"));
		uploadPath = FileConstantHelper.getValue("uploadPath");
		uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		tempPath = FileConstantHelper.getValue("tempPath");
		tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
	}

	/**
	 * 
	 * @param request
	 * @return true 
	 */
	@SuppressWarnings("unchecked")
	public static boolean upload(HttpServletRequest request) {
		boolean flag = true;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(sizeThreshold); // 
				factory.setRepository(tempPathFile);// 
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-");// 
				upload.setSizeMax(sizeMax);//
				List<FileItem> items = upload.parseRequest(request);
				// 
				if (!checkFileType(items))
					return false;
				Iterator<FileItem> itr = items.iterator();//
				//
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();//
					if (!item.isFormField()) {// 
						String name = item.getName();// 
						if (name != null) {
							File fullFile = new File(item.getName());
							File savedFile = new File(uploadPath,
									fullFile.getName());
							item.write(savedFile);
						}
					}
				}
			} catch (FileUploadException e) {
				flag = false;
				e.printStackTrace();
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		} else {
			flag = false;
			System.out.println("the enctype must be multipart/form-data");
		}
		return flag;
	}

	/**
	 * 
	 * @param filePath
	 *            
	 */
	public static void deleteFile(String[] filePath) {
		if (filePath != null && filePath.length > 0) {
			for (String path : filePath) {
				String realPath = uploadPath + path;
				File delfile = new File(realPath);
				if (delfile.exists()) {
					delfile.delete();
				}
			}

		}
	}

	/**
	 * @param filePath
	 *          
	 */
	public static void deleteFile(String filePath) {
		if (filePath != null && !"".equals(filePath)) {
			String[] path = new String[] { filePath };
			deleteFile(path);
		}
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	private static Boolean checkFileType(List<FileItem> items) {
		Iterator<FileItem> itr = items.iterator();// 
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();//
			if (!item.isFormField()) {// 
				String name = item.getName();// 
				if (name != null) {
					File fullFile = new File(item.getName());
					boolean isType = ReadUploadFileTypeHelper
							.readUploadFileType(fullFile);
					if (!isType)
						return false;
					break;
				}
			}
		}

		return true;
	}
}
