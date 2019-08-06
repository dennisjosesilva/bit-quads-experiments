package attributes;

import java.util.Hashtable;

import mmlib4j.representation.tree.componentTree.NodeCT;

public interface IncrementallyComputableAttribute {
	public void preProcess(NodeCT node);
	public void merge(NodeCT node, NodeCT child);
	public void postProcess(NodeCT node);
	
	public Hashtable<AttributeName, Double[]> getComputedAttribute();
}
