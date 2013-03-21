package com.bagnet.clients.b6;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import au.com.bytecode.opencsv.CSVReader;

public class ScannerDataSourceImpl implements ScannerDataSource{
	
	@Override
	public ScannerDTO getScannerData(Date startDate, Date endDate,
			String bagTagNumber) {
		
		return getScannerData(startDate, endDate, bagTagNumber, 0);
	}

	@Override
	public ScannerDTO getScannerData(Date startDate, Date endDate,
			String bagTagNumber, int timeout) {
		
		ScannerDTO newDto = new ScannerDTO();
		ArrayList<ScannerDataDTO> list = new ArrayList<ScannerDataDTO>();
		
		String bagtag = "";
		if(bagTagNumber.length() >  6){
			int end = bagTagNumber.length();
			int begin = end - 6;
			bagtag = bagTagNumber.substring(begin, end);
		} else {
			bagtag = bagTagNumber;
		}
		
		String sql = "select s.a as bagtag, s.b as remarks, s.flightdate as date, s.c as loc, s.d as ohd from scandata s" +
				" where s.a like '%" + bagtag + "' "; 
				if(startDate != null && endDate != null){
					sql += " and s.flightdate between :startdate and :enddate ";
				}
				sql += " order by s.id asc";
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			if(startDate != null && endDate != null){
				q.setDate("startdate", startDate);
				q.setDate("enddate", endDate);
			}
			q.addScalar("bagtag", StandardBasicTypes.STRING);
			q.addScalar("remarks", StandardBasicTypes.STRING);
			q.addScalar("date", StandardBasicTypes.DATE);
			q.addScalar("loc", StandardBasicTypes.STRING);
			q.addScalar("ohd", StandardBasicTypes.STRING);
			List<Object[]> ret = q.list();
			
			for(Object[] o:ret){
				String tag = (String)o[0];
				String remark = (String)o[1];
				Date d = (Date)o[2];
				String loc = (String)o[3];
				String ohd = (String)o[4];
				ScannerDataDTO dtoItem = new ScannerDataDTO(tag, d.toString(), loc, "", remark.toString(), this.getOHDid(bagtag, d), null);
				list.add(dtoItem);
			}
			
			newDto.setScannerDataDTOs(list);
			return newDto;
			
		}catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
			
