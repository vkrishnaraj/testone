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

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.MaintainCompanyForm;

public class LzUtils {
	
	private static Logger logger = Logger.getLogger(LzUtils.class);


	public static Lz getLz(Station station) {
		return getLz(station.getLz_ID());
	}
	
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
	
	public static ArrayList getIncidentLzStationsBeans(String companyCode) {
		ArrayList al = new ArrayList();

		List tmpList = getIncidentLzStations(companyCode);

		for (Iterator i = tmpList.iterator(); i.hasNext();) {
			Lz lz = (Lz) i.next();
			String label = lz.getStation().getStationcode();
			al.add(new LabelValueBean(label, new Integer(lz.getLz_ID()).toString()));
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
			Station station = StationBMO.getStation(stationId);
			lz.setStation(station);
			lz.setCompanyCode_ID(station.getCompany().getCompanyCode_ID());
			lz.setPercent_load(0);
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
				Lz lz = LzUtils.getLz(station);
				int formLz = new Integer(request.getParameter("station[" + i + "].lz")).intValue();
				if (lz.getLz_ID() != formLz) {
					// Check to see if the station has changed.
					station.setLz_ID(formLz);
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
	
	public static boolean updateLzList(MaintainCompanyForm form, HttpServletRequest request, Agent user) {
		Session sess = null;
		Transaction t = null;
		List<Lz> lzList = form.getLzStations();
		ArrayList<Lz> deleteThese = new ArrayList();
		Lz defaultLz = null;
		boolean needNewDefault = false;
		boolean newDefaultAdded = false;
		boolean deletingAllLz = true;

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			// Determine if we need to update the default lz
			for (int i=0; i<lzList.size(); ++i) {
				Lz lz = lzList.get(i);
				String deleteString = request.getParameter("lz[" + i + "].delete");
				
				if (deleteString != null && deleteString.length() > 0) {
					deleteThese.add(lz);
					if (form.getDefaultLz().intValue() == lz.getLz_ID()) {
						needNewDefault = true;
					}
				} else {
					deletingAllLz = false;
				}
			}
			
			if (deletingAllLz) {
				return false;
			}
			
			// Update LZ Data
			for (int i=0; i<lzList.size(); ++i) {
				Lz lz = lzList.get(i);
				
				
				String percentString = request.getParameter("lz[" + i + "].percent");

				if (percentString != null) {
					lz.setPercent_load(new Double(percentString).doubleValue());	
				}
				
				if (needNewDefault) {
					 if (!deleteThese.contains(lz) && !newDefaultAdded) {
						lz.setIs_default(true);
						defaultLz = lz;
						newDefaultAdded = true;
					 } else {
						 lz.setIs_default(false);
					 }
				} else {
					if (form.getDefaultLz().intValue() == lz.getLz_ID()) {
						lz.setIs_default(true);
						defaultLz = lz;
					} else {
						lz.setIs_default(false);
					}
				}
			}
			
			// Update stations assigned to deleted LZs to the new default.
			// if assigned lz is in delete these
			// change to new default
			List<Station> stationList = AdminUtils.getCustomStations(null, form.getCompanyCode(), 0, 0, TracingConstants.AgentActiveStatus.ALL);
			for (int i=0; i<stationList.size(); ++i) {
				Station station = stationList.get(i);
				Lz lz = LzUtils.getLz(station);
				if (deleteThese.contains(lz)) {
					station.setLz_ID(defaultLz.getLz_ID());
					HibernateUtils.saveStation(station, user);

				}
			}
			
			// Save/Delete LZs
			for (int i=0; i<lzList.size(); ++i) {
				Lz lz = lzList.get(i);
				
				if (deleteThese.contains(lz)) {
					// Delete LZ
					t = sess.beginTransaction();
					sess.delete(lz);
					t.commit();
				} else {
					t = sess.beginTransaction();
					sess.saveOrUpdate(lz);
					t.commit();
				}
			}
			
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			return true;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
					return false;
				}
			}
		}
		return true;
	}
	
	public static List getIncidentLzStations(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Lz.class);
			cri.add(Expression.eq("companyCode_ID", companyCode));
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