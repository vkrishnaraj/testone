package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

import org.junit.Test;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class MorningDutiesTest {
//	@Test
	public void createTaskTest(){
		Incident inc = new Incident();
		inc.setIncident_ID("ACYWS00038741");
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		MorningDutiesTask mdt = (MorningDutiesTask)MorningDutiesUtil.createTask(agent, inc, 2);
	}
	
//	@Test
//	public void getCountTest(){
//		Agent agent = new Agent();
//		agent.setAgent_ID(1755);
//		System.out.println("count: " + MorningDutiesUtil.getTwoDayCount(agent));
//	}
	
//	@Test
	public void dateTest(){
//		System.out.println(new Date());
		GregorianCalendar a = new GregorianCalendar(new SimpleTimeZone(-(3600000 * 12), "-12GMT"));
		System.out.println(a.getTime());
		a.add(Calendar.HOUR, 8);
		System.out.println(a.getTime());
		a.set(Calendar.SECOND, 0);
		a.set(Calendar.MILLISECOND, 0);
		a.set(Calendar.MINUTE, 0);
		a.set(Calendar.HOUR_OF_DAY, 0);
		System.out.println(a.getTime());
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		sdf.format(a.getTime());
//		SimpleDateFormat.format(a);
		
		Date now = DateUtils.convertToGMTDate(a.getTime());
		System.out.println(now);
		
		
		GregorianCalendar b = new GregorianCalendar(new SimpleTimeZone(-(3600000 + 12), "-12GMT"));
		System.out.println(b.getTime());
		Date c = DateUtils.convertToGMTDate(DateUtils.floorDate(b.getTime()));
		System.out.println(c);
	}
//	@Test
	public void dateStringTest(){
		System.out.println(MorningDutiesUtil.getDateRange(2));
		System.out.println(MorningDutiesUtil.getDateRange(3));
		System.out.println(MorningDutiesUtil.getDateRange(4));
	}
//	@Test
	public void getTaskTest(){
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		Station station = new Station();
		station.setState_ID("FLL");
		station.setStation_ID(170);
		agent.setStation(station);
		MorningDutiesUtil.getTask(agent, 2);
	}
//	@Test
	public void getTaskByIdTest(){
		GeneralTask task = TaskManagerBMO.getTaskById(12);
		System.out.println(task.getTask_id());
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		MorningDutiesUtil.deferTask(agent, (MorningDutiesTask)task, 30);
	}
//	@Test
	public void getListTest(){
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		Station station = new Station();
		station.setState_ID("FLL");
		station.setStation_ID(170);
		agent.setStation(station);
		List<Incident> list = MorningDutiesUtil.getTaskList(agent, 2);
		for(Incident inc:list){
			System.out.println(inc.getIncident_ID());
		}
	}
//	@Test
	public void getPausedListTest(){
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		Station station = new Station();
		station.setState_ID("FLL");
		station.setStation_ID(170);
		agent.setStation(station);
		List<Incident> list = MorningDutiesUtil.getPauseList(agent, 2);
		for(Incident inc:list){
			System.out.println(inc.getIncident_ID());
		}
	}
	
//	@Test
//	public void testReport(){
//		ReportBMO rbmo = new ReportBMO(null);
//		rbmo.createMorningDutiesReport(null, 0, ReportingConstants.RPT_101_NAME, "Morning Duties Report");
//	}
	
//	@Test
	public void testGetPaused(){
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		List <Incident>list = MorningDutiesUtil.getPaginatedList(agent, 0, 0, 0);
		for(Incident i:list){
			System.out.println(i.getIncident_ID());
		}

//		System.out.println(task.getIncident().getIncident_ID());
	}
	
	@Test
	public void oc(){
		OnlineClaimsDao ocd = new OnlineClaimsDao();
		Agent agent = new Agent();
		agent.setAgent_ID(1755);
		Station station = new Station();
		station.setState_ID("FLL");
		station.setStation_ID(170);
		agent.setStation(station);
		
		OnlineClaim c = new OnlineClaim();
		try{
		ocd.saveOnlineClaimWsUseOnly(c, "YYCWS00104896", agent);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
