package com.bagnet.clients.defaul;

import javax.servlet.http.HttpServletRequest;

import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class FckEditorPathBuilderImpl implements net.fckeditor.requestcycle.UserPathBuilder{
	static String subdir = "/bagbuzz";
	static String context = "/" + TracerProperties.get("application_context");
	
	public String getUserFilesAbsolutePath(HttpServletRequest request){
		return TracerProperties.get("image_store") + subdir;
	}
	public String getUserFilesPath(HttpServletRequest request){
		return context + "/showImage?ID=" + subdir + "/";
	}
}
