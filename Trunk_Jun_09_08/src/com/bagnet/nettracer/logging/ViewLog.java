/*
 * Created on Feb 7, 2007
 *
 * matt
 */
package com.bagnet.nettracer.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ViewLog extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ViewLog() {
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
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *          the request send by the client to the server
	 * @param response
	 *          the response send by the server to the client
	 * @throws ServletException
	 *           if an error occurred
	 * @throws IOException
	 *           if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		

		if (request.getParameter("userid") != null && request.getParameter("pass") != null && request.getParameter("userid").equals("nettracer") && request.getParameter("pass").equals("nettracerpass1")) {
			ServletContext sc = getServletContext();
			String logpath = sc.getRealPath("/");
			if (logpath.indexOf("tmp/") > 0) {
				logpath = logpath.substring(0, logpath.indexOf("tmp/"));
				logpath = logpath + "log/";
			} else if (logpath.indexOf("tmp\\") > 0) {
				logpath = logpath.substring(0, logpath.indexOf("tmp\\"));
				logpath = logpath + "log\\";
			}
			if (request.getParameter("logfile") != null && request.getParameter("logfile").length() > 0) {
				try {
					File logfile = new File(logpath + request.getParameter("logfile"));
	
					response.setContentType("text/plain");
					response.setContentLength((int)logfile.length());
					BufferedReader in = new BufferedReader(new FileReader(logpath + request.getParameter("logfile")), 1024); 
					String line = null;
					PrintWriter outwrite = response.getWriter();
					while ((line = in.readLine()) != null) {
						outwrite.println(line);
					}
					outwrite.flush();
					outwrite.close();
					
				} catch (Exception e) {}
			} else {

				File dir = new File(logpath);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				// The list of files can also be retrieved as File objects
				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
				out.println("<HTML>");
				out.println("  <HEAD><TITLE>View Log</TITLE></HEAD>");
				out.println("  <BODY>");
				out.println("<p>Log Dir: " + logpath);
				out.println("<p>Files:<br>");
				File[] files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					out.println(i + ": <a href='ViewLog?userid=nettracer&pass=nettracerpass1&logfile=" + f.getName() + "'>" + f.getName() + "</a> - size: " + (f.length()/1000) + " kb <br>");
				}
				out.println("  </BODY>");
				out.println("</HTML>");
				out.flush();
				out.close();
			}
		}

		
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request
	 *          the request send by the client to the server
	 * @param response
	 *          the response send by the server to the client
	 * @throws ServletException
	 *           if an error occurred
	 * @throws IOException
	 *           if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *           if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	private byte[] readBytes(String filepath) throws Exception {
		File f = new File(filepath);
		if (!f.exists()) {
			return null;
		}
		InputStream is = new FileInputStream(f);
		long length = f.length();

		byte[] bytes = new byte[(int) length];

		/*
		 * is.read( data, 0, size ); // read into byte array is.close();
		 */

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			return null;
		}
		return bytes;
	}

}