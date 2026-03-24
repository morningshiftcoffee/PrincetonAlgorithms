package DequesAndRandomizedQueues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// A double-ended queue or deque (pronounced “deck”) is a generalization of a
// stack and a queue that supports adding and removing items from either the
// front or the back of the data structure.
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    // Node for double linked list
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null");
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null");
        }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.previous = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is empty");
        }

        Node oldFirst = first;

        // if the deque only has 1 node, return it. And set first/last to null
        if (first.next == null) {
            first = null;
            last = null;
            size--;
            return oldFirst.item;
        }

        first = oldFirst.next;
        first.previous = null;
        Item item = oldFirst.item;
        size--;

        // avoid loitering
        oldFirst.next = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is empty");
        }

        Node oldLast = last;
        if (last.previous == null) {
            first = null;
            last = null;
            size--;
            return oldLast.item;
        }

        last = oldLast.previous;
        last.next = null;
        Item item = oldLast.item;
        size--;

        // avoid loitering
        oldLast.previous = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there's no next item");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        if (!deque.isEmpty()) {
            StdOut.printf("The deque should be empty after initialized");
        }
        deque.addFirst("apple");
        Iterator<String> iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        deque.addLast("orange");
        iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        deque.addFirst("watermelon");
        iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        StdOut.printf("Removed first item %s\n", deque.removeFirst());
        iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        StdOut.printf("Removed last item %s\n", deque.removeLast());
        iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        deque.addLast("blueberry");
        iterator = deque.iterator();
        StdOut.printf("Print all items:\n");
        for (String element : deque) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("==============\n");
        StdOut.printf("current size of items is %d\n", deque.size());
    }
}
