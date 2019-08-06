package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;

public class BitQuadOne extends BitQuad {
	public BitQuadOne(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean compare(int px, int py, int componentGrayLevel, GrayScaleImage img) {
		return componentGrayLevel <= getValue(img, px + x, py + y);
	}
}
