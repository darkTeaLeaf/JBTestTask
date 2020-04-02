package expressions;

/**
 * Expression which represent constant variable
 *
 * @author Arina Fedorovskaya
 */

public class ConstantExpression extends SingleExpression {
    private int value;

    /**
     * Constructor a {@code ConstantExpression}
     *
     * @param value number of current constant variable
     */
    public ConstantExpression(int value){
        this.value = value;
        this.type = ExpressionType.ARITHMETIC;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
