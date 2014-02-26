package com.bagnet.nettracer.tracing.db.documents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.bagnet.clients.defaul.FckEditorPathBuilderImpl;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

@Entity
@Table(name = "document")
public class Document {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "templateId")
	private Template template;
	
	private String content;
	
	private String fileName;
	
	public Document() { }
	
	public Document(Template template) {
		this.template = template;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() throws InsufficientInformationException {
		if (template == null) {
			throw new InsufficientInformationException(Template.class);
		}
		return template.getName();
	}
	
	public String getXml(Agent user) {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
		xml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		xml.append("<html><body>");
		String toReplace = "/" + TracerProperties.get(user.getCompanycode_ID(),"application_context") + "/showImage\\?ID=" + FckEditorPathBuilderImpl.subdir + "/";
		String replaceWith = TracerProperties.get(user.getCompanycode_ID(),"image_store").replaceAll("^[cdCD]:", "").replaceAll("\\\\", "\\\\\\\\") + FckEditorPathBuilderImpl.subdir;
		String imageFriendlyContent = getContent().replaceAll(toReplace, replaceWith);
		xml.append(imageFriendlyContent);
		xml.append("</body></html>");
		return xml.toString();
	}
	
	public void appendContent(Document toAppend) {
		if (toAppend == null || toAppend.getContent() == null || StringUtils.isBlank(toAppend.getContent())) return;
		StringBuilder newContent = new StringBuilder(getContent() != null ? getContent() : "");
		newContent.append("<div style=\"page-break-after: always\"><span style=\"display: none;\">&nbsp;</span></div>");
		newContent.append(toAppend.getContent());
		this.setContent(newContent.toString());
	}

}
