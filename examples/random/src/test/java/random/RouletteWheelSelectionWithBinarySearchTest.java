package random;

public class RouletteWheelSelectionWithBinarySearchTest extends AbstractSelectionTest {
    @Override
    protected int randomIndex(final double[] weights) {
        return RouletteWheelSelectionWithBinarySearch.randomWeightedInteger(weights);
    }
}