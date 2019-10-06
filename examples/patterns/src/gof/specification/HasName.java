package gof.specification;

public class HasName implements Specification<Person> {
    private final String desiredName;

    public HasName(final String desiredName) {
        this.desiredName = desiredName;
    }

    @Override
    public boolean isSatisfiedFor(final Person object) {
        return desiredName.equals(object.getName());
    }
}
