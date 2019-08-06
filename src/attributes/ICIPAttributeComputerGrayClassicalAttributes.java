package attributes;

import mmlib4j.images.GrayScaleImage;

public class ICIPAttributeComputerGrayClassicalAttributes extends ICIPAttributeComputer {
	
	public ICIPAttributeComputerGrayClassicalAttributes(GrayScaleImage image, int numOfNode) {
		super(image, numOfNode);
	}

	@Override
	public double area(int nodeId) {
		return (nQ1[nodeId] + (2.0*nQ2[nodeId]) + (2.0*nQD[nodeId]) + (3.0*nQ3[nodeId]) + (4.0*nQ4[nodeId]))/4.0;
	}

	@Override
	public double perimeter(int nodeId) {
		return (nQ1[nodeId] + nQ2[nodeId] + (2.0*nQD[nodeId]) + nQ3[nodeId]);
	}

	@Override
	public double eulerNumber(int nodeId) {
		return (nQ1[nodeId] - nQ3[nodeId] - (2.0*nQD[nodeId]))/4.0;
	}

}
