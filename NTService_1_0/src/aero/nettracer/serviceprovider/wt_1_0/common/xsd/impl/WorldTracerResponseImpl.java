/*
 * XML Type:  WorldTracerResponse
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML WorldTracerResponse(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class WorldTracerResponseImpl extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl.GenericResponseImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse
{
    
    public WorldTracerResponseImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACTIONFILES$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "actionFiles");
    private static final javax.xml.namespace.QName AHL$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ahl");
    private static final javax.xml.namespace.QName CAPTCHA$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "captcha");
    private static final javax.xml.namespace.QName CAPTCHATIMESTAMP$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "captchaTimestamp");
    private static final javax.xml.namespace.QName CONNECTIONREF$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "connectionRef");
    private static final javax.xml.namespace.QName COUNTS$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "counts");
    private static final javax.xml.namespace.QName OHD$12 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ohd");
    private static final javax.xml.namespace.QName RESPONSEDATA$14 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "responseData");
    private static final javax.xml.namespace.QName RESPONSEID$16 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "responseId");
    private static final javax.xml.namespace.QName SUCCESS$18 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "success");
    
    
    /**
     * Gets array of all "actionFiles" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[] getActionFilesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ACTIONFILES$0, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "actionFiles" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile getActionFilesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().find_element_user(ACTIONFILES$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "actionFiles" element
     */
    public boolean isNilActionFilesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().find_element_user(ACTIONFILES$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "actionFiles" element
     */
    public int sizeOfActionFilesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACTIONFILES$0);
        }
    }
    
    /**
     * Sets array of all "actionFiles" element
     */
    public void setActionFilesArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[] actionFilesArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(actionFilesArray, ACTIONFILES$0);
        }
    }
    
    /**
     * Sets ith "actionFiles" element
     */
    public void setActionFilesArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile actionFiles)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().find_element_user(ACTIONFILES$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(actionFiles);
        }
    }
    
    /**
     * Nils the ith "actionFiles" element
     */
    public void setNilActionFilesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().find_element_user(ACTIONFILES$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "actionFiles" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile insertNewActionFiles(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().insert_element_user(ACTIONFILES$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "actionFiles" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile addNewActionFiles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile)get_store().add_element_user(ACTIONFILES$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "actionFiles" element
     */
    public void removeActionFiles(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACTIONFILES$0, i);
        }
    }
    
    /**
     * Gets the "ahl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl getAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "ahl" element
     */
    public boolean isNilAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ahl" element
     */
    public boolean isSetAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AHL$2) != 0;
        }
    }
    
    /**
     * Sets the "ahl" element
     */
    public void setAhl(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl ahl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
            }
            target.set(ahl);
        }
    }
    
    /**
     * Appends and returns a new empty "ahl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl addNewAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
            return target;
        }
    }
    
    /**
     * Nils the "ahl" element
     */
    public void setNilAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ahl" element
     */
    public void unsetAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AHL$2, 0);
        }
    }
    
    /**
     * Gets the "captcha" element
     */
    public byte[] getCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPTCHA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getByteArrayValue();
        }
    }
    
    /**
     * Gets (as xml) the "captcha" element
     */
    public org.apache.xmlbeans.XmlBase64Binary xgetCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBase64Binary target = null;
            target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(CAPTCHA$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "captcha" element
     */
    public boolean isNilCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBase64Binary target = null;
            target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(CAPTCHA$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "captcha" element
     */
    public boolean isSetCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPTCHA$4) != 0;
        }
    }
    
    /**
     * Sets the "captcha" element
     */
    public void setCaptcha(byte[] captcha)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPTCHA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPTCHA$4);
            }
            target.setByteArrayValue(captcha);
        }
    }
    
    /**
     * Sets (as xml) the "captcha" element
     */
    public void xsetCaptcha(org.apache.xmlbeans.XmlBase64Binary captcha)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBase64Binary target = null;
            target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(CAPTCHA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(CAPTCHA$4);
            }
            target.set(captcha);
        }
    }
    
    /**
     * Nils the "captcha" element
     */
    public void setNilCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBase64Binary target = null;
            target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(CAPTCHA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(CAPTCHA$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "captcha" element
     */
    public void unsetCaptcha()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPTCHA$4, 0);
        }
    }
    
    /**
     * Gets the "captchaTimestamp" element
     */
    public java.lang.String getCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "captchaTimestamp" element
     */
    public org.apache.xmlbeans.XmlString xgetCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "captchaTimestamp" element
     */
    public boolean isNilCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "captchaTimestamp" element
     */
    public boolean isSetCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPTCHATIMESTAMP$6) != 0;
        }
    }
    
    /**
     * Sets the "captchaTimestamp" element
     */
    public void setCaptchaTimestamp(java.lang.String captchaTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPTCHATIMESTAMP$6);
            }
            target.setStringValue(captchaTimestamp);
        }
    }
    
    /**
     * Sets (as xml) the "captchaTimestamp" element
     */
    public void xsetCaptchaTimestamp(org.apache.xmlbeans.XmlString captchaTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAPTCHATIMESTAMP$6);
            }
            target.set(captchaTimestamp);
        }
    }
    
    /**
     * Nils the "captchaTimestamp" element
     */
    public void setNilCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAPTCHATIMESTAMP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAPTCHATIMESTAMP$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "captchaTimestamp" element
     */
    public void unsetCaptchaTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPTCHATIMESTAMP$6, 0);
        }
    }
    
    /**
     * Gets the "connectionRef" element
     */
    public java.lang.String getConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONNECTIONREF$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "connectionRef" element
     */
    public org.apache.xmlbeans.XmlString xgetConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONNECTIONREF$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "connectionRef" element
     */
    public boolean isNilConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONNECTIONREF$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "connectionRef" element
     */
    public boolean isSetConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONNECTIONREF$8) != 0;
        }
    }
    
    /**
     * Sets the "connectionRef" element
     */
    public void setConnectionRef(java.lang.String connectionRef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONNECTIONREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONNECTIONREF$8);
            }
            target.setStringValue(connectionRef);
        }
    }
    
    /**
     * Sets (as xml) the "connectionRef" element
     */
    public void xsetConnectionRef(org.apache.xmlbeans.XmlString connectionRef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONNECTIONREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONNECTIONREF$8);
            }
            target.set(connectionRef);
        }
    }
    
    /**
     * Nils the "connectionRef" element
     */
    public void setNilConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONNECTIONREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONNECTIONREF$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "connectionRef" element
     */
    public void unsetConnectionRef()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONNECTIONREF$8, 0);
        }
    }
    
    /**
     * Gets array of all "counts" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] getCountsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(COUNTS$10, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "counts" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount getCountsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().find_element_user(COUNTS$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "counts" element
     */
    public boolean isNilCountsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().find_element_user(COUNTS$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "counts" element
     */
    public int sizeOfCountsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COUNTS$10);
        }
    }
    
    /**
     * Sets array of all "counts" element
     */
    public void setCountsArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] countsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(countsArray, COUNTS$10);
        }
    }
    
    /**
     * Sets ith "counts" element
     */
    public void setCountsArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount counts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().find_element_user(COUNTS$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(counts);
        }
    }
    
    /**
     * Nils the ith "counts" element
     */
    public void setNilCountsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().find_element_user(COUNTS$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "counts" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount insertNewCounts(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().insert_element_user(COUNTS$10, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "counts" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount addNewCounts()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount)get_store().add_element_user(COUNTS$10);
            return target;
        }
    }
    
    /**
     * Removes the ith "counts" element
     */
    public void removeCounts(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COUNTS$10, i);
        }
    }
    
    /**
     * Gets the "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "ohd" element
     */
    public boolean isNilOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ohd" element
     */
    public boolean isSetOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHD$12) != 0;
        }
    }
    
    /**
     * Sets the "ohd" element
     */
    public void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$12, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$12);
            }
            target.set(ohd);
        }
    }
    
    /**
     * Appends and returns a new empty "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$12);
            return target;
        }
    }
    
    /**
     * Nils the "ohd" element
     */
    public void setNilOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$12, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ohd" element
     */
    public void unsetOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHD$12, 0);
        }
    }
    
    /**
     * Gets the "responseData" element
     */
    public java.lang.String getResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESPONSEDATA$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "responseData" element
     */
    public org.apache.xmlbeans.XmlString xgetResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEDATA$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "responseData" element
     */
    public boolean isNilResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEDATA$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "responseData" element
     */
    public boolean isSetResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESPONSEDATA$14) != 0;
        }
    }
    
    /**
     * Sets the "responseData" element
     */
    public void setResponseData(java.lang.String responseData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESPONSEDATA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESPONSEDATA$14);
            }
            target.setStringValue(responseData);
        }
    }
    
    /**
     * Sets (as xml) the "responseData" element
     */
    public void xsetResponseData(org.apache.xmlbeans.XmlString responseData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEDATA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESPONSEDATA$14);
            }
            target.set(responseData);
        }
    }
    
    /**
     * Nils the "responseData" element
     */
    public void setNilResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEDATA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESPONSEDATA$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "responseData" element
     */
    public void unsetResponseData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESPONSEDATA$14, 0);
        }
    }
    
    /**
     * Gets the "responseId" element
     */
    public java.lang.String getResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESPONSEID$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "responseId" element
     */
    public org.apache.xmlbeans.XmlString xgetResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEID$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "responseId" element
     */
    public boolean isNilResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEID$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "responseId" element
     */
    public boolean isSetResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESPONSEID$16) != 0;
        }
    }
    
    /**
     * Sets the "responseId" element
     */
    public void setResponseId(java.lang.String responseId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESPONSEID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESPONSEID$16);
            }
            target.setStringValue(responseId);
        }
    }
    
    /**
     * Sets (as xml) the "responseId" element
     */
    public void xsetResponseId(org.apache.xmlbeans.XmlString responseId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESPONSEID$16);
            }
            target.set(responseId);
        }
    }
    
    /**
     * Nils the "responseId" element
     */
    public void setNilResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESPONSEID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESPONSEID$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "responseId" element
     */
    public void unsetResponseId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESPONSEID$16, 0);
        }
    }
    
    /**
     * Gets the "success" element
     */
    public boolean getSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUCCESS$18, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "success" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(SUCCESS$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "success" element
     */
    public boolean isSetSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUCCESS$18) != 0;
        }
    }
    
    /**
     * Sets the "success" element
     */
    public void setSuccess(boolean success)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUCCESS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUCCESS$18);
            }
            target.setBooleanValue(success);
        }
    }
    
    /**
     * Sets (as xml) the "success" element
     */
    public void xsetSuccess(org.apache.xmlbeans.XmlBoolean success)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(SUCCESS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(SUCCESS$18);
            }
            target.set(success);
        }
    }
    
    /**
     * Unsets the "success" element
     */
    public void unsetSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUCCESS$18, 0);
        }
    }
}
