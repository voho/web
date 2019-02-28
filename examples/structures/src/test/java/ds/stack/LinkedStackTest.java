package ds.stack;

public class LinkedStackTest extends AbstractStackTest {
    @Override
    protected Stack<String> createInstance() {
        return new LinkedStack<>();
    }
}
