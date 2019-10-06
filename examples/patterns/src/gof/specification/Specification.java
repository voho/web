package gof.specification;

public interface Specification<T> {
    boolean isSatisfiedFor(T object);

    default Specification<T> and(final Specification<T> other) {
        return new AndSpecification<>(Specification.this, other);
    }

    default Specification<T> or(final Specification<T> other) {
        return new OrSpecification<>(Specification.this, other);
    }

    default Specification<T> not() {
        return new NotSpecification<>(Specification.this);
    }
}
