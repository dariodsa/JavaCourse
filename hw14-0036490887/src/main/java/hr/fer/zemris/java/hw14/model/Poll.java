package hr.fer.zemris.java.hw14.model;

/**
 * Class {@link Poll} encapsulate table entity POLLS from database. It consists
 * of it's id ,title and question. Title and question will be shown to the user
 * in their web browser when they will want to vote or just to see it's results.
 * 
 * @author dario
 *
 */
public class Poll {
    /**
     * poll's id
     */
    private long pollId;
    /**
     * poll's title
     */
    private String title;
    /**
     * poll's question
     */
    private String question;

    /**
     * Sets the poll's id from database.
     * 
     * @param pollId
     *            long, poll's id
     */
    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    /**
     * Sets poll's title.
     * 
     * @param title
     *            {@link String} title of a poll
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the poll's question.
     * 
     * @param question
     *            {@link String} poll's question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the poll's id.
     * 
     * @return poll's id
     */
    public long getPollId() {
        return pollId;
    }

    /**
     * Returns the poll's title.
     * 
     * @return {@link String} poll's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the poll's question.
     * 
     * @return {@link String} poll's question
     */
    public String getQuestion() {
        return question;
    }
}
