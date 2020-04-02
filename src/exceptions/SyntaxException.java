package exceptions;

/**
 * Thrown when syntax error in call or expression
 * or invalid symbols are presented
 *
 * @author Arina Fedorovskaya
 */

public class SyntaxException extends RuntimeException {

    /**
     * Constructs a {@code SyntaxException} with no detail message.
     */
    public SyntaxException(){
        super();
    }

    /**
     * Constructs a {@code SyntaxException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public SyntaxException(String message){
        super(message);
    }
}
