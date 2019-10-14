package gof.state;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // vytvořit kontext

        final Context context = new Context();

        // nastavit první stav (štěstí)

        context.beHappy();
        context.express();

        // nastavit druhý stav (smutek)

        context.beSad();
        context.express();
    }
}
