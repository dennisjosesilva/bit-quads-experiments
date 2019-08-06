package attributes.patterns;

import java.util.ArrayList;
import java.util.List;

import attributes.patterns.element.PatternElement;
import mmlib4j.images.GrayScaleImage;

public class Pattern {
	private List<PatternElement> patternElements;
	
	public Pattern(PatternElement ... patternElements) {
		this.patternElements = new ArrayList<PatternElement>();
		for (PatternElement e : patternElements)
			this.patternElements.add(e);
	}
	
	public void add(PatternElement patternElement) {
		this.patternElements.add(patternElement);
	}
	
	public boolean match(GrayScaleImage image, int x, int y) {
		for (PatternElement e : patternElements) {
			if (!e.compare(image, x, y))
				return false;
		}
		return true;
	}
	
	public String getPatternString() {
		String pattern[][] = new String[3][3];
		String text = "";
		for (PatternElement e: patternElements) {
			int j = 1 + e.x;
			int i = 1 + e.y;
			pattern[i][j] = e.getSymbol();
		}
		
		pattern[1][1] = "p";
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; j++) {
				if (pattern[i][j] != null)
					text += pattern[i][j] + " ";
			}
			text += "\n";
		}
		return text;
	}
}