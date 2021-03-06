package aero.nettracer.fs.utilities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.FsActionAudit;
import aero.nettracer.fs.model.FsMatchHistoryAudit;
import aero.nettracer.fs.utilities.tracing.SaveAuditThread;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class AuditUtil {

	public static final String ACTION_CREATE_FILE = "create_file";
	public static final String ACTION_UPDATE_FILE = "update_file";
	public static final String ACTION_TRACE_FILE = "trace_file";
	public static final String ACTION_GET_FILE = "get_file";
	public static final String ACTION_GET_TRACE_RESULTS = "get_trace_results";
	public static final String ACTION_GET_AUTO_TRACE_RESULTS = "get_auto_trace_results";
	public static final String ACTION_REQUEST_ACCESS = "request_access";
	public static final String ACTION_GET_ACCESS_REQUESTS = "get_access_requests";
	public static final String ACTION_APPROVE_REQUEST = "approve_access_request";
	public static final String ACTION_DENY_REQUEST = "deny_access_request";
	public static final String ACTION_DELETE_MATCH = "delete_match";
	public static final String ACTION_DATARETENTION = "data_retention";
	public static final String ACTION_DELETE_FILE = "delete_file";
	
	public static final String METRIC_TOTAL_TRACE = "total_trace";
	public static final String METRIC_TOTAL_PRODUCER = "total_producer";
	public static final String METRIC_PRODUCER_GEO = "producer_geo";
	public static final String METRIC_PRODUCER_MAIN = "producer_main";
	public static final String METRIC_TOTAL_CONSUMER = "total_consumer";
	public static final String METRIC_TOTAL_USER_WAIT = "total_user_wait";
	
	
	public static FsActionAudit createAuditAction(String action, long file_id, String companycode_id, Set<FsMatchHistoryAudit> matches){
		FsActionAudit toSave = new FsActionAudit();
		toSave.setAction(action);
		toSave.setFile_id(file_id);
		toSave.setCompanycode_id(companycode_id);
		toSave.setActiondate(new Date());
		if(matches != null){
			for(FsMatchHistoryAudit match:matches){
				match.setAction(toSave);
			}
			toSave.setMatchdetails(matches);
		}
		return toSave;
	}
	
	
	public static long saveActionAudit(String action, long file_id, String companycode_id, Set<FsMatchHistoryAudit> matches){
		FsActionAudit toSave = new FsActionAudit();
		toSave.setAction(action);
		toSave.setFile_id(file_id);
		toSave.setCompanycode_id(companycode_id);
		toSave.setActiondate(new Date());
		if(matches != null){
			for(FsMatchHistoryAudit match:matches){
				match.setAction(toSave);
			}
			toSave.setMatchdetails(matches);
		}
		return saveActionAudit(toSave);
	}
	
	public static long saveActionAudit(String action, long file_id, String companycode_id){
		return saveActionAudit(action, file_id, companycode_id, null);
	}
	
	public static long saveActionAudit(FsActionAudit action, boolean async){
		if(async){
			SaveAuditThread sat = new SaveAuditThread(action);
			Thread t = new Thread(sat);
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
			return 0;
		} else {
			return AuditUtil.saveActionAudit(action);
		}
	}
	
	public static long saveActionAudit(FsActionAudit action){
		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(action);
			System.out.println("Saving audit action... " + action.getId());
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
				}
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
		return (action.getId()>0?action.getId():-1);
	}
	
	
}
