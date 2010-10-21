package com.bagnet.nettracer.wt.connector;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;

import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;
import com.bagnet.nettracer.wt.utils.ActionFileDto;

public interface WorldTracerConnector {

	String insertIncident(Incident i, String companyCode, String stationCode, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;

	String insertOhd(OHD ohd, String companyCode, String stationCode, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;

	String closeIncident(Incident incident, String wtId, String stationCode, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;

	String closeOhd(OHD ohd, String wt_id, String wt_stationcode, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;

	String sendFwd(WtqFwdGeneral fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	void eraseActionFile(String station_id, String companyCode, ActionFileType area, int day, int itemNum, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;

	Ahl findAHL(String wt_id, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	Ohd findOHD(String wt_id, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	void suspendOHD(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	void suspendAHL(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	void reinstateOHD(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	void reinstateAHL(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String forwardOhd(WtqFwdOhd forward, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String amendAhl(Incident incident, String wt_ahl_id, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String amendOhd(OHD ohd, String wt_ohd_id, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String requestOhd(String wt_ohd_id, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap, WebServiceDto dto, WtqRequestOhd roh) throws WorldTracerException, CaptchaException;

	String createBdo(Map<WorldTracerField, List<String>> fieldMap, String ahl_id, String ohd_id,
			DeliverCompany delivercompany, Station station, WebServiceDto dto, BDO bdo) throws WorldTracerException, CaptchaException;

	String requestQoh(String fromStation, String fromAirline,
			String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap, WebServiceDto dto, WtqRequestQoh wtq) throws WorldTracerException, CaptchaException;
	
	EnumMap<ActionFileType, int[]> getActionFileCounts(String companyCode, String wtStation, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	

	List<Worldtracer_Actionfiles> getActionFiles(String companyCode, String stationCode, ActionFileType afType, int day, int count, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	List<ActionFileDto> getActionFileSummary(String companyCode, String stationCode, ActionFileType afType, int day, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String getActionFileDetails(String companyCode, String stationCode, ActionFileType afType, int day, int itemNum, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	public String sendPxf(WtqRequestPxf pxf, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	void logout();

	void initialize();

	WebServiceDto getDto(HttpSession session);

	String sendQoh(WtqQoh wtqQoh, WebServiceDto dto) throws WorldTracerException, CaptchaException;

}
