import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
            
        String champion = "";
        String inputWord;
        int wordNum = 1;
        while(!StdIn.isEmpty()){
            inputWord = StdIn.readString();
            if(StdRandom.bernoulli((double)1/wordNum)){
                champion = inputWord;
            }
            wordNum += 1;
        }
        StdOut.println(champion);
    }
}
