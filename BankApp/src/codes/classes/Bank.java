package codes.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5514139715359808295L;
	private final String bankName;
    private final HashMap<String, Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new HashMap<>();
    }

    private void addAccount(Account acc) {
        accounts.put(acc.getAcc(), acc);
    }


    // Current account
    public void addAccount(String name, String nid, double balance, String tradeLicenseNumber) {
        addAccount(new CurrentAccount(name, nid, balance, tradeLicenseNumber, uniqueID()));
    }

    // saving account
    public void addAccount(String name, String nid, double balance, double maxWithdrawLimit) {
        addAccount(new SavingAccount(name, nid, balance, maxWithdrawLimit, uniqueID()));
    }

    // student account
    public void addAccount(String name, String nid, double balance, String studentID, String university) {
        addAccount(new StudentAccount(name, nid, balance, studentID, university, uniqueID()));
    }

    public Account findAcc(String accNo) {
        return accounts.get(accNo);
    }

    public int deposit(String accNo, double amount) {
        // 5 == account not found
        Account acc = findAcc(accNo);
        if (acc != null)
            return acc.deposit(amount);
        return 5;
    }

    public int withdraw(String accNo, double amount) {
        // 5 == account not found
        Account acc = findAcc(accNo);
        if (acc != null)
            return acc.withdraw(amount);
        return 5;
    }

    public int transfer(String accNo1, String accNo2, double amount) {
        Account acc1 = findAcc(accNo1);
        Account acc2 = findAcc(accNo2);
        if (acc1 != null && acc2 != null) {
            int res = acc1.withdraw(amount);
            if (res == 0) {
                acc2.deposit(amount);
                // not an ideal solution but works
                acc1.transactions.get(acc1.transactions.size() - 1).setType("Transferred to " + acc2.getAcc());
                acc2.transactions.get(acc2.transactions.size() - 1).setType("Transferred from " + acc1.getAcc());
                return 0;
            } else {
                // if failed money goes back to account
                acc1.deposit(amount);
            }
            return res;
        }
        return 5;
    }

    public double getBalance(String accNo) {
        Account acc = findAcc(accNo);
        return acc.getClass().getSimpleName().equalsIgnoreCase("CurrentAccount") ? acc.getAnyDouble("netBalance") : acc.getBalance();
    }

    // Getter
    public String getBankName() {
        return bankName;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public HashMap<String, Account> getAccounts(String type) {
        HashMap<String, Account> acc = new HashMap<>();
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getClass().getSimpleName().equalsIgnoreCase(type))
                acc.put(entry.getKey(), entry.getValue());
        }
        return acc;
    }

    public ArrayList<Transaction> getTransactions(String accNo) {
        return findAcc(accNo).transactions;
    }

    // Member methods
    public ArrayList<Account> findAccWithNID(String nid) {
        ArrayList<Account> list = new ArrayList<>();
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getNid().equalsIgnoreCase(nid))
                list.add(entry.getValue());
        }
        return list;
    }

    // Custom method for generating unique id for every user
    private String uniqueID() {
        // in case bank has more account than account number limit
        if (accounts.size() == 89999)
            return "OVERLOAD";

        String str = null;
        boolean alreadyExist = true;

        while (alreadyExist) {
            str = (10000 + (int) (Math.random() * ((99999 - 10000) + 1))) + "";
            alreadyExist = accounts.containsKey(str);
        }

        return str;
    }

}
