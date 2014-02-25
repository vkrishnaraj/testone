package com.bagnet.clients.defaul;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class FckEditorPathBuilderImpl implements net.fckeditor.requestcycle.UserPathBuilder{
	public static String subdir = "/editor_files";
	public static String context = "/" + TracerProperties.get(TracerProperties.get("wt.company.code"),"application_context");
	
	public String getUserFilesAbsolutePath(HttpServletRequest request){
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		return TracerProperties.get(user.getCompanycode_ID(),"image_store") + subdir;
	}
	public String getUserFilesPath(HttpServletRequest request){
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		context = "/" + TracerProperties.get(user.getCompanycode_ID(),"application_context");
		return context + "/showImage?ID=" + subdir + "/";
	}
}
