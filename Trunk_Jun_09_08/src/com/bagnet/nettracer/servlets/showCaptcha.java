package com.bagnet.nettracer.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
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
		WebServiceDto dto = (WebServiceDto) session.getAttribute(TracingConstants.WEB_SERVICE_DTO);
		
		byte[] img = dto.getCaptcha();

		res.setContentLength(img.length);	
		OutputStream out = res.getOutputStream();

		out.write(img);
		out.flush();
		out.close();
	}
}