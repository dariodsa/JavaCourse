package hr.fer.zemris.java.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.web.files.FileHandler;
import hr.fer.zemris.java.web.objects.BendResult;

/**
 * Class {@link GlasanjeXls} extends {@link HttpServlet}
 * and sets it link to glasanje-xls. It sets results to the 
 * excel workbook using org.apache.poi.hssf.usermodel package which 
 * is available online.
 * @author dario
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXls extends HttpServlet{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BendResult> results = FileHandler.getInstance(req.getServletContext()).getBendsResults();
        Collections.sort(results);
        
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("rezultati");
        for (int i = 0; i < results.size(); ++i) {
            
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(results.get(i).getBend().getId());
            row.createCell(1).setCellValue(results.get(i).getBend().getName());
            row.createCell(2).setCellValue(results.get(i).getBend().getLink());
            row.createCell(3).setCellValue(results.get(i).getNumOfVotes());
        }

        resp.setContentType("application/vnd.ms-excel");
        workBook.write(resp.getOutputStream());
        workBook.close();
    }
}
