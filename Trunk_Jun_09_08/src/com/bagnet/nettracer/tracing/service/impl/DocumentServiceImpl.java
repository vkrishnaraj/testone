package com.bagnet.nettracer.tracing.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.AuthorizationException;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateVar;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
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
		if (document == null) {
			throw new IllegalArgumentException("Documents cannot be null");
		}
		
		List<Document> documents = new ArrayList<Document>(0);
		documents.add(document);
		return generatePdf(user, documents, directory);
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#generatePdf(com.bagnet.nettracer.tracing.db.documents.Document, java.lang.String)
	 */
	@Override
	public DocumentTemplateResult generatePdf(Agent user, List<Document> documents, String directory) throws InsufficientInformationException {
		if (documents == null || documents.isEmpty()) {
			throw new IllegalArgumentException("No document to be generated");
		}
		
		Document firstDocument = documents.get(0);
		if (firstDocument.getTemplate() == null) {
			throw new InsufficientInformationException(Template.class);
		}
		
		if (user == null) {
			throw new InsufficientInformationException(Agent.class);
		}
		
		if (directory == null || directory.isEmpty()) {
			throw new InsufficientInformationException("directory");
		}
		
		OutputStream out = null;
		String rootPath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + directory + "\\";
		DocumentTemplateResult result = new DocumentTemplateResult();
		try {
			String templateName = (documents.size() == 1) ? firstDocument.getName() : String.format("%s-%s", firstDocument.getName(), documents.size());
			String fileName = this.generateFileName(templateName);
			StringBuilder filePath = new StringBuilder(rootPath);
			filePath.append(fileName);
			out = new FileOutputStream(filePath.toString());
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(firstDocument.getXml());
			renderer.layout();
			renderer.createPDF(out, false); 
			
			for (int i = 1; i < documents.size(); i++) {
				Document document = documents.get(i);
				if (document == null  || StringUtils.isBlank(document.getXml())) {
					logger.debug("Document is empty: document Id = " + document.getId());
					continue;
				}
				
		        renderer.setDocumentFromString(document.getXml());
		        renderer.layout();
		        renderer.writeNextDocument();
		    }
		    renderer.finishPDF();
			
			// update the result details
			result.setSuccess(true);
			result.setMessageKey("document.generated.success");
			result.setPayload(fileName);
		} catch (Exception e) {
			logger.error("Error occurred while attempting to generate document: " + firstDocument.getName(), e);
			result.setMessageKey("document.generated.failure");
		} finally {
			IOUtils.closeQuietly(out);
		 }
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.service.DocumentService#generatePdfForPortal(com.bagnet.nettracer.tracing.db.documents.Document, java.lang.String)
	 */
	@Override
	public OCFile generatePdfForPortal(Agent user, Document document, String directory) throws InsufficientInformationException {
		if (document.getTemplate() == null) {
			throw new InsufficientInformationException(Template.class);
		}
		
		if (user == null) {
			throw new InsufficientInformationException(Agent.class);
		}
		
		if (directory == null || directory.isEmpty()) {
			throw new InsufficientInformationException("directory");
		}
		
		OCFile file=new OCFile();
		
		String rootPath = TracerProperties.get(user.getCompanycode_ID(),"oc_image_store") + directory + "\\";
		if(!ImageUtils.makeFolder(rootPath)){
			logger.error("Unable to create directory");
			return null;
		}
		try {
			String fileName = this.generateFileName(document.getName());
			StringBuilder filePath = new StringBuilder(rootPath);
			filePath.append(fileName);
			OutputStream out = new FileOutputStream(filePath.toString());
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(document.getXml());
			renderer.layout();
			renderer.createPDF(out);
			
			// update the file details
			file.setFilename(fileName);
			file.setPath(directory);
			file.setDateUploaded(new Date());
			file.setPublish(true);
			file.setStatusId(TracingConstants.OC_STATUS_PUBLISHED);
		} catch (Exception e) {
			logger.error("Error occurred while attempting to generate document: " + document.getName(), e);
		}
		
		return file;
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
	
	public OCFile publishDocument(IncidentActivity ia) throws InsufficientInformationException{
		if (ia.getDocument() == null) {
			throw new InsufficientInformationException(Document.class);
		}
		if (ia.getIncident() == null) {
			throw new InsufficientInformationException(Incident.class);
		}
		if(ia.getAgent()==null){
			throw new InsufficientInformationException(Agent.class);
		}
		
		String incidentId=ia.getIncident().getIncident_ID();
		OCFile file=new OCFile();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String path=PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_LETTERS)+"/"+year+"-"+month+"/"+day;
		file = generatePdfForPortal(ia.getAgent(), ia.getDocument(), path);
		if(file!=null){
			OnlineClaimsDao dao=new OnlineClaimsDao();
			OnlineClaim c=dao.getOnlineClaim(ia.getIncident().getIncident_ID());
			if (c == null) {
				try {
					String fName="";
					String name="";
					if(ia.getIncident().getPassenger_list()!=null && ia.getIncident().getPassenger_list().get(0)!=null){
						Passenger p=ia.getIncident().getPassenger_list().get(0);
						fName=p.getFirstname();
						name=p.getLastname();
					}
					c = dao.createOnlineClaim(incidentId, null, fName, name);
					dao.saveOnlineClaimWsUseOnly(c, incidentId, null, null);
				} catch (AuthorizationException e) {
					e.printStackTrace();
				}
			}
			file.setIncAct(ia);
			file.setClaim(c);
		}
		return file;
	}

	@Override
	public boolean hasCustomerCommunicationRequiredInformation(Document document) {
		return getMissingRequiredVariable(document) == null;
	}
	
	@Override
	public String getMissingRequiredVariable(Document document) {
		if (document == null || document.getContent() == null) return null;
		
		String[] varData = StringUtils.substringsBetween(document.getContent(), "{", "}");
		if (varData == null || varData.length == 0) {
			return null;
		}
		
		String toReturn = null;
		String regex = "([a-z]+)([\\.]{1}?)([a-z]+)([0-9]*)?";
		for (String var: varData) {
			if ((var = StringUtils.deleteWhitespace(var)) != null && var.toLowerCase().matches(regex)) {
				toReturn = var;
				break;
			}
		}
		return toReturn;
	}
	
}
