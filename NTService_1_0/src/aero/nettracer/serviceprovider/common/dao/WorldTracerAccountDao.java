package aero.nettracer.serviceprovider.common.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;

public class WorldTracerAccountDao {

	public static List<WorldTracerWebAccount> getByProfile(Session sess, long profile) {
		Query q = sess.getNamedQuery(WorldTracerWebAccount.LOAD_BY_PROFILE);
		q.setBigInteger("profile", BigInteger.valueOf(profile));
		return (List<WorldTracerWebAccount>) q.list();
	}

}
