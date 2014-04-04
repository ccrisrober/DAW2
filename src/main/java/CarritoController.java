import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        
        if(!Functions.isID(error)){
            error = "No se encuentra la sesión";
        }
        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }else{
            session.setAttribute("carrito", new Carrito(Integer.parseInt(id_usu_aux)));
            ProductoDAO conexionBD = new ProductoDAO(ds);
            List<Producto> all = conexionBD.getAll();
            
            if(all == null){
                all = new LinkedList<Producto>();
            }
            request.setAttribute("productos", all);
        }        
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
