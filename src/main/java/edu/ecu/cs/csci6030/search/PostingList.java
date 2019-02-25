package edu.ecu.cs.csci6030.search;


import java.util.Arrays;

/**
 *The positional postings for a term.  It is an integer array of term positions.
 *
 * @author Mike Finch
 *
 */
public class PostingList {
    private int[] listings;
    private int capacity = 20;
    private int size = 0;

    public PostingList() {
        listings = new int[capacity];

    }

    public int size() {
        return this.size;
    }

    public PostingList add(int i) {
        if (size == 0) {
            listings[size++] = i;
        } else {
            if (i > listings[size - 1]) {
                if (capacity == size) doubleCapacity();
                listings[size++] = i;
            } else {
                throw new UnexpectedPositionAddException("Item at position" + i + "added out of sequence");
            }
        }
        return this;
    }

    private void doubleCapacity(){
        listings = Arrays.copyOf(this.listings, capacity*=2);
    }

    public int[] getList() {
        return Arrays.copyOf(this.listings, size);
    }

    public PostingList clone() {
        PostingList clone = new PostingList();
        clone.listings = Arrays.copyOf(this.listings, size);
        clone.size = this.size;
        clone.capacity = this.size;
        return clone;
    }
}
