package attributes;

import java.util.Hashtable;

import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;

public interface Attribute {
	Hashtable<AttributeName, Double[]> computeAllAttribute(ConnectedFilteringByComponentTree tree);
}
