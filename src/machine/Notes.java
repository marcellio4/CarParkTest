package machine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @param notes         set of notes that we use for our payment
 * @param insertedNotes arrayList of all inserted notes from client
 * @author Marcel Zacharias
 */
public class Notes implements Money {
    private final String[] notes = { "$5", "$10", "$20" };
    private ArrayList<Double> insertedNotes = new ArrayList<>();

    /**
     * Check if inserted coin is in our set of notes value
     * 
     * @param note
     * @return boolean
     */
    public Boolean isNotes(String note) {
        return Arrays.stream(this.notes).anyMatch(note::equals);
    }

    /**
     * Match current notes string and return real flaot number
     * 
     * @param note
     * @return
     */
    @Override
    public Double getMoneyNumeration(String money) {
        switch (money) {
        case "$5":
            return 5.00;
        case "$10":
            return 10.00;
        case "$20":
            return 20.00;
        default:
            return 0.00;
        }
    }

    /**
     * Set real number of inserted notes to arrayList
     * 
     * @param notes
     */
    public void setInsertedNotes(Double notes) {
        this.insertedNotes.add(notes);
    }

    /**
     * 
     * @return arrayList of inserted notes in numeric representation
     */
    public ArrayList<Double> getInsertedNotes() {
        return this.insertedNotes;
    }

    /**
     * 
     * @return total amount of inserted notes
     */
    public Double sumOfInsertedNotes() {
        return this.insertedNotes.stream().mapToDouble(d -> d.doubleValue()).sum();
    }

    @Override
    public String toString() {

        StringBuffer paymentNotes = new StringBuffer();
        paymentNotes.append("Notes: ");
        for (String note : this.notes) {
            paymentNotes.append(note + ", ");
        }

        return paymentNotes.toString();
    }
}
