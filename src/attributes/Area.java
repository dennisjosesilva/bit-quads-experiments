package attributes;

import java.util.Hashtable;

import mmlib4j.representation.tree.componentTree.NodeCT;

public class Area implements IncrementallyComputableAttribute {
	
	private Double[] area; 
	
	public Area(int numNodes) {
		area = new Double[numNodes];
	}
	
	@Override
	public void preProcess(NodeCT node) {
		area[node.getId()] = (double)node.getNumCanonicalPixel();
	}

	@Override
	public void merge(NodeCT node, NodeCT child) {
		area[node.getId()] += area[child.getId()];
	}

	@Override
	public void postProcess(NodeCT node) {
		// It is not necessary for this method of area calculation.
	}

	@Override
	public Hashtable<AttributeName, Double[]> getComputedAttribute() {
		Hashtable<AttributeName, Double[]> computedAttributes = new Hashtable<AttributeName, Double[]>();
		computedAttributes.put(AttributeName.Area, area);
		return computedAttributes;
	}
}
