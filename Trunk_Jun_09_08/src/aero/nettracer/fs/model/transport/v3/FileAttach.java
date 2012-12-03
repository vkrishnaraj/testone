package aero.nettracer.fs.model.transport.v3;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class FileAttach implements Serializable {
	File file;
	Set<Attachment> attachments;
	
	public File getFile(){
		return file;
	}
	public void setFile(File file){
		this.file=file;
	}
	public Set<Attachment> getAttachments(){
		return attachments;
	}
	public void setAttachments(Set<Attachment> attachments){
		this.attachments=attachments;
	}
}