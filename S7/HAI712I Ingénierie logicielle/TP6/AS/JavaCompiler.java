public class JavaCompiler extends AbstractCompiler {
    
    public Lexer newLexer(){
        return new JavaLexer();
    }
	public Parser newParser(){
        return new JavaParser();
    }
	public Generator newGenerator(){
        return new JavaGenerator();
    }

    public class JavaLexer extends Lexer {
        @Override
        public void scan() {
            System.out.println("Scanning a java programm...");
        }
    }


    public class JavaParser extends Parser {
        @Override
        public void parse() {
            System.out.println("Parsing a java programm...");
        }
    }

    public class JavaGenerator extends Generator {
        @Override
        public void generate() {
            System.out.println("Generating a java programm...");
        }
    }
}
