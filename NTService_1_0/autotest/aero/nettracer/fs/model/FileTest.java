package aero.nettracer.fs.model;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class FileTest {
	@Test
	public void testPersonCache(){
		File file = new File();
		HashSet<Person> plist1 = new HashSet<Person>();
		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();
		plist1.add(p1);
		plist1.add(p2);
		HashSet<Person> plist2 = new HashSet<Person>();
		plist2.add(p3);
		file.setPersonCache(plist1, true);
		file.setPersonCache(plist2, false);
		
		assertTrue(file.getPersonCache(true).size() == 2);
		assertTrue(file.getPersonCache(false).size() == 1);
	}
}
