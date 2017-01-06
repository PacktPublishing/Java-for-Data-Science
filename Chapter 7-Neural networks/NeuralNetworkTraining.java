import java.text.*;

public class NeuralNetworkTraining {
	
	public static void main(String args[]){
		double xorIN[][] ={
					{0.0,0.0},
					{1.0,0.0},
					{0.0,1.0},
					{1.0,1.0}};

		double xorEXPECTED[][] = { {0.0},{1.0},{1.0},{0.0}};

		//System.out.println("Learn:");
		
		SampleNeuralNetwork network = new SampleNeuralNetwork(2,3,1,0.7,0.9);

//		NumberFormat percentFormat = NumberFormat.getPercentInstance();
//		percentFormat.setMinimumFractionDigits(4);


		for (int runCnt=0;runCnt<1000;runCnt++) {
			for (int loc=0;loc<xorIN.length;loc++) {
				network.calcOutput(xorIN[loc]);
				network.calcError(xorEXPECTED[loc]);
				network.train();
			}
			System.out.println("Trial #" + runCnt + ",Error:" + network.getError(xorIN.length));
			//System.out.println( "Trial #" + i + ",Error:" +
		//		percentFormat .format(network.getError(xorIN.length)) );
		}

//		System.out.println("Recall:");
//
//		for (int i=0;i<xorIN.length;i++) {
//
//			for (int j=0;j<xorIN[0].length;j++) {
//				System.out.print( xorIN[i][j] +":" );
//			}
//
//			double out[] = network.calcOutput(xorIN[i]);
//			System.out.println("="+out[0]);
//		}
	}
}