public class HelloGoodbye {
     
    public static void main(String[] args) {
        if(args.length == 2){
            String name1 = args[0];
            String name2 = args[1];
            System.out.println(String.format("Hello %s and %s.", name1, name2));
            System.out.println(String.format("Goodbye %s and %s.", name2, name1));                        
        } else{
            System.out.println("Not enough args were entered!");
        } 
    }
}
