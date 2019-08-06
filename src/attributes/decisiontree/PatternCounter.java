package attributes.decisiontree;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mmlib4j.images.GrayScaleImage;

/*This class expected a binary file named decision-tree.dat (contained data of the decision tree of the patterns) 
 * at 'resources' directory of the project*/
public class PatternCounter {
	public enum PatternType {
		P1(0), P2(1), P3(2), P4(3), PD(4), P1T(5), P2T(6), P3T(7), PDT(8);
		
		private final int value;
		
		private PatternType(int value) { this.value = value; }
		public int getValue() { return this.value; }
	}
	
	private final int N_LEAVES = 6561; // number of leaves in the decision tree (3**8)
	private final int N_PATTERNS_TYPE = 9; // pattern type = p1, p2, p3, p4, pd, p1t, p2t, p3t, pdt
	private final int[] dx = {-1,0,1,-1,1,-1,0,1};
	private final int[] dy = {-1,-1,-1,0,0,1,1,1};
	private byte[][] leavesMap;
	private char[] tempKey;
	
	public PatternCounter() throws IOException {
		InputStream in = PatternCounter.class.getResourceAsStream("dt-max-tree-8c.dat");
		leavesMap = new byte[N_LEAVES][];
		tempKey = new char[8];
		
		for (int i = 0; i < leavesMap.length; ++i) {
			leavesMap[i] = new byte[N_PATTERNS_TYPE];
			in.read(leavesMap[i]);
		}
	}
	
	private int getBased10IntFromABase3String(String base3string) {
		return Integer.parseInt(base3string, 3);
	}
	
	// 0 - lower than relation (<), 1 - equals relation (=), 2 - greater than relation (>)
	private char computeRelationType(int px, int py, int qx, int qy, GrayScaleImage img) {
		if (!img.isPixelValid(qx, qy)) // lower than
			return '0';
		else if (img.getValue(qx, qy) < img.getValue(px, py)) // lower than
			return '0';
		else if (img.getValue(qx, qy) > img.getValue(px, py)) // greater than
			return '2';
		else // equals relation
			return '1';
	}
	
	private int computeLeavesMapKey(int px, int py, GrayScaleImage img) {
		for (int i = 0; i < dx.length; i++)
			tempKey[i] = computeRelationType(px, py, px + dx[i], py + dy[i], img);
		
		return getBased10IntFromABase3String(new String(tempKey));
	}
	
	public byte[] count(int px, int py, GrayScaleImage img) {
		int key = computeLeavesMapKey(px, py, img);
		return leavesMap[key];
	}
}