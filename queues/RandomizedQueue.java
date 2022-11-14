import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

    public Iterator<Item> iterator() { return new RandomizedQueueIterator();}

    // return an independent iterator over items in random order
    private class RandomizedQueueIterator implements Iterator<Item>
    {
	private Node current = first;
	private int[] pmt = StdRandom.permutation(N);
	private int position = 0;
        	

	public boolean hasNext() {return (position < N); }
	public void remove() { throw new UnsupportedOperationException("Not supported."); }
	public Item next()
	{
	    
	    if (N == 0) { throw new NoSuchElementException("No such element."); }	   
	    else{
		Node current = first;
		for(int i = 0; i < pmt[position]; i++){  current = current.next; }
	        position++;
		Item item = current.item;
		return item;
	    }
	}
    }

    private int N = 0; // number of items
    private Node first;
    private Node last;

    private class Node{
	Item item;
	Node next;
    }

    

    // construct an empty randomized queue
    public RandomizedQueue(){
	first = null;
	last = first;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){ return first == null; }
    
    // return the number of items on the randomized queue
    public int size(){ return N; }

    // add the item
    public void enqueue(Item item){
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

    // remove and return a random item
    public Item dequeue(){

	if (first == null) { throw new NoSuchElementException("No such element."); }
	else{
	    if (last == first) { Item item = first.item; first = null; N--; return item; }           
	    int randomP = StdRandom.uniformInt(N); // N >= 2
	    if (randomP == 0) {
		Item item = first.item;
		first = first.next;
		N--;
		return item;
	    }
	    else if (randomP == N-1){
		Item item = last.item;
		Node secondLast = first;
		while(secondLast.next != last) { secondLast = secondLast.next; }
		last = secondLast;
		N--;
		return item;
	    }
	    else{
		Node current;
		Node beforeCurrent = first;
		for(int i = 0; i < randomP-1; i++){
		    beforeCurrent = beforeCurrent.next;
		}
		current = beforeCurrent.next;
		Item item = current.item;
		beforeCurrent.next = current.next;
		N--;
		return item;
	    }
	}
    }

    // return a random item (but do not remove it)
    public Item sample(){
	if (first == null) { throw new NoSuchElementException("No such element."); }
	else{
	    int randomP = StdRandom.uniformInt(N);
	    Node current = first;
	    for (int i = 0; i < randomP; i++){
		current = current.next;
	    }
	    Item item = current.item;
	    return item;
	}
    }
    /*
    private void LinkedListtoArray(){
	if (first == null) { throw new NoSuchElementException("No such element."); }
	a = new Item[N];
	Node current = first;
	for(int i = 0; i < N; i++){ a[i] = current.item; current = current.next; }	
    }
    */

    // unit testing (required)
    public static void main(String[] args){
	RandomizedQueue<String> rQ = new RandomizedQueue<String>();
	StdOut.println("rQ is empty?" + rQ.isEmpty());
	StdOut.println(rQ.first);
	StdOut.println("rQ has size: " + rQ.size());
	rQ.enqueue("or");
	StdOut.println(rQ.first.item);
	StdOut.println(rQ.last.item);
        StdOut.println("rQ has size: " + rQ.size());
	rQ.enqueue("be");
        StdOut.println(rQ.first.item);
	StdOut.println(rQ.last.item);
        StdOut.println("rQ has size: " + rQ.size());
	rQ.enqueue("to");
        StdOut.println(rQ.first.item);
	StdOut.println(rQ.last.item);
        StdOut.println("rQ has size: " + rQ.size());
	StdOut.println("Iterator Testing:");
	for(String s : rQ) {StdOut.println(s);}
	StdOut.println("Sample: " + rQ.sample());
	StdOut.println(rQ.first.item);
	StdOut.println(rQ.last.item);
        StdOut.println("rQ has size: " + rQ.size());
	StdOut.println(rQ.dequeue());
	StdOut.println(rQ.first.item);
	StdOut.println(rQ.last.item);
        StdOut.println("rQ has size: " + rQ.size());
    }



}
