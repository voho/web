package gof.interpreter;

public class LogicalOrOperator implements LogicalExpression {
    private final LogicalExpression leftOperand;
    private final LogicalExpression rightOperand;

    public LogicalOrOperator(final LogicalExpression leftOperand, final LogicalExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public boolean evaluate(final Context context) {
        return leftOperand.evaluate(context) || rightOperand.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(%s OR %s)", leftOperand, rightOperand);
    }
}
