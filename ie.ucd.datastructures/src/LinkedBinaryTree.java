import java.util.ArrayList;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

	static java.util.Random rnd = new java.util.Random();
	/**
	 * The root of the binary tree
	 */
	protected Node<E> root = null; // root of the tree

	// LinkedBinaryTree instance variables
	/**
	 * The number of nodes in the binary tree
	 */
	private int size = 0; // number of nodes in the tree

	/**
	 * Constructs an empty binary tree.
	 */
	public LinkedBinaryTree() {
	} // constructs an empty binary tree

	// constructor

	public static void main(String[] args) {
		LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

		// Direct construction of Tree
		Position<Integer> root = bt.addRoot(12);
		Position<Integer> p1 = bt.addLeft(root, 25);
		Position<Integer> p2 = bt.addRight(root, 31);

		Position<Integer> p3 = bt.addLeft(p1, 58);
		bt.addRight(p1, 36);

		bt.addLeft(p2, 42);
		bt.addRight(p2, 90);

		bt.addLeft(p3, 62);
		bt.addRight(p3, 75);

		// Can you write a level order constructor?
		// Level order construction
		// Integer[] arr = {12, 25, 31, 58, 36, 42, 90, 62, 75};
		// bt.createLevelOrder(arr);

		/*
		 * System.out.println("bt inorder: " + bt.size() + " " + bt.inorder());
		 * System.out.println("bt preorder: " + bt.size() + " " + bt.preorder());
		 * System.out.println("bt preorder: " + bt.size() + " " + bt.postorder());
		 * 
		 * System.out.println("bt height: " + bt.height(bt.root()));
		 * System.out.println("bt depth: " + bt.depth(bt.root()));
		 */

		System.out.println(bt.toString());
	}

	/**
	 * Factory function to create a new node storing element e.
	 */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}

	/**
	 * Verifies that a Position belongs to the appropriate class, and is not one
	 * that has been previously removed. Note that our current implementation does
	 * not actually verify that the position belongs to this particular list
	 * instance.
	 *
	 * @param p a Position (that should belong to this tree)
	 * @return the underlying Node instance for the position
	 * @throws IllegalArgumentException if an invalid position is detected
	 */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node)) {
			throw new IllegalArgumentException("Not valid position type");
		}
		Node<E> node = (Node<E>) p;
		if (node.parent == node) {
			throw new IllegalArgumentException("p is no longer in the tree");
		}
		return node;
	}

	/**
	 * Returns the number of nodes in the tree.
	 *
	 * @return number of nodes in the tree
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns the root Position of the tree (or null if tree is empty).
	 *
	 * @return root Position of the tree (or null if tree is empty)
	 */
	@Override
	public Position<E> root() {
		return root;
	}

	/**
	 * Returns the Position of p's parent (or null if p is root).
	 *
	 * @param p A valid Position within the tree
	 * @return Position of p's parent (or null if p is root)
	 * @throws IllegalArgumentException if p is not a valid Position for this tree.
	 */
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		if (size == 0) {
			throw new IllegalArgumentException("The tree is empty");
		}

		Node<E> child = validate(p);

		return child.parent;
	}

	/**
	 * Returns the Position of p's left child (or null if no child exists).
	 *
	 * @param p A valid Position within the tree
	 * @return the Position of the left child (or null if no child exists)
	 * @throws IllegalArgumentException if p is not a valid Position for this tree
	 */
	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		if (size == 0) {
			throw new IllegalArgumentException("The tree is empty");
		}

		Node<E> parent = validate(p);

		return parent.left;
	}

	/**
	 * Returns the Position of p's right child (or null if no child exists).
	 *
	 * @param p A valid Position within the tree
	 * @return the Position of the right child (or null if no child exists)
	 * @throws IllegalArgumentException if p is not a valid Position for this tree
	 */
	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		if (size == 0) {
			throw new IllegalArgumentException("The tree is empty");
		}

		Node<E> parent = validate(p);

		return parent.right;
	}

	/**
	 * Places element e at the root of an empty tree and returns its new Position.
	 *
	 * @param e the new element
	 * @return the Position of the new element
	 * @throws IllegalStateException if the tree is not empty
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (this.root != null) {
			throw new IllegalStateException("The tree is not empty");
		}
		this.root = createNode(e, null, null, null);
		this.size++;
		return root;
	}

	/**
	 * Creates a new left child of Position p storing element e and returns its
	 * Position.
	 *
	 * @param p the Position to the left of which the new element is inserted
	 * @param e the new element
	 * @return the Position of the new element
	 * @throws IllegalArgumentException if p is not a valid Position for this tree
	 * @throws IllegalArgumentException if p already has a left child
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {

		Node<E> parent = validate(p);

		if (parent.getLeft() == null) {
			Node<E> newNode = createNode(e, parent, null, null);
			parent.left = newNode;
			this.size++;
			return newNode;
		} else {
			throw new IllegalArgumentException("Position p already has a left child");
		}
	}

	/**
	 * Creates a new right child of Position p storing element e and returns its
	 * Position.
	 *
	 * @param p the Position to the right of which the new element is inserted
	 * @param e the new element
	 * @return the Position of the new element
	 * @throws IllegalArgumentException if p is not a valid Position for this tree.
	 * @throws IllegalArgumentException if p already has a right child
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {

		Node<E> parent = validate(p);

		if (parent.getRight() == null) {
			Node<E> newNode = createNode(e, parent, null, null);
			parent.right = newNode;
			this.size++;
			return newNode;
		} else {
			throw new IllegalArgumentException("Position p already has a right child");
		}
	}

	/**
	 * Replaces the element at Position p with element e and returns the replaced
	 * element.
	 *
	 * @param p the relevant Position
	 * @param e the new element
	 * @return the replaced element
	 * @throws IllegalArgumentException if p is not a valid Position for this tree.
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> original = validate(p);
		E element = p.getElement();

		original.setElement(e);

		return element;
	}

	/**
	 * Attaches trees t1 and t2, respectively, as the left and right subtree of the
	 * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
	 *
	 * @param p  a leaf of the tree
	 * @param t1 an independent tree whose structure becomes the left child of p
	 * @param t2 an independent tree whose structure becomes the right child of p
	 * @throws IllegalArgumentException if p is not a valid Position for this tree
	 * @throws IllegalArgumentException if p is not a leaf
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
		if (this.isInternal(p)) {
			throw new IllegalArgumentException("Position p is not a leaf");
		}

		Node<E> leafNode = validate(p);
		leafNode.left = t1.root;
		leafNode.right = t2.root;

		t1.root.parent = leafNode;
		t2.root.parent = leafNode;
	}

	/**
	 * Removes the node at Position p and replaces it with its child, if any.
	 *
	 * @param p the relevant Position
	 * @return element that was removed
	 * @throws IllegalArgumentException if p is not a valid Position for this tree.
	 * @throws IllegalArgumentException if p has two children.
	 */
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> oldNode = validate(p);

		if (oldNode.getLeft() != null && oldNode.getRight() != null) {
			throw new IllegalArgumentException("The position p has more then one child");
		} else {

			Node<E> child;

			if (oldNode.getLeft() != null) {
				child = oldNode.getLeft();
			} else {
				child = oldNode.getRight();
			}

			if (child != null) {

				child.setParent(oldNode.getParent());

			}

			if (oldNode.getParent() != null) {

				Node<E> parent = oldNode.parent;

				if (parent.getLeft() == oldNode) {
					parent.left = child;
				} else {
					parent.right = child;
				}

			} else {
				this.root = child;
			}

			oldNode.parent = oldNode;
			this.size--;
			return oldNode.getElement();
		}
	}

	public String toString() {
		ArrayList<E> buf = new ArrayList<>();

		for (Position<E> p : positions()) {
			// sb.append(p.getElement());
			// sb.append(", ");
			if (p.getElement() != null) {
				buf.add(p.getElement());
			}
		}

		return buf.toString();
	}

	public boolean checkCousins(Position<E> p1, Position<E> p2) {
		if (this.parent(p1) == this.parent(p2)) {
			return false;
		}

		if (this.depth(p1) == this.depth(p2)) {

			return true;

		} else {
			return false;
		}
	}

	public void validDiskUsage(Position<E> p) {
		if (children(p) == null) {

			validate(p);

		}

		Node<E> node = validate(p);

		validDiskUsage(node.left);
		validDiskUsage(node.right);
	}

	public void createLevelOrder(ArrayList<E> l) {
		root = createLevelOrderHelper(l, root, 0);
	}

	private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> p, int i) {
		if (i < l.size()) {

			Node<E> n = createNode(l.get(i), p, null, null);

			n.left = createLevelOrderHelper(l, n.left, 2 * i + 1);
			n.right = createLevelOrderHelper(l, n.right, 2 * i + 2);

			size++;

			return n;
		}

		return p;
	}

	public void createLevelOrder(E[] arr) {
		root = createLevelOrderHelper(arr, root, 0);
	}

	private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {

		if (i < arr.length) {
			Node<E> n = new Node<E>(arr[i], p, null, null);

			n.left = createLevelOrderHelper(arr, n.left, 2 * i + 1);
			n.right = createLevelOrderHelper(arr, n.right, 2 * i + 2);

			size++;

			return n;
		}

		return p;
	}

	public boolean symmetry() {
		return isMirror(this.root(), this.root());
	}

	public boolean isMirror(Position<E> p1, Position<E> p2) {

		Node<E> n1 = (Node<E>) p1;
		Node<E> n2 = (Node<E>) p2;

		if ((n1 == null) && (n2 == null)) {
			return true;
		}

		else if ((n1 != null) && (n2 != null)) {
			return (isMirror(n1.right, n2.left) && isMirror(n1.left, n2.right));
		}

		return false;
	}

	public void mirror() {
		mirror(this.root);
	}

	// Mirror helper function
	public Position<E> mirror(Position<E> p) {

		Node<E> n = (Node<E>) p;

		if (n == null) {
			return n;
		}

		Node<E> left = (Node<E>) mirror(n.left);
		Node<E> right = (Node<E>) mirror(n.right);

		n.left = right;
		n.right = left;

		return n;
	}

	public int dist(Position<E> p1, Position<E> p2) {
		return this.depth(p1) + this.depth(p2) - 2 * (this.depth(LCA(p1, p2)));
	}

	// This calculates the lowest common ancestor and is a helper function for dist
	public Position<E> LCA(Position<E> p1, Position<E> p2) {

		Node<E> n1 = (Node<E>) p1;
		Node<E> n2 = (Node<E>) p2;

		while (n1.parent != n2.parent) {
			n1 = n1.parent;
			n2 = n2.parent;
		}

		return n1.parent;
	}

	/**
	 * Nested static class for a binary tree node.
	 */
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> left, right, parent;

		public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
			this.element = e;
			this.parent = p;
			this.left = l;
			this.right = r;
		}

		public E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		public Node<E> getLeft() {
			return left;
		}

		public Node<E> getRight() {
			return right;
		}

		public Node<E> getParent() {
			return parent;
		}

		public void setParent(Node<E> n) {
			this.parent = n;
		}

		public void setLeft(Node<E> n) {
			this.left = n;
		}

		public void setRight(Node<E> n) {
			this.right = n;
		}

		@Override
		public String toString() {

			return this.getElement().toString();
		}

	}

}
