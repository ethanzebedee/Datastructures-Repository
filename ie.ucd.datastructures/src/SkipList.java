//import java.util.AbstractMap;
import java.util.*;

public class SkipList<K extends Comparable<? super K>, V> extends AbstractMap<K, V> {

    private static class SkipListNode<K, V> {
        private K key;
        private V value;
        public ArrayList<SkipListNode<K, V> > nextNodes;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public SkipListNode(K key, V value) {
            this.value = value;
            this.key = key;
            nextNodes = new ArrayList<SkipListNode<K, V> >();
        }

        public int level() {
            return nextNodes.size()-1;
        }

        public String toString() {
            return new StringBuilder().append("[").append(key).append(", ").append(value).append("]").toString();
        }
    }


    private SkipListNode<K,V> head;
    private int maxLevel;
    private int size;
    private Random rnd;

    //private static final double PROBABILITY = 0.5;
    private Comparator<K> comp;

    protected SkipList(Comparator<K> c) { comp = c; }


    /** Method for comparing two entries according to key */
    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }

    public SkipList() {
        this(new DefaultComparator<K>());
        rnd = new Random();
        size = 0;
        maxLevel = 0;
        // a SkipListNode with value null marks the beginning
        head = new SkipListNode<K, V>(null, null);
        // null marks the end
        head.nextNodes.add(null);
    }

    public SkipListNode<K, V> getHead() {
        return head;
    }

    // Adds e to the skiplist.
    // Returns false if already in skiplist, true otherwise.
    @Override
    public V put(K k, V v) {
       
    	SkipListNode<K, V> node= find(k);
    	
    	
    	if(node!=null && node.value!=null && (compare(k, node.getKey())==0)){
    		return node.getValue();
    	}
    	
    	
    	int level=0;
    	while(rnd.nextBoolean()==false) {
    		level++;
    	}
    	
    	
    	while(level > maxLevel) {
    		maxLevel++;
    		head.nextNodes.add(null);
    	}
    	
    	SkipListNode<K, V> newNode = new SkipListNode<K, V>(k, v);
    	SkipListNode<K, V> current = head;
    	
    	
    	do {

    		current=findNext(k, current, level);
    		newNode.nextNodes.add(0, current.nextNodes.get(level));
    		current.nextNodes.set(level, newNode);

    	}while(level-->0);
    	
    	
    	size++;
    	return v;
    }

    @Override
    public V remove(K key) {
    	
    	SkipListNode<K, V> node = find(key);
    	
     	if(node == null || node.value == null || (compare(key, node.getKey())!=0)){
     		return null;
     	}

     	int level = node.level();
     	
     	V value = node.getValue();
     	SkipListNode<K, V> current = head;
     	
     	
     	do {
     		while(compare(key, current.nextNodes.get(level).getKey())<0) {
     			
     			current.nextNodes.remove(node);
     			
     		}
    		
     	}while(level-- > 0);
     	
     	size--;
        return value;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        
    	ArrayList<Entry<K, V>> entrySet = new ArrayList<Entry<K, V>>();
    	
    	
    	SkipListNode<K, V> current = head;
    	
    	for(int i=0;i < size(); i++) {
    		
    		SkipListNode<K, V> next = current.nextNodes.get(0);
    		
    		Entry<K, V> entry = new MapEntry<K, V>(next.getKey(), next.getValue());
    		entrySet.add(entry);

    		current = next;
    	}
    	 
        return entrySet;
    }
    
    // Returns the skiplist node with greatest value <= e
    private SkipListNode<K, V> find(K k) {
        return find(k, head, maxLevel);
    }

    // Returns the skiplist node with greatest value <= e
    // Starts at node start and level
    private SkipListNode<K, V> find(K k, SkipListNode<K, V> current, int level) {
        do{
        	current = findNext(k, current, level);
        }while(level-->0);
        
        return current;
    }

    // Returns the node at a given level with highest value less than e
    private SkipListNode<K, V> findNext(K k, SkipListNode<K, V> current, int level) {
        SkipListNode<K, V> next = current.nextNodes.get(level);
        while(next!=null) {
        	if(compare(k, (K) next.getKey())>=0) {
        		current = next;
        		next= current.nextNodes.get(level);
        	}
        	else {
        		break;
        	}
        }
        
        return current;
    }

    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        // Use find to get the value
    	return (find(key).getValue());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int level = 0;
        SkipListNode<K, V> current = head.nextNodes.get(level);

        while(current != null) {
            sb.append(current.toString()).append(", ");
            current = current.nextNodes.get(level);
        }
        sb.append(")");
        return sb.toString();
    }

    /******************************************************************************
     * Testing                                                                     *
     ******************************************************************************/

    public static void main(String[] args) {
        SkipList<Integer, String> testList = new SkipList<Integer, String>();
        System.out.println(testList);

        testList.put(4, "four");
        System.out.println(testList);
        testList.put(1, "one");
        System.out.println(testList);

        System.out.println("Get 1 "+ testList.get(1));
        
        for(int i=0;i<20;i++) {
        	testList.put(i, "test");
        }
        
        System.out.println(testList);
        
        System.out.println(testList.entrySet());
        testList.remove(0);
        
        System.out.println(testList);
        
        testList.remove(0);
        
        System.out.println(testList);
        
        testList.remove(5);
        
        System.out.println(testList);
//        for(Integer k : testList.keySet()) {
//            System.out.println(k);
//        }
//
//        for(String s : testList.values()) {
//            System.out.println(s);
//        }
    }


}