package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @hibernate.class table="PROPERTIES"
 * 
 */
public class Property implements Serializable {

	private int id;
	private String keyStr;
	private String valueStr;

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Property_0"
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the keyStr
	 * @hibernate.property type="string"
	 */
	public String getKeyStr() {
		return keyStr;
	}

	/**
	 * @param keyStr
	 *          the keyStr to set
	 */
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	/**
	 * @return the valueStr
	 * @hibernate.property type="string"
	 */
	public String getValueStr() {
		return valueStr;
	}

	/**
	 * @param valueStr
	 *          the valueStr to set
	 */
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

}
