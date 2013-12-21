package com.bagnet.nettracer.tracing.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.email.EmailException;
import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.actions.salvage.SalvageSearchAction;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;

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
	
	private static boolean createAndSendEmail(Incident inc, Agent a, String emailName, String subjectKey, String realpath) throws InsufficientInformationException {

		if (inc == null) {
			throw new InsufficientInformationException(Incident.class);
		}
		if (a == null) {
			throw new InsufficientInformationException(Agent.class);
		}
		boolean success=true;

		String configpath = realpath + "/WEB-INF/classes/";
		String imagepath = realpath + "/deployment/main/images/nettracer/";
		String toemail = null;
		String passname = null;
		Passenger pa = (Passenger) inc.getPassenger_list().get(0);
		Address adr = pa.getAddress(0);
		toemail = adr.getEmail();
		passname = pa.getFirstname() + " " + pa.getLastname();

		HtmlEmail he = new HtmlEmail();
		// get email
		if (toemail != null && toemail.length() > 0) {

			// send email
			try {
				// HtmlEmail he = new HtmlEmail();
				he.setHostName(a.getStation().getCompany().getVariable().getEmail_host());
				he.setSmtpPort(a.getStation().getCompany().getVariable().getEmail_port());
				he.setFrom(a.getStation().getCompany().getVariable().getEmail_from());

				String currentLocale = inc.getLanguage();

				if (currentLocale == null || currentLocale.trim().length() < 1) {
					currentLocale = a.getCurrentlocale();
				}

				ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
				al.add(new InternetAddress(toemail));
				he.setTo(al);
				String bcc = a.getStation().getCompany().getVariable().getBlindEmail();
				if (bcc != null && bcc.trim().length() > 0) {
					List<InternetAddress> bccList = new ArrayList<InternetAddress>();
					bccList.add(new InternetAddress(bcc));
					he.setBcc(bccList);
				}

				MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

				String htmlFileName = emailName;
				boolean embedImage = true;

				HashMap<String, String> h = new HashMap<String, String>();
				h.put("PASS_NAME", passname);
				if (subjectKey != null && !subjectKey.equalsIgnoreCase("")) {
					he.setSubject(messages.getMessage(new Locale(currentLocale), subjectKey));
				} else {
					he.setSubject(messages.getMessage(new Locale(currentLocale), "email.subject",
							messages.getMessage(new Locale(currentLocale), "email.mishandled")));
				}
				
				if (currentLocale != null && !currentLocale.equalsIgnoreCase("en")) {
					htmlFileName = htmlFileName.replaceFirst("\\.html$", "_" + currentLocale.toLowerCase() + ".html");
				}

				// set embedded images
				String myEmailHeaderImage = TracingConstants.BANNER_IMAGE;
				String strImageLogoFromPropertiesFile = messages.getMessage(new Locale(currentLocale), "email.to.pax.header.image.file");
				if (strImageLogoFromPropertiesFile != null && strImageLogoFromPropertiesFile.equalsIgnoreCase("")) {
					myEmailHeaderImage = strImageLogoFromPropertiesFile;
				}

				if (embedImage) {
					String img1 = he.embed(new URL("file:/" + imagepath + myEmailHeaderImage), myEmailHeaderImage);
					h.put("BANNER_IMAGE", img1);
				}

				String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);

				// logger.error("entire html email:" + msg);

				if (msg != null) {
					he.setHtmlMsg(msg);
					he.setCharset("UTF-8");
					he.send();
				} else {
					logger.warn("Unable to send email because email HTML file was not parsed.");
					success=false;
				}

			} catch (Exception maile) {
				logger.error("Unable to send mail due to smtp error. " + maile);
				success=false;
			}
		}
		return success;
	}
	
	public static boolean sendIncidentActivityEmail(IncidentActivity ia, String realpath) throws InsufficientInformationException{
		if(ia==null){
			throw new InsufficientInformationException(IncidentActivity.class);
		}
		return createAndSendEmail(ia.getIncident(), ia.getAgent(), TracingConstants.REPORT_UPDATE_INFO_EMAIL, PropertyBMO.SUBJECT_INFO_UPDATE, realpath);
		
	}
	
}
