import java.util.*;

/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

	// ---------------- nested BalanceableBinaryTree class ----------------
	/**
	 * A specialized version of the LinkedBinaryTree class with additional mutators
	 * to support binary search tree operations, and a specialized node class that
	 * includes an auxiliary instance variable for balancing data.
	 */
	protected static class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
		// -------------- nested BSTNode class --------------
		// this extends the inherited LinkedBinaryTree.Node class
		protected static class BSTNode<E> extends Node<E> {
			int aux = 0;

			BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
				super(e, parent, leftChild, rightChild);
			}

			public int getAux() {
				return aux;
			}

			public void setAux(int value) {
				aux = value;
			}
		} // --------- end of nested BSTNode class ---------

		// positional-based methods related to aux field
		public int getAux(Position<Entry<K, V>> p) {
			return ((BSTNode<Entry<K, V>>) p).getAux();
		}

		public void setAux(Position<Entry<K, V>> p, int value) {
			((BSTNode<Entry<K, V>>) p).setAux(value);
		}

		// Override node factory function to produce a BSTNode (rather than a Node)
		@Override
		protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
				Node<Entry<K, V>> right) {
			return new BSTNode<>(e, parent, left, right);
		}

		/** Relinks a parent node with its oriented child node. */
		private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {

			child.setParent(parent);
			if (makeLeftChild) {
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
			return;
		}

		/**
		 * Rotates Position p above its parent. Switches between these configurations,
		 * depending on whether p is a or p is b.
		 * 
		 * <pre>
		 *          b                  a
		 *         / \                / \
		 *        a  t2             t0   b
		 *       / \                    / \
		 *      t0  t1                 t1  t2
		 * </pre>
		 * 
		 * Caller should ensure that p is not the root.
		 */
		public void rotate(Position<Entry<K, V>> p) {

			Node<Entry<K, V>> x = validate(p);
			Node<Entry<K, V>> y = x.getParent();
			Node<Entry<K, V>> z = y.getParent();

			if (z == null) {
				root = x;
				x.setParent(null);
			} else {
				relink(x, y, y == z.getLeft());
			}

			if (x == y.getLeft()) {
				relink(y, x.getRight(), true);
				relink(x, y, false);
			} else {
				relink(y, x.getLeft(), false);
				relink(x, y, true);
			}
			return;
		}

		/**
		 *
		 * Returns the Position that becomes the root of the restructured subtree.
		 *
		 * Assumes the nodes are in one of the following configurations:
		 * 
		 * <pre>
		 *     z=a                 z=c           z=a               z=c
		 *    /  \                /  \          /  \              /  \
		 *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
		 *      /  \            /  \               /  \         /  \
		 *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
		 *        /  \        /  \               /  \              /  \
		 *       t2  t3      t0  t1             t1  t2            t1  t2
		 * </pre>
		 * 
		 * The subtree will be restructured so that the node with key b becomes its
		 * root.
		 * 
		 * <pre>
		 *           b
		 *         /   \
		 *       a       c
		 *      / \     / \
		 *     t0  t1  t2  t3
		 * </pre>
		 * 
		 * Caller should ensure that x has a grandparent.
		 */
		public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {

			Position<Entry<K, V>> y = parent(x);
			Position<Entry<K, V>> z = parent(y);

			if ((x == right(y)) == (y == right(z))) {
				rotate(y);
				return y;
			} else {

				rotate(x);
				rotate(x);
				return x;
			}
		}
	} // ----------- end of nested BalanceableBinaryTree class -----------

	// We reuse the LinkedBinaryTree class. A limitation here is that we only use
	// the key.
	// protected LinkedBinaryTree<Entry<K, V>> tree = new LinkedBinaryTree<Entry<K,
	// V>>();
	protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	/** Constructs an empty map using the natural ordering of keys. */
	public TreeMap() {
		super(); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 * 
	 * @param comp comparator defining the order of keys in the map
	 */
	public TreeMap(Comparator<K> comp) {
		super(comp); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Returns the number of entries in the map.
	 * 
	 * @return number of entries in the map
	 */
	@Override
	public int size() {
		return (tree.size() - 1) / 2; // only internal nodes have entries
	}

	protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
		return tree.restructure(x);
	}

	/**
	 * Rebalances the tree after an insertion of specified position. This version of
	 * the method does not do anything, but it can be overridden by subclasses.
	 *
	 * @param p the position which was recently inserted
	 */
	protected void rebalanceInsert(Position<Entry<K, V>> p) {
	}

	/**
	 * Rebalances the tree after a child of specified position has been removed.
	 * This version of the method does not do anything, but it can be overridden by
	 * subclasses.
	 * 
	 * @param p the position of the sibling of the removed leaf
	 */
	protected void rebalanceDelete(Position<Entry<K, V>> p) {
	}

	/**
	 * Rebalances the tree after an access of specified position. This version of
	 * the method does not do anything, but it can be overridden by a subclasses.
	 * 
	 * @param p the Position which was recently accessed (possibly a leaf)
	 */
	protected void rebalanceAccess(Position<Entry<K, V>> p) {
	}

	/** Utility used when inserting a new entry at a leaf of the tree */
	private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {

		tree.set(p, entry);

		tree.addLeft(p, null);
		tree.addRight(p, null);

		return;
	}

	// Some notational shorthands for brevity (yet not efficiency)
	protected Position<Entry<K, V>> root() {
		return tree.root();
	}

	protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
		return tree.parent(p);
	}

	protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
		return tree.left(p);
	}

	protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
		return tree.right(p);
	}

	protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
		return tree.sibling(p);
	}

	protected boolean isRoot(Position<Entry<K, V>> p) {
		// Check if the parent of p is null. If it is, it is the root
		return parent(p) == null;
	}

	protected boolean isExternal(Position<Entry<K, V>> p) {
		// Make sure that p has no right or left kids
		return ((left(p) == null) && (right(p) == null));
	}

	protected boolean isInternal(Position<Entry<K, V>> p) {
		// return the opposite of isExternal
		return !isExternal(p);
	}

	protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {

		// Change the element held by the node to be entry e
		tree.set(p, e);
		return;
	}

	protected Entry<K, V> remove(Position<Entry<K, V>> p) {
		return tree.remove(p);
	}

	/**
	 * Returns the position in p's subtree having the given key (or else the
	 * terminal leaf).
	 * 
	 * @param key a target key
	 * @param p   a position of the tree serving as root of a subtree
	 * @return Position holding key, or last node reached during search
	 */
	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		if (isExternal(p)) {
			return p;
		}

		int compare = compare(key, p.getElement());

		if (compare == 0) {
			return p;
		}

		if (compare < 0) {
			return treeSearch(left(p), key);
		} else {
			return treeSearch(right(p), key);
		}
	}

	/**
	 * Returns position with the minimal key in the subtree rooted at Position p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with minimal key in subtree
	 */
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		if (!(isExternal(left(p)))) {
			p = treeMin(left(p));
		}

		return p;
	}

	/**
	 * Returns the position with the maximum key in the subtree rooted at p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with maximum key in subtree
	 */
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		if (!(isExternal(right(p)))) {
			p = treeMax(right(p));
		}

		return p;
	}

	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 * 
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	@Override
	public V get(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(root(), key);

		if (isExternal(p)) {
			return null;
		} else {
			return p.getElement().getValue();
		}
	}

	/**
	 * Associates the given value with the given key. If an entry with the key was
	 * already in the map, this replaced the previous value with the new one and
	 * returns the old value. Otherwise, a new entry is added and null is returned.
	 * 
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 */
	@Override
	public V put(K key, V value) throws IllegalArgumentException {
		Entry<K, V> entry = new MapEntry<K, V>(key, value);
		Position<Entry<K, V>> entryPosition = treeSearch(root(), key);

		if (isExternal(entryPosition)) {
			expandExternal(entryPosition, entry);

			return null;
		} else {

			V oldValue = entryPosition.getElement().getValue();
			set(entryPosition, entry);

			return oldValue;
		}
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 * 
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	@Override
	public V remove(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(root(), key);

		if (isExternal(p)) {

			return null;

		}

		V value = p.getElement().getValue();

		if (isInternal(left(p)) && isInternal(right(p))) {
			Position<Entry<K, V>> rightmostNode = treeMax(left(p));

			set(p, rightmostNode.getElement());

			p = rightmostNode;
		}

		Position<Entry<K, V>> leaf = isExternal(left(p)) ? left(p) : right(p);
		Position<Entry<K, V>> sibling = sibling(leaf);
		System.out.println(this.remove(leaf));
		this.remove(p);
		rebalanceDelete(sibling);

		return value;

	}

	// additional behaviors of the SortedMap interface
	/**
	 * Returns the entry having the least key (or null if map is empty).
	 * 
	 * @return entry with least key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> firstEntry() {
		if (tree.isEmpty()) {
			return null;
		} else {
			Entry<K, V> entry = treeMin(root()).getElement();

			return entry;
		}
	}

	/**
	 * Returns the entry having the greatest key (or null if map is empty).
	 * 
	 * @return entry with greatest key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> lastEntry() {
		if (tree.isEmpty()) {
			return null;
		} else {
			Entry<K, V> entry = treeMax(root()).getElement();

			return entry;
		}
	}

	/**
	 * Returns the entry with least key greater than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with least key greater than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
		Entry<K, V> current = null;
		Entry<K, V> leastFound = null;

		while (entryIterator.hasNext()) {

			current = entryIterator.next();

			int compare = compare(current, key);

			if (compare == 0) {
				return current;
			}

			if (compare > 0) {

				if (leastFound == null) {
					leastFound = current;
				}

				compare = compare(current, leastFound);

				if (compare < 0) {
					leastFound = current;
				}
			}
		}

		return leastFound;
	}

	/**
	 * Returns the entry with greatest key less than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with greatest key less than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {

		Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
		Entry<K, V> current = null;
		Entry<K, V> greatestFound = null;

		// Run through all the entries
		while (entryIterator.hasNext()) {

			current = entryIterator.next();

			int compare = compare(current, key);

			if (compare == 0) {
				return current;
			}

			if (compare < 0) {

				if (greatestFound == null) {
					greatestFound = current;
				}

				compare = compare(current, greatestFound);

				if (compare > 0) {
					greatestFound = current;
				}
			}
		}

		return greatestFound;
	}

	/**
	 * Returns the entry with greatest key strictly less than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with greatest key strictly less than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {

		Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
		Entry<K, V> current = null;
		Entry<K, V> greatestFound = null;

		while (entryIterator.hasNext()) {

			current = entryIterator.next();

			int compare = compare(current, key);
			/*
			 * //If they are the same, return the current entry if(compare==0) { return
			 * current; }
			 */

			if (compare < 0) {

				if (greatestFound == null) {
					greatestFound = current;
				}

				compare = compare(current, greatestFound);

				if (compare > 0) {
					greatestFound = current;
				}
			}
		}

		return greatestFound;
	}

	/**
	 * Returns the entry with least key strictly greater than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with least key strictly greater than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {

		Iterator<Entry<K, V>> entryIterator = entrySet().iterator();

		Entry<K, V> current = null;
		Entry<K, V> leastFound = null;

		// Run through all the entries
		while (entryIterator.hasNext()) {

			current = entryIterator.next();

			int compare = compare(current, key);

			if (compare > 0) {

				if (leastFound == null) {
					leastFound = current;
				}

				compare = compare(current, leastFound);

				if (compare < 0) {
					leastFound = current;
				}
			}
		}

		return leastFound;
	}

	// Support for iteration
	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());

		Iterator<Position<Entry<K, V>>> positionsIterator = tree.inorder().iterator();

		for (int i = 0; i < size(); i++) {
			Entry<K, V> nextEntry = positionsIterator.next().getElement();

			if (nextEntry != null) {
				buffer.add(i, nextEntry);
			} else {
				i--;
			}
		}

		return buffer;
	}

	public String toString() {
		return entrySet().toString();
	}

	/**
	 * Returns an iterable containing all entries with keys in the range from
	 * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
	 * 
	 * @return iterable with keys in desired range
	 * @throws IllegalArgumentException if <code>fromKey</code> or
	 *                                  <code>toKey</code> is not compatible with
	 *                                  the map
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {

		Iterator<Entry<K, V>> entrySetIterator = this.entrySet().iterator();

		ArrayList<Entry<K, V>> subMap = new ArrayList<Entry<K, V>>();

		boolean from = false;

		// Run through the map
		while (entrySetIterator.hasNext()) {
			Entry<K, V> element = entrySetIterator.next();

			int compare = compare(fromKey, element);

			if (compare == 0) {
				from = true;
			}

			compare = compare(toKey, element);
			if (compare == 0 || compare < 0) {
				break;
			}

			if (from) {
				subMap.add(element);
			}

		}
		return subMap;
	}

	protected void rotate(Position<Entry<K, V>> p) {
		tree.rotate(p);
	}

	// remainder of class is for debug purposes only
	/** Prints textual representation of tree structure (for debug purpose only). */
	protected void dump() {
		dumpRecurse(root(), 0);
	}

	/** This exists for debugging only */
	private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
		if (isExternal(p))
			System.out.println(indent + "leaf");
		else {
			System.out.println(indent + p.getElement());
			dumpRecurse(left(p), depth + 1);
			dumpRecurse(right(p), depth + 1);
		}
	}

	public String toBinaryTreeString() {
		BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>((LinkedBinaryTree<Entry<K, V>>) this.tree);
		return btp.print();
	}

	public static void main(String[] args) {
		Random rnd = new Random(10);

		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();

		int n = 80;
		int max_n = 100;

		rnd.ints(1, max_n).limit(n).distinct().boxed().forEach(x -> treeMap.put(x, x));

		System.out.println(treeMap.tree.inorder());

		System.out.println(treeMap.keySet().toString());
		Iterator<Entry<Integer, Integer>> iterator = treeMap.entrySet().iterator();
		Iterator<Integer> keyIterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Integer> current = iterator.next();
			System.out.println(
					"The current element has a key of " + current.getKey() + " and a value of " + current.getValue());
			System.out.println("The key iterator returned a key of " + keyIterator.next());
		}
		System.out.println("The overall keySet is " + treeMap.keySet().toString());
	}

}
