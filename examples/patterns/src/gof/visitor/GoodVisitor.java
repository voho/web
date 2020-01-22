package gof.visitor;

public class GoodVisitor implements Visitor {
    @Override
    public void visit(final Museum museum) {
        System.out.println(this.toString() + " je v muzeu");
    }

    @Override
    public void visit(final Cinema cinema) {
        System.out.println(this.toString() + " je v kině");
    }

    @Override
    public String toString() {
        return "pan Hodný";
    }
}
