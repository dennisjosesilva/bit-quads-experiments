package attributes;

import mmlib4j.images.GrayScaleImage;

public class ICIPAttributeComputerContinuousAproxByDuda extends ICIPAttributeComputer {

	public ICIPAttributeComputerContinuousAproxByDuda(GrayScaleImage image, int numOfNode) {
		super(image, numOfNode);
	}
	
	@Override
	public double area(int nodeId) {
		return (0.25*nQ1[nodeId])+ (0.5*nQ2[nodeId]) + (0.875*nQ3[nodeId]) + nQ4[nodeId] + (0.75*nQD[nodeId]);
	}

	@Override
	public double perimeter(int nodeId) {		
		double invRoot2 = (1.0/Math.sqrt(2.0));
		return nQ2[nodeId] + (invRoot2*(nQ1[nodeId] + nQ3[nodeId] + (2.0*nQD[nodeId])));
	}

	@Override
	public double eulerNumber(int nodeId) {
		return 0.25*(nQ1[nodeId] - nQ3[nodeId] - (2.0*nQD[nodeId]));
	}
}
