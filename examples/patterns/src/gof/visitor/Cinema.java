package gof.visitor;

public class Cinema implements Place {
    @Override
    public void accept(final Visitor visitor) {
        System.out.println("do kina přišel " + visitor.toString());
        visitor.visit(this);
    }
}
