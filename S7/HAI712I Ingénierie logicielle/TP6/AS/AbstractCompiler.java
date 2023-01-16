
public abstract class AbstractCompiler {
    
	public abstract Lexer newLexer();
	public abstract Parser newParser();
	public abstract Generator newGenerator();

    public static AbstractCompiler compile(String language) throws NotSupportedLanguageException {
        if(language.equals("Java")) {
            return new JavaCompiler();
        }
        else if (language.equals("C++")) {
            return new CppCompiler();
        }
        else {
            throw new NotSupportedLanguageException("Error: "+ language + " is not supported yet !");
        }
    }

}
