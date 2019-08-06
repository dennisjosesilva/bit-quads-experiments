package attributes;

import mmlib4j.images.GrayScaleImage;

public class ICIPAttributeComputerContinuousAproxByGray extends ICIPAttributeComputer {

	public ICIPAttributeComputerContinuousAproxByGray(GrayScaleImage image, int numOfNode) {
		super(image, numOfNode);
	}
	
	@Override
	public double area(int nodeId) {
		return 0.25*((0.5*nQ1[nodeId]) + nQ2[nodeId] + nQD[nodeId] + (3.5*nQ3[nodeId]) + (4.0*nQ4[nodeId])) ;
	}

	@Override
	public double perimeter(int nodeId) {		
		double invRoot2 = (1.0/Math.sqrt(2.0));
		return nQ2[nodeId] + ((nQ1[nodeId] + nQ3[nodeId])*invRoot2);
	}

	@Override
	public double eulerNumber(int nodeId) {
		return 0.25*(nQ1[nodeId] - nQ3[nodeId] - (2.0*nQD[nodeId]));
	}
}
