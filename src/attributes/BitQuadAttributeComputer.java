package attributes;

import java.util.Hashtable;

import attributes.bitquads.BitQuadAttribute;
import attributes.bitquads.BitQuadCounter;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class BitQuadAttributeComputer implements Attribute {

	private BitQuadCounter bitQuadCounter;
	
	public BitQuadAttributeComputer() {
		this.bitQuadCounter = new BitQuadCounter();
	}
	
	@Override
	public Hashtable<AttributeName, Double[]> computeAllAttribute(ConnectedFilteringByComponentTree tree) {
		Double[] area = new Double[tree.getNumNode()];
		Double[] perimeter = new Double[tree.getNumNode()];
		Double[] eulerNumber = new Double[tree.getNumNode()];
		
		GrayScaleImage image = tree.getInputImage();
		
		for (NodeCT node : tree.getListNodes()) {
			BitQuadAttribute bAttrs = bitQuadCounter.count(image, node);
			area[node.getId()] = bAttrs.computeArea();
			perimeter[node.getId()] = bAttrs.computePerimeter();
			eulerNumber[node.getId()] = bAttrs.computeNumberOfEuler();
		}
		
		Hashtable<AttributeName, Double[]> attrs = new Hashtable<AttributeName, Double[]>();
		attrs.put(AttributeName.Area, area);
		attrs.put(AttributeName.Perimeter, perimeter);
		attrs.put(AttributeName.EulerNumber, eulerNumber);
		
		return attrs;
	}
}
