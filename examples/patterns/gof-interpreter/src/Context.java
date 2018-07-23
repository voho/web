import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Context {
    private final Map<String, Boolean> variableValues;

    public Context() {
        this.variableValues = new HashMap<>();
    }

    public void setVariableToFalse(final String variableName) {
        this.variableValues.put(variableName, false);
    }

    public void setVariableToTrue(final String variableName) {
        this.variableValues.put(variableName, true);
    }

    public boolean getVariableValue(final String variableName) {
        return Optional.ofNullable(this.variableValues.get(variableName)).orElseThrow(NoSuchElementException::new);
    }
}
