package com.bagnet.nettracer.tracing.service;

import java.util.List;
import java.util.Map;

import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.service.impl.DocumentTemplateSearchDTO;

public interface DocumentTemplateService {
	public DocumentTemplate load(long documentTemplateId);
	public long save(DocumentTemplate template);
	public boolean update(DocumentTemplate template);
	public boolean delete(long documentTemplateId);
	
	public Map<String, List<String>> getDocumentTemplateVars();
	public List<String> getVariableNames(String templateData);
	public DocumentTemplate createDocumentTemplate();
	public boolean containsValidVariables(String data);
	public String getFirstInvalidVar(String data);
	
	public int getDocumentTemplateCount(DocumentTemplateSearchDTO dto);
}
