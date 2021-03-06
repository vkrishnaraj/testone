package aero.nettracer.general.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;

@Stateless
public class GeneralServiceBean implements GeneralServiceRemote{
	@Override
	public String echo(String s) {
		return "echo: " + s;
	}

	@Override
	public List<CountryCode> getCountries() {
		
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.CountryCode c order by c.country asc";
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
		String sql = "from com.bagnet.nettracer.tracing.db.Station s where s.company.companyCode_ID = :companycode order by s.stationdesc";
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
	public List<Station> getStations(String companycode,String subcompany) {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from com.bagnet.nettracer.tracing.db.lf.SubcompanyStation s where s.station.company.companyCode_ID = :companycode and s.subcompany.subcompanyCode=:subcompanycode order by s.station.stationdesc";
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
		q.setParameter("subcompanycode", subcompany);
		List<Station> stationList = new ArrayList<Station>();
		try{
			List<SubcompanyStation> substationList = (List<SubcompanyStation>)q.list();
			
			for(SubcompanyStation ss:substationList){
				stationList.add(ss.getStation());
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
	public List<Station> getStations(String companycode, List<String> associated_airports) {
		Session sess = HibernateWrapper.getSession().openSession();
		
		String sql = "from com.bagnet.nettracer.tracing.db.Station s where s.company.companyCode_ID = :companycode";
		// s.associated_airport in (:associated_airports)";
		if (associated_airports != null && !associated_airports.isEmpty()) {
			sql += " and (";
			for (String company: associated_airports) {
				sql += "s.associated_airport = \'" + company + "\' or ";
			}
			sql = sql.substring(0, sql.lastIndexOf(" or "));
			sql += ")";
		}
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
//		q.setParameter("associated_airports", companies);
		List<Station> stationList = null;
		try{
			stationList = (List<Station>)q.list();
			if(stationList == null){
				System.out.println("general.getStations : " + (companycode!=null?companycode:"null") + (associated_airports != null?associated_airports:"empty") );
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
	
	public static Agent getAgent(int agentid){
		if(agentid == 0){
			return null;
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		Agent a = null;
		List<Agent> list = null;
		try{
			Criteria crit = sess.createCriteria(Agent.class).add(Restrictions.eq("agent_ID", agentid));
			list = crit.list();
		}catch (HibernateException e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		if(list != null && list.size() > 0){
			a = list.get(0);
		}
		return a;
	}

	public String getCompanyFromSubCompany(String subcompany){
		return SubCompanyDAO.loadSubcompany(subcompany).getCompany().getCompanyCode_ID();
	}
}
