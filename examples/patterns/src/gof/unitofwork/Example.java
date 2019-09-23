package gof.unitofwork;

import org.junit.Test;

public class Example {
    private final UnitOfWork<Integer> unitOfWork = new UnitOfWork<>(new PrintingDao<>());

    @Test
    public void testCommit() {
        System.out.println("--- TRANSACTION 1 ---");

        unitOfWork.markAsNew(1);
        unitOfWork.markAsNew(2);
        unitOfWork.markAsNew(3);
        unitOfWork.markAsRemoved(2);
        unitOfWork.commit();

        System.out.println("--- TRANSACTION 2 ---");

        unitOfWork.markAsDirty(2);
        unitOfWork.commit();

        System.out.println("--- TRANSACTION 3 ---");

        unitOfWork.markAsRemoved(3);
        unitOfWork.markAsRemoved(1);
        unitOfWork.commit();
    }

    @Test
    public void testRollback() {
        System.out.println("--- TRANSACTION 1 ---");

        unitOfWork.markAsNew(1);
        unitOfWork.markAsNew(2);
        unitOfWork.markAsNew(3);
        unitOfWork.markAsRemoved(2);
        unitOfWork.rollback();

        System.out.println("--- TRANSACTION 2 ---");

        unitOfWork.markAsDirty(2);
        unitOfWork.rollback();
    }
}
