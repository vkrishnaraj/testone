package aero.nettracer;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class RegeocodeAddress {

	public static List<Long> getAddressId(){
		String sql = "select a.id from fsaddress a where a.lattitude = 0 and (address1 is not null and length(address1) > 0) and a.country = 'US'";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("id", Hibernate.LONG);
		List<Long> result = pq.list();
		sess.close();
		return result;
	}
	
	
	public static void updateGeocode(long addressId, int type, double lat, double lon){
		String sql = "update fsaddress set lattitude=:lat, longitude=:lon, geocodetype=:type where id = :id";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		pq = sess.createSQLQuery(sql.toString());
		pq.setParameter("lat", lat);
		pq.setParameter("lon", lon);
		pq.setParameter("type", type);
		pq.setParameter("id", addressId);
		pq.addScalar("id", Hibernate.LONG);
		pq.executeUpdate();
		t.commit();
		sess.close();
	}
	
	
	public static void geocodeAddress(long addressId){
		
		String sql = "select a.address1, a.city city, a.state, a.zip, a.province, a.country from fsaddress a where a.id = :id";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.setParameter("id", addressId);
		pq.addScalar("address1", Hibernate.STRING);
		pq.addScalar("city", Hibernate.STRING);
		pq.addScalar("state", Hibernate.STRING);
		pq.addScalar("zip", Hibernate.STRING);
		pq.addScalar("province", Hibernate.STRING);
		pq.addScalar("country", Hibernate.STRING);
		Object[] result = (Object[]) pq.uniqueResult();
		sess.close();
		
		try {
			GeoLocation loc = null;
			if(result[0] != null){
				loc = GeoCode.locate(((String)result[0]).replaceAll("[\\.#\"><%!@$%^&*()_+-/]", " "),
						(String)result[1], (String)result[2], (String)result[3], (String)result[4],
						(String)result[5], null);
			}

			if (loc != null) {
				updateGeocode(addressId, loc.getType(), loc.getLatitude(), loc.getLongitude());
			}
		} catch (InternationalException e) {
			// Ignore; not pertinent at this time.
		} catch (Exception e) {
			// Log error only
			e.printStackTrace();
		}
	}
	
	public static void run(){
		List<Long> addressIds = getAddressId();
		if(addressIds != null){
			System.out.println("Number of addresses to geocode: " + addressIds.size());
			int i = 0;
			for(Long addressId:addressIds){
				geocodeAddress(addressId);
				i++;
				if(i%20 == 0){
					System.out.println("Current count " + i + " / " + addressIds.size() );
				}
			}
		}
		System.out.println("Done");
	}
	
	public static void main(String [] args){
		run();
	}
	
}
