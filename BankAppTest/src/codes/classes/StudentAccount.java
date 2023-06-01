package codes.classes;

import java.io.Serializable;

public class StudentAccount extends SavingAccount implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2989649941567678136L;
	private final String studentID;
    private final String university;

    public StudentAccount(String name, String nid, double balance, String studentID, String university, String acc) {
        super(name, nid, balance, 100, 20000, acc);
        this.studentID = studentID;
        this.university = university;
    }

    @Override
    public String toString() {
        return super.toString() + "\nStudent ID: " + studentID + "\nUniversity: " + university;
    }
}
