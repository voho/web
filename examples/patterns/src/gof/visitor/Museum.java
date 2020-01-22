package gof.visitor;

public class Museum implements Place {
    @Override
    public void accept(final Visitor visitor) {
        System.out.println("do muzea přišel " + visitor.toString());
        visitor.visit(this);
    }
}
