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
 * Class {@link ColorChooser} extends {@link HttpServlet} and sets the link on
 * the /setColor. It sees if the color id was set, if it was, new color will be
 * put into session variable. Otherwise it will prepare list of colors and send
 * it to colors.jsp.
 * 
 * @author dario
 *
 */
@WebServlet("/setcolor")
public class ColorChooser extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("color") != null) {
            req.getSession().setAttribute("pickedBgCol", req.getParameter("color"));
        }
        
        List<Pair> colors = new ArrayList<>();
        
        colors.add(new Pair("White", "FFFFFF"));
        colors.add(new Pair("Red", "FF0000"));
        colors.add(new Pair("Green", "00FF00"));
        colors.add(new Pair("Cyan", "00FFFF"));
        
        req.setAttribute("colors", colors);
        req.getSession().setAttribute("colors", colors);
        req.getRequestDispatcher("WEB-INF/pages/colors.jsp").forward(req, resp);

    }

    /**
     * Public static class {@link Pair} encapsulate color, which have two string
     * values, it's name and hex value.
     * 
     * @author dario
     *
     */
    public static class Pair {
        /**
         * color name
         */
        private String name;
        /**
         * color, hex value
         */
        private String value;

        /**
         * Constructs Pair of two string, in this case it will be pair of color's name
         * and hex value
         * 
         * @param name
         *            color name
         * @param value
         *            hex value of the color, without #
         */
        public Pair(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }

        /**
         * Returns color name
         * 
         * @return color name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns color's hex value.
         * 
         * @return hex value of the color
         */
        public String getValue() {
            return value;
        }

    }
}
