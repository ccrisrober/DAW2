package admin;

import java.io.IOException;
import java.util.ArrayList;
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
import producto.Producto;
import producto.ProductoDAO;
import user.User;
import user.UserDAO;

public class AdminController extends Controller {
    
    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessAdmin(request, response);
        UserDAO dao = new UserDAO(ds);
        List<User> users = dao.getAll();
        if(users == null) {
            users = new LinkedList<User>();
        }
        request.setAttribute("users", users);
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Administrador");
        ltv.add("Listado usuarios");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/usuario/list.js");
        
        PageTemplate pt = new PageTemplate("admin/user.jsp", "", tv, null, footer, null, "", true, "Listar productos", true);
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessAdmin(request, response);
        
        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getAll();
        if(products == null) {
            products = new ArrayList<Producto>();
        }
        request.setAttribute("products", products);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Administrador");
        ltv.add("Listado productos");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");
        
        PageTemplate pt = new PageTemplate("admin/list.jsp", "", tv, null, footer, null, "", true, "Listar productos", true);
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }    
    
    public void actionPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessAdmin(request, response);
        
        PedidoDAO pDao = new PedidoDAO(ds);
        List<Pedido> pedidos = pDao.getAll();
        if(pedidos == null) {
            pedidos = new ArrayList<Pedido>();
        }
        request.setAttribute("pedidos", pedidos);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Administrador");
        ltv.add("Listado pedidos");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");
        
        PageTemplate pt = new PageTemplate("admin/pedidos.jsp", "", tv, null, footer, null, "", true, "Listar pedidos", true);
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.checkAccessAdmin(request, response);

        String id_pedido = request.getParameter("id_ped");

        String error = "";
        if (!Functions.isID(id_pedido)) {
            error += "<li>Pedido not found.</li>";
        }

        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_pedido);
            PedidoDAO dao = new PedidoDAO(ds);
            Pedido pedido = dao.get(id);
            dao.validate(id);
            if (pedido == null) {
                request.setAttribute("error", "Pedido no encontrado");
            } else {
                request.setAttribute("id_pedido", pedido.getId_pedido());
                request.setAttribute("id_usu", pedido.getId_usu());
                request.setAttribute("date", pedido.getDate());
                request.setAttribute("price", pedido.getDate());
                request.setAttribute("validate", pedido.isProcesado());
                
            }
            
            List<Pedido> pedidos = dao.getAll();
            if(pedidos == null) {
                System.out.println("hola3");
                pedidos = new ArrayList<Pedido>();
            }
            request.setAttribute("pedidos", pedidos);
            
            dao.close();
        }       

        List<String> ltv = new LinkedList<String>();
        ltv.add("Pedido");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        List<String> footer = new LinkedList<String>();
        if (!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/admin/pedidos.js");
        }

        PageTemplate pt = new PageTemplate("admin/pedidos.jsp", "", tv, null, footer, null, "", true, "Listar de pedidos", true);
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postValidar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        
        String error = "";
        
        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            PedidoDAO dao = new PedidoDAO(ds);
            dao.validateAll();
            /*if (pedido == null) {
                request.setAttribute("error", "Pedido no encontrado");
            } else {
                request.setAttribute("id_pedido", pedido.getId_pedido());
                request.setAttribute("id_usu", pedido.getId_usu());
                request.setAttribute("date", pedido.getDate());
                request.setAttribute("price", pedido.getDate());
                request.setAttribute("validate", pedido.isProcesado());
            }*/
            
            List<Pedido> pedidos = dao.getAll();
            if(pedidos == null) {
                pedidos = new ArrayList<Pedido>();
            }
            request.setAttribute("pedidos", pedidos);
            
            dao.close();
        }       

        List<String> ltv = new LinkedList<String>();
        ltv.add("Pedido");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        List<String> footer = new LinkedList<String>();
        if (!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/admin/pedidos.js");
        }

        PageTemplate pt = new PageTemplate("admin/pedidos.jsp", "", tv, null, footer, null, "", true, "Listar de pedidos", true);
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
}