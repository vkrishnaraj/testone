package aero.nettracer.fs.model.detection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;



@Entity
@Proxy(lazy = false)
public class IPWhiteList implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue
	int id;
	String ipAddress;
	private String description;

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
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
	
	
}
