package com.bagnet.nettracer.tracing;

import java.util.ArrayList;

import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;

import com.bagnet.nettracer.email.EmailException;
import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;


public class TestEmailUtils {

	@Test
	public void testEmailSalvageSummary() {
//		EmailUtils.emailSalvageSummary("C:\\reports\\tmp\\salvage_report_13_02242011084413.xls");
		String file = "C:\\reports\\tmp\\salvage_report_13_02242011084413.xls";
		String fileName = file.substring(file.lastIndexOf('\\')+1);
		String companyCode = "US";
		String from = "DONOTREPLY@nettracer.aero";
		try {
			Company company = CompanyBMO.getCompany(companyCode);
			if (company.getEmail_address() != null && !company.getEmail_address().isEmpty()) {
				from = company.getEmail_address();
			}
			
			String msg = "Salvage summary submitted.\n\n";
			msg +="Company:\t"+ company.getCompanydesc() +"\n";
			msg +="Salvage ID:\t 13\n";
			msg +="Salvage Date:\t02/25/2011";
			
			Company_Specific_Variable compVar = AdminUtils.getCompVariable(company.getCompanyCode_ID());
			HtmlEmail email = new HtmlEmail();
			email.setHostName(compVar.getEmail_host());
			email.setSmtpPort(compVar.getEmail_port());
			email.setFrom(from);
			ArrayList<InternetAddress> toList = new ArrayList<InternetAddress>();
			toList.add(new InternetAddress("msanders@nettracer.aero"));
			email.setTo(toList);
			email.setSubject(fileName);
			email.setTextMsg(msg);
			email.setCharset("UTF-8");
			email.attach(new FileDataSource(file), fileName, "");
			email.send();			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
