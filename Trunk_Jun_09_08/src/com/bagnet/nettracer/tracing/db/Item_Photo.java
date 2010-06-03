/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ListIterator;

import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Item_Photo"
 */
public class Item_Photo implements Serializable {
	private int Photo_ID;
	private String thumbpath;
	private String picpath;
	private Item item;
	
	private String fileName;
	

	public String getFileName() {
		String result = "";
		if (fileName == null || fileName.equals("")) {
			result = picpath.substring(picpath.lastIndexOf('/')+1, picpath.length());
		} else {
			result = fileName;
		}
		
		return result;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<photo>");
		sb.append("<Photo_ID>" + Photo_ID + "</Photo_ID>");
		sb.append("<thumbpath>" + thumbpath + "</thumbpath>");
		sb.append("<picpath>" + picpath + "</picpath>");
		sb.append("</photo>");

		return sb.toString();
	}

	public static Item_Photo XMLtoObject(ElementNode root) {
		Item_Photo obj = new Item_Photo();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Photo_ID")) {
				obj.setPhoto_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("thumbpath")) {
				obj.setThumbpath(child.getTextContents());
			} else if (child.getType().equals("picpath")) {
				obj.setPicpath(child.getTextContents());
			} 

		}

		return obj;
	}
	
	/**
	 * @return Returns the photo_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Photo_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="item_photo_0"
	 *  
	 */
	public int getPhoto_ID() {
		return Photo_ID;
	}

	/**
	 * @param photo_ID
	 *          The photo_ID to set.
	 */
	public void setPhoto_ID(int photo_ID) {
		Photo_ID = photo_ID;
	}

	
	/**
	 * @return Returns the item.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Item"
	 *                        column="item_ID" not-null="true"
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * @param item The item to set.
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	
	/**
	 * @return Returns the picpath.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getPicpath() {
		return picpath;
	}

	/**
	 * @param picpath
	 *          The picpath to set.
	 */
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	/**
	 * @return Returns the thumbpath.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getThumbpath() {
		return thumbpath;
	}

	/**
	 * @param thumbpath
	 *          The thumbpath to set.
	 */
	public void setThumbpath(String thumbpath) {
		this.thumbpath = thumbpath;
	}
}