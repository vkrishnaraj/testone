package aero;

import java.util.List;

import javax.management.Query;
import javax.transaction.Transaction;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.Test;



import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class FraudModel {
	//@Test
	public void run(){
		try{
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from aero.nettracer.fs.model.Reservation";
		org.hibernate.Query q = sess.createQuery(sql.toString());

		List<Reservation> result = q.list();
		
		org.hibernate.Transaction t; 
		t = sess.beginTransaction();
		for(Reservation r:result){
			System.out.println(r.getId());
//			if(r.getExpirationDate() != null){
//				String m = r.getExpirationDate().substring(0, 2);
//				String y = r.getExpirationDate().substring(2,4);
//				r.setCcExpMonth(new Integer(m));
//				r.setCcExpYear(new Integer(y));
//				
//				sess.saveOrUpdate(r);
//				
//			}
		}
		t.commit();
		sess.close();
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
//	@Test
	public void run2(){
		try{
			Session sess = HibernateWrapper.getSession().openSession();
			String sql = "from aero.nettracer.fs.model.Reservation";
			org.hibernate.Query q = sess.createQuery(sql.toString());

			List<Reservation> result = q.list();
			
			org.hibernate.Transaction t; 
			t = sess.beginTransaction();
			for(Reservation r:result){
				Person p = new Person();
//				p.setFirstName(r.getCcFName());
//				p.setLastName(r.getCcLName());
//				p.setMiddleName(r.getCcMName());
				r.setPurchaser(p);
				sess.saveOrUpdate(r);
			}
			t.commit();
			sess.close();
			
			} catch (Exception e){
				e.printStackTrace();
			}
	}
	
	@Test
	public void testMe() {
		for (int i=0; i< 10*5; ++ i) {
			createFiles();
		}
	}
	
//	@Test
	public void createFiles(){
		try{
			Session sess = HibernateWrapper.getSession().openSession();
			String sql = "select c.id as c, i.id as i from fsincident i left outer join fsclaim c on i.claim_id = c.id where i.file_id is  null limit 10000";
//			String sql = "select c.id as c, i.id as i from fsincident i left outer join fsclaim c on i.claim_id = c.id where i.id >= 100000 and i.id < 200000";
//			String sql = "select c.id as c, i.id as i from fsincident i left outer join fsclaim c on i.claim_id = c.id where i.id >= 200000";
			org.hibernate.SQLQuery q = sess.createSQLQuery(sql.toString());
			org.hibernate.Transaction t; 
			
			q.addScalar("c", Hibernate.LONG);
			q.addScalar("i", Hibernate.LONG);
			
			List<Object[]> result = q.list();
			
			t = sess.beginTransaction();
			
			int count = 0;
			for (Object[] strs : result) {
				Long c = (Long) strs[0];
				Long i = (Long) strs[1];
				
				count ++;
				if(count%1000 ==0){
					System.out.println(c + " " + i);
				}

				
				File file = new File();
				if(c != null){
					FsClaim claim = TraceWrapper.loadClaim(c);
					claim.setFile(file);
					file.setClaim(claim);
					if(claim.getIncident() != null){
						claim.getIncident().setFile(file);
						file.setIncident(claim.getIncident());
					}
				} else if(i != null){
					FsIncident incident = TraceWrapper.loadIncident(i);
					incident.setFile(file);
					file.setIncident(incident);
				}
				sess.saveOrUpdate(file);
			}
			
			t.commit();
			sess.close();
			
			} catch (Exception e){
				e.printStackTrace();
			}
	}
}
