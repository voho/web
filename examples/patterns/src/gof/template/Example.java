package gof.template;

import org.junit.Test;

public class Example {
    @Test
    public void testWorker() {
        final Person worker = new Worker();
        worker.printDailyRoutine();

        // -- prints --
        // Waking up at 6:00.
        // Going to work. Working...
        // Watching stupid movies.
        // Going to bed.
    }

    @Test
    public void testChild() {
        final Person child = new Child();
        child.printDailyRoutine();

        // -- prints --
        // Waking up at 7:00.
        // Going to school.
        // Playing with other kids.
        // Going to bed.
    }
}
