package aero.nettracer.serviceprovider.common.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.WorldTracerISharesAccount;
import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;

public class WorldTracerAccountDao {

	public static List<WorldTracerWebAccount> getWtWebByProfile(Session sess, long profile) {
		Query q = sess.getNamedQuery(WorldTracerWebAccount.LOAD_BY_PROFILE);
		q.setBigInteger("profile", BigInteger.valueOf(profile));
		return (List<WorldTracerWebAccount>) q.list();
	}
	
	public static List<WorldTracerISharesAccount> getISharesByProfile(Session sess, long profile) {
		Query q = sess.getNamedQuery(WorldTracerISharesAccount.LOAD_BY_PROFILE);
		q.setBigInteger("profile", BigInteger.valueOf(profile));
		return (List<WorldTracerISharesAccount>) q.list();
	}

}
