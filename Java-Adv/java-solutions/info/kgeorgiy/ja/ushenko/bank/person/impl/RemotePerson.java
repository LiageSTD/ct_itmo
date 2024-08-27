package info.kgeorgiy.ja.ushenko.bank.person.impl;

import info.kgeorgiy.ja.ushenko.bank.account.Account;
import info.kgeorgiy.ja.ushenko.bank.account.impl.RemoteAccount;
import info.kgeorgiy.ja.ushenko.bank.bank.impl.RemoteBank;
import info.kgeorgiy.ja.ushenko.bank.person.AbstractPerson;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentMap;

public class RemotePerson extends AbstractPerson implements Remote {
    private final RemoteBank bank;
    public RemotePerson(RemoteBank remoteBank, String name, String secondName, String passport) {
        super(name, secondName, passport);
        bank = remoteBank;
    }


    @Override
    public ConcurrentMap<String, Account> getAccount() throws RemoteException {
        return bank.getPersonsAccounts(this);
    }
}
