package gof.template;

public class Worker extends Person {
    protected void wake() {
        System.out.println("Waking up at 6:00.");
    }

    protected void work() {
        System.out.println("Going to work. Working...");
    }

    protected void relax() {
        System.out.println("Watching stupid movies.");
    }

    protected void sleep() {
        System.out.println("Going to bed.");
    }
}