
import java.util.Iterator;

/**
 * A basic singly linked list implementation.
 */
public class SinglyLinkedList<E> implements Cloneable, List<E> {
	// ---------------- nested Node class ----------------

	/**
	 * Node of a singly linked list, which stores a reference to its element and to
	 * the subsequent node in the list (or null if this is the last node).
	 */
	// This is the implemented Node class
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

	// instance variables of the SinglyLinkedList
	private Node<E> head; // head node of the list (or null if empty)

	private int size = 0; // number of nodes in the list

	public SinglyLinkedList() {
		this.head = null;
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
		if (size > i) {

			Node<E> pointer = head;

			for (int j = i; j > 0; j--) {
				pointer = pointer.next;
			}

			return pointer.element;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (size > i) {
			Node<E> pointer = head;

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
		if (size >= 1) {
			Node<E> pointer = head;

			Node<E> trails = pointer;

			for (int j = i; j > 0; j--) {
				trails = pointer;
				pointer = pointer.getNext();
			}

			Node<E> added = new Node<E>(e, pointer);

			trails.setNext(added);

			this.size++;
		} else {
			throw new IndexOutOfBoundsException("The index is greater then the size of the list");
		}

	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if (size > 0) {
			Node<E> beforeHead = head;
			Node<E> atHead = head;

			for (int j = i; (j > 0) && (atHead.getNext() != null); j--) {
				beforeHead = atHead;
				atHead = atHead.getNext();
			}

			E element = atHead.element;

			beforeHead.next = atHead.next;

			this.size--;

			return element;
		} else {
			return null;
		}
	}

	/**
	 * Returns (but does not remove) the first element of the list
	 *
	 * @return element at the front of the list (or null if empty)
	 */
	public E first() {
		if (head == null) {
			return null;
		}

		return head.getElement();

	}

	/**
	 * Returns the last node of the list
	 *
	 * @return last node of the list (or null if empty)
	 */
	public Node<E> getLast() {
		if (head == null) {
			return null;
		} else {
			Node<E> GoToLast = head;

			while (GoToLast.getNext() != null) {
				GoToLast = GoToLast.next;
			}

			return GoToLast;
		}
	}

	/**
	 * Returns (but does not remove) the last element of the list.
	 *
	 * @return element at the end of the list (or null if empty)
	 */
	public E last() {
		Node<E> last = this.getLast();

		if (last == null) {
			return null;
		} else {

			return last.getElement();
		}
	}

	// update methods

	/**
	 * Adds an element to the front of the list.
	 *
	 * @param e the new element to add
	 */
	public void addFirst(E e) {

		Node<E> first = new Node<E>(e, head);

		this.head = first;
		this.size++;
	}

	/**
	 * Adds an element to the end of the list.
	 *
	 * @param e the new element to add
	 */
	public void addLast(E e) {

		Node<E> NewLast = new Node<E>(e, null);
		Node<E> CurrentLast = this.getLast();

		if (CurrentLast != null) {
			CurrentLast.setNext(NewLast);
		} else {
			this.head = NewLast;
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

			E element = head.getElement();

			this.head = head.getNext();
			this.size--;

			return element;
		} else {
			return null;
		}
	}

	public E removeLast() {
		if (!this.isEmpty()) {

			Node<E> beforeHead = null;
			Node<E> atHead = head;

			while (atHead.next != null) {
				beforeHead = atHead;
				atHead = atHead.next;
			}

			E element = atHead.element;

			if (beforeHead != null) {
				beforeHead.next = null;
			} else {
				this.head = null;
			}

			this.size--;
			return element;
		} else {
			return null;
		}
	}

	public boolean equals(Object o) {
		// Check if Object o and the list are of the same type

		return false; // if we reach this, everything matched successfully
	}

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		if (head == null) {
			return null;
		}

		String returned = "[";

		Node<E> pointer = head;
		while (pointer.getNext() != null) {

			returned = returned + pointer.getElement() + ", ";
			pointer = pointer.next;
		}

		returned = returned + pointer.getElement() + "]";

		return returned;
	}

	// From the stacks and queues tutorial
	public void reverse() {

		ArrayStack<E> toReverse = new ArrayStack<E>(this.size());

		while (!this.isEmpty()) {
			toReverse.push(this.removeLast());
		}

		while (!toReverse.isEmpty()) {
			this.addFirst(toReverse.pop());
		}
	}

	private class SinglyLinkedListIterator<Q> implements Iterator<E> {
		Node<E> current = head;

		@Override
		public boolean hasNext() {
			if (current == null) {
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

	public Iterator<E> iterator() {
		return new SinglyLinkedListIterator<E>();
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();

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

		ll.reverse();

		System.out.println(ll);
	}
}
