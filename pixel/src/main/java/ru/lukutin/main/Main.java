package ru.lukutin.main;

/**
 * Created by Sergey on 7/30/2015.
 */
public class Main {


    public static void main(String[] args) {
        final Account a = new Account(1000);
        final Account b = new Account(2000);


        new Thread(new Runnable() {
            public void run() {
                transfer(a, b, 500);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                transfer(a, b, 1000);
            }
        }).start();

        transfer(b, a, 100);

        System.out.println("balance a:" + a.getBalance());
        System.out.println("balance b:" + b.getBalance());
    }

    static void transfer(Account a1, Account a2, int amount) { // throws InsufficientFundsException {
        if (a1.getBalance() < amount) {
            System.out.println("small balance");

            //throw new InsufficientFundsException();
        }

        synchronized (a1) {
            System.out.println("synchronized a1");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            synchronized (a2) {
                System.out.println("synchronized a2");

                a1.withdraw(amount);
                a2.deposit(amount);
            }
        }

        System.out.println("transfer success");
    }
}
