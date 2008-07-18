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
 *  sort normal:
 *  ArrayList al = Collections.sort(al,fl2);
 *  sort reverse:
 *  ArrayList al = Collections.sort(al,Collections.reverseOrder(fl2));
 * @author matt
 *
 */
public class BeanPropertyComparator implements Comparator {

	private String property;
	private Comparator comparator;

  public BeanPropertyComparator(String property) {
    this(property,null);
  }

 
	public BeanPropertyComparator(String property, Comparator comparator) {
		this.property = property;
		this.comparator = comparator;
	}

	
  public int compare(Object o1, Object o2) throws IllegalArgumentException {
    // Get the value of the properties
  	try {
	  	Object p1 = BeanUtils.getProperty(o1,property);
			Object p2 = BeanUtils.getProperty(o2,property);
	    if (comparator == null) {
	      // try to find p1 or p2 that implements Comparator
	      if (p1 instanceof Comparable) {
	        return ((Comparable)p1).compareTo(p2);
	      } else if (p2 instanceof Comparable) {
	        return ((Comparable)p2).compareTo(p1);
	      } else {
	        // we have no comparables; try String comparison
	        String s1 = String.valueOf(p1); // calls toString safely
	        String s2 = String.valueOf(p2); 
	        return s1.compareTo(s2); // String implements comparable
	      }
	    } else {
	      return comparator.compare(p1,p2);
	    }
  	} catch (Exception e) {
  	}
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
