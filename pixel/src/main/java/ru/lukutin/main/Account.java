package ru.lukutin.main;

/**
 * Created by Sergey on 7/30/2015.
 */
public class Account {

    private int balance;

    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return this.balance;
    }

}
