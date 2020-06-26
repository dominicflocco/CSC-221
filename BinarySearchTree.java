/**
 * Balanced binary search tree.
 */
public class BinarySearchTree {
	public Node root;

	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Updates the level of a node based on the levels
	 * of the left and right children.
	 * 
	 * @param currentRoot Node that will have its level updated.
	 */
	private void updateLevel(Node currentRoot) {
		Node child;
		if(currentRoot.left != null) {
			child = currentRoot.left;
		}
		else {
			child = currentRoot.right;
		}
		if (child != null){
			currentRoot.level = child.level + 1;
		}
		else{
			currentRoot.level = 0;
		}
		


	}

	/**
	 * Calculates the height skew of a node based on the levels
	 * of the left and right children.
	 * 
	 * @param currentRoot Node that will have its height skew calculated.
	 * 
	 * @return The height skew: the difference between the right subtree's level
	 *         minus the left subtree's level. Note that this is:
	 *         - a positive number if the right subtree has a higher level;
	 *         - a negative number if the left subtree has a higher level.
	 */
	private int calculateSkew(Node currentRoot) {
		return 1000000; // wow
	}

	/**
	 * Performs a left-side rotation around a top (parent) node and a pivot.
	 * 
	 * We expect that pivot is the right child of the top (parent) node.
	 * 
	 * @param top The top (parent) node.
	 * @param pivot The pivot node.
	 */
	void rotateLeft(Node top, Node pivot) {
	}

	/**
	 * Performs a right-side rotation around a top (parent) node and a pivot.
	 * 
	 * We expect that pivot is the left child of the top (parent) node.
	 * 
	 * @param top The top (parent) node.
	 * @param pivot The pivot node.
	 */
	void rotateRight(Node top, Node pivot) {
	}

	/**
	 * Rebalances a node by checking the height skew and performing
	 * the appropriate rotations if necessary.
	 * 
	 * @param currentRoot Node to be rebalanced.
	 */
	void rebalance(Node currentRoot) {
	}

	/**
	 * Adds an element into the tree.
	 * 
	 * @param value The element to be inserted in the tree.
	 * 
	 * @return True if the element was inserted; false if the value was already present.
	 */
	public boolean add(int value) {
		Node temp = new Node(value);

		if(root == null) {
			root = temp;

			return true;
		}
		else {

			return add(temp, root);
		}
	}

