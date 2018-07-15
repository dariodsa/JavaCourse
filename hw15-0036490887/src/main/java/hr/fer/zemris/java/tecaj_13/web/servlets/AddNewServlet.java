package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class {@link AddNewServlet} extends {@link HttpServlet} and add new entry to
 * the database and redirect user to show it.
 * 
 * @author dario
 *
 */

@WebServlet("/servleti/addingNew")
public class AddNewServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getAttribute("user").toString();
        System.out.println("NewADDServlet");
        System.out.println(userName);

        if (req.getSession().getAttribute("current.user.nick") == null) {
            resp.setStatus(401);
            resp.sendRedirect("../main?error=Nemas dozvole za tu akciju");
        }
        if (userName == null
                || userName.compareTo(req.getSession().getAttribute("current.user.nick").toString()) != 0) {
            System.out.println("Nije usao.");
            resp.setStatus(401);
            resp.sendRedirect("../main?error=Nemas dozvole za tu akciju");
        } else {
            System.out.println("Usao");
            req.getRequestDispatcher("/WEB-INF/pages/addNew.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        String heading = req.getParameter("heading");
        String username = (String)req.getSession().getAttribute("current.user.nick");

        if(username == null) {
            resp.sendRedirect("../main?error=Nemas ovlasti");
            return;
        }
         
        BlogUser currentUser = DAOProvider.getDAO().getUser(username);

        BlogEntry entry = new BlogEntry();
        StringBuilder error = new StringBuilder();
        if (text == null || text.isEmpty()) {
            error.append("Text je prazan.<br>");
        }
        if (heading == null || heading.isEmpty()) {
            error.append("Naslov je prazan.<br>");
        }
        
        entry.setText(text);
        entry.setTitle(heading);
        entry.setUser(currentUser);
        entry.setCreatedAt(Calendar.getInstance().getTime());
        entry.setLastModifiedAt(Calendar.getInstance().getTime());
        
        
        if (error.length() == 0) {
            DAOProvider.getDAO().addNewBlogEntry(entry);
            //resp.sendRedirect("author/" + username);
            resp.sendRedirect(req.getServletContext().getContextPath() + String.format("/servleti/author/%s", username));
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/addNew.jsp" + "?error=" + error.toString()).forward(req, resp);
        }

    }
}
