package gof.unitofwork;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Unit of Work.
 * @param <V> třída entity
 * @author Vojtěch Hordějčuk
 */
public class UnitOfWork<V> {
    /**
     * množina nových instancí
     */
    private final Set<V> setOfNew;
    /**
     * množina změněných instancí
     */
    private final Set<V> setOfDirty;
    /**
     * množina odstraněných instancí
     */
    private final Set<V> setOfRemoved;
    /**
     * DAO pro načítání entit z trvalého úložiště
     */
    private final Dao<V> dao;

    /**
     * Vytvoří novou instanci.
     * @param dao Dao pro zadaný typ entit
     */
    public UnitOfWork(final Dao<V> dao) {
        this.setOfNew = new LinkedHashSet<>();
        this.setOfDirty = new LinkedHashSet<V>();
        this.setOfRemoved = new LinkedHashSet<V>();
        this.dao = dao;
    }

    /**
     * Označí instanci jako novou.
     * @param entity instance entity
     */
    public void markAsNew(final V entity) {
        if (this.setOfDirty.contains(entity)) {
            // nová instance nemohla být změněna, pokud neexistovala
            throw new IllegalStateException("Already registered as dirty.");
        }

        if (this.setOfRemoved.contains(entity)) {
            // nová instance nemohla být odstraněna, pokud neexistovala
            throw new IllegalStateException("Already registered as removed.");
        }

        this.setOfNew.add(entity);
    }

    /**
     * Označí instanci jako změněnou.
     * @param entity instance entity
     */
    public void markAsDirty(final V entity) {
        if (this.setOfNew.contains(entity)) {
            // pokud je instance entity nová, není ještě v úložišti, proto by nebylo co upravovat
            return;
        }

        this.setOfDirty.add(entity);
    }

    /**
     * Označí instanci jako smazanou.
     * @param entity instance entity
     */
    public void markAsRemoved(final V entity) {
        if (!this.setOfNew.contains(entity)) {
            // instance byla dřív označena jako změněná
            // tato informace již není platná a musí se smazat
            this.setOfDirty.remove(entity);
            this.setOfRemoved.add(entity);
        } else {
            // instance byla nová, v úložišti tedy není
            // proto ji lze odebrat jen tak
            this.setOfNew.remove(entity);
        }
    }

    /**
     * Vymaže záznamy o změnách zadané entity.
     * @param entity instance entity
     */
    public void removeFromChanges(final V entity) {
        this.setOfNew.remove(entity);
        this.setOfDirty.remove(entity);
        this.setOfRemoved.remove(entity);
    }

    /**
     * Provede změny v trvalém úložišti.
     */
    public void commit() {
        // vytvořit nové

        for (final V newEntity : this.setOfNew) {
            this.dao.insert(newEntity);
        }

        this.setOfNew.clear();

        // uložit změněné

        for (final V dirtyEntity : this.setOfDirty) {
            this.dao.update(dirtyEntity);
        }

        this.setOfDirty.clear();

        // smazat odstraněné

        for (final V removedEntity : this.setOfRemoved) {
            this.dao.remove(removedEntity);
        }

        this.setOfRemoved.clear();
    }

    /**
     * Vymaže všechny záznamy o změnách.
     */
    public void rollback() {
        this.setOfNew.clear();
        this.setOfDirty.clear();
        this.setOfRemoved.clear();
    }
}
