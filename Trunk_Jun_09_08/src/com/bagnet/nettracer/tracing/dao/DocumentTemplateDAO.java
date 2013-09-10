package com.bagnet.nettracer.tracing.dao;

import java.util.List;

import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplateVar;
import com.bagnet.nettracer.tracing.dto.DocumentTemplateSearchDTO;

public interface DocumentTemplateDAO {
	public DocumentTemplate load(long documentTemplateId);
	public long save(DocumentTemplate template);
	public boolean update(DocumentTemplate template);
	public boolean delete(long documentTemplateId);
	public List<DocumentTemplateVar> getDocumentTemplateVars();
	public boolean haveDefinitionsForDocumentTemplateVars(List<String> vars);
	public String getFirstInvalidVar(List<String> vars);
	public int getDocumentTemplateCount(DocumentTemplateSearchDTO dto);
	public List<DocumentTemplate> listDocumentTemplates(DocumentTemplateSearchDTO dto);
}
