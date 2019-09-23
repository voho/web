package gof.template;

public abstract class Person {
    public void printDailyRoutine() {
        // this is a template method
        wake();
        work();
        relax();
        sleep();
    }

    abstract protected void wake();

    abstract protected void work();

    abstract protected void relax();

    abstract protected void sleep();
}