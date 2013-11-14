package com.bagnet.nettracer.tracing.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.dto.TemplateDTO;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;

/**
 * The TemplateService interface defines methods which provide database and maintenance operations 
 * to the user in regards to Templates defined in the system. 
 * @author Mike
 *
 */
public interface TemplateService {
	public Template load(long templateId);
	public long save(Template template);
	public boolean update(Template template);
	public boolean delete(long templateId);
	public Template createTemplate();
	
	/**
	 * Returns the available template variables in the database
	 * @return a list of available template variables in the system
	 */
	public Map<String, List<String>> getTemplateVars();
	
	/**
	 * Scans the given template data for embedded variables
	 * @param templateData the data to parse for variables
	 * @return a list containing all of the variables contained in the template data
	 */
	public List<String> getVariableNames(String templateData);
	
	/**
	 * Returns true if the given data contains variables that have been defined 
	 * in the system
	 * @param data to parse for variable declarations
	 * @return true if the contained variables are valid, false otherwise
	 */
	public boolean containsValidVariables(String data);
	
	/**
	 * Parses the given data and returns the first variable in the data for which no
	 * definition exists in the system
	 * @param data to scan for embedded variables
	 * @return the first variable found for which no definition exists in the system
	 */
	public String getFirstInvalidVar(String data);
	
	/**
	 * Returns the number of templates in the database based on the given search criteria
	 * @param dto which contains the search criteria used to get the template count
	 * @return the number of templates in the database based on the given search criteria
	 */
	public int getTemplateCount(TemplateSearchDTO dto);
	
	/**
	 * Returns a list of templates based on the given search criteria
	 * @param dto which contains the search criteria used to get the templates
	 * @return a list of templates based on the given search criteria
	 */
	public List<TemplateDTO> listTemplates(TemplateSearchDTO dto);
	
	/**
	 * Verifies that the template can be used to generate a document
	 * @param template to validate
	 * @return a DocumentTemplateResult indicating whether or not the 
	 * template can be used to generate a Document
	 */
	public DocumentTemplateResult validateTemplate(Template template);
	
	/**
	 * Determines the type of template based on the dependencies between the variables
	 * embedded in the template data
	 * @param template for which the type needs to be determined
	 * @return the TemplateTypeMappings for the given template
	 */
	public Set<TemplateTypeMapping> determineRequiredTemplateTypes(Template template);
	
	/**
	 * Retrieives the list of template names that can be used for a specific object type
	 * (ie: Incidents, Claims, etc...)
	 * @param type the TemplateType for which to get the available templates
	 * @return a List of TemplateOptionDTOs containing the available list of template 
	 * names for the given type
	 */
	public List<OptionDTO> getTemplateOptionsByType(TemplateType type);

}
