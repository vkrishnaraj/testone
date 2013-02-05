import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.axis2.AxisFault;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument;
import aero.nettracer.serviceprovider.wt_1_0.WorldTracerServiceStub;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;

import com.bagnet.nettracer.cronjob.incident.AutoClose;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.FraudValuationReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.search.QueryNetTracerDocument;
import com.bagnet.nettracer.ws.search.QueryNetTracerDocument.QueryNetTracer;
import com.bagnet.nettracer.ws.search.ScannerMbrQueryServiceImpl;
import com.bagnet.nettracer.wt.WorldTracerException;


public class Test {
//	private static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	
//	@org.junit.Test
//	public void getActionFileCounts(
//			) throws WorldTracerException {
//		String companyCode = "US"; String wtStation = "XAX";
//		try {
//			WorldTracerServiceStub stub = new WorldTracerServiceStub("endpoint");
//			
//			RequestHeader header = new RequestHeader();
//			header.setUsername("username");
//			header.setPassword("password");
//			
//			ActionFileRequestData data = new ActionFileRequestData();
//			data.setAirline(companyCode);
//			data.setStation(wtStation);		
//			
//			GetActionFileCountsDocument d = GetActionFileCountsDocument.Factory.newInstance();
//			GetActionFileCounts c = d.addNewGetActionFileCounts();
//			
//
//			c.setHeader(mapper.map(header,aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
//			c.setData(mapper.map(data,aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData.class));
//			stub.getActionFileCounts(d);
//			
//			
//		} catch (AxisFault e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//	}
//	
//	@org.junit.Test
//	public void t1() {
//		int[] result = new int[7];
//		int i = 1;
//	}
//	
//
//    @org.junit.Test
//    public void test() {
//    	String[] tags = {"1037918255", "US920622", "US936469"};
//    	String[] pnrs = {"DWCLF9", "AMVRSQ"};
//    	ScannerMbrQueryServiceImpl impl = new ScannerMbrQueryServiceImpl();
//    	QueryNetTracerDocument doc = QueryNetTracerDocument.Factory.newInstance();
//    	QueryNetTracer trace = QueryNetTracer.Factory.newInstance();
//    	trace.setTagNumberArray(tags);
//    	trace.setPnrArray(pnrs);
//    	doc.setQueryNetTracer(trace);
//    	impl.queryNetTracer(doc, 328);
//    	impl.queryNetTracer(doc, 328);
//    	impl.queryNetTracer(doc, 328);
//    }
//
//    @org.junit.Test
//	public void testReportClaimDate() {
//    	System.setProperty("user.timezone", "CST");
//    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//    	cal.set(2012, 7, 20, 17, 8, 27);
//    	Date claimDate = cal.getTime();
//		java.util.Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(claimDate, TracingConstants.DB_DATEFORMAT, null, null) + " "
//				+ DateUtils.formatDate(claimDate, TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
//		
//		String date = DateUtils.formatDate(tempdate, "MM/dd/yyyy", null, TimeZone.getTimeZone("PST"));
//		
//		System.out.println(cal);
//		System.out.println(cal.getTime());
//		System.out.println(claimDate);
//		System.out.println(DateUtils.formatDate(claimDate, TracingConstants.DB_DATEFORMAT, null, null));
//		System.out.println(DateUtils.formatDate(claimDate, TracingConstants.DB_TIMEFORMAT, null, null));
//		System.out.println(tempdate);
//		System.out.println(date);
//		System.out.println();
//		System.out.println("NEXT TEST");
//		System.out.println();
//		Calendar tzFix = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//		Calendar time = Calendar.getInstance();
//		time.set(tzFix.get(Calendar.YEAR), tzFix.get(Calendar.MONTH), tzFix.get(Calendar.DAY_OF_MONTH), 
//				tzFix.get(Calendar.HOUR_OF_DAY), tzFix.get(Calendar.MINUTE), tzFix.get(Calendar.SECOND));
//		System.out.println(tzFix.getTime());
//		System.out.println(time.getTime());
//		System.out.println(new Date());
//
//	}
//
//    @org.junit.Test
//	public void testReportClaimDate() {
//    	AutoClose cronJob = new AutoClose("WS");
//    	cronJob.autoCloseIncidents();
//
//	}
//    
//    @org.junit.Test
//	public void testSQLReturn() {
//    	Session sess = HibernateWrapper.getDirtySession().openSession();
//		try {
//			TimeZone tz = TimeZone.getTimeZone("EST");
//
//			/*************** use direct jdbc sql statement **************/
//			Connection conn = sess.connection();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = null;
//			String sql = ""
//					
//					+ "select c.id, i.airlineIncidentId, c.claimDate, c.amountClaimed, c.amountClaimedCurrency, c.amountPaid, c.amountPaidCurrency, s.description "
//					+ "from fsfile f, fsclaim c, fsincident i, status s where 1=1 ";
//			
//			Date sdate = null, edate = null;
//			Date sdate1 = null, edate1 = null; // add one for timezone
//			Date stime = null; // time to compare (04:00 if eastern, for example)
//
//			String simpleDateQ = "";
//			
//			ArrayList<Date> dateal = null;
//			if ((dateal = calculateDateDiff(tz, "08/20/2012", null)) == null) {
//				return;
//			} 
//			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
//			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
//			stime = (Date)dateal.get(4);
//			
//			if (sdate != null && edate != null) {
//				simpleDateQ += " and c.claimDate >= '"
//						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
//						+ " "
//						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties(), true),null,null)
//						+ "' and c.claimDate <= '"
//						+ DateUtils.formatDate(edate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
//						+ " "
//						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties(), true),null,null)
//						+ "'";
//				
//			} else if (sdate != null) {
//				edate = null;
//				
//				simpleDateQ += " and c.claimDate >= '"
//					+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
//					+ " "
//					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties(), true),null,null)
//					+ "'";
//			}
//
//			sql += simpleDateQ;
//			
//			sql += " and c.file_id = f.id and c.statusId = s.Status_ID and f.id = i.file_id";
//
//			System.out.println("SQL: " + sql);
//			
//			rs = stmt.executeQuery(sql);
//
//			FraudValuationReportDTO sr = null;
//			List<FraudValuationReportDTO> claimList = new ArrayList<FraudValuationReportDTO>();
//			List<String> idList = new ArrayList<String>();
//
//			while (rs.next()) {
//				sr = new FraudValuationReportDTO();
//				
//				sr.setClaimID(rs.getString("id"));
//				sr.setIncidentID(rs.getString("airlineIncidentId"));
//				sr.setClaimDate(rs.getDate("claimDate"));
//				sr.setStatus(rs.getString("description"));
//				sr.setAmountClaimed(rs.getString("amountClaimed"));
//				sr.setAmountClaimedCurrency(rs.getString("amountClaimedCurrency"));
//				sr.setAmountPaid(rs.getString("amountPaid"));
//				sr.setAmountPaidCurrency(rs.getString("amountPaidCurrency"));
//				
//				sr.set_DATEFORMAT("MM/dd/yyyy");
//				sr.set_TIMEZONE(tz);
//				
//				claimList.add(sr);
//				idList.add(rs.getString("id"));
//				
//				System.out.println("DATE: " + sr.getClaimDate());
//				System.out.println("DATE: " + rs.getDate("claimDate"));
//				System.out.println("DATE: " + rs.getString("claimDate"));
//				System.out.println("DATE: " + rs.getTimestamp("claimDate"));
//			}
//			
//			stmt.close();
//			rs.close();		
//			
//			System.out.println(claimList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			sess.close();
//		}
//    }
//	
//	/**
//	 * method to calculate the agent timezone based date/time for comparing dates in db
//	 * @param sdate
//	 * @param sdate1
//	 * @param edate
//	 * @param edate1
//	 * @param stime
//	 * @param srDTO
//	 * @param tz
//	 * @return
//	 */
//	public static ArrayList<Date> calculateDateDiff(TimeZone tz, String start, String end) {
//		ArrayList<Date> al = new ArrayList<Date>();
//		Date sdate=null,sdate1=null,edate=null, edate1=null, stime=null;
//		if (start != null && start.length() > 0) {
//			sdate = DateUtils.convertToDate(start, "MM/dd/yyyy", null);
//			if (sdate == null) // invalid date
//				return null;
//			
//			if (TracerDateTime.getHourDiff(tz) == 0) {
//				sdate1 = sdate;
//			} else {
//
//				Calendar c = new GregorianCalendar();
//				c.setTime(sdate);
//				c.add(Calendar.DAY_OF_MONTH, 1);
//				sdate1 = c.getTime();
//			}
//			stime = DateUtils.convertToDate(Integer.toString(TracerDateTime.getHourDiff(tz)), "H", null);
//
//		}
//		if (end != null && end.length() > 0) {
//			edate = DateUtils.convertToDate(end, "MM/dd/yyyy", null);
//			if (edate == null) // invalid date
//				return null;
//			
//			if (TracerDateTime.getHourDiff(tz) == 0) {
//				edate1 = edate;
//			} else {
//				Calendar c = new GregorianCalendar();
//				c.setTime(edate);
//				c.add(Calendar.DAY_OF_MONTH, 1);
//				edate1 = c.getTime();
//			}
//		}
//		al.add(sdate);
//		al.add(sdate1);
//		al.add(edate);
//		al.add(edate1);
//		al.add(stime);
//
//		return al;
//	}

    @org.junit.Test
	public void testSQLReturn() {
    	int count = 1;
    	int triples = 0;
    	Calendar now = Calendar.getInstance();
    	now.add(Calendar.DATE, 2);
    	int month = now.get(Calendar.MONTH);
    	while (triples < 25) {
    		now.add(Calendar.DATE, 14);
    		int check = now.get(Calendar.MONTH);
    		if (check == month) {
    			count++;
    		} else {
    			month = check;
    			count = 1;
    		}
    		if (count == 3) {
    			System.out.println("Month = " + getMonth(month) + " | Year = " + now.get(Calendar.YEAR));
    			triples++;
    		}
    	}
    	
    }
    
    private String getMonth(int month) {
    	switch (month) {
    		case(0):
    			return "January";
    		case(1):
    			return "February";
    		case(2):
    			return "March";
    		case(3):
    			return "April";
    		case(4):
    			return "May";
    		case(5):
    			return "June";
    		case(6):
    			return "July";
    		case(7):
    			return "August";
    		case(8):
    			return "September";
    		case(9):
    			return "October";
    		case(10):
    			return "November";
    		case(11):
    			return "December";
    		default:
    			return "Unknown";
    	}
    }
}
