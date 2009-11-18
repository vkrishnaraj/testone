package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.wt.svc.WorldTracerConnection;

public class Test {
	public static void main(String[] args) {
		WorldTracerAccount acct = new WorldTracerAccount();
		
		acct.setUsername("");
		WorldTracerConnection conn = new WorldTracerConnection(acct, "", "TRAINING");
		
		
		StringBuffer x = new StringBuffer();
		x.append("								<span class=\"field\" style=\"padding-bottom: 10px;float: right;\">");
		x.append("									<img src=\"captcha.do?random=1253129766463\"");
		x.append("										id=\"captchaImage\" />");
		x.append("								</span>");
		
		String y = x.toString();
		String base = "https://wtrweb.worldtracer.aero/WorldTracerWeb/";
		
		Pattern rtPat = Pattern.compile("captcha.do\\?random=([0-9]*)");
		Matcher m1 = rtPat.matcher(y);
		if (m1.find()) {
			String captcha = m1.group();
			System.out.println(m1.group(0));
			System.out.println(m1.group(1));
			//System.out.println(base  + captcha);
		} else {
			System.out.println("no");
		}
		System.exit(0);

        FileOutputStream out; // declare a file output object
        PrintStream p; // declare a print stream object

        try
        {
                // Create a new file output stream
                // connected to "myfile.txt"
                out = new FileOutputStream("captcha.1253129766463.bmp");
                GetMethod login = new GetMethod("https://wtrweb.worldtracer.aero/WorldTracerWeb/captcha.do?random=1253129766463");
                conn.executeMethod(login);
                out.write(login.getResponseBody());
                // Connect print stream to the output stream
                //p = new PrintStream( out );
                

                //p.println ("This is written to a file");

                out.close();
        }
        catch (Exception e)
        {
                System.err.println ("Error writing to file");
        }
        
		System.exit(0);
		// https://wtrweb.worldtracer.aero/WorldTracerWeb/login.do
		//GetMethod login = new GetMethod("https://wtrweb.worldtracer.aero/WorldTracerWeb/login.do");
		GetMethod login = new GetMethod("https://wtrweb.worldtracer.aero/WorldTracerWeb/captcha.do?random=1253129766463");
		
		
		try {
			conn.executeMethod(login);
			String response = login.getResponseBodyAsString();
			System.out.println(response);
			System.out.println("Contains captcha.do: " + response.contains("captcha.do?"));
		} catch (HttpException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
