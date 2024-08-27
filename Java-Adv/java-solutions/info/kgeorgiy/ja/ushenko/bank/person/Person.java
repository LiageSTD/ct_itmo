package info.kgeorgiy.ja.ushenko.bank.person;

import info.kgeorgiy.ja.ushenko.bank.account.Account;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentMap;

public interface Person {
    /**
     * Returns the person's name.
     *
     * @return the person's name
     */
    String getName() throws RemoteException;
    /**
     * Returns the person's last name.
     *
     * @return the person's last name
     */
    String getLastName() throws RemoteException;
    /**
     * Returns the person's passport.
     *
     * @return the person's passport
     */
    String getPassport() throws RemoteException;
    /**
     * Returns the person's accounts.
     *
     * @return the person's accounts
     */
    ConcurrentMap<String, Account> getAccount() throws RemoteException;
}