		return null;
	}
	
	private String getOHDid(String bagtag, Date founddate){
		String sql = "select o.OHD_ID as id from ohd o " +
				" where o.founddate between :start and :end " +
				" and o.claimnum like  '%" + bagtag + "' " +
				" order by o.founddate asc";
		GregorianCalendar scal = new GregorianCalendar();
		scal.setTime(founddate);
		scal.add(Calendar.DATE, -1);
		Date start = scal.getTime();
		GregorianCalendar ecal = new GregorianCalendar();
		ecal.setTime(founddate);
		ecal.add(Calendar.DATE, 4);
		Date end = ecal.getTime();
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setDate("start", start);
			q.setDate("end", end);
			q.addScalar("id", StandardBasicTypes.STRING);
			List<String> ret = q.list();
			if(ret != null && ret.size() > 0){
				return ret.get(0);
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public List<String[]> consumeScannerDataFromCSV(String file){
		System.out.println(file);
		try{
			CSVReader reader = new CSVReader(new FileReader(file));
			List<String[]> item = reader.readAll();
//			for(String[] s:item){
//				String out = "";
//				for(int i = 0;i<s.length;i++){
//					out+=s[i] + " ";
//				}
//				System.out.println(out);
//			}
			return item;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertData(Session sess, Date flightdate, String a, String b, String c, String d, String e){
//		System.out.println(a);
		String sql = "insert into scandata (createdate,flightdate,a,b,c,d,e) values (:date,:flightdate,:a,:b,:c,:d,:e)";
//		Session sess = null;
		
		Transaction t = null;
		try{
			if(sess == null || !sess.isOpen()){
				sess = HibernateWrapper.getSession().openSession();
			}
			t = sess.beginTransaction();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("date", DateUtils.convertToGMTDate(new Date()));
			q.setParameter("flightdate", flightdate);
			q.setParameter("a", a);
			q.setParameter("b", b);
			q.setParameter("c", c);
			q.setParameter("d", d);
			q.setParameter("e", e);
			q.executeUpdate();
			t.commit();
		}catch (Exception e1) {
			e1.printStackTrace();
			try {
				t.rollback();
			} catch (Exception te) {
				te.printStackTrace();
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void consumeBAG_A(String file){
		List<String[]>list = this.consumeScannerDataFromCSV(file);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if(list!=null){
				for(String[]s:list){
					String bagtag = s[0]+s[1];
					String date = s[3] + " " + s[4];
					Date d = DateUtils.convertToDate(date, "MMM dd yyyy", "US");
					String remark = "Tag Number: "+bagtag;
					if(s[9].trim().length()>0){
						remark+="<br/>"+s[9].trim();
					}
					if(s[16].trim().length()>0){
						remark+="<br/>"+s[16].trim();
					}
					if(s[17].trim().length()>0){
						remark+="<br/>"+s[17].trim();
					}
					this.insertData(sess,d, bagtag, remark,null,null,null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void consumeBAG_B(String file){
		List<String[]>list = this.consumeScannerDataFromCSV(file);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if(list!=null){
				for(String[]s:list){
					String bagtag = s[0]+s[1];
					String date = s[3];
					Date d = DateUtils.convertToDate(date, "yyyyMMdd", "US");
					String remark = "Tag Number: "+bagtag;
					if(s[8].trim().length()>0){
						remark+="<br/>"+s[8].trim();
					}
					if(s[14].trim().length()>0){
						remark+="<br/>"+s[14].trim();
					}
					if(s[15].trim().length()>0){
						remark+="<br/>"+s[15].trim();
					}
					this.insertData(sess,d, bagtag, remark,null,null,null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void consumeHISTORY_A(String file){
		List<String[]>list = this.consumeScannerDataFromCSV(file);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if(list!=null){
				for(String[]s:list){
					String bagtag = s[3]+s[4];
					String date = s[1] + " " + s[2];
					Date d = DateUtils.convertToDate(date, "MMM dd yyyy", "US");
					String loc = "MCO";
					if(s[5].trim().length() > 0){
						loc += ":" + s[5].trim();
					}
					String scanner = s[7];
					
					String remark = "Tag Number: " + bagtag;
					if(scanner.trim().length() > 0){
						remark += "<br/>" + scanner.trim();
					}
					if(s[6].trim().length()>0){
						remark += "<br/>" + s[6].trim();
					}
					this.insertData(sess,d,bagtag, remark,loc,null,null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void consumeHISTORY_B(String file){
		List<String[]>list = this.consumeScannerDataFromCSV(file);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if(list!=null){
				for(String[]s:list){
					String bagtag = s[2]+s[3];
					String date = s[1];
					Date d = DateUtils.convertToDate(date, "yyyyMMdd", "US");
					String loc = "MCO";
					if(s[4].trim().length() > 0){
						loc += ":" + s[4].trim();
					}
					String scanner = s[6];
					
					String remark = "Tag Number: " + bagtag;
					if(scanner.trim().length() > 0){
						remark += "<br/>" + scanner.trim();
					}
					if(s[5].trim().length()>0){
						remark += "<br/>" + s[5].trim();
					}
					this.insertData(sess,d,bagtag, remark,loc,null,null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main (String [] args){
		ScannerDataSourceImpl s = new ScannerDataSourceImpl();
////		s.consumeBAG("C:\\Users\\Matt\\Documents\\b6scandata\\BAG.csv");
//		s.consumeHISTORY("C:\\Users\\Matt\\Documents\\b6scandata\\HISTORY.csv");
//		ScannerDTO b = s.getScannerData(null, null, "279310459");
//		System.out.println(b);
		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-11.csv");
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-11.csv");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-12.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-12.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-13.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-13.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-14.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-14.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-15.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-15.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-16.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-16.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-17.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-17.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-18.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-18.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-19.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-19.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-20.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-20.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-21.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-21.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-22.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-22.del");
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-23.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-23.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-24.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-24.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-25.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-25.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-26.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-26.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-27.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-27.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-28.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-28.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-29.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-29.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-30.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-30.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-01-31.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-01-31.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-01.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-01.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-02.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-02.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-03.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-03.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-04.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-04.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-05.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-05.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-06.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-06.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-07.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-07.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-08.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-08.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-09.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-09.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-10.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-10.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-11.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-11.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-12.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-12.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-13.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-13.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-14.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-14.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-15.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-15.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-16.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-16.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-17.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-17.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-18.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-18.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-19.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-19.del");
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-20.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-20.del");	
//		
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-21.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-21.del");	
//
//		s.consumeBAG_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\TBAG_2012-02-22.del");	
//		s.consumeHISTORY_B("C:\\Users\\Matt\\Documents\\b6scandata\\all\\THISTORY_2012-02-22.del");	
	}
}
