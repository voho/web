package gof.visitor;

public class EvilVisitor implements Visitor {
    @Override
    public void visit(final Museum museum) {
        System.out.println(this.toString() + " vykradl muzeum.");
    }

    @Override
    public void visit(final Cinema cinema) {
        System.out.println(this.toString() + " udělal nepořádek v kině.");
    }

    @Override
    public String toString() {
        return "pan Zlý";
    }
}
