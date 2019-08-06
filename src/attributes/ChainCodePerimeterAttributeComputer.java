package attributes;

import java.util.Hashtable;

import attributes.perimeter.PerimeterBasedInChainCode;
import mmlib4j.images.BinaryImage;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.ImageFactory;
import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class ChainCodePerimeterAttributeComputer implements Attribute {

	@Override
	public Hashtable<AttributeName, Double[]> computeAllAttribute(ConnectedFilteringByComponentTree tree) {
		Double[] perimeter = new Double[tree.getNumNode()];
		GrayScaleImage treeImage = tree.getInputImage();
		
		for (NodeCT node : tree.getListNodes()) {
			BinaryImage bimg = createImage(node, treeImage);
			perimeter[node.getId()] = calculatePerimeter(bimg);
		}
		
		Hashtable<AttributeName, Double[]> attr = new Hashtable<AttributeName, Double[]>();
		attr.put(AttributeName.Perimeter, perimeter);
		
		return attr;
	}
	
	
	private double calculatePerimeter(BinaryImage bImage) {
		PerimeterBasedInChainCode perimeterChainCode = new PerimeterBasedInChainCode(bImage);
		return perimeterChainCode.calculatePerimeter();
	}
	
	private BinaryImage createImage(NodeCT node, GrayScaleImage image) {
		int xmin = node.getXmin();
		int xmax = node.getXmax();
		int ymin = node.getYmin();
		int ymax = node.getYmax();
		
		BinaryImage bimg = ImageFactory.createBinaryImage(xmax-xmin+3, ymax-ymin+3);
		
		for (int p : node.getPixelsOfCC()) {
			int px = (p % image.getWidth()) - xmin+1;
			int py = (p / image.getWidth()) - ymin+1;
			bimg.setPixel(px, py, true);
		}
		
		return bimg;
	}
}
