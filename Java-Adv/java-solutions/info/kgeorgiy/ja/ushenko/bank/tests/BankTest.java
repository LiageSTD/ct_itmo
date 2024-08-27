package info.kgeorgiy.ja.ushenko.bank.tests;

import info.kgeorgiy.ja.ushenko.bank.bank.impl.RemoteBank;
import info.kgeorgiy.ja.ushenko.bank.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    RemoteBank bank;

    @BeforeEach
    void setUp() {
        bank = new RemoteBank(8080);
    }

    @Test
    void creationTest() throws RemoteException {
        bank.createAccount("1:1");
        assertEquals(bank.createAccount("1:1"), bank.getAccount("1:1"));
    }

    @Test
    void changingTest() throws RemoteException {
        Person person = bank.createPerson("1", "IVAN", "IVANOV");
        bank.createAccount("1:1");
        Person local = bank.getPerson("1", true);
        person.getAccount().get("1:1").setAmount(100);
        assertNotEquals(100, local.getAccount().get("1:1").getAmount());
        assertEquals(100, bank.getAccount("1:1").getAmount());
        assertEquals(0, local.getAccount().get("1:1").getAmount());
    }

    @Test
    void invalidDataTest() throws RemoteException {
        Person person = bank.createPerson("1", "IVAN", "IVANOV");
        bank.createAccount("1:1");
        assertNull(bank.createAccount("2:1"));
        assertNotNull(bank.createAccount("1"));
        bank.getAccount("1").setAmount(100);
        assertEquals(100, bank.getAccount("1").getAmount());
        assertNotNull(bank.createAccount("1:2"));
    }

    @Test
    void parallelChanging() throws RemoteException {
        Person person = bank.createPerson("1", "IVAN", "IVANOV");
        bank.createAccount("1:1");
        bank.getAccount("1:1").setAmount(400);
        Person remote = bank.getPerson("1", false);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            final int amount = 100;
            executor.submit(() -> {
                try {
                    remote.getAccount().get("1:1").setAmount(remote.getAccount().get("1:1").getAmount() - amount);
                } catch (RemoteException ignored) {
                    assert false;
                }
            });
        }
        executor.close();
        assertEquals(0, bank.getAccount("1:1").getAmount());
    }
    @Test
    void parallelCreation() throws RemoteException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            final int amount = 100 + i;
            final int id = i;
            executor.submit(() -> {
                try {
                    bank.createPerson(String.valueOf(id), "IVAN", "IVANOV");
                    bank.createAccount(id + ":1");
                    bank.getAccount(id + ":1").setAmount(amount);
                } catch (RemoteException ignored) {
                    assert false;
                }
            });
        }

        for (int i = 0; i < 50; i++) {
            final int pos = i;
            executor.submit(() -> {
                try {
                    assertEquals(100 + pos, bank.getAccount(pos + ":1").getAmount());
                } catch (RemoteException ignored) {
                    assert false;
                }
            });
        }
        executor.close();
    }
}
