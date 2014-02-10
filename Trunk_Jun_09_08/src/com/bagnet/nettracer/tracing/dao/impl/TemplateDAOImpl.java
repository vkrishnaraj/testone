package com.bagnet.nettracer.tracing.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.TemplateDAO;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVarDependency;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;

public class TemplateDAOImpl implements TemplateDAO {

	private static Logger logger = Logger.getLogger(TemplateDAOImpl.class);
	
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	
	static {		
		map.put("id", 			"t.id");
		map.put("name", 		"t.name");
		map.put("createDate", 	"t.createDate");
		map.put("active", 		"t.active");
	}
	
	@Override
	public Template load(long templateId) {
		Template template = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			template = (Template) session.get(Template.class, templateId);			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load template with id: " + templateId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return template;
	}
	
	@Override
	public boolean delete(long templateId) {
		boolean success = false;
		if (templateId <= 0) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			Template template = (Template) session.createCriteria(Template.class, "t").add(Restrictions.eq("t.id", templateId)).uniqueResult();
			session.delete(template);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete template with id: " + templateId, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	public boolean update(Template template) {
		boolean success = false;
		if (template == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			Template oldTemplate = (Template) session.get(Template.class, template.getId());
			if (oldTemplate == null) return false;			
			transaction = session.beginTransaction();		
			
			template.setCreateDate(oldTemplate.getCreateDate());
			template.setVariables(new LinkedHashSet<TemplateVar>(getVarsForDocumentTemplate(template)));
			BeanUtils.copyProperties(template, oldTemplate);
			
			session.merge(template);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to update template with id: " + template.getId(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	public long save(Template template) {
		long id = 0;
		if (template == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();			
			template.setVariables(new LinkedHashSet<TemplateVar>(getVarsForDocumentTemplate(template)));
			session.save(template);
			transaction.commit();
			id = template.getId();
		} catch (Exception e) {
			logger.error("Failed to save template: " + template.getName(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getTemplateVars()
	 */
	@SuppressWarnings("unchecked")
	public List<TemplateVar> getTemplateVars() {
		List<TemplateVar> vars = new ArrayList<TemplateVar>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(TemplateVar.class, "v");
			criteria.addOrder(Order.asc("v.associatedClass"));
			criteria.addOrder(Order.asc("v.displayTag"));
			vars = criteria.list();
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load the Template Variables", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return vars;		
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#haveDefinitionsForTemplateVars(java.util.List)
	 */
	@Override
	public boolean haveDefinitionsForTemplateVars(List<String> vars) {
		if (vars == null || vars.isEmpty()) return true;
		for (String var: vars) {
			if (getVarFromData(var) == null) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getFirstInvalidVar(java.util.List)
	 */
	@Override
	public String getFirstInvalidVar(List<String> vars) {
		if (vars == null || vars.isEmpty()) return null;
		for (String var: vars) {
			if (getVarFromData(var) == null) {
				return var;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getTemplateCount(com.bagnet.nettracer.tracing.dto.TemplateSearchDTO)
	 */
	@Override
	public int getTemplateCount(TemplateSearchDTO dto) {
		Session session = null;
		int count = 0;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromDto(session, dto);
			criteria.setProjection(Projections.rowCount());
			Object result = criteria.uniqueResult();
			if (result != null) {
				count = ((Long) result).intValue();
			} else {
				logger.error("Received null result for count in TemplateDAOImpl.getDocumentTemplateCount");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to retrieve the template count", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#listTemplates(com.bagnet.nettracer.tracing.dto.TemplateSearchDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listTemplates(TemplateSearchDTO dto) {
		List<Template> results = new ArrayList<Template>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromDto(session, dto);
			applyOrder(dto, criteria);
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("t.id").as("id"))
					.add(Projections.property("t.name").as("name"))
					.add(Projections.property("t.description").as("description"))
					.add(Projections.property("t.active").as("active"))
					.add(Projections.property("t.createDate").as("createDate"))
					.add(Projections.property("t.lastUpdated").as("lastUpdated"))
					.add(Projections.property("t.data").as("data")));
			criteria.setResultTransformer(Transformers.aliasToBean(Template.class));
			results = (List<Template>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of templates", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getTemplateTypeMapping(com.bagnet.nettracer.tracing.enums.TemplateType)
	 */
	@Override
	public TemplateTypeMapping getTemplateTypeMapping(TemplateType type) {
		TemplateTypeMapping mapping = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(TemplateTypeMapping.class, "tt");
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.eq("tt.ordinal", type.ordinal()));
			and.add(Restrictions.eq("tt.defaultName", type.getDefaultName()));
			criteria.add(and);
			mapping = (TemplateTypeMapping) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Failed to load TemplateTypeMapping", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return mapping;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getTemplatesByType(java.util.List)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Template> getActiveTemplatesByType(TemplateType type) {
		List<Template> templates = new ArrayList<Template>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select id from template where typeAvailableFor = :ordinal and active = 1";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("ordinal", type.getOrdinal());
			query.addScalar("id", StandardBasicTypes.LONG);
			List ids = query.list();
			
			if (ids != null && !ids.isEmpty()) {
				Query hql = session.createQuery("from Template t where t.id in :ids");
				hql.setParameterList("ids", ids);
				templates = hql.list();
			}
		} catch (Exception e) {
			logger.error("Failed to load templates from the given types", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return templates;
	}
	
	private Criteria getCriteriaFromDto(Session session, TemplateSearchDTO dto) {
		Criteria criteria = session.createCriteria(Template.class, "t");
		
		if (dto.getId() > 0) {
			criteria.add(Restrictions.eq("t.id", dto.getId()));
		}
		
		if (dto.getName() != null && !dto.getName().isEmpty()) {
			criteria.add(Restrictions.ilike("t.name", "%" + dto.getName() + "%"));
		}
		
		if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
			criteria.add(Restrictions.ilike("t.description", "%" + dto.getDescription() + "%"));
		}
		
		if (dto.getActive() != TracingConstants.ACTIVE_SEARCH_BOTH && dto.getActive() != 0) {
			criteria.add(Restrictions.eq("t.active", dto.getActive() == TracingConstants.ACTIVE_SEARCH_ACTIVE));
		}
		
		Date start = dto.getStartCreateDate();
		Date end = dto.getEndCreateDate();
		if (start != null && end != null) {
			criteria.add(Restrictions.between("t.createDate", start, end));
		} else if (start != null) {
			criteria.add(Restrictions.ge("t.createDate", start));
		} else if (end != null) {
			criteria.add(Restrictions.lt("t.createDate", end));
		}
		
		if (dto.getRowsPerPage() > 0) {
			criteria.setFirstResult(dto.getCurrentPage() * dto.getRowsPerPage());
			criteria.setMaxResults(dto.getRowsPerPage());
		}
		
		return criteria;
	}
	
	private void applyOrder(TemplateSearchDTO dto, Criteria criteria) {
		String orderVar = TemplateDAOImpl.map.get(dto.getSort());
		if (orderVar == null) {
			orderVar = "t.id";
			logger.warn("Illegal sort value found for templates: " + dto.getSort());
		}
		
		String dir = dto.getDir();
		if (!TracingConstants.SORT_ASCENDING.equals(dir) && !TracingConstants.SORT_DESCENDING.equals(dir)) {
			dir = TracingConstants.SORT_ASCENDING;
			logger.warn("Illegal sort direction value found for templates: " + dto.getDir());
		}
		
		criteria.addOrder(TracingConstants.SORT_ASCENDING.equals(dir) ? Order.asc(orderVar) : Order.desc(orderVar));
	}
	
	private List<TemplateVar> getVarsForDocumentTemplate(Template template) {
		ArrayList<TemplateVar> toReturn = new ArrayList<TemplateVar>();
		String[] vars = StringUtils.substringsBetween(template.getData(), "{", "}");
		if (vars != null && vars.length > 0) {
			LinkedHashMap<String, TemplateVar> processed = new LinkedHashMap<String, TemplateVar>();
			for (String var: vars) {
				if (!processed.containsKey(var)) {
					TemplateVar fromDb = getVarFromData(var);
					toReturn.add(fromDb);
					processed.put(var, fromDb);
				}
			}
		}
		return toReturn;
	}

	private TemplateVar getVarFromData(String fromData) {
		TemplateVar fromDb = null;
		Session session = null;
		try {
			String[] tokens = fromData.split("\\.");
			String associatedClass = tokens[0];
			String displayTag = tokens[1];
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(TemplateVar.class, "v");
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.eq("v.displayTag", displayTag));
			and.add(Restrictions.eq("v.associatedClass", associatedClass));
			criteria.add(and);
			fromDb = (TemplateVar) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("An error occurred while trying to load variable: " + fromData, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return fromDb;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getVarDependencies(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<TemplateVarDependency> getVarDependencies(List<String> classNames) {
		List<TemplateVarDependency> results = new ArrayList<TemplateVarDependency>();
		if (classNames == null || classNames.isEmpty()) return results;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(TemplateVarDependency.class, "d");
			criteria.add(Restrictions.in("d.associatedClass", classNames));
			results = (List<TemplateVarDependency>) criteria.list();
		} catch (Exception e) {
			logger.error("Error occurred while attempting to get template variable dependencies", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
}
