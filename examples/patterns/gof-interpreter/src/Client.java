public class Client {
    public static void main(final String[] args) {
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
