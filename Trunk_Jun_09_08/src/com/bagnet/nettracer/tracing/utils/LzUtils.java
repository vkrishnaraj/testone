package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DbLocale;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.forms.MaintainCompanyForm;

public class LzUtils {
	
	private static Logger logger = Logger.getLogger(LzUtils.class);

	public static Lz getLz(int key) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Lz.class);
			cri.add(Expression.eq("lz_ID", key));
			return (Lz)cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static ArrayList getIncidentLzStationsBeans() {
		ArrayList al = new ArrayList();

		List tmpList = getIncidentLzStations();

		for (Iterator i = tmpList.iterator(); i.hasNext();) {
			Lz lz = (Lz) i.next();
			al.add(new LabelValueBean(lz.getStation().getStationcode(), new Integer(lz.getLz_ID()).toString()));
		}
		return al;
	}
	
	public static void addNewLz(int stationId) {
		// Create a new LZ pointing to correct station
		// Save it.
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			// Insure station does not already exist as an LZ
			Criteria cri = sess.createCriteria(Lz.class);
			List<Lz> lzList = cri.list();
			
			for (int i=0; i<lzList.size(); ++i) {
				Lz tmp = lzList.get(i);
				// If the station already exists as an LZ, abort.
				if (tmp.getStation().getStation_ID() == stationId)
					return;
			}
			
			Lz lz = new Lz();
			Station station = TracerUtils.getStation(stationId);
			lz.setStation(station);
			lz.setPercent(0);
			lz.setIs_default(false);
			
			sess.save(lz);

		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static void updateStationAssignments(MaintainCompanyForm form, HttpServletRequest request, Agent user) {
		Session sess = null;
		Transaction t = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			List<Station> stationList = (List)request.getAttribute("stationList");
			for (int i=0; i<stationList.size(); ++i) {
				Station station = stationList.get(i);
				Lz lz = station.getLz();
				int formLz = new Integer(request.getParameter("station[" + i + "].lz")).intValue();
				if (lz.getLz_ID() != formLz) {
					// Check to see if the station has changed.
					Lz newLz = getLz(formLz);
					station.setLz(newLz);
					HibernateUtils.saveStation(station, user);
				}
			}
			
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static void updateLzList(MaintainCompanyForm form, HttpServletRequest request) {
		Session sess = null;
		Transaction t = null;
		List<Lz> lzList = form.getLzStations();

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			for (int i=0; i<lzList.size(); ++i) {
				Lz lz = lzList.get(i);
				
				String percentString = request.getParameter("lz[" + i + "].percent");
				String deleteString = request.getParameter("lz[" + i + "].delete");

				if (percentString != null) {
					lz.setPercent(new Double(percentString).doubleValue());	
				}
				
				if (deleteString != null && deleteString.length() > 0) {
					// Delete LZ
					t = sess.beginTransaction();
					sess.delete(lz);
					t.commit();
				} else {
					// Modify & Save LZ
					if (form.getDefaultLz().intValue() == lz.getLz_ID()) {
						lz.setIs_default(true);
					} else {
						lz.setIs_default(false);
					}
					t = sess.beginTransaction();
					sess.saveOrUpdate(lz);
					t.commit();
				}
			}
			
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static List getIncidentLzStations() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Lz.class);
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}


	public static int getDefaultLz(List<Lz> list) {
		for (int i=0; i<list.size(); ++i) {
			Lz tmp = list.get(i);
			
			if (tmp.isIs_default())
				return tmp.getLz_ID();
		}
		return 0;
	}
	
	public static void moveIncidentsToLz(List<Incident> incidentList) {
		// Determine method of distribution to use and built initialization data.
		
		// Call distribution algorithm
	}
	
	private static ArrayList<Bucket> percentDistribute(ArrayList<Incident> toSort, HashMap criteria) {
		
		// Get Criteria in the form of STATION > DOUBLE ( PERCENTAGE )
		
		ArrayList buckets = new ArrayList();
		Object key = null;
		int initialSize = 0;
		int toSortIndex = 0;
		
		double itemPercent = 100 / toSort.size();
		
		Iterator iter = criteria.keySet().iterator();
		while (iter.hasNext()) {
			key = iter.next();
			Bucket b = new Bucket();
			initialSize = b.initialize(key, toSort.size(), (Double)criteria.get(key));
			buckets.add(b);
			
			// Fill bucket
			for (int x=0; x<initialSize; ++x) {
				if (x+toSortIndex < toSort.size())
					b.add(toSort.get(x+toSortIndex));
			}
			toSortIndex += initialSize;
		}
		
		while (toSortIndex < toSort.size()) {
			double largestCapacity = 0;
			Bucket largestBucket = null;
			for (int i=0; i < buckets.size(); ++i) {
				Bucket currentBucket = (Bucket) buckets.get(i);
				double currentCapacity = currentBucket.getRemainingCapacity();
				if (currentCapacity > largestCapacity || largestBucket == null) {
					largestBucket = currentBucket;
					largestCapacity = currentCapacity;
				}
			}
			
			largestBucket.add(toSort.get(toSortIndex));
			++toSortIndex;
		}
			
		return buckets;
	}
}

class Bucket extends java.util.ArrayList {
	Object key;
	double percentOfWhole;
	int totalItems;
	
	// For use by assignment algorithm.
	public void initialize(Object key) {
		this.key = key;
	}
	
	// For use by percentage algorithm
	public int initialize (Object key, int totalItems, Double percentOfWhole) {
		this.key = key;
		this.totalItems = totalItems;
		this.percentOfWhole = percentOfWhole.doubleValue();
		return (int) (percentOfWhole / (100 / totalItems));
	}
	
	public double getRemainingCapacity() {
		return 100 - (100 / totalItems * this.size());
	}
	
	public Object getKey() {
		return this.key;
	}
	
	public boolean add(Object o) {
		return super.add(o);
	}
}