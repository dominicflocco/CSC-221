
import java.util.*;
import java.io.*;

/**
 * Implements Huffman Encoding compression scheme
 * 
 * @author Dominic Flocco
 * @author Henry Howell
 * 
 *         Time Spent: 10 hours
 * 
 */
public class HuffmanTree {
    public HuffmanNode root;

    /**
     * Constructs a Huffman coding tree using the given array of frequencies
     * 
     * This method implemnts the Huffman encoding sorting algorithm as described in
     * the supplementaty description. It uses the given array of frequencies to
     * first place each node into a forest and constructs the tree one subtree at a
     * time.
     * 
     * @param count - array of frequencies where count[i] is the number of
     *              occurences of the character with the ASCII value i.
     */

    public HuffmanTree(int[] count) {
        PriorityQueue<HuffmanNode> encodeTree = new PriorityQueue<HuffmanNode>();

        // creates a forest by adding nodes to encodeTree
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                HuffmanNode temp = new HuffmanNode(count[i], i);
                temp.left = null;
                temp.right = null;
                encodeTree.add(temp);
            }
        }
        // adds end of file marker
        int eof = count.length;
        encodeTree.add(new HuffmanNode(1, eof));

        while (encodeTree.size() > 1) {

            // retrieve and remove the two nodes with the smallest frequencies in tree
            HuffmanNode temp1 = encodeTree.poll();
            HuffmanNode temp2 = encodeTree.poll();
            // creates a new node with the sum of the frequencies
            HuffmanNode newNode = new HuffmanNode(temp1.freq + temp2.freq, -1);

            // assigns temp1 and temp2 as the children of the two smallest nodes
            newNode.left = temp1;
            newNode.right = temp2;
            // adds the new node to the tree
            encodeTree.add(newNode);
        }
        // assigns the root of the tree
        root = encodeTree.peek();
    }

    /**
     * Writes the Huffman tree to the supplied output stream.
     * 
     * This method calls on the recursive method traverse to perform a left to right
     * traversal on the tree. Once a leaf is reached it writes the bit and ASCII
     * value to the output stream in a readible format.
     * 
     * @param output - output stream that the ASCII and bit values are written to
     */

    public void write(PrintStream output) {
        traverse(output, root, "");

    }

    /**
     * Recursive helper function for the write method.
     * 
     * Performs a left to write traversal on the Huffman tree by using recursion to
     * traverse the tree. Each time a leaf is reached, the method writes the
     * corresponding ASCII value and bit value to the output stream. While the
     * method traverses the tree, it keeps track of the Huffman code of each
     * character.
     * 
     * @param output - output stream that the ASCII and bit values are written to
     * @param node   - current node in the Huffman tree during traversal
     * @param prefix - prefix of bit value that changes as each node is traversed
     */

    public void traverse(PrintStream output, HuffmanNode node, String prefix) {

        // base case
        if (node == null) {
            return;
        }
        // assign the current prefix to the bit of each leaf
        if (node.left == null && node.right == null) {
            node.bit = prefix;
            // add node to nodes list in left to right traversal order
            output.println(node.value);
            output.println(node.bit);
            return;
        }
        // traverse left
        traverse(output, node.left, prefix + "0");

        // traverse right
        traverse(output, node.right, prefix + "1");

    }

    /**
     * Constructs a Huffman tree from a file that contains the description of a tree
     * stored in the specific format.
     * 
     * Builds a Huffman tree for decoding purposes from an input file. The method is
     * very similar to HuffmanTree(int[]) above. This method first creates
     * HuffmanNodes and adds them to a forest, and then constructs the tree using
     * Huffman's encoding algorithm.
     * 
     * @param input - input file containing the description of Huffman tree to be
     *              decoded
     */

    public HuffmanTree(Scanner input) {
        // initializes root of tree
        root = new HuffmanNode(-1);
        HuffmanNode node = root;
        // reads file and builds huffman tree
        while (input.hasNextLine()) {
            int n = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            for (int i = 0; i < code.length(); i++) {
                char bit = code.charAt(i);
                if (bit == '0') { // if next bit is 0, go left
                    // if there is not a node at the left, make an interior one
                    if (node.left == null) { 
                        node.left = new HuffmanNode(-1);
                    }
                    node = node.left;
                } else if (bit == '1') { // if next bit is 1, go right
                    // if there is not a node at the right, make an interior one
                    if (node.right == null) {
                        node.right = new HuffmanNode(-1);
                    }
                    node = node.right;
                }
                // once we get to the end of the code, we have reached a leaf
                if (i == code.length() - 1) {
                    // assign correct value to leaf
                    node.value = n;
                    // go back to the root and repeat
                    node = root;
                }

            }
        }
    }

    /** Reads the individual bits from the input stream and writes teh corresponding 
     * characters to the supplied output stream 
     * 
     * Decodes the BitInputStream by visiting every leaf on the Huffman tree and writing
     * the corresponding character to the output stream, thus recreating the original body
     * ot text. This method uses the readBit() method from the BitInputStream class in 
     * order to find the approprate leaf by traversing either left or right.
     * 
     * @param input - input stream to be decoded
     * @param output - supplied output strean that decoded message is written to
     * @param eof - pseudo-EOF marker that marks the end of the file
     */

    public void decode(BitInputStream input, PrintStream output, int eof){
        HuffmanNode node = root;
        int value = -1;
        
        while(true){
            if (value == eof){
                break;
            }
            while (node.right != null || node.left != null){
                int bit = input.readBit();
                if (bit == 0){
                    node = node.left;
                }
                else if (bit == 1){
                    node = node.right;
                } 
            }
            value = node.value;
            output.write(value);
            node = root;
        }
    }
    /** HuffmanNode class used to store values in the HuffmanTree. 
     * 
     * This class implements Comparable to sort HuffmanNodes into a Priority Queue by 
     * their frequencies. The class has munltiple attributes: frequency, ASCII value and 
     * bit code. As well as left and right child pointers. 
     * 
     */
    private class HuffmanNode implements Comparable<HuffmanNode> {
        private int freq; 
        private int value; 
        private String bit;

        private HuffmanNode left; 
        private HuffmanNode right; 
      
        /** Initializes attributes of HuffmanNode class for encoding purposes
         * 
         * @param freq - frequency of character in text file 
         * @param value - ASCII value of character
         */
        public HuffmanNode(int freq, int value){
            this.freq = freq; 
            this.value = value;
            left = null;
            right = null;
         
        }
         /** Initializes attributes of HuffmanNode class for decoding purposes
         * 
         * @param value - ASCII value of character
         */
        public HuffmanNode(int value){
            
            this.value = value;
            left = null;
            right = null;
         
        }
        private int getFreq(){
            return freq;
        }
        
        private String getChar(){
            return Character.toString ((char) value);
        }
        /** Implements the comparable attribute of HuffmanNode class
         * 
         * First compares nodes by frequency and then leximologically
         * 
         * @param o - HuffmanNode to be compared to this
         */
        
        @Override
        public int compareTo(HuffmanNode o){
            int dif = this.freq - o.freq; 
            return dif;
        }
    }
}