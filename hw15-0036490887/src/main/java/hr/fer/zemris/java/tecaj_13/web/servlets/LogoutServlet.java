package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@link LogoutServlet} extends {@link HttpServlet} and manage all logout actions which will erase session and redirect to main page. 
 * @author dario
 *
 */
@WebServlet("/servleti/main/logout")
public class LogoutServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        
        resp.sendRedirect("../main");
    }
}
