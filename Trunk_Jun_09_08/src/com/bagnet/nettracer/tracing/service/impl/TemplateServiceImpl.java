package com.bagnet.nettracer.tracing.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.dao.TemplateDAO;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVarDependency;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.dto.TemplateDTO;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.service.TemplateService;

public class TemplateServiceImpl implements TemplateService {
	
	private TemplateDAO dao;

	@Override
	public Template load(long templateId) {
		return dao.load(templateId);
	}

	@Override
	public long save(Template template) {
		return dao.save(template);
	}

	@Override
	public boolean update(Template template) {
		return dao.update(template);
	}

	@Override
	public boolean delete(long templateId) {
		return dao.delete(templateId);
	}

	@Override
	public Template createTemplate() {
		Template template = new Template();
		template.setVariables(new LinkedHashSet<TemplateVar>());
		return template;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#getTemplateVars()
	 */
	@Override
	public Map<String, List<String>> getTemplateVars() {
		LinkedHashMap<String, List<String>> templateVars = new LinkedHashMap<String, List<String>>();
		List<TemplateVar> fromDb = dao.getTemplateVars();
		
		for (TemplateVar var: fromDb) {
			if (!templateVars.containsKey(var.getAssociatedClass())) {
				List<String> vars = new ArrayList<String>();
				vars.add(var.getDisplayTag());
				templateVars.put(var.getAssociatedClass(), vars);
			} else {
				templateVars.get(var.getAssociatedClass()).add(var.getDisplayTag());
			}
		}
		return templateVars;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#getVariableNames(java.lang.String)
	 */
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

	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#containsValidVariables(java.lang.String)
	 */
	@Override
	public boolean containsValidVariables(String data) {
		return dao.haveDefinitionsForTemplateVars(getVarsFromData(data));
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#getFirstInvalidVar(java.lang.String)
	 */
	@Override
	public String getFirstInvalidVar(String data) {
		return dao.getFirstInvalidVar(getVarsFromData(data));
	}


	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#getTemplateCount(com.bagnet.nettracer.tracing.dto.TemplateSearchDTO)
	 */
	@Override
	public int getTemplateCount(TemplateSearchDTO dto) {
		return dao.getTemplateCount(dto);
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#listTemplates(com.bagnet.nettracer.tracing.dto.TemplateSearchDTO)
	 */
	@Override
	public List<TemplateDTO> listTemplates(TemplateSearchDTO dto) {
		List<TemplateDTO> results = new ArrayList<TemplateDTO>();
		List<Template> fromDb = dao.listTemplates(dto);
		if (fromDb != null && !fromDb.isEmpty()) {
			for (Template template: fromDb) {
				TemplateDTO dtdto = new TemplateDTO();
				dtdto.set_DATEFORMAT(dto.get_DATEFORMAT());
				dtdto.set_TIMEFORMAT(dto.get_TIMEFORMAT());
				dtdto.set_TIMEZONE(dto.get_TIMEZONE());
				dtdto.setId(template.getId());
				dtdto.setName(template.getName());
				dtdto.setDescription(template.getDescription());
				dtdto.setCreateDate(template.getCreateDate());
				dtdto.setActive(template.isActive());
				results.add(dtdto);
			}
		}		
		return results;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#validateTemplate(com.bagnet.nettracer.tracing.db.documents.templates.Template)
	 */
	@Override
	public DocumentTemplateResult validateTemplate(Template template) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		
		for (TemplateTypeMapping typeMapping: template.getTypes()) {
			TemplateType toCheck = TemplateType.fromOrdinal(typeMapping.getOrdinal());
			if (toCheck == null || toCheck == TemplateType.INVALID) {
				result.setMessageKey("document.template.invalid.dependencies");
				return result;
			}
		}
		
		result.setSuccess(true);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#determineRequiredTemplateType(com.bagnet.nettracer.tracing.db.documents.templates.Template)
	 */
	@Override
	public Set<TemplateTypeMapping> determineRequiredTemplateTypes(Template template) {
		List<String> varsInData = getVarsFromData(template.getData());
		List<TemplateVarDependency> dependencies = getVariableDependencies(varsInData);
		
		Set<TemplateTypeMapping> types = new LinkedHashSet<TemplateTypeMapping>();
		// if there are no variables or no dependencies, then assume the template is static
		if (varsInData.isEmpty() || dependencies.isEmpty()) {
			types.add(dao.getTemplateTypeMapping(TemplateType.STATIC));
			return types;
		}
		
		List<String> varClassNames = getClassNamesFromDataVars(varsInData);
		
		// if there are unmet dependencies, then the template is invalid (ie: we can't make a document from it)
		if (!allDependenciesMet(varClassNames, dependencies)) {
			types.add(dao.getTemplateTypeMapping(TemplateType.INVALID));
			return types;
		}
		
		for (TemplateType type: TemplateType.getDependencyTemplateTypes()) {
			if (varClassNames.contains(type.getDefaultName())) {
				types.add(dao.getTemplateTypeMapping(type));
			}
		}

		if (types.isEmpty()) {
			types.add(dao.getTemplateTypeMapping(TemplateType.INVALID));
		}
		
		return types;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.TemplateService#getTemplatesByType(java.util.List)
	 */
	@Override
	public List<OptionDTO> getTemplateOptionsByType(TemplateType type) {
		List<Template> fromDb = dao.getActiveTemplatesByType(type);
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		if (!fromDb.isEmpty()) {
			for (Template template: fromDb) {
				options.add(new OptionDTO(String.valueOf(template.getId()), template.getName()));
			}
		}
		return options;
	}
	
	public TemplateDAO getDao() {
		return dao;
	}
	
	public void setDao(TemplateDAO dao) {
		this.dao = dao;
	}

	private List<TemplateVarDependency> getVariableDependencies(List<String> varsInData) {
		return dao.getVarDependencies(getClassNamesFromDataVars(varsInData));
	}
	
	private boolean allDependenciesMet(List<String> varClassNames, List<TemplateVarDependency> dependencies) {
		if (dependencies == null || dependencies.isEmpty()) return true;
		for (TemplateVarDependency dependency: dependencies) {
			if (dependency.satisfied(varClassNames)) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> getClassNamesFromDataVars(List<String> varsInData) {
		List<String> classNames = new ArrayList<String>();
		if (varsInData != null) {
			for (String var: varsInData) {
				String[] tokens = var.split("\\.");
				classNames.add(tokens[0]);
			}
		}
		return classNames;
	}
	
	private List<String> getVarsFromData(String data) {
		ArrayList<String> vars = new ArrayList<String>();
		String[] varData = StringUtils.substringsBetween(data, "{", "}");
		if (varData != null && varData.length > 0) {
			for (String var: varData) {
				if (!vars.contains(var)) {
					vars.add(var);
				}
			}
		}
		return vars;
	}
}
