public class App {
    public static void main(String[] args) {
        try{
        System.out.println("-----------------------");
        Compiler c1 = new Compiler("Java");
        c1.compile();
        System.out.println("-----------------------");
        Compiler c2 = new Compiler ("C++");
        c2.compile();
        System.out.println("-----------------------");
        Compiler c3 = new Compiler("ADA");
        c3.compile();}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
