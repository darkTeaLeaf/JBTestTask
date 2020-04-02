package exceptions;

/**
 * Thrown when user tries to put non-boolean expression
 * in filter call or non-arithmetic expression in map call
 * when user tries to apply logic operation on arithmetic
 * expression or arithmetic operation on boolean type expression
 * or comparison operation on boolean type expression
 *
* @author Arina Fedorovskaya
 */

public class InvalidTypeException extends RuntimeException{

    /**
     * Constructs a {@code InvalidTypeException} with no detail message.
     */
    public InvalidTypeException(){
        super();
    }

    /**
     * Constructs a {@code InvalidTypeException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public InvalidTypeException(String message){
        super(message);
    }
}
