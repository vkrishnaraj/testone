package aero.nettracer.fs.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.File;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
/**
 * 
 * @author SeanFine
 * 
 * Class used for Data Retention for FS Files, used to delete old data and gets deleted Dates
 *
 */
public class DataRetentionUtil {

	/**
	 * 
	 * @return Date for deleted for FS Files
	 * @throws Exception
	 */
	private Date getDeleteDate() throws Exception{
		int years = PropertyBMO.getValueAsInt(PropertyBMO.FS_RETENTION_YEARS);
		if(years > 0 && !PropertyBMO.isTrue(PropertyBMO.NT_USER)){
			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.YEAR, -years);
			System.out.println(cal.getTime());
			return cal.getTime();
		} else {
			throw new Exception("unable to determine retention policy");
		}
	}
	
	/**
	 * Method to delete old FS files that exist beyond the years of the company's Airline Data retention years.
	 * ie. FSFiles created older than 5 Retention Years, will be deleted.
	 */
	public void deleteOldFiles(){
		String sql = "select f.id from aero.nettracer.fs.model.File f left outer join f.incident i left outer join f.claims c" +
				" where (i.timestampOpen < :date or c.claimDate < :date) ";
		
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Query q = sess.createQuery(sql);

			q.setParameter("date", getDeleteDate());

			List<Long> list = (List<Long>)q.list();
			
			for(Long id:list){
				System.out.println("deleting: " + id);
				if(!deleteFile(id)){
//					throw new Exception("failed to delete file:" + id);
					System.out.println("Failed to delete file:"+id);
				}
			}
			
			ClaimUtils.enterAuditClaimEntry(-1, TracingConstants.FS_AUDIT_ITEM_TYPE_PROCESS, -1, TracingConstants.FS_ACTION_DATA_RETENTION_SCAN);
			
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (sess != null) {
				sess.close();
			}
		}
		
	}
	
	/**
	 * 
	 * @param id - Identifier for the file to be deleted
	 * @return Whether or not the file was successfully deleted
	 */
	public boolean deleteFile(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			File file = (File)sess.load(File.class, id);
			if(file != null){
				try{
					Transaction t = sess.getTransaction();
					t.begin();
					sess.delete(file);
					t.commit();
					sess.close();
					//TODO get admin user
					ClaimUtils.enterAuditClaimEntry(-1, TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, id, TracingConstants.FS_ACTION_DELETE);
					return true;
				} catch (Exception e){
					e.printStackTrace();
					sess.close();
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
			return false;
		} finally {
			if (sess != null && sess.isOpen()) {
				sess.close();
			}
		}
	}
	
}
