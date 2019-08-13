package programs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import attributes.Area;
import attributes.Attribute;
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

public class TimeBenchmark {
	public static void main(String[] args) throws IOException {
		if (args.length < 3) {
			System.out.println("Argument Error: You should provide a method, an input directory, and output file");
			System.out.println("Valid values for 'method': ");
			System.out.println("\tnon_opt_quads");
			System.out.println("\topt_quads");
			System.out.println("\tnon_inc_bitquads");
			System.out.println("\tim");
			System.out.println("\tchaincode");
			
			System.exit(-1);
		}
		
		File folder = new File(args[1]);
		File[] files = folder.listFiles();
		String output = args[2];
		
		PrintWriter writer = new PrintWriter(output);
		
		// time in milliseconds
		writer.println("file;size;nnodes;time");
		int i = 0;
		
		for (File f: files) {
			if (f.isFile()) {
				compute(args[0], f, writer);
				i++;
				System.out.println(f.getName() + " Finalized - " + i + " out of " + files.length);
			}
		}
		writer.flush();
		writer.close();
	}
	
	private static Attribute getAttributeComputerMethod(String methodName, ConnectedFilteringByComponentTree tree) throws IOException {
		switch (methodName) {
		case "opt_quads":
			return new IncrementalAttributeComputer(
					new DecisionTreePatternCountAttributeComputer(tree.getInputImage(), tree.getNumNode()));
		
		case "non_opt_quads":
			return new IncrementalAttributeComputer( 
					new ICIPAttributeComputerGrayClassicalAttributes(tree.getInputImage(), tree.getNumNode()));
		
		case "non_inc_bitquads":
			return new BitQuadAttributeComputer();
			
		case "im":
			return new IncrementalAttributeComputer(
					new Area(tree.getNumNode()), 
					new Perimeter(tree.getInputImage(), tree.getNumNode()),
					new ClimentsNumberOfEuler(tree.getInputImage(), tree.getNumNode()));
		
		case "chaincode":
			return new ChainCodePerimeterAttributeComputer();
		
		default:
			break;
		}
		return null;
	}
	
	private static void compute(String methodName, File file, PrintWriter writer) throws IOException {
		Utils.debug = false;
		AdjacencyRelation adj = AdjacencyRelation.getAdjacency8();
		GrayScaleImage img = ImageBuilder.openGrayImage(file);
		ConnectedFilteringByComponentTree tree = new ConnectedFilteringByComponentTree(img, adj, true);
		Attribute attr = getAttributeComputerMethod(methodName, tree);
		
		long start = System.currentTimeMillis();
		attr.computeAllAttribute(tree);
		long end = System.currentTimeMillis();
		long duration = end - start;
		
		writer.println(file.getName() + ";" 
				+ img.getWidth() + "x" + img.getHeight() + ";"
				+ tree.getNumNode() + ";" 
				+ duration);
	}
}
