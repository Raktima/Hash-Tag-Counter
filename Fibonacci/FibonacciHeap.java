package Fibonacci;

import Input.*;
import MainPackage.*;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Input.ReadInput.map;

/**
 * Created by Raktima on 11/4/2016.
 */

/* This class is resposible for performing all the heap related operations such as insert, increasekey, removemax etc*/
public class FibonacciHeap{

//    ReadInput r=new ReadInput();

    FibonacciHeapNode root;
    FibonacciHeapNode currptr;
    FibonacciHeapNode max;
    int count;
    public Map<String, Integer> deleted;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FibonacciHeap() {
        root = null;
    }

    public void insert(FibonacciHeapNode node)
    {
        //currptr=root;
      //FibonacciHeapNode node=new FibonacciHeapNode(item);
      if(root!=null)
      {
        node.leftpointer=currptr;
        node.rightpointer=root;
        root.leftpointer=node;
        currptr.rightpointer=node;
        currptr=node;
        if(node.getFrequency()>max.getFrequency()) {
            max = node;
            //System.out.println("max hash tag: " + node.getHashTag());
        }
      }
      else
      {
          root=node;
          root.rightpointer=root;
          root.leftpointer=root;
          max=root;
          currptr=root;

      }
      node.setChild(null);
      node.setParent(null);
      node.setChildCut(false);
      //count++;
    }

    public void increaseKey(FibonacciHeapNode node,int newfrequency) {
        //System.out.println("Entered with value :"+node.getHashTag());

        if (node.parent==null)
        {

            node.setFrequency(node.getFrequency()+newfrequency);
            if (node.getFrequency() > max.getFrequency()) {
                max = node;
            }

        }
        else
        {
            node.setFrequency(node.getFrequency()+newfrequency);

            if(node.parent!=null && node.frequency>node.parent.frequency)
            {
                FibonacciHeapNode childptr=node;
                while(/*childptr.parent!=null && !childptr.parent.childCut*/true)
                {

                    FibonacciHeapNode parPtr=childptr.getParent();
                    //System.out.println("Parent is :"+parPtr.getHashTag()+" and child is " + childptr.getHashTag());
                    boolean oldChildCut=parPtr.isChildCut();
                    //System.out.println("Parent is :"+parPtr.getHashTag()+" and childcut value is " + parPtr.isChildCut());
                    parPtr.degree--;
                    if(parPtr.parent!=null)
                        parPtr.childCut=true;
                    else
                        parPtr.childCut=false;

                    //else
                       // parPtr.childCut=false;


                    if(parPtr.child==childptr)
                    {
                        if(childptr.rightpointer==childptr)
                            parPtr.child=null;
                        else
                            parPtr.child=childptr.rightpointer;
                    }
                    if(childptr.rightpointer!=childptr)
                    {
                        childptr.rightpointer.leftpointer=childptr.leftpointer;
                        childptr.leftpointer.rightpointer=childptr.rightpointer;
                    }
                    insert(childptr);
                   if(!oldChildCut)
                        break;
                    childptr=parPtr;
                }

            }
        }
    }

    public List<String> findMaxFrequencies(int query) throws IOException {

        List<String> maxTags = new ArrayList<>();
          deleted = new HashMap<>();;
        for(int i=1;i<=query;i++) {
            FibonacciHeapNode node = removeMax();
            /*if(node.getHashTag().equals("#chirurgy"))
                System.out.println("Node is: "+node.getHashTag()+" and its parent is: "+ node.parent);*/
            int frequency = node.getFrequency();
            String hashTag = node.getHashTag();
            //System.out.println(ReadInput.map);
            map.remove(hashTag);
            maxTags.add(hashTag);
            deleted.put(hashTag,frequency);


        }


        /*for (Map.Entry<String, FibonacciHeapNode> entry : deleted.entrySet()) {
            ReadInput.insertIntoHashTable(entry.getKey(),String.valueOf(entry.getValue().frequency));
        }*/
        return maxTags;
    }

    private FibonacciHeapNode removeMax() {
        //max points to the maximum frequency node at all times
        FibonacciHeapNode maximumFrequencyNode=max;
        System.out.println("Max is:" +max.frequency +"of hashtag:"+ max.getHashTag());
        //if there is no tree in the heap
        if(maximumFrequencyNode==null)
            return null;
        //if there is only 1 tree in the heap
        else if(maximumFrequencyNode.rightpointer==maximumFrequencyNode)
        {
            root=null;
            max=null;
        }
        //if there are more than 1 tree
        else
        {//since its a doubly circular list we disconnect the maximum frequency node from the heap
            if(max==root)
                root=root.rightpointer;
            maximumFrequencyNode.leftpointer.rightpointer=maximumFrequencyNode.rightpointer;
            maximumFrequencyNode.rightpointer.leftpointer=maximumFrequencyNode.leftpointer;
            currptr=root;//finding out max frequency again after removing the node
            max=root;
            do
            {
               if(currptr.frequency>max.frequency)
                   max=currptr;
                currptr = currptr.rightpointer;
            } while (currptr != root && currptr!= null);

        }
        //count--;
        FibonacciHeapNode children=maximumFrequencyNode.getChild();
            join(children);
        return maximumFrequencyNode;
    }

    private void join(FibonacciHeapNode children) {
        FibonacciHeapNode childptr=children;
        /*This is the case when children are present
        We are making the parent field to null and making childcut
        values to false
         */
        if(children!=null)
        {
            if(max==null)
                max=children;
                do {
                    childptr.parent = null;
                    childptr.childCut = false;
                    if (childptr.frequency > max.frequency)
                        max = childptr;
                    childptr = childptr.rightpointer;
                } while (childptr != children);
                childptr = childptr.leftpointer;

        }
        /*If there is only 1 tree in the heap*/
        if(root==null)
        {
            root=children;
            meld();
            return;
        }
        /*If there are more than 1 trees then we will unite the 2
        circular lists*/
        else
        {
            if(children!=null)
            {
                currptr=root;
                do {
                    currptr=currptr.rightpointer;
                }while (currptr!=root);
                currptr=currptr.leftpointer;
                currptr.rightpointer=children;
                children.leftpointer=currptr;
                childptr.rightpointer=root;
                root.leftpointer=childptr;
                //root=children;
            }
            currptr=root;
            //Update max node
            do{
                if(currptr.frequency>max.frequency)
                    max=currptr;
                currptr=currptr.rightpointer;
            }while(currptr!=root);
            meld();
        }
    }
    private void meld() {
        if(root==null)
            return;
        int maxDegree=map.size();
        //System.out.println(maxDegree);
        FibonacciHeapNode degreeTable[]=new FibonacciHeapNode[maxDegree];
        FibonacciHeapNode v=root;
        FibonacciHeapNode w;
        FibonacciHeapNode combinedNode;
        int d;
        count=0;
        do {
            count++;
            v=v.rightpointer;
        }while(v!=root);
        //System.out.println("count is:"+count);
        for(int i=1;i<=count;i++)
        {
            d=v.degree;
            while(degreeTable[d]!=null)
            {

                w=degreeTable[d];
                v=linkNodes(v,w);

                degreeTable[d]=null;
                d=d+1;
            }
            degreeTable[d]=v;
            v=v.rightpointer;
        }
        root=null;
        max=null;

        for(int i=0;i<maxDegree;i++)
        {
            if(degreeTable[i]!=null)
            {
                insert(degreeTable[i]);
            }
        }
    }

    private FibonacciHeapNode linkNodes(FibonacciHeapNode v, FibonacciHeapNode w) {
        if(v.frequency>w.frequency)
        {
            w.leftpointer.rightpointer=w.rightpointer;
            w.rightpointer.leftpointer=w.leftpointer;
            v.degree++;
            w.parent=v;
            if(v.child==null)
            {
                v.child=w;
                w.leftpointer=w;
                w.rightpointer=w;
            }
            else
            {
                w.leftpointer=v.child;
                w.rightpointer=v.child.rightpointer;
                v.child.rightpointer=w;
                w.rightpointer.leftpointer=w;
            }
            v.childCut=false;
            return v;
        }
        else
        {
            /*v.leftpointer.rightpointer=v.rightpointer;
            v.rightpointer.leftpointer=v.leftpointer;
            w.degree++;
            v.parent=w;
            */
            w.leftpointer.rightpointer=w.rightpointer;
            w.rightpointer.leftpointer=w.leftpointer;
            v.leftpointer.rightpointer=w;
            v.rightpointer.leftpointer=w;
            w.leftpointer=v.leftpointer;
            w.rightpointer=v.rightpointer;
            w.degree++;
            v.parent=w;
            if(w.child==null)
            {
                w.child=v;
                v.leftpointer=v;
                v.rightpointer=v;
            }
            else
            {
                v.leftpointer=w.child;
                v.rightpointer=w.child.rightpointer;
                w.child.rightpointer=v;
                v.rightpointer.leftpointer=v;
            }
            w.childCut=false;
            return w;
        }
    }
    private int maximumDegree() {
        currptr = root;
        int maxdegree = root.degree;
        do {
            if (currptr.degree > maxdegree)
                maxdegree = currptr.degree;
            currptr = currptr.rightpointer;
        }while (currptr!=root && currptr!=null);
        return maxdegree;
    }

}
