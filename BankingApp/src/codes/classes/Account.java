package codes.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class Account implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4841763659239161406L;
	protected String name;
    protected String nid;
    protected String acc;
    protected double balance;
    protected double minBalance;
    protected ArrayList<Transaction> transactions;

    public Account(String name, String nid, double balance, double minBalance) {
        this.name = name;
        this.nid = nid;
        this.balance = balance;
        this.minBalance = minBalance;
        transactions = new ArrayList<>();

        // NOT IDEAL AT ALL
        // DO NOT USE
        Random rand = new Random();
        this.acc = 10000 + rand.nextInt(99999) + "";
    }

    public Account(String name, String nid, double balance, double minBalance, String acc) {
        this.name = name;
        this.nid = nid;
        this.balance = balance;
        this.minBalance = minBalance;
        this.acc = acc;
        transactions = new ArrayList<>();
    }


    public int deposit(double amount) {
        // o == success
        // 2 == invalid amount
        // 3 == unknown error
        //
        // 5 == account not found
        return deposit(amount, "deposit");
    }

    protected int deposit(double amount, String type) {
        if (amount < 0) return 2;
        this.balance += amount;
        addTransaction(amount, type);
        return 0;
    }

    public int withdraw(double amount) {
        // o == success
        // 1 == insufficient Balance
        // 2 == insufficient Minimum Balance
        // 3 == Invalid Amount
        //
        // 4 == out of max withdraw limit
        // 5 == account not found
        return withdraw(amount, "withdraw");
    }

    protected int withdraw(double amount, String type) {
        if (amount > this.balance)
            return 1;
        if (amount > this.balance - this.minBalance)
            return 2;
        if (amount < 0)
            return 3;
        this.balance -= amount;
        addTransaction(amount, type);
        return 0;
    }

    public void addTransaction(double amount, String type) {
        transactions.add(new Transaction(amount, type));
    }

    public String getAcc() {
        return acc;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String toString() {
        return "Member Name: " + name + "\nAccount Type: " + getClass().getSimpleName() + "\nAccount Balance: " + balance + "\nMember NID: " + nid + "\nAccount Number: " + acc + "\nMinimum Balance: " + minBalance;
    }

    // will be used by table
    public String getName() {
        return name;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public String getNid() {
        return nid;
    }

    public abstract double getAnyDouble(String str);
}
