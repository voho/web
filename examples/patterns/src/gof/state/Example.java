package gof.state;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // vytvořit kontext
        final Context context = new Context();

        // nastavit náhodný stav
        context.changeYourMood();

        // vypíše stav
        context.express();
    }
}
