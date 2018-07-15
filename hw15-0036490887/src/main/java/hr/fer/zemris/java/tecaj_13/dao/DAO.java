package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface through the subsystem for data persistent.
 * 
 * @author dario
 *
 */
public interface DAO {

    /**
     * Returns the entry with the given entry id.
     * 
     * @param id
     *            entry id
     * @return entry or null if it doesn't exist
     * @throws DAOException
     *             error
     */
    public BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Returns the {@link BlogUser} with given username and password
     * 
     * @param username
     *            username
     * @param passwordHash
     *            password hash
     * @return {@link BlogUser} with the above attributes
     * @throws DAOException
     *             DAO exception
     */
    public BlogUser getBlogUser(String username, String passwordHash) throws DAOException;

    /**
     * Adds the new {@link BlogUser} user.
     * 
     * @param mainUser
     *            new user
     * @throws DAOException
     *             exception
     */
    public void addNewUser(BlogUser mainUser) throws DAOException;

    /**
     * Returns the list of {@link BlogUser} users.
     * 
     * @return list of {@link BlogUser} users.
     * @throws DAOException
     *             DAO exception
     */
    public List<BlogUser> getUsers() throws DAOException;

    /**
     * Returns the user with the given username
     * 
     * @param userName
     *            username, nick
     * @return user or null if it doesn't exist
     * @throws DAOException
     *             exception
     */
    public BlogUser getUser(String userName) throws DAOException;

    /**
     * Add new blog entry.
     * 
     * @param entry
     *            new entry
     * @throws DAOException
     *             exception
     */
    public void addNewBlogEntry(BlogEntry entry) throws DAOException;

    /**
     * Adds new {@link BlogComment} comment.
     * 
     * @param comment
     *            new comment
     * @throws DAOException
     *             exception
     */
    public void addNewBlogComment(BlogComment comment) throws DAOException;

    /**
     * Updates the existing {@link BlogEntry}.
     * 
     * @param entry
     *            new value of entry
     * @throws DAOException
     *             exception
     */
    public void updateBlogEntry(BlogEntry entry) throws DAOException;
}