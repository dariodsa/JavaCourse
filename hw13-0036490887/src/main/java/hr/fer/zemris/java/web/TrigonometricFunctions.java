package hr.fer.zemris.java.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@link TrigonometricFunctions} extends {@link HttpServlet} and sets
 * link to /trigonometric. It accepts a and b values and after that is prints
 * sin and cosine values of all degrees in between.
 * 
 * @author dario
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricFunctions extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = 0;
        int b = 360;

        if (req.getParameter("a") != null) {
            try {
                a = Integer.parseInt(req.getParameter("a"));
            } catch (Exception ignorable) {
            }
        }

        if (req.getParameter("b") != null) {
            try {
                b = Integer.parseInt(req.getParameter("b"));
            } catch (Exception ignorable) {
            }
        }

        if (a > b) {
            int c = a;
            a = b;
            b = c;
        }
        if (b > a + 720) {
            b = a + 720;
        }

        List<Values> list = new ArrayList<>();
        for (int val = a; val <= b; ++val) {
            list.add(new Values(val));
        }

        req.setAttribute("values", list);

        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

    }

    /**
     * Class {@link Values} encapsulate values of degrees and its sin and cosine
     * values.
     * 
     * @author dario
     *
     */
    public static class Values {
        /**
         * degrees
         */
        int x;
        /**
         * sin value from degrees
         */
        double sin;
        /**
         * cosine value from degrees
         */
        double cos;

        /**
         * Constructs object with degree and its sin and cosine values.
         * 
         * @param x
         *            degree
         */
        public Values(int x) {
            this.x = x;
            this.sin = Math.sin(x * (Math.PI / 180.0));
            this.cos = Math.cos(x * (Math.PI / 180.0));
        }

        /**
         * Returns object's degree.
         * 
         * @return degree
         */
        public int getX() {
            return x;
        }

        /**
         * Returns sin value of the object's degree
         * 
         * @return sin value of degree
         */
        public double getSin() {
            return sin;
        }

        /**
         * Returns cosine value of the object's degree
         * 
         * @return cosine value of degree
         */
        public double getCos() {
            return cos;
        }

    }
}
