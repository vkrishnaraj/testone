package com.bagnet.nettracer.tracing.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpiresHeaderFilter implements Filter {
	
	private static final String EXPIRES_MINUTES = "expires-minutes";

	FilterConfig fc;
	
	public void destroy() {
		this.fc = null;

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		String path = request.getRequestURI().toLowerCase();
		
		int index = path.lastIndexOf(".");
		if (index != -1) {
			String type = path.substring(index + 1);
			String delayStr = fc.getInitParameter(type + "-" + EXPIRES_MINUTES);
			if(delayStr != null) {
				int delay = Integer.parseInt(delayStr);
				long expireTime = new Date().getTime() + (delay * 60 * 1000);
				SimpleDateFormat sdf = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss z");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				String expireStr = sdf.format(new Date(expireTime));
				response.setHeader("Expires", expireStr);
			}
		}
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;

	}

}
