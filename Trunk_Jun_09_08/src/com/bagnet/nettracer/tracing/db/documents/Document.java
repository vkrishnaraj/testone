package com.bagnet.nettracer.tracing.db.documents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;

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
	
	public String getXml() {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
		xml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		xml.append("<html><body>");
		xml.append(getContent());
		xml.append("</body></html>");
		return xml.toString();
	}

}
