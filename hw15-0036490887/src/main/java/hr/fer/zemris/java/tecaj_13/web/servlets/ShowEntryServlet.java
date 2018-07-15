package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Class {@link ShowEntryServlet} extends {@link HttpServlet} and shows the
 * entry with the given id.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/author/entries")
public class ShowEntryServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long entryID = Long.parseLong(req.getAttribute("entryID").toString());

        BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryID);

        blogEntry.getComments().sort(new Comparator<BlogComment>() {

            @Override
            public int compare(BlogComment b1, BlogComment b2) {
                return b2.getPostedOn().compareTo(b1.getPostedOn());
            }
        });

        req.setAttribute("entry", blogEntry);

        req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // adding new comments
        long entryID = Long.parseLong(req.getAttribute("entryID").toString());
        String username = (String) req.getAttribute("user");

        String comment = (String) req.getParameter("comment");
        String email = (String) req.getParameter("email");
        
        
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);

        BlogComment blogComment = new BlogComment();
        blogComment.setMessage(comment);
        blogComment.setPostedOn(Calendar.getInstance().getTime());
        blogComment.setUsersEMail(email);
        blogComment.setBlogEntry(entry);

        if(blogComment.giveError().length() != 0) {
            resp.sendRedirect(username + "/" + entryID + "?error=" + blogComment.giveError());
            return;
        }
        
        System.out.println("DODAJEM NOVI KOMENTAR");

        DAOProvider.getDAO().addNewBlogComment(blogComment);
        resp.sendRedirect(username + "/" + entryID);

    }
}
