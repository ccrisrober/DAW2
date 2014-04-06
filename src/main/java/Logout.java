/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import others.Controller;

/**
 *
 * @author Cristian
 */
public class Logout extends Controller {
 
    //Ya sea que el método sea por GET o POST, cerraremos la sesion.
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession sesion = request.getSession(true);
       
        //Cerrar sesion
        sesion.invalidate();
       
        //Redirecciono a index.jsp
        response.sendRedirect("Index");
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}