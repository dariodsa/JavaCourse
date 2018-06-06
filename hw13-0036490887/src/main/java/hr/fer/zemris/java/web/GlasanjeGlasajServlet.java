package hr.fer.zemris.java.web;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id == null) {
            //error
            return;
        }
        try {
            int bandId = Integer.parseInt(id);
            //make a vote
            
            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
            Path file = Paths.get(fileName);
            List<String> lines = Files.readAllLines(file); 
            Path path = Paths.get(fileName);
            BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(path.toFile()));
            for(String line : lines) {
                String[] splitted = line.split("\\t");
                try {
                    int br = Integer.parseInt(splitted[0]);
                    int num = Integer.parseInt(splitted[1]);
                    if(bandId == br) {
                        ++num;
                        bis.write(new String(bandId +"\t" + (num) + "\n").getBytes("utf-8"));
                        continue;
                    }
                } catch(Exception ignorable) {}
                bis.write((line+"\n").getBytes("utf-8"));
            }
            
            bis.close();
            
            resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
            
        } catch(NumberFormatException ex) {
            //error
        }
    }
}
