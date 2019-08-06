package attributes.patterns.element;

import mmlib4j.images.GrayScaleImage;

public class PatternElementLowerL extends PatternElement {

	public PatternElementLowerL(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean compare(GrayScaleImage image, int px, int py) {
		return getValue(image, px, py) >= getValue(image, px + x, py + y);
	}

	@Override
	public String getSymbol() {
		return "l";
	}
	
}
