package attributes;

import java.util.Hashtable;

import attributes.patterns.Pattern;
import attributes.patterns.PatternSet;
import attributes.patterns.element.PatternElementLowerG;
import attributes.patterns.element.PatternElementLowerL;
import attributes.patterns.element.PatternElementUpperG;
import attributes.patterns.element.PatternElementUpperL;
import mmlib4j.datastruct.SimpleLinkedList;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.PixelIndexer;
import mmlib4j.representation.tree.componentTree.NodeCT;

public abstract class ICIPAttributeComputer implements IncrementallyComputableAttribute {

	protected int[] nQ1;
	protected int[] nQ2;
	protected int[] nQ3;
	protected int[] nQ4;
	protected int[] nQD;
	
	protected int[] nQ1T;
	protected int[] nQ2T;
	protected int[] nQ3T;
	protected int[] nQDT;
	
	protected GrayScaleImage image;
	
	protected Double[] eulerNumber;
	protected Double[] area;
	protected Double[] perimeter;
	
	protected PatternSet pQ1;
	protected PatternSet pQ2;
	protected PatternSet pQ3;
	protected PatternSet pQ4;
	protected PatternSet pQD;
	
	protected PatternSet pQ1T;
	protected PatternSet pQ2T;
	protected PatternSet pQ3T;
	protected PatternSet pQDT;
	
	public ICIPAttributeComputer(GrayScaleImage image, int numOfNode) {
		this.image = image;
		this.image.setPixelIndexer(PixelIndexer.getDefaultValueIndexer(this.image.getWidth(), this.image.getHeight()));
		
		eulerNumber = new Double[numOfNode];
		area = new Double[numOfNode];
		perimeter = new Double[numOfNode];
		
		nQ1 = new int[numOfNode];
		nQ2 = new int[numOfNode];
		nQ3 = new int[numOfNode];
		nQ4 = new int[numOfNode];
		nQD = new int[numOfNode];
		
		nQ1T = new int[numOfNode];
		nQ2T = new int[numOfNode];
		nQ3T = new int[numOfNode];
		nQDT = new int[numOfNode];
		
		initPatterns();
	}
	
	private void initPatterns() {
		pQ1 = new PatternSet(new Pattern(new PatternElementUpperL(0, -1), new PatternElementUpperL(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperL(0, -1), new PatternElementUpperL(-1, -1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperL(-1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(1, 0), new PatternElementUpperL(1, 1), new PatternElementUpperL(0, 1)));
		
		pQ2 = new PatternSet(new Pattern(new PatternElementLowerG(1, 0), new PatternElementUpperL(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementLowerG(0, 1), new PatternElementUpperL(-1, 1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementLowerG(-1, 0), new PatternElementUpperL(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerG(0,-1), new PatternElementUpperL(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementUpperL(-1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperL(-1, -1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementUpperL(1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementUpperG(0, 1), new PatternElementUpperL(1, 0), new PatternElementUpperL(1, 1)));
		
		pQ3 = new PatternSet(new Pattern(new PatternElementUpperG(0, 1), new PatternElementUpperL(-1, 1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementUpperL(1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperL(1, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementUpperL(-1, -1), new PatternElementUpperG(0, -1)),
				new Pattern(new PatternElementLowerG(1, 0), new PatternElementUpperG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementLowerG(-1, 0), new PatternElementUpperG(-1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementLowerG(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerG(1, 0), new PatternElementUpperG(1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerG(0, -1), new PatternElementLowerG(-1, -1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementLowerG(0, -1), new PatternElementLowerG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementLowerG(0, 1), new PatternElementLowerG(1, 1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementLowerG(0, 1), new PatternElementLowerG(-1, 1), new PatternElementUpperL(-1, 0)));
		
		pQ4 = new PatternSet(new Pattern(new PatternElementLowerG(1, 0), new PatternElementLowerG(1, 1), new PatternElementLowerG(0, 1)),
				new Pattern(new PatternElementLowerG(0, 1), new PatternElementLowerG(-1, 1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementLowerG(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperG(0,  -1)),
				new Pattern(new PatternElementUpperG(0,-1), new PatternElementUpperG(1, -1), new PatternElementUpperG(1, 0)));
		
		pQD = new PatternSet(new Pattern(new PatternElementUpperL(1, 0), new PatternElementLowerG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(0,-1), new PatternElementLowerG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementUpperL(0, 1), new PatternElementUpperG(-1, 1), new PatternElementUpperL(-1, 0)));
		
		pQ1T = new PatternSet(new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperG(-1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(1, 0), new PatternElementUpperG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperL(0, -1), new PatternElementUpperG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperL(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerL(1, 0), new PatternElementUpperL(1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(0, 1), new PatternElementUpperL(-1, 1), new PatternElementLowerL(-1, 0)),
				new Pattern(new PatternElementLowerL(-1, 0), new PatternElementUpperL(-1, -1), new PatternElementUpperG(0, -1)),
				new Pattern(new PatternElementUpperG(0, -1), new PatternElementUpperL(1, -1), new PatternElementLowerL(1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementLowerL(-1, -1), new PatternElementLowerL(0, -1)),
				new Pattern(new PatternElementLowerL(0, -1), new PatternElementLowerL(1, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementLowerL(1, 1), new PatternElementLowerL(0, 1)),
				new Pattern(new PatternElementLowerL(0, 1), new PatternElementLowerL(-1, 1), new PatternElementUpperG(-1, 0)));
		
		pQ2T = new PatternSet(new Pattern(new PatternElementUpperG(0,-1), new PatternElementUpperG(1, -1), new PatternElementUpperL(1, 0)),
				new Pattern(new PatternElementUpperG(1,0), new PatternElementUpperG(1, 1), new PatternElementUpperL(0, 1)),
				new Pattern(new PatternElementUpperG(0, 1), new PatternElementUpperG(-1, 1), new PatternElementUpperL(-1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperL(0, -1)),
				new Pattern(new PatternElementLowerL(-1, 0), new PatternElementUpperG(-1, -1), new PatternElementUpperG(0, -1)),
				new Pattern(new PatternElementLowerL(0, -1), new PatternElementUpperG(1, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementLowerL(1, 0), new PatternElementUpperG(1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementLowerL(0, 1), new PatternElementUpperG(-1, 1), new PatternElementUpperG(-1, 0)));
		
		pQ3T = new PatternSet(new Pattern(new PatternElementUpperG(1,0), new PatternElementUpperG(1, -1), new PatternElementUpperG(0, -1)),
				new Pattern(new PatternElementUpperG(0,-1), new PatternElementUpperG(-1, -1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementUpperG(-1, 0), new PatternElementUpperG(-1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(1, 0), new PatternElementUpperG(1, 1), new PatternElementUpperG(0, 1)));
		
		pQDT = new PatternSet(new Pattern(new PatternElementUpperG(0,-1), new PatternElementUpperL(1, -1), new PatternElementUpperG(1, 0)),
				new Pattern(new PatternElementUpperG(1,0), new PatternElementUpperL(1, 1), new PatternElementUpperG(0, 1)),
				new Pattern(new PatternElementUpperG(0,1), new PatternElementLowerL(-1, 1), new PatternElementUpperG(-1, 0)),
				new Pattern(new PatternElementUpperG(-1,0), new PatternElementLowerL(-1, -1), new PatternElementUpperG(0, -1)));
	}
	
	
	@Override
	public void preProcess(NodeCT node) {
		SimpleLinkedList<Integer> pixels = node.getCanonicalPixels();
		int nodeId = node.getId();
		for (Integer p : pixels) {
			int px = p % image.getWidth();
			int py = p / image.getWidth();
			
			nQ1[nodeId] += pQ1.count(image, px, py);
			nQ2[nodeId] += pQ2.count(image, px, py);
			nQ3[nodeId] += pQ3.count(image, px, py);
			nQ4[nodeId] += pQ4.count(image, px, py);
			nQD[nodeId] += pQD.count(image, px, py);
			
			nQ1T[nodeId] += pQ1T.count(image, px, py);
			nQ2T[nodeId] += pQ2T.count(image, px, py);
			nQ3T[nodeId] += pQ3T.count(image, px, py);
			nQDT[nodeId] += pQDT.count(image, px, py);
		}
	}

	@Override
	public void merge(NodeCT node, NodeCT child) {
		int nodeId = node.getId();
		int childId = child.getId();
		
		nQ1[nodeId] += nQ1[childId];
		nQ2[nodeId] += nQ2[childId];
		nQ3[nodeId] += nQ3[childId];
		nQ4[nodeId] += nQ4[childId];
		nQD[nodeId] += nQD[childId];
	}

	@Override
	public void postProcess(NodeCT node) {
		int nodeId = node.getId();
		
		nQ1[nodeId] -= nQ1T[nodeId];
		nQ2[nodeId] -= nQ2T[nodeId];
		nQ3[nodeId] -= nQ3T[nodeId];
		nQD[nodeId] -= nQDT[nodeId];
		
		eulerNumber[nodeId] = eulerNumber(nodeId);
		area[nodeId] = area(nodeId);
		perimeter[nodeId] = perimeter(nodeId);
	}

	@Override
	public Hashtable<AttributeName, Double[]> getComputedAttribute() {
		Hashtable<AttributeName, Double[]> attrs = new Hashtable<AttributeName, Double[]>();
		attrs.put(AttributeName.Area, area);
		attrs.put(AttributeName.EulerNumber, eulerNumber);
		attrs.put(AttributeName.Perimeter, perimeter);
		return attrs;
	}
	
	public abstract double area(int nodeId);
	public abstract double perimeter(int nodeId);
	public abstract double eulerNumber(int nodeId);
}
