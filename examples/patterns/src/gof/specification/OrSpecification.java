package gof.specification;

import java.util.Arrays;

public class OrSpecification<T> implements Specification<T> {
    private final Iterable<Specification<T>> clauses;

    public OrSpecification(final Specification<T>... clauses) {
        this(Arrays.asList(clauses));
    }

    public OrSpecification(final Iterable<Specification<T>> clauses) {
        this.clauses = clauses;
    }

    @Override
    public boolean isSatisfiedFor(final T object) {
        for (final Specification<T> clause : clauses) {
            if (clause.isSatisfiedFor(object)) {
                return true;
            }
        }

        return false;
    }
}