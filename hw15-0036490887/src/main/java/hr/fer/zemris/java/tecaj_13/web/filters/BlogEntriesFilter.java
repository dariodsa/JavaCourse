package hr.fer.zemris.java.tecaj_13.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Class {@link BlogEntriesFilter} implements {@link Filter} and manage all
 * connection which will go on /servleti/author/*. It will manage where should
 * each connection go, and set all attributes.
 * 
 * @author dario
 *
 */
@WebFilter("/servleti/author/*")
public class BlogEntriesFilter implements Filter {

    /**
     * new action
     */
    private static final String NEW = "new";
    /**
     * edit action
     */
    private static final String EDIT = "edit";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String path = ((HttpServletRequest) request).getRequestURL().toString();
            int cnt = 0;
            int index = 0;
            int indexLast = 0;
            for (int i = 0, len = path.length(); i < len; ++i) {
                if (path.charAt(i) == '/') {
                    ++cnt;
                    if (cnt == 6) {
                        index = i;
                    }
                }
            }
            for (indexLast = index + 1; indexLast < path.length(); ++indexLast) {
                if (path.charAt(indexLast) == '/') {
                    break;
                }
            }
            String userName = path.substring(index + 1, indexLast);
            System.out.println(userName);
            request.setAttribute("user", userName);

            String requestMethod = path.substring(path.lastIndexOf('/') + 1);
            System.out.println(requestMethod);

            if (indexLast == path.length()) {
                request.getRequestDispatcher("/servleti/authors").forward(request, response);
                return;
            } else if (requestMethod.compareTo(NEW) == 0) {
                request.getRequestDispatcher("/servleti/addingNew").forward(request, response);
                return;
            } else if (requestMethod.compareTo(EDIT) == 0) {
                request.getRequestDispatcher("/servleti/editing").forward(request, response);
                return;
            } else {
                try {
                    long id = Long.parseLong(requestMethod);
                    System.out.println("ID " + id);
                    request.setAttribute("entryID", id);
                    request.getRequestDispatcher("/servleti/author/entries").forward(request, response);
                } catch (NumberFormatException ignorable) {
                    chain.doFilter(request, response);
                }
            }
        }

    }

    @Override
    public void destroy() {
    }

}
