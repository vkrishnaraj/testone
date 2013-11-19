package com.bagnet.nettracer.tracing.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

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
		String content = document.getTemplate().getData();
		if (vars != null && !vars.isEmpty()) {
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
			
		}
		document.setContent(content);
		
		result.setMessageKey("document.generated.success");
		result.setSuccess(true);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#generatePdf(com.bagnet.nettracer.tracing.db.documents.Document, java.lang.String)
	 */
	@Override
	public DocumentTemplateResult generatePdf(Agent user, Document document, String directory) throws InsufficientInformationException {
		if (document.getTemplate() == null) {
			throw new InsufficientInformationException(Template.class);
		}
		
		if (user == null) {
			throw new InsufficientInformationException(Agent.class);
		}
		
		if (directory == null || directory.isEmpty()) {
			throw new InsufficientInformationException("directory");
		}
		
		String rootPath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + directory + "\\";
		DocumentTemplateResult result = new DocumentTemplateResult();
		try {
			String fileName = this.generateFileName(document.getName());
			StringBuilder filePath = new StringBuilder(rootPath);
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
			result.setMessageKey("document.generated.failure");
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#canPreviewFile(java.lang.String)
	 */
	@Override
	public DocumentTemplateResult canPreviewFile(Agent user, String fileName, String directory) {
		String fullPath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + directory + "\\" + fileName;
		DocumentTemplateResult result = new DocumentTemplateResult();
		File toPreview = new File(fullPath);
		if (!toPreview.exists()) {
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#previewFile(com.bagnet.nettracer.tracing.db.Agent, java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public DocumentTemplateResult previewFile(Agent user, String fileName, String directory, HttpServletResponse response) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		
		String documentStorePath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + directory + "\\";
		File toPreview = new File(documentStorePath + fileName);
		if (!toPreview.exists()) {
			result.setMessageKey("document.file.not.found");
			result.setPayload(fileName);
			return result;
		}
		
		response.setContentType("application/pdf");
		response.setContentLength((int) toPreview.length());
		
		FileInputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(toPreview);
			out = response.getOutputStream();
			
			int current = -1;
			while ((current = in.read()) > -1) {
				out.write(current);
			}

			result.setSuccess(true);
		} catch (IOException e) {
			logger.error("Failed to read the preview file: " + fileName, e);
		} finally {
			try {
				if (in != null) in.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close the input and output streams in file preview", e);
			}
		}
		
		return result;
	}
	
	public DocumentDAO getDao() {
		return dao;
	}
	
	public void setDao(DocumentDAO dao) {
		this.dao = dao;
	}
	
	private String generateFileName(String templateName) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(templateName.replace(" ", "_"));
		fileName.append("_" + new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		fileName.append(".pdf");
		return fileName.toString();
	}
	
}
