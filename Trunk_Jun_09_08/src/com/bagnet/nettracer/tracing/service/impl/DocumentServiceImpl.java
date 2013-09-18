package com.bagnet.nettracer.tracing.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class DocumentServiceImpl implements DocumentService {
	
	private Logger logger = Logger.getLogger(DocumentServiceImpl.class);
	
	private DocumentDAO dao;

	@Override
	public Document load(long documentId) {
		return dao.load(documentId);
	}

	@Override
	public long save(Document document) {
		return dao.save(document);
	}

	@Override
	public boolean update(Document document) {
		return dao.update(document);
	}

	@Override
	public boolean delete(long documentId) {
		return dao.delete(documentId);
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#merge(com.bagnet.nettracer.tracing.db.documents.Document, com.bagnet.nettracer.tracing.adapter.TemplateAdapter)
	 */
	@Override
	public DocumentTemplateResult merge(Document document, TemplateAdapter adapter) throws InsufficientInformationException {
		if (document == null) {
			throw new InsufficientInformationException(Document.class);
		}
		
		if (adapter == null) {
			throw new InsufficientInformationException(TemplateAdapter.class);
		}
		
		DocumentTemplateResult result = new DocumentTemplateResult();
		Set<TemplateVar> vars = document.getTemplate().getVariables();
		if (vars != null && !vars.isEmpty()) {
			String content = document.getTemplate().getData();
			for (TemplateVar var: vars) {
				String associatedClass = var.getAssociatedClass();
				String displayTag = var.getDisplayTag();
				String toReplace = "{" + associatedClass + "." + displayTag + "}";
				
				try {
					Method getter = adapter.getClass().getDeclaredMethod("get" + associatedClass + displayTag, new Class[] { });
					content = content.replace(toReplace, (String) getter.invoke(adapter, new Object[] { }));
				} catch (NoSuchMethodException nsme) {
					// MJS: not catastrophic; make note of the exception and move on
					logger.info("NoSuchMethodException caught attempting to find method: TemplateAdapter.get" + associatedClass + displayTag + "()");
					continue;
				} catch (Exception e) {
					// MJS: catching generic Exception since there's nothing 
					// different to do depending on which type of exception is caught
					logger.error(e);
					result.setMessageKey("document.generated.failure");
					return result;
				}
				
			}
			
			document.setContent(content);
		}
		
		result.setMessageKey("document.generated.success");
		result.setSuccess(true);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#generatePdf(com.bagnet.nettracer.tracing.db.documents.Document, java.lang.String)
	 */
	@Override
	public DocumentTemplateResult generatePdf(Document document, String rootPath) throws InsufficientInformationException {
		if (document.getTemplate() == null) {
			throw new InsufficientInformationException(Template.class);
		}
		
		if (rootPath == null || rootPath.isEmpty()) {
			throw new InsufficientInformationException("rootPath");
		}

		DocumentTemplateResult result = new DocumentTemplateResult();
		try {
			String fileName = this.generateFileName(rootPath, document.getName());
			StringBuilder filePath = new StringBuilder(rootPath);
			filePath.append("/");
			filePath.append(fileName);
			OutputStream out = new FileOutputStream(filePath.toString());
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(document.getXml());
			renderer.layout();
			renderer.createPDF(out);
			
			// update the result details
			result.setSuccess(true);
			result.setMessageKey("document.generated.success");
			result.setPayload(fileName);
		} catch (Exception e) {
			logger.error("Error occurred while attempting to generate document: " + document.getName(), e);
			result.setMessageKey("document.geterated.failure");
		}
		
		return result;
	}
	
	public DocumentDAO getDao() {
		return dao;
	}
	
	public void setDao(DocumentDAO dao) {
		this.dao = dao;
	}
	
	private String generateFileName(String rootPath, String templateName) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(templateName.replace(" ", "_"));
		fileName.append("_" + new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		fileName.append(".pdf");
		return fileName.toString();
	}
	
}
