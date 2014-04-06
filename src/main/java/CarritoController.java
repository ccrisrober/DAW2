import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import others.Controller;
import others.Functions;
import others.PageTemplate;
import others.TreeView;
import pedido.Pedido;
import pedido.PedidoDAO;
import pedido.PedidoProducto;
import producto.Producto;
import producto.ProductoDAO;

/**
 *
 * @author Cristian
 */
@WebServlet(urlPatterns = {"/CarritoController"})
public class CarritoController extends Controller {

    private int numpedido;

    @Override
    public void init() throws ServletException {
        numpedido = 0;
    }

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        checkAccessLogin(request, response);
        numpedido++;
        
        HttpSession session = request.getSession(true);
        
        session.setAttribute("id_user", 1);
        session.setAttribute("num_ped", numpedido);
        
        int id_usu_aux = (Integer) session.getAttribute("id_user");
        String error = "";
        
        /*if(!Functions.isID(id_usu_aux)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        } else {*/
            session.setAttribute("carrito", new Carrito(id_usu_aux));
            ProductoDAO dao = new ProductoDAO(ds);
            List<Producto> products = dao.getAll();
            if (products == null) {
                products = new LinkedList<Producto>();
            }
            request.setAttribute("products", products);
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
        
        PageTemplate pt = new PageTemplate("pedido/list.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        PrintWriter out = response.getWriter();
        
        int num_ped = (Integer)request.getSession(true).getAttribute("num_ped");
        int id_user = (Integer)(request.getSession(true).getAttribute("id_user"));  //Esto puede servir para validar que todo va bien, creo xD
        
        List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();
        
        ProductoDAO dao = new ProductoDAO(ds);
        
        double total = 0.0;
        
        Carrito carr = (Carrito)request.getSession(true).getAttribute("carrito");
        
        for(Map.Entry<String, String[]> entry: entrySet) {
            if(entry.getKey().startsWith("cantidad")) {
                int key = Integer.parseInt(entry.getKey().substring("cantidad".length()+1, entry.getKey().length()-1));
                if(Integer.parseInt(entry.getValue()[0]) > 0) {
                    int value = Integer.parseInt(entry.getValue()[0]);
                    out.println("Obteniendo producto con id = " + key);
                    Producto producto = dao.get(key);
                    PedidoProducto pp = new PedidoProducto(producto, value);
                    lpp.add(pp);
                    total += producto.getPrecio()*value;
                    carr.annadirProducto(key, value);
                }
            }
        }
        request.getSession(true).setAttribute("carrito", carr);
        dao.close();
        
        out.println("Imprimimos resultados: " + lpp.size());
        for(PedidoProducto pp: lpp) {
            out.println(pp.getProd().getName() + ": " + pp.getQuantity());
        }
        
        request.setAttribute("products", lpp);
        request.setAttribute("total", total);
        
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
        
        PageTemplate pt = new PageTemplate("pedido/factura.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postFinish(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        checkAccessLogin(request, response);
        
        HttpSession session = request.getSession(true);
        int id_usu_aux = (Integer) session.getAttribute("id_user");
        /*String error = "";
        
        if(!Functions.isID(error)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }else{*/
            Carrito car = (Carrito) session.getAttribute("carrito");
            Map<Integer, Integer> productos = car.getProductos();
            
            PedidoDAO pedidos = new PedidoDAO(ds);
            boolean creado = pedidos.create(productos, id_usu_aux);
            PrintWriter out = response.getWriter();
            if(!creado){
                request.setAttribute("error", "Hay un error puto");
                out.println("Error");
            }else{
                request.setAttribute("ok", "pedido creado con éxito");
                out.println("todo guay");
            }
            out.close();
        //} 
        
        //redireccion
    }
    
}
