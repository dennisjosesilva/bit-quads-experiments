package attributes.bitquads;

import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class BitQuadCounter {
	private BitQuadSet B1;
	private BitQuadSet B2;
	private BitQuadSet B3;
	private BitQuadSet B4;
	private BitQuadSet BD;
	
	public BitQuadCounter() {
		B1 = new BitQuadSet(new BitQuadGroup(new BitQuadZero(1, 0), new BitQuadZero(1, 1), new BitQuadZero(0, 1)),
				new BitQuadGroup(new BitQuadZero(0, 1), new BitQuadZero(-1, 1), new BitQuadZero(-1, 0)), 
				new BitQuadGroup(new BitQuadZero(0, -1), new BitQuadZero(1, -1), new BitQuadZero(1, 0)),
				new BitQuadGroup(new BitQuadZero(-1, 0), new BitQuadZero(-1, -1), new BitQuadZero(0, -1)));
	
		B2 = new BitQuadSet(new BitQuadGroup(new BitQuadOne(1, 0), new BitQuadZero(1, 1), new BitQuadZero(0, 1)),
			new BitQuadGroup(new BitQuadOne(0, 1), new BitQuadZero(-1, 1), new BitQuadZero(-1, 0)),
			new BitQuadGroup(new BitQuadOne(-1, 0), new BitQuadZero(-1, -1), new BitQuadZero(0, -1)),
			new BitQuadGroup(new BitQuadOne(0, -1), new BitQuadZero(1, -1), new BitQuadZero(1, 0)));
	
		B3 = new BitQuadSet(new BitQuadGroup(new BitQuadOne(0, -1), new BitQuadOne(-1, -1), new BitQuadZero(-1, 0)),
			new BitQuadGroup(new BitQuadZero(-1, 0), new BitQuadOne(-1, 1), new BitQuadOne(0, 1)),
			new BitQuadGroup(new BitQuadOne(0, 1), new BitQuadOne(1, 1), new BitQuadZero(1, 0)),
			new BitQuadGroup(new BitQuadZero(1, 0), new BitQuadOne(1, -1), new BitQuadOne(0, -1)));
	
		B4 = new BitQuadSet(new BitQuadGroup(new BitQuadOne(0, -1), new BitQuadOne(1, -1), new BitQuadOne(1, 0)));
	
		BD = new BitQuadSet(new BitQuadGroup(new BitQuadZero(1, 0), new BitQuadOne(1, 1), new BitQuadZero(0, 1)),
			new BitQuadGroup(new BitQuadZero(0, -1), new BitQuadOne(1, -1), new BitQuadZero(1, 0)));
	}
	
	public BitQuadAttribute count(GrayScaleImage image, NodeCT node) {
		BitQuadAttribute counting = new BitQuadAttribute();
		
		for (int p : node.getPixelsOfCC()) {
			int px = p % image.getWidth();
			int py = p / image.getWidth();
			
			counting.nB1 += B1.countB(px, py, node.level, image);
			counting.nB2 += B2.countB(px, py, node.level, image);
			counting.nB3 += B3.countB(px, py, node.level, image);
			counting.nB4 += B4.countB(px, py, node.level, image);
			counting.nBD += BD.countB(px, py, node.level, image);
		}
		
		return counting;
	}
}

