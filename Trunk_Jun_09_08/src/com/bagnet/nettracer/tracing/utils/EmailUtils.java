package com.bagnet.nettracer.tracing.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.EmailException;
import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.actions.salvage.SalvageSearchAction;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;

public class EmailUtils {
	
	private static Logger logger = Logger.getLogger(SalvageSearchAction.class);

	public static void emailSalvageSummary(String filePath, Agent user, Salvage salvage) {
		if (filePath == null || filePath.isEmpty()) {
			return;
		}
		
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		
		String fileName = filePath.substring(filePath.lastIndexOf('/')+1);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		
		String from = resources.getString("email.salvage.from");
		Company company = CompanyBMO.getCompany(user.getCompanycode_ID());
		if (company.getEmail_address() != null && !company.getEmail_address().isEmpty()) {
			from = company.getEmail_address();
		}
		
		String msg = resources.getString("email.salvage.body") + " " + company.getCompanydesc() + ".\n\n";
		msg += resources.getString("email.salvage.body.salvageid") + " " + salvage.getSalvageId() + "\n";
		msg += resources.getString("email.salvage.body.salvagedate") + salvage.getDisSalvageDate(user.getDateformat().getFormat());
		
		try {
			Company_Specific_Variable compVar = AdminUtils.getCompVariable(company.getCompanyCode_ID());
			HtmlEmail email = new HtmlEmail();
			email.setHostName(compVar.getEmail_host());
			email.setSmtpPort(compVar.getEmail_port());
			email.setFrom(from);
			ArrayList<InternetAddress> toList = new ArrayList<InternetAddress>();
			toList.add(new InternetAddress(PropertyBMO.getValue("ocs.email")));
			email.setTo(toList);
			email.setSubject(fileName);
			email.setTextMsg(msg);
			email.setCharset("UTF-8");
			email.attach(new FileDataSource(file), fileName, "");
			email.send();
		} catch (EmailException ee) {
			logger.error(ee);
		} catch (AddressException ae) {
			logger.error(ae);
		}
		
	}
	
}
