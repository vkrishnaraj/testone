package aero.nettracer.fs.utilities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.detection.AddressWhiteList;
import aero.nettracer.fs.model.detection.IPWhiteList;
import aero.nettracer.fs.model.detection.PhoneWhiteList;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class WhiteListUtil {

	public static String normalizeAddress(GeoParsedAddress address, String city, String state, String zip){
		if(address != null){
			String ret = (address.getPrefix() != null?address.getPrefix():"")
						+(address.getNum() != null?address.getNum():"")
						+(address.getName() != null?address.getName():"")
						+(address.getType() != null?address.getType():"")
						+(address.getSuffix() != null?address.getSuffix():"")
						+(address.getUnit() != null?address.getUnit():"")
						+(city != null?city:"")
						+(state != null?state:"")
						+(zip != null?zip:"");
			return ret.replaceAll(" ","").toUpperCase();
		}
		return null;
	}
	
	public static String normalizeAddress(FsAddress address){
		GeoParsedAddress parsedAddress = GeoCode.parse(address.getAddress1(), address.getCity(), address.getState(), address.getZip());
		return normalizeAddress(parsedAddress, address.getCity(), address.getState(), address.getZip());
		
//		String ret = (address.getAddress1()!=null?address.getAddress1():"")
//					+(address.getAddress2()!=null?address.getAddress2():"")
//					+(address.getCity()!=null?address.getCity():"")
//					+(address.getState()!=null?address.getState():"")
//					+(address.getZip()!=null?address.getZip():"");
//		
//		ret = ret.replaceAll(" ","").replaceAll("[\\.#\"><%!@$%^&*()_+-/]", "").toUpperCase();
//		
//		return ret;
	}
	
	public static AddressWhiteList isAddressWhiteListed(FsAddress address){
		Session sess = null;
		String nAddress = normalizeAddress(address);
		address.setNormAddress(nAddress);
		if(nAddress == null){
			return null;
		}
		try{
			sess = HibernateWrapper.getSession().openSession();
			String sql = "from aero.nettracer.fs.model.detection.AddressWhiteList a where a.address = :address";
			Query q = sess.createQuery(sql);
			q.setParameter("address", nAddress);
			List result = q.list();
			if(result != null && result.size() > 0){
				return (AddressWhiteList)result.get(0);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		return null;
	}
	
	public static boolean isAddressWhiteListed(GeoParsedAddress parsedAddress, FsAddress address){
		Session sess = null;
		String nAddress = normalizeAddress(parsedAddress, address.getCity(), address.getState(), address.getZip());
		if(nAddress == null){
			return false;
		}
		try{
			sess = HibernateWrapper.getSession().openSession();
			String sql = "from aero.nettracer.fs.model.detection.AddressWhiteList a where a.address = :address";
			Query q = sess.createQuery(sql);
			q.setParameter("address", normalizeAddress(address));
			List result = q.list();
			if(result != null && result.size() > 0){
				return true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		return false;
	}
	
	public static long saveAddressWhiteList(AddressWhiteList awl, boolean whiteListExisting){
		Session sess = null;
		Transaction t = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		t = sess.beginTransaction();
		sess.saveOrUpdate(awl);
		t.commit();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		
		if(whiteListExisting){
			WhiteListUtil.whiteListAddress(awl.getAddress());
		}
		
		return awl.getId();
	}
	
	public static void whiteListAddress(String address){
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String sql = "update fsaddress set whiteListed=1 where address = " + address;
		Query q = sess.createSQLQuery(sql);
		q.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
	}
	
	public static void clearWhiteListedAddress(){
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String reset = "update fsaddress set whiteListed=0";
		String whiteList = "from aero.nettracer.fs.model.detection.AddressWhiteList";
		Query resetQ = sess.createSQLQuery(reset);
		resetQ.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
	}
	
	public static void whiteListAllAddress(){
		WhiteListUtil.clearWhiteListedAddress();
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String whiteList = "from aero.nettracer.fs.model.detection.AddressWhiteList";
		Query whiteListQuery = sess.createQuery(whiteList);
		
		List<AddressWhiteList> addresses = (List<AddressWhiteList>)whiteListQuery.list();
		if(addresses != null){
			for(AddressWhiteList address:addresses){
				WhiteListUtil.whiteListAddress(address.getAddress());
			}
		}
		
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
	}
	
	
	public static PhoneWhiteList isPhoneWhiteListed(String phone){
		if (phone != null) {
			Session sess = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				String sql = "from aero.nettracer.fs.model.detection.PhoneWhiteList p where p.phoneNumber = :phone";
				Query q = sess.createQuery(sql);
				q.setParameter("phone", phone.trim());
				List<PhoneWhiteList> result = q.list();
				if (result != null && result.size() > 0) {
					return (PhoneWhiteList) result.get(0);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sess != null) {
					sess.close();
				}
			}
		}
			
		return null;
	}
	
	
	public static IPWhiteList isIPWhiteListed(String ip){
		if (ip != null) {
			Session sess = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				String sql = "from aero.nettracer.fs.model.detection.IPWhiteList w where w.ipAddress = :ip";
				Query q = sess.createQuery(sql);
				q.setParameter("ip", ip.trim());
				List<IPWhiteList> result = q.list();
				if (result != null && result.size() > 0) {
					return (IPWhiteList) result.get(0);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sess != null) {
					sess.close();
				}
			}
		}
			
		return null;
	}
	
	
	
	public static long savePhoneWhiteList(PhoneWhiteList pwl, boolean whiteListExisting){
		Session sess = null;
		Transaction t = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		t = sess.beginTransaction();
		sess.saveOrUpdate(pwl);
		t.commit();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		
		if(whiteListExisting){
			WhiteListUtil.whiteListPhone(pwl.getPhoneNumber());
		}
		
		return pwl.getId();
	}
	
	public static void whiteListPhone(String phone){
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String sql = "update phone set whiteListed=1 where phoneNumber = " + phone;
		Query q = sess.createSQLQuery(sql);
		q.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		
	}
	
	public static void clearWhiteListedPhones(){
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String reset = "update phone set whiteListed=0";
		String whiteList = "from aero.nettracer.fs.model.detection.PhoneWhiteList";
		Query resetQ = sess.createSQLQuery(reset);
		resetQ.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
	}
	
	public static void whiteListAllPhones(){
		WhiteListUtil.clearWhiteListedPhones();
		Session sess = null;
		try{
		sess = HibernateWrapper.getSession().openSession();
		String whiteList = "from aero.nettracer.fs.model.detection.PhoneWhiteList";
		Query whiteListQuery = sess.createQuery(whiteList);
		
		List<PhoneWhiteList> phones = (List<PhoneWhiteList>)whiteListQuery.list();
		if(phones != null){
			for(PhoneWhiteList phone:phones){
				WhiteListUtil.whiteListPhone(phone.getPhoneNumber());
			}
		}
		
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
	}
	
}
