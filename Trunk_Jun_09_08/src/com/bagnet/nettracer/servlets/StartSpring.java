package com.bagnet.nettracer.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

/**
 * This servlet is used to access the image from the file system.
 * 
 * @author Ankur Gupta
 * 
 * create date - Feb 28, 2005
 */
public class StartSpring extends HttpServlet {
	 
    private int count;
 
    @Override
    public void init() throws ServletException {
        SpringUtils.init();
    }
 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().log("service() called");
    }
 
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }   
 
}
