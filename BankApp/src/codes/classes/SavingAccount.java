package codes.classes;

import java.io.Serializable;

public class SavingAccount extends Account implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3439210720381305948L;
	private final double interest;
    private final double maxWithdraw;

    public SavingAccount(String name, String nid, double balance, double maxWithdraw, String acc) {
        super(name, nid, balance, 2000, acc);
        this.maxWithdraw = maxWithdraw;
        this.interest = 0.05;
    }

    public SavingAccount(String name, String nid, double balance, double minBalance, double maxWithdraw, String acc) {
        super(name, nid, balance, minBalance, acc);
        this.maxWithdraw = maxWithdraw;
        this.interest = 0.05;
    }

    public double getNetBalance() {
        return balance + calculateInterest();
    }

    private double calculateInterest() {
        return balance * interest;
    }

    @Override
    protected int withdraw(double amount, String type) {
        // 4 == out of max withdraw limit
        if (amount > maxWithdraw)
            return 4;
        return super.withdraw(amount, type);
    }

    @Override
    public String toString() {
        return super.toString() + "\nMaximum Withdraw Limit: " + maxWithdraw;
    }

    @Override
    public double getAnyDouble(String str) {
        if (str.equalsIgnoreCase("interest"))
            return this.interest;
        else if (str.equalsIgnoreCase("maxWithdraw"))
            return this.maxWithdraw;
        else if (str.equalsIgnoreCase("netBalance"))
            return getNetBalance();
        else return 0;
    }
}
