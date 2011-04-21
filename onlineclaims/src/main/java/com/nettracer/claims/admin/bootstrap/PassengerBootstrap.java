/**
 * 
 */
package com.nettracer.claims.admin.bootstrap;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.service.PaxViewService;

/**
 * @author utpal
 *
 */
public class PassengerBootstrap implements InitializingBean {
	private static Logger logger = Logger.getLogger(PassengerBootstrap.class);
	
	private static List<Localetext> loginPageList;

	@Autowired
	PaxViewService adminService;
	
	@Override
	public  void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		logger.info("afterPropertiesSet of  PassengerBootstrap is Invoked");
		
		loginPageList=adminService.getPassengerLoginContents("English-US"); //by default language is US-English

		logger.info("Size of loginPageList= "+loginPageList);
	}
	
	public static List<Localetext> getLoginPageList(){
		return loginPageList;
	}

}
