package gof.unitofwork;

public interface Dao<T> {
    void insert(T newEntity);

    void update(T dirtyEntity);

    void remove(T entityToRemove);
}
