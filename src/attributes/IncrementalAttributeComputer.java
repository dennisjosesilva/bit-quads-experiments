package attributes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class IncrementalAttributeComputer implements Attribute {
	private List<IncrementallyComputableAttribute> attributes;
	
	public IncrementalAttributeComputer(IncrementallyComputableAttribute ...attributes) {
		this.attributes = new ArrayList<IncrementallyComputableAttribute>();
		for (IncrementallyComputableAttribute attr : attributes)
			this.attributes.add(attr);
	}
	
	public IncrementalAttributeComputer addAttributeToCompute(IncrementallyComputableAttribute attribute) {
		attributes.add(attribute);
		return this;
	}
				
	@Override
	public Hashtable<AttributeName, Double[]> computeAllAttribute(ConnectedFilteringByComponentTree tree) {
		computeAttribute(tree.getRoot());
		Hashtable<AttributeName, Double[]> computedAttributes = new Hashtable<AttributeName, Double[]>();
		
		for (IncrementallyComputableAttribute attr : attributes)
			computedAttributes.putAll(attr.getComputedAttribute());
		
		return computedAttributes;
	}
	
	protected void computeAttribute(NodeCT node) {
		for (IncrementallyComputableAttribute attr : attributes)
			attr.preProcess(node);
		
		List<NodeCT> children = node.getChildren();
		for (NodeCT child : children) {
			computeAttribute(child);
			for (IncrementallyComputableAttribute attr : attributes)
				attr.merge(node, child);
		}
		
		for (IncrementallyComputableAttribute attr : attributes) 
			attr.postProcess(node);
	}
}
