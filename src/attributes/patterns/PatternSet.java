package attributes.patterns;

import java.util.ArrayList;
import java.util.List;

import mmlib4j.images.GrayScaleImage;

public class PatternSet {
	private List<Pattern> patterns;
	
	public PatternSet(Pattern ...patterns) {
		this.patterns = new ArrayList<Pattern>();
		for (Pattern p : patterns) 
			this.patterns.add(p);
	}

	public int count(GrayScaleImage image, int px, int py) {
		int numOfPatterns = 0;
		
		for (Pattern p : this.patterns) {
			if (p.match(image, px, py)) 
				numOfPatterns++;
		}
		
		return numOfPatterns;
	}
	
	public void printPatternSet() {
		for (Pattern p : this.patterns)
			System.out.println(p.getPatternString());
		
		System.out.println("---------------------------------");
	}
}
