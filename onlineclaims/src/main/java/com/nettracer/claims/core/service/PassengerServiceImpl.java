package com.nettracer.claims.core.service;

import java.util.List;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.dao.PassengerDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;

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

	@Override
	public List<Localetext> getPassengerDirection(String selectedLanguage)
			throws SimplePersistenceException {
		
		return passengerDao.getPassengerDirection(selectedLanguage);
	}

	@Override
	public MultilingualLabel getPassengerInfo(String selectedLanguage)
			throws SimplePersistenceException {
		// TODO Auto-generated method stub
		
		MultilingualLabel multilingualLabel=new MultilingualLabel();
		List<Localetext> localetextList=passengerDao.getPassengerInfo(selectedLanguage);
		if(localetextList != null){

			for(Localetext localetext:localetextList){
				if(localetext.getLabel().getLabel().contains("Permanent")){
					multilingualLabel.setPermanentAddress(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Help")){
					multilingualLabel.setPassengerInfoHelp(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Descriptive")){
					multilingualLabel.setPassengerInfoDescriptiveText(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Last")){
					multilingualLabel.setPassengerInfolastName(localetext.getDisplayText());
					multilingualLabel.setLastNameDelayedState(localetext.getLabel().getDelayedState().longValue());
				}else if(localetext.getLabel().getLabel().contains("First")){
					multilingualLabel.setFirstName(localetext.getDisplayText());
					multilingualLabel.setFirstNameDelayedState(localetext.getLabel().getDelayedState().longValue());
				}else if(localetext.getLabel().getLabel().contains("Middle")){
					multilingualLabel.setMiddleInitial(localetext.getDisplayText());
					multilingualLabel.setMiddleInitialDelayedState(localetext.getLabel().getDelayedState().longValue());
				}
				
			}
		}else{
			return null;
		}
		return multilingualLabel;
	}

	

	

}
