package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link JPADAOImpl} implements DAO with using {@link JPAEMProvider} which
 * returns {@link EntityManager} which do all the works.
 * 
 * @author dario
 *
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
        return blogEntry;
    }

    @Override
    public BlogUser getBlogUser(String username, String passwordHash) throws DAOException {
        try {
            System.out.println("User: " + username + " " + passwordHash);
            BlogUser blogUser = JPAEMProvider.getEntityManager()
                    .createQuery("select  b from BlogUser as b where b.nick=:username and b.passwordHash=:hash",
                            BlogUser.class)
                    .setParameter("username", username).setParameter("hash", passwordHash).getSingleResult();
            System.out.println("Nasao user-a");
            return blogUser;
        } catch (NoResultException ex) {

        }

        return null;
    }

    @Override
    public void addNewUser(BlogUser mainUser) throws DAOException {
        try {
            EntityManager em = JPAEMProvider.getEntityManager();
            em.persist(mainUser);
            System.out.println("NEW USER");
            
        } catch (Exception ex) {
            throw new DAOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<BlogUser> getUsers() throws DAOException {
        List<BlogUser> list = JPAEMProvider.getEntityManager()
                .createQuery("select b from BlogUser as b", BlogUser.class).getResultList();
        System.out.println("dosao rez");
        return list;

    }

    @Override
    public BlogUser getUser(String userName) throws DAOException {
        try {
            BlogUser user = JPAEMProvider.getEntityManager()
                    .createQuery("select b from BlogUser as b where b.nick=:user", BlogUser.class)
                    .setParameter("user", userName).getSingleResult();
            return user;
        } catch (Exception ex) {
            throw new DAOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void addNewBlogEntry(BlogEntry entry) throws DAOException {
        try {
            EntityManager em = JPAEMProvider.getEntityManager();
            em.persist(entry);
        } catch (Exception ex) {
            throw new DAOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void addNewBlogComment(BlogComment comment) throws DAOException {
        try {
            EntityManager em = JPAEMProvider.getEntityManager();
            em.persist(comment);
        } catch (Exception ex) {
            throw new DAOException(ex.getMessage(), ex.getCause());
        }

    }

    @Override
    public void updateBlogEntry(BlogEntry entry) throws DAOException {
        try {
            JPAEMProvider.getEntityManager().persist(entry);
        } catch (Exception ex) {
            throw new DAOException(ex.getMessage(), ex.getCause());
        }
    }

}