package gof.nullobject;

public class NullPerson extends Person {
    public static final Person INSTANCE = new NullPerson();

    private NullPerson() {
        super.setName("(žádné jméno)");
        super.setPhone("(žádné telefonní číslo)");
    }

    @Override
    public void setName(final String name) {
        // NOP
    }

    @Override
    public void setPhone(final String phone) {
        // NOP
    }
}
