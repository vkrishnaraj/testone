package analysis;

import java.util.Comparator;

import analysis.objects.Word;

public class IncidentWordFrequencyComparator implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		if (o1.getIncidentOccurances() < o2.getIncidentOccurances()) {
			return -1;
		} else if (o1.getIncidentOccurances() == o2.getIncidentOccurances()) {
			return 0;
		} else {
			return 1;
		}
	}

}
