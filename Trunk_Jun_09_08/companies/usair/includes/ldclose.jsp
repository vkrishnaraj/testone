<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
%>

function validateLdClose(form, doCheck) { if (doCheck == 1) {
<%
	Station zzz = StationBMO.getStationByCode("ZZZ", "US");
	int zzzId = zzz.getStation_ID();

	if (request.getAttribute("lostdelay") != null) {
%>
      if (form.faultstation_id.value == <%=zzzId%>) { 
         if (form.loss_code.value != 79) {
          alert("<bean:message key="ldclose.error.settozzz79" />");
          form.loss_code.value = 79;
          return false;
        } 
      }
<%
	}
%>
    return true;
  } else { return true; }
}
