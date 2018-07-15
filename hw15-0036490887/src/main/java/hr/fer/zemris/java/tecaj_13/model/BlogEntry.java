package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class {@link BlogEntry} encapsulate entries in a blog and allows user to get
 * and set all attributes using the get and set methods.
 * 
 * @author dario
 *
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

    /**
     * entry id
     */
    private Long id;
    /**
     * list of comments
     */
    private List<BlogComment> comments = new ArrayList<>();
    /**
     * date created at
     */
    private Date createdAt;
    /**
     * date last modified at
     */
    private Date lastModifiedAt;
    /**
     * title
     */
    private String title;
    /**
     * text
     */
    private String text;
    /**
     * author
     */
    private BlogUser user;

    /**
     * Returns the entry id.
     * 
     * @return entry id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the entry id.
     * 
     * @param id
     *            entry id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the list of comments.
     * 
     * @return list of comments on an entry
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets the comments on an entry.
     * 
     * @param comments
     *            list of comments.
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Returns the creation date.
     * 
     * @return creation date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date of creation.
     * 
     * @param createdAt
     *            date of creation
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets the date of last modification.
     * 
     * @return Date last modification
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets the lastModifiedAt attribute.
     * 
     * @param lastModifiedAt
     *            date of last modification
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Returns the title's entry.
     * 
     * @return title's entry
     */
    @Column(length = 200, nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * Setst the entry title.
     * 
     * @param title
     *            entry's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the text of an entry.
     * 
     * @return text entry
     */
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }

    /**
     * Sets the text entry.
     * 
     * @param text
     *            text of an entry
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the author of the entry.
     * 
     * @param user
     *            {@link BlogUser} author
     */
    public void setUser(BlogUser user) {
        this.user = user;
    }

    /**
     * Returns the author of the entry.
     * 
     * @return {@link BlogUser} author
     */
    @ManyToOne
    public BlogUser getUser() {
        return this.user;
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
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}