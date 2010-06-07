package com.nettracer.claims.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.dao.PassengerDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Localetext;

/**
 * @author David Antosh
 * @author utpal.patra
 * @Date: Nov 4, 2009 Description : Provides Services by calling Dao methods.
 *        Can be called from the Controllers.
 */
@Service
public class PassengerServiceImpl implements PassengerService {
	//private static Logger logger = Logger.getLogger(RequiredFieldsServiceImpl.class);
	
	@Autowired
	private PassengerDao passengerDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Localetext> getPassengerLoginContents(String languageSelected)
			throws SimplePersistenceException {
		return (List<Localetext>)passengerDao.getPassengerLoginContents(languageSelected);
	}

	public void setPassengerDao(PassengerDao passengerDao) {
		this.passengerDao = passengerDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	

	

}
