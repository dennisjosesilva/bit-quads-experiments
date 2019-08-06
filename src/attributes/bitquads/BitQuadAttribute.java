package attributes.bitquads;

public class BitQuadAttribute {
	public int nB1;
	public int nB2;
	public int nB3;
	public int nB4;
	public int nBD;
	
	public BitQuadAttribute() {
		nB1 = 0;
		nB2 = 0;
		nB3 = 0;
		nB4 = 0;
		nBD = 0;
	}
	
	public double computeArea() {
		return (nB1 + (2.0*nB2) + (2.0*nBD) + (3.0*nB3) + (4*nB4))/4.0;
	}
	
	public double computePerimeter() {
		return (nB1 + nB2 + (2.0*nBD) + nB3);
	}
	
	public double computeNumberOfEuler() {
		return (nB1 - nB3 - (2.0*nBD))/4.0;
	}
	
	public double computeCircularity() {
		return (4.0 * Math.PI * computeArea()) / (Math.pow(computePerimeter(), 2));
	}
}