	/**
	 * Adds a node into a (non-null) subtree rooted at currentRoot.
	 * 
	 * @param node The node being inserted.
	 * @param currentRoot The root of the current subtree where we're adding the node.
	 * 
	 * @return True if the element was inserted; false if the value was already present.
	 */
	private boolean add(Node node, Node currentRoot) {
		updateLevel(currentRoot);
		if(node.value < currentRoot.value) {
			node.parent = currentRoot;
			
			if(currentRoot.left != null) {
				
				boolean result = add(node, currentRoot.left);
				
				return result;
			}
			else {
				currentRoot.left = node;

				return true;
			}
		}
		else if(node.value > currentRoot.value) {
			node.parent = currentRoot;
			
			if(currentRoot.right != null) {
				boolean result = add(node, currentRoot.right);
				

				return result;
			}
			else {
				currentRoot.right = node;

				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true if a given value is contained in the tree.
	 * 
	 * @param value The value being checked for containment.
	 * 
	 * @return True if the value is present; false otherwise.
	 */
	public boolean contains(int value) {
		return contains(value, root);
	}

	/**
	 * Checks if a value is contained in the a subtree rooted at currentRoot.
	 * 
	 * @param value The value being checked for containment.
	 * @param currentRoot The root of the current subtree where we're currently checking
	 *                    the value for containment.
	 * 
	 * @return True if the value is present; false otherwise.
	 */
	public boolean contains(int value, Node currentRoot) {
		if(currentRoot == null) {
			return false;
		}

		if(value == currentRoot.value) {
			return true;
		}
		else if(value < currentRoot.value) {
			return contains(value, currentRoot.left);
		}
		else {
			return contains(value, currentRoot.right);
		}
	}

	/**
	 * Returns the minimum value of the tree.
	 * 
	 * @return The minimum value of the tree, or -1 if the tree is empty.
	 */
	public int minimumValue() {
		if(root == null) {
			return -1;
		}

		Node minimumNode = minimumNode(root);

		return minimumNode.value;
	}

	/**
	 * Returns the node with the minimum key in the (non-null) subtree
	 * rooted at currentRoot.
	 * 
	 * @param currentRoot The root of the subtree that contains the minimum node.
	 * 
	 * @return The node with the minimum key in the (non-null) subtree
	 *         rooted at currentRoot.
	 */
	private Node minimumNode(Node currentRoot) {
		if(currentRoot.left != null) {
			return minimumNode(currentRoot.left);
		}

		return currentRoot;
	}

	/**
	 * Returns the maximum value of the tree.
	 * 
	 * @return The maximum value of the tree, or -1 if the tree is empty.
	 */
	public int maximumValue() {
		if(root == null) {
			return -1;
		}

		Node maximumNode = maximumNode(root);

		return maximumNode.value;
	}

	/**
	 * Returns the node with the maximum key in the (non-null) subtree
	 * rooted at currentRoot.
	 * 
	 * @param currentRoot The root of the subtree that contains the maximum node.
	 * 
	 * @return The node with the maximum key in the (non-null) subtree
	 *         rooted at currentRoot.
	 */
	private Node maximumNode(Node currentRoot) {
		if(currentRoot.right != null) {
			return maximumNode(currentRoot.right);
		}

		return currentRoot;
	}

	/**
	 * Removes an element from the tree.
	 * 
	 * @param value Value to be removed from the tree.
	 * 
	 * @return True if the value was removed; false if the value was not found.
	 */
	public boolean remove(int value) {
		
		return remove(value, root, null);
	}

	/**
	 * Removes an element from the tree.
	 * 
	 * @param value Value to be removed from the tree.
	 * @param currentRoot Root of the current subtree where we're removing the value.
	 * @param currentParent Parent of the currentRoot (null if currentRoot is the root).
	 * 
	 * @return True if the value was removed; false if the value was not found.
	 */
	private boolean remove(int value, Node currentRoot, Node currentParent) {

		if(currentRoot == null) {
			return false;
		}
		else if(value < currentRoot.value) {
			
			boolean result = remove(value, currentRoot.left, currentRoot);
			
			return result;
		}
		else if(value > currentRoot.value) {
			boolean result = remove(value, currentRoot.right, currentRoot);
			
			return result;
		}

		// When value == currentRoot.value...

		if(currentRoot.left != null && currentRoot.right != null) {
			int min = minimumNode(currentRoot.right).value;

			// This will always succeed because you know
			// that the element to be removed is present

			remove(min, currentRoot.right, currentRoot);

			currentRoot.value = min;

			return true;
		}

		Node child;

		if(currentRoot.left != null) {
			child = currentRoot.left;
			
		}
		else {
			child = currentRoot.right;
			
		}
		
		if(currentParent == null) {
			
			root = child;
		}
		else {
			if (child != null){
				child.parent = currentParent;
				
			}
			updateLevel(currentParent);
			
			if(currentParent.left == currentRoot) {
				
				currentParent.left = child;
			}
			else {
				
				currentParent.right = child;
			}
		}
		

		// Note that if both children were null,
		// the part above works too!

		return true;
	}


	 /** Returns true if and only if the parent links of all nodes are correct.
	 * 
	 * @return True iff the parent links of all nodes are correct.
	 * UNCOMMENT AFTER YOU IMPLEMENT THE PARENT LINKS
	 */
	private boolean testParentLinks() {
		if(root == null) {
			return true;
		}

		if(root.parent != null) {
			return false;
		}

		return testParentLinks(root);
	}

	
	 /** Helper method for the testParentLinks() function.
	 * Returns true if and only if the parent links of all nodes in the tree rooted at the specified node
	 * are correct.
	 * 
	 * @param currentRoot Root of the tree.
	 * @return True iff the parent links of all nodes in the tree rooted at currentRoot are correct.
	 * 
	 * UNCOMMENT AFTER YOU IMPLEMENT THE LEVELS
	 */
	 
	private boolean testParentLinks(Node currentRoot) {
		if(currentRoot == null) {
			return true;
		}

		if(currentRoot.left != null) {
			if(currentRoot.left.parent != currentRoot || !testParentLinks(currentRoot.left)) {
				return false;
			}
		}

		if(currentRoot.right != null) {
			if(currentRoot.right.parent != currentRoot || !testParentLinks(currentRoot.right)) {
				return false;
			}
		}

		return true;
	} 
	
    
    /**
     * Draws the tree starting at the root
     */
    public void drawTree(){
        drawTree(root, "");
    }
    
    /** Draws the tree following a pre-order traversal starting at
     * currentRoot
     *
     * @param currentRoot - The root of the tree to draw
     * @param space - The number of spaces to print before
     * printing the value of the node. All nodes on the same level
     * in the tree will be printed with the same indentation
     */
    public void drawTree(Node currentRoot, String space){
        if(currentRoot == null){
            return;
        }
        System.out.println(space + currentRoot);
        drawTree(currentRoot.left, space + "  |");
        drawTree(currentRoot.right, space + "  |");
    }

	/**
	 * Prints elements level-by-level (very useful for debugging).
	 */
	private void printTree() {
		if(root != null) {
			printInOrder(root, 0);
		}
	}

	/**
	 * Prints a subtree in-order with indentation.
	 * 
	 * @param currentRoot The subtree being printed.
	 * @param indentLevel The level of indentation in which this subtree should be printed.
	 */
	private void printInOrder(Node currentRoot, int indentLevel) {
		if(currentRoot == null) {
			return;
		}

		printInOrder(currentRoot.right, indentLevel + 1);
		for(int i = 0; i < indentLevel; i++) {
			System.out.print("   ");
		}
		System.out.println(currentRoot);
		printInOrder(currentRoot.left, indentLevel + 1);

	}

	// Add your test cases here
	public static void main(String args[]) {
		BinarySearchTree tree = new BinarySearchTree();

		System.out.println("Adding 5: " + tree.add(5));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 7: " + tree.add(7));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 9: " + tree.add(9));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 11: " + tree.add(11));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 4: " + tree.add(4));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 3: " + tree.add(3));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 2: " + tree.add(2));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Adding 1: " + tree.add(1));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Removing 5:" + tree.remove(5));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Removing 4:" + tree.remove(4));
		tree.printTree();
		System.out.println(tree.testParentLinks());

		System.out.println("Removing 11:" + tree.remove(11));
		tree.printTree();
		System.out.println(tree.testParentLinks());
	}
}
