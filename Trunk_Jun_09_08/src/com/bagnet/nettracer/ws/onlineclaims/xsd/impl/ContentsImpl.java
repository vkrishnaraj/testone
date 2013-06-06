/*
 * XML Type:  Contents
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Contents
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML Contents(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class ContentsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.Contents
{
    private static final long serialVersionUID = 1L;
    
    public ContentsImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ARTICLE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "article");
    private static final javax.xml.namespace.QName BRAND$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "brand");
    private static final javax.xml.namespace.QName COLOR$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "color");
    private static final javax.xml.namespace.QName CONTENTOWNER$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "contentOwner");
    private static final javax.xml.namespace.QName CURRENCY$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "currency");
    private static final javax.xml.namespace.QName MALE$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "male");
    private static final javax.xml.namespace.QName PRICE$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "price");
    private static final javax.xml.namespace.QName PURCHASEDAT$14 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "purchasedAt");
    private static final javax.xml.namespace.QName PURCHASEDDATE$16 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "purchasedDate");
    private static final javax.xml.namespace.QName QUANTITY$18 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "quantity");
    private static final javax.xml.namespace.QName SIZE$20 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "size");
    
    
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
     * Gets the "brand" element
     */
    public java.lang.String getBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BRAND$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "brand" element
     */
    public org.apache.xmlbeans.XmlString xgetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "brand" element
     */
    public boolean isNilBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "brand" element
     */
    public boolean isSetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BRAND$2) != 0;
        }
    }
    
    /**
     * Sets the "brand" element
     */
    public void setBrand(java.lang.String brand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BRAND$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BRAND$2);
            }
            target.setStringValue(brand);
        }
    }
    
    /**
     * Sets (as xml) the "brand" element
     */
    public void xsetBrand(org.apache.xmlbeans.XmlString brand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BRAND$2);
            }
            target.set(brand);
        }
    }
    
    /**
     * Nils the "brand" element
     */
    public void setNilBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BRAND$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "brand" element
     */
    public void unsetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BRAND$2, 0);
        }
    }
    
    /**
     * Gets the "color" element
     */
    public java.lang.String getColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "color" element
     */
    public org.apache.xmlbeans.XmlString xgetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "color" element
     */
    public boolean isNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "color" element
     */
    public boolean isSetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COLOR$4) != 0;
        }
    }
    
    /**
     * Sets the "color" element
     */
    public void setColor(java.lang.String color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLOR$4);
            }
            target.setStringValue(color);
        }
    }
    
    /**
     * Sets (as xml) the "color" element
     */
    public void xsetColor(org.apache.xmlbeans.XmlString color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$4);
            }
            target.set(color);
        }
    }
    
    /**
     * Nils the "color" element
     */
    public void setNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "color" element
     */
    public void unsetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COLOR$4, 0);
        }
    }
    
    /**
     * Gets the "contentOwner" element
     */
    public java.lang.String getContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENTOWNER$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "contentOwner" element
     */
    public org.apache.xmlbeans.XmlString xgetContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENTOWNER$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "contentOwner" element
     */
    public boolean isNilContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENTOWNER$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "contentOwner" element
     */
    public boolean isSetContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTENTOWNER$6) != 0;
        }
    }
    
    /**
     * Sets the "contentOwner" element
     */
    public void setContentOwner(java.lang.String contentOwner)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENTOWNER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTENTOWNER$6);
            }
            target.setStringValue(contentOwner);
        }
    }
    
    /**
     * Sets (as xml) the "contentOwner" element
     */
    public void xsetContentOwner(org.apache.xmlbeans.XmlString contentOwner)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENTOWNER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTENTOWNER$6);
            }
            target.set(contentOwner);
        }
    }
    
    /**
     * Nils the "contentOwner" element
     */
    public void setNilContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENTOWNER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTENTOWNER$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "contentOwner" element
     */
    public void unsetContentOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTENTOWNER$6, 0);
        }
    }
    
    /**
     * Gets the "currency" element
     */
    public java.lang.String getCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENCY$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currency" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCY$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currency" element
     */
    public boolean isNilCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCY$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currency" element
     */
    public boolean isSetCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRENCY$8) != 0;
        }
    }
    
    /**
     * Sets the "currency" element
     */
    public void setCurrency(java.lang.String currency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENCY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRENCY$8);
            }
            target.setStringValue(currency);
        }
    }
    
    /**
     * Sets (as xml) the "currency" element
     */
    public void xsetCurrency(org.apache.xmlbeans.XmlString currency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENCY$8);
            }
            target.set(currency);
        }
    }
    
    /**
     * Nils the "currency" element
     */
    public void setNilCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENCY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENCY$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currency" element
     */
    public void unsetCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRENCY$8, 0);
        }
    }
    
    /**
     * Gets the "male" element
     */
    public int getMale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MALE$10, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "male" element
     */
    public org.apache.xmlbeans.XmlInt xgetMale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MALE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "male" element
     */
    public boolean isSetMale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MALE$10) != 0;
        }
    }
    
    /**
     * Sets the "male" element
     */
    public void setMale(int male)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MALE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MALE$10);
            }
            target.setIntValue(male);
        }
    }
    
    /**
     * Sets (as xml) the "male" element
     */
    public void xsetMale(org.apache.xmlbeans.XmlInt male)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MALE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MALE$10);
            }
            target.set(male);
        }
    }
    
    /**
     * Unsets the "male" element
     */
    public void unsetMale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MALE$10, 0);
        }
    }
    
    /**
     * Gets the "price" element
     */
    public double getPrice()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRICE$12, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "price" element
     */
    public org.apache.xmlbeans.XmlDouble xgetPrice()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PRICE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "price" element
     */
    public boolean isSetPrice()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRICE$12) != 0;
        }
    }
    
    /**
     * Sets the "price" element
     */
    public void setPrice(double price)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRICE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRICE$12);
            }
            target.setDoubleValue(price);
        }
    }
    
    /**
     * Sets (as xml) the "price" element
     */
    public void xsetPrice(org.apache.xmlbeans.XmlDouble price)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PRICE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(PRICE$12);
            }
            target.set(price);
        }
    }
    
    /**
     * Unsets the "price" element
     */
    public void unsetPrice()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRICE$12, 0);
        }
    }
    
    /**
     * Gets the "purchasedAt" element
     */
    public java.lang.String getPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDAT$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "purchasedAt" element
     */
    public org.apache.xmlbeans.XmlString xgetPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDAT$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "purchasedAt" element
     */
    public boolean isNilPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDAT$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "purchasedAt" element
     */
    public boolean isSetPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PURCHASEDAT$14) != 0;
        }
    }
    
    /**
     * Sets the "purchasedAt" element
     */
    public void setPurchasedAt(java.lang.String purchasedAt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDAT$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PURCHASEDAT$14);
            }
            target.setStringValue(purchasedAt);
        }
    }
    
    /**
     * Sets (as xml) the "purchasedAt" element
     */
    public void xsetPurchasedAt(org.apache.xmlbeans.XmlString purchasedAt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDAT$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDAT$14);
            }
            target.set(purchasedAt);
        }
    }
    
    /**
     * Nils the "purchasedAt" element
     */
    public void setNilPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDAT$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDAT$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "purchasedAt" element
     */
    public void unsetPurchasedAt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PURCHASEDAT$14, 0);
        }
    }
    
    /**
     * Gets the "purchasedDate" element
     */
    public java.lang.String getPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDDATE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "purchasedDate" element
     */
    public org.apache.xmlbeans.XmlString xgetPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDDATE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "purchasedDate" element
     */
    public boolean isNilPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDDATE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "purchasedDate" element
     */
    public boolean isSetPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PURCHASEDDATE$16) != 0;
        }
    }
    
    /**
     * Sets the "purchasedDate" element
     */
    public void setPurchasedDate(java.lang.String purchasedDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PURCHASEDDATE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PURCHASEDDATE$16);
            }
            target.setStringValue(purchasedDate);
        }
    }
    
    /**
     * Sets (as xml) the "purchasedDate" element
     */
    public void xsetPurchasedDate(org.apache.xmlbeans.XmlString purchasedDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDDATE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDDATE$16);
            }
            target.set(purchasedDate);
        }
    }
    
    /**
     * Nils the "purchasedDate" element
     */
    public void setNilPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PURCHASEDDATE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PURCHASEDDATE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "purchasedDate" element
     */
    public void unsetPurchasedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PURCHASEDDATE$16, 0);
        }
    }
    
    /**
     * Gets the "quantity" element
     */
    public int getQuantity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUANTITY$18, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "quantity" element
     */
    public org.apache.xmlbeans.XmlInt xgetQuantity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(QUANTITY$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "quantity" element
     */
    public boolean isSetQuantity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUANTITY$18) != 0;
        }
    }
    
    /**
     * Sets the "quantity" element
     */
    public void setQuantity(int quantity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUANTITY$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUANTITY$18);
            }
            target.setIntValue(quantity);
        }
    }
    
    /**
     * Sets (as xml) the "quantity" element
     */
    public void xsetQuantity(org.apache.xmlbeans.XmlInt quantity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(QUANTITY$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(QUANTITY$18);
            }
            target.set(quantity);
        }
    }
    
    /**
     * Unsets the "quantity" element
     */
    public void unsetQuantity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUANTITY$18, 0);
        }
    }
    
    /**
     * Gets the "size" element
     */
    public java.lang.String getSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIZE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "size" element
     */
    public org.apache.xmlbeans.XmlString xgetSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIZE$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "size" element
     */
    public boolean isNilSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIZE$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "size" element
     */
    public boolean isSetSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SIZE$20) != 0;
        }
    }
    
    /**
     * Sets the "size" element
     */
    public void setSize(java.lang.String size)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIZE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIZE$20);
            }
            target.setStringValue(size);
        }
    }
    
    /**
     * Sets (as xml) the "size" element
     */
    public void xsetSize(org.apache.xmlbeans.XmlString size)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIZE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIZE$20);
            }
            target.set(size);
        }
    }
    
    /**
     * Nils the "size" element
     */
    public void setNilSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIZE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIZE$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "size" element
     */
    public void unsetSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SIZE$20, 0);
        }
    }
}
