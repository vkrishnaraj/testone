package com.bagnet.nettracer.tracing.utils;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.struts.action.ActionMessages;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;

public class SecurityUtilsTest {

	private static String TEA_PASSWORD = "D6:19:A1:A1:C3:2F:26:26:B:50:93:FF:28:C0:7D:2C:";//Password12!
	private static String SHA1_PASSWORD = "C81B60EDC2B694751BD9A1742418E1C03E2604F6";//Password12!
	private static String JUNIT_USER = "junitagent";
	private static String ACCEPTABLE_PASSWORD = "Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@Ab1@";
	
	private static boolean setPassword(String password){
		String sql = "update agent set password=:password,reset_password=0  where username = :user and companycode_ID = 'OW'";
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("password", password);
			q.setParameter("user", JUNIT_USER);
			t = sess.beginTransaction();
			q.executeUpdate();
			t.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			if(sess != null){
				sess.close();
			}
		}
		return false;
	}
	
	@Test
	public void loginTEStoSHA1passResetTest(){
		String companyCode = "OW";
		String password = "Password12!";
		ActionMessages errors = new ActionMessages();
		
		
		assertTrue(setPassword(TEA_PASSWORD));
		Agent agent = SecurityUtils.authUser(JUNIT_USER, password, companyCode, 2, errors);
		assertTrue(agent != null);
		assertTrue(agent.isReset_password());
		
//		assertTrue(setPassword(SHA1_PASSWORD));
//		agent = SecurityUtils.authUser(JUNIT_USER, password, companyCode, 2, null);
//		assertTrue(agent != null);
//		assertTrue(!agent.isReset_password());
	}
	
//	@Test
	public void lastXPasswordsTest(){
		String companyCode = "OW";
		String password = "Password12!";
		assertTrue(setPassword(SHA1_PASSWORD));
		Agent agent = SecurityUtils.authUser(JUNIT_USER, password, companyCode, 2, null);
		assertTrue(agent!=null);
		
		String newPassword1 = StringUtils.sha1("password" + (new Date()).getTime(), true);
		assertTrue(SecurityUtils.insertPasswordHistory(agent.getAgent_ID(), newPassword1));//3
		
		String newPassword2 = StringUtils.sha1("password" + (new Date()).getTime(), true);
		assertTrue(SecurityUtils.insertPasswordHistory(agent.getAgent_ID(), newPassword2));//2
		
		String newPassword3 = StringUtils.sha1("password" + (new Date()).getTime(), true);
		assertTrue(SecurityUtils.insertPasswordHistory(agent.getAgent_ID(), newPassword3));//1
		
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 3, newPassword3));
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 2, newPassword3));
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 1, newPassword3));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 0, newPassword3));
		
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 3, newPassword2));
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 2, newPassword2));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 1, newPassword2));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 0, newPassword2));
		
		assertTrue(SecurityUtils.lastXPasswords(agent.getAgent_ID(), 3, newPassword1));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 2, newPassword1));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 1, newPassword1));
		assertTrue(!SecurityUtils.lastXPasswords(agent.getAgent_ID(), 0, newPassword1));
	}
	
//	@Test
	public void isPolicyAcceptablePasswordTest(){
		Company comp = CompanyBMO.getCompany(TracerProperties.get("wt.company.code"));
		String passwordMinLengthFail = ACCEPTABLE_PASSWORD.substring(0, comp.getVariable().getMin_pass_size() - 1);
		String passwordMinLengthPass = ACCEPTABLE_PASSWORD.substring(0, comp.getVariable().getMin_pass_size());
		
		assertTrue(!SecurityUtils.isPolicyAcceptablePassword(TracerProperties.get("wt.company.code"), passwordMinLengthFail, JUNIT_USER, null, true));
		assertTrue(SecurityUtils.isPolicyAcceptablePassword(TracerProperties.get("wt.company.code"), passwordMinLengthPass, JUNIT_USER, null, true));
	}
}
