public class ArrayQueue<E> implements Queue<E> {

	private E data[];
	private int front;
	private int rear;
	private int N;
	private static final int CAPACITY=1000;


	public ArrayQueue() {
		this(CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayQueue(int capacity) {
		this.front=0;
		this.rear=0;
		this.N= capacity+1;
		this.data= (E[]) new Object [capacity];
	}
	@Override
	public int size() {
		if(front-rear>=0) {
			return front - rear;
		}
		else {
			return rear - front;
		}
	}

	@Override
	public boolean isEmpty() {
		return front == rear;
	}

	@Override
	public void enqueue(E e) {
		rear = (front+this.size())%N;
		data[rear] = e;
		rear = rear + 1;
	}

	@Override
	public E first() {
		if(this.size()==0) {
			return null;
		}
		else {
			E e=data[front];
			return e;
		}
	}

	@Override
	public E dequeue() {
		E e = data[front];
		front = (front+1)%N;
		return e;
	}

}
