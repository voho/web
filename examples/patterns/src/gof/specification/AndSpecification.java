package gof.specification;

import java.util.Arrays;

public class AndSpecification<T> implements Specification<T> {
    private final Iterable<Specification<T>> clauses;

    public AndSpecification(final Specification<T>... clauses) {
        this(Arrays.asList(clauses));
    }

    public AndSpecification(final Iterable<Specification<T>> clauses) {
        this.clauses = clauses;
    }

    @Override
    public boolean isSatisfiedFor(final T object) {
        for (final Specification<T> clause : clauses) {
            if (!clause.isSatisfiedFor(object)) {
                return false;
            }
        }

        return true;
    }
}
