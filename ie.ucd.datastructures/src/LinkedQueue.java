
public class LinkedQueue<E> implements Queue<E> {

	private DoublyLinkedList<E> list= new DoublyLinkedList<>();
	

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.size()==0;
	}

	@Override
	public void enqueue(E e) {
		this.list.addLast(e);
	}

	@Override
	public E first() {
		E e= this.list.first();
		return e;
	}

	@Override
	public E dequeue() {
		if(list.size()>0) {
			E e= this.list.removeFirst();
		return e;
		}
		else {
			return null;
		}
	}
	
	public String toString() {
		return list.toString();
	}
}
