/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

//import com.bagnet.nettracer.tracing.utils.NumberUtils;
//import com.cci.utils.parser.ElementNode;


@Entity
@Proxy(lazy = false)
public class FsAttachment implements Serializable {
	@Id
	@GeneratedValue
	private int id;
//	private String thumbpath;
	private String path;
	private String description;
	private long claim_id;
	private String compCode;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getClaim_id() {
		return claim_id;
	}
	
	public void setClaim_id(long claim_id) {
		this.claim_id = claim_id;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}


//	public String getThumbpath() {
//		return thumbpath;
//	}
//
//	public void setThumbpath(String thumbpath) {
//		this.thumbpath = thumbpath;
//	}
}

