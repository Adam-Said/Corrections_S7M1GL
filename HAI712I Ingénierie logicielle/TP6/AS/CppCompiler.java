public class CppCompiler extends AbstractCompiler {
    
    public Lexer newLexer(){
        return new CppLexer();
    }
	public Parser newParser(){
        return new CppParser();
    }
	public Generator newGenerator(){
        return new CppGenerator();
    }

    public class CppLexer extends Lexer {
        @Override
        public void scan() {
            System.out.println("Scanning a cpp programm...");
        }
    }


    public class CppParser extends Parser {
        @Override
        public void parse() {
            System.out.println("Parsing a cpp programm...");
        }
    }

    public class CppGenerator extends Generator {
        @Override
        public void generate() {
            System.out.println("Generating a cpp programm...");
        }
    }
}
