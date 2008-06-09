/*
 * XML Type:  WS_Incident
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_Incident(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident {
    private static final javax.xml.namespace.QName AGENT$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "agent");
    private static final javax.xml.namespace.QName AGENTASSIGNED$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "agentassigned");
    private static final javax.xml.namespace.QName ARTICLES$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "articles");
    private static final javax.xml.namespace.QName CHECKEDLOCATION$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "checkedlocation");
    private static final javax.xml.namespace.QName CLAIMCHECKS$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "claimchecks");
    private static final javax.xml.namespace.QName CLOSEDATE$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "closedate");
    private static final javax.xml.namespace.QName COMPANYCODEID$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "companycode_ID");
    private static final javax.xml.namespace.QName COURTESYREPORT$14 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "courtesyreport");
    private static final javax.xml.namespace.QName CREATEDATE$16 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "createdate");
    private static final javax.xml.namespace.QName CREATETIME$18 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "createtime");
    private static final javax.xml.namespace.QName CUSTOMCLEARED$20 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "customcleared");
    private static final javax.xml.namespace.QName ERRORCODE$22 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "errorcode");
    private static final javax.xml.namespace.QName FAULTAIRLINE$24 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "faultairline");
    private static final javax.xml.namespace.QName FAULTSTATION$26 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "faultstation");
    private static final javax.xml.namespace.QName INCIDENTID$28 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "incident_ID");
    private static final javax.xml.namespace.QName ITEMS$30 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "items");
    private static final javax.xml.namespace.QName ITEMTYPE$32 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "itemtype");
    private static final javax.xml.namespace.QName ITINERARIES$34 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "itineraries");
    private static final javax.xml.namespace.QName LOSSCODE$36 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "loss_code");
    private static final javax.xml.namespace.QName NONREVENUE$38 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "nonrevenue");
    private static final javax.xml.namespace.QName NUMBAGCHECKED$40 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "numbagchecked");
    private static final javax.xml.namespace.QName NUMBAGRECEIVED$42 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "numbagreceived");
    private static final javax.xml.namespace.QName NUMPASSENGERS$44 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "numpassengers");
    private static final javax.xml.namespace.QName PASSENGERS$46 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "passengers");
    private static final javax.xml.namespace.QName RECORDLOCATOR$48 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "recordlocator");
    private static final javax.xml.namespace.QName REPORTMETHOD$50 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "reportmethod");
    private static final javax.xml.namespace.QName STATIONASSIGNED$52 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "stationassigned");
    private static final javax.xml.namespace.QName STATIONCREATED$54 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "stationcreated");
    private static final javax.xml.namespace.QName STATUS$56 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "status");
    private static final javax.xml.namespace.QName TICKETNUMBER$58 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "ticketnumber");
    private static final javax.xml.namespace.QName TSACHECKED$60 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "tsachecked");
    private static final javax.xml.namespace.QName USERNAME$62 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "username");
    private static final javax.xml.namespace.QName VOLUNTARYSEPARATION$64 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "voluntaryseparation");
    private static final javax.xml.namespace.QName WEBSERVICESESSIONID$66 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "webservice_session_ID");

    public WSIncidentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "agent" element
     */
    public java.lang.String getAgent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AGENT$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "agent" element
     */
    public org.apache.xmlbeans.XmlString xgetAgent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENT$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "agent" element
     */
    public boolean isNilAgent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENT$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "agent" element
     */
    public boolean isSetAgent() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(AGENT$0) != 0;
        }
    }

    /**
     * Sets the "agent" element
     */
    public void setAgent(java.lang.String agent) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AGENT$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AGENT$0);
            }

            target.setStringValue(agent);
        }
    }

    /**
     * Sets (as xml) the "agent" element
     */
    public void xsetAgent(org.apache.xmlbeans.XmlString agent) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENT$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AGENT$0);
            }

            target.set(agent);
        }
    }

    /**
     * Nils the "agent" element
     */
    public void setNilAgent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENT$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AGENT$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "agent" element
     */
    public void unsetAgent() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(AGENT$0, 0);
        }
    }

    /**
     * Gets the "agentassigned" element
     */
    public java.lang.String getAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AGENTASSIGNED$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "agentassigned" element
     */
    public org.apache.xmlbeans.XmlString xgetAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENTASSIGNED$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "agentassigned" element
     */
    public boolean isNilAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENTASSIGNED$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "agentassigned" element
     */
    public boolean isSetAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(AGENTASSIGNED$2) != 0;
        }
    }

    /**
     * Sets the "agentassigned" element
     */
    public void setAgentassigned(java.lang.String agentassigned) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AGENTASSIGNED$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AGENTASSIGNED$2);
            }

            target.setStringValue(agentassigned);
        }
    }

    /**
     * Sets (as xml) the "agentassigned" element
     */
    public void xsetAgentassigned(org.apache.xmlbeans.XmlString agentassigned) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENTASSIGNED$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AGENTASSIGNED$2);
            }

            target.set(agentassigned);
        }
    }

    /**
     * Nils the "agentassigned" element
     */
    public void setNilAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AGENTASSIGNED$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AGENTASSIGNED$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "agentassigned" element
     */
    public void unsetAgentassigned() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(AGENTASSIGNED$2, 0);
        }
    }

    /**
     * Gets array of all "articles" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[] getArticlesArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ARTICLES$4, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "articles" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle getArticlesArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .find_element_user(ARTICLES$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "articles" element
     */
    public boolean isNilArticlesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .find_element_user(ARTICLES$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "articles" element
     */
    public int sizeOfArticlesArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARTICLES$4);
        }
    }

    /**
     * Sets array of all "articles" element
     */
    public void setArticlesArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[] articlesArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(articlesArray, ARTICLES$4);
        }
    }

    /**
     * Sets ith "articles" element
     */
    public void setArticlesArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle articles) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .find_element_user(ARTICLES$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(articles);
        }
    }

    /**
     * Nils the ith "articles" element
     */
    public void setNilArticlesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .find_element_user(ARTICLES$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "articles" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle insertNewArticles(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .insert_element_user(ARTICLES$4,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "articles" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle addNewArticles() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) get_store()
                                                                           .add_element_user(ARTICLES$4);

            return target;
        }
    }

    /**
     * Removes the ith "articles" element
     */
    public void removeArticles(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARTICLES$4, i);
        }
    }

    /**
     * Gets the "checkedlocation" element
     */
    public java.lang.String getCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CHECKEDLOCATION$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "checkedlocation" element
     */
    public org.apache.xmlbeans.XmlString xgetCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CHECKEDLOCATION$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "checkedlocation" element
     */
    public boolean isNilCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CHECKEDLOCATION$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "checkedlocation" element
     */
    public boolean isSetCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CHECKEDLOCATION$6) != 0;
        }
    }

    /**
     * Sets the "checkedlocation" element
     */
    public void setCheckedlocation(java.lang.String checkedlocation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CHECKEDLOCATION$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CHECKEDLOCATION$6);
            }

            target.setStringValue(checkedlocation);
        }
    }

    /**
     * Sets (as xml) the "checkedlocation" element
     */
    public void xsetCheckedlocation(
        org.apache.xmlbeans.XmlString checkedlocation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CHECKEDLOCATION$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CHECKEDLOCATION$6);
            }

            target.set(checkedlocation);
        }
    }

    /**
     * Nils the "checkedlocation" element
     */
    public void setNilCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CHECKEDLOCATION$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CHECKEDLOCATION$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "checkedlocation" element
     */
    public void unsetCheckedlocation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CHECKEDLOCATION$6, 0);
        }
    }

    /**
     * Gets array of all "claimchecks" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[] getClaimchecksArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECKS$8, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "claimchecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck getClaimchecksArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .find_element_user(CLAIMCHECKS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "claimchecks" element
     */
    public boolean isNilClaimchecksArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .find_element_user(CLAIMCHECKS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "claimchecks" element
     */
    public int sizeOfClaimchecksArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECKS$8);
        }
    }

    /**
     * Sets array of all "claimchecks" element
     */
    public void setClaimchecksArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[] claimchecksArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(claimchecksArray, CLAIMCHECKS$8);
        }
    }

    /**
     * Sets ith "claimchecks" element
     */
    public void setClaimchecksArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck claimchecks) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .find_element_user(CLAIMCHECKS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(claimchecks);
        }
    }

    /**
     * Nils the ith "claimchecks" element
     */
    public void setNilClaimchecksArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .find_element_user(CLAIMCHECKS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimchecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck insertNewClaimchecks(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .insert_element_user(CLAIMCHECKS$8,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "claimchecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck addNewClaimchecks() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) get_store()
                                                                                      .add_element_user(CLAIMCHECKS$8);

            return target;
        }
    }

    /**
     * Removes the ith "claimchecks" element
     */
    public void removeClaimchecks(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKS$8, i);
        }
    }

    /**
     * Gets the "closedate" element
     */
    public java.lang.String getClosedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLOSEDATE$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "closedate" element
     */
    public org.apache.xmlbeans.XmlString xgetClosedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLOSEDATE$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "closedate" element
     */
    public boolean isNilClosedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLOSEDATE$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "closedate" element
     */
    public boolean isSetClosedate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLOSEDATE$10) != 0;
        }
    }

    /**
     * Sets the "closedate" element
     */
    public void setClosedate(java.lang.String closedate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLOSEDATE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CLOSEDATE$10);
            }

            target.setStringValue(closedate);
        }
    }

    /**
     * Sets (as xml) the "closedate" element
     */
    public void xsetClosedate(org.apache.xmlbeans.XmlString closedate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLOSEDATE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLOSEDATE$10);
            }

            target.set(closedate);
        }
    }

    /**
     * Nils the "closedate" element
     */
    public void setNilClosedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLOSEDATE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLOSEDATE$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "closedate" element
     */
    public void unsetClosedate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLOSEDATE$10, 0);
        }
    }

    /**
     * Gets the "companycode_ID" element
     */
    public java.lang.String getCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMPANYCODEID$12,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "companycode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$12,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "companycode_ID" element
     */
    public boolean isNilCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "companycode_ID" element
     */
    public boolean isSetCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COMPANYCODEID$12) != 0;
        }
    }

    /**
     * Sets the "companycode_ID" element
     */
    public void setCompanycodeID(java.lang.String companycodeID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMPANYCODEID$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COMPANYCODEID$12);
            }

            target.setStringValue(companycodeID);
        }
    }

    /**
     * Sets (as xml) the "companycode_ID" element
     */
    public void xsetCompanycodeID(org.apache.xmlbeans.XmlString companycodeID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMPANYCODEID$12);
            }

            target.set(companycodeID);
        }
    }

    /**
     * Nils the "companycode_ID" element
     */
    public void setNilCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMPANYCODEID$12);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "companycode_ID" element
     */
    public void unsetCompanycodeID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$12, 0);
        }
    }

    /**
     * Gets the "courtesyreport" element
     */
    public int getCourtesyreport() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COURTESYREPORT$14,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "courtesyreport" element
     */
    public org.apache.xmlbeans.XmlInt xgetCourtesyreport() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(COURTESYREPORT$14,
                    0);

            return target;
        }
    }

    /**
     * True if has "courtesyreport" element
     */
    public boolean isSetCourtesyreport() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COURTESYREPORT$14) != 0;
        }
    }

    /**
     * Sets the "courtesyreport" element
     */
    public void setCourtesyreport(int courtesyreport) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COURTESYREPORT$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COURTESYREPORT$14);
            }

            target.setIntValue(courtesyreport);
        }
    }

    /**
     * Sets (as xml) the "courtesyreport" element
     */
    public void xsetCourtesyreport(org.apache.xmlbeans.XmlInt courtesyreport) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(COURTESYREPORT$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(COURTESYREPORT$14);
            }

            target.set(courtesyreport);
        }
    }

    /**
     * Unsets the "courtesyreport" element
     */
    public void unsetCourtesyreport() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COURTESYREPORT$14, 0);
        }
    }

    /**
     * Gets the "createdate" element
     */
    public java.lang.String getCreatedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CREATEDATE$16,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "createdate" element
     */
    public org.apache.xmlbeans.XmlString xgetCreatedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATEDATE$16,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "createdate" element
     */
    public boolean isNilCreatedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATEDATE$16,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "createdate" element
     */
    public boolean isSetCreatedate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CREATEDATE$16) != 0;
        }
    }

    /**
     * Sets the "createdate" element
     */
    public void setCreatedate(java.lang.String createdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CREATEDATE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CREATEDATE$16);
            }

            target.setStringValue(createdate);
        }
    }

    /**
     * Sets (as xml) the "createdate" element
     */
    public void xsetCreatedate(org.apache.xmlbeans.XmlString createdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATEDATE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CREATEDATE$16);
            }

            target.set(createdate);
        }
    }

    /**
     * Nils the "createdate" element
     */
    public void setNilCreatedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATEDATE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CREATEDATE$16);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "createdate" element
     */
    public void unsetCreatedate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CREATEDATE$16, 0);
        }
    }

    /**
     * Gets the "createtime" element
     */
    public java.lang.String getCreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CREATETIME$18,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "createtime" element
     */
    public org.apache.xmlbeans.XmlString xgetCreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATETIME$18,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "createtime" element
     */
    public boolean isNilCreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATETIME$18,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "createtime" element
     */
    public boolean isSetCreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CREATETIME$18) != 0;
        }
    }

    /**
     * Sets the "createtime" element
     */
    public void setCreatetime(java.lang.String createtime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CREATETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CREATETIME$18);
            }

            target.setStringValue(createtime);
        }
    }

    /**
     * Sets (as xml) the "createtime" element
     */
    public void xsetCreatetime(org.apache.xmlbeans.XmlString createtime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CREATETIME$18);
            }

            target.set(createtime);
        }
    }

    /**
     * Nils the "createtime" element
     */
    public void setNilCreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CREATETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CREATETIME$18);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "createtime" element
     */
    public void unsetCreatetime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CREATETIME$18, 0);
        }
    }

    /**
     * Gets the "customcleared" element
     */
    public int getCustomcleared() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CUSTOMCLEARED$20,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "customcleared" element
     */
    public org.apache.xmlbeans.XmlInt xgetCustomcleared() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(CUSTOMCLEARED$20,
                    0);

            return target;
        }
    }

    /**
     * True if has "customcleared" element
     */
    public boolean isSetCustomcleared() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CUSTOMCLEARED$20) != 0;
        }
    }

    /**
     * Sets the "customcleared" element
     */
    public void setCustomcleared(int customcleared) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CUSTOMCLEARED$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CUSTOMCLEARED$20);
            }

            target.setIntValue(customcleared);
        }
    }

    /**
     * Sets (as xml) the "customcleared" element
     */
    public void xsetCustomcleared(org.apache.xmlbeans.XmlInt customcleared) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(CUSTOMCLEARED$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(CUSTOMCLEARED$20);
            }

            target.set(customcleared);
        }
    }

    /**
     * Unsets the "customcleared" element
     */
    public void unsetCustomcleared() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CUSTOMCLEARED$20, 0);
        }
    }

    /**
     * Gets the "errorcode" element
     */
    public java.lang.String getErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORCODE$22,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "errorcode" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$22,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "errorcode" element
     */
    public boolean isNilErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$22,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "errorcode" element
     */
    public boolean isSetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ERRORCODE$22) != 0;
        }
    }

    /**
     * Sets the "errorcode" element
     */
    public void setErrorcode(java.lang.String errorcode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORCODE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ERRORCODE$22);
            }

            target.setStringValue(errorcode);
        }
    }

    /**
     * Sets (as xml) the "errorcode" element
     */
    public void xsetErrorcode(org.apache.xmlbeans.XmlString errorcode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORCODE$22);
            }

            target.set(errorcode);
        }
    }

    /**
     * Nils the "errorcode" element
     */
    public void setNilErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORCODE$22);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "errorcode" element
     */
    public void unsetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ERRORCODE$22, 0);
        }
    }

    /**
     * Gets the "faultairline" element
     */
    public java.lang.String getFaultairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTAIRLINE$24,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "faultairline" element
     */
    public org.apache.xmlbeans.XmlString xgetFaultairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTAIRLINE$24,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "faultairline" element
     */
    public boolean isNilFaultairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTAIRLINE$24,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "faultairline" element
     */
    public boolean isSetFaultairline() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FAULTAIRLINE$24) != 0;
        }
    }

    /**
     * Sets the "faultairline" element
     */
    public void setFaultairline(java.lang.String faultairline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTAIRLINE$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FAULTAIRLINE$24);
            }

            target.setStringValue(faultairline);
        }
    }

    /**
     * Sets (as xml) the "faultairline" element
     */
    public void xsetFaultairline(org.apache.xmlbeans.XmlString faultairline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTAIRLINE$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTAIRLINE$24);
            }

            target.set(faultairline);
        }
    }

    /**
     * Nils the "faultairline" element
     */
    public void setNilFaultairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTAIRLINE$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTAIRLINE$24);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "faultairline" element
     */
    public void unsetFaultairline() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FAULTAIRLINE$24, 0);
        }
    }

    /**
     * Gets the "faultstation" element
     */
    public java.lang.String getFaultstation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTSTATION$26,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "faultstation" element
     */
    public org.apache.xmlbeans.XmlString xgetFaultstation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$26,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "faultstation" element
     */
    public boolean isNilFaultstation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$26,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "faultstation" element
     */
    public boolean isSetFaultstation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FAULTSTATION$26) != 0;
        }
    }

    /**
     * Sets the "faultstation" element
     */
    public void setFaultstation(java.lang.String faultstation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTSTATION$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FAULTSTATION$26);
            }

            target.setStringValue(faultstation);
        }
    }

    /**
     * Sets (as xml) the "faultstation" element
     */
    public void xsetFaultstation(org.apache.xmlbeans.XmlString faultstation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTSTATION$26);
            }

            target.set(faultstation);
        }
    }

    /**
     * Nils the "faultstation" element
     */
    public void setNilFaultstation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTSTATION$26);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "faultstation" element
     */
    public void unsetFaultstation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FAULTSTATION$26, 0);
        }
    }

    /**
     * Gets the "incident_ID" element
     */
    public java.lang.String getIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTID$28,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "incident_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$28,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "incident_ID" element
     */
    public boolean isNilIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$28,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "incident_ID" element
     */
    public boolean isSetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(INCIDENTID$28) != 0;
        }
    }

    /**
     * Sets the "incident_ID" element
     */
    public void setIncidentID(java.lang.String incidentID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTID$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(INCIDENTID$28);
            }

            target.setStringValue(incidentID);
        }
    }

    /**
     * Sets (as xml) the "incident_ID" element
     */
    public void xsetIncidentID(org.apache.xmlbeans.XmlString incidentID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTID$28);
            }

            target.set(incidentID);
        }
    }

    /**
     * Nils the "incident_ID" element
     */
    public void setNilIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTID$28);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "incident_ID" element
     */
    public void unsetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(INCIDENTID$28, 0);
        }
    }

    /**
     * Gets array of all "items" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[] getItemsArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEMS$30, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItem getItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .find_element_user(ITEMS$30,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "items" element
     */
    public boolean isNilItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .find_element_user(ITEMS$30,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "items" element
     */
    public int sizeOfItemsArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITEMS$30);
        }
    }

    /**
     * Sets array of all "items" element
     */
    public void setItemsArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[] itemsArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(itemsArray, ITEMS$30);
        }
    }

    /**
     * Sets ith "items" element
     */
    public void setItemsArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItem items) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .find_element_user(ITEMS$30,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(items);
        }
    }

    /**
     * Nils the ith "items" element
     */
    public void setNilItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .find_element_user(ITEMS$30,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItem insertNewItems(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .insert_element_user(ITEMS$30,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItem addNewItems() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItem) get_store()
                                                                        .add_element_user(ITEMS$30);

            return target;
        }
    }

    /**
     * Removes the ith "items" element
     */
    public void removeItems(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITEMS$30, i);
        }
    }

    /**
     * Gets the "itemtype" element
     */
    public java.lang.String getItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITEMTYPE$32,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "itemtype" element
     */
    public org.apache.xmlbeans.XmlString xgetItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$32,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "itemtype" element
     */
    public boolean isNilItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$32,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "itemtype" element
     */
    public boolean isSetItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITEMTYPE$32) != 0;
        }
    }

    /**
     * Sets the "itemtype" element
     */
    public void setItemtype(java.lang.String itemtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITEMTYPE$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ITEMTYPE$32);
            }

            target.setStringValue(itemtype);
        }
    }

    /**
     * Sets (as xml) the "itemtype" element
     */
    public void xsetItemtype(org.apache.xmlbeans.XmlString itemtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ITEMTYPE$32);
            }

            target.set(itemtype);
        }
    }

    /**
     * Nils the "itemtype" element
     */
    public void setNilItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ITEMTYPE$32);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "itemtype" element
     */
    public void unsetItemtype() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITEMTYPE$32, 0);
        }
    }

    /**
     * Gets array of all "itineraries" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] getItinerariesArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITINERARIES$34, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary getItinerariesArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .find_element_user(ITINERARIES$34,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "itineraries" element
     */
    public boolean isNilItinerariesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .find_element_user(ITINERARIES$34,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "itineraries" element
     */
    public int sizeOfItinerariesArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITINERARIES$34);
        }
    }

    /**
     * Sets array of all "itineraries" element
     */
    public void setItinerariesArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] itinerariesArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(itinerariesArray, ITINERARIES$34);
        }
    }

    /**
     * Sets ith "itineraries" element
     */
    public void setItinerariesArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary itineraries) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .find_element_user(ITINERARIES$34,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(itineraries);
        }
    }

    /**
     * Nils the ith "itineraries" element
     */
    public void setNilItinerariesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .find_element_user(ITINERARIES$34,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary insertNewItineraries(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .insert_element_user(ITINERARIES$34,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary addNewItineraries() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary) get_store()
                                                                             .add_element_user(ITINERARIES$34);

            return target;
        }
    }

    /**
     * Removes the ith "itineraries" element
     */
    public void removeItineraries(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITINERARIES$34, i);
        }
    }

    /**
     * Gets the "loss_code" element
     */
    public int getLossCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LOSSCODE$36,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "loss_code" element
     */
    public org.apache.xmlbeans.XmlInt xgetLossCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LOSSCODE$36,
                    0);

            return target;
        }
    }

    /**
     * True if has "loss_code" element
     */
    public boolean isSetLossCode() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LOSSCODE$36) != 0;
        }
    }

    /**
     * Sets the "loss_code" element
     */
    public void setLossCode(int lossCode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LOSSCODE$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LOSSCODE$36);
            }

            target.setIntValue(lossCode);
        }
    }

    /**
     * Sets (as xml) the "loss_code" element
     */
    public void xsetLossCode(org.apache.xmlbeans.XmlInt lossCode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LOSSCODE$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(LOSSCODE$36);
            }

            target.set(lossCode);
        }
    }

    /**
     * Unsets the "loss_code" element
     */
    public void unsetLossCode() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LOSSCODE$36, 0);
        }
    }

    /**
     * Gets the "nonrevenue" element
     */
    public int getNonrevenue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NONREVENUE$38,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "nonrevenue" element
     */
    public org.apache.xmlbeans.XmlInt xgetNonrevenue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NONREVENUE$38,
                    0);

            return target;
        }
    }

    /**
     * True if has "nonrevenue" element
     */
    public boolean isSetNonrevenue() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NONREVENUE$38) != 0;
        }
    }

    /**
     * Sets the "nonrevenue" element
     */
    public void setNonrevenue(int nonrevenue) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NONREVENUE$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NONREVENUE$38);
            }

            target.setIntValue(nonrevenue);
        }
    }

    /**
     * Sets (as xml) the "nonrevenue" element
     */
    public void xsetNonrevenue(org.apache.xmlbeans.XmlInt nonrevenue) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NONREVENUE$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NONREVENUE$38);
            }

            target.set(nonrevenue);
        }
    }

    /**
     * Unsets the "nonrevenue" element
     */
    public void unsetNonrevenue() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NONREVENUE$38, 0);
        }
    }

    /**
     * Gets the "numbagchecked" element
     */
    public int getNumbagchecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBAGCHECKED$40,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "numbagchecked" element
     */
    public org.apache.xmlbeans.XmlInt xgetNumbagchecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBAGCHECKED$40,
                    0);

            return target;
        }
    }

    /**
     * True if has "numbagchecked" element
     */
    public boolean isSetNumbagchecked() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NUMBAGCHECKED$40) != 0;
        }
    }

    /**
     * Sets the "numbagchecked" element
     */
    public void setNumbagchecked(int numbagchecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBAGCHECKED$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NUMBAGCHECKED$40);
            }

            target.setIntValue(numbagchecked);
        }
    }

    /**
     * Sets (as xml) the "numbagchecked" element
     */
    public void xsetNumbagchecked(org.apache.xmlbeans.XmlInt numbagchecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBAGCHECKED$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NUMBAGCHECKED$40);
            }

            target.set(numbagchecked);
        }
    }

    /**
     * Unsets the "numbagchecked" element
     */
    public void unsetNumbagchecked() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NUMBAGCHECKED$40, 0);
        }
    }

    /**
     * Gets the "numbagreceived" element
     */
    public int getNumbagreceived() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBAGRECEIVED$42,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "numbagreceived" element
     */
    public org.apache.xmlbeans.XmlInt xgetNumbagreceived() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBAGRECEIVED$42,
                    0);

            return target;
        }
    }

    /**
     * True if has "numbagreceived" element
     */
    public boolean isSetNumbagreceived() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NUMBAGRECEIVED$42) != 0;
        }
    }

    /**
     * Sets the "numbagreceived" element
     */
    public void setNumbagreceived(int numbagreceived) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBAGRECEIVED$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NUMBAGRECEIVED$42);
            }

            target.setIntValue(numbagreceived);
        }
    }

    /**
     * Sets (as xml) the "numbagreceived" element
     */
    public void xsetNumbagreceived(org.apache.xmlbeans.XmlInt numbagreceived) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBAGRECEIVED$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NUMBAGRECEIVED$42);
            }

            target.set(numbagreceived);
        }
    }

    /**
     * Unsets the "numbagreceived" element
     */
    public void unsetNumbagreceived() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NUMBAGRECEIVED$42, 0);
        }
    }

    /**
     * Gets the "numpassengers" element
     */
    public int getNumpassengers() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMPASSENGERS$44,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "numpassengers" element
     */
    public org.apache.xmlbeans.XmlInt xgetNumpassengers() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMPASSENGERS$44,
                    0);

            return target;
        }
    }

    /**
     * True if has "numpassengers" element
     */
    public boolean isSetNumpassengers() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NUMPASSENGERS$44) != 0;
        }
    }

    /**
     * Sets the "numpassengers" element
     */
    public void setNumpassengers(int numpassengers) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMPASSENGERS$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NUMPASSENGERS$44);
            }

            target.setIntValue(numpassengers);
        }
    }

    /**
     * Sets (as xml) the "numpassengers" element
     */
    public void xsetNumpassengers(org.apache.xmlbeans.XmlInt numpassengers) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMPASSENGERS$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NUMPASSENGERS$44);
            }

            target.set(numpassengers);
        }
    }

    /**
     * Unsets the "numpassengers" element
     */
    public void unsetNumpassengers() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NUMPASSENGERS$44, 0);
        }
    }

    /**
     * Gets array of all "passengers" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] getPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$46, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger getPassengersArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .find_element_user(PASSENGERS$46,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "passengers" element
     */
    public boolean isNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .find_element_user(PASSENGERS$46,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "passengers" element
     */
    public int sizeOfPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSENGERS$46);
        }
    }

    /**
     * Sets array of all "passengers" element
     */
    public void setPassengersArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] passengersArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(passengersArray, PASSENGERS$46);
        }
    }

    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger passengers) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .find_element_user(PASSENGERS$46,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(passengers);
        }
    }

    /**
     * Nils the ith "passengers" element
     */
    public void setNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .find_element_user(PASSENGERS$46,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger insertNewPassengers(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .insert_element_user(PASSENGERS$46,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger addNewPassengers() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger) get_store()
                                                                             .add_element_user(PASSENGERS$46);

            return target;
        }
    }

    /**
     * Removes the ith "passengers" element
     */
    public void removePassengers(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSENGERS$46, i);
        }
    }

    /**
     * Gets the "recordlocator" element
     */
    public java.lang.String getRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RECORDLOCATOR$48,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "recordlocator" element
     */
    public org.apache.xmlbeans.XmlString xgetRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RECORDLOCATOR$48,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "recordlocator" element
     */
    public boolean isNilRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RECORDLOCATOR$48,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "recordlocator" element
     */
    public boolean isSetRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RECORDLOCATOR$48) != 0;
        }
    }

    /**
     * Sets the "recordlocator" element
     */
    public void setRecordlocator(java.lang.String recordlocator) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RECORDLOCATOR$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RECORDLOCATOR$48);
            }

            target.setStringValue(recordlocator);
        }
    }

    /**
     * Sets (as xml) the "recordlocator" element
     */
    public void xsetRecordlocator(org.apache.xmlbeans.XmlString recordlocator) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RECORDLOCATOR$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RECORDLOCATOR$48);
            }

            target.set(recordlocator);
        }
    }

    /**
     * Nils the "recordlocator" element
     */
    public void setNilRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RECORDLOCATOR$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RECORDLOCATOR$48);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "recordlocator" element
     */
    public void unsetRecordlocator() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RECORDLOCATOR$48, 0);
        }
    }

    /**
     * Gets the "reportmethod" element
     */
    public java.lang.String getReportmethod() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(REPORTMETHOD$50,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "reportmethod" element
     */
    public org.apache.xmlbeans.XmlString xgetReportmethod() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REPORTMETHOD$50,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "reportmethod" element
     */
    public boolean isNilReportmethod() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REPORTMETHOD$50,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "reportmethod" element
     */
    public boolean isSetReportmethod() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(REPORTMETHOD$50) != 0;
        }
    }

    /**
     * Sets the "reportmethod" element
     */
    public void setReportmethod(java.lang.String reportmethod) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(REPORTMETHOD$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(REPORTMETHOD$50);
            }

            target.setStringValue(reportmethod);
        }
    }

    /**
     * Sets (as xml) the "reportmethod" element
     */
    public void xsetReportmethod(org.apache.xmlbeans.XmlString reportmethod) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REPORTMETHOD$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(REPORTMETHOD$50);
            }

            target.set(reportmethod);
        }
    }

    /**
     * Nils the "reportmethod" element
     */
    public void setNilReportmethod() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REPORTMETHOD$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(REPORTMETHOD$50);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "reportmethod" element
     */
    public void unsetReportmethod() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(REPORTMETHOD$50, 0);
        }
    }

    /**
     * Gets the "stationassigned" element
     */
    public java.lang.String getStationassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONASSIGNED$52,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "stationassigned" element
     */
    public org.apache.xmlbeans.XmlString xgetStationassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONASSIGNED$52,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "stationassigned" element
     */
    public boolean isNilStationassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONASSIGNED$52,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "stationassigned" element
     */
    public boolean isSetStationassigned() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATIONASSIGNED$52) != 0;
        }
    }

    /**
     * Sets the "stationassigned" element
     */
    public void setStationassigned(java.lang.String stationassigned) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONASSIGNED$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATIONASSIGNED$52);
            }

            target.setStringValue(stationassigned);
        }
    }

    /**
     * Sets (as xml) the "stationassigned" element
     */
    public void xsetStationassigned(
        org.apache.xmlbeans.XmlString stationassigned) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONASSIGNED$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONASSIGNED$52);
            }

            target.set(stationassigned);
        }
    }

    /**
     * Nils the "stationassigned" element
     */
    public void setNilStationassigned() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONASSIGNED$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONASSIGNED$52);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "stationassigned" element
     */
    public void unsetStationassigned() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATIONASSIGNED$52, 0);
        }
    }

    /**
     * Gets the "stationcreated" element
     */
    public java.lang.String getStationcreated() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONCREATED$54,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "stationcreated" element
     */
    public org.apache.xmlbeans.XmlString xgetStationcreated() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCREATED$54,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "stationcreated" element
     */
    public boolean isNilStationcreated() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCREATED$54,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "stationcreated" element
     */
    public boolean isSetStationcreated() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATIONCREATED$54) != 0;
        }
    }

    /**
     * Sets the "stationcreated" element
     */
    public void setStationcreated(java.lang.String stationcreated) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONCREATED$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATIONCREATED$54);
            }

            target.setStringValue(stationcreated);
        }
    }

    /**
     * Sets (as xml) the "stationcreated" element
     */
    public void xsetStationcreated(org.apache.xmlbeans.XmlString stationcreated) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCREATED$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONCREATED$54);
            }

            target.set(stationcreated);
        }
    }

    /**
     * Nils the "stationcreated" element
     */
    public void setNilStationcreated() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCREATED$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONCREATED$54);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "stationcreated" element
     */
    public void unsetStationcreated() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATIONCREATED$54, 0);
        }
    }

    /**
     * Gets the "status" element
     */
    public java.lang.String getStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUS$56,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlString xgetStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$56,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "status" element
     */
    public boolean isNilStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$56,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "status" element
     */
    public boolean isSetStatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATUS$56) != 0;
        }
    }

    /**
     * Sets the "status" element
     */
    public void setStatus(java.lang.String status) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUS$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATUS$56);
            }

            target.setStringValue(status);
        }
    }

    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlString status) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATUS$56);
            }

            target.set(status);
        }
    }

    /**
     * Nils the "status" element
     */
    public void setNilStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATUS$56);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "status" element
     */
    public void unsetStatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATUS$56, 0);
        }
    }

    /**
     * Gets the "ticketnumber" element
     */
    public java.lang.String getTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TICKETNUMBER$58,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "ticketnumber" element
     */
    public org.apache.xmlbeans.XmlString xgetTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TICKETNUMBER$58,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "ticketnumber" element
     */
    public boolean isNilTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TICKETNUMBER$58,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "ticketnumber" element
     */
    public boolean isSetTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TICKETNUMBER$58) != 0;
        }
    }

    /**
     * Sets the "ticketnumber" element
     */
    public void setTicketnumber(java.lang.String ticketnumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TICKETNUMBER$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TICKETNUMBER$58);
            }

            target.setStringValue(ticketnumber);
        }
    }

    /**
     * Sets (as xml) the "ticketnumber" element
     */
    public void xsetTicketnumber(org.apache.xmlbeans.XmlString ticketnumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TICKETNUMBER$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TICKETNUMBER$58);
            }

            target.set(ticketnumber);
        }
    }

    /**
     * Nils the "ticketnumber" element
     */
    public void setNilTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TICKETNUMBER$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TICKETNUMBER$58);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "ticketnumber" element
     */
    public void unsetTicketnumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TICKETNUMBER$58, 0);
        }
    }

    /**
     * Gets the "tsachecked" element
     */
    public int getTsachecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TSACHECKED$60,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "tsachecked" element
     */
    public org.apache.xmlbeans.XmlInt xgetTsachecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(TSACHECKED$60,
                    0);

            return target;
        }
    }

    /**
     * True if has "tsachecked" element
     */
    public boolean isSetTsachecked() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TSACHECKED$60) != 0;
        }
    }

    /**
     * Sets the "tsachecked" element
     */
    public void setTsachecked(int tsachecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TSACHECKED$60,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TSACHECKED$60);
            }

            target.setIntValue(tsachecked);
        }
    }

    /**
     * Sets (as xml) the "tsachecked" element
     */
    public void xsetTsachecked(org.apache.xmlbeans.XmlInt tsachecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(TSACHECKED$60,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(TSACHECKED$60);
            }

            target.set(tsachecked);
        }
    }

    /**
     * Unsets the "tsachecked" element
     */
    public void unsetTsachecked() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TSACHECKED$60, 0);
        }
    }

    /**
     * Gets the "username" element
     */
    public java.lang.String getUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNAME$62,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "username" element
     */
    public org.apache.xmlbeans.XmlString xgetUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$62,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "username" element
     */
    public boolean isNilUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$62,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "username" element
     */
    public boolean isSetUsername() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(USERNAME$62) != 0;
        }
    }

    /**
     * Sets the "username" element
     */
    public void setUsername(java.lang.String username) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNAME$62,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(USERNAME$62);
            }

            target.setStringValue(username);
        }
    }

    /**
     * Sets (as xml) the "username" element
     */
    public void xsetUsername(org.apache.xmlbeans.XmlString username) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$62,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNAME$62);
            }

            target.set(username);
        }
    }

    /**
     * Nils the "username" element
     */
    public void setNilUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$62,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNAME$62);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "username" element
     */
    public void unsetUsername() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(USERNAME$62, 0);
        }
    }

    /**
     * Gets the "voluntaryseparation" element
     */
    public int getVoluntaryseparation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VOLUNTARYSEPARATION$64,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "voluntaryseparation" element
     */
    public org.apache.xmlbeans.XmlInt xgetVoluntaryseparation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(VOLUNTARYSEPARATION$64,
                    0);

            return target;
        }
    }

    /**
     * True if has "voluntaryseparation" element
     */
    public boolean isSetVoluntaryseparation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(VOLUNTARYSEPARATION$64) != 0;
        }
    }

    /**
     * Sets the "voluntaryseparation" element
     */
    public void setVoluntaryseparation(int voluntaryseparation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VOLUNTARYSEPARATION$64,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(VOLUNTARYSEPARATION$64);
            }

            target.setIntValue(voluntaryseparation);
        }
    }

    /**
     * Sets (as xml) the "voluntaryseparation" element
     */
    public void xsetVoluntaryseparation(
        org.apache.xmlbeans.XmlInt voluntaryseparation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(VOLUNTARYSEPARATION$64,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(VOLUNTARYSEPARATION$64);
            }

            target.set(voluntaryseparation);
        }
    }

    /**
     * Unsets the "voluntaryseparation" element
     */
    public void unsetVoluntaryseparation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(VOLUNTARYSEPARATION$64, 0);
        }
    }

    /**
     * Gets the "webservice_session_ID" element
     */
    public java.lang.String getWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "webservice_session_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "webservice_session_ID" element
     */
    public boolean isNilWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "webservice_session_ID" element
     */
    public boolean isSetWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(WEBSERVICESESSIONID$66) != 0;
        }
    }

    /**
     * Sets the "webservice_session_ID" element
     */
    public void setWebserviceSessionID(java.lang.String webserviceSessionID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(WEBSERVICESESSIONID$66);
            }

            target.setStringValue(webserviceSessionID);
        }
    }

    /**
     * Sets (as xml) the "webservice_session_ID" element
     */
    public void xsetWebserviceSessionID(
        org.apache.xmlbeans.XmlString webserviceSessionID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WEBSERVICESESSIONID$66);
            }

            target.set(webserviceSessionID);
        }
    }

    /**
     * Nils the "webservice_session_ID" element
     */
    public void setNilWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WEBSERVICESESSIONID$66,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WEBSERVICESESSIONID$66);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "webservice_session_ID" element
     */
    public void unsetWebserviceSessionID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(WEBSERVICESESSIONID$66, 0);
        }
    }
}
