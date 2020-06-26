import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/** Detects straight lines in an inputed image and creates a new HoughTransform 
 * image
 * 
 * @author Dominic Flocco
 * 
 * 
 *
 */

public class HoughImage
{
	
	private int myWidth; 
	private int myHeight;
	private int[][] myArray; 
	private static final int NORMALIZED = 255;  

	/** Constructs an empty Array with a given width and height 
	 * 
	 * @param width - integer defining the width of the new array
	 * @param height - integer defining the height of the new array 
	 */
	public HoughImage(int width, int height) 
	{
		myWidth = width;
		myHeight = height;
		myArray = new int[width][height];
	}
	/** Constructs a new HoughImage object from the inputed image fileName
	 * 
	 * 
	 * @param fileName - String specifying inputed file name by HoughImage
	 * @throws IOException - throws exception out of the method
	 */
	public HoughImage(String fileName) throws IOException 
	{
		BufferedImage inputImage = ImageIO.read(new File(fileName));
		
		myWidth = inputImage.getWidth();
		myHeight = inputImage.getHeight();
		myArray = new int[myWidth][myHeight];
		
		for (int col = myWidth - 1; col >= 0; col--) 
	    {
			for (int row = myHeight - 1; row >= 0; row--)
	      {
	    	  int rgbValue = inputImage.getRGB(col,row);
	    	  
	    	  int redValue = ((rgbValue & 0xFF00000) >>> 16);
	    	  int blueValue = ((rgbValue & 0x0000FF));
	    	  int greenValue = ((rgbValue & 0x00FF00) >>> 8);
	    	  
	    	  int sumValue = redValue + blueValue + greenValue; 
	    	  
	    	  myArray[col][row] = sumValue; 
	      }
	    }
	}
	
	/** Creates a color value for a given pixel 
	 * 
	 * @param col - integer that defines the column number of a pixel in myArray
	 * @param row - integer that defines the row number of a pixel in myArray 
	 * @return - integer color value from the HoughImage at index (col, row)
	 */
	public int getValue(int col, int row) 
	{
		
		return myArray[col][row];
	}
	/** Defines the width of an inputed HoughImage 
	 * 
	 * @return the width of an inputed image
	 */
	public int getWidth() 
	{
		return myWidth;
	}
	/** Defines the height of an inputed HoughImage 
	 * 
	 * @return - integer height of an inputed image
	 */
	public int getHeight()
	{
		return myHeight;
	}
	/** Sets a value into HoughImage at a specific index and returns true if 
	 * the index exists. Returns false if the index does not exist.
	 * 
	 * @param col - integer that defines the column number of a pixel in myArray 
	 * @param row - integer that defines the row number of a pixel in myArray 
	 * @param value - inputed integer value to be set into HoughImage
	 * @return - boolean value to determine whether the index exists
	 */
	public boolean setValue(int col, int row, int value)
	{	
		if ((col >= 0) && (col < myWidth) && (row < myHeight) && (row >= 0)){ 
			myArray[col][row] = value;
			return true; }
		else { return false; } 	
	}
	/**  Stores the maximum integer number stored in the HoughImage
	 * 
	 * @return - maximum integer number stored in HoughImage
	 */
	public int getMax()
	{
		int maxValue = myArray[0][0]; 
		
		for (int col = myWidth - 1; col >= 0; col--) 
	    {
			for (int row = myHeight - 1; row >= 0; row--)
	      {
	    	  if (myArray[col][row] > maxValue) {
	    		  maxValue = myArray[col][row];
	    		  
	    	  }
	      }
		
	    } 
		return maxValue; 

	}
	/** Adds delta value in the HoughImage at a specific index
	 * 
	 * @param col - integer that defines the column number of a pixel in HoughImage
	 * @param row - integer that defines the row number of a pixel in HoughImage
	 * @param delta - integer to be added to HoughImage index
	 * @return - boolean value to determine whether the index exists
	 */
	public boolean accumulate(int col, int row, int delta)
	{
		if ((col >= 0) && (col < myWidth) && (row < myHeight) && (row >= 0)){ 
			myArray[col][row] += delta;
			return true; }
		else { return false; } 
		
	}
	/** Uses a for loop to determine whether any of the surrounding pixels are more than 
	 * MinContrast away from pixel at an index
	 * 
	 * @param col - integer that defines the column number of a pixel in HoughImage
	 * @param row - integer that defines the row number of a pixel in HoughImage
	 * @param minContrast - integer used to detect if the surrounding pixels create a line
	 * @return - boolean that indicates whether the surrounding pixel is more than 
	 * MinContrast away from pixel value at index
	 */
	public boolean contrast(int col, int row, int minContrast)
	{
		for (int c = col -1; c <= col + 1; c++ ) 
		{ 
			for (int r = row -1; r <= row + 1; r++ ) 
			{ 
				if ((c > 0) && (r > 0) && (r < myHeight) && (c < myWidth)) 
				{
					if (Math.abs((myArray[col][row] - myArray[c][r])) > (minContrast))
					{
					return true; 
					} 
				}
			}
		} 
		return false;	
	}
	/** Writes a file for the HoughTransform of HoughImage
	 * 
	 * @param fileName - string inputed by the user that specifies which image to transform
	 * @throws IOException - throws exception out of the method
	 */
	public void writeImage(String fileName) throws IOException
	{
		BufferedImage outputImage = new
		        BufferedImage(myWidth, myHeight, BufferedImage.TYPE_INT_ARGB);
		
		int maxValue = getMax(); 
		for (int col = myWidth - 1; col >= 0; col--) 
	    {
			for (int row = myHeight - 1; row >= 0; row--) {
	    	  int n = Math.floorDiv((myArray[col][row] * NORMALIZED), maxValue); 
	    	  outputImage.setRGB(col, row, (n<< 16)| (n << 8) | n & 0xFF | 0xFF000000);
	    	  
	      }
	    }
		ImageIO.write(outputImage, "PNG", new File(fileName));
	}

}