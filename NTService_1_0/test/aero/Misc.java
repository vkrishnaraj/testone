package aero;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.ISharesResponseParser;

public class Misc {

	// ORIGINAL: private static final Pattern PATT_DXF =
	// Pattern.compile("^(\\d+)/(.*\\n)*(^\\s*$){1,}", Pattern.MULTILINE);
	private static final Pattern PATT_BEGIN = Pattern.compile("^\\d+/", Pattern.MULTILINE);
//	/private static final Pattern PATT_END = Pattern.compile("^\\d+/|^END OF REPORT|^\\&GT", Pattern.MULTILINE);
	private static final Pattern PATT_END = Pattern.compile("^\\d+/|^END OF REPORT|^\\&GT|^\\s*$^\\s*$", Pattern.MULTILINE);

	
	@Test
	public void quickTest() {
		String var = "UNDERWEAR/lots of bras underwear in plastic bag 1oran.-ge 1bk 1lavender / 1zebra pattern";
		
		System.out.println(var.indexOf("/"));
		System.out.println(var.substring(0,var.indexOf("/")));
		System.out.println(var.substring(var.indexOf("/") + 1));
	}
	
	@Test
	public void testThisNew() {
		StringBuffer sb = new StringBuffer();
		sb.append("4/TNT  TN US222222\n"); 
		sb.append("\n");
		sb.append("FWD XLFUS\n"); 
		sb.append("NM SMITH \n");
		sb.append("XT US222222 \n");
		sb.append("NR XAX \n");
		sb.append("NF US321/22JAN\n"); 
		sb.append("AG NTADMIN \n");
		sb.append("\n");
		sb.append("QOH XAXUS US222222 - CREATED 25JAN10/1825GMT\n"); 
		sb.append("END OF REPORT \n");
		
		List<ActionFile> afs = ISharesResponseParser.processActionFileDetail(sb.toString());
		for (ActionFile af: afs) {
			System.out.println(af.getSummary());
			System.out.println("==============");
		}
		System.out.println("==============");
	}
	
	@Test
	public void testThis3() throws CommandNotProperlyFormedException {

		StringBuffer sb = new StringBuffer();
		sb.append("7/07JAN10 2251GMT FROM DBCDL\n");
		sb.append("TPAUSAP/ATLDLAP \n");
		sb.append("PAX CREATED A FILE US  YYZUS00527093 PLS AHL FWD TO YYZ/\n");
		sb.append("THANKS \n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("ERASE AND DISPLAY NEXT  EXF\n");
		sb.append("TRANSFER TO FILE        TXF ... ...........\n");
		sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../\n");
		sb.append("&GTWMPN \n");
		
		List<ActionFile> afs = ISharesResponseParser.processActionFileDetail(sb.toString());
		for (ActionFile af: afs) {
			System.out.println(af.getSummary());
			System.out.println("==============");
		}

		
	}

	
	@Test
	public void testThis2() throws CommandNotProperlyFormedException {

		StringBuffer sb = new StringBuffer();
		sb.append("&GTWM ACTION FILE/T XCIUS  -AA- D1/4 \n");
		sb.append(" \n");
		sb.append("1/----ROH 25JAN10/1807GMT \n");
		sb.append("FORWARD OHD XCIUS10048 TO MATCHING FILE XAXUS10497 \n");
		sb.append("AG SHERRER \n");
		sb.append("FI TEST TEST \n");
		sb.append("SI TEST TEST \n");
		sb.append("TX HDQLZUS \n");
		sb.append(" \n");
		sb.append("2/----ROH 25JAN10/1819GMT \n");
		sb.append("FORWARD OHD XCIUS10048 TO MATCHING FILE XAXUS10497 \n");
		sb.append("AG SHERRER \n");
		sb.append("FI TEST FILE  TEST FILE \n");
		sb.append("SI TEST FILE  TEST FILE \n");
		sb.append("TX HDQLZUS \n");
		sb.append(" \n");
		sb.append("3/----ROH 25JAN10/1956GMT \n");
		sb.append("FORWARD OHD XCIUS10048 TO MATCHING FILE XAXUS10497 \n");
		sb.append("AG SHERRER \n");
		sb.append("FI PAX NEEDS \n");
		sb.append("SI PAX NEEDS \n");
		sb.append("TX HDQLZUS \n");
		sb.append("&GTWMPN \n");
		sb.append("\n");

		
		List<ActionFile> afs = ISharesResponseParser.processActionFileDetail(sb.toString());
		for (ActionFile af: afs) {
			System.out.println(af.getSummary());
			System.out.println("==============");
		}

		System.out.println("==============");
	}
	
}
