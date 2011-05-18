package aero.nettracer.general.services;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;

@Stateless
public class GeneralServiceBean implements GeneralServiceRemote{
	@Override
	public String echo(String s) {
		return "echo: " + s;
	}

	@Override
	public List<CountryCode> getCountries() {
		
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.CountryCode";
		Query q = sess.createQuery(sql);
		List<CountryCode> countryList = null;
		
		try{
			countryList = (List<CountryCode>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return countryList;
	}

	@Override
	public List<Station> getStations(String companycode) {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.Station s where s.company.companyCode_ID = :companycode";
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
		List<Station> stationList = null;
		try{
			stationList = (List<Station>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return stationList;
	}

	@Override
	public List<State> getState() {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.State";
		Query q = sess.createQuery(sql);
		List<State> stateList = null;
		
		try{
			stateList = (List<State>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return stateList;
	}
}
