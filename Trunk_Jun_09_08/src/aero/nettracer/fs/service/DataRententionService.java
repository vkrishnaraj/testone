package aero.nettracer.fs.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

import aero.nettracer.fs.model.File;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsBean;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;

public class DataRententionService {
	private Date getDeleteDate(String companycode) throws Exception{
		PrivacyPermissionsBean bean = new PrivacyPermissionsBean();
		PrivacyPermissions p = bean.getPrivacyPermissions(companycode, PrivacyPermissions.AccessLevelType.def);
		if(p != null && p.getRetention() > 0){
			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.YEAR, -p.getRetention());
			System.out.println(cal.getTime());
			return cal.getTime();
		} else {
			throw new Exception("unable to determine retention policy for " + companycode);
		}
	}
	
	public void deleteOldFiles(String companycode){
		String sql = "select f.id from aero.nettracer.fs.model.File f left outer join f.incident i left outer join f.claims c" +
				" where f.validatingCompanycode = :companycode and (i.timestampOpen < :date or c.claimDate < :date) ";
		
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Query q = sess.createQuery(sql);

			q.setParameter("date", getDeleteDate(companycode));
			q.setParameter("companycode", companycode);

			List<Long> list = (List<Long>)q.list();
			
			for(Long id:list){
				System.out.println("deleting: " + id);
				if(!deleteFile(id)){
					throw new Exception("failed to delete file:" + id);
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	
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
			if (sess != null) {
				sess.close();
			}
		}
	}
}
