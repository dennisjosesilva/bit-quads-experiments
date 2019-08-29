package programs;

import java.io.File;

import mmlib4j.images.BinaryImage;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.componentTree.ConnectedFilteringByComponentTree;
import mmlib4j.representation.tree.componentTree.NodeCT;
import mmlib4j.utils.AdjacencyRelation;
import mmlib4j.utils.ImageBuilder;

public class DrawCTNodes {

	private final static String PATH = "/home/dennis/Documents/phd/research-content/code/java/bechmark/perimeter-precision/nodes-ct/";
	
	public static void main(String args[]) {
		File file = new File("precision.png");
		AdjacencyRelation adj = AdjacencyRelation.getAdjacency8();
		GrayScaleImage img = ImageBuilder.openGrayImage(file);
		ConnectedFilteringByComponentTree tree = new ConnectedFilteringByComponentTree(img, adj, true);
		
		for(NodeCT node : tree.getListNodes()) {
			BinaryImage bimg = node.createImage();
			ImageBuilder.saveImage(bimg, new File(PATH + node.getId() + ".png"));
			System.out.println("DONE -> " + node.getId());
		}
	}
}
