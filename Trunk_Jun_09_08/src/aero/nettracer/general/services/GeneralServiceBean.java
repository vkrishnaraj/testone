package aero.nettracer.general.services;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.db.Agent;
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
	public List<Station> getStations(String companycode, String associated_airport) {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.Station s where s.company.companyCode_ID = :companycode" +
				" and s.associated_airport = :associated_airport";
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
		q.setParameter("associated_airport", associated_airport);
		List<Station> stationList = null;
		try{
			stationList = (List<Station>)q.list();
			if(stationList == null){
				System.out.println("general.getStations : " + (companycode!=null?companycode:"null") + (associated_airport!=null?associated_airport:"null") );
			}
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
	
	public Agent getAgent(String username, String companycode_ID){
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.Agent a " +
				"where a.username = :username and a.companycode_ID = :companycode_ID";
		Query q = sess.createQuery(sql);
		q.setParameter("username", username);
		q.setParameter("companycode_ID", companycode_ID);
		
		Agent agent = null;
		try{
			agent = (Agent)q.uniqueResult();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return agent;
	}
	
	public static void main(String [] args){
		GeneralServiceBean bean = new GeneralServiceBean();
		System.out.println(bean.getAgent("ntadmin", "B6").getAgent_ID());
	}
}
