package gof.specification;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        final Person somePerson = new Person("John Doe", 19);

        // první způsob
        final boolean satisfied1 = new HasName("John Doe").and(new HasAge(18, 20)).isSatisfiedFor(somePerson);

        // druhý způsob
        final boolean satisfied2 = new AndSpecification<>(new HasName("John Doe"), new HasAge(18, 20)).isSatisfiedFor(somePerson);
    }
}
