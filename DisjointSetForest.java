import java.util.*;
import java.awt.Color;

/** DisjointSetForest class that defines methods of DisjointSetForest 
 * and initializes the forest itself. 
 * 
 * @author Dominic Flocco 
 * @author Henry Howel
 * 
 * Time Spent: 4 hours
 * 
 */
public class DisjointSetForest{
    public Map<Pixel, Node> vertices;
    private ArrayList<Node> path;
    public Pixel[][] pixArray;
    
    /** Initializes forest based on the provided color array. 
     * 
     * Creates a map to effeciently assosiate pixels with their nodes and creates
     * a pixel array to effeciently find the location of pixels and their initial
     * color values. Uses the initial color array of the image to create a forest 
     * with each node as a single pixel in the array. Parent pointers are initially
     * null.
     * 
     * @param array - rgbArray of the image to be segmented
     */
    public DisjointSetForest(Color[][] array){
        // initialize vertices map and pixel array
        vertices = new HashMap<Pixel,Node>();
        pixArray = new Pixel[array.length][array[0].length];
        // loop through each entry in color array
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j ++){
                // create new pixel and new node
                Pixel pixel = new Pixel(i,j,array[i][j]);
                Node vertex = new Node(pixel);
                // add vertex to forest
                vertices.put(pixel, vertex);
                // add pixel to pixel array
                pixArray[i][j] = pixel;
            }
        }

    }
    /** Find the set in which an element belongs, which is represented by 
     * the root node. 
     * 
     * Uses the vertices map to find the corresponding node of the arbitrary pixel 
     * and then performs a recursive call to traverse up the tree to find the root 
     * of the segment in the forest using parent pointers. After the root node has 
     * been found, we compress the path by reassignin the parent pointers of all
     * nodes on the path to the root of the segment.
     * 
     * @param p - arbitrary pixel whose segment node is searching for
     * @return - the corresponding pixel of the root of the segment that contains 
     * the inputted pixel
     */
    public Pixel find(Pixel p){
        // initialize path compression
        path = new ArrayList<Node>();
        // finds segment of pixel by traversing up the tree
        Node root = find(vertices.get(p), path); 
        // path compression
        for (Node node : path){
            // the parent of all nodes in the path is the root of segment
            node.parent = root;
        }
        // return pixel value of root node
        return root.pixel;
    }

    /** Recursive helper function to the find(Pixel) method above. 
     * 
     * Performs recursion on the parent of the current node to traverse up the tree 
     * until a node without a parent is reached. Every node found on the path to the 
     * root is added to the path for path compression in the find(Pixel) method above. 
     * 
     * @param n - node corresponding to the pixel we want to find
     * @param path - arraylist of nodes of all nodes on the path to the root
     * @return - the root node of the segment
     */
    public Node find(Node n, ArrayList<Node> path){
        // base case: if parent is null we have reached a root
        if (n.parent == null){
            
            return n;
        }
        /*if (path.contains(n)){
            
            return n;
        }*/
        else{
            // add n to path for compression 
            path.add(n);
            // recursive call on parent
            return find(n.parent, path);
        }
        
    }
    /** Method that performs the union-by-rank algorithm.
     * 
     * Using rank, this method joins two nodes and their corresponding trees, 
     * or segments, in order to create a larger tree. It also keeps track of 
     * the segment length size in order to determine how many levels the tree 
     * has and thus how long the segment associated 
     * with the root node is.
     * 
     * @param n - the root pixel of the first segment to be merged
     * @param o - the root pixel of the second segment to be merged
     * @param weight - weight of the edge that joins the two segments
     */
    public void union(Pixel n, Pixel o, double weight){
        // find coresponding nodes to given pixels
        Node rootN = vertices.get(n);
        Node rootO = vertices.get(o);

        // if rootN rank is less than rootO, rootO is new root
        if (rootN.rank < rootO.rank){
            // update parent pointer
            rootN.parent = rootO;
            // update segement size
            rootO.segSize += rootN.segSize;
            // update id to be the weight of edge used to merge segments
            rootO.id = weight;
        }
        // if rootO rank is less than rootN, rootN is the new root
        else if (rootN.rank > rootO.rank){
            // update parent pointer
            rootO.parent = rootN;
            // update segement size
            rootN.segSize += rootO.segSize;
            // update id to be the weight of edge used to merge segments
            rootN.id = weight;
        }
        
        // if their ranks are equal, rootN is the new root and we update the rank of N
        else{
            // update parent pointer
            rootO.parent = rootN;
            // increase rank by 1 because they are equal
            rootN.rank ++;
            // update segement size
            rootN.segSize += rootO.segSize;
            // update id to be the weight of edge used to merge segments
            rootN.id = weight;

        }
    }
    /** Uses the map to find the corresponding node of a given pixel.
     * 
     * @param n - pixel whose node we find
     * @return - the node corresponding to this pixel
     */
    public Node toNode(Pixel n){
        return vertices.get(n);
    }

    /** Retreives a set of pixels our forest using the vertices map. 
     * 
     * @return - the set of pixels (keys) 
     */
    public Set<Pixel> getSegments(){
        return vertices.keySet(); 
    }

    /** Retreives the pixel array of the original image to easily access the 
     * color of the pixel.
     * 
     * @return - pixel array corresponding to our initial image
     * 
     */
    public Pixel[][] getPixelArray(){
        return pixArray;
    }

    /** Takes in a pixel and uses the map to find the corresponding node, then tests 
     * the parent pointer and returns true if it is the root of a segment.
     * 
     * @return - true if the pixel is the root of a segment.
     */
    public boolean isRoot(Pixel p){
        return (vertices.get(p).parent == null);
    }

    /** Internal node class that initializes several attributes of a node including
     * its parent, pixel, rank, segment size, and internal distance. Node's are used 
     * in the construction of our forest and segments.
     * 
     */
    public class Node{
        public Node parent;
        public Pixel pixel;
        public int rank;
        public int segSize;
        public double id;
       
        /** Initializes the attributes of the node class.
         * 
         * @param pixel - pixel value of the corresponding node
         */
        public Node(Pixel pixel){
            parent = null;
            this.pixel = pixel; 
            rank = 0;
            segSize = 1;
            id = 0;
            
        }
    } 
}