import java.io.*;
import java.util.*;


public class CombineResources {

	public static void main(String[] args) {
		/*
		String defaultInput = "default.txt";
		String overrideInput = "override.txt";
		String output = "output.txt";
		 */
		
		String defaultInput = args[0];
		String overrideInput = args[1];
		String output = args[2];
		
		try {
			FileInputStream fstream1 = new FileInputStream(defaultInput);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(fstream1));
			Hashtable<String, Integer> dh = new Hashtable();
			ArrayList<String> da = new ArrayList();

			FileInputStream fstream2 = new FileInputStream(overrideInput);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(fstream2));
			ArrayList<String> oa = new ArrayList();
			
			ArrayList<String> append = new ArrayList();

			FileOutputStream out;
			PrintStream p;

			String tmp = null;
			int index = 0;
			int lineNum = 0;

			// Read in the default file to hashmap and array
			while ((tmp = in1.readLine()) != null) {
				tmp = tmp.trim();
				da.add(tmp);
				index = tmp.indexOf("=");
				if (index >= 0)
					dh.put(tmp.substring(0,index), new Integer(lineNum));
				lineNum++;
				//System.out.println(tmp);
			}
			
			// Read in the override file to hashmap and array
			lineNum = 0;
			while ((tmp = in2.readLine()) != null) {
				tmp = tmp.trim();
				oa.add(tmp);
				lineNum++;
				//System.out.println(tmp);
			}
			
			// Iterate through override file
			String key = null;
			for (int x = 0; x < oa.size(); ++x) {

				tmp = oa.get(x);
				index = tmp.indexOf("=");

				if (index >= 0) {
					key = tmp.substring(0,index);
					
					// 	If override key is in default hashmap
					if (dh.containsKey(key)) {
						// 	Update value in default array with override data
						Integer value = dh.get(key);
						da.set(value.intValue(), tmp);
					} else {
						append.add(tmp);
					}
					
				} else {
					append.add(tmp);
				}
			}

			// Iterate through what remains of override file
			for (int x = 0; x < append.size(); ++x) {
				// 	Append to default array
				da.add(append.get(x));
			}
			
			// Output the final file
			out = new FileOutputStream(output);
			p = new PrintStream(out);
			for (int x=0; x< da.size(); ++x)
				p.println(da.get(x));
			p.close();
			
		} catch (Exception e) {
			System.err.println("Error...");
			e.printStackTrace();
		}
	}
}
