package aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;
import java.util.regex.Pattern;

public class ISharesHttpClientTest {
	
	@Test
	public void keyGen(){
		String patt = "^\\d{14}[0-9A-F]{8}$";
		for (int i = 0; i < 100; i++){
			Assert.assertTrue(Pattern.matches(patt,ISharesHttpClient.keyGen()));
			System.out.println(ISharesHttpClient.keyGen());
		}
	}
	
	@Test
	public void hmacHash(){
		//key	201009241812003851A59F
		//pass	test
		//ret	D280C2E8F3D2253F6D7464B9299DA936DCEC8BCD
		Assert.assertEquals("D280C2E8F3D2253F6D7464B9299DA936DCEC8BCD",
				ISharesHttpClient.hmacHash("201009241812003851A59F", "test"));
		
		//key	20100924181949B4A11C84
		//pass	Tuesday21!
		//ret	DCB99EAEB76F0E38DA3D781D965AFC3E08BC24BF
		Assert.assertEquals("DCB99EAEB76F0E38DA3D781D965AFC3E08BC24BF",
				ISharesHttpClient.hmacHash("20100924181949B4A11C84", "Tuesday21"));
		
		//key	20100924181844A957460C
		//pass	nalgene
		//ret	049538463ECBE05FEAA99D2AF58BC7525A0BE23B
		Assert.assertEquals("049538463ECBE05FEAA99D2AF58BC7525A0BE23B",
				ISharesHttpClient.hmacHash("20100924181844A957460C", "nalgene"));
		
		//key  20101215052601D9A75D9E
		//pass BOLSA3
		//ret  3B603ADA79A15F1B9A82B7851DD01E420649916F
		Assert.assertEquals("3B603ADA79A15F1B9A82B7851DD01E420649916F",
				ISharesHttpClient.hmacHash("20101215052601D9A75D9E", "BOLSA3"));
		System.out.println(ISharesHttpClient.hmacHash("20101215052601D9A75D9E", "BOLSA3"));
		
	}
}
