package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.Depreciation_Category;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;

public class CategoryBMO {

	public static OHD_CategoryType getCategory(int code, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(
					Restrictions.eq("OHD_CategoryType_ID", new Integer(code)));
			@SuppressWarnings("unchecked")
			List<OHD_CategoryType> retList = cri.list();
			
			if (retList.size() == 1) {
				OHD_CategoryType tmp = (OHD_CategoryType) cri.list().get(0);
				tmp.setLocale(locale);
				return tmp;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static OHD_CategoryType getCategory(String code, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(
					Restrictions.eq("OHD_CategoryType_ID", new Integer(code)));
			OHD_CategoryType tmp = (OHD_CategoryType) cri.list().get(0);
			tmp.setLocale(locale);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static OHD_CategoryType getCategoryByWT(String code, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(
					Restrictions.eq("wtCategory", code));
			OHD_CategoryType tmp = (OHD_CategoryType) cri.list().get(0);
			tmp.setLocale(locale);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Category getCategory(int categoryID) {
		Session sess = null;
		
		try {
			if(categoryID!=0){
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Category.class).add(
					Restrictions.eq("id", Long.valueOf(categoryID)));
			Category tmp = (Category) cri.list().get(0);
			return tmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<Depreciation_Category> getDepreciationCategories(String companycode){
		String sql = "from com.bagnet.nettracer.tracing.db.Depreciation_Category dc where dc.companyCode=:companycode";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("companycode", companycode);
			@SuppressWarnings("unchecked")
			List<Depreciation_Category> ilist= (List<Depreciation_Category>) q.list();
			return ilist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Depreciation_Category getDepreciationCategory(int categoryID) {
		Session sess = null;
		
		try {
			if(categoryID!=0){
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Depreciation_Category.class).add(
					Restrictions.eq("id", categoryID));
			Depreciation_Category tmp = (Depreciation_Category) cri.list().get(0);
			return tmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<Depreciation_Item> getDeprecItemsByCategory(int catId) {
		String sql = "from com.bagnet.nettracer.tracing.db.Depreciation_Item di where di.category.id=:catId and di.category.id!=0";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			q.setParameter("catId", catId);
			@SuppressWarnings("unchecked")
			List<Depreciation_Item> ilist= (List<Depreciation_Item>) q.list();
			return ilist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
