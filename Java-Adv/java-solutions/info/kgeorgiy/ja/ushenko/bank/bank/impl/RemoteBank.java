package info.kgeorgiy.ja.ushenko.bank.bank.impl;

import info.kgeorgiy.ja.ushenko.bank.account.Account;
import info.kgeorgiy.ja.ushenko.bank.account.impl.RemoteAccount;
import info.kgeorgiy.ja.ushenko.bank.bank.Bank;
import info.kgeorgiy.ja.ushenko.bank.person.Person;
import info.kgeorgiy.ja.ushenko.bank.person.impl.LocalPerson;
import info.kgeorgiy.ja.ushenko.bank.person.impl.RemotePerson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteBank implements Bank {
    private final int port;
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public Account createAccount(final String id) throws RemoteException {
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid account id");
            return null;
        }
        String[] d = id.split(":");
        System.out.println("Working with account " + id);

        final Account account = new RemoteAccount(id, 0);
        if (d.length == 2 && !d[1].isEmpty()) {
            Person toAddAcc = persons.get(d[0]);
            if (toAddAcc == null) {
                return null;
            }
            accounts.put(id, account);
            ConcurrentMap<String, Account> toCreatePool = toAddAcc.getAccount();
            if (toCreatePool == null) {
                toCreatePool = new ConcurrentHashMap<>();

            }
            return account;
        } else {
            if (accounts.putIfAbsent(id, account) == null) {
                UnicastRemoteObject.exportObject(account, port);
                return account;
            } else {
                return getAccount(id);
            }
        }
    }

    @Override
    public Account getAccount(final String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid account id");
            return null;
        }
        System.out.println("Retrieving account " + id);
        return accounts.get(id);
    }

    @Override
    public Person getPerson(String passport, boolean getALocalPerson) throws RemoteException {
        System.out.println("Retrieving person " + passport);
        if (getALocalPerson) {
            Person toFormat = persons.get(passport);
            return new LocalPerson(getPersonsAccounts(toFormat), toFormat.getName(), toFormat.getLastName(), toFormat.getPassport());
        } else {
            return persons.get(passport);
        }
    }

    @Override
    public Person createPerson(String passport, String name, String secondName) throws RemoteException {
        System.out.println("Creating account " + passport);
        if (passport == null || name == null || secondName == null
                || passport.isEmpty() || name.isEmpty() || secondName.isEmpty()) {
            System.out.println("Invalid person data");
            return null;
        }
        final RemotePerson person = new RemotePerson(this, name, secondName, passport);
        if (persons.putIfAbsent(passport, person) == null) {
            UnicastRemoteObject.exportObject(person, port);
            return person;
        } else {
            return getPerson(passport, true);
        }
    }

    public ConcurrentMap<String, Account> getPersonsAccounts(Person person) throws RemoteException {
        if (person instanceof LocalPerson) {
            return person.getAccount();
        }
        Set<String> m1 = accounts.keySet();
        ConcurrentHashMap<String, Account> con = new ConcurrentHashMap<String, Account>();
        for (String a : m1) {
            if (a.contains(person.getPassport())) {
                con.put(a, accounts.get(a));
            }
        }
        return con;
    }
}
