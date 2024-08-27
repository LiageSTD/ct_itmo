package info.kgeorgiy.ja.ushenko.bank.bank;

import info.kgeorgiy.ja.ushenko.bank.account.Account;
import info.kgeorgiy.ja.ushenko.bank.person.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
    /**
     * Creates a new account with specified identifier if it does not already exist.
     * @param id account id
     * @return created or existing account.
     */
    Account createAccount(String id) throws RemoteException;

    /**
     * Returns account by identifier.
     * @param id account id
     * @return account with specified identifier or {@code null} if such account does not exist.
     */
    Account getAccount(String id) throws RemoteException;
    /**
     * Returns person by identifier.
     * @param id person id
     * @return person with specified identifier or {@code null} if such account does not exist.
     */
    Person getPerson(String id, boolean getALocalPerson) throws RemoteException;
    /**
     * Creates a new person with specified identifier if it does not already exist.
     * @param passport person id
     * @param name person's name
     * @param secondName person's secondName
     * @return created or existing person.
     */
    Person createPerson(String passport, String name, String secondName) throws RemoteException;

}
