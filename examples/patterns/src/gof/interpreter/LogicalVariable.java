package gof.interpreter;

public class LogicalVariable implements LogicalExpression {
    private final String name;

    public LogicalVariable(final String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(final Context context) {
        return context.getVariableValue(this.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
