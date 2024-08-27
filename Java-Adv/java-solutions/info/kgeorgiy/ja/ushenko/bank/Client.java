package info.kgeorgiy.ja.ushenko.bank;

import info.kgeorgiy.ja.ushenko.bank.account.Account;
import info.kgeorgiy.ja.ushenko.bank.bank.Bank;
import info.kgeorgiy.ja.ushenko.bank.person.Person;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public final class Client {
    /** Utility class. */
    private Client() {}

    public static void main(final String... args) throws RemoteException {
        final Bank bank;
        try {
            bank = (Bank) Naming.lookup("//localhost/bank");
        } catch (final NotBoundException e) {
            System.out.println("Bank is not bound");
            return;
        } catch (final MalformedURLException e) {
            System.out.println("Bank URL is invalid");
            return;
        }
        final String name = args.length >= 1 ? args[0] : "Ivan";
        final String secondName = args.length >= 2 ? args[1] : "Ivanov";
        final String passport = args.length >= 3 ? args[2] : "123456";
        final String accountId = args.length >= 4 ? args[3] : "123456:123";
        final int money = args.length >= 5 ? Integer.parseInt(args[4]) : 100;

        Person remotePerson = bank.getPerson(passport, false);
        if (remotePerson == null) {
            System.out.println("Creating person");
            remotePerson = bank.createPerson(name, secondName, passport);
        } else {
            System.out.println("Person already exists");
            if (!remotePerson.getLastName().equals(secondName) || !remotePerson.getName().equals(name)) {
                System.out.println("Invalid person data");
                return;
            }
        }
        Account personAccount = remotePerson.getAccount().get(accountId);
        if (personAccount == null) {
            System.out.println("Creating account " + accountId);
            personAccount = bank.createAccount(accountId);
        } else {
            System.out.println("Account already exists");
        }
        remotePerson.getAccount().get(accountId).setAmount(remotePerson.getAccount().get(accountId).getAmount() + money);
        System.out.println("Person: " + remotePerson.getName() + " " + remotePerson.getLastName());
        System.out.println("Passport: " + remotePerson.getPassport());
        System.out.println("Account amount is: " + remotePerson.getAccount().get(accountId).getAmount());

    }
}
