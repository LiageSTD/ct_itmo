package info.kgeorgiy.ja.ushenko.bank.person;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentMap;
/**
 * Person default realization.
 */
public abstract class AbstractPerson implements Person {

    private final String name;
    private final String secondName;
    private final String passport;
    /**
     * Creates a new person.
     *
     * @param name       the person's name
     * @param secondName the person's last name
     * @param passport   the person's passport
     */
    public AbstractPerson(String name, String secondName, String passport) {
        this.name = name;
        this.secondName = secondName;
        this.passport = passport;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getLastName() throws RemoteException {
        return secondName;
    }

    @Override
    public String getPassport() throws RemoteException {
        return passport;
    }


}
