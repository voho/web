package gof.specification;

public class NotSpecification<T> implements Specification<T> {
    private final Specification<T> clause;

    public NotSpecification(final Specification<T> clause) {
        this.clause = clause;
    }

    @Override
    public boolean isSatisfiedFor(final T object) {
        return !this.clause.isSatisfiedFor(object);
    }
}
