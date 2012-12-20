package aero.nettracer.fs.model.transport.v3;

import java.io.Serializable;
import java.util.Set;

public class FileAttach implements Serializable {

	private static final long serialVersionUID = 1L;

	File file;
	Set<FsAttachment> attachments;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Set<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<FsAttachment> attachments) {
		this.attachments = attachments;
	}
}