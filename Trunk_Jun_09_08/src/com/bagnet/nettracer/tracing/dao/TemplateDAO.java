package com.bagnet.nettracer.tracing.dao;

import java.util.List;

import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVarDependency;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;

public interface TemplateDAO {
	public Template load(long templateId);
	public long save(Template template);
	public boolean update(Template template);
	public boolean delete(long templateId);
	
	/**
	 * Returns a list of the template variables defined in the database
	 * @return a list containing all of the template variables defined in the
	 * database
	 */
	public List<TemplateVar> getTemplateVars();
	
	/**
	 * Indicates whether or not all of the submitted variables have been defined 
	 * in the database
	 * @param vars the variables to find database definitions for
	 * @return true if all of the variables have definitions, false otherwise
	 */
	public boolean haveDefinitionsForTemplateVars(List<String> vars);
	
	/**
	 * Returns the first variable found in the list for which no definition exists 
	 * in the database
	 * @param vars the list of variables to search
	 * @return the name of the first variable found for which not definition exists 
	 * in the database, null otherwise
	 */
	public String getFirstInvalidVar(List<String> vars);
	
	/**
	 * @param dto containing the search criteria used to find the template count
	 * @return the number of templates in the database that match the given search 
	 * criteria
	 */
	public int getTemplateCount(TemplateSearchDTO dto);
	
	/**
	 * @param dto containing the search criteria used to get the templates
	 * @return a list of templates that match the given search criteria
	 */
	public List<Template> listTemplates(TemplateSearchDTO dto);
	
	/**
	 * @param classNames a list of class names that may or may not have variable 
	 * dependencies defined in the database
	 * @return a list of class dependencies for the given variable class names
	 */
	public List<TemplateVarDependency> getVarDependencies(List<String> classNames);
	
	/**
	 * Loads the TemplateTypeMapping for the given TemplateType
	 * @param type to load the TemplateTypeMapping for
	 * @return the TemplateTypeMapping for the given TemplateType
	 */
	public TemplateTypeMapping getTemplateTypeMapping(TemplateType type);
	
}
