public class Node {
	public int value;
	public int level;
	
	public Node left;
	public Node right;
	public Node parent;
	
	public Node(int value) {
		this.value = value;

		parent = null;
		left = null;
		right = null;
	}


	
	// Suggestion: modify to include the level too
	//             after you implement the levels.
	public String toString() {
		return "[" + value + "]" + level;
	}
}
