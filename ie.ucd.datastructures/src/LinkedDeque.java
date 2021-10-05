
public class LinkedDeque<E> implements Deque<E> {

	private DoublyLinkedList<E> list= new DoublyLinkedList<>();
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public E first() {
		return list.first();
	}

	@Override
	public E last() {
		return list.last();
	}

	@Override
	public void addFirst(E e) {
		list.addFirst(e);
	}

	@Override
	public void addLast(E e) {
		list.addLast(e);
	}

	@Override
	public E removeFirst() {
		E e=list.removeFirst();
		return e;
	}

	@Override
	public E removeLast() {
		E e= list.removeLast();
		return e;
	}

}
