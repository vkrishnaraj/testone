package com.bagnet.nettracer.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.wt.connector.NewWorldTracerConnector;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * This servlet is used to access the image from the file system.
 * 
 * @author Ankur Gupta
 * 
 * create date - Feb 28, 2005
 */
public class showCaptcha extends HttpServlet {
	//~ Constructors
	// *******************************************************************************

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		HttpSession session = req.getSession(true);

		WorldTracerService service = SpringUtils.getWorldTracerService();
		NewWorldTracerConnector conn = (NewWorldTracerConnector) service.getWtConnector();
		int i = 0;

		byte[] img = conn.getCaptcha();

		
		res.setContentLength(img.length);	
		OutputStream out = res.getOutputStream();

		out.write(img);
		out.flush();
		out.close();
	}
}