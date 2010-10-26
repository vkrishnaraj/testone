/*
 * XML Type:  WS_Article
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_Article(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSArticleImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle
{
    
    public WSArticleImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ARTICLE$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "article");
    private static final javax.xml.namespace.QName COST$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "cost");
    private static final javax.xml.namespace.QName CURRENCYID$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "currency_ID");
    private static final javax.xml.namespace.QName DESCRIPTION$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "description");
    private static final javax.xml.namespace.QName PURCHASEDATE$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "purchasedate");
    
    
    /**
     * Gets the "article" element
     */
    public java.lang.String getArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARTICLE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "article" element
     */
    public org.apache.xmlbeans.XmlString xgetArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARTICLE$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "article" element
     */
    public boolean isNilArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARTICLE$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "article" element
     */
    public boolean isSetArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARTICLE$0) != 0;
        }
    }
    
    /**
     * Sets the "article" element
     */
    public void setArticle(java.lang.String article)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARTICLE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARTICLE$0);
            }
            target.setStringValue(article);
        }
    }
    
    /**
     * Sets (as xml) the "article" element
     */
    public void xsetArticle(org.apache.xmlbeans.XmlString article)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARTICLE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ARTICLE$0);
            }
            target.set(article);
        }
    }
    
    /**
     * Nils the "article" element
     */
    public void setNilArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARTICLE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ARTICLE$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "article" element
     */
    public void unsetArticle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARTICLE$0, 0);
        }
    }
    
    /**
     * Gets the "cost" element
     */
    public double getCost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COST$2, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "cost" element
     */
    public org.apache.xmlbeans.XmlDouble xgetCost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(COST$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "cost" element
     */
    public boolean isSetCost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COST$2) != 0;
        }
    }
    
    /**
     * Sets the "cost" element
     */
    public void setCost(double cost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COST$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COST$2);
            }
            target.setDoubleValue(cost);
        }
    }
    
    /**
     * Sets (as xml) the "cost" element
     */
    public void xsetCost(org.apache.xmlbeans.XmlDouble cost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(COST$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(COST$2);
            }
            target.set(cost);
        }
    }
    
    /**
     * Unsets the "cost" element
     */
    public void unsetCost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COST$2, 0);
        }
    }
    
    /**
     * Gets the "currency_ID" element
     */
    public java.lang.String getCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENCYID$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currency_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCYID$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currency_ID" element
     */
    public boolean isNilCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCYID$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currency_ID" element
     */
    public boolean isSetCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRENCYID$4) != 0;
        }
    }
    
    /**
     * Sets the "currency_ID" element
     */
    public void setCurrencyID(java.lang.String currencyID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENCYID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRENCYID$4);
            }
            target.setStringValue(currencyID);
        }
    }
    
    /**
     * Sets (as xml) the "currency_ID" element
     */
    public void xsetCurrencyID(org.apache.xmlbeans.XmlString currencyID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCYID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENCYID$4);
            }
            target.set(currencyID);
        }
    }
    
    /**
     * Nils the "currency_ID" element
     */
    public void setNilCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCYID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENCYID$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currency_ID" element
     */
    public void unsetCurrencyID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRENCYID$4, 0);
        }
    }
    
    /**
     * Gets the "description" element
     */
    public java.lang.String getDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "description" element
     */
    public org.apache.xmlbeans.XmlString xgetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "description" element
     */
    public boolean isNilDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "description" element
     */
    public boolean isSetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESCRIPTION$6) != 0;
        }
    }
    
    /**
     * Sets the "description" element
     */
    public void setDescription(java.lang.String description)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRIPTION$6);
            }
            target.setStringValue(description);
        }
    }
    
    /**
     * Sets (as xml) the "description" element
     */
    public void xsetDescription(org.apache.xmlbeans.XmlString description)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$6);
            }
            target.set(description);
        }
    }
    
    /**
     * Nils the "description" element
     */
    public void setNilDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "description" element
     */
    public void unsetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESCRIPTION$6, 0);
        }
    }
    
    /**
     * Gets the "purchasedate" element
     */
    public java.lang.String getPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDATE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "purchasedate" element
     */
    public org.apache.xmlbeans.XmlString xgetPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDATE$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "purchasedate" element
     */
    public boolean isNilPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDATE$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "purchasedate" element
     */
    public boolean isSetPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PURCHASEDATE$8) != 0;
        }
    }
    
    /**
     * Sets the "purchasedate" element
     */
    public void setPurchasedate(java.lang.String purchasedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PURCHASEDATE$8);
            }
            target.setStringValue(purchasedate);
        }
    }
    
    /**
     * Sets (as xml) the "purchasedate" element
     */
    public void xsetPurchasedate(org.apache.xmlbeans.XmlString purchasedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDATE$8);
            }
            target.set(purchasedate);
        }
    }
    
    /**
     * Nils the "purchasedate" element
     */
    public void setNilPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDATE$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "purchasedate" element
     */
    public void unsetPurchasedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PURCHASEDATE$8, 0);
        }
    }
}
