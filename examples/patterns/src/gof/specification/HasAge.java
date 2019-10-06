package gof.specification;

public class HasAge implements Specification<Person> {
    private final int minAge;
    private final int maxAge;

    public HasAge(final int minAge, final int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public boolean isSatisfiedFor(final Person object) {
        return minAge <= object.getAge() && maxAge >= object.getAge();
    }
}
