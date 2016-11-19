package Fibonacci;

/**
 * Created by Raktima on 11/4/2016.
 */

/* This class contains the data structures of a single node in the fibonacci heap*/
public class FibonacciHeapNode {



    FibonacciHeapNode parent;
    FibonacciHeapNode child;
    FibonacciHeapNode leftpointer;
    FibonacciHeapNode rightpointer;
    int item;
    int frequency;
    boolean childCut;
    int degree;

    String hashTag;


    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
    public FibonacciHeapNode getChild() {
        return child;
    }

    public void setChild(FibonacciHeapNode node) {
        this.child = child;
    }
    public FibonacciHeapNode getParent() {
        return parent;
    }


    public void setParent(FibonacciHeapNode parent) {
        this.parent = parent;
    }
    public boolean isChildCut() {
        return childCut;
    }

    public void setChildCut(boolean childCut) {
        this.childCut = childCut;
    }

}
