package info.kgeorgiy.ja.ushenko.bank.account.impl;

import info.kgeorgiy.ja.ushenko.bank.account.AbstractAccount;

import java.io.Serializable;

public class LocalAccount extends AbstractAccount implements Serializable {

    public LocalAccount(String id, int amount) {
        super(id,amount);
    }
}
