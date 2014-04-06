/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import others.Controller;
import others.Functions;
import others.PageTemplate;
import others.TreeView;
import user.UserDAO;

/**
 *
 * @author Cristian
 */
public class Login extends Controller {

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
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
        
        List<String> ltv = new LinkedList<String>();
        
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> header = new LinkedList<String>();

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/index/index.js");

        List<String> jspservlet = new LinkedList<String>();
        //jspservlet.add("login.jsp");

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
        String user = request.getParameter("userfield");
        String pass = request.getParameter("passfield");

        boolean error = false;
        if(Functions.isEmpty(user)) {
            error = true;
        } else if(Functions.isEmpty(pass)) {
            error = true;
        }
        if(error) {
            error = true;
            request.setAttribute("error", "Usuario y/o contraseña vacío(s)");
        } else {
            UserDAO dao = new UserDAO(ds);
            int pos = dao.validate(user, pass);
            if(pos > 0) {
                request.setAttribute("ok", "Conectado con éxito");
                HttpSession session = request.getSession(true);
                session.setAttribute("id_user", pos);
                System.err.println("Creo usuario");
                if(dao.isAdmin(pos)) {
                    System.err.println("Creo admin");
                    session.setAttribute("admin_mode", true);
                }
            } else {
                error = true;
                request.setAttribute("error", "Pareja usuario/contraseña no encontrado");
            }
        }
        if(error) {
            response.sendRedirect("Login");
        } else {
            response.sendRedirect("Index");
        }
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
