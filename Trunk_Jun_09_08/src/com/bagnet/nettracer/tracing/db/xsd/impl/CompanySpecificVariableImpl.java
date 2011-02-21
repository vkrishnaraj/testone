/*
 * XML Type:  Company_Specific_Variable
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Company_Specific_Variable(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class CompanySpecificVariableImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable
{
    
    public CompanySpecificVariableImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUDITAGENT$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_agent");
    private static final javax.xml.namespace.QName AUDITAIRPORT$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_airport");
    private static final javax.xml.namespace.QName AUDITCLAIMS$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_claims");
    private static final javax.xml.namespace.QName AUDITCOMPANY$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_company");
    private static final javax.xml.namespace.QName AUDITDAMAGED$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_damaged");
    private static final javax.xml.namespace.QName AUDITDELIVERYCOMPANIES$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_delivery_companies");
    private static final javax.xml.namespace.QName AUDITGROUP$12 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_group");
    private static final javax.xml.namespace.QName AUDITLOSSCODES$14 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_loss_codes");
    private static final javax.xml.namespace.QName AUDITLOSTDELAYED$16 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_lost_delayed");
    private static final javax.xml.namespace.QName AUDITLOSTFOUND$18 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_lost_found");
    private static final javax.xml.namespace.QName AUDITMISSINGARTICLES$20 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_missing_articles");
    private static final javax.xml.namespace.QName AUDITOHD$22 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_ohd");
    private static final javax.xml.namespace.QName AUDITSHIFT$24 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_shift");
    private static final javax.xml.namespace.QName AUDITSTATION$26 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "audit_station");
    private static final javax.xml.namespace.QName AUTOCLOSEOHD$28 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "autoCloseOhd");
    private static final javax.xml.namespace.QName AUTOWTAMEND$30 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "auto_wt_amend");
    private static final javax.xml.namespace.QName BAKNTTRACERDATADAYS$32 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "bak_nttracer_data_days");
    private static final javax.xml.namespace.QName BAKNTTRACERLOSTFOUNDDATADAYS$34 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "bak_nttracer_lostfound_data_days");
    private static final javax.xml.namespace.QName BAKNTTRACEROHDDATADAYS$36 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "bak_nttracer_ohd_data_days");
    private static final javax.xml.namespace.QName BLINDEMAIL$38 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "blindEmail");
    private static final javax.xml.namespace.QName COMPANYCODEID$40 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "companyCode_ID");
    private static final javax.xml.namespace.QName DAMAGEDTOLZDAYS$42 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "damaged_to_lz_days");
    private static final javax.xml.namespace.QName DEFAULTLOSSCODE$44 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "default_loss_code");
    private static final javax.xml.namespace.QName DEFAULTSTATIONCODE$46 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "default_station_code");
    private static final javax.xml.namespace.QName EMAILCUSTOMER$48 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_customer");
    private static final javax.xml.namespace.QName EMAILFROM$50 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_from");
    private static final javax.xml.namespace.QName EMAILHOST$52 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_host");
    private static final javax.xml.namespace.QName EMAILPORT$54 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_port");
    private static final javax.xml.namespace.QName EMAILTO$56 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_to");
    private static final javax.xml.namespace.QName LZMODE$58 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "lz_mode");
    private static final javax.xml.namespace.QName MAXFAILEDLOGINS$60 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "max_failed_logins");
    private static final javax.xml.namespace.QName MAXIMAGEFILESIZE$62 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "max_image_file_size");
    private static final javax.xml.namespace.QName MBRTOLZDAYS$64 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "mbr_to_lz_days");
    private static final javax.xml.namespace.QName MBRTOWTDAYS$66 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "mbr_to_wt_days");
    private static final javax.xml.namespace.QName MININTERIMAPPROVALCCREFUND$68 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_interim_approval_cc_refund");
    private static final javax.xml.namespace.QName MININTERIMAPPROVALCHECK$70 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_interim_approval_check");
    private static final javax.xml.namespace.QName MININTERIMAPPROVALINCIDENTAL$72 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_interim_approval_incidental");
    private static final javax.xml.namespace.QName MININTERIMAPPROVALMILES$74 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_interim_approval_miles");
    private static final javax.xml.namespace.QName MININTERIMAPPROVALVOUCHER$76 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_interim_approval_voucher");
    private static final javax.xml.namespace.QName MINMATCHPERCENT$78 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "min_match_percent");
    private static final javax.xml.namespace.QName MISSTOLZDAYS$80 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "miss_to_lz_days");
    private static final javax.xml.namespace.QName OALINCHOURS$82 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "oal_inc_hours");
    private static final javax.xml.namespace.QName OALOHDHOURS$84 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "oal_ohd_hours");
    private static final javax.xml.namespace.QName OHDLZ$86 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "ohd_lz");
    private static final javax.xml.namespace.QName OHDTOLZDAYS$88 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "ohd_to_lz_days");
    private static final javax.xml.namespace.QName OHDTOWTHOURS$90 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "ohd_to_wt_hours");
    private static final javax.xml.namespace.QName PASSEXPIREDAYS$92 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "pass_expire_days");
    private static final javax.xml.namespace.QName REPORTMETHOD$94 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "report_method");
    private static final javax.xml.namespace.QName RETRIEVEACTIONFILEINTERVAL$96 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "retrieve_actionfile_interval");
    private static final javax.xml.namespace.QName SCANNERDEFAULTBACK$98 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "scannerDefaultBack");
    private static final javax.xml.namespace.QName SCANNERDEFAULTFORWARD$100 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "scannerDefaultForward");
    private static final javax.xml.namespace.QName SECONDSWAIT$102 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "seconds_wait");
    private static final javax.xml.namespace.QName SECUREPASSWORD$104 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "secure_password");
    private static final javax.xml.namespace.QName TOTALTHREADS$106 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "total_threads");
    private static final javax.xml.namespace.QName WEBSENABLED$108 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "webs_enabled");
    private static final javax.xml.namespace.QName WTAIRLINECODE$110 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_airlinecode");
    private static final javax.xml.namespace.QName WTENABLED$112 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_enabled");
    private static final javax.xml.namespace.QName WTPASS$114 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_pass");
    private static final javax.xml.namespace.QName WTURL$116 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_url");
    private static final javax.xml.namespace.QName WTUSER$118 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_user");
    private static final javax.xml.namespace.QName WTWRITEENABLED$120 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_write_enabled");
    
    
    /**
     * Gets the "audit_agent" element
     */
    public int getAuditAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITAGENT$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_agent" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITAGENT$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_agent" element
     */
    public boolean isSetAuditAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITAGENT$0) != 0;
        }
    }
    
    /**
     * Sets the "audit_agent" element
     */
    public void setAuditAgent(int auditAgent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITAGENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITAGENT$0);
            }
            target.setIntValue(auditAgent);
        }
    }
    
    /**
     * Sets (as xml) the "audit_agent" element
     */
    public void xsetAuditAgent(org.apache.xmlbeans.XmlInt auditAgent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITAGENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITAGENT$0);
            }
            target.set(auditAgent);
        }
    }
    
    /**
     * Unsets the "audit_agent" element
     */
    public void unsetAuditAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITAGENT$0, 0);
        }
    }
    
    /**
     * Gets the "audit_airport" element
     */
    public int getAuditAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITAIRPORT$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_airport" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITAIRPORT$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_airport" element
     */
    public boolean isSetAuditAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITAIRPORT$2) != 0;
        }
    }
    
    /**
     * Sets the "audit_airport" element
     */
    public void setAuditAirport(int auditAirport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITAIRPORT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITAIRPORT$2);
            }
            target.setIntValue(auditAirport);
        }
    }
    
    /**
     * Sets (as xml) the "audit_airport" element
     */
    public void xsetAuditAirport(org.apache.xmlbeans.XmlInt auditAirport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITAIRPORT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITAIRPORT$2);
            }
            target.set(auditAirport);
        }
    }
    
    /**
     * Unsets the "audit_airport" element
     */
    public void unsetAuditAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITAIRPORT$2, 0);
        }
    }
    
    /**
     * Gets the "audit_claims" element
     */
    public int getAuditClaims()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITCLAIMS$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_claims" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditClaims()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITCLAIMS$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_claims" element
     */
    public boolean isSetAuditClaims()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITCLAIMS$4) != 0;
        }
    }
    
    /**
     * Sets the "audit_claims" element
     */
    public void setAuditClaims(int auditClaims)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITCLAIMS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITCLAIMS$4);
            }
            target.setIntValue(auditClaims);
        }
    }
    
    /**
     * Sets (as xml) the "audit_claims" element
     */
    public void xsetAuditClaims(org.apache.xmlbeans.XmlInt auditClaims)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITCLAIMS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITCLAIMS$4);
            }
            target.set(auditClaims);
        }
    }
    
    /**
     * Unsets the "audit_claims" element
     */
    public void unsetAuditClaims()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITCLAIMS$4, 0);
        }
    }
    
    /**
     * Gets the "audit_company" element
     */
    public int getAuditCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITCOMPANY$6, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_company" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITCOMPANY$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_company" element
     */
    public boolean isSetAuditCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITCOMPANY$6) != 0;
        }
    }
    
    /**
     * Sets the "audit_company" element
     */
    public void setAuditCompany(int auditCompany)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITCOMPANY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITCOMPANY$6);
            }
            target.setIntValue(auditCompany);
        }
    }
    
    /**
     * Sets (as xml) the "audit_company" element
     */
    public void xsetAuditCompany(org.apache.xmlbeans.XmlInt auditCompany)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITCOMPANY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITCOMPANY$6);
            }
            target.set(auditCompany);
        }
    }
    
    /**
     * Unsets the "audit_company" element
     */
    public void unsetAuditCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITCOMPANY$6, 0);
        }
    }
    
    /**
     * Gets the "audit_damaged" element
     */
    public int getAuditDamaged()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITDAMAGED$8, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_damaged" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditDamaged()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITDAMAGED$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_damaged" element
     */
    public boolean isSetAuditDamaged()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITDAMAGED$8) != 0;
        }
    }
    
    /**
     * Sets the "audit_damaged" element
     */
    public void setAuditDamaged(int auditDamaged)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITDAMAGED$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITDAMAGED$8);
            }
            target.setIntValue(auditDamaged);
        }
    }
    
    /**
     * Sets (as xml) the "audit_damaged" element
     */
    public void xsetAuditDamaged(org.apache.xmlbeans.XmlInt auditDamaged)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITDAMAGED$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITDAMAGED$8);
            }
            target.set(auditDamaged);
        }
    }
    
    /**
     * Unsets the "audit_damaged" element
     */
    public void unsetAuditDamaged()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITDAMAGED$8, 0);
        }
    }
    
    /**
     * Gets the "audit_delivery_companies" element
     */
    public int getAuditDeliveryCompanies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITDELIVERYCOMPANIES$10, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_delivery_companies" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditDeliveryCompanies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITDELIVERYCOMPANIES$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_delivery_companies" element
     */
    public boolean isSetAuditDeliveryCompanies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITDELIVERYCOMPANIES$10) != 0;
        }
    }
    
    /**
     * Sets the "audit_delivery_companies" element
     */
    public void setAuditDeliveryCompanies(int auditDeliveryCompanies)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITDELIVERYCOMPANIES$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITDELIVERYCOMPANIES$10);
            }
            target.setIntValue(auditDeliveryCompanies);
        }
    }
    
    /**
     * Sets (as xml) the "audit_delivery_companies" element
     */
    public void xsetAuditDeliveryCompanies(org.apache.xmlbeans.XmlInt auditDeliveryCompanies)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITDELIVERYCOMPANIES$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITDELIVERYCOMPANIES$10);
            }
            target.set(auditDeliveryCompanies);
        }
    }
    
    /**
     * Unsets the "audit_delivery_companies" element
     */
    public void unsetAuditDeliveryCompanies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITDELIVERYCOMPANIES$10, 0);
        }
    }
    
    /**
     * Gets the "audit_group" element
     */
    public int getAuditGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITGROUP$12, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_group" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITGROUP$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_group" element
     */
    public boolean isSetAuditGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITGROUP$12) != 0;
        }
    }
    
    /**
     * Sets the "audit_group" element
     */
    public void setAuditGroup(int auditGroup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITGROUP$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITGROUP$12);
            }
            target.setIntValue(auditGroup);
        }
    }
    
    /**
     * Sets (as xml) the "audit_group" element
     */
    public void xsetAuditGroup(org.apache.xmlbeans.XmlInt auditGroup)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITGROUP$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITGROUP$12);
            }
            target.set(auditGroup);
        }
    }
    
    /**
     * Unsets the "audit_group" element
     */
    public void unsetAuditGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITGROUP$12, 0);
        }
    }
    
    /**
     * Gets the "audit_loss_codes" element
     */
    public int getAuditLossCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSSCODES$14, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_loss_codes" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditLossCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSSCODES$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_loss_codes" element
     */
    public boolean isSetAuditLossCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITLOSSCODES$14) != 0;
        }
    }
    
    /**
     * Sets the "audit_loss_codes" element
     */
    public void setAuditLossCodes(int auditLossCodes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSSCODES$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITLOSSCODES$14);
            }
            target.setIntValue(auditLossCodes);
        }
    }
    
    /**
     * Sets (as xml) the "audit_loss_codes" element
     */
    public void xsetAuditLossCodes(org.apache.xmlbeans.XmlInt auditLossCodes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSSCODES$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITLOSSCODES$14);
            }
            target.set(auditLossCodes);
        }
    }
    
    /**
     * Unsets the "audit_loss_codes" element
     */
    public void unsetAuditLossCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITLOSSCODES$14, 0);
        }
    }
    
    /**
     * Gets the "audit_lost_delayed" element
     */
    public int getAuditLostDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSTDELAYED$16, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_lost_delayed" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditLostDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSTDELAYED$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_lost_delayed" element
     */
    public boolean isSetAuditLostDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITLOSTDELAYED$16) != 0;
        }
    }
    
    /**
     * Sets the "audit_lost_delayed" element
     */
    public void setAuditLostDelayed(int auditLostDelayed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSTDELAYED$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITLOSTDELAYED$16);
            }
            target.setIntValue(auditLostDelayed);
        }
    }
    
    /**
     * Sets (as xml) the "audit_lost_delayed" element
     */
    public void xsetAuditLostDelayed(org.apache.xmlbeans.XmlInt auditLostDelayed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSTDELAYED$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITLOSTDELAYED$16);
            }
            target.set(auditLostDelayed);
        }
    }
    
    /**
     * Unsets the "audit_lost_delayed" element
     */
    public void unsetAuditLostDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITLOSTDELAYED$16, 0);
        }
    }
    
    /**
     * Gets the "audit_lost_found" element
     */
    public int getAuditLostFound()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSTFOUND$18, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_lost_found" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditLostFound()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSTFOUND$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_lost_found" element
     */
    public boolean isSetAuditLostFound()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITLOSTFOUND$18) != 0;
        }
    }
    
    /**
     * Sets the "audit_lost_found" element
     */
    public void setAuditLostFound(int auditLostFound)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITLOSTFOUND$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITLOSTFOUND$18);
            }
            target.setIntValue(auditLostFound);
        }
    }
    
    /**
     * Sets (as xml) the "audit_lost_found" element
     */
    public void xsetAuditLostFound(org.apache.xmlbeans.XmlInt auditLostFound)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITLOSTFOUND$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITLOSTFOUND$18);
            }
            target.set(auditLostFound);
        }
    }
    
    /**
     * Unsets the "audit_lost_found" element
     */
    public void unsetAuditLostFound()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITLOSTFOUND$18, 0);
        }
    }
    
    /**
     * Gets the "audit_missing_articles" element
     */
    public int getAuditMissingArticles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITMISSINGARTICLES$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_missing_articles" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditMissingArticles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITMISSINGARTICLES$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_missing_articles" element
     */
    public boolean isSetAuditMissingArticles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITMISSINGARTICLES$20) != 0;
        }
    }
    
    /**
     * Sets the "audit_missing_articles" element
     */
    public void setAuditMissingArticles(int auditMissingArticles)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITMISSINGARTICLES$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITMISSINGARTICLES$20);
            }
            target.setIntValue(auditMissingArticles);
        }
    }
    
    /**
     * Sets (as xml) the "audit_missing_articles" element
     */
    public void xsetAuditMissingArticles(org.apache.xmlbeans.XmlInt auditMissingArticles)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITMISSINGARTICLES$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITMISSINGARTICLES$20);
            }
            target.set(auditMissingArticles);
        }
    }
    
    /**
     * Unsets the "audit_missing_articles" element
     */
    public void unsetAuditMissingArticles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITMISSINGARTICLES$20, 0);
        }
    }
    
    /**
     * Gets the "audit_ohd" element
     */
    public int getAuditOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITOHD$22, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_ohd" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITOHD$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_ohd" element
     */
    public boolean isSetAuditOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITOHD$22) != 0;
        }
    }
    
    /**
     * Sets the "audit_ohd" element
     */
    public void setAuditOhd(int auditOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITOHD$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITOHD$22);
            }
            target.setIntValue(auditOhd);
        }
    }
    
    /**
     * Sets (as xml) the "audit_ohd" element
     */
    public void xsetAuditOhd(org.apache.xmlbeans.XmlInt auditOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITOHD$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITOHD$22);
            }
            target.set(auditOhd);
        }
    }
    
    /**
     * Unsets the "audit_ohd" element
     */
    public void unsetAuditOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITOHD$22, 0);
        }
    }
    
    /**
     * Gets the "audit_shift" element
     */
    public int getAuditShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITSHIFT$24, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_shift" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITSHIFT$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_shift" element
     */
    public boolean isSetAuditShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITSHIFT$24) != 0;
        }
    }
    
    /**
     * Sets the "audit_shift" element
     */
    public void setAuditShift(int auditShift)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITSHIFT$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITSHIFT$24);
            }
            target.setIntValue(auditShift);
        }
    }
    
    /**
     * Sets (as xml) the "audit_shift" element
     */
    public void xsetAuditShift(org.apache.xmlbeans.XmlInt auditShift)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITSHIFT$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITSHIFT$24);
            }
            target.set(auditShift);
        }
    }
    
    /**
     * Unsets the "audit_shift" element
     */
    public void unsetAuditShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITSHIFT$24, 0);
        }
    }
    
    /**
     * Gets the "audit_station" element
     */
    public int getAuditStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITSTATION$26, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "audit_station" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuditStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITSTATION$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "audit_station" element
     */
    public boolean isSetAuditStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUDITSTATION$26) != 0;
        }
    }
    
    /**
     * Sets the "audit_station" element
     */
    public void setAuditStation(int auditStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUDITSTATION$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUDITSTATION$26);
            }
            target.setIntValue(auditStation);
        }
    }
    
    /**
     * Sets (as xml) the "audit_station" element
     */
    public void xsetAuditStation(org.apache.xmlbeans.XmlInt auditStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUDITSTATION$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUDITSTATION$26);
            }
            target.set(auditStation);
        }
    }
    
    /**
     * Unsets the "audit_station" element
     */
    public void unsetAuditStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUDITSTATION$26, 0);
        }
    }
    
    /**
     * Gets the "autoCloseOhd" element
     */
    public boolean getAutoCloseOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTOCLOSEOHD$28, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "autoCloseOhd" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetAutoCloseOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTOCLOSEOHD$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "autoCloseOhd" element
     */
    public boolean isSetAutoCloseOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUTOCLOSEOHD$28) != 0;
        }
    }
    
    /**
     * Sets the "autoCloseOhd" element
     */
    public void setAutoCloseOhd(boolean autoCloseOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTOCLOSEOHD$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTOCLOSEOHD$28);
            }
            target.setBooleanValue(autoCloseOhd);
        }
    }
    
    /**
     * Sets (as xml) the "autoCloseOhd" element
     */
    public void xsetAutoCloseOhd(org.apache.xmlbeans.XmlBoolean autoCloseOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTOCLOSEOHD$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(AUTOCLOSEOHD$28);
            }
            target.set(autoCloseOhd);
        }
    }
    
    /**
     * Unsets the "autoCloseOhd" element
     */
    public void unsetAutoCloseOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUTOCLOSEOHD$28, 0);
        }
    }
    
    /**
     * Gets the "auto_wt_amend" element
     */
    public boolean getAutoWtAmend()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTOWTAMEND$30, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "auto_wt_amend" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetAutoWtAmend()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTOWTAMEND$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "auto_wt_amend" element
     */
    public boolean isSetAutoWtAmend()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUTOWTAMEND$30) != 0;
        }
    }
    
    /**
     * Sets the "auto_wt_amend" element
     */
    public void setAutoWtAmend(boolean autoWtAmend)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTOWTAMEND$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTOWTAMEND$30);
            }
            target.setBooleanValue(autoWtAmend);
        }
    }
    
    /**
     * Sets (as xml) the "auto_wt_amend" element
     */
    public void xsetAutoWtAmend(org.apache.xmlbeans.XmlBoolean autoWtAmend)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTOWTAMEND$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(AUTOWTAMEND$30);
            }
            target.set(autoWtAmend);
        }
    }
    
    /**
     * Unsets the "auto_wt_amend" element
     */
    public void unsetAutoWtAmend()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUTOWTAMEND$30, 0);
        }
    }
    
    /**
     * Gets the "bak_nttracer_data_days" element
     */
    public int getBakNttracerDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACERDATADAYS$32, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bak_nttracer_data_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetBakNttracerDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACERDATADAYS$32, 0);
            return target;
        }
    }
    
    /**
     * True if has "bak_nttracer_data_days" element
     */
    public boolean isSetBakNttracerDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAKNTTRACERDATADAYS$32) != 0;
        }
    }
    
    /**
     * Sets the "bak_nttracer_data_days" element
     */
    public void setBakNttracerDataDays(int bakNttracerDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACERDATADAYS$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAKNTTRACERDATADAYS$32);
            }
            target.setIntValue(bakNttracerDataDays);
        }
    }
    
    /**
     * Sets (as xml) the "bak_nttracer_data_days" element
     */
    public void xsetBakNttracerDataDays(org.apache.xmlbeans.XmlInt bakNttracerDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACERDATADAYS$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAKNTTRACERDATADAYS$32);
            }
            target.set(bakNttracerDataDays);
        }
    }
    
    /**
     * Unsets the "bak_nttracer_data_days" element
     */
    public void unsetBakNttracerDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAKNTTRACERDATADAYS$32, 0);
        }
    }
    
    /**
     * Gets the "bak_nttracer_lostfound_data_days" element
     */
    public int getBakNttracerLostfoundDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bak_nttracer_lostfound_data_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetBakNttracerLostfoundDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "bak_nttracer_lostfound_data_days" element
     */
    public boolean isSetBakNttracerLostfoundDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAKNTTRACERLOSTFOUNDDATADAYS$34) != 0;
        }
    }
    
    /**
     * Sets the "bak_nttracer_lostfound_data_days" element
     */
    public void setBakNttracerLostfoundDataDays(int bakNttracerLostfoundDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34);
            }
            target.setIntValue(bakNttracerLostfoundDataDays);
        }
    }
    
    /**
     * Sets (as xml) the "bak_nttracer_lostfound_data_days" element
     */
    public void xsetBakNttracerLostfoundDataDays(org.apache.xmlbeans.XmlInt bakNttracerLostfoundDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAKNTTRACERLOSTFOUNDDATADAYS$34);
            }
            target.set(bakNttracerLostfoundDataDays);
        }
    }
    
    /**
     * Unsets the "bak_nttracer_lostfound_data_days" element
     */
    public void unsetBakNttracerLostfoundDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAKNTTRACERLOSTFOUNDDATADAYS$34, 0);
        }
    }
    
    /**
     * Gets the "bak_nttracer_ohd_data_days" element
     */
    public int getBakNttracerOhdDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACEROHDDATADAYS$36, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bak_nttracer_ohd_data_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetBakNttracerOhdDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACEROHDDATADAYS$36, 0);
            return target;
        }
    }
    
    /**
     * True if has "bak_nttracer_ohd_data_days" element
     */
    public boolean isSetBakNttracerOhdDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAKNTTRACEROHDDATADAYS$36) != 0;
        }
    }
    
    /**
     * Sets the "bak_nttracer_ohd_data_days" element
     */
    public void setBakNttracerOhdDataDays(int bakNttracerOhdDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAKNTTRACEROHDDATADAYS$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAKNTTRACEROHDDATADAYS$36);
            }
            target.setIntValue(bakNttracerOhdDataDays);
        }
    }
    
    /**
     * Sets (as xml) the "bak_nttracer_ohd_data_days" element
     */
    public void xsetBakNttracerOhdDataDays(org.apache.xmlbeans.XmlInt bakNttracerOhdDataDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAKNTTRACEROHDDATADAYS$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAKNTTRACEROHDDATADAYS$36);
            }
            target.set(bakNttracerOhdDataDays);
        }
    }
    
    /**
     * Unsets the "bak_nttracer_ohd_data_days" element
     */
    public void unsetBakNttracerOhdDataDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAKNTTRACEROHDDATADAYS$36, 0);
        }
    }
    
    /**
     * Gets the "blindEmail" element
     */
    public java.lang.String getBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BLINDEMAIL$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "blindEmail" element
     */
    public org.apache.xmlbeans.XmlString xgetBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BLINDEMAIL$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "blindEmail" element
     */
    public boolean isNilBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BLINDEMAIL$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "blindEmail" element
     */
    public boolean isSetBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BLINDEMAIL$38) != 0;
        }
    }
    
    /**
     * Sets the "blindEmail" element
     */
    public void setBlindEmail(java.lang.String blindEmail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BLINDEMAIL$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BLINDEMAIL$38);
            }
            target.setStringValue(blindEmail);
        }
    }
    
    /**
     * Sets (as xml) the "blindEmail" element
     */
    public void xsetBlindEmail(org.apache.xmlbeans.XmlString blindEmail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BLINDEMAIL$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BLINDEMAIL$38);
            }
            target.set(blindEmail);
        }
    }
    
    /**
     * Nils the "blindEmail" element
     */
    public void setNilBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BLINDEMAIL$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BLINDEMAIL$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "blindEmail" element
     */
    public void unsetBlindEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BLINDEMAIL$38, 0);
        }
    }
    
    /**
     * Gets the "companyCode_ID" element
     */
    public java.lang.String getCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "companyCode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$40, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "companyCode_ID" element
     */
    public boolean isNilCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$40, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "companyCode_ID" element
     */
    public boolean isSetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$40) != 0;
        }
    }
    
    /**
     * Sets the "companyCode_ID" element
     */
    public void setCompanyCodeID(java.lang.String companyCodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$40);
            }
            target.setStringValue(companyCodeID);
        }
    }
    
    /**
     * Sets (as xml) the "companyCode_ID" element
     */
    public void xsetCompanyCodeID(org.apache.xmlbeans.XmlString companyCodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$40);
            }
            target.set(companyCodeID);
        }
    }
    
    /**
     * Nils the "companyCode_ID" element
     */
    public void setNilCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$40);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "companyCode_ID" element
     */
    public void unsetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$40, 0);
        }
    }
    
    /**
     * Gets the "damaged_to_lz_days" element
     */
    public int getDamagedToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DAMAGEDTOLZDAYS$42, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "damaged_to_lz_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetDamagedToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DAMAGEDTOLZDAYS$42, 0);
            return target;
        }
    }
    
    /**
     * True if has "damaged_to_lz_days" element
     */
    public boolean isSetDamagedToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DAMAGEDTOLZDAYS$42) != 0;
        }
    }
    
    /**
     * Sets the "damaged_to_lz_days" element
     */
    public void setDamagedToLzDays(int damagedToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DAMAGEDTOLZDAYS$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DAMAGEDTOLZDAYS$42);
            }
            target.setIntValue(damagedToLzDays);
        }
    }
    
    /**
     * Sets (as xml) the "damaged_to_lz_days" element
     */
    public void xsetDamagedToLzDays(org.apache.xmlbeans.XmlInt damagedToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DAMAGEDTOLZDAYS$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DAMAGEDTOLZDAYS$42);
            }
            target.set(damagedToLzDays);
        }
    }
    
    /**
     * Unsets the "damaged_to_lz_days" element
     */
    public void unsetDamagedToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DAMAGEDTOLZDAYS$42, 0);
        }
    }
    
    /**
     * Gets the "default_loss_code" element
     */
    public int getDefaultLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTLOSSCODE$44, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "default_loss_code" element
     */
    public org.apache.xmlbeans.XmlInt xgetDefaultLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEFAULTLOSSCODE$44, 0);
            return target;
        }
    }
    
    /**
     * True if has "default_loss_code" element
     */
    public boolean isSetDefaultLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEFAULTLOSSCODE$44) != 0;
        }
    }
    
    /**
     * Sets the "default_loss_code" element
     */
    public void setDefaultLossCode(int defaultLossCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTLOSSCODE$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEFAULTLOSSCODE$44);
            }
            target.setIntValue(defaultLossCode);
        }
    }
    
    /**
     * Sets (as xml) the "default_loss_code" element
     */
    public void xsetDefaultLossCode(org.apache.xmlbeans.XmlInt defaultLossCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEFAULTLOSSCODE$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEFAULTLOSSCODE$44);
            }
            target.set(defaultLossCode);
        }
    }
    
    /**
     * Unsets the "default_loss_code" element
     */
    public void unsetDefaultLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEFAULTLOSSCODE$44, 0);
        }
    }
    
    /**
     * Gets the "default_station_code" element
     */
    public int getDefaultStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTSTATIONCODE$46, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "default_station_code" element
     */
    public org.apache.xmlbeans.XmlInt xgetDefaultStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEFAULTSTATIONCODE$46, 0);
            return target;
        }
    }
    
    /**
     * True if has "default_station_code" element
     */
    public boolean isSetDefaultStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEFAULTSTATIONCODE$46) != 0;
        }
    }
    
    /**
     * Sets the "default_station_code" element
     */
    public void setDefaultStationCode(int defaultStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTSTATIONCODE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEFAULTSTATIONCODE$46);
            }
            target.setIntValue(defaultStationCode);
        }
    }
    
    /**
     * Sets (as xml) the "default_station_code" element
     */
    public void xsetDefaultStationCode(org.apache.xmlbeans.XmlInt defaultStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEFAULTSTATIONCODE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEFAULTSTATIONCODE$46);
            }
            target.set(defaultStationCode);
        }
    }
    
    /**
     * Unsets the "default_station_code" element
     */
    public void unsetDefaultStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEFAULTSTATIONCODE$46, 0);
        }
    }
    
    /**
     * Gets the "email_customer" element
     */
    public boolean getEmailCustomer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILCUSTOMER$48, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_customer" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetEmailCustomer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EMAILCUSTOMER$48, 0);
            return target;
        }
    }
    
    /**
     * True if has "email_customer" element
     */
    public boolean isSetEmailCustomer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILCUSTOMER$48) != 0;
        }
    }
    
    /**
     * Sets the "email_customer" element
     */
    public void setEmailCustomer(boolean emailCustomer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILCUSTOMER$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILCUSTOMER$48);
            }
            target.setBooleanValue(emailCustomer);
        }
    }
    
    /**
     * Sets (as xml) the "email_customer" element
     */
    public void xsetEmailCustomer(org.apache.xmlbeans.XmlBoolean emailCustomer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EMAILCUSTOMER$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(EMAILCUSTOMER$48);
            }
            target.set(emailCustomer);
        }
    }
    
    /**
     * Unsets the "email_customer" element
     */
    public void unsetEmailCustomer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILCUSTOMER$48, 0);
        }
    }
    
    /**
     * Gets the "email_from" element
     */
    public java.lang.String getEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILFROM$50, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_from" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILFROM$50, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email_from" element
     */
    public boolean isNilEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILFROM$50, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email_from" element
     */
    public boolean isSetEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILFROM$50) != 0;
        }
    }
    
    /**
     * Sets the "email_from" element
     */
    public void setEmailFrom(java.lang.String emailFrom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILFROM$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILFROM$50);
            }
            target.setStringValue(emailFrom);
        }
    }
    
    /**
     * Sets (as xml) the "email_from" element
     */
    public void xsetEmailFrom(org.apache.xmlbeans.XmlString emailFrom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILFROM$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILFROM$50);
            }
            target.set(emailFrom);
        }
    }
    
    /**
     * Nils the "email_from" element
     */
    public void setNilEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILFROM$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILFROM$50);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email_from" element
     */
    public void unsetEmailFrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILFROM$50, 0);
        }
    }
    
    /**
     * Gets the "email_host" element
     */
    public java.lang.String getEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILHOST$52, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_host" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILHOST$52, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email_host" element
     */
    public boolean isNilEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILHOST$52, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email_host" element
     */
    public boolean isSetEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILHOST$52) != 0;
        }
    }
    
    /**
     * Sets the "email_host" element
     */
    public void setEmailHost(java.lang.String emailHost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILHOST$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILHOST$52);
            }
            target.setStringValue(emailHost);
        }
    }
    
    /**
     * Sets (as xml) the "email_host" element
     */
    public void xsetEmailHost(org.apache.xmlbeans.XmlString emailHost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILHOST$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILHOST$52);
            }
            target.set(emailHost);
        }
    }
    
    /**
     * Nils the "email_host" element
     */
    public void setNilEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILHOST$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILHOST$52);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email_host" element
     */
    public void unsetEmailHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILHOST$52, 0);
        }
    }
    
    /**
     * Gets the "email_port" element
     */
    public int getEmailPort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILPORT$54, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_port" element
     */
    public org.apache.xmlbeans.XmlInt xgetEmailPort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EMAILPORT$54, 0);
            return target;
        }
    }
    
    /**
     * True if has "email_port" element
     */
    public boolean isSetEmailPort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILPORT$54) != 0;
        }
    }
    
    /**
     * Sets the "email_port" element
     */
    public void setEmailPort(int emailPort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILPORT$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILPORT$54);
            }
            target.setIntValue(emailPort);
        }
    }
    
    /**
     * Sets (as xml) the "email_port" element
     */
    public void xsetEmailPort(org.apache.xmlbeans.XmlInt emailPort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EMAILPORT$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(EMAILPORT$54);
            }
            target.set(emailPort);
        }
    }
    
    /**
     * Unsets the "email_port" element
     */
    public void unsetEmailPort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILPORT$54, 0);
        }
    }
    
    /**
     * Gets the "email_to" element
     */
    public java.lang.String getEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILTO$56, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_to" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILTO$56, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email_to" element
     */
    public boolean isNilEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILTO$56, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email_to" element
     */
    public boolean isSetEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILTO$56) != 0;
        }
    }
    
    /**
     * Sets the "email_to" element
     */
    public void setEmailTo(java.lang.String emailTo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILTO$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILTO$56);
            }
            target.setStringValue(emailTo);
        }
    }
    
    /**
     * Sets (as xml) the "email_to" element
     */
    public void xsetEmailTo(org.apache.xmlbeans.XmlString emailTo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILTO$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILTO$56);
            }
            target.set(emailTo);
        }
    }
    
    /**
     * Nils the "email_to" element
     */
    public void setNilEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILTO$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILTO$56);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email_to" element
     */
    public void unsetEmailTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILTO$56, 0);
        }
    }
    
    /**
     * Gets the "lz_mode" element
     */
    public int getLzMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LZMODE$58, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "lz_mode" element
     */
    public org.apache.xmlbeans.XmlInt xgetLzMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LZMODE$58, 0);
            return target;
        }
    }
    
    /**
     * True if has "lz_mode" element
     */
    public boolean isSetLzMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LZMODE$58) != 0;
        }
    }
    
    /**
     * Sets the "lz_mode" element
     */
    public void setLzMode(int lzMode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LZMODE$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LZMODE$58);
            }
            target.setIntValue(lzMode);
        }
    }
    
    /**
     * Sets (as xml) the "lz_mode" element
     */
    public void xsetLzMode(org.apache.xmlbeans.XmlInt lzMode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LZMODE$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(LZMODE$58);
            }
            target.set(lzMode);
        }
    }
    
    /**
     * Unsets the "lz_mode" element
     */
    public void unsetLzMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LZMODE$58, 0);
        }
    }
    
    /**
     * Gets the "max_failed_logins" element
     */
    public int getMaxFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXFAILEDLOGINS$60, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "max_failed_logins" element
     */
    public org.apache.xmlbeans.XmlInt xgetMaxFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXFAILEDLOGINS$60, 0);
            return target;
        }
    }
    
    /**
     * True if has "max_failed_logins" element
     */
    public boolean isSetMaxFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAXFAILEDLOGINS$60) != 0;
        }
    }
    
    /**
     * Sets the "max_failed_logins" element
     */
    public void setMaxFailedLogins(int maxFailedLogins)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXFAILEDLOGINS$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAXFAILEDLOGINS$60);
            }
            target.setIntValue(maxFailedLogins);
        }
    }
    
    /**
     * Sets (as xml) the "max_failed_logins" element
     */
    public void xsetMaxFailedLogins(org.apache.xmlbeans.XmlInt maxFailedLogins)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXFAILEDLOGINS$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MAXFAILEDLOGINS$60);
            }
            target.set(maxFailedLogins);
        }
    }
    
    /**
     * Unsets the "max_failed_logins" element
     */
    public void unsetMaxFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAXFAILEDLOGINS$60, 0);
        }
    }
    
    /**
     * Gets the "max_image_file_size" element
     */
    public int getMaxImageFileSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXIMAGEFILESIZE$62, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "max_image_file_size" element
     */
    public org.apache.xmlbeans.XmlInt xgetMaxImageFileSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXIMAGEFILESIZE$62, 0);
            return target;
        }
    }
    
    /**
     * True if has "max_image_file_size" element
     */
    public boolean isSetMaxImageFileSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAXIMAGEFILESIZE$62) != 0;
        }
    }
    
    /**
     * Sets the "max_image_file_size" element
     */
    public void setMaxImageFileSize(int maxImageFileSize)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXIMAGEFILESIZE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAXIMAGEFILESIZE$62);
            }
            target.setIntValue(maxImageFileSize);
        }
    }
    
    /**
     * Sets (as xml) the "max_image_file_size" element
     */
    public void xsetMaxImageFileSize(org.apache.xmlbeans.XmlInt maxImageFileSize)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXIMAGEFILESIZE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MAXIMAGEFILESIZE$62);
            }
            target.set(maxImageFileSize);
        }
    }
    
    /**
     * Unsets the "max_image_file_size" element
     */
    public void unsetMaxImageFileSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAXIMAGEFILESIZE$62, 0);
        }
    }
    
    /**
     * Gets the "mbr_to_lz_days" element
     */
    public int getMbrToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MBRTOLZDAYS$64, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "mbr_to_lz_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetMbrToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MBRTOLZDAYS$64, 0);
            return target;
        }
    }
    
    /**
     * True if has "mbr_to_lz_days" element
     */
    public boolean isSetMbrToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MBRTOLZDAYS$64) != 0;
        }
    }
    
    /**
     * Sets the "mbr_to_lz_days" element
     */
    public void setMbrToLzDays(int mbrToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MBRTOLZDAYS$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MBRTOLZDAYS$64);
            }
            target.setIntValue(mbrToLzDays);
        }
    }
    
    /**
     * Sets (as xml) the "mbr_to_lz_days" element
     */
    public void xsetMbrToLzDays(org.apache.xmlbeans.XmlInt mbrToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MBRTOLZDAYS$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MBRTOLZDAYS$64);
            }
            target.set(mbrToLzDays);
        }
    }
    
    /**
     * Unsets the "mbr_to_lz_days" element
     */
    public void unsetMbrToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MBRTOLZDAYS$64, 0);
        }
    }
    
    /**
     * Gets the "mbr_to_wt_days" element
     */
    public int getMbrToWtDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MBRTOWTDAYS$66, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "mbr_to_wt_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetMbrToWtDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MBRTOWTDAYS$66, 0);
            return target;
        }
    }
    
    /**
     * True if has "mbr_to_wt_days" element
     */
    public boolean isSetMbrToWtDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MBRTOWTDAYS$66) != 0;
        }
    }
    
    /**
     * Sets the "mbr_to_wt_days" element
     */
    public void setMbrToWtDays(int mbrToWtDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MBRTOWTDAYS$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MBRTOWTDAYS$66);
            }
            target.setIntValue(mbrToWtDays);
        }
    }
    
    /**
     * Sets (as xml) the "mbr_to_wt_days" element
     */
    public void xsetMbrToWtDays(org.apache.xmlbeans.XmlInt mbrToWtDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MBRTOWTDAYS$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MBRTOWTDAYS$66);
            }
            target.set(mbrToWtDays);
        }
    }
    
    /**
     * Unsets the "mbr_to_wt_days" element
     */
    public void unsetMbrToWtDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MBRTOWTDAYS$66, 0);
        }
    }
    
    /**
     * Gets the "min_interim_approval_cc_refund" element
     */
    public double getMinInterimApprovalCcRefund()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALCCREFUND$68, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_interim_approval_cc_refund" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinInterimApprovalCcRefund()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALCCREFUND$68, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_interim_approval_cc_refund" element
     */
    public boolean isSetMinInterimApprovalCcRefund()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MININTERIMAPPROVALCCREFUND$68) != 0;
        }
    }
    
    /**
     * Sets the "min_interim_approval_cc_refund" element
     */
    public void setMinInterimApprovalCcRefund(double minInterimApprovalCcRefund)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALCCREFUND$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MININTERIMAPPROVALCCREFUND$68);
            }
            target.setDoubleValue(minInterimApprovalCcRefund);
        }
    }
    
    /**
     * Sets (as xml) the "min_interim_approval_cc_refund" element
     */
    public void xsetMinInterimApprovalCcRefund(org.apache.xmlbeans.XmlDouble minInterimApprovalCcRefund)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALCCREFUND$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MININTERIMAPPROVALCCREFUND$68);
            }
            target.set(minInterimApprovalCcRefund);
        }
    }
    
    /**
     * Unsets the "min_interim_approval_cc_refund" element
     */
    public void unsetMinInterimApprovalCcRefund()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MININTERIMAPPROVALCCREFUND$68, 0);
        }
    }
    
    /**
     * Gets the "min_interim_approval_check" element
     */
    public double getMinInterimApprovalCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALCHECK$70, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_interim_approval_check" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinInterimApprovalCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALCHECK$70, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_interim_approval_check" element
     */
    public boolean isSetMinInterimApprovalCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MININTERIMAPPROVALCHECK$70) != 0;
        }
    }
    
    /**
     * Sets the "min_interim_approval_check" element
     */
    public void setMinInterimApprovalCheck(double minInterimApprovalCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALCHECK$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MININTERIMAPPROVALCHECK$70);
            }
            target.setDoubleValue(minInterimApprovalCheck);
        }
    }
    
    /**
     * Sets (as xml) the "min_interim_approval_check" element
     */
    public void xsetMinInterimApprovalCheck(org.apache.xmlbeans.XmlDouble minInterimApprovalCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALCHECK$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MININTERIMAPPROVALCHECK$70);
            }
            target.set(minInterimApprovalCheck);
        }
    }
    
    /**
     * Unsets the "min_interim_approval_check" element
     */
    public void unsetMinInterimApprovalCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MININTERIMAPPROVALCHECK$70, 0);
        }
    }
    
    /**
     * Gets the "min_interim_approval_incidental" element
     */
    public double getMinInterimApprovalIncidental()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALINCIDENTAL$72, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_interim_approval_incidental" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinInterimApprovalIncidental()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALINCIDENTAL$72, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_interim_approval_incidental" element
     */
    public boolean isSetMinInterimApprovalIncidental()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MININTERIMAPPROVALINCIDENTAL$72) != 0;
        }
    }
    
    /**
     * Sets the "min_interim_approval_incidental" element
     */
    public void setMinInterimApprovalIncidental(double minInterimApprovalIncidental)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALINCIDENTAL$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MININTERIMAPPROVALINCIDENTAL$72);
            }
            target.setDoubleValue(minInterimApprovalIncidental);
        }
    }
    
    /**
     * Sets (as xml) the "min_interim_approval_incidental" element
     */
    public void xsetMinInterimApprovalIncidental(org.apache.xmlbeans.XmlDouble minInterimApprovalIncidental)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALINCIDENTAL$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MININTERIMAPPROVALINCIDENTAL$72);
            }
            target.set(minInterimApprovalIncidental);
        }
    }
    
    /**
     * Unsets the "min_interim_approval_incidental" element
     */
    public void unsetMinInterimApprovalIncidental()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MININTERIMAPPROVALINCIDENTAL$72, 0);
        }
    }
    
    /**
     * Gets the "min_interim_approval_miles" element
     */
    public double getMinInterimApprovalMiles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALMILES$74, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_interim_approval_miles" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinInterimApprovalMiles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALMILES$74, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_interim_approval_miles" element
     */
    public boolean isSetMinInterimApprovalMiles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MININTERIMAPPROVALMILES$74) != 0;
        }
    }
    
    /**
     * Sets the "min_interim_approval_miles" element
     */
    public void setMinInterimApprovalMiles(double minInterimApprovalMiles)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALMILES$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MININTERIMAPPROVALMILES$74);
            }
            target.setDoubleValue(minInterimApprovalMiles);
        }
    }
    
    /**
     * Sets (as xml) the "min_interim_approval_miles" element
     */
    public void xsetMinInterimApprovalMiles(org.apache.xmlbeans.XmlDouble minInterimApprovalMiles)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALMILES$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MININTERIMAPPROVALMILES$74);
            }
            target.set(minInterimApprovalMiles);
        }
    }
    
    /**
     * Unsets the "min_interim_approval_miles" element
     */
    public void unsetMinInterimApprovalMiles()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MININTERIMAPPROVALMILES$74, 0);
        }
    }
    
    /**
     * Gets the "min_interim_approval_voucher" element
     */
    public double getMinInterimApprovalVoucher()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALVOUCHER$76, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_interim_approval_voucher" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinInterimApprovalVoucher()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALVOUCHER$76, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_interim_approval_voucher" element
     */
    public boolean isSetMinInterimApprovalVoucher()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MININTERIMAPPROVALVOUCHER$76) != 0;
        }
    }
    
    /**
     * Sets the "min_interim_approval_voucher" element
     */
    public void setMinInterimApprovalVoucher(double minInterimApprovalVoucher)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MININTERIMAPPROVALVOUCHER$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MININTERIMAPPROVALVOUCHER$76);
            }
            target.setDoubleValue(minInterimApprovalVoucher);
        }
    }
    
    /**
     * Sets (as xml) the "min_interim_approval_voucher" element
     */
    public void xsetMinInterimApprovalVoucher(org.apache.xmlbeans.XmlDouble minInterimApprovalVoucher)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MININTERIMAPPROVALVOUCHER$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MININTERIMAPPROVALVOUCHER$76);
            }
            target.set(minInterimApprovalVoucher);
        }
    }
    
    /**
     * Unsets the "min_interim_approval_voucher" element
     */
    public void unsetMinInterimApprovalVoucher()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MININTERIMAPPROVALVOUCHER$76, 0);
        }
    }
    
    /**
     * Gets the "min_match_percent" element
     */
    public double getMinMatchPercent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MINMATCHPERCENT$78, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "min_match_percent" element
     */
    public org.apache.xmlbeans.XmlDouble xgetMinMatchPercent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MINMATCHPERCENT$78, 0);
            return target;
        }
    }
    
    /**
     * True if has "min_match_percent" element
     */
    public boolean isSetMinMatchPercent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MINMATCHPERCENT$78) != 0;
        }
    }
    
    /**
     * Sets the "min_match_percent" element
     */
    public void setMinMatchPercent(double minMatchPercent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MINMATCHPERCENT$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MINMATCHPERCENT$78);
            }
            target.setDoubleValue(minMatchPercent);
        }
    }
    
    /**
     * Sets (as xml) the "min_match_percent" element
     */
    public void xsetMinMatchPercent(org.apache.xmlbeans.XmlDouble minMatchPercent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(MINMATCHPERCENT$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(MINMATCHPERCENT$78);
            }
            target.set(minMatchPercent);
        }
    }
    
    /**
     * Unsets the "min_match_percent" element
     */
    public void unsetMinMatchPercent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MINMATCHPERCENT$78, 0);
        }
    }
    
    /**
     * Gets the "miss_to_lz_days" element
     */
    public int getMissToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MISSTOLZDAYS$80, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "miss_to_lz_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetMissToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MISSTOLZDAYS$80, 0);
            return target;
        }
    }
    
    /**
     * True if has "miss_to_lz_days" element
     */
    public boolean isSetMissToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MISSTOLZDAYS$80) != 0;
        }
    }
    
    /**
     * Sets the "miss_to_lz_days" element
     */
    public void setMissToLzDays(int missToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MISSTOLZDAYS$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MISSTOLZDAYS$80);
            }
            target.setIntValue(missToLzDays);
        }
    }
    
    /**
     * Sets (as xml) the "miss_to_lz_days" element
     */
    public void xsetMissToLzDays(org.apache.xmlbeans.XmlInt missToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MISSTOLZDAYS$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MISSTOLZDAYS$80);
            }
            target.set(missToLzDays);
        }
    }
    
    /**
     * Unsets the "miss_to_lz_days" element
     */
    public void unsetMissToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MISSTOLZDAYS$80, 0);
        }
    }
    
    /**
     * Gets the "oal_inc_hours" element
     */
    public int getOalIncHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OALINCHOURS$82, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "oal_inc_hours" element
     */
    public org.apache.xmlbeans.XmlInt xgetOalIncHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OALINCHOURS$82, 0);
            return target;
        }
    }
    
    /**
     * True if has "oal_inc_hours" element
     */
    public boolean isSetOalIncHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OALINCHOURS$82) != 0;
        }
    }
    
    /**
     * Sets the "oal_inc_hours" element
     */
    public void setOalIncHours(int oalIncHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OALINCHOURS$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OALINCHOURS$82);
            }
            target.setIntValue(oalIncHours);
        }
    }
    
    /**
     * Sets (as xml) the "oal_inc_hours" element
     */
    public void xsetOalIncHours(org.apache.xmlbeans.XmlInt oalIncHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OALINCHOURS$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OALINCHOURS$82);
            }
            target.set(oalIncHours);
        }
    }
    
    /**
     * Unsets the "oal_inc_hours" element
     */
    public void unsetOalIncHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OALINCHOURS$82, 0);
        }
    }
    
    /**
     * Gets the "oal_ohd_hours" element
     */
    public int getOalOhdHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OALOHDHOURS$84, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "oal_ohd_hours" element
     */
    public org.apache.xmlbeans.XmlInt xgetOalOhdHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OALOHDHOURS$84, 0);
            return target;
        }
    }
    
    /**
     * True if has "oal_ohd_hours" element
     */
    public boolean isSetOalOhdHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OALOHDHOURS$84) != 0;
        }
    }
    
    /**
     * Sets the "oal_ohd_hours" element
     */
    public void setOalOhdHours(int oalOhdHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OALOHDHOURS$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OALOHDHOURS$84);
            }
            target.setIntValue(oalOhdHours);
        }
    }
    
    /**
     * Sets (as xml) the "oal_ohd_hours" element
     */
    public void xsetOalOhdHours(org.apache.xmlbeans.XmlInt oalOhdHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OALOHDHOURS$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OALOHDHOURS$84);
            }
            target.set(oalOhdHours);
        }
    }
    
    /**
     * Unsets the "oal_ohd_hours" element
     */
    public void unsetOalOhdHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OALOHDHOURS$84, 0);
        }
    }
    
    /**
     * Gets the "ohd_lz" element
     */
    public int getOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDLZ$86, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ohd_lz" element
     */
    public org.apache.xmlbeans.XmlInt xgetOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDLZ$86, 0);
            return target;
        }
    }
    
    /**
     * True if has "ohd_lz" element
     */
    public boolean isSetOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDLZ$86) != 0;
        }
    }
    
    /**
     * Sets the "ohd_lz" element
     */
    public void setOhdLz(int ohdLz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDLZ$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDLZ$86);
            }
            target.setIntValue(ohdLz);
        }
    }
    
    /**
     * Sets (as xml) the "ohd_lz" element
     */
    public void xsetOhdLz(org.apache.xmlbeans.XmlInt ohdLz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDLZ$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OHDLZ$86);
            }
            target.set(ohdLz);
        }
    }
    
    /**
     * Unsets the "ohd_lz" element
     */
    public void unsetOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDLZ$86, 0);
        }
    }
    
    /**
     * Gets the "ohd_to_lz_days" element
     */
    public int getOhdToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDTOLZDAYS$88, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ohd_to_lz_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetOhdToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDTOLZDAYS$88, 0);
            return target;
        }
    }
    
    /**
     * True if has "ohd_to_lz_days" element
     */
    public boolean isSetOhdToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDTOLZDAYS$88) != 0;
        }
    }
    
    /**
     * Sets the "ohd_to_lz_days" element
     */
    public void setOhdToLzDays(int ohdToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDTOLZDAYS$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDTOLZDAYS$88);
            }
            target.setIntValue(ohdToLzDays);
        }
    }
    
    /**
     * Sets (as xml) the "ohd_to_lz_days" element
     */
    public void xsetOhdToLzDays(org.apache.xmlbeans.XmlInt ohdToLzDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDTOLZDAYS$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OHDTOLZDAYS$88);
            }
            target.set(ohdToLzDays);
        }
    }
    
    /**
     * Unsets the "ohd_to_lz_days" element
     */
    public void unsetOhdToLzDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDTOLZDAYS$88, 0);
        }
    }
    
    /**
     * Gets the "ohd_to_wt_hours" element
     */
    public int getOhdToWtHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDTOWTHOURS$90, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ohd_to_wt_hours" element
     */
    public org.apache.xmlbeans.XmlInt xgetOhdToWtHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDTOWTHOURS$90, 0);
            return target;
        }
    }
    
    /**
     * True if has "ohd_to_wt_hours" element
     */
    public boolean isSetOhdToWtHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDTOWTHOURS$90) != 0;
        }
    }
    
    /**
     * Sets the "ohd_to_wt_hours" element
     */
    public void setOhdToWtHours(int ohdToWtHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDTOWTHOURS$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDTOWTHOURS$90);
            }
            target.setIntValue(ohdToWtHours);
        }
    }
    
    /**
     * Sets (as xml) the "ohd_to_wt_hours" element
     */
    public void xsetOhdToWtHours(org.apache.xmlbeans.XmlInt ohdToWtHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OHDTOWTHOURS$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OHDTOWTHOURS$90);
            }
            target.set(ohdToWtHours);
        }
    }
    
    /**
     * Unsets the "ohd_to_wt_hours" element
     */
    public void unsetOhdToWtHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDTOWTHOURS$90, 0);
        }
    }
    
    /**
     * Gets the "pass_expire_days" element
     */
    public int getPassExpireDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSEXPIREDAYS$92, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "pass_expire_days" element
     */
    public org.apache.xmlbeans.XmlInt xgetPassExpireDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PASSEXPIREDAYS$92, 0);
            return target;
        }
    }
    
    /**
     * True if has "pass_expire_days" element
     */
    public boolean isSetPassExpireDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSEXPIREDAYS$92) != 0;
        }
    }
    
    /**
     * Sets the "pass_expire_days" element
     */
    public void setPassExpireDays(int passExpireDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSEXPIREDAYS$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSEXPIREDAYS$92);
            }
            target.setIntValue(passExpireDays);
        }
    }
    
    /**
     * Sets (as xml) the "pass_expire_days" element
     */
    public void xsetPassExpireDays(org.apache.xmlbeans.XmlInt passExpireDays)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PASSEXPIREDAYS$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PASSEXPIREDAYS$92);
            }
            target.set(passExpireDays);
        }
    }
    
    /**
     * Unsets the "pass_expire_days" element
     */
    public void unsetPassExpireDays()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSEXPIREDAYS$92, 0);
        }
    }
    
    /**
     * Gets the "report_method" element
     */
    public int getReportMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTMETHOD$94, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "report_method" element
     */
    public org.apache.xmlbeans.XmlInt xgetReportMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REPORTMETHOD$94, 0);
            return target;
        }
    }
    
    /**
     * True if has "report_method" element
     */
    public boolean isSetReportMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPORTMETHOD$94) != 0;
        }
    }
    
    /**
     * Sets the "report_method" element
     */
    public void setReportMethod(int reportMethod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTMETHOD$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPORTMETHOD$94);
            }
            target.setIntValue(reportMethod);
        }
    }
    
    /**
     * Sets (as xml) the "report_method" element
     */
    public void xsetReportMethod(org.apache.xmlbeans.XmlInt reportMethod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REPORTMETHOD$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(REPORTMETHOD$94);
            }
            target.set(reportMethod);
        }
    }
    
    /**
     * Unsets the "report_method" element
     */
    public void unsetReportMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPORTMETHOD$94, 0);
        }
    }
    
    /**
     * Gets the "retrieve_actionfile_interval" element
     */
    public int getRetrieveActionfileInterval()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETRIEVEACTIONFILEINTERVAL$96, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "retrieve_actionfile_interval" element
     */
    public org.apache.xmlbeans.XmlInt xgetRetrieveActionfileInterval()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETRIEVEACTIONFILEINTERVAL$96, 0);
            return target;
        }
    }
    
    /**
     * True if has "retrieve_actionfile_interval" element
     */
    public boolean isSetRetrieveActionfileInterval()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RETRIEVEACTIONFILEINTERVAL$96) != 0;
        }
    }
    
    /**
     * Sets the "retrieve_actionfile_interval" element
     */
    public void setRetrieveActionfileInterval(int retrieveActionfileInterval)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETRIEVEACTIONFILEINTERVAL$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RETRIEVEACTIONFILEINTERVAL$96);
            }
            target.setIntValue(retrieveActionfileInterval);
        }
    }
    
    /**
     * Sets (as xml) the "retrieve_actionfile_interval" element
     */
    public void xsetRetrieveActionfileInterval(org.apache.xmlbeans.XmlInt retrieveActionfileInterval)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETRIEVEACTIONFILEINTERVAL$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(RETRIEVEACTIONFILEINTERVAL$96);
            }
            target.set(retrieveActionfileInterval);
        }
    }
    
    /**
     * Unsets the "retrieve_actionfile_interval" element
     */
    public void unsetRetrieveActionfileInterval()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RETRIEVEACTIONFILEINTERVAL$96, 0);
        }
    }
    
    /**
     * Gets the "scannerDefaultBack" element
     */
    public int getScannerDefaultBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCANNERDEFAULTBACK$98, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "scannerDefaultBack" element
     */
    public org.apache.xmlbeans.XmlInt xgetScannerDefaultBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SCANNERDEFAULTBACK$98, 0);
            return target;
        }
    }
    
    /**
     * True if has "scannerDefaultBack" element
     */
    public boolean isSetScannerDefaultBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCANNERDEFAULTBACK$98) != 0;
        }
    }
    
    /**
     * Sets the "scannerDefaultBack" element
     */
    public void setScannerDefaultBack(int scannerDefaultBack)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCANNERDEFAULTBACK$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCANNERDEFAULTBACK$98);
            }
            target.setIntValue(scannerDefaultBack);
        }
    }
    
    /**
     * Sets (as xml) the "scannerDefaultBack" element
     */
    public void xsetScannerDefaultBack(org.apache.xmlbeans.XmlInt scannerDefaultBack)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SCANNERDEFAULTBACK$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SCANNERDEFAULTBACK$98);
            }
            target.set(scannerDefaultBack);
        }
    }
    
    /**
     * Unsets the "scannerDefaultBack" element
     */
    public void unsetScannerDefaultBack()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCANNERDEFAULTBACK$98, 0);
        }
    }
    
    /**
     * Gets the "scannerDefaultForward" element
     */
    public int getScannerDefaultForward()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCANNERDEFAULTFORWARD$100, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "scannerDefaultForward" element
     */
    public org.apache.xmlbeans.XmlInt xgetScannerDefaultForward()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SCANNERDEFAULTFORWARD$100, 0);
            return target;
        }
    }
    
    /**
     * True if has "scannerDefaultForward" element
     */
    public boolean isSetScannerDefaultForward()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCANNERDEFAULTFORWARD$100) != 0;
        }
    }
    
    /**
     * Sets the "scannerDefaultForward" element
     */
    public void setScannerDefaultForward(int scannerDefaultForward)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCANNERDEFAULTFORWARD$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCANNERDEFAULTFORWARD$100);
            }
            target.setIntValue(scannerDefaultForward);
        }
    }
    
    /**
     * Sets (as xml) the "scannerDefaultForward" element
     */
    public void xsetScannerDefaultForward(org.apache.xmlbeans.XmlInt scannerDefaultForward)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SCANNERDEFAULTFORWARD$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SCANNERDEFAULTFORWARD$100);
            }
            target.set(scannerDefaultForward);
        }
    }
    
    /**
     * Unsets the "scannerDefaultForward" element
     */
    public void unsetScannerDefaultForward()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCANNERDEFAULTFORWARD$100, 0);
        }
    }
    
    /**
     * Gets the "seconds_wait" element
     */
    public int getSecondsWait()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECONDSWAIT$102, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "seconds_wait" element
     */
    public org.apache.xmlbeans.XmlInt xgetSecondsWait()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SECONDSWAIT$102, 0);
            return target;
        }
    }
    
    /**
     * True if has "seconds_wait" element
     */
    public boolean isSetSecondsWait()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SECONDSWAIT$102) != 0;
        }
    }
    
    /**
     * Sets the "seconds_wait" element
     */
    public void setSecondsWait(int secondsWait)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECONDSWAIT$102, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SECONDSWAIT$102);
            }
            target.setIntValue(secondsWait);
        }
    }
    
    /**
     * Sets (as xml) the "seconds_wait" element
     */
    public void xsetSecondsWait(org.apache.xmlbeans.XmlInt secondsWait)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SECONDSWAIT$102, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SECONDSWAIT$102);
            }
            target.set(secondsWait);
        }
    }
    
    /**
     * Unsets the "seconds_wait" element
     */
    public void unsetSecondsWait()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SECONDSWAIT$102, 0);
        }
    }
    
    /**
     * Gets the "secure_password" element
     */
    public int getSecurePassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECUREPASSWORD$104, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "secure_password" element
     */
    public org.apache.xmlbeans.XmlInt xgetSecurePassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SECUREPASSWORD$104, 0);
            return target;
        }
    }
    
    /**
     * True if has "secure_password" element
     */
    public boolean isSetSecurePassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SECUREPASSWORD$104) != 0;
        }
    }
    
    /**
     * Sets the "secure_password" element
     */
    public void setSecurePassword(int securePassword)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECUREPASSWORD$104, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SECUREPASSWORD$104);
            }
            target.setIntValue(securePassword);
        }
    }
    
    /**
     * Sets (as xml) the "secure_password" element
     */
    public void xsetSecurePassword(org.apache.xmlbeans.XmlInt securePassword)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SECUREPASSWORD$104, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SECUREPASSWORD$104);
            }
            target.set(securePassword);
        }
    }
    
    /**
     * Unsets the "secure_password" element
     */
    public void unsetSecurePassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SECUREPASSWORD$104, 0);
        }
    }
    
    /**
     * Gets the "total_threads" element
     */
    public int getTotalThreads()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALTHREADS$106, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "total_threads" element
     */
    public org.apache.xmlbeans.XmlInt xgetTotalThreads()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TOTALTHREADS$106, 0);
            return target;
        }
    }
    
    /**
     * True if has "total_threads" element
     */
    public boolean isSetTotalThreads()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TOTALTHREADS$106) != 0;
        }
    }
    
    /**
     * Sets the "total_threads" element
     */
    public void setTotalThreads(int totalThreads)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALTHREADS$106, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TOTALTHREADS$106);
            }
            target.setIntValue(totalThreads);
        }
    }
    
    /**
     * Sets (as xml) the "total_threads" element
     */
    public void xsetTotalThreads(org.apache.xmlbeans.XmlInt totalThreads)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TOTALTHREADS$106, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TOTALTHREADS$106);
            }
            target.set(totalThreads);
        }
    }
    
    /**
     * Unsets the "total_threads" element
     */
    public void unsetTotalThreads()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TOTALTHREADS$106, 0);
        }
    }
    
    /**
     * Gets the "webs_enabled" element
     */
    public int getWebsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBSENABLED$108, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "webs_enabled" element
     */
    public org.apache.xmlbeans.XmlInt xgetWebsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WEBSENABLED$108, 0);
            return target;
        }
    }
    
    /**
     * True if has "webs_enabled" element
     */
    public boolean isSetWebsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WEBSENABLED$108) != 0;
        }
    }
    
    /**
     * Sets the "webs_enabled" element
     */
    public void setWebsEnabled(int websEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBSENABLED$108, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WEBSENABLED$108);
            }
            target.setIntValue(websEnabled);
        }
    }
    
    /**
     * Sets (as xml) the "webs_enabled" element
     */
    public void xsetWebsEnabled(org.apache.xmlbeans.XmlInt websEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WEBSENABLED$108, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(WEBSENABLED$108);
            }
            target.set(websEnabled);
        }
    }
    
    /**
     * Unsets the "webs_enabled" element
     */
    public void unsetWebsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WEBSENABLED$108, 0);
        }
    }
    
    /**
     * Gets the "wt_airlinecode" element
     */
    public java.lang.String getWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTAIRLINECODE$110, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_airlinecode" element
     */
    public org.apache.xmlbeans.XmlString xgetWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTAIRLINECODE$110, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "wt_airlinecode" element
     */
    public boolean isNilWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTAIRLINECODE$110, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "wt_airlinecode" element
     */
    public boolean isSetWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTAIRLINECODE$110) != 0;
        }
    }
    
    /**
     * Sets the "wt_airlinecode" element
     */
    public void setWtAirlinecode(java.lang.String wtAirlinecode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTAIRLINECODE$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTAIRLINECODE$110);
            }
            target.setStringValue(wtAirlinecode);
        }
    }
    
    /**
     * Sets (as xml) the "wt_airlinecode" element
     */
    public void xsetWtAirlinecode(org.apache.xmlbeans.XmlString wtAirlinecode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTAIRLINECODE$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTAIRLINECODE$110);
            }
            target.set(wtAirlinecode);
        }
    }
    
    /**
     * Nils the "wt_airlinecode" element
     */
    public void setNilWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTAIRLINECODE$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTAIRLINECODE$110);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "wt_airlinecode" element
     */
    public void unsetWtAirlinecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTAIRLINECODE$110, 0);
        }
    }
    
    /**
     * Gets the "wt_enabled" element
     */
    public int getWtEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTENABLED$112, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_enabled" element
     */
    public org.apache.xmlbeans.XmlInt xgetWtEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WTENABLED$112, 0);
            return target;
        }
    }
    
    /**
     * True if has "wt_enabled" element
     */
    public boolean isSetWtEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTENABLED$112) != 0;
        }
    }
    
    /**
     * Sets the "wt_enabled" element
     */
    public void setWtEnabled(int wtEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTENABLED$112, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTENABLED$112);
            }
            target.setIntValue(wtEnabled);
        }
    }
    
    /**
     * Sets (as xml) the "wt_enabled" element
     */
    public void xsetWtEnabled(org.apache.xmlbeans.XmlInt wtEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WTENABLED$112, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(WTENABLED$112);
            }
            target.set(wtEnabled);
        }
    }
    
    /**
     * Unsets the "wt_enabled" element
     */
    public void unsetWtEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTENABLED$112, 0);
        }
    }
    
    /**
     * Gets the "wt_pass" element
     */
    public java.lang.String getWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTPASS$114, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_pass" element
     */
    public org.apache.xmlbeans.XmlString xgetWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTPASS$114, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "wt_pass" element
     */
    public boolean isNilWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTPASS$114, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "wt_pass" element
     */
    public boolean isSetWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTPASS$114) != 0;
        }
    }
    
    /**
     * Sets the "wt_pass" element
     */
    public void setWtPass(java.lang.String wtPass)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTPASS$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTPASS$114);
            }
            target.setStringValue(wtPass);
        }
    }
    
    /**
     * Sets (as xml) the "wt_pass" element
     */
    public void xsetWtPass(org.apache.xmlbeans.XmlString wtPass)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTPASS$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTPASS$114);
            }
            target.set(wtPass);
        }
    }
    
    /**
     * Nils the "wt_pass" element
     */
    public void setNilWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTPASS$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTPASS$114);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "wt_pass" element
     */
    public void unsetWtPass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTPASS$114, 0);
        }
    }
    
    /**
     * Gets the "wt_url" element
     */
    public java.lang.String getWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTURL$116, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_url" element
     */
    public org.apache.xmlbeans.XmlString xgetWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTURL$116, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "wt_url" element
     */
    public boolean isNilWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTURL$116, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "wt_url" element
     */
    public boolean isSetWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTURL$116) != 0;
        }
    }
    
    /**
     * Sets the "wt_url" element
     */
    public void setWtUrl(java.lang.String wtUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTURL$116, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTURL$116);
            }
            target.setStringValue(wtUrl);
        }
    }
    
    /**
     * Sets (as xml) the "wt_url" element
     */
    public void xsetWtUrl(org.apache.xmlbeans.XmlString wtUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTURL$116, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTURL$116);
            }
            target.set(wtUrl);
        }
    }
    
    /**
     * Nils the "wt_url" element
     */
    public void setNilWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTURL$116, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTURL$116);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "wt_url" element
     */
    public void unsetWtUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTURL$116, 0);
        }
    }
    
    /**
     * Gets the "wt_user" element
     */
    public java.lang.String getWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTUSER$118, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_user" element
     */
    public org.apache.xmlbeans.XmlString xgetWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTUSER$118, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "wt_user" element
     */
    public boolean isNilWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTUSER$118, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "wt_user" element
     */
    public boolean isSetWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTUSER$118) != 0;
        }
    }
    
    /**
     * Sets the "wt_user" element
     */
    public void setWtUser(java.lang.String wtUser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTUSER$118, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTUSER$118);
            }
            target.setStringValue(wtUser);
        }
    }
    
    /**
     * Sets (as xml) the "wt_user" element
     */
    public void xsetWtUser(org.apache.xmlbeans.XmlString wtUser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTUSER$118, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTUSER$118);
            }
            target.set(wtUser);
        }
    }
    
    /**
     * Nils the "wt_user" element
     */
    public void setNilWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTUSER$118, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTUSER$118);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "wt_user" element
     */
    public void unsetWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTUSER$118, 0);
        }
    }
    
    /**
     * Gets the "wt_write_enabled" element
     */
    public int getWtWriteEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTWRITEENABLED$120, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_write_enabled" element
     */
    public org.apache.xmlbeans.XmlInt xgetWtWriteEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WTWRITEENABLED$120, 0);
            return target;
        }
    }
    
    /**
     * True if has "wt_write_enabled" element
     */
    public boolean isSetWtWriteEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTWRITEENABLED$120) != 0;
        }
    }
    
    /**
     * Sets the "wt_write_enabled" element
     */
    public void setWtWriteEnabled(int wtWriteEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTWRITEENABLED$120, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTWRITEENABLED$120);
            }
            target.setIntValue(wtWriteEnabled);
        }
    }
    
    /**
     * Sets (as xml) the "wt_write_enabled" element
     */
    public void xsetWtWriteEnabled(org.apache.xmlbeans.XmlInt wtWriteEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WTWRITEENABLED$120, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(WTWRITEENABLED$120);
            }
            target.set(wtWriteEnabled);
        }
    }
    
    /**
     * Unsets the "wt_write_enabled" element
     */
    public void unsetWtWriteEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTWRITEENABLED$120, 0);
        }
    }
}
