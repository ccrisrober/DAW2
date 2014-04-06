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
import others.PageTemplate;
import others.TreeView;
import pedido.Pedido;
import pedido.PedidoDAO;
import producto.ProductoDAO;

/**
 *
 * @author d
 */
public class UserController extends Controller {
    
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Esto está mal, es para probar : D
        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        /*if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/carrito/list.js");
        }*/
        
        PageTemplate pt = new PageTemplate("user/change_password.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Extramoes usuario sessión
        
        int id_user = 2;
        
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if(!password1.equals(password2)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
        } else {
            UserDAO user = new UserDAO(ds);
            boolean edit = user.editPassword(id_user, password2);
            if(!edit) {
                request.setAttribute("error", "No se ha podido editar la contraseña.");
            } else {
                request.setAttribute("ok", "Contraseña cambiada con éxito");
            }
        }
        //Esto está mal, es para probar : D
        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        /*if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/carrito/list.js");
        }*/
        
        PageTemplate pt = new PageTemplate("user/change_password.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession(true).invalidate();
        response.sendRedirect("Index");
    }
    
    public void actionMisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        //int id_usu_aux = (Integer) session.getAttribute("id_user");
        String error = "";
        
        
        int id_usu_aux = 1;
        
        /*if(!Functions.isID(id_usu_aux)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        } else {*/
            PedidoDAO dao = new PedidoDAO(ds);
            List<Pedido> pedidos = dao.getAll(id_usu_aux);
            if(pedidos == null) {
                pedidos = new LinkedList<Pedido>();
            }
            dao.close();
            request.setAttribute("pedidos", pedidos);
        //}
        //Esto está mal, es para probar : D
        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/carrito/list.js");
        }
        
        PageTemplate pt = new PageTemplate("user/list.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
        
    }
    
    public void actionProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        //int id_usu_aux = (Integer) session.getAttribute("id_user");
        
        
        int id_usu_aux = 1;
        
            UserDAO dao = new UserDAO(ds);
            User user = dao.getUser(id_usu_aux);
            if(user == null) {
                request.setAttribute("error", "Usuario no encontrado");
            } else {
                request.setAttribute("user", user);
            }
            dao.close();
        //}
        //Esto está mal, es para probar : D
        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        
        PageTemplate pt = new PageTemplate("user/profile.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
        
        
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
