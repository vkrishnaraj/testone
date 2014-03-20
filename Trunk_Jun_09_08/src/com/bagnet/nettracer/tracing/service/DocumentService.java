package com.bagnet.nettracer.tracing.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;

/**
 * The DocumentService interface defines methods which provide database and maintenance operations 
 * to the user in regards to Documents defined in the system.
 * @author Mike
 *
 */
public interface DocumentService {
	public Document load(long documentId);
	public long save(Document document);
	public boolean update(Document document);
	public boolean delete(long documentId);
	
	/**
	 * The mergeDocumentToPrint method will merge the document with the adapter, but will replace all 
	 * variables for which there is no data in the adapter with empty strings to remove variable names 
	 * from printed documents.
	 * @param document the Document to merge
	 * @param adapter the TemplateAdapter which contains the data to merge with the document
	 * @return DocumentTemplateResult, which holds the results of the merge process
	 * @throws InsufficientInformationException if the document or adapter is null
	 */
	public DocumentTemplateResult mergeDocumentToPrint(Document document, TemplateAdapter adapter) throws InsufficientInformationException;
	
	/**
	 * The mergeDocumentToEdit method will merge the document with the adapter, leaving variable names 
	 * in place for any variables for which there is no data in the adapter.
	 * @param document the Document to merge
	 * @param adapter the TemplateAdapter which contains the data to merge with the document
	 * @return DocumentTemplateResult, which holds the results of the merge process
	 * @throws InsufficientInformationException if the document or adapter is null
	 */
	public DocumentTemplateResult mergeDocumentToEdit(Document document, TemplateAdapter adapter) throws InsufficientInformationException;
	
	/**
	 * The generatePdf method creates a pdf file on the file share from the contents of the given document.
	 * @param user requesting the pdf be generated
	 * @param document from which the file will be created
	 * @param directory is the folder where the pdf should be generated
	 * @return a DocumentTemplateResult containing meta data regarding the results of the generatePdf operation
	 * @throws InsufficientInformationException if the method is called without both the document and the 
	 * root path to the file share being supplied
	 */
	public DocumentTemplateResult generatePdf(Agent user, Document document, String directory) throws InsufficientInformationException;
	
	/**
	 * The generatePdf method creates a pdf file on the file share from the contents of the given document.
	 * @param user requesting the pdf be generated
	 * @param documents from which the file will be created
	 * @param directory is the folder where the pdf should be generated
	 * @return a DocumentTemplateResult containing meta data regarding the results of the generatePdf operation
	 * @throws InsufficientInformationException if the method is called without both the document and the 
	 * root path to the file share being supplied
	 */
	public DocumentTemplateResult generatePdf(Agent user, List<Document> documents, String directory) throws InsufficientInformationException;
	
	/**
	 * Verifies that the given fielName exists on the file share
	 * @param user requesting the file preview
	 * @param fileName the name of the file to verify
	 * @param directory is the folder in which the pdf is stored
	 * @return DocumentTemplateResult indicating whether or not the file
	 * exists on the file share
	 */
	public DocumentTemplateResult canPreviewFile(Agent user, String fileName, String directory);
	
	/**
	 * The previewFile method sets the required attributes on the given HttpServletResponse and writes the 
	 * pdf file content to its OutputStream for viewing in the web page.
	 * @param user the Agent requesting the file preview
	 * @param fileName the name of the file to preview
	 * @param directory is the folder in which the pdf is stored
	 * @param response the HttpServletResponse to output the pdf content too.
	 * @return a DocumentTemplateResult containing the results of the previewFile operation
	 */
	public DocumentTemplateResult previewFile(Agent user, String fileName, String directory, HttpServletResponse response);
	
	/**
	 * The generatePdfForPortal method creates a pdf file in the OC Claim Files directory from the contents of the given document.
	 * @param user requesting the pdf be generated
	 * @param document from which the file will be created
	 * @param directory is the folder where the pdf should be generated
	 * @return a OCFile to save and push to the portal
	 * @throws InsufficientInformationException if the method is called without both the document and the 
	 * root path to the file share being supplied
	 */
	public OCFile generatePdfForPortal(Agent user, Document document,
			String directory) throws InsufficientInformationException;
	

	/**
	 * The publish document method takes the document of a Incident Activity and makes a file version of itself to push to the Online Claims portal for customers to view files
	 * @param ia is the Incident Activity which to reference the Document to create
	 * @return a OCFile to save and push to the portal
	 * @throws InsufficientInformationException if the method is called without both the document and the 
	 * root path to the file share being supplied
	 */
	public OCFile publishDocument(IncidentActivity ia) throws InsufficientInformationException;
	
	/**
	 * This method ensures that the text of the document contains no variables, which should have been 
	 * merged from their data source.
	 * @param document to check for un-substituted variable data
	 * @return true if all the variables have been replaced with data, false otherwise.
	 */
	public boolean hasCustomerCommunicationRequiredInformation(Document document);
	
	/**
	 * This method gets the first variable in the text of the document which should have been replaced 
	 * with application data.
	 * @param document to check for un-substituted variable data
	 * @return the name of the first variable found, null otherwise.
	 */
	public String getMissingRequiredVariable(Document document);
	
	/**
	 * This method gets the content of a document in the form of a byte array for output to an HttpServletResponse.
	 * @param document - the Document to display the content of.
	 * @param companyCode - the company code of the user making the request
	 * @param sourceDirectory - the source directory containing the pdf file, which is used only if outputType is set
	 * to pdf.
	 * @param outputType - the output type of the document, currently only html or pdf.
	 * @return - a byte array representation of the document content.
	 */
	public byte[] getByteArrayForDocument(Document document, String companyCode, String sourceDirectory, int outputType);
}
