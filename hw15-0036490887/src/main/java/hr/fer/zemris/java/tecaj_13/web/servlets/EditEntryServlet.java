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

/**
 * Class {@link EditEntryServlet} extends {@link HttpServlet} and updates the 
 * given entry and also shows the given form in which will edit one. 
 * @author dario
 *
 */
@WebServlet("/servleti/entryediting")
public class EditEntryServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO GET");
        long entryId = Long.parseLong((String) req.getParameter("entryID"));

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);

        req.setAttribute("entryID", entryId);
        req.setAttribute("title", entry.getTitle());
        req.setAttribute("text", entry.getText());

        req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long entryId = Long.parseLong((String) req.getParameter("entryID"));
        String text = req.getParameter("text");

        String username = (String)req.getSession().getAttribute("current.user.nick");
        
        if(username == null) {
            resp.sendRedirect("../main?error=Nemas ovlasti");
            return;
        }
        
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
        
        
        if(username.compareTo(entry.getUser().getNick()) != 0) {
            resp.sendRedirect("../main?error=Nemas ovlasti");
            return;
        }
        
        entry.setLastModifiedAt(Calendar.getInstance().getTime());
        entry.setText(text);

        DAOProvider.getDAO().updateBlogEntry(entry);

        resp.sendRedirect("author/" + entry.getUser().getNick());
    }
}
