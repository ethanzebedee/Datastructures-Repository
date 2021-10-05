import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

	// ---------------- nested Node class ----------------
	/**
	 * Node of a doubly linked list, which stores a reference to its element and to
	 * both the previous and next node in the list.
	 */
	private static class Node<E> {
		private E element;
		Node<E> next;
		Node<E> last;

		// The constructor for the Node class
		public Node(E element, Node<E> next, Node<E> last) {

			this.element = element;
			this.next = next;
			this.last = last;
		}

		// Getters for next and element in the Node class (Accessors)
		public Node<E> getNext() {
			return next;
		}

		public Node<E> getLast() {
			return last;
		}

		public E getElement() {
			return element;
		}

		// A Mutator for the next Node
		public void setNext(Node<E> next) {
			this.next = next;
		}

		// A Mutator for the last Node
		public void setLast(Node<E> last) {
			this.last = last;
		}
	} // ----------- end of nested Node class -----------

	// instance variables of the DoublyLinkedList
	/** Sentinel node at the beginning of the list */
	private Node<E> header; // header sentinel

	/** Sentinel node at the end of the list */
	private Node<E> trailer; // trailer sentinel

	/** Number of elements in the list (not including sentinels) */
	private int size = 0; // number of elements in the list

	/** Constructs a new empty list. */
	public DoublyLinkedList() {
		this.header = new Node<E>(null, null, null);
		this.trailer = new Node<E>(null, null, null);
		this.header.setNext(trailer);
		;
		this.trailer.setLast(header);
		;
	}

	// public accessor methods
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

			Node<E> pointer = header.getNext();

			for (int j = i; j > 0 && (pointer != null); j--) {
				pointer = pointer.next;
			}

			return pointer.element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (size >= i) {

			Node<E> pointer = header.getNext();

			for (int j = i; j > 0 && (pointer != null); j--) {
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
		if (size >= i) {

			Node<E> pointer = header.getNext();

			Node<E> trails = pointer;

			for (int j = i; j > 0 && (pointer != null); j--) {
				trails = pointer;
				pointer = pointer.next;
			}

			Node<E> added = new Node<E>(e, pointer, trails);

			trails.setNext(added);

			this.size++;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}

	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if (size > i) {
			Node<E> beforeHead = header.getNext();
			Node<E> atHead = beforeHead;

			for (int j = i; (j > 0) && (atHead.getNext() != null); j--) {
				beforeHead = atHead;
				atHead = atHead.getNext();
			}

			E element = atHead.element;

			beforeHead.next = atHead.next;
			atHead = atHead.next;
			atHead.setLast(beforeHead);

			this.size--;
			return element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new DoublyLinkedListIterator<E>();

	}

	private class DoublyLinkedListIterator<Q> implements Iterator<E> {
		Node<E> current = header.next;

		@Override
		public boolean hasNext() {
			if (current.next == null) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E next() {

			E el = current.element;
			current = current.next;
			return el;
		}
	}

	/**
	 * Returns (but does not remove) the first element of the list.
	 * 
	 * @return element at the front of the list (or null if empty)
	 */
	public E first() {
		if (header.getNext() == null) {
			return null;
		} else {
			return header.getNext().getElement();
		}
	}

	/**
	 * Returns (but does not remove) the last element of the list.
	 * 
	 * @return element at the end of the list (or null if empty)
	 */
	public E last() {
		if (trailer.getLast() == null) {
			return null;
		} else {
			return trailer.getLast().getElement();
		}
	}

	// public update methods
	/**
	 * Adds an element to the front of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addFirst(E e) {
		Node<E> first = new Node<E>(e, null, header);

		Node<E> oldFirst = header.getNext();

		oldFirst.setLast(first);
		first.setNext(oldFirst);
		this.header.setNext(first);
		this.size++;
		return;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addLast(E e) {
		Node<E> last = new Node<E>(e, trailer, null);
		Node<E> oldLast = trailer.getLast();
		oldLast.setNext(last);

		last.setLast(oldLast);
		this.trailer.setLast(last);

		this.size++;
		return;
	}

	/**
	 * Removes and returns the first element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeFirst() {
		if (size == 0) {
			return null;
		} else {

			E element = header.getNext().getElement();
			Node<E> newFirst = header.getNext().getNext();

			header.next = newFirst;
			newFirst.setLast(header);

			this.size--;
			return element;
		}
	}

	/**
	 * Removes and returns the last element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeLast() {
		if (size == 0) {
			return null;
		} else {

			E element = trailer.getLast().getElement();
			Node<E> newLast = trailer.getLast().getLast();
			trailer.setLast(newLast);
			newLast.setNext(trailer);

			this.size--;
			return element;
		}
	}

	// private update methods
	/**
	 * Adds an element to the linked list in between the given nodes. The given
	 * predecessor and successor should be neighboring each other prior to the call.
	 *
	 * @param predecessor node just before the location where the new element is
	 *                    inserted
	 * @param successor   node just after the location where the new element is
	 *                    inserted
	 */
	private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> fromHead = header;

		while (fromHead != trailer) {
			if ((fromHead == predecessor) && (fromHead.getNext() == successor)) {
				Node<E> added = new Node<E>(e, successor, predecessor);
				predecessor.setNext(added);
				successor.setLast(added);
				this.size++;
				break;
			}
			fromHead = fromHead.getNext();
		}
		return;
	}

	/**
	 * Removes the given node from the list and returns its element.
	 * 
	 * @param node the node to be removed (must not be a sentinel)
	 */
	private E remove(Node<E> node) {

		if (size == 0 || node == trailer || node == header) {
			return null;
		} else {

			Node<E> fromHead = header;

			E element = null;
			while (fromHead != trailer) {
				if (fromHead.getNext() == node) {

					element = node.getElement();

					Node<E> newNext = fromHead.getNext().getNext();
					fromHead.setNext(newNext);
					newNext.setLast(fromHead);

					this.size--;
					break;
				}
			}

			return element;
		}
	}

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		if (size == 0) {
			return null;
		}

		String returned = "[";

		Node<E> pointer = header.getNext();
		while (pointer.getNext() != trailer) {
			returned = returned + pointer.getElement() + ", ";
			pointer = pointer.next;
		}

		returned = returned + pointer.getElement() + "]";

		return returned;
	}

	public static void main(String[] args) {
		DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();

		ll.addFirst(0);
		ll.addFirst(1);
		ll.addFirst(3);
		ll.addFirst(4);
		ll.addFirst(5);
		ll.add(3, 2);

		System.out.println(ll);

		ll.addFirst(-100);
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

		ll.addBetween(-1, ll.header.getNext(), ll.header.getNext().getNext());

		System.out.println(ll);

		ll.remove(ll.header.getNext());

		System.out.println(ll);
	}

} // ----------- end of DoublyLinkedList class -----------
