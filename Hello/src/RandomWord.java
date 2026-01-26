package Hello.src;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/* *****************************************************************************
 *  Name:              morningshiftcoffee
 *  Coursera User ID:  morningshiftcoffee
 *  Last modified:     Jan 24, 2026
 **************************************************************************** */
public class RandomWord {
    public static void main(String[] args) {
        int wordCount = 0;
        String chosenWord = "";
        while (!StdIn.isEmpty()) {
            String nextWord = StdIn.readString();
            wordCount += 1;
            if (StdRandom.bernoulli((double) 1 / wordCount)) {
                chosenWord = nextWord;
            }
        }
        StdOut.println(chosenWord);
    }
}
