import java.util.*;
import java.awt.Color;

/** Image segmenter class that uses DisjointSetForest in order to find and merge
 *  pixels based on their edge weights, recoloring the image based on their segments.
 * 
 * @author Dominic Flocco 
 * @author Henry Howell
 * 
 * Time Spent: 7 hours
 */
public class ImageSegmenter {
    public static SortedSet<Edge> edges;
    public static Color[][] segmentImage;
    public static DisjointSetForest forest;
    
    /** Performs an image segmenting algorithm to create a new segmented image. 
     * 
     * Creates a forest and a graph based on the given rgbArray and the weights of 
     * edges between pixels. Iterates through the edges and merges pixel segments 
     * based on the granularity constant with the help of the DisjointSetForest class. 
     * Then assigns a random color to each segment of the image.
     * 
     * @param rgbArray - given color array of the image to be segmented
     * @param granularity -  a number that controls the size of the neighborhood 
     * of the internal 
     * distance of the two segments
     * @return - segmented two dimensional color array
     */

    public static Color[][] segment(Color[][] rgbArray, double granularity) {
        // initializes the forest and the color[][] of the new segmented image
        forest = new DisjointSetForest(rgbArray);
        segmentImage = new Color[rgbArray.length][rgbArray[0].length];

        // create sorted list of edges
        constructGraph(rgbArray);
        // cycle through edges and join segments when necessary 
        for (Edge e : edges){
            // initialize endpoints of edge
            Pixel v1 = e.getFirstPixel();
            Pixel v2 = e.getSecondPixel();
            // find segments of each endpoint
            Pixel s1 = forest.find(v1); 
            Pixel s2 = forest.find(v2);
            // if the pixels are not in the same segment
            if (s1 != s2){
                // determine the size of segments
                int size1 = forest.toNode(s1).segSize;
                int size2 = forest.toNode(s2).segSize;
                
                // determines the internal distance of the segments
                double id1 = forest.toNode(s1).id;
                double id2 = forest.toNode(s2).id;
                if (e.getWeight() < Math.min(id1 + (granularity/size1), 
                    id2 + (granularity/size2))){
                    // merges the two segments when necessary
                    forest.union(s1, s2, e.getWeight());
                    
                }
            }    
        }

        changeColor(forest);
        
        return segmentImage; 
    }

    /** Helper function that changes the color of each pixel in the forest 
     * based on their segment. 
     * 
     * Iterates through all the pixels in the forest, assigning a random color 
     * accordingly. The method determines whether a given pixel is a root or a 
     * child of a segment and assigns children of a segment the same color as 
     * their root.
     * 
     * @param forest - DisjoingSetForest of pixels
     */

    public static void changeColor(DisjointSetForest forest){
        
        // retrieves segments of pixels from the forest
        Set<Pixel> pixels = forest.getSegments();
        ColorPicker colorGenerator = new ColorPicker();
        
        
        // find all the roots and change their colors
        for (Pixel p : pixels){
            int r = p.getRow();
            int c = p.getCol();
            
            // if p is a root 
            if (forest.isRoot(p)){
                // if p is a root and doesn't have a color
                if (segmentImage[r][c] == null){
                    Color randomColor = colorGenerator.nextColor();
                    // assign random color to root
                    segmentImage[r][c] = randomColor;
                }  
                // if p is a root and has a color, we don't need to do anything
            }
            // if p is not a root
            else if(!forest.isRoot(p)){
                Pixel root = forest.find(p);
                Color rootColor;
                // if the root doesn't have a color
                if (segmentImage[root.getRow()][root.getCol()] == null){
                    Color randomColor = colorGenerator.nextColor();
                    rootColor = randomColor;
                    // assign root a random color
                    segmentImage[root.getRow()][root.getCol()] = rootColor;
                    // assign p it's roots new random color
                    segmentImage[r][c] = rootColor;
                } 

                // if the root has a color
                else{
                    // assign p root's color
                    rootColor = segmentImage[root.getRow()][root.getCol()];
                    segmentImage[r][c] = rootColor;
                }
            }
        }
    }

    /** Constructs a graph of pixels by creating an edge between its neighboring
     * pixels in the pixel array. 
     * 
     * Iterates through the row and column of the pixel array and assigns edges between
     * neighboring pixels. Then, if the edge is not already in the sorted set of edges, 
     * it adds the edge to this set, which is sorted by weight. Uses RDELTA and CDELTA 
     * as constants for checking the surrounding neighbors.
     * 
     * @param array - two dimensional color array of original image
     */
    public static void constructGraph(Color[][] array){
        
        // constants used to test surrounding pixels
        final int[] RDELTA = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] CDELTA = {-1, 0, 1, -1, 1, -1, 0, 1};

        // retrieves pixel array from forest 
        Pixel[][] pixArray = forest.getPixelArray();
        // initializes sorted set of edges
        edges = new TreeSet<Edge>();

        // iterates through pixel array
        for (int i = 0; i < pixArray.length; i++){
            for (int j = 0; j < pixArray[0].length; j ++){
                // retrieves center pixel from array
                Pixel firstPix = pixArray[i][j];
                // iterates through eight possible neighboring positions
                for (int k = 0; k < RDELTA.length; k++) {
                    // tests if the neighbor is in bounds
                    if (i + RDELTA[k] >= 0 && i + RDELTA[k] < array.length 
                        && j + CDELTA[k] >= 0 && j + CDELTA[k] < array[0].length){
                        // retrieves neighboring pixel from array
                        Pixel secondPix = pixArray[i+RDELTA[k]][j+ CDELTA[k]];
                        // creates an edge between center pixel and its neighbor
                        Edge newEdge = new Edge(firstPix,secondPix);
                        // if this edge is not an edge in the sortedset, add to set
                        if (!edges.contains(newEdge)){
                            edges.add(newEdge);
                        }
                    }
                }
            }
        }
    }
}

