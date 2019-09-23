package gof.template;

public class Child extends Person {
    protected void wake() {
        System.out.println("Waking up at 7:00.");
    }

    protected void work() {
        System.out.println("Going to school.");
    }

    protected void relax() {
        System.out.println("Playing with other kids.");
    }

    protected void sleep() {
        System.out.println("Going to bed.");
    }
}