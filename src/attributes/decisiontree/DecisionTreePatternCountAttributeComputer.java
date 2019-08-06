package attributes.decisiontree;

import java.io.IOException;
import java.util.Hashtable;

import attributes.AttributeName;
import attributes.IncrementallyComputableAttribute;
import mmlib4j.datastruct.SimpleLinkedList;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.PixelIndexer;
import mmlib4j.representation.tree.componentTree.NodeCT;

public class DecisionTreePatternCountAttributeComputer implements IncrementallyComputableAttribute {
	
	protected int[] nP1;
	protected int[] nP2;
	protected int[] nP3;
	protected int[] nP4;
	protected int[] nPD;

	protected int[] nP1T;
	protected int[] nP2T;
	protected int[] nP3T;
	protected int[] nPDT;
	
	protected GrayScaleImage img;
	
	protected Double[] eulerNumber;
	protected Double[] area;
	protected Double[] perimeter;
	
	private PatternCounter patternCounter;
	
	public DecisionTreePatternCountAttributeComputer(GrayScaleImage image, int numOfNodes) throws IOException {
		this.img = image;
		this.img.setPixelIndexer(PixelIndexer.getDefaultValueIndexer(img.getWidth(), img.getHeight()));
		this.patternCounter = new PatternCounter();
		
		eulerNumber = new Double[numOfNodes];
		area = new Double[numOfNodes];
		perimeter = new Double[numOfNodes];
		
		nP1 = new int[numOfNodes];
		nP2 = new int[numOfNodes];
		nP3 = new int[numOfNodes];
		nP4 = new int[numOfNodes];
		nPD = new int[numOfNodes];
		
		nP1T = new int[numOfNodes];
		nP2T = new int[numOfNodes];
		nP3T = new int[numOfNodes];
		nPDT = new int[numOfNodes];
	}
	
	@Override
	public void preProcess(NodeCT node) {
		SimpleLinkedList<Integer> pixels = node.getCanonicalPixels();
		int nodeId = node.getId();
		for (Integer p : pixels) {
			int px = p % img.getWidth();
			int py = p / img.getWidth();
			byte[] counts = patternCounter.count(px, py, img);
			
			nP1[nodeId] += counts[PatternCounter.PatternType.P1.getValue()];
			nP2[nodeId] += counts[PatternCounter.PatternType.P2.getValue()];
			nP3[nodeId] += counts[PatternCounter.PatternType.P3.getValue()];
			nP4[nodeId] += counts[PatternCounter.PatternType.P4.getValue()];
			nPD[nodeId] += counts[PatternCounter.PatternType.PD.getValue()];
			
			nP1T[nodeId] += counts[PatternCounter.PatternType.P1T.getValue()];
			nP2T[nodeId] += counts[PatternCounter.PatternType.P2T.getValue()];
			nP3T[nodeId] += counts[PatternCounter.PatternType.P3T.getValue()];
			nPDT[nodeId] += counts[PatternCounter.PatternType.PDT.getValue()];
		}
	}

	@Override
	public void merge(NodeCT node, NodeCT child) {
		int nodeId = node.getId();
		int childId = child.getId();
		
		nP1[nodeId] += nP1[childId];
		nP2[nodeId] += nP2[childId];
		nP3[nodeId] += nP3[childId];
		nP4[nodeId] += nP4[childId];
		nPD[nodeId] += nPD[childId];
	}

	@Override
	public void postProcess(NodeCT node) {
		int nodeId = node.getId();
		
		nP1[nodeId] -= nP1T[nodeId];
		nP2[nodeId] -= nP2T[nodeId];
		nP3[nodeId] -= nP3T[nodeId];
		nPD[nodeId] -= nPDT[nodeId];
		
		eulerNumber[nodeId] = eulerNumber(nodeId);
		area[nodeId] = area(nodeId);
		perimeter[nodeId] = perimeter(nodeId);
	}

	protected double area(int nodeId) {
		return (nP1[nodeId] + (2.*nP2[nodeId]) + (2.*nPD[nodeId]) + (3.*nP3[nodeId]) + (4.*nP4[nodeId]))/4.0;
	}
	
	protected double perimeter(int nodeId) {
		return (nP1[nodeId] + nP2[nodeId] + (2.*nPD[nodeId]) + nP3[nodeId]);
	}
	
	protected double eulerNumber(int nodeId) {
		return (nP1[nodeId] - nP3[nodeId] - (2.*nPD[nodeId]))/4.0; 
	}
	
	@Override
	public Hashtable<AttributeName, Double[]> getComputedAttribute() {
		Hashtable<AttributeName, Double[]> attrs = new Hashtable<AttributeName, Double[]>();
		attrs.put(AttributeName.Area, area);
		attrs.put(AttributeName.EulerNumber, eulerNumber);
		attrs.put(AttributeName.Perimeter, perimeter);
		return attrs;
	}
}