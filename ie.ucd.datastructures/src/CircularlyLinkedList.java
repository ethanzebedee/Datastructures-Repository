import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {
	// ---------------- nested Node class ----------------
	/**
	 * Singly linked node, which stores a reference to its element and to the
	 * subsequent node in the list.
	 */
	private static class Node<E> {
		private E element;
		Node<E> next;

		// The constructor for the Node class
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		// Getters for next and element in the Node class (Accessors)
		public Node<E> getNext() {
			return next;
		}

		public E getElement() {
			return element;
		}

		// A Mutator for the next Node
		public void setNext(Node<E> next) {
			this.next = next;
		}
	} // ----------- end of nested Node class -----------

	// instance variables of the CircularlyLinkedList
	/** The designated cursor of the list */
	private Node<E> tail; // we store tail (but not head)

	/** Number of nodes in the list */
	private int size = 0; // number of nodes in the list

	/** Constructs an initially empty list. */
	public CircularlyLinkedList() {
		this.tail = new Node<E>(null, null);
	} // constructs an initially empty list

	// access methods
	/**
	 * Returns the number of elements in the linked list.
	 * 
	 * @return number of elements in the linked list
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests whether the linked list is empty.
	 * 
	 * @return true if the linked list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		if (size >= i) {
			Node<E> pointer = tail;

			for (int j = i + 1; j > 0 && (j < size); j--) {
				pointer = pointer.getNext();
			}

			return pointer.element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (size >= i) {
			Node<E> pointer = tail;

			for (int j = i + 1; j > 0 && (j < size); j--) {
				pointer = pointer.next;
			}

			E element = pointer.element;
			pointer.element = e;

			return element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		if (i > size) {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");

		} else {

			Node<E> pointer = tail;
			Node<E> trails = pointer;

			for (int j = i + 1; j > 0 && (j < size); j--) {
				trails = pointer;
				pointer = pointer.next;
			}

			Node<E> added = new Node<E>(e, pointer);

			trails.setNext(added);
			this.size++;
		}

	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if (size > 0) {

			Node<E> beforeHead = tail;
			Node<E> atHead = tail;

			for (int j = i + 1; (j > 0) && (j < size); j--) {
				beforeHead = atHead;
				atHead = atHead.getNext();
			}

			E element = atHead.element;

			beforeHead.next = atHead.next;

			this.size--;
			return element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	/**
	 * Returns (but does not remove) the first element of the list
	 * 
	 * @return element at the front of the list (or null if empty)
	 */
	public E first() { // returns (but does not remove) the first element
		if (tail == null) {

			return null;

		} else {

			return tail.getNext().getElement();

		}
	}

	/**
	 * Returns (but does not remove) the last element of the list
	 * 
	 * @return element at the back of the list (or null if empty)
	 */
	public E last() {
		if (tail == null) {
			return null;

		} else {
			return tail.getElement();
		}
	}

	// update methods
	/**
	 * Rotate the first element to the back of the list.
	 */
	public void rotate() { // rotate the first element to the back of the list
		tail = tail.getNext();
	}

	/**
	 * Adds an element to the front of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addFirst(E e) {
		if (size == 0) {
			Node<E> first = new Node<E>(e, null);
			tail = first;
			first.setNext(tail);
		} else {
			Node<E> first = new Node<E>(e, tail.getNext());
			tail.setNext(first);
		}

		this.size++;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addLast(E e) {
		if (size == 0) {
			Node<E> first = new Node<E>(e, null);
			tail = first;
			first.setNext(tail);
		} else {
			Node<E> last = new Node<E>(e, tail.getNext());
			tail.setNext(last);
			tail = last;
		}

		this.size++;
	}

	/**
	 * Removes and returns the first element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeFirst() {
		if (size > 0) {
			E element = tail.getNext().getElement();
			tail.setNext(tail.getNext().getNext());
			this.size--;

			return element;
		} else {
			return null;
		}
	}

	public E removeLast() {
		if (size > 0) {
			Node<E> GoToLast = tail;

			for (int i = 0; i < (size - 1); i++) {
				GoToLast = GoToLast.next;
			}

			E element = tail.getElement();
			tail = tail.getNext();
			GoToLast.setNext(tail);
			this.size--;
			return element;
		} else {
			return null;
		}
	}

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		if (tail == null) {
			return null;
		}
		String returned = "[";
		Node<E> pointer = tail;
		for (int i = 0; i < size - 1; i++) {
			returned = returned + pointer.getElement() + ", ";
			pointer = pointer.getNext();
		}
		returned = returned + pointer.getElement() + "]";
		return returned;
	}

	public static void main(String[] args) {
		CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();

		ll.addFirst(0);
		ll.addFirst(1);
		ll.addFirst(3);
		ll.addFirst(4);
		ll.addFirst(5);
		ll.add(3, 2);

		System.out.println(ll);

		ll.addFirst(-100);

		System.out.println(ll);

		ll.addLast(+100);

		System.out.println(ll);

		ll.removeFirst();
		ll.removeLast();

		System.out.println(ll);

		ll.remove(2);
		System.out.println(ll);

		ll.removeFirst();
		System.out.println(ll);

		ll.removeLast();
		System.out.println(ll);

		ll.removeFirst();
		System.out.println(ll);

		ll.addFirst(9999);
		ll.addFirst(8888);
		ll.addFirst(7777);

		System.out.println(ll);
		System.out.println(ll.get(0));
		System.out.println(ll.get(1));
		System.out.println(ll.get(2));
		System.out.println(ll);

		ll.rotate();

		System.out.println(ll);

	}
}
