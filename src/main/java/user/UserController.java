/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import others.Controller;
import others.Functions;
import pedido.Pedido;
import pedido.PedidoDAO;

/**
 *
 * @author d
 */
public class UserController extends Controller {
    
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    public void postEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    public void actionMisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String id_usu_aux = (String) session.getAttribute("id_user");
        String error = "";
        
        if(!Functions.isID(id_usu_aux)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        } else {
            PedidoDAO dao = new PedidoDAO(ds);
            List<Pedido> pedidos = dao.getAll(Integer.parseInt(id_usu_aux));
            if(pedidos == null) {
                pedidos = new LinkedList<Pedido>();
            }
            request.setAttribute("pedidos", pedidos);
        }
        
    }
    
    public void actionProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    /*public void postEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldpassword = request.getParameter("oldpasswordfield");
        String password = request.getParameter("passwordfield");
        String password2 = request.getParameter("password2field");
        
        HttpSession session = request.getSession(true);
        String user_id_aux = (String) session.getAttribute("user_id");
        
        // Compruebo errores
        String error = "";
        if(!Functions.isID(user_id_aux)) {
            // Errores : D
        }
        if(oldpassword.isEmpty()) {
            
        }
        if(password.isEmpty()) {
            password = "#";
        }
        if(password2.isEmpty()) {
            password2 = "@";
        }
        
        if(!password.equals(password2)) {
            
        }
        
        if (!error.isEmpty()) {
            
        } else {
            int id_user = Integer.parseInt(user_id_aux);
            UserDAO dao = new UserDAO(ds);
            boolean edit = dao.editPassword(id_user, password);
            if(edit) {
                
            } else {
                // ERRORES D':
            }
        }
        
        // Redirijo ;)
    }
    
    // Esto muestra los datos del usuario
    public void actionIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String user_id_aux = (String) session.getAttribute("user_id");
        
        if(!Functions.isID(user_id_aux)) {
            // Errores : D
        } else {
            int id_user = Integer.parseInt(user_id_aux);
            UserDAO dao = new UserDAO(ds);
            User us = dao.getUser(id_user);
            us.setPassword(""); // Así no tiene acceso al password e.e
            request.setAttribute("user", us);
        }
        
        // Aquí abrimos la página : D
        
        
    }
    
    // Este action es para mostrar el password y editarlo
    public void actionChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String user_id_aux = (String) session.getAttribute("user_id");
        
        if(!Functions.isID(user_id_aux)) {
            // Errores : D
        } else {
            int id_user = Integer.parseInt(user_id_aux);
            UserDAO dao = new UserDAO(ds);
            User us = dao.getUser(id_user);
            request.setAttribute("user", us.getName());
            request.setAttribute("password", us.getPassword());
        }
        
        // Aquí abrimos la página : D
        
        
    }*/
        
}
