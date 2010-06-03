/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;

/**
 * @author Administrator
 * 
 * @hibernate.class table="XDescElement"
 * @hibernate.cache usage="read-only"
 */
public class XDescElement extends LocaleBasedObject  implements Serializable {
	private int XDesc_ID;
	private String code;

	private String MSG_KEY = "DESCELEMENT_";
	/**
	 * @return Returns the xDesc_ID.
	 * 
	 * @hibernate.id generator-class="native" type="int" length="6"
	 * @hibernate.generator-param name="sequence" value="xdescelement_0"
	 *  
	 */
	public int getXDesc_ID() {
		return XDesc_ID;
	}

	/**
	 * @param desc_ID
	 *          The xDesc_ID to set.
	 */
	public void setXDesc_ID(int desc_ID) {
		XDesc_ID = desc_ID;
	}

	/**
	 * @return Returns the code.
	 * 
	 * @hibernate.property type="string" length="1"
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *          The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getKey() {
		return MSG_KEY + code;
	}
}