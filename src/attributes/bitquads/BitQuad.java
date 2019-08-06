package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;

public abstract class BitQuad {
	protected int x;
	protected int y;
	
	public BitQuad(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract boolean compare(int px, int py, int componentGrayLevel, GrayScaleImage img);
	
	protected int getValue(GrayScaleImage img, int px, int py) {
		if (img.isPixelValid(px, py))
			return img.getValue(px, py);
		
		return -1;
	}
}
