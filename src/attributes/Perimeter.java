package attributes;

import java.util.Hashtable;

import mmlib4j.datastruct.SimpleLinkedList;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.PixelIndexer;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class Perimeter implements IncrementallyComputableAttribute {
	
	class Pixel {
		public int x;
		public int y;
		
		public Pixel(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private Double[] perimeter;
	private GrayScaleImage image;
	private Pixel[] displacementNeighborhood;
	
	public Perimeter(GrayScaleImage image, int numOfNodes) {
		this.image = image;
		this.perimeter = new Double[numOfNodes];
		image.setPixelIndexer(PixelIndexer.getDefaultValueIndexer(image.getWidth(), image.getHeight()));
		displacementNeighborhood = new Pixel[] { new Pixel(-1,0),  new Pixel(0,-1), new Pixel(1,0), new Pixel(0,1) };
	}
	
	@Override
	public void preProcess(NodeCT node) {
		SimpleLinkedList<Integer> pixels = node.getCanonicalPixels();
		perimeter[node.getId()] = 0.0;
		
		for (int p : pixels) {
			int px = p % image.getWidth();
			int py = p / image.getWidth();
			
			int lowerPixels = 0;
			int greaterPixels = 0;
			
			for (Pixel d : displacementNeighborhood) {
				int nx = d.x + px;
				int ny = d.y + py;
				
				if (getValue(image, px, py) < getValue(image, nx, ny))
					lowerPixels++;
				else if (getValue(image, px, py) > getValue(image, nx, ny))
					greaterPixels++;
			}
			perimeter[node.getId()] += greaterPixels - lowerPixels;
		}
	}

	private int getValue(GrayScaleImage image, int x, int y) {
		int index = image.getIndex(x, y);
		if (index < 0) 
			return -1;
		return image.getValue(index);
	}
	
	@Override
	public void merge(NodeCT node, NodeCT child) {
		perimeter[node.getId()] += perimeter[child.getId()];
	}

	@Override
	public void postProcess(NodeCT node) {
		// Intentionally left blank.
	}

	@Override
	public Hashtable<AttributeName, Double[]> getComputedAttribute() {
		Hashtable<AttributeName, Double[]> attr = new Hashtable<AttributeName, Double[]>();
		attr.put(AttributeName.Perimeter, perimeter);
		return attr;
	}

}
