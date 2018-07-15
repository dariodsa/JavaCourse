package hr.fer.zemris.java.hw14.model;

/**
 * Class {@link PollOptions} encapsulate table entity POLLOPTIONS from database.
 * It consists of {@link #pollOptionId}, {@link #optionTitle},
 * {@link #optionLink} and {@link #votesCount}. It encapsulate one option that
 * will be shown in a poll.
 * 
 * @author dario
 *
 */
public class PollOptions implements Comparable<PollOptions> {
    /**
     * id of an option
     */
    private long pollOptionId;
    /**
     * option's title
     */
    private String optionTitle;
    /**
     * option link that leads user to more information about selection
     */
    private String optionLink;
    /**
     * poll id on which is this option relevant
     */
    private long pollid;
    /**
     * votes for the given option
     */
    private long votesCount;

    /**
     * Sets the pollOption id which will be used in adding one vote to this option
     * when user click on it.
     * 
     * @param pollOptionId
     *            long value, pollOption id
     */
    public void setPollOptionId(long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }

    /**
     * Sets the option title, name of the option that will be shown to the users.
     * 
     * @param optionTitle
     *            option title
     */
    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    /**
     * Sets the option link, on which user can find more information about the
     * option.
     * 
     * @param optionLink
     *            option link
     */
    public void setOptionLink(String optionLink) {
        this.optionLink = optionLink;
    }

    /**
     * Sets poll's id in which will be shown this option.
     * 
     * @param pollid
     *            poll's id
     */
    public void setPollid(long pollid) {
        this.pollid = pollid;
    }

    /**
     * Sets the number of votes for the given option.
     * 
     * @param votesCount
     *            long value, number of votes
     */
    public void setVotesCount(long votesCount) {
        this.votesCount = votesCount;
    }

    @Override
    public int compareTo(PollOptions option) {
        return Long.compare(option.votesCount, votesCount);
    }

    /**
     * Returns the poll option id
     * 
     * @return poll option id
     */
    public long getPollOptionId() {
        return pollOptionId;
    }

    /**
     * Returns the option title.
     * 
     * @return {@link String} option title
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * Returns the option link.
     * 
     * @return {@link String} option link
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Returns the number of poll's id,
     * 
     * @return long value, poll id
     */
    public long getPollid() {
        return pollid;
    }

    /**
     * Returns the number of votes for this option.
     * 
     * @return long value, number of votes
     */
    public long getVotesCount() {
        return votesCount;
    }
}
