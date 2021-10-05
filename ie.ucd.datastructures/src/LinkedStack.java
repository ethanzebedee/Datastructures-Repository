

public class LinkedStack<E> implements Stack<E>{

	//Create a private singlyLinkedList to store the stack
	private SinglyLinkedList<E> list= new SinglyLinkedList<>();
	
	public LinkedStack() {
	}
	
	public static void main(String[] args) {
		System.out.println(delimatorAlgorithm("{[()]}"));
		System.out.println(delimatorAlgorithm("{[(])}"));
		System.out.println(delimatorAlgorithm("{{[[(())]]}}"));
		System.out.println(delimatorAlgorithm("][]][][[]][]][][[["));
		System.out.println(delimatorAlgorithm("(((abc))((d)))))"));

	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void push(E e) {
		list.addFirst(e);
	}

	@Override
	public E top() {
		return list.first();
	}

	@Override
	public E pop() {
		E e=list.removeFirst();
		return e;
	}
	
	public String toString() {
		return list.toString();
	}
	
	//The delimator algorithm from the tutorial
	public static boolean delimatorAlgorithm(String string) {
	    LinkedStack<String> delimators=new LinkedStack<String>();
	    char [] charArray=string.toCharArray();
	    
	    for(int i=0;i<string.length();i++) {
	    	if(charArray[i]== '[' ||charArray[i]== '{' ||charArray[i]== '(' )
	    	{
	    		String s=""+ charArray[i];
	    		delimators.push(s);
	    	}
	    	else if(charArray[i]== ')' || charArray[i]=='}' ||charArray[i]== ']')
	    	{
	    		char start;
	    		switch(charArray[i]) {
	    		case(')') : start='(';
	    		break;
	    		case('}'): start= '{';
	    		break;
	    		default : start='[';
	    		break;
	    		}
	    		String s=""+ start;
	    		if(delimators.isEmpty()) {
	    			return false;
	    		}
	    		else if(!delimators.pop().equals(s)){	    		
	    			return false;
	    		}
	    	}
	    }
		if(delimators.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
}
