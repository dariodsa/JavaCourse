package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;

/**
 * This is an implementation of subsystems DAO using the technology SQL. This
 * concrete implementation  expects that connection is already establish using
 * {@link SQLConnectionProvider} class. That means that web listeners should establish 
 * that connection when the web server is started. One filter should be configured
 * which will redirect all calls of servlet and before that inject one connection 
 * from the connection pool and remove it in the end.   
 * @author dario
 */
public class SQLDAO implements DAO {

    @Override
    public List<Poll> getPolls() {
        
        List<Poll> polls = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst = null;
        
        try {
            pst = con.prepareStatement("select id, title, message from Polls order by id");
            
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    while(rs != null && rs.next()) {
                        Poll poll = new Poll();
                        poll.setPollId(rs.getLong(1));
                        poll.setTitle(rs.getString(2));
                        poll.setQuestion(rs.getString(3));
                        polls.add(poll);
                    }
                } finally {
                    try { rs.close(); } catch(Exception ignorable) {}
                }
            } finally {
                try {pst.close(); } catch(Exception ignorable) {}
            }
            
        } catch(Exception e) {
            throw new DAOException(e.getCause());
        }
        
        return polls;
    }

    @Override
    public Poll getPoll(long pollID) {
        
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst = null;
        
        try {
            pst = con.prepareStatement("select id, title, message from Polls where id="+pollID);
            
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    while(rs != null && rs.next()) {
                        Poll poll = new Poll();
                        poll.setPollId(rs.getLong(1));
                        poll.setTitle(rs.getString(2));
                        poll.setQuestion(rs.getString(3));
                        return poll;
                    }
                } finally {
                    try { rs.close(); } catch(Exception ignorable) {}
                }
            } finally {
                try {pst.close(); } catch(Exception ignorable) {}
            }
            
        } catch(Exception e) {
            throw new DAOException(e.getCause());
        }
        return null;
    }

    @Override
    public List<PollOptions> getPollOptions(long pollID) {
        List<PollOptions> pollOptions = new ArrayList<>();
        
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst = null;
        
        try {
            pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=?");
            pst.setLong(1, pollID); 
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    while(rs != null && rs.next()) {
                        PollOptions options = new PollOptions();
                        options.setPollOptionId(rs.getLong(1));
                        options.setOptionTitle(rs.getString(2));
                        options.setOptionLink(rs.getString(3));
                        options.setPollid(rs.getLong(4));
                        options.setVotesCount(rs.getLong(5));
                        pollOptions.add(options);
                    }
                } finally {
                    try { rs.close(); } catch(Exception ignorable) {}
                }
            } finally {
                try {pst.close(); } catch(Exception ignorable) {}
            }
            
        } catch(Exception e) {
            throw new DAOException(e.getCause());
        }
        return pollOptions;
    }

    @Override
    public void addOneVote(long optionId) {
        //
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst = null;
        
        try {
            pst = con.prepareStatement("update polloptions SET votesCount = votesCount + 1 WHERE id ="+optionId);
            
            pst.executeUpdate();
            
        } catch(Exception e) {
            throw new DAOException(e.getCause());
        }
    }

	 

}