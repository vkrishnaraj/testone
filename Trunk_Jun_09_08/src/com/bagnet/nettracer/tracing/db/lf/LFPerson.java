package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.security.AES;

@Entity
@Proxy(lazy = false)
public class LFPerson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7228145418177131087L;

	@Id
	@GeneratedValue
	private long id;
	
	private String lastName;
	
	private String firstName;
	
	private String middleName;
	
	private String email;
	
	@Transient
	private String decryptedEmail;
	
	@Transient
	private String confirmEmail;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFAddress.class, cascade = CascadeType.ALL)
	private LFAddress address;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFPhone> phones;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LFAddress getAddress() {
		return address;
	}

	public void setAddress(LFAddress address) {
		this.address = address;
	}

	public Set<LFPhone> getPhones() {
		return phones;
	}

	public void setPhones(Set<LFPhone> phones) {
		this.phones = phones;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedEmail instead*/
	public String getEmail() {
		return email;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedEmail instead*/
	public void setEmail(String email) {
		this.decryptedEmail = null;
		this.email = email;
	}

	public String getConfirmEmail() {
		if (confirmEmail == null) {
			confirmEmail = getDecryptedEmail();
		}
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		if(confirmEmail != null){
			confirmEmail = confirmEmail.toLowerCase();
		}
		this.confirmEmail = confirmEmail;
	}

	public void setDecryptedEmail(String email) {
		this.decryptedEmail = null;
		try {
			this.email = AES.encrypt(email!=null?email.trim().toLowerCase():null);
		} catch (Exception e){
			e.printStackTrace();
			this.email = null;
		}
	}

	public String getDecryptedEmail() {
		if(this.decryptedEmail == null){
			try{
				this.decryptedEmail = AES.decrypt(this.email);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedEmail;
	}
	
	public boolean isEmpty() {
		boolean empty = true;

		if ((firstName != null && !firstName.isEmpty())
				|| (lastName != null && !lastName.isEmpty())
				|| (middleName != null && !middleName.isEmpty())
				|| (getDecryptedEmail() != null && !getDecryptedEmail().isEmpty())) {
			empty = false;
		}
		
		if (empty) {
			empty = address.isEmpty();
		}
		
		if (empty) {
			for (LFPhone p: phones) {
				empty = p.isEmpty();
				if (!empty) {
					break;
				}
			}
		}
		
		return empty;
	}
	
}
