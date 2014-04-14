package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import others.Controller;
import others.Functions;
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
        ltv.add("Cambiar contraseña");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        
        PageTemplate pt = new PageTemplate("user/change_password.jsp", "", tv, null, footer, null, "", true, "Editar contraseña");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        
        int id_user = (Integer)request.getSession(true).getAttribute("id_user");
        
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if(!password1.equals(password2)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
        } else {
            UserDAO user = new UserDAO(ds);
            boolean edit = user.editPassword(id_user, password2);
            if(edit) {
                request.setAttribute("ok", "Contraseña cambiada con éxito");
            } else {
                request.setAttribute("error", "No se ha podido editar la contraseña.");
            }
        }
        actionEditPassword(request, response);
    }
    
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        PrintWriter out = response.getWriter();
        int id_user = (Integer)request.getSession(true).getAttribute("id_user");
        UserDAO dao = new UserDAO(ds);
        boolean delete = dao.delete(id_user);
        if(delete) {
            request.getSession(true).invalidate();
            out.println("<script>alert('Usuario borrado con éxito'); windows.location.href='Index'</script>");
        } else {
            out.println("<script>alert('Error, no se ha podido borrar la cuenta'); windows.location.href='Index'</script>");
        }
        dao.close();
        out.close();
    }
    
    public void actionMisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        
        int id_usu_aux = (Integer)request.getSession(true).getAttribute("id_user");
        
        PedidoDAO dao = new PedidoDAO(ds);
        List<Pedido> pedidos = dao.getAll(id_usu_aux);
        if(pedidos == null) {
            pedidos = new LinkedList<Pedido>();
        }
        dao.close();
        request.setAttribute("pedidos", pedidos);
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Usuario");
        ltv.add("Mis pedidos");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        
        PageTemplate pt = new PageTemplate("user/list.jsp", "", tv, null, footer, null, "", true, "Mis pedidos");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.checkAccessLogin(request, response);
        
        int id_usu_aux = -1;
        String name = "Unknow";
        if(isAdmin(request)) {
            String id_aux = request.getParameter("id");
            if(!Functions.isID(id_aux)) {
                request.setAttribute("error", "Usuario no identificado");
            } else {
                id_usu_aux = Integer.parseInt(id_aux);
            }
        } else {
            id_usu_aux = (Integer)request.getSession(true).getAttribute("id_user");
        }
        if(id_usu_aux >= 0) {
        UserDAO dao = new UserDAO(ds);
        User user = dao.getUser(id_usu_aux);
        if(user != null) {
            request.setAttribute("user", user);
            name = user.getName();
        } else {
            request.setAttribute("error", "Usuario no encontrado");
        }
        dao.close();
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add("Usuario");
        ltv.add("Perfil");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        
        PageTemplate pt = new PageTemplate("user/profile.jsp", "", tv, null, footer, null, "", true, "Perfil - " + name);
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
}
