public class ArrayStack<E> implements Stack<E> {
	private static final int CAPACITY=1000;

	private int t;
	private E data[];

	private int N;
	
	
	public static void main(String[] args) {
		ArrayStack<Integer> s = new ArrayStack<>();
		for(int i = 0; i < 10; ++i)
			s.push(i);
		System.out.println(s.size());
		System.out.println(s.toString());

	}
	
	public ArrayStack() {
		this(CAPACITY);
	}
	//Constructs the stack
	@SuppressWarnings("unchecked")
	public ArrayStack (int capacity){
		this.t=-1;
		this.N=capacity+1;
		this.data= (E[]) new Object[capacity];
	}
	@Override
	public int size() {
		return t+1;
	}

	@Override
	public boolean isEmpty() {
		return t==-1;
	}

	@Override
	public void push(E e) {
		if(this.size()==N) {
			throw new IndexOutOfBoundsException("The size of the stack array's max capacity has been exceeded");
		}
		else {
		this.t++;
		this.data[t]=e;
		}
	}

	@Override
	public E top() {
		if(t==-1) {
		return null;
		}
		else {
			return this.data[t];
		}
	}

	
	@Override
	public E pop() {
		if(this.isEmpty()) {
			return null;
			}
			else {
				this.t--;
				return this.data[t+1];
			}
	}
	
	public String toString() {
		String result="[";
		for(int i=t;i>0;i--) {
			result=result+ data[i] + ", ";
		}
		result=result + data[0]+ "]";
		return result;
	}
	
}
