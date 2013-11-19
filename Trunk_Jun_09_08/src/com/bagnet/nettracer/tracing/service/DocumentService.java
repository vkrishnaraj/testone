package com.bagnet.nettracer.tracing.service;

import javax.servlet.http.HttpServletResponse;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.Document;
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
	 * The merge method combines the data contained in the supplied TemplateAdapter with the variables defined 
	 * in the Template data contained in the supplied Document to generate the document's contents.
	 * @param document which contains the Template data with which the adapter data should be merged
	 * @param adapter which contains the variable data which will be merged with the document's Template data
	 * @return a DocumentTemplateResult containing meta data regarding the results of the merge operation
	 * @throws InsufficientInformationException if the method is called without both an adapter and document 
	 * being supplied
	 */
	public DocumentTemplateResult merge(Document document, TemplateAdapter adapter) throws InsufficientInformationException;
	
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
}
