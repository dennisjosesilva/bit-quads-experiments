package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;

public class BitQuadSet {
	private BitQuadGroup[] bitquadGroups;
	
	public BitQuadSet(BitQuadGroup ...bitQuadGroups) {
		this.bitquadGroups = bitQuadGroups;
	}
	
	public int countB(int px, int py, int componentGrayLevel, GrayScaleImage img) {
		int counting = 0;
		
		for (BitQuadGroup b : bitquadGroups) {
			counting += b.match(px, py, componentGrayLevel, img) ? 1 : 0;
		}
		
		return counting;
	}
}
