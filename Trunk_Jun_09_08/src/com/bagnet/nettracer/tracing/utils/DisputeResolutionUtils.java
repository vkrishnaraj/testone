/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.IncidentForm;


/**
 * @author void
 * for dispute resolution process - lock incident file to 
 * prevent further change on the fault station and fault code
 */
public class DisputeResolutionUtils {

	public static boolean isIncidentLocked(String incident_ID) {
		boolean result = false;
		
		Incident myIncident = IncidentUtils.findIncidentByID(incident_ID);
		
		if (myIncident != null) {
			if (myIncident.isLocked()) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isIncidentStationLocked(String incident_ID) {
		
		Incident myIncident = IncidentUtils.findIncidentByID(incident_ID);
		
		if (myIncident != null) {
			return myIncident.isStationLocked();
		}
		return false;
	}
	
	public static boolean isIncidentCodeLocked(String incident_ID) {
		Incident myIncident = IncidentUtils.findIncidentByID(incident_ID);
		
		if (myIncident != null) {
			return myIncident.isCodeLocked();
		}
		return false;
	}
	
	public static Incident lockIncident(String incident_ID, IncidentForm theForm){
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		Station faultStation = new Station();
		if(theForm != null && theForm.getFaultstation_id() > 0){
			faultStation = StationBMO.getStation(theForm.getFaultstation_id());
		}
		
		if (result != null) {
			if (! result.isLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					if(theForm != null){
						t = sess.beginTransaction();
						Station s = (Station) sess.load(Station.class, theForm.getFaultstation().getStation_ID());
						result.setFaultstation(s);
						result.setLoss_code(theForm.getLoss_code());
						sess.saveOrUpdate(result);
						t.commit();
					}
					t = sess.beginTransaction();
					result.setLocked(true);
					sess.update(result);
					t.commit();
					sess.close();
					sess = null;
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}	
		}
		
		return result;
	}
	
	public static Incident lockIncidentCode(String incident_ID, int loss_code){
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
				
		if (result != null) {
			if (! result.isCodeLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setLoss_code(loss_code);
					sess.saveOrUpdate(result);
					result.setCodeLocked(true);
					sess.update(result);
					t.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}	
		}
		
		return result;
	}
	
	public static Incident lockIncidentStation(String incident_ID, int stationID){
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
				
		if (result != null) {
			if (! result.isStationLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					Station s = (Station) sess.load(Station.class, stationID); 
					result.setFaultstation(s);
					sess.saveOrUpdate(result);
					result.setStationLocked(true);
					sess.update(result);
					t.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}	
		}
		
		return result;
	}
	
	public static Incident lockIncident(String incident_ID) throws HibernateException {
		return lockIncident(incident_ID, null);
	}
	
	public static Incident unlockIncident(String incident_ID) throws HibernateException {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			if (result.isLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setLocked(false);
					sess.update(result);
					t.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
			} 
		}
		
		return result;
	}
	
	public static Incident unlockIncidentCode(String incident_ID) throws HibernateException {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			if (result.isCodeLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setCodeLocked(false);
					sess.update(result);
					t.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
			} 
		}
		
		return result;
	}
	
	public static Incident unlockIncidentStation(String incident_ID) throws HibernateException {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			if (result.isStationLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setStationLocked(false);
					sess.update(result);
					t.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
			} 
		}
		
		return result;
	}

	public static Incident auditIncidentLockOrUnlock(String incident_ID, Agent agent) {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			Session sess = HibernateWrapper.getSession().openSession();
			Transaction t = null;
			try {
				t = sess.beginTransaction();
				IncidentBMO ibmo = new IncidentBMO();
				ibmo.saveAndAuditIncident(false, result, agent, sess);
				if (! t.wasCommitted()) {
					t.commit();
				}
				
				sess.close();
				sess = null;
			} catch (Exception e) {
				//catch stale state here
				e.printStackTrace();
				try {
					t.rollback();
				} catch (Exception ex) {
					// Fails
					ex.printStackTrace();
				}
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	 
		}		

		return result;
	}
	
	public static int getIncidentType(String incident_ID) {
		int myType = 1;
		
		Incident myIncident = IncidentUtils.findIncidentByID(incident_ID);
		
		if (myIncident != null) {
			myType = myIncident.getItemtype_ID();
		}
		return myType;
	}
	
	public static Incident lockIncidentWithAudit(String incident_ID, Agent agent) throws HibernateException {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			if (! result.isLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setLocked(true);
					sess.update(result);
					
					IncidentBMO incidentBMO = new IncidentBMO();
//					Incident inc = IncidentBMO.getIncidentByID(incident_ID, sess);
//					Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(inc, agent);
//					sess.save(audit_dto);
//					incidentBMO.saveAndAuditIncident(inc, agent, sess);
					incidentBMO.saveAndAuditIncident(false, result, agent, sess);
					
					if (!t.wasCommitted()) {
						t.commit();
					}
					
					sess.close();
					sess = null;
				} catch (Exception e) {
					//catch stale state here
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}	
		}
		
		return result;
	}
	
	public static Incident unlockIncidentWithAudit(String incident_ID, Agent agent) throws HibernateException {
		Incident result = IncidentUtils.findIncidentByID(incident_ID);
		
		if (result != null) {
			if (result.isLocked()) {
				Session sess = HibernateWrapper.getSession().openSession();
				Transaction t = null;
				try {
					t = sess.beginTransaction();
					result.setLocked(false);
					sess.update(result);
					
					IncidentBMO incidentBMO = new IncidentBMO();
//					Incident inc = IncidentBMO.getIncidentByID(incident_ID, sess);
//					Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(inc, agent);
//					sess.save(audit_dto);
//					incidentBMO.saveAndAuditIncident(inc, agent, sess);
					incidentBMO.saveAndAuditIncident(false, result, agent, sess);
					
					if (!t.wasCommitted()) {
						t.commit();
					}
					
					sess.close();
					sess = null;
				} catch (Exception e) {
					//catch stale state here
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
						// Fails
						ex.printStackTrace();
					}
				} finally {
					if (sess != null) {
						try {
							sess.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
			} 
		}
		
		return result;
	}
}