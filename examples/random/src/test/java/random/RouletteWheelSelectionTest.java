package random;

public class RouletteWheelSelectionTest extends AbstractSelectionTest {
    @Override
    protected int randomIndex(final double[] weights) {
        return RouletteWheelSelection.randomWeightedInteger(weights);
    }
}