package aero.nettracer.fs.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.fs.model.FsNameAlias;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class FsNameAliasBMO {
	public static List<FsNameAlias> getAlias(String name){
		if(name != null && name.trim().length() > 0){
			List<FsNameAlias> ret = null;
			String sql = "from aero.nettracer.fs.model.FsNameAlias n where n.name = :name";
			Session sess = null;
			try{
				sess = HibernateWrapper.getSession().openSession();
				Query q = sess.createQuery(sql);
				q.setParameter("name", name);
				ret = q.list();
			}catch (Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
			return ret;
		} else {
			return null;
		}
	}
}
