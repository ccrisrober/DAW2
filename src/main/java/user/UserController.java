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

public class UserController extends Controller {
    
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.checkAccessLogin(request, response);
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Usuario");
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
        
        this.checkAccessLogin(request, response);
        
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
        
        PageTemplate pt = new PageTemplate("user/change_password.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.checkAccessLogin(request, response);
        
        request.getSession(true).invalidate();
        response.sendRedirect("Index");
    }
    
    public void actionMisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.checkAccessLogin(request, response);
        
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
        
        this.checkAccessLogin(request, response);
        
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
}
