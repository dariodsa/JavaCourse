package hr.fer.zemris.java.hw14.servleti;

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

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class {@link GlasanjeXls} extends {@link HttpServlet}
 * and sets it link to glasanje-xls. It sets results to the 
 * excel workbook using org.apache.poi.hssf.usermodel package which 
 * is available online.
 * @author dario
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXls extends HttpServlet{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pollID = Long.parseLong(req.getParameter("pollID"));
        List<PollOptions> results = DAOProvider.getDao().getPollOptions(pollID);
        Collections.sort(results);
        
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("rezultati");
        
        HSSFRow mainRow = sheet.createRow(0);
        mainRow.createCell(0).setCellValue("Id opcije");
        mainRow.createCell(1).setCellValue("Naziv opcije");
        mainRow.createCell(2).setCellValue("Link opcije");
        mainRow.createCell(3).setCellValue("Broj glasova");
        
        for (int i = 0; i < results.size(); ++i) {
            
            HSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(results.get(i).getPollOptionId());
            row.createCell(1).setCellValue(results.get(i).getOptionTitle());
            row.createCell(2).setCellValue(results.get(i).getOptionLink());
            row.createCell(3).setCellValue(results.get(i).getVotesCount());
        }

        resp.setContentType("application/vnd.ms-excel");
        workBook.write(resp.getOutputStream());
        workBook.close();
    }
}
