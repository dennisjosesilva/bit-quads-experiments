package programs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Hashtable;

import attributes.Area;
import attributes.AttributeName;
import attributes.BitQuadAttributeComputer;
import attributes.ChainCodePerimeterAttributeComputer;
import attributes.ClimentsNumberOfEuler;
import attributes.ICIPAttributeComputerGrayClassicalAttributes;
import attributes.IncrementalAttributeComputer;
import attributes.Perimeter;
import attributes.decisiontree.DecisionTreePatternCountAttributeComputer;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.utils.AdjacencyRelation;
import mmlib4j.utils.ImageBuilder;
import mmlib4j.utils.Utils;

public class CheckMethod {
	
	private static BufferedImage getImage(byte[] data, int width, int height) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		byte[] imageData = ((DataBufferByte)newImage.getRaster().getDataBuffer()).getData();
		
		for (int i = 0; i < imageData.length; i++)
		{
			imageData[i] = data[i];
		}
		
		return newImage;
	}
	
	
	public static void main(String[] args) throws IOException {
		byte[] values = {
				0,0,0,0,0,0,0,
				0,4,4,4,7,7,7,
				0,7,7,4,7,4,7,
				0,7,4,4,7,4,7,
				0,4,4,4,7,4,7,
				0,7,7,4,7,7,7,
				0,0,0,0,0,0,0
		};
		
		GrayScaleImage f = ImageBuilder.convertToGrayImage(getImage(values, 7, 7));
		ConnectedFilteringByComponentTree tree = 
				new ConnectedFilteringByComponentTree(f, AdjacencyRelation.getAdjacency8(), true);
		
		Utils.debug = false;
		
		System.out.println("---------------- DECISION TREE ALGORITHM -----------------------------------");
		IncrementalAttributeComputer optquads = new IncrementalAttributeComputer(
				new DecisionTreePatternCountAttributeComputer(f, tree.getNumNode()));
		
		Hashtable<AttributeName, Double[]> attr = optquads.computeAllAttribute(tree);
		Double[] euler = attr.get(AttributeName.EulerNumber);
		System.out.println("E(A) = " + euler[2] + "   E(B) = " + euler[3] + "   E(C) = " + euler[4] 
				+ "   E(D) = " + euler[1] + "    E(R) = " + euler[0]);
		System.out.println();
		Double[] area = attr.get(AttributeName.Area);
		System.out.println("A(A) = " + area[2] + "   A(B) = " + area[3] + "   A(C) = " + area[4] 
				+ "   A(D) = " + area[1] + "    A(R) = " + area[0]);
		System.out.println();
		Double[] perimeter = attr.get(AttributeName.Perimeter);
		System.out.println("P(A) = " + perimeter[2] + "   P(B) = " + perimeter[3] + "   P(C) = " + perimeter[4] 
				+ "   P(D) = " + perimeter[1] + "    P(R) = " + perimeter[0]);
		
		
		System.out.println("\n");
		System.out.println("-------------------- INCREMENTAL METHODS --------------------------------------------");
		IncrementalAttributeComputer im = new IncrementalAttributeComputer(
				new Area(tree.getNumNode()), 
				new Perimeter(tree.getInputImage(), tree.getNumNode()),
				new ClimentsNumberOfEuler(tree.getInputImage(), tree.getNumNode()));
		
		attr = im.computeAllAttribute(tree);
		euler = attr.get(AttributeName.EulerNumber);
		System.out.println("E(A) = " + euler[2] + "   E(B) = " + euler[3] + "   E(C) = " + euler[4] 
				+ "   E(D) = " + euler[1] + "    E(R) = " + euler[0]);
		System.out.println();
		area = attr.get(AttributeName.Area);
		System.out.println("A(A) = " + area[2] + "   A(B) = " + area[3] + "   A(C) = " + area[4] 
				+ "   A(D) = " + area[1] + "    A(R) = " + area[0]);
		System.out.println();
		perimeter = attr.get(AttributeName.Perimeter);
		System.out.println("P(A) = " + perimeter[2] + "   P(B) = " + perimeter[3] + "   P(C) = " + perimeter[4] 
				+ "   P(D) = " + perimeter[1] + "    P(R) = " + perimeter[0]);
		
		System.out.println("\n");
		System.out.println("-------------------- CLASSICAL QUADS (ICIP IMPLEMENTATION) --------------------------------------------");
		IncrementalAttributeComputer icip = new IncrementalAttributeComputer( 
				new ICIPAttributeComputerGrayClassicalAttributes(tree.getInputImage(), tree.getNumNode()));
		
		attr = icip.computeAllAttribute(tree);
		euler = attr.get(AttributeName.EulerNumber);
		System.out.println("E(A) = " + euler[2] + "   E(B) = " + euler[3] + "   E(C) = " + euler[4] 
				+ "   E(D) = " + euler[1] + "    E(R) = " + euler[0]);
		System.out.println();
		area = attr.get(AttributeName.Area);
		System.out.println("A(A) = " + area[2] + "   A(B) = " + area[3] + "   A(C) = " + area[4] 
				+ "   A(D) = " + area[1] + "    A(R) = " + area[0]);
		System.out.println();
		perimeter = attr.get(AttributeName.Perimeter);
		System.out.println("P(A) = " + perimeter[2] + "   P(B) = " + perimeter[3] + "   P(C) = " + perimeter[4] 
				+ "   P(D) = " + perimeter[1] + "    P(R) = " + perimeter[0]);
		
		System.out.println("\n");
		System.out.println("-------------------- NON-INCREMENTAL QUADS  --------------------------------------------");
		BitQuadAttributeComputer nonincr = new BitQuadAttributeComputer();
		
		attr = nonincr.computeAllAttribute(tree);
		euler = attr.get(AttributeName.EulerNumber);
		System.out.println("E(A) = " + euler[2] + "   E(B) = " + euler[3] + "   E(C) = " + euler[4] 
				+ "   E(D) = " + euler[1] + "    E(R) = " + euler[0]);
		System.out.println();
		area = attr.get(AttributeName.Area);
		System.out.println("A(A) = " + area[2] + "   A(B) = " + area[3] + "   A(C) = " + area[4] 
				+ "   A(D) = " + area[1] + "    A(R) = " + area[0]);
		System.out.println();
		perimeter = attr.get(AttributeName.Perimeter);
		System.out.println("P(A) = " + perimeter[2] + "   P(B) = " + perimeter[3] + "   P(C) = " + perimeter[4] 
				+ "   P(D) = " + perimeter[1] + "    P(R) = " + perimeter[0]);
		
	
		System.out.println("\n");
		System.out.println("-------------------- CHAIN CODE PERIMETER  --------------------------------------------");
		ChainCodePerimeterAttributeComputer chaincode = new ChainCodePerimeterAttributeComputer();
		
		attr = chaincode.computeAllAttribute(tree);
		perimeter = attr.get(AttributeName.Perimeter);
		System.out.println("P(A) = " + perimeter[2] + "   P(B) = " + perimeter[3] + "   P(C) = " + perimeter[4] 
				+ "   P(D) = " + perimeter[1] + "    P(R) = " + perimeter[0]);
	
		
	}
}
