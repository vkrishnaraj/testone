import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import au.com.bytecode.opencsv.CSVReader;

public class Test {

	public static void main(String[] args) {
		
		LinkedHashMap<String, ClaimDto> lhm = new LinkedHashMap<String, ClaimDto>();
		String fileName = null;
		String phoneNumbers = null;
		try {
			fileName = "2008 Claims.csv";
			phoneNumbers = "2008 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());
			
			fileName = "2009 Claims.csv";
			phoneNumbers = "2009 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());
			
			fileName = "2010 Claims.csv";
			phoneNumbers = "2010 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadClaimsFromFile(LinkedHashMap<String, ClaimDto> lhm, String fileName, String phoneNumbers) throws FileNotFoundException, IOException {
	  CSVReader reader = new CSVReader(new FileReader(fileName));

	  String[] line = new String[0];
	  while (line != null) {
	  	line = reader.readNext();
	  	if (line != null) {
	  		ClaimDto dto = new ClaimDto();
	  		int i = 0;
	  		dto.setReportType(line[++i]);
	  		dto.setId(line[++i]);
	  		dto.setFirstName(line[++i]);
	  		dto.setLastName(line[++i]);
	  		dto.setAddress1(line[++i]);
	  		dto.setAddress2(line[++i]);
	  		dto.setCity(line[++i]);
	  		dto.setState(line[++i]);
	  		dto.setZip(line[++i]);
	  		dto.setCountry(line[++i]);
	  		lhm.put(dto.getId(), dto);
	  	}
	  	
	  }
	  reader.close();
	  
	  reader = new CSVReader(new FileReader(phoneNumbers));

	  line = new String[0];
	  while (line != null) {
	  	line = reader.readNext();
	  	if (line != null) {
	  		String reportNumber = line[1];
	  		String phoneType = line[2];
	  		String phoneNumber = line[3] + line[4] + line[5];
	  		
	  		Phone phone = new Phone();
	  		phone.setNumber(phoneNumber);
	  		phone.setType(phoneType);
	  		try {
	  			ClaimDto dto = lhm.get(reportNumber);
	  			dto.getPhone().add(phone);
	  			if (dto.getPhone().size() > 1) {
	  				System.out.println("*** MORE THAN 1 PHONE: " + dto.getPhone().size());
	  				
	  			}
	  			
	  		} catch (Exception e) {
	  			System.out.println("Failed to load: " + reportNumber);
	  			e.printStackTrace();
	  		}
	  	}
	  	
	  }
	  reader.close();
	  	  
	  
	  System.out.println("Done loading claims and phone numbers for: " + fileName + " and " + phoneNumbers);
  }

}
