package com.bagnet.nettracer.reporting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/*
 * Created on Jan 10, 2005
 *
 * matt
 */
/**
 * @author matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HibernateResultDS implements JRDataSource {
	private Iterator iterator;
	private Object currentValue;

	public HibernateResultDS(List list) {
		this.iterator = list.iterator();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		try {
			Method fld = currentValue.getClass().getMethod("get" + field.getName(), null);
			//System.out.println(fld.getName());
			value = fld.invoke(currentValue, null);
			//System.out.println(value);
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		}
		return value;
	}

	public boolean next() throws JRException {
		currentValue = iterator.hasNext() ? iterator.next() : null;
		return (currentValue != null);
	}
}