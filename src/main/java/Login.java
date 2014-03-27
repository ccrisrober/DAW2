/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import others.Functions;
import others.PageTemplate;
import others.TreeView;

/**
 *
 * @author Cristian
 */
public class Login extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        
        List<String> ltv = new LinkedList<String>();
        
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> header = new LinkedList<String>();

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/index/index.js");

        List<String> jspservlet = new LinkedList<String>();
        jspservlet.add("index.jsp");

        PageTemplate pt = new PageTemplate("login.jsp", "index", tv, header, footer, jspservlet, "", true, "Dashboard");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String user = request.getParameter("userfield");
        String pass = request.getParameter("passfield");

        boolean error = false;
        JSONObject json = new JSONObject();
        if(Functions.isEmpty(user)) {
            error = true;
        } else if(Functions.isEmpty(pass)) {
            error = true;
        }
        if(error) {
            json.put("data", "Usuario y/o contraseña vacío(s).");
        } else {
            UserDAO dao = new UserDAO(null);
            boolean validate = dao.validate(user, pass);
            if(validate) {
                json.put("data", "Conectado con éxito");
            } else {
                error = true;
                json.put("data", "Pareja usuario/contraseña no encontrado");
            }
        }
        json.put("error", error);

        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
