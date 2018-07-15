package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Class {@link BlogComment} encapsulate comment on a blog entry. User can
 * access all attributes and change it using get and set methods.
 * 
 * @author dario
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

    /**
     * comment id
     */
    private Long id;
    /**
     * on which entry comment was commited
     */
    private BlogEntry blogEntry;
    /**
     * users email
     */
    private String usersEMail;
    /**
     * message
     */

    private String message;

    /**
     * Date of creation
     */
    private Date postedOn;

    /**
     * error
     */
    private String error = "";
    
    /**
     * Returns comment's id.
     * 
     * @return comment's id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the comment's id
     * 
     * @param id
     *            comment's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the comment's entry.
     * 
     * @return entry on which comment was commited
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets the {@link BlogEntry} entry.
     * 
     * @param blogEntry
     *            blogentry
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Returns the comment's creator email.
     * 
     * @return comment's creator email
     */
    @Column(length = 100, nullable = false)
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Sets the comment's creator email.
     * 
     * @param usersEMail
     *            creator email
     */
    public void setUsersEMail(String usersEMail) {
        if(usersEMail == null || !EmailValidator.getInstance().isValid(usersEMail)) {
            error += "Email nije valjan.<br>";
            return;
        }
        this.usersEMail = usersEMail;
    }

    /**
     * Returns the comment's message.
     * 
     * @return comment's messsage
     */
    @Column(length = 4096, nullable = false)
    public String getMessage() {
        return message;
    }

    /**
     * Sets the comment's message
     * 
     * @param message
     *            comment's message
     */
    public void setMessage(String message) {
        if(message == null || message.isEmpty()) {
            error += "Komentar je prazan.<br>";
            return;
        }
        this.message = message;
    }

    /**
     * Returns the {@link Date} of creation.
     * 
     * @return date of creation
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets the {@link Date} of creation.
     * 
     * @param postedOn
     *            date of creation
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    /**
     * Returns the generated error.
     * @return {@link String} error
     */
    public String giveError() {
        return this.error;
    }
}