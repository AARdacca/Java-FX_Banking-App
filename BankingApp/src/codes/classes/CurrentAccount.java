package codes.classes;

import java.io.Serializable;

public class CurrentAccount extends Account implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -623211942806880836L;
	private final String tradingLicense;

    public CurrentAccount(String name, String nid, double balance, String tradingLicense, String acc) {
        super(name, nid, balance, 5000, acc);
        this.tradingLicense = tradingLicense;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTrading License: " + tradingLicense;
    }

    @Override
    public double getAnyDouble(String str) {
        return 0;
    }
}
