public class LogicalNotOperator implements LogicalExpression {
    private final LogicalExpression operand;

    public LogicalNotOperator(final LogicalExpression operand) {
        this.operand = operand;
    }

    @Override
    public boolean evaluate(final Context context) {
        return !operand.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("NOT %s", operand);
    }
}
