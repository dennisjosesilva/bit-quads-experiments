package attributes.perimeter;

import mmlib4j.images.BinaryImage;
import mmlib4j.images.impl.PixelIndexer;

public class PerimeterBasedInChainCode {
	class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static final int BACKGROUND = 0;
	static final int FOREGROUND = 1;
	static final int START_LABEL = 2;
	static final int VISITED = -1;
	
	private int width;
	private int height;
	private int currentLabel;
	private int[][] labelArray;
	private int maxLabel;
	
	private int unitLineCounting;
	private int diagonalLineCounting;
	private BinaryImage image;
	
	public PerimeterBasedInChainCode(BinaryImage image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		labelArray = new int[width+2][height+2];
		unitLineCounting = diagonalLineCounting = 0;
		this.image.setPixelIndexer(PixelIndexer.getDefaultValueIndexer(width, height));
		initializeLabels();
	}
	
	private void initializeLabels() {
		labelArray = new int[width][height];
		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				labelArray[u][v] = image.getPixel(u, v) ? 1 : 0;
			}
		}
	}
	
	private boolean getPixel(int x, int y) {
		int index = image.getPixelIndexer().getIndex(x, y);
		if (index < 0) 
			return false;
		return image.getPixel(index);
	}
	
	private int getLabel(int u, int v) {
		if (u >= 0 && u < width && v >= 0 && v < height)
			return labelArray[u][v];
		else
			return BACKGROUND;
	}
	
	private void setLabel(int u, int v, int label) {
		if (u >= 0 && u < width && v >= 0 && v < height)
			labelArray[u][v] = label;
	}
	
	public double calculatePerimeter() {
		resetLabel();
		
		for (int v = 0; v < height; ++v) {
			int label = 0;
			for (int u = 0; u < width; ++u) {
				if (getPixel(u, v)) {
					if (label != 0)
						setLabel(u, v, label);
					else {
						label = getLabel(u, v);
						if (label == 0) {
							label = getNextLabel();
							traceContour(u, v, 0, label);
							setLabel(u, v, label);
						}
					}
				}
				else { // background pixel
					if (label != 0 ) {
						if (getLabel(u, v) == BACKGROUND) {
							traceContour(u-1, v, 1, label);
						}
						label = 0;
					}
				}
			}
		}
		return (0.95*(unitLineCounting + (Math.sqrt(2.0)*diagonalLineCounting)));
	}
	
	private void traceContour(int xS, int yS, int dS, int label) {
		int xT, yT;
		int xP, yP;
		int xC, yC;
		Point pt = new Point(xS, yS);
		int dNext = findNextPoint(pt, dS);
		addPixelOnPerimeter(dNext);
		xP = xS; yP = yS;
		xC = xT = pt.x;
		yC = yT = pt.y;
		
		boolean done = (xS == xT && yS == yT);
		while (!done) {
			setLabel(xC, yC, label);
			pt = new Point(xC, yC);
			int dSearch = (dNext + 6) % 8;
			dNext = findNextPoint(pt, dSearch);
			xP = xC; yP = yC;
			xC = pt.x; yC = pt.y;
			done = (xP == xS && yP == yS && xC == xT && yC == yT);
			if (!done)
				addPixelOnPerimeter(dNext);
		}
		
	}
	
	static final int[][] delta = {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}};
	
	private int findNextPoint(Point pt, int dir) {
		for (int i = 0; i < 7; i++) {
			int x = pt.x + delta[dir][0];
			int y = pt.y + delta[dir][1];
			
			if (!getPixel(x, y)) {
				setLabel(x, y, VISITED);
				dir = (dir + 1) % 8;
			}
			else {
				pt.x = x;
				pt.y = y;
				break;
			}	
		}
		return dir;
	}
	
	private void addPixelOnPerimeter(int dir) {
		if (dir % 2 == 0) // even direction
			unitLineCounting++;
		else
			diagonalLineCounting++;
	}
	
	private int getNextLabel() {
		if (currentLabel < 1) 
			currentLabel = START_LABEL;
		else
			currentLabel = currentLabel + 1;
		maxLabel = currentLabel;
		
		return currentLabel;
	}
	
	private void resetLabel() {
		currentLabel = -1;
		maxLabel = -1;
	}
}
