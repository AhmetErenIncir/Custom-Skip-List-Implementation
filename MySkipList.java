import java.util.Arrays;
import java.util.Random;

public class MySkipList<E extends Comparable<E>> {
    /**
     * Head of the skip-list
     */
    public Node<E> head;
    /**
     * Size of the skip list
     */
    private int size;
    /**
     * The maximum level of the skip-list
     */
    private int maxLevel;
    /**
     * Smallest power of 2 that is greater than the current skip-list size
     */
    private int maxCap;
    /**
     * Natural log of 2
     */
    static final double LOG2 = Math.log(2.0);
    /**
     * Minimum possible integer value for the head
     */
    static final int MIN = Integer.MIN_VALUE;
    /**
     * Random number generator
     */
    private Random rand = new Random();

    //Constructor

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MySkipList(){
        size = 0;
        maxLevel = 0;
        maxCap = computeMaxCap(maxLevel);
        head = new Node(maxLevel, MIN);
    }

    /**
     * Search for an item in the list
     * @param target The item being sought
     * @return An Node array which references the predecessors of the target at each level.
     */
    @SuppressWarnings("unchecked")
    private Node<E>[] search(E target){
        Node<E>[] pred = (Node<E>[]) new Node[maxLevel];
        Node<E> current = head;
        for(int i = current.links.length - 1; i >= 0; i--){
            while(current.links[i] != null
                    && current.links[i].less(target)){
                current = current.links[i];
            }
            pred[i] = current;
        }
        return pred;
    }

    /**
     * Find an object in the skip-list
     * @param target The item being sought
     * @return A reference to the object in the skip-list that matches
     * 		   the target. If not found, null is returned
     */
    public E find(E target){
        Node<E>[] pred = search(target);
        if(pred[0].links != null && pred[0].links[0].contains(target)){
            return pred[0].links[0].find(target);
        } else {
            return null;
        }
    }

    /**
     * Inserts the new element into list
     * @param item The element to add
     * @return true as the item is added
     */
    boolean add(E item){
        size++;
        Node<E>[] pred = search(item);
        if(size > maxCap){
            maxLevel++;
            maxCap = computeMaxCap(maxLevel);
            head.links = Arrays.copyOf(head.links, maxLevel);
            pred = Arrays.copyOf(pred, maxLevel);
            pred[maxLevel - 1] = head;
        }
        Node<E> newNode = new Node<E>(logRandom(), item);
        for(int i = 0; i < newNode.links.length; i++){
            newNode.links[i] = pred[i].links[i];
            pred[i].links[i] = newNode;
        }
        return true;
    }



    /**
     * @return a random logarithmic distributed int between 1 and maxLevel
     */
    private int logRandom(){
        int r = rand.nextInt(maxCap);
        int k = (int) (Math.log(r + 1) / LOG2);
        if(k > maxLevel - 1)
            k = maxLevel - 1;
        return maxLevel - k;
    }

    /**
     * @param level of list
     * @return nev max cap
     */
    private int computeMaxCap(int level){
        return (int) Math.pow(2, level) - 1;
    }

    /**
     * Prints the list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String toString(){
        if(size == 0)
            return "Empty";
        StringBuilder sc = new StringBuilder();
        Node itr = head;
        sc.append("Head: " + maxLevel);
        int lineMaker = 0;
        while(itr.links[0] != null){
            itr = itr.links[0];
            sc.append(" --> " + itr.toString());
            lineMaker++;
            if(lineMaker == 10){
                sc.append("\n");
                lineMaker = 0;
            }
        }
        return sc.toString();
    }

    /**
     * Inner Node class
     * @param <E>
     */
    static class Node<E> {
        Node<E>[] links;
        E[] MultiData;
        int maxData;
        int numElement;

        /**
         * Create a node of level m
         * @param m The level of the node
         * @param data The data to be stored
         */
        @SuppressWarnings("unchecked")
        public Node(int m, E data){
            maxData = 10;
            if(m==1){
                add(data);
            }
            else{
                numElement = 0;
                MultiData = (E[]) new Object[maxData];
                links = (Node<E>[]) new Node[m];
                MultiData[numElement] = data;
                numElement++;
            }

        }

        /**
         * Insert the data to Skip list.
         * @param data data
         */
        @SuppressWarnings("unchecked")
        public void add(E data){
            if(MultiData == null){
                MultiData = (E[]) new Object[maxData];
                links = (Node<E>[]) new Node[1];
                numElement = 0;
            }
            if(numElement<maxData){
                MultiData[numElement] = data;
                numElement++;
            }
            else
                return;
        }

        /**
         * @param target
         * @return true if  target is in node.
         */
        public boolean contains(E target){
            for (int i=0 ; i<numElement ; i++){
                if(target.equals(MultiData[i])){
                    return true;
                }
            }
            return false;
        }

        /**
         * Search the target in node's elements
         * @param target
         * @return target if node contains it
         */
        public E find(E target){

            for (int i=0 ; i<numElement ; i++){
                if(target.equals(MultiData[i])){
                    return MultiData[i];
                }
            }

            return null;
        }

        /**
         * @param target
         * @return false if current element less than target
         */
        public boolean less(E target){

            for (int i=0 ; i<numElement ; i++){
                if((Integer) target > (Integer) MultiData[i]){
                    return false;
                }
            }

            return true;
        }

        public String toString(){
            StringBuilder temp = new StringBuilder();
            for (int i=0 ; i<numElement ; i++){
                temp.append(MultiData[i]).append(" , ");
            }

            return (temp + " |" + links.length + "|");
        }


    }
}