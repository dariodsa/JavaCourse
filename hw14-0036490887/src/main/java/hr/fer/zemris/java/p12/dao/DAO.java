package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * Interface through the subsystem for data persistent.
 * 
 * @author dario
 *
 */
public interface DAO {

    /**
     * Returns the list of polls.
     * 
     * @return {@link List} list of polls.
     */
    List<Poll> getPolls();

    /**
     * Returns poll with specific pollID.
     * 
     * @param pollID
     *            poll identificator
     * @return {@link Poll} with given identificator
     */
    Poll getPoll(long pollID);

    /**
     * Returns the {@link List} of {@link PollOptions} with the specific pollID.
     * 
     * @param pollID
     *            poll identificator
     * @return list of poll options with the specific poll id.
     */
    List<PollOptions> getPollOptions(long pollID);

    /**
     * Add one vote to the option with the given option id.
     * 
     * @param optionId
     *            option id
     */
    void addOneVote(long optionId);

}