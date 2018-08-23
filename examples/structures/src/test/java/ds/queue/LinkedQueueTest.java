package ds.queue;

public class LinkedQueueTest extends AbstractQueueTest {
    @Override
    protected Queue<String> createInstance() {
        return new LinkedQueue<>();
    }
}
