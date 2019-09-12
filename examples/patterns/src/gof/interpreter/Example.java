package gof.interpreter;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // ((a AND NOT b) OR c)

        final LogicalExpression root = new LogicalOrOperator(
                new LogicalAndOperator(
                        new LogicalVariable("a"),
                        new LogicalNotOperator(
                                new LogicalVariable("b")
                        )
                ),
                new LogicalVariable("c")
        );

        System.out.println(root);

        final Context context = new Context();
        context.setVariableToFalse("a");
        context.setVariableToTrue("b");
        context.setVariableToFalse("c");

        // = FALSE

        System.out.println(root.evaluate(context));
    }
}
