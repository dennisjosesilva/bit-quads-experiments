package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;

public class BitQuadZero extends BitQuad {
	public BitQuadZero(int x, int y) {
		super(x, y);
	}
	
	public boolean compare(int px, int py, int componentGrayLevel, GrayScaleImage img) {
		return componentGrayLevel > getValue(img, px + x, py + y);
	}
}
