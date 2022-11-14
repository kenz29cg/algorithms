import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator();}

    private class DequeIterator implements Iterator<Item>
    {
	private Node current = first;

	public boolean hasNext() {return current != null; }
	public void remove() { throw new UnsupportedOperationException("Not supported."); }
	public Item next()
	{
	    
	    if (current == null) { throw new NoSuchElementException("No such element."); }
	    else{
		Item item = current.item;
		current = current.next;
		return item;
	    }
	}
    }

    private Node first;
    private Node last;
    private int N = 0;
    
    private class Node{
        Item item;
	Node next;
    }

    // construct an empty deque
    public Deque(){
	first = null;
	last = first;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return first == null;   
    }
    
    // return the number of items on the deque
    public int size()
    {
	return N;
	/*
	if (first == null) { return 0; }
	else{
	    int n = 1;
	    for (Node current = first; current != last; current = current.next)
		{
		    n++;
		}
	    return n;
	}
	*/
    }
    
    // add the item to the front
    public void addFirst(Item item){
	if (first == null) { first = new Node(); first.item = item; last = first; }
	else if (first == last) { first = new Node(); first.next = last; first.item = item; last.next = null; }
	else{
	    Node oldFirst = first;
	    first = new Node();
	    first.next = oldFirst;
	    first.item = item;
	}
	N++;
    }
    
    // add the item to the back
    public void addLast(Item item){
	if (first == null) { first = new Node(); first.item = item; last = first; last.next = null; }
	else{
	    if (last == first) { last = new Node(); first.next = last; last.item = item; }
	    Node oldLast = last;
	    last = new Node();
	    oldLast.next = last;
	    last.item = item;
	}
	N++;
    }
    
    // remove and return the item from the front
    public Item removeFirst(){
        if (first == null) { throw new NoSuchElementException("No such element."); }
	else{
	    if (last == first) { Item item = first.item; first = null; N--; return item; }
	    Item item = first.item;
	    first = first.next;
	    N--;
	    return item;
	}	
    }
    
    // remove and return the item from the back
    public Item removeLast(){
	if (first == null) { throw new NoSuchElementException("No such element."); }
	else{
	    if (last == first) { Item item = first.item; first = null; N--; return item; }
	    Item item = last.item;
	    Node secondLast = first;
	    while(secondLast.next != last) { secondLast = secondLast.next; }
	    last = secondLast;
	    N--;
	    return item;
	}	
    } // here we don't have a constant worst-case time
    
    // unit testing (required)
    public static void main(String[] args){
        Deque<String> deq = new Deque<String>();
	StdOut.println("Deque is empty?" + deq.isEmpty());
	StdOut.println(deq.first);
	StdOut.println("Deque has size: " + deq.size());
	deq.addFirst("to");
	StdOut.println(deq.first.item);
	StdOut.println(deq.last.item);
        StdOut.println("Deque has size: " + deq.size());
	deq.addFirst("be");
        StdOut.println(deq.first.item);
	StdOut.println(deq.last.item);
        StdOut.println("Deque has size: " + deq.size());
	deq.addLast("or");
        StdOut.println(deq.first.item);
	StdOut.println(deq.last.item);
        StdOut.println("Deque has size: " + deq.size());
	StdOut.println("Iterator Testing:");
	for(String s : deq) {StdOut.println(s);}
	StdOut.println(deq.removeFirst());
	StdOut.println(deq.first.item);
	StdOut.println(deq.last.item);
        StdOut.println("Deque has size: " + deq.size());
	StdOut.println(deq.removeLast());
	StdOut.println(deq.first.item);
	StdOut.println(deq.last.item);
        StdOut.println("Deque has size: " + deq.size());
    }
    
}
