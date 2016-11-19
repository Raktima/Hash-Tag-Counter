package Input;
import Fibonacci.FibonacciHeap;
import Fibonacci.FibonacciHeapNode;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raktima on 11/4/2016.
 */

/* This class performs the read operation from the sample input file and eprforms various operations on them*/
public class ReadInput {

    FileInputStream in = null;
    FileOutputStream out = null;
    public static Map<String, FibonacciHeapNode>  map = new HashMap<>();//Hash map for storing the hash tag values and the node pointers
    //Map<String, String> demo = new HashMap<>();
    FibonacciHeapNode node;
    FibonacciHeap heap=new FibonacciHeap();
    BufferedWriter bw = null;

    public Map<String, FibonacciHeapNode> getMap() {
        return map;
    }

    public void setMap(Map<String, FibonacciHeapNode> map) {
        this.map = map;
    }

    public void readSample() throws IOException { //reads the input file line by line

        in = new FileInputStream("src/demoInput.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = br.readLine()) != null) {
            //System.out.println("Line is:"+ line);
            if (line.contains("STOP")) {
                System.out.println("Exitting Program...");
                break;
            } else
                extractCharacter(line);
        }
        br.close();

    }

    public void insertIntoHashTable(String part1, String part2) throws IOException {
    /*    if(map.isEmpty())
            heap=new FibonacciHeap();*/
        //System.out.println("In here for "+ part1);

        if (!map.containsKey(part1)) {
            /*if(part1.equals("#chon"))
                System.out.println("In if "+Integer.parseInt(part2));*/
            FibonacciHeapNode node = new FibonacciHeapNode();

            node.setFrequency(Integer.parseInt(part2));
            node.setDegree(0);
            node.setHashTag(part1);
            heap.insert(node);
            map.put(part1, node);
            //System.out.println("Node name "+ node.getHashTag()+"node address is" + node);
        } else {
            node=map.get(part1);
           /* if(node.getHashTag().equals("#chirurgy"))
                System.out.println("In else "+Integer.parseInt(part2));*/
            heap.increaseKey(node, Integer.parseInt(part2));
           if(node.getHashTag().equals("#choke"))
                System.out.println("In read "+node.getFrequency() + " Node location is: " +node);
        }

    }

    public void extractCharacter(String line) throws IOException {

        char ch;
        int length = line.length();
        int query;
        String parts[] = line.split(" ");
        ch = line.charAt(0);
        if (ch == '#') {
            String st=parts[0];
            insertIntoHashTable(st, parts[1]);
        } else if ((query = Integer.parseInt(line)) <= 20) {
            List<String> hashtags = heap.findMaxFrequencies(query);
            //if(map.containsKey("#chirurgy"))
               // System.out.println("Still contains");
            for (Map.Entry<String, Integer> entry : heap.deleted.entrySet()) {
                insertIntoHashTable(entry.getKey(),String.valueOf(entry.getValue()));
        }

            StringBuilder hash = new StringBuilder();
            for (int i = 0; i < hashtags.size(); i++) {
                hash.append(hashtags.get(i)).append(",");
            }
            hash.deleteCharAt(hash.length() - 1);
            System.out.println("The top " + query + " results are:" + hash.toString());
        }
    }

}

