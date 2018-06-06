package hr.fer.zemris.java.web.objects;

/**
 * Class {@link BendResult} implements {@link Comparable} which sorts
 * {@link BendResult} according to the number of votes, descending.
 * 
 * @author dario
 *
 */
public class BendResult implements Comparable<BendResult> {
    /**
     * bend
     */
    private Bend bend;
    /**
     * number of votes
     */
    private int numOfVotes;

    /**
     * Constructs {@link BendResult} with the bend and it's number of votes.
     * 
     * @param bend
     *            bend
     * @param numOfVotes
     *            number of votes of the given bend
     */
    public BendResult(Bend bend, int numOfVotes) {
        this.bend = bend;
        this.numOfVotes = numOfVotes;
    }

    /**
     * Returns number of votes for the given bend.
     * 
     * @return number of votes
     */
    public int getNumOfVotes() {
        return numOfVotes;
    }

    /**
     * Returns bend of which is in the given object, and for which we are saving its
     * number of votes.
     * 
     * @return bend
     */
    public Bend getBend() {
        return bend;
    }

    @Override
    public int compareTo(BendResult bendResult) {
        return Integer.compare(bendResult.numOfVotes, numOfVotes);
    }
}