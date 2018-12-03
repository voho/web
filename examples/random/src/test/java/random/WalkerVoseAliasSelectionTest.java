package random;

public class WalkerVoseAliasSelectionTest extends AbstractSelectionTest {
    @Override
    protected int randomIndex(final double[] weights) {
        // normally, we would NOT initialize each time!
        final WalkerVoseAliasSelection selection = new WalkerVoseAliasSelection();
        selection.initialize(weights);

        return selection.sample();
    }
}