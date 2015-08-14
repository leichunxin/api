package com.hdu.newlife.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected static final Logger logger = Logger.getLogger(BaseServlet.class);
	
	protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Constructor of the object.
	 */
	public BaseServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 
	 * @param response
	 * @param object
	 */
	public void writeJson(HttpServletResponse response, Object object) {
		try {
			if (null != response) {
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().write(JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss"));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * 
	 * @param response
	 * @param list
	 */
	public void writeJson(HttpServletResponse response, List<Object> list) {
		try {
			if (null != response) {
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().write(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * The doDelete method of the servlet. <br>
	 * 
	 * This method is called when a HTTP delete request is received.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * The doPut method of the servlet. <br>
	 * 
	 * This method is called when a HTTP put request is received.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Put your code here
	}

	/**
	 * Returns information about the servlet, such as author, version, and copyright.
	 * 
	 * @return String information about this servlet
	 */
	public String getServletInfo() {
		return "This is my default servlet created by Eclipse";
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
