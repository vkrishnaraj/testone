package com.bagnet.nettracer.tracing.utils;

import java.util.Comparator;

import org.apache.commons.beanutils.BeanUtils;
/**
 * 
 * bean comparator, 
 * 
 * usage:
 * 
 *  Comparator field1 = new BeanPropertyComparator("field1");
 *  Comparator field2 = new BeanPropertyComparator("field2");
 *  Comparator f12 = new CompositeComparator(field1,field2);	// sort by field1 first, and then sort by field2
 *  
 * @author matt
 *
 */
public class BeanPropertyComparator implements Comparator {

	private String property;
	private Comparator comparator;

	public BeanPropertyComparator(String property, Comparator comparator) {
		this.property = property;
		this.comparator = comparator;
	}

	public int compare(Object bean1, Object bean2) {
		// Get the value of the properties
		try {
			Object value1 = BeanUtils.getProperty(bean1,property);
			Object value2 = BeanUtils.getProperty(bean2,property);
			return comparator.compare(value1, value2);
		} catch (Exception e) {}
		return 0;
	}

}

class CompositeComparator implements Comparator {
  private Comparator major;
  private Comparator minor;
  public CompositeComparator(Comparator major, Comparator minor) {
    this.major = major;
    this.minor = minor;
  }
  public int compare(Object o1, Object o2) {
    int result = major.compare(o1,o2);
    if (result != 0) {
      return result;
    } else {
      return minor.compare(o1,o2);
    }
  }
}
