package hr.fer.zemris.java.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.web.objects.Bend;

@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
    private String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath(VOTE_DEFINITION_FILE);
        Path file = Paths.get(fileName);
        
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
        //dalje ...
        List<Bend> bends = new ArrayList<>();
        
        List<String> lines = Files.readAllLines(file);
        for(String line : lines) {
            String[] splitted = line.split("\\t");
            //System.out.println(splitted[0] + " " +splitted[1] + splitted[2]);
            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1];
            String link = splitted[2];
            
            bends.add(new Bend(id, name, link));
        }
        
        req.setAttribute("bends", bends);
        
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
        
        //resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
        
    }
}
