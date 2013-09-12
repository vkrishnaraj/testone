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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.DocumentTemplateDAO;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplateVar;
import com.bagnet.nettracer.tracing.dto.DocumentTemplateSearchDTO;

public class DocumentTemplateDAOImpl implements DocumentTemplateDAO {

	private static Logger logger = Logger.getLogger(DocumentTemplateDAOImpl.class);
	
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	
	static {		
		map.put("id", 			"t.id");
		map.put("name", 		"name");
		map.put("createDate", 	"t.createDate");
		map.put("active", 		"t.active");
	}
	
	public DocumentTemplate load(long documentTemplateId) {
		DocumentTemplate template = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			template = (DocumentTemplate) session.get(DocumentTemplate.class, documentTemplateId);			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load document template with id: " + documentTemplateId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return template;
	}
	
	public boolean delete(long documentTemplateId) {
		boolean success = false;
		if (documentTemplateId <= 0) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			DocumentTemplate template = (DocumentTemplate) session.createCriteria(DocumentTemplate.class, "d").add(Restrictions.eq("d.id", documentTemplateId)).uniqueResult();
			session.delete(template);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete template with id: " + documentTemplateId, e);
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
	
	public boolean update(DocumentTemplate template) {
		boolean success = false;
		if (template == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			DocumentTemplate oldTemplate = (DocumentTemplate) session.get(DocumentTemplate.class, template.getId());
			if (oldTemplate == null) return false;			
			
			template.setCreateDate(oldTemplate.getCreateDate());
			template.setVariables(new LinkedHashSet<DocumentTemplateVar>(getVarsForDocumentTemplate(template)));
			BeanUtils.copyProperties(template, oldTemplate);
			
			transaction = session.beginTransaction();			
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
	
	public long save(DocumentTemplate template) {
		long id = 0;
		if (template == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();			
			template.setVariables(new LinkedHashSet<DocumentTemplateVar>(getVarsForDocumentTemplate(template)));
			session.save(template);
			transaction.commit();
			id = template.getId();
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
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentTemplateVar> getDocumentTemplateVars() {
		List<DocumentTemplateVar> vars = new ArrayList<DocumentTemplateVar>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(DocumentTemplateVar.class, "v");
			criteria.addOrder(Order.asc("v.associatedClass"));
			vars = criteria.list();
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load the Document Template Variables", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return vars;		
	}

	@Override
	public boolean haveDefinitionsForDocumentTemplateVars(List<String> vars) {
		if (vars == null || vars.isEmpty()) return true;
		for (String var: vars) {
			if (getVarFromData(var) == null) {
				return false;
			}
		}
		return true;
	}

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

	@Override
	public int getDocumentTemplateCount(DocumentTemplateSearchDTO dto) {
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
				logger.error("Received null result for count in DocumentTemplateDAOImpl.getDocumentTemplateCount");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to retrieve the document template count", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentTemplate> listDocumentTemplates(DocumentTemplateSearchDTO dto) {
		List<DocumentTemplate> results = new ArrayList<DocumentTemplate>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromDto(session, dto);
			applyOrder(dto, criteria);
			results = (List<DocumentTemplate>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of document templates", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	private Criteria getCriteriaFromDto(Session session, DocumentTemplateSearchDTO dto) {
		Criteria criteria = session.createCriteria(DocumentTemplate.class, "t");
		
		if (dto.getId() > 0) {
			criteria.add(Restrictions.eq("t.id", dto.getId()));
		}
		
		if (dto.getName() != null && !dto.getName().isEmpty()) {
			criteria.add(Restrictions.like("t.name", dto.getName()));
		}
		
		if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
			criteria.add(Restrictions.like("t.description", "%" + dto.getDescription() + "%"));
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
	
	private void applyOrder(DocumentTemplateSearchDTO dto, Criteria criteria) {
		String orderVar = DocumentTemplateDAOImpl.map.get(dto.getSort());
		if (orderVar == null) {
			orderVar = "t.id";
			logger.warn("Illegal sort value found for document templates: " + dto.getSort());
		}
		
		String dir = dto.getDir();
		if (!TracingConstants.SORT_ASCENDING.equals(dir) && !TracingConstants.SORT_DESCENDING.equals(dir)) {
			dir = TracingConstants.SORT_ASCENDING;
			logger.warn("Illegal sort direction value found for document templates: " + dto.getDir());
		}
		
		criteria.addOrder(TracingConstants.SORT_ASCENDING.equals(dir) ? Order.asc(orderVar) : Order.desc(orderVar));
	}
	
	private List<DocumentTemplateVar> getVarsForDocumentTemplate(DocumentTemplate template) {
		ArrayList<DocumentTemplateVar> toReturn = new ArrayList<DocumentTemplateVar>();
		String[] vars = StringUtils.substringsBetween(template.getData(), "{", "}");
		if (vars != null && vars.length > 0) {
			LinkedHashMap<String, DocumentTemplateVar> processed = new LinkedHashMap<String, DocumentTemplateVar>();
			for (String var: vars) {
				if (!processed.containsKey(var)) {
					DocumentTemplateVar fromDb = getVarFromData(var);
					toReturn.add(fromDb);
					processed.put(var, fromDb);
				}
			}
		}
		return toReturn;
	}

	private DocumentTemplateVar getVarFromData(String fromData) {
		DocumentTemplateVar fromDb = null;
		Session session = null;
		try {
			String[] tokens = fromData.split("\\.");
			String associatedClass = tokens[0];
			String displayTag = tokens[1];
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(DocumentTemplateVar.class, "v");
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.eq("v.displayTag", displayTag));
			and.add(Restrictions.eq("v.associatedClass", associatedClass));
			criteria.add(and);
			fromDb = (DocumentTemplateVar) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("An error occurred while trying to load variable: " + fromData, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return fromDb;
	}
}
