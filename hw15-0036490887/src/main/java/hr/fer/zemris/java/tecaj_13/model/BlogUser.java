package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.EmailValidator;

import hr.fer.zemris.java.util.Util;

/**
 * Class {@link BlogUser} encapsulate user of a blog. User can access and change
 * all attributes of an object using it's get and set methods.
 * 
 * @author dario
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {
    /**
     * id of a user
     */
    private Long id;
    /**
     * first name
     */
    private String firstName;
    /**
     * last name
     */
    private String lastName;
    /**
     * user's nick
     */
    private String nick;
    /**
     * user's email
     */
    private String email;
    /**
     * user's password hash
     */
    private String passwordHash;
    /**
     * user's entries
     */
    private List<BlogEntry> entries;
    /**
     * list of errors
     */
    private String error = "";

    /**
     * Default constructor of a {@link BlogUser}
     */
    public BlogUser() {
    }

    /**
     * Constructs the {@link BlogUser} from the {@link HttpServletRequest}.
     * 
     * @param req
     *            {@link HttpServletRequest}
     */
    public BlogUser(HttpServletRequest req) {

        if(req.getParameter("password").compareTo(req.getParameter("password2")) != 0) {
            error += "Lozinka nije jednaka.<br>";
        }
        setFirstName(req.getParameter("firstname"));
        setLastName(req.getParameter("lastname"));
        setNick(req.getParameter("username"));
        setEmail(req.getParameter("email"));

        if (req.getParameter("password") == null || req.getParameter("password").isEmpty()) {
            error += "Lozinka nije ispunjena.<br>";
        } else {
            setPasswordHash(Util.calculateHash(req.getParameter("password")));
        }
        System.out.println(firstName);
        System.out.println("Error: " + error);
    }

    /**
     * Returns the user's id.
     * 
     * @return user's id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's id
     * 
     * @param id
     *            user's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's first name
     * 
     * @return user's first name
     */
    @Column(nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name
     * 
     * @param firstName
     *            user's first name
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            error += "Ime nije ispunjeno.<br>";
            return;
        }
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name
     * 
     * @return user's last name
     */
    @Column(nullable = false, length = 35)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name
     * 
     * @param lastName
     *            user's last name
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            error += "Prezime nije ispunjeno.<br>";
            return;
        }
        this.lastName = lastName;
    }

    /**
     * Returns the user's nickname
     * 
     * @return user's nickname
     */
    @Column(nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    /**
     * Sets the user's nickname.
     * 
     * @param nick
     *            nickname
     */
    public void setNick(String nick) {
        if (nick == null || nick.isEmpty()) {
            error += "Korisnicko ime nije ispunjeno.<br>";
            return;
        }
        this.nick = nick;
    }

    /**
     * Returns the user's email.
     * 
     * @return user's email
     */
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Setst the user's mail.
     * 
     * @param email
     *            user's email
     */
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            error += "Email nije ispunjen.<br>";
            return;
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            error += "Email nije valjan.";
            return;
        }
        this.email = email;
    }

    /**
     * Sets the user's password hash using SHA-1 encryption
     * 
     * @return user's password hash
     */
    @Column(nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the user's password hash.
     * 
     * @param hashPassword
     *            user's password hash
     */
    public void setPasswordHash(String hashPassword) {
        this.passwordHash = hashPassword;
    }

    /**
     * Sets the user's entries.
     * 
     * @param entries
     *            user's entries
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    /**
     * Returns the user's entries.
     * 
     * @return user's entries
     */
    @OneToMany(mappedBy = "user")
    public List<BlogEntry> getEntries() {
        return this.entries;
    }

    /**
     * Returns the errors in creation of an user
     * 
     * @return errors
     */
    public String giveError() {
        return error;
    }

    /**
     * Returns true if there was errors in creation of an user, false otherwise
     * 
     * @return true if there was errors, false otherwise
     */
    public boolean hasError() {
        return !(error != null && error.isEmpty());
    }

}
