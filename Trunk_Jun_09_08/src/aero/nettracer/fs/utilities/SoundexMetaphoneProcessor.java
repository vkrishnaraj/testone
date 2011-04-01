package aero.nettracer.fs.utilities;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;

public class SoundexMetaphoneProcessor {
	
	
	public static String getSoundex(String s) {
    Soundex sndx = new Soundex();
    return sndx.encode(s);
	}
	
	public static String getDoubleMetaphone(String s) {
		DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
    return doubleMetaphone.encode(s);
	}
	
	
}
