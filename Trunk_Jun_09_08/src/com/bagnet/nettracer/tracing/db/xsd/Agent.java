/*
 * XML Type:  Agent
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Agent
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd;


/**
 * An XML Agent(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Agent extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Agent.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3120780053B2C9C6ED89C37135AF9582").resolveHandle("agent8214type");
    
    /**
     * Gets the "account_locked" element
     */
    boolean getAccountLocked();
    
    /**
     * Gets (as xml) the "account_locked" element
     */
    org.apache.xmlbeans.XmlBoolean xgetAccountLocked();
    
    /**
     * True if has "account_locked" element
     */
    boolean isSetAccountLocked();
    
    /**
     * Sets the "account_locked" element
     */
    void setAccountLocked(boolean accountLocked);
    
    /**
     * Sets (as xml) the "account_locked" element
     */
    void xsetAccountLocked(org.apache.xmlbeans.XmlBoolean accountLocked);
    
    /**
     * Unsets the "account_locked" element
     */
    void unsetAccountLocked();
    
    /**
     * Gets the "active" element
     */
    boolean getActive();
    
    /**
     * Gets (as xml) the "active" element
     */
    org.apache.xmlbeans.XmlBoolean xgetActive();
    
    /**
     * True if has "active" element
     */
    boolean isSetActive();
    
    /**
     * Sets the "active" element
     */
    void setActive(boolean active);
    
    /**
     * Sets (as xml) the "active" element
     */
    void xsetActive(org.apache.xmlbeans.XmlBoolean active);
    
    /**
     * Unsets the "active" element
     */
    void unsetActive();
    
    /**
     * Gets the "agent_ID" element
     */
    int getAgentID();
    
    /**
     * Gets (as xml) the "agent_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetAgentID();
    
    /**
     * True if has "agent_ID" element
     */
    boolean isSetAgentID();
    
    /**
     * Sets the "agent_ID" element
     */
    void setAgentID(int agentID);
    
    /**
     * Sets (as xml) the "agent_ID" element
     */
    void xsetAgentID(org.apache.xmlbeans.XmlInt agentID);
    
    /**
     * Unsets the "agent_ID" element
     */
    void unsetAgentID();
    
    /**
     * Gets the "companycode_ID" element
     */
    java.lang.String getCompanycodeID();
    
    /**
     * Gets (as xml) the "companycode_ID" element
     */
    org.apache.xmlbeans.XmlString xgetCompanycodeID();
    
    /**
     * Tests for nil "companycode_ID" element
     */
    boolean isNilCompanycodeID();
    
    /**
     * True if has "companycode_ID" element
     */
    boolean isSetCompanycodeID();
    
    /**
     * Sets the "companycode_ID" element
     */
    void setCompanycodeID(java.lang.String companycodeID);
    
    /**
     * Sets (as xml) the "companycode_ID" element
     */
    void xsetCompanycodeID(org.apache.xmlbeans.XmlString companycodeID);
    
    /**
     * Nils the "companycode_ID" element
     */
    void setNilCompanycodeID();
    
    /**
     * Unsets the "companycode_ID" element
     */
    void unsetCompanycodeID();
    
    /**
     * Gets the "currentlocale" element
     */
    java.lang.String getCurrentlocale();
    
    /**
     * Gets (as xml) the "currentlocale" element
     */
    org.apache.xmlbeans.XmlString xgetCurrentlocale();
    
    /**
     * Tests for nil "currentlocale" element
     */
    boolean isNilCurrentlocale();
    
    /**
     * True if has "currentlocale" element
     */
    boolean isSetCurrentlocale();
    
    /**
     * Sets the "currentlocale" element
     */
    void setCurrentlocale(java.lang.String currentlocale);
    
    /**
     * Sets (as xml) the "currentlocale" element
     */
    void xsetCurrentlocale(org.apache.xmlbeans.XmlString currentlocale);
    
    /**
     * Nils the "currentlocale" element
     */
    void setNilCurrentlocale();
    
    /**
     * Unsets the "currentlocale" element
     */
    void unsetCurrentlocale();
    
    /**
     * Gets the "currenttimezone" element
     */
    java.lang.String getCurrenttimezone();
    
    /**
     * Gets (as xml) the "currenttimezone" element
     */
    org.apache.xmlbeans.XmlString xgetCurrenttimezone();
    
    /**
     * Tests for nil "currenttimezone" element
     */
    boolean isNilCurrenttimezone();
    
    /**
     * True if has "currenttimezone" element
     */
    boolean isSetCurrenttimezone();
    
    /**
     * Sets the "currenttimezone" element
     */
    void setCurrenttimezone(java.lang.String currenttimezone);
    
    /**
     * Sets (as xml) the "currenttimezone" element
     */
    void xsetCurrenttimezone(org.apache.xmlbeans.XmlString currenttimezone);
    
    /**
     * Nils the "currenttimezone" element
     */
    void setNilCurrenttimezone();
    
    /**
     * Unsets the "currenttimezone" element
     */
    void unsetCurrenttimezone();
    
    /**
     * Gets the "dateformat" element
     */
    com.bagnet.nettracer.tracing.db.xsd.NTDateFormat getDateformat();
    
    /**
     * Tests for nil "dateformat" element
     */
    boolean isNilDateformat();
    
    /**
     * True if has "dateformat" element
     */
    boolean isSetDateformat();
    
    /**
     * Sets the "dateformat" element
     */
    void setDateformat(com.bagnet.nettracer.tracing.db.xsd.NTDateFormat dateformat);
    
    /**
     * Appends and returns a new empty "dateformat" element
     */
    com.bagnet.nettracer.tracing.db.xsd.NTDateFormat addNewDateformat();
    
    /**
     * Nils the "dateformat" element
     */
    void setNilDateformat();
    
    /**
     * Unsets the "dateformat" element
     */
    void unsetDateformat();
    
    /**
     * Gets the "defaultcurrency" element
     */
    java.lang.String getDefaultcurrency();
    
    /**
     * Gets (as xml) the "defaultcurrency" element
     */
    org.apache.xmlbeans.XmlString xgetDefaultcurrency();
    
    /**
     * Tests for nil "defaultcurrency" element
     */
    boolean isNilDefaultcurrency();
    
    /**
     * True if has "defaultcurrency" element
     */
    boolean isSetDefaultcurrency();
    
    /**
     * Sets the "defaultcurrency" element
     */
    void setDefaultcurrency(java.lang.String defaultcurrency);
    
    /**
     * Sets (as xml) the "defaultcurrency" element
     */
    void xsetDefaultcurrency(org.apache.xmlbeans.XmlString defaultcurrency);
    
    /**
     * Nils the "defaultcurrency" element
     */
    void setNilDefaultcurrency();
    
    /**
     * Unsets the "defaultcurrency" element
     */
    void unsetDefaultcurrency();
    
    /**
     * Gets the "defaultlocale" element
     */
    java.lang.String getDefaultlocale();
    
    /**
     * Gets (as xml) the "defaultlocale" element
     */
    org.apache.xmlbeans.XmlString xgetDefaultlocale();
    
    /**
     * Tests for nil "defaultlocale" element
     */
    boolean isNilDefaultlocale();
    
    /**
     * True if has "defaultlocale" element
     */
    boolean isSetDefaultlocale();
    
    /**
     * Sets the "defaultlocale" element
     */
    void setDefaultlocale(java.lang.String defaultlocale);
    
    /**
     * Sets (as xml) the "defaultlocale" element
     */
    void xsetDefaultlocale(org.apache.xmlbeans.XmlString defaultlocale);
    
    /**
     * Nils the "defaultlocale" element
     */
    void setNilDefaultlocale();
    
    /**
     * Unsets the "defaultlocale" element
     */
    void unsetDefaultlocale();
    
    /**
     * Gets the "defaulttimezone" element
     */
    java.lang.String getDefaulttimezone();
    
    /**
     * Gets (as xml) the "defaulttimezone" element
     */
    org.apache.xmlbeans.XmlString xgetDefaulttimezone();
    
    /**
     * Tests for nil "defaulttimezone" element
     */
    boolean isNilDefaulttimezone();
    
    /**
     * True if has "defaulttimezone" element
     */
    boolean isSetDefaulttimezone();
    
    /**
     * Sets the "defaulttimezone" element
     */
    void setDefaulttimezone(java.lang.String defaulttimezone);
    
    /**
     * Sets (as xml) the "defaulttimezone" element
     */
    void xsetDefaulttimezone(org.apache.xmlbeans.XmlString defaulttimezone);
    
    /**
     * Nils the "defaulttimezone" element
     */
    void setNilDefaulttimezone();
    
    /**
     * Unsets the "defaulttimezone" element
     */
    void unsetDefaulttimezone();
    
    /**
     * Gets the "failed_logins" element
     */
    int getFailedLogins();
    
    /**
     * Gets (as xml) the "failed_logins" element
     */
    org.apache.xmlbeans.XmlInt xgetFailedLogins();
    
    /**
     * True if has "failed_logins" element
     */
    boolean isSetFailedLogins();
    
    /**
     * Sets the "failed_logins" element
     */
    void setFailedLogins(int failedLogins);
    
    /**
     * Sets (as xml) the "failed_logins" element
     */
    void xsetFailedLogins(org.apache.xmlbeans.XmlInt failedLogins);
    
    /**
     * Unsets the "failed_logins" element
     */
    void unsetFailedLogins();
    
    /**
     * Gets the "firstname" element
     */
    java.lang.String getFirstname();
    
    /**
     * Gets (as xml) the "firstname" element
     */
    org.apache.xmlbeans.XmlString xgetFirstname();
    
    /**
     * Tests for nil "firstname" element
     */
    boolean isNilFirstname();
    
    /**
     * True if has "firstname" element
     */
    boolean isSetFirstname();
    
    /**
     * Sets the "firstname" element
     */
    void setFirstname(java.lang.String firstname);
    
    /**
     * Sets (as xml) the "firstname" element
     */
    void xsetFirstname(org.apache.xmlbeans.XmlString firstname);
    
    /**
     * Nils the "firstname" element
     */
    void setNilFirstname();
    
    /**
     * Unsets the "firstname" element
     */
    void unsetFirstname();
    
    /**
     * Gets the "group" element
     */
    com.bagnet.nettracer.tracing.db.xsd.UserGroup getGroup();
    
    /**
     * Tests for nil "group" element
     */
    boolean isNilGroup();
    
    /**
     * True if has "group" element
     */
    boolean isSetGroup();
    
    /**
     * Sets the "group" element
     */
    void setGroup(com.bagnet.nettracer.tracing.db.xsd.UserGroup group);
    
    /**
     * Appends and returns a new empty "group" element
     */
    com.bagnet.nettracer.tracing.db.xsd.UserGroup addNewGroup();
    
    /**
     * Nils the "group" element
     */
    void setNilGroup();
    
    /**
     * Unsets the "group" element
     */
    void unsetGroup();
    
    /**
     * Gets the "initial" element
     */
    java.lang.String getInitial();
    
    /**
     * Gets (as xml) the "initial" element
     */
    org.apache.xmlbeans.XmlString xgetInitial();
    
    /**
     * Tests for nil "initial" element
     */
    boolean isNilInitial();
    
    /**
     * True if has "initial" element
     */
    boolean isSetInitial();
    
    /**
     * Sets the "initial" element
     */
    void setInitial(java.lang.String initial);
    
    /**
     * Sets (as xml) the "initial" element
     */
    void xsetInitial(org.apache.xmlbeans.XmlString initial);
    
    /**
     * Nils the "initial" element
     */
    void setNilInitial();
    
    /**
     * Unsets the "initial" element
     */
    void unsetInitial();
    
    /**
     * Gets the "is_online" element
     */
    int getIsOnline();
    
    /**
     * Gets (as xml) the "is_online" element
     */
    org.apache.xmlbeans.XmlInt xgetIsOnline();
    
    /**
     * True if has "is_online" element
     */
    boolean isSetIsOnline();
    
    /**
     * Sets the "is_online" element
     */
    void setIsOnline(int isOnline);
    
    /**
     * Sets (as xml) the "is_online" element
     */
    void xsetIsOnline(org.apache.xmlbeans.XmlInt isOnline);
    
    /**
     * Unsets the "is_online" element
     */
    void unsetIsOnline();
    
    /**
     * Gets the "is_wt_user" element
     */
    int getIsWtUser();
    
    /**
     * Gets (as xml) the "is_wt_user" element
     */
    org.apache.xmlbeans.XmlInt xgetIsWtUser();
    
    /**
     * True if has "is_wt_user" element
     */
    boolean isSetIsWtUser();
    
    /**
     * Sets the "is_wt_user" element
     */
    void setIsWtUser(int isWtUser);
    
    /**
     * Sets (as xml) the "is_wt_user" element
     */
    void xsetIsWtUser(org.apache.xmlbeans.XmlInt isWtUser);
    
    /**
     * Unsets the "is_wt_user" element
     */
    void unsetIsWtUser();
    
    /**
     * Gets the "last_logged_on" element
     */
    java.util.Calendar getLastLoggedOn();
    
    /**
     * Gets (as xml) the "last_logged_on" element
     */
    org.apache.xmlbeans.XmlDate xgetLastLoggedOn();
    
    /**
     * Tests for nil "last_logged_on" element
     */
    boolean isNilLastLoggedOn();
    
    /**
     * True if has "last_logged_on" element
     */
    boolean isSetLastLoggedOn();
    
    /**
     * Sets the "last_logged_on" element
     */
    void setLastLoggedOn(java.util.Calendar lastLoggedOn);
    
    /**
     * Sets (as xml) the "last_logged_on" element
     */
    void xsetLastLoggedOn(org.apache.xmlbeans.XmlDate lastLoggedOn);
    
    /**
     * Nils the "last_logged_on" element
     */
    void setNilLastLoggedOn();
    
    /**
     * Unsets the "last_logged_on" element
     */
    void unsetLastLoggedOn();
    
    /**
     * Gets the "last_pass_reset_date" element
     */
    java.util.Calendar getLastPassResetDate();
    
    /**
     * Gets (as xml) the "last_pass_reset_date" element
     */
    org.apache.xmlbeans.XmlDate xgetLastPassResetDate();
    
    /**
     * Tests for nil "last_pass_reset_date" element
     */
    boolean isNilLastPassResetDate();
    
    /**
     * True if has "last_pass_reset_date" element
     */
    boolean isSetLastPassResetDate();
    
    /**
     * Sets the "last_pass_reset_date" element
     */
    void setLastPassResetDate(java.util.Calendar lastPassResetDate);
    
    /**
     * Sets (as xml) the "last_pass_reset_date" element
     */
    void xsetLastPassResetDate(org.apache.xmlbeans.XmlDate lastPassResetDate);
    
    /**
     * Nils the "last_pass_reset_date" element
     */
    void setNilLastPassResetDate();
    
    /**
     * Unsets the "last_pass_reset_date" element
     */
    void unsetLastPassResetDate();
    
    /**
     * Gets the "lastname" element
     */
    java.lang.String getLastname();
    
    /**
     * Gets (as xml) the "lastname" element
     */
    org.apache.xmlbeans.XmlString xgetLastname();
    
    /**
     * Tests for nil "lastname" element
     */
    boolean isNilLastname();
    
    /**
     * True if has "lastname" element
     */
    boolean isSetLastname();
    
    /**
     * Sets the "lastname" element
     */
    void setLastname(java.lang.String lastname);
    
    /**
     * Sets (as xml) the "lastname" element
     */
    void xsetLastname(org.apache.xmlbeans.XmlString lastname);
    
    /**
     * Nils the "lastname" element
     */
    void setNilLastname();
    
    /**
     * Unsets the "lastname" element
     */
    void unsetLastname();
    
    /**
     * Gets the "max_ws_sessions" element
     */
    int getMaxWsSessions();
    
    /**
     * Gets (as xml) the "max_ws_sessions" element
     */
    org.apache.xmlbeans.XmlInt xgetMaxWsSessions();
    
    /**
     * True if has "max_ws_sessions" element
     */
    boolean isSetMaxWsSessions();
    
    /**
     * Sets the "max_ws_sessions" element
     */
    void setMaxWsSessions(int maxWsSessions);
    
    /**
     * Sets (as xml) the "max_ws_sessions" element
     */
    void xsetMaxWsSessions(org.apache.xmlbeans.XmlInt maxWsSessions);
    
    /**
     * Unsets the "max_ws_sessions" element
     */
    void unsetMaxWsSessions();
    
    /**
     * Gets the "mname" element
     */
    java.lang.String getMname();
    
    /**
     * Gets (as xml) the "mname" element
     */
    org.apache.xmlbeans.XmlString xgetMname();
    
    /**
     * Tests for nil "mname" element
     */
    boolean isNilMname();
    
    /**
     * True if has "mname" element
     */
    boolean isSetMname();
    
    /**
     * Sets the "mname" element
     */
    void setMname(java.lang.String mname);
    
    /**
     * Sets (as xml) the "mname" element
     */
    void xsetMname(org.apache.xmlbeans.XmlString mname);
    
    /**
     * Nils the "mname" element
     */
    void setNilMname();
    
    /**
     * Unsets the "mname" element
     */
    void unsetMname();
    
    /**
     * Gets the "password" element
     */
    java.lang.String getPassword();
    
    /**
     * Gets (as xml) the "password" element
     */
    org.apache.xmlbeans.XmlString xgetPassword();
    
    /**
     * Tests for nil "password" element
     */
    boolean isNilPassword();
    
    /**
     * True if has "password" element
     */
    boolean isSetPassword();
    
    /**
     * Sets the "password" element
     */
    void setPassword(java.lang.String password);
    
    /**
     * Sets (as xml) the "password" element
     */
    void xsetPassword(org.apache.xmlbeans.XmlString password);
    
    /**
     * Nils the "password" element
     */
    void setNilPassword();
    
    /**
     * Unsets the "password" element
     */
    void unsetPassword();
    
    /**
     * Gets the "reset_password" element
     */
    boolean getResetPassword();
    
    /**
     * Gets (as xml) the "reset_password" element
     */
    org.apache.xmlbeans.XmlBoolean xgetResetPassword();
    
    /**
     * True if has "reset_password" element
     */
    boolean isSetResetPassword();
    
    /**
     * Sets the "reset_password" element
     */
    void setResetPassword(boolean resetPassword);
    
    /**
     * Sets (as xml) the "reset_password" element
     */
    void xsetResetPassword(org.apache.xmlbeans.XmlBoolean resetPassword);
    
    /**
     * Unsets the "reset_password" element
     */
    void unsetResetPassword();
    
    /**
     * Gets the "shift" element
     */
    com.bagnet.nettracer.tracing.db.xsd.WorkShift getShift();
    
    /**
     * Tests for nil "shift" element
     */
    boolean isNilShift();
    
    /**
     * True if has "shift" element
     */
    boolean isSetShift();
    
    /**
     * Sets the "shift" element
     */
    void setShift(com.bagnet.nettracer.tracing.db.xsd.WorkShift shift);
    
    /**
     * Appends and returns a new empty "shift" element
     */
    com.bagnet.nettracer.tracing.db.xsd.WorkShift addNewShift();
    
    /**
     * Nils the "shift" element
     */
    void setNilShift();
    
    /**
     * Unsets the "shift" element
     */
    void unsetShift();
    
    /**
     * Gets the "station" element
     */
    com.bagnet.nettracer.tracing.db.xsd.Station getStation();
    
    /**
     * Tests for nil "station" element
     */
    boolean isNilStation();
    
    /**
     * True if has "station" element
     */
    boolean isSetStation();
    
    /**
     * Sets the "station" element
     */
    void setStation(com.bagnet.nettracer.tracing.db.xsd.Station station);
    
    /**
     * Appends and returns a new empty "station" element
     */
    com.bagnet.nettracer.tracing.db.xsd.Station addNewStation();
    
    /**
     * Nils the "station" element
     */
    void setNilStation();
    
    /**
     * Unsets the "station" element
     */
    void unsetStation();
    
    /**
     * Gets the "timeformat" element
     */
    com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat getTimeformat();
    
    /**
     * Tests for nil "timeformat" element
     */
    boolean isNilTimeformat();
    
    /**
     * True if has "timeformat" element
     */
    boolean isSetTimeformat();
    
    /**
     * Sets the "timeformat" element
     */
    void setTimeformat(com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat timeformat);
    
    /**
     * Appends and returns a new empty "timeformat" element
     */
    com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat addNewTimeformat();
    
    /**
     * Nils the "timeformat" element
     */
    void setNilTimeformat();
    
    /**
     * Unsets the "timeformat" element
     */
    void unsetTimeformat();
    
    /**
     * Gets the "timeout" element
     */
    int getTimeout();
    
    /**
     * Gets (as xml) the "timeout" element
     */
    org.apache.xmlbeans.XmlInt xgetTimeout();
    
    /**
     * True if has "timeout" element
     */
    boolean isSetTimeout();
    
    /**
     * Sets the "timeout" element
     */
    void setTimeout(int timeout);
    
    /**
     * Sets (as xml) the "timeout" element
     */
    void xsetTimeout(org.apache.xmlbeans.XmlInt timeout);
    
    /**
     * Unsets the "timeout" element
     */
    void unsetTimeout();
    
    /**
     * Gets the "usergroup_id" element
     */
    int getUsergroupId();
    
    /**
     * Gets (as xml) the "usergroup_id" element
     */
    org.apache.xmlbeans.XmlInt xgetUsergroupId();
    
    /**
     * True if has "usergroup_id" element
     */
    boolean isSetUsergroupId();
    
    /**
     * Sets the "usergroup_id" element
     */
    void setUsergroupId(int usergroupId);
    
    /**
     * Sets (as xml) the "usergroup_id" element
     */
    void xsetUsergroupId(org.apache.xmlbeans.XmlInt usergroupId);
    
    /**
     * Unsets the "usergroup_id" element
     */
    void unsetUsergroupId();
    
    /**
     * Gets the "username" element
     */
    java.lang.String getUsername();
    
    /**
     * Gets (as xml) the "username" element
     */
    org.apache.xmlbeans.XmlString xgetUsername();
    
    /**
     * Tests for nil "username" element
     */
    boolean isNilUsername();
    
    /**
     * True if has "username" element
     */
    boolean isSetUsername();
    
    /**
     * Sets the "username" element
     */
    void setUsername(java.lang.String username);
    
    /**
     * Sets (as xml) the "username" element
     */
    void xsetUsername(org.apache.xmlbeans.XmlString username);
    
    /**
     * Nils the "username" element
     */
    void setNilUsername();
    
    /**
     * Unsets the "username" element
     */
    void unsetUsername();
    
    /**
     * Gets the "web_enabled" element
     */
    boolean getWebEnabled();
    
    /**
     * Gets (as xml) the "web_enabled" element
     */
    org.apache.xmlbeans.XmlBoolean xgetWebEnabled();
    
    /**
     * True if has "web_enabled" element
     */
    boolean isSetWebEnabled();
    
    /**
     * Sets the "web_enabled" element
     */
    void setWebEnabled(boolean webEnabled);
    
    /**
     * Sets (as xml) the "web_enabled" element
     */
    void xsetWebEnabled(org.apache.xmlbeans.XmlBoolean webEnabled);
    
    /**
     * Unsets the "web_enabled" element
     */
    void unsetWebEnabled();
    
    /**
     * Gets the "ws_enabled" element
     */
    boolean getWsEnabled();
    
    /**
     * Gets (as xml) the "ws_enabled" element
     */
    org.apache.xmlbeans.XmlBoolean xgetWsEnabled();
    
    /**
     * True if has "ws_enabled" element
     */
    boolean isSetWsEnabled();
    
    /**
     * Sets the "ws_enabled" element
     */
    void setWsEnabled(boolean wsEnabled);
    
    /**
     * Sets (as xml) the "ws_enabled" element
     */
    void xsetWsEnabled(org.apache.xmlbeans.XmlBoolean wsEnabled);
    
    /**
     * Unsets the "ws_enabled" element
     */
    void unsetWsEnabled();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.tracing.db.xsd.Agent newInstance() {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Agent parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Agent) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
