package attributes;

import java.util.Hashtable;

import attributes.patterns.PatternSet;
import attributes.patterns.Pattern;
import attributes.patterns.element.PatternElementLowerG;
import attributes.patterns.element.PatternElementLowerL;
import attributes.patterns.element.PatternElementUpperG;
import attributes.patterns.element.PatternElementUpperL;
import mmlib4j.datastruct.SimpleLinkedList;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.PixelIndexer;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class ClimentsNumberOfEuler implements IncrementallyComputableAttribute {
	private int[] nQ1;
	private int[] nQ2;
	private int[] nQ3;
	private int[] nQ4;

	private PatternSet pQ1;
	private PatternSet pQ2;
	private PatternSet pQ3;
	private PatternSet pQ4;
	
	GrayScaleImage image;
	
	private Double[] numberOfEuler;
	
	public ClimentsNumberOfEuler(GrayScaleImage image, int numOfNodes) {
		this.image = image;
		this.image.setPixelIndexer(PixelIndexer.getDefaultValueIndexer(this.image.getWidth(), this.image.getHeight()));
		nQ1 = new int[numOfNodes];
		nQ2 = new int[numOfNodes];
		nQ3 = new int[numOfNodes];
		nQ4 = new int[numOfNodes];
		
		numberOfEuler = new Double[numOfNodes];
		
		pQ1 = new PatternSet(new Pattern(new PatternElementUpperL(1,0), new PatternElementUpperL(1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementUpperL(0, -1), new PatternElementUpperL(-1, -1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperL(-1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(0, 1), new PatternElementUpperL(1, 1), new PatternElementUpperL(1, 0)));
		
		pQ2 = new PatternSet(new Pattern(new PatternElementUpperG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(0, 1), new PatternElementUpperG(-1, 1)),
				new Pattern(new PatternElementUpperG(-1,-1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementUpperL(0,-1), new PatternElementUpperG(1, -1)),
				new Pattern(new PatternElementLowerG(-1, -1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementLowerG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperL(1, 0), new PatternElementLowerG(1, 1)),
				new Pattern(new PatternElementLowerG(-1, 1), new PatternElementUpperL(-1, 0)));
		
		pQ3 = new PatternSet(new Pattern(new PatternElementUpperL(0,1), new PatternElementUpperG(-1, 1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementUpperL(1, 0), new PatternElementUpperG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(0,-1), new PatternElementUpperG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerL(1, 0), new PatternElementUpperL(1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(0, 1), new PatternElementUpperL(-1, 1), new PatternElementLowerL(-1, 0)),
				new Pattern(new PatternElementLowerL(-1, 0), new PatternElementUpperL(-1, -1), new PatternElementUpperG(0, -1)),
				new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperL(1, -1), new PatternElementLowerL(1, 0)),
				new Pattern(new PatternElementLowerL(0, -1), new PatternElementLowerL(-1, -1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementLowerL(0, -1), new PatternElementLowerL(1, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementLowerL(1, 1), new PatternElementLowerL(0, 1)),
				new Pattern(new PatternElementLowerL(0, 1), new PatternElementLowerL(-1, 1), new PatternElementUpperG(-1, 0)));
		
		pQ4 = new PatternSet(new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementUpperG(0, 1)));
	
	}
	
	@Override
	public void preProcess(NodeCT node) {
		SimpleLinkedList<Integer> pixels = node.getCanonicalPixels();
		for (int p : pixels) {
			int px = p % image.getWidth();
			int py = p / image.getWidth();
			int nodeId = node.getId();
			
			nQ1[nodeId] += pQ1.count(image, px, py);
			nQ2[nodeId] += pQ2.count(image, px, py);
			nQ3[nodeId] += pQ3.count(image, px, py);
			nQ4[nodeId] += pQ4.count(image, px, py);
		}
	}

	@Override
	public void merge(NodeCT node, NodeCT child) {
		int nodeId = node.getId();
		int childId = child.getId();
		
		nQ1[nodeId] += nQ1[childId];
		nQ2[nodeId] += nQ2[childId];
	}

	@Override
	public void postProcess(NodeCT node) {
		int nodeId = node.getId();
		nQ1[nodeId] -= nQ3[nodeId];
		nQ2[nodeId] -= nQ4[nodeId];
		
		numberOfEuler[nodeId] = ((nQ1[nodeId] - nQ2[nodeId]) / 4.0);
	}

	@Override
	public Hashtable<AttributeName, Double[]> getComputedAttribute() {
		Hashtable<AttributeName, Double[]> attr = new Hashtable<AttributeName, Double[]>();
		attr.put(AttributeName.EulerNumber, numberOfEuler);
		return attr;
	}
}
