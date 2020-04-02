package expressions;

public class ConstantExpression extends SingleExpression {
    private int value;

    public ConstantExpression(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
