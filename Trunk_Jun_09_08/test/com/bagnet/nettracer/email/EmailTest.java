package com.bagnet.nettracer.email;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

import org.apache.struts.util.MessageResources;
import org.junit.Test;

import com.bagnet.nettracer.tracing.utils.EmailParser;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class EmailTest {

	@Test
	public void sendFrenchEmailLostDelayTest() {
		try {
			HtmlEmail he = new HtmlEmail();
			he.setHostName("192.168.2.128");
			he.setSmtpPort(25);

			he.setFrom("DONOTREPLY@testcase.nettracer.aero");

			String currentLocale = "en";

			ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
			al.add(new InternetAddress("bsmith@nettracer.aero"));
			he.setTo(al);

			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

			boolean embedImage = true;

			HashMap<String, Object> h = new HashMap<String, Object>();
			h.put("PASS_NAME", "Byron Smith");
			h.put("REPORT_TYPE", messages.getMessage(new Locale(currentLocale), "email.mishandled"));

			embedImage = !TracerProperties.isTrue(TracerProperties.EMAIL_REPORT_LD_DISABLE_IMAGE);
			he.setSubject(messages.getMessage(new Locale(currentLocale), "email.subject", messages.getMessage(
					new Locale(currentLocale), "email.mishandled")));


			h.put("REPORT_NUMBER", "YOWWS00000435");
			h.put("AIRLINE", "WestJet");

			h.put("CLAIM_CHECKS", "WS123456<BR>WS654321");

			// he.setSubject(messages.getMessage(new Locale(currentLocale),
			// "email.subject", messages.getMessage(new Locale(currentLocale),
			// "email.mishandled")));

			// he.setSubject("Report for your " + h.get("REPORT_TYPE") +
			// " has been filed.");

			// set embedded images
			if (embedImage) {
				String img1 = he.embed(new URL("file:/C:/work/ntmain/tracer-trunk/companies/westjet/web/deployment/main/images/nettracer/header_bg_noconf.gif"),
						"header_bg_noconf.gif");
				h.put("BANNER_IMAGE", img1);
			}

			String msg = EmailParser.parse("C:/work/ntmain/tracer-trunk/companies/westjet/environment/test/report_email_fr.html", h, currentLocale);

			if (msg != null) {
				he.setHtmlMsg(msg);
				he.setCharset("UTF-8");
				he.send();
			} else {
				System.out.println("Unable to send email because email HTML file was not parsed.");
			}

		} catch (Exception maile) {
			System.out.println("Unable to send mail due to smtp error. " + maile);
		}
	}
}
