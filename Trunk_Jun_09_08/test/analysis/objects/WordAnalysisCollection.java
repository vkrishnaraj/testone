package analysis.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordAnalysisCollection {

	HashMap<String, Word> map = new HashMap<String, Word>(10000);
	ArrayList<Word> list = new ArrayList<Word>(10000);

	public Word getKey(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			Word word = new Word();
			word.setWord(key);
			map.put(key, word);
			list.add(word);
			return word;
		}
	}
	
	public List<Word> getList() {
		return list;
	}
	
	public int getSize() {
		return map.size();
	}
}
