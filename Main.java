/**
 * Tests the implementation of the skip list
 * @author Ahmet Eren Incir
 *
 */
public class Main {
    public static void main(String[] args){
        MySkipList<Integer> test = new MySkipList<Integer>();

        for(int i = 0; i < 50; i++){
            test.add(i);
       }

        System.out.println(test.toString());
    }
}