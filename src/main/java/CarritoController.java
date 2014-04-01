/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import others.Controller;

/**
 *
 * @author Cristian
 */
@WebServlet(urlPatterns = {"/CarritoController"})
public class CarritoController extends Controller {
    
    protected Map<Integer, Carrito> carritos;
    
    protected void actionIndex(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        
        
        
    }
    
    protected void actionInsert(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String id_user_aux = (String)session.getAttribute("id_user");
        
        // Supongo no errores, esto ya se valida cuando se acabe todo : D
        int id_user = Integer.parseInt(id_user_aux);
        
        // Si exite el carrito con ese user, me lo cepillo
        carritos.put(id_user, new Carrito(id_user));
    }
    
    
    protected void postFinish(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String id_user_aux = (String)session.getAttribute("id_user");
        
        // Supongo no errores, esto ya se valida cuando se acabe todo : D
        int id_user = Integer.parseInt(id_user_aux);
        String[] quantity = (String[])session.getAttribute("quantity");
        
        
        // Dejo el carrito a null
        //carrito = null;
    }
    
}
