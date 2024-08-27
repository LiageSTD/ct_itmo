package info.kgeorgiy.ja.ushenko.bank.person.impl;

import info.kgeorgiy.ja.ushenko.bank.account.Account;
import info.kgeorgiy.ja.ushenko.bank.account.impl.LocalAccount;
import info.kgeorgiy.ja.ushenko.bank.person.AbstractPerson;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LocalPerson extends AbstractPerson {
    ConcurrentMap<String, Account> accounts;

    public LocalPerson(ConcurrentMap<String, Account> accounts, String name, String secondName, String passport) throws RemoteException {
        super(name, secondName, passport);
        this.accounts = new ConcurrentHashMap<>();
        for (Map.Entry<String, Account> a : accounts.entrySet()) {
            this.accounts.put(a.getKey(), new LocalAccount(a.getKey(), a.getValue().getAmount()));
        }
    }

    @Override
    public ConcurrentMap<String, Account> getAccount() {
        return this.accounts;
    }
}
