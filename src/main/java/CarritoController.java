import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import pedido.PedidoDAO;
import producto.Producto;
import producto.ProductoDAO;

/**
 *
 * @author Cristian
 */
@WebServlet(urlPatterns = {"/CarritoController"})
public class CarritoController extends Controller {

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        String id_usu_aux = (String) session.getAttribute("id_user");
        String error = "";
        
        /*if(!Functions.isID(error)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        } else {*/
            session.setAttribute("carrito", new Carrito(1/*Integer.parseInt(id_usu_aux)*/));
            ProductoDAO dao = new ProductoDAO(ds);
            List<Producto> products = dao.getAll();
            System.out.println(products.size());
            if (products == null) {
                products = new LinkedList<Producto>();
            }
            request.setAttribute("products", products);
        //}     
        
        //Esto está mal, es para probar : D
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/update.js");
        }
        
        PageTemplate pt = new PageTemplate("pedido/list.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void postListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        System.out.println("-----------");
        System.out.println("-----------");
        System.out.println("Valores: ");
        String values = "";
        Map<String, Integer> map = new HashMap<String, Integer>();
            for(Map.Entry<String, String[]> entry: entrySet) {
                if(entry.getKey().startsWith("cantidad")) {
                    String key = entry.getKey().substring("cantidad".length()+1, entry.getKey().length()-1);
                    if(Integer.parseInt(entry.getValue()[0]) > 0) {
                        map.put(key, Integer.parseInt(entry.getValue()[0]));
                    }
                }
            }
        Set<Map.Entry<String, Integer>> entrySet1 = map.entrySet();
        for(Map.Entry<String, Integer> e: entrySet1) {
            System.out.println(e);
        }
        /*HttpSession session = request.getSession(true);
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if(carrito == null) {
        //error
        } else {
        System.out.println("\n\nvalores: ");
        for(Map.Entry<String, String[]> entry: entrySet) {
        System.out.print(entry.getKey());
        for(String ss: entry.getValue()) {
        System.out.println(ss);
        }
        }
        }*/
    }
    
    public void postFinish(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        String id_usu_aux = (String) session.getAttribute("id_user");
        String error = "";
        
        if(!Functions.isID(error)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }else{
            Carrito car = (Carrito) session.getAttribute("carrito");
            Map<Integer, Integer> productos = car.getProductos();
            
            PedidoDAO pedidos = new PedidoDAO(ds);
            boolean creado = pedidos.create(productos, Integer.parseInt(id_usu_aux));
            
            if(!creado){
                request.setAttribute("error", error);
            }else{
                request.setAttribute("ok", "pedido creado con éxito");
            }
        } 
        
        //redireccion
    }
    
}
