package codes.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6290675734347814738L;
	private final LocalDateTime time;
    private final double amount;
    private String type;

    public Transaction(double amount, String type) {
        this.amount = amount;
        this.type = type;
        this.time = LocalDateTime.now();
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String str = this.time.format(formatter);
        str += "\t" + this.amount;
        str += "\t" + this.type;
        return str;
    }

    // getter will be used by table
    public LocalDateTime getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    // used when transfer
    public void setType(String type) {
        this.type = type;
    }
}
