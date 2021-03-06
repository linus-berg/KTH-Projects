/*
 * This implements a FIFO Queue in a class using generics.
*/
import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class FIFOQueue<T> implements Iterable<T> {
  private static class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;
  }

  private Node<T> first;
  private Node<T> last;
  private int total_nodes;

  public FIFOQueue() {
    first = null;
    last  = null;
    total_nodes = 0;
  }
  
  /**
    * Add item to the back of the queue.
    * @author Linus Berg 
    * @param <T> : Item to add.
    * @date 12/09/2018
    */
  public void Add(T item) {
    Node<T> old = this.last;
    this.last = new Node<T>();
    this.last.item = item;
    /* If first is null, then this is the first item ever added. */ 
    if (first == null) {
      first = this.last;
    }
    if (old != null) {
      old.next = this.last;
      first.previous = null;
    }
    this.last.next = null;
    this.last.previous = old;
    total_nodes++;
  }
 
  /**
    * Pop element that came in first (FIFO).
    * @author Linus Berg 
    * @return <T> : Item in first node.  
    * @date 12/09/2018
    */
  public T Pop() {
    Node<T> old_first = this.first;
    this.first = old_first.next;
    total_nodes--;
    return old_first.item;
  }
 

  /**
    * Remove element at index.
    * @author Linus Berg 
    * @param int : Index of the item to remove, latest added item is index 1.  
    * @date 12/09/2018
    */
  public void Remove(int index) {
    int current_index = total_nodes; 
    Iterator<T> i = this.iterator();
    while (i.hasNext()) {
      if (index == current_index) {
        i.remove();
        break;
      }
      current_index--;
      i.next();
    }
  }

  public String toString() {
    StringBuilder data = new StringBuilder();
    int i = 0;
    for(T item : this) {
      data.append("[");
      data.append(item);
      data.append("]");
      data.append(" ");
    }
    return data.toString();
  }

  public Iterator<T> iterator()  {
        return new ListIterator<T>(first);  
    }

  private class ListIterator<E> implements Iterator<E> {
    private Node<E> current;

    public ListIterator(Node<E> first) {
      current = first;
    }

    public boolean hasNext()  {
      return current != null;
    }
    
    public void remove() {
      Node<E> deleted = current;
      if (deleted.previous != null) {
        deleted.previous.next = deleted.next;
      } else {
        /* If previous is null then we are on the first node. */
        FIFOQueue.this.first = FIFOQueue.this.first.next;
      }
      if (deleted.next != null) {
        deleted.next.previous = deleted.previous;
      } else {
        /* If next is null then we are on the last node. */
        FIFOQueue.this.last = FIFOQueue.this.last.previous;
      }
      total_nodes--;
    }

    public E next() {
      if (!hasNext()) throw new NoSuchElementException();
      E item = current.item;
      current = current.next; 
      return item;
    }
  }
  
  public static void main(String[] args) {
    FIFOQueue<Integer> test = new FIFOQueue<Integer>();
    test.Add(Integer.valueOf(100));
    test.Add(Integer.valueOf(340));
    test.Add(Integer.valueOf(210));
    test.Add(Integer.valueOf(128));
    test.Remove(1);
    test.Add(Integer.valueOf(256));
    test.Remove(2);
    System.out.println(test.toString());
  }
}
