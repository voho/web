package gof.unitofwork;

public class PrintingDao<T> implements Dao<T> {
    @Override
    public void insert(final T newEntity) {
        System.out.println("Insert: " + newEntity);
    }

    @Override
    public void update(final T dirtyEntity) {
        System.out.println("Update: " + dirtyEntity);
    }

    @Override
    public void remove(final T entityToRemove) {
        System.out.println("Remove: " + entityToRemove);
    }
}
