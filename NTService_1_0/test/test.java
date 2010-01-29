import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.ContentRule;


public class test {
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	
//	@Test
//	public void  myTest() throws ParseException {
//		String v = "12NOV";
//		Locale defaultLocale = Locale.US;
//		GregorianCalendar x = new GregorianCalendar();
//		x.add(Calendar.MONTH, -x.get(Calendar.MONTH));
//		Date d = ITIN_DATE_FORMAT.parse(v);
//		GregorianCalendar y = new GregorianCalendar();
//		y.setTime(d);
//		x.set(Calendar.MONTH, y.get(Calendar.MONTH));
//		x.set(Calendar.DATE, y.get(Calendar.DATE));
//		x.set(Calendar.HOUR, 0);
//		x.set(Calendar.MINUTE, 0);
//		x.set(Calendar.SECOND, 0);
//		
//		System.out.println(x.getTime());
//		
//		
//	}

	
	@Test
	public void  myTest() throws ParseException {

		Content c1 = new Content();
		c1.setCategory("CATEGORY");
		c1.setDescription("DESCRIPTION /X1/X/1/2/X/3.\\#\" :");
		
		Content[] content = new Content[1];
		content[0] = c1;
		int bagCount = 0;
		Item i = new Item();
		i.setContent(content);
		if (i.getContent() != null) {

			Map<String, List<String>> temp = new HashMap<String, List<String>>();
			for (Content inv : i.getContent()) {
				String category = inv.getCategory();
				String contents = inv.getDescription().trim().toUpperCase();
				if (category == null || contents == null || category.trim().length() == 0
						|| contents.trim().length() == 0)
					continue;
				if (temp.get(category) == null) {
					temp.put(category, new ArrayList<String>());
				}
				temp.get(category).add(contents);
			}
			if (temp.size() > 0) {
				String entry = ContentRule.buildEntry(temp, bagCount);
				if (entry != null) {
					System.out.println(entry);
					bagCount += 1;
				}
			}
		}
	}

}
