package DequesAndRandomizedQueues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// A randomized queue is similar to a stack or queue, except that the item
// removed is chosen uniformly at random among items in the data structure.
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private int capacity = 10;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int newCapacity) {
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
        capacity = newCapacity;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }
        items[size++] = item;
        if (size == capacity) {
            capacity = capacity * 2;
            resize(capacity);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }

        int randomIndex = StdRandom.uniformInt(size--);
        Item randomItem = items[randomIndex];
        items[randomIndex] = items[size];
        items[size] = null;

        if (size < capacity / 4) {
            capacity = capacity / 2;
            resize(capacity);
        }
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int currentLeft = size;
        Item[] randomizedItems = (Item[]) new Object[size];

        // deep copy the items
        public RandomizedQueueIterator() {
            for (int i = 0; i < size; i++) {
                randomizedItems[i] = items[i];
            }
        }

        public boolean hasNext() {
            return currentLeft > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there's no next item");
            }
            int chosenIndex = StdRandom.uniformInt(0, currentLeft);
            Item chosenItem = randomizedItems[chosenIndex];

            randomizedItems[chosenIndex]  = randomizedItems[currentLeft - 1];
            randomizedItems[currentLeft - 1] = null;
            currentLeft--;
            return chosenItem;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("apple");
        queue.enqueue("pear");
        queue.enqueue("orange");
        queue.enqueue("watermelon");
        queue.enqueue("cranberry");
        queue.enqueue("Avocado");
        queue.enqueue("blueberry");
        queue.enqueue("mango");
        queue.enqueue("lychee");
        queue.enqueue("strawberry");
        queue.enqueue("lemon");
        queue.enqueue("lime");
        for (String element : queue) {
            StdOut.printf("%s\n", element);
        }
        StdOut.printf("queue has %d elements\n", queue.size());
        StdOut.printf("a sample element is %s\n", queue.sample());
        while (!queue.isEmpty()) {
            StdOut.printf("dequeue element %s\n", queue.dequeue());
        }
    }
}
