/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import others.Functions;
import user.UserDAO;

/**
 *
 * @author Cristian
 */
public class Register extends HttpServlet {
    
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
        getServletContext().getRequestDispatcher("/pages/register.jsp").forward(request, response);
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
        String pass = request.getParameter("passwordfield");
        String pass2 = request.getParameter("passwordfield2");
        String acepto = request.getParameter("remember_me");
        
        String error = "";
        boolean errorPass = false;
        if (acepto == null || !acepto.equals("on")) {
            error += "<li>Tienes que aceptar las condiciones de uso.</li>";
        }
        if (Functions.isEmpty(user)) {
            error += "<li>Usuario está vacío</li>";
        }
        if (Functions.isEmpty(pass)) {
            error += "<li>Password está vacío</li>";
            errorPass = true;
        }
        if (Functions.isEmpty(pass2)) {
            error += "<li>Password Confirm está vacío</li>";
            errorPass = true;
        }
        if(!errorPass) {
            if(!pass.equals(pass2)) {
                error += "<li>Las contraseñas no son iguales</li>";
            }
        }
        
        request.setAttribute("userfield", user);
        request.setAttribute("passfield", pass);
        request.setAttribute("passfield2", pass2);
        request.setAttribute("remember_me", acepto);
        
        // Si la variable error tiene caracteres, salimos y marcamos los errores
        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
            
            
            
            
            
            
            
            
        } else {
            UserDAO dao = new UserDAO(null);
            boolean exito = dao.register(user, pass);            // Registramos al usuario
            if(!exito) {
                request.setAttribute("error", "Error al registrar.");
                
                
                
                
                
                
                
                
            }
            else {
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Se has ingresado con éxito.'); location.href='Login';</script>");
                out.close();
            }
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
