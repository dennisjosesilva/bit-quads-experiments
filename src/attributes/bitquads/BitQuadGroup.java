package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;

public class BitQuadGroup {
	private BitQuad[] bitquads;
	
	public BitQuadGroup(BitQuad ...bitQuads) {
		this.bitquads = bitQuads;
	}
	
	public boolean match(int px, int py, int componentGrayLevel, GrayScaleImage img) {
		if (!isForegroundPixel(px, py, componentGrayLevel, img))
			return false;
		
		for (BitQuad b : bitquads) {
			if (!b.compare(px, py, componentGrayLevel, img))
				return false;
		}
		return true;
	}
	
	private boolean isForegroundPixel(int px, int py, int componentGrayLevel, GrayScaleImage img) {
		if (img.isPixelValid(px, py))
			return img.getValue(px, py) >= componentGrayLevel;
			
		return false;
	}
}
