import java.io.IOException;

/**Transform an image from x-y image space into Hough space.
 * 
 * @author Tabitha Peck
 * 
 * Based on the Rosetta Code project.
 *
 */

public class HoughTransform {
	
	private int myThetaSize;
	private int myRAxisSize;
	private int myMinContrast;
	
	private double[] mySin;
	private double[] myCos;
	
	/**Construct a new HoughTransform object based on the parameters and define the 
	 * theta and radius resolutions, and build the trig tables from the resolutions.
	 * 
	 * @param thetaSize - integer defining the theta resolution of the hough space
	 * @param rAxisSize - integer defining the radius resolution of the hough space
	 * @param minContrast - integer defining the minimum contrast looked for in the image
	 */
	public HoughTransform(int thetaSize, int rAxisSize, int minContrast) {
		myThetaSize = thetaSize;
		myRAxisSize = rAxisSize;
		myMinContrast = minContrast;
		
		buildTrigTables();
	}
	
	
	/**Transform a HoughImage object into Hough Space and return the transformed HoughImage
	 * 
	 * @param inputData - a HoughImage
	 * @return a HoughImage of the inputData transformed into HoughSpace
	 */
	public HoughImage houghTransform(HoughImage inputData)
	  {
	    int width = inputData.getWidth();
	    int height = inputData.getHeight();
	    
	    int maxRadius = (int)Math.ceil(Math.hypot(width, height));
	    int halfRAxisSize = myRAxisSize >>> 1;//Can you figure out what is happening here?
	    	    
	    HoughImage outputData = new HoughImage(myThetaSize, myRAxisSize);
	     
	    for (int row = height - 1; row >= 0; row--)
	    {
	      for (int col = width - 1; col >= 0; col--)
	      {
	        if (inputData.contrast(col, row, myMinContrast))
	        {
	          for (int theta = myThetaSize - 1; theta >= 0; theta--)
	          {
	            double r = myCos[theta] * col + mySin[theta] * row;
	            int rScaled = (int)Math.round(r * halfRAxisSize / maxRadius) + halfRAxisSize;
	            outputData.accumulate(rScaled, theta, 1);
	          }
	        }
	      }
	    }

	    return outputData;
	  }
	
	
	/** Build the trig tables for sin and cos of resolution myThetaSize
	 * 
	 */
	private void buildTrigTables() {

	    mySin = new double[myThetaSize];
	    myCos = new double[myThetaSize];
	    for (int theta = 0; theta < myThetaSize; theta++)
	    {
	      double thetaRadians = theta * Math.PI / myThetaSize;
	      mySin[theta] = Math.sin(thetaRadians);
	      myCos[theta] = Math.cos(thetaRadians); 
	    }
	}
	
	
	/** The main tester function.
	 * 
	 * @param args
	 * @throws IOException - if the image file cannot be found
	 */
	public static void main(String[] args) throws IOException
	  {
		HoughImage inputData = new HoughImage("pentagon.png");
	    HoughTransform h1 = new HoughTransform(480, 480, 100);
	    HoughTransform h2 = new HoughTransform(480, 480, 20);
	    HoughImage outputData = h1.houghTransform(inputData);
	    HoughImage outputData2 = h2.houghTransform(inputData);
	    outputData.writeImage("pentagonHough.png");
	    outputData2.writeImage("pentagonHough2.png");
	  }
}
