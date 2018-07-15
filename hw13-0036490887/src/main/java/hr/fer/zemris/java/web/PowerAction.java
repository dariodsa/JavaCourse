package hr.fer.zemris.java.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class {@link PowerAction} extends {@link HttpServlet} and sets link to
 * powers. It accepts three parameters a, b and n and creates excel workbook
 * using {@link HSSFWorkbook} class. It will make n sheets and on each sheet
 * will print numbers from a to b and in second column print power to the i-th
 * sheet.
 * 
 * @author dario
 *
 */
@WebServlet("/powers")
public class PowerAction extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * minimal value a
     */
    private static final int MIN_VALUE_A = -100;
    /**
     * maximal value a
     */
    private static final int MAX_VALUE_A = 100;
    /**
     * minimal value b
     */
    private static final int MIN_VALUE_B = -100;
    /**
     * maximal value b
     */
    private static final int MAX_VALUE_B = 100;
    /**
     * minimal value n
     */
    private static final int MIN_VALUE_N = 1;
    /**
     * maximal value n
     */
    private static final int MAX_VALUE_N = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = true;

        result = checkParameter("a", MIN_VALUE_A, MAX_VALUE_A, req);
        result &= checkParameter("b", MIN_VALUE_B, MAX_VALUE_B, req);
        result &= checkParameter("n", MIN_VALUE_N, MAX_VALUE_N, req);

        if (!result) {
            resp.getWriter().append(
                    "Imate grešku u zadanim parametrima.<br>Provjerite jeste li zadali parametre te jesu li oni u zadanim granicama.<br>");
        } else {
            int n = Integer.parseInt(req.getParameter("n"));
            int a = Integer.parseInt(req.getParameter("a"));
            int b = Integer.parseInt(req.getParameter("b"));

            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet[] sheets = new HSSFSheet[n];

            // initialization of sheets
            for (int i = 0; i < n; ++i) {
                sheets[i] = workBook.createSheet("sheet " + i);
            }

            for (int i = 0; i < n; ++i) {
                for (int num = Math.min(a, b), br = 0; num <= Math.max(a, b); ++num, ++br) {
                    HSSFRow row = sheets[i].createRow(br);
                    row.createCell(0).setCellValue(num);
                    row.createCell(1).setCellValue((long) Math.pow(num, i));
                }
            }

            resp.setContentType("application/vnd.ms-excel");
            workBook.write(resp.getOutputStream());
            workBook.close();

        }
    }

    /**
     * Returns true if the parameter fits in the given specifications, minimal and
     * maximal value. If the parameter can't be parsed into integer, it will be
     * returned false.
     * 
     * @param name
     *            name of the parameter
     * @param min
     *            minimal value
     * @param max
     *            maximal value
     * @param req
     *            {@link HttpServletRequest} request
     * @return true if the parameter is in given boundaries, false otherwise
     */
    private boolean checkParameter(String name, int min, int max, HttpServletRequest req) {
        if (req.getParameter(name) == null)
            return false;
        try {
            int n = Integer.parseInt(req.getParameter(name));
            if (!(n >= min && n <= max))
                return false;
        } catch (Exception ignorable) {
            return false;
        }

        return true;
    }

}
