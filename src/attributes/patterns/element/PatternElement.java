package attributes.patterns.element;

import mmlib4j.images.GrayScaleImage;

public abstract class PatternElement {
	public int x;
	public int y;
	
	public PatternElement(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract boolean compare(GrayScaleImage image, int px, int py);
	public abstract String getSymbol();
	
	protected int getValue(GrayScaleImage image, int x, int y) {
		int index = image.getIndex(x, y);
		if (index == -1)
			return -1;
		return image.getPixel(index);
	}
}
