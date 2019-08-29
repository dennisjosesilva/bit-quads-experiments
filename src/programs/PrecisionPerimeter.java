package programs;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import attributes.Area;
import attributes.Attribute;
import attributes.AttributeName;
import attributes.ChainCodePerimeterAttributeComputer;
import attributes.ClimentsNumberOfEuler;
import attributes.IncrementalAttributeComputer;
import attributes.Perimeter;
import attributes.decisiontree.DecisionTreePatternCountAttributeComputer;
import attributes.decisiontree.DecisionTreePatternCountContinuosAttributeComputer;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.representation.tree.componentTree.NodeCT;
import mmlib4j.utils.AdjacencyRelation;
import mmlib4j.utils.ImageBuilder;

public class PrecisionPerimeter {

	public static void main(String[] args) throws IOException {
		File file = new File("precision.png");
		AdjacencyRelation adj = AdjacencyRelation.getAdjacency8();
		GrayScaleImage img = ImageBuilder.openGrayImage(file);
		ConnectedFilteringByComponentTree tree = new ConnectedFilteringByComponentTree(img, adj, true);
		
		Attribute proposedMethodComputer = new IncrementalAttributeComputer(
				new DecisionTreePatternCountContinuosAttributeComputer(img, tree.getNumNode()));
		Attribute chaincode = new ChainCodePerimeterAttributeComputer();
		Attribute im = new IncrementalAttributeComputer(
				new Area(tree.getNumNode()), 
				new Perimeter(tree.getInputImage(), tree.getNumNode()),
				new ClimentsNumberOfEuler(tree.getInputImage(), tree.getNumNode()));
		
		PrintWriter writer = new PrintWriter("output-perimeter.csv");
		writer.println("node-id;PM-perimeter;PM-area;IM-perimeter;IM-area;CCM-perimeter;");
		
		Hashtable<AttributeName, Double[]> proposedMethod = proposedMethodComputer.computeAllAttribute(tree);
		Hashtable<AttributeName, Double[]> chaincodeMethod  = chaincode.computeAllAttribute(tree);
		Hashtable<AttributeName, Double[]> imMethod = im.computeAllAttribute(tree);
		
		for (NodeCT node : tree.getListNodes()) {
			int id = node.getId();
			writer.print(node.getId());
			writer.print(";" + proposedMethod.get(AttributeName.Perimeter)[id] + ";" + proposedMethod.get(AttributeName.Area)[id]);
			writer.print(";" + imMethod.get(AttributeName.Perimeter)[id] + ";" + imMethod.get(AttributeName.Area)[id]);
			writer.println(";" + chaincodeMethod.get(AttributeName.Perimeter)[id]);			
		}
		
		writer.close();
		System.out.println("Process finished");
	}
}
