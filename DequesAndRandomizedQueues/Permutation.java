package DequesAndRandomizedQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Permutation takes an integer k as a command-line argument; reads a sequence
// of strings from standard input using StdIn.readString(); and prints exactly
// k of them, uniformly at random. Print each item from the sequence at most once.
public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        // corner case
        if (k == 0) {
            return;
        }
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        int outputCount = 0;

        for (String item : randomizedQueue) {
            StdOut.printf("%s\n", item);
            outputCount++;
            if (outputCount == k) {
                break;
            }
        }
    }
}

