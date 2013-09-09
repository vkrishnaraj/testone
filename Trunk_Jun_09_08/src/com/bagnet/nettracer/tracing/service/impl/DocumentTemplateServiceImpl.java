package com.bagnet.nettracer.tracing.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bagnet.nettracer.tracing.dao.DocumentTemplateDAO;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplateVar;
import com.bagnet.nettracer.tracing.service.DocumentTemplateService;

public class DocumentTemplateServiceImpl implements DocumentTemplateService {
	
	private DocumentTemplateDAO dao;

	@Override
	public DocumentTemplate load(long documentTemplateId) {
		return dao.load(documentTemplateId);
	}

	@Override
	public long save(DocumentTemplate template) {
		return dao.save(template);
	}

	@Override
	public boolean update(DocumentTemplate template) {
		return dao.update(template);
	}

	@Override
	public boolean delete(long documentTemplateId) {
		return dao.delete(documentTemplateId);
	}

	@Override
	public Map<String, List<String>> getDocumentTemplateVars() {
		LinkedHashMap<String, List<String>> documentTemplateVars = new LinkedHashMap<String, List<String>>();
		List<DocumentTemplateVar> fromDb = dao.getDocumentTemplateVars();
		
		for (DocumentTemplateVar var: fromDb) {
			if (!documentTemplateVars.containsKey(var.getAssociatedClass())) {
				List<String> vars = new ArrayList<String>();
				vars.add(var.getDisplayTag());
				documentTemplateVars.put(var.getAssociatedClass(), vars);
			} else {
				documentTemplateVars.get(var.getAssociatedClass()).add(var.getDisplayTag());
			}
		}
		return documentTemplateVars;
	}

	@Override
	public List<String> getVariableNames(String templateData) {
		ArrayList<String> variableNames = new ArrayList<String>();
		
		for (int i = 0; i < templateData.length(); ++i){
			if (templateData.charAt(i) == '{') {
				int j = i+1;
				for (; j < templateData.length(); ++j) {
					if (templateData.charAt(j) == '}') {
						break;
					}
				}
				variableNames.add(templateData.substring(i+1, j));
			}
		}
		return variableNames;
	}

	@Override
	public DocumentTemplate createDocumentTemplate() {
		DocumentTemplate template = new DocumentTemplate();
		template.setVariables(new LinkedHashSet<DocumentTemplateVar>());
		return template;
	}
	
	@Override
	public boolean containsValidVariables(String data) {
		return dao.haveDefinitionsForDocumentTemplateVars(getVarsFromData(data));
	}
	
	@Override
	public String getFirstInvalidVar(String data) {
		return dao.getFirstInvalidVar(getVarsFromData(data));
	}

	public DocumentTemplateDAO getDao() {
		return dao;
	}
	
	public void setDao(DocumentTemplateDAO dao) {
		this.dao = dao;
	}
	
	private List<String> getVarsFromData(String data) {
		String[] varData = StringUtils.substringsBetween(data, "{", "}");
		ArrayList<String> vars = new ArrayList<String>();
		for (String var: varData) {
			if (!vars.contains(var)) {
				vars.add(var);
			}
		}
		return vars;
	}

	@Override
	public int getDocumentTemplateCount(DocumentTemplateSearchDTO dto) {
		return dao.getDocumentTemplateCount(dto);
	}
}
