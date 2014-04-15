package pedido;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import others.Controller;
import others.PageTemplate;
import others.TreeView;
import producto.Producto;
import producto.ProductoDAO;

public class CarritoController extends Controller {

    private int numpedido;

    @Override
    public void init() throws ServletException {
        numpedido = 0;
    }

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;

    /**
     * Redefinimos esta operación para que el usuario no pueda recargar la
     * página una vez finalizado el pago
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty() && action.equals("create")) {
            actionCreate(request, response);
        } else {
            request.setAttribute("error", "No puedes volver a editar un carrito ya creado");
            response.sendRedirect("Index");
        }
    }

    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);

        numpedido++;

        HttpSession session = request.getSession(true);

        session.setAttribute("num_ped", numpedido);

        int id_usu_aux = (Integer) request.getSession(true).getAttribute("id_user");

        session.setAttribute("carrito", new Carrito(id_usu_aux));
        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getAll();
        if (products == null) {
            products = new LinkedList<Producto>();
        }
        dao.close();
        request.setAttribute("products", products);

        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("pedido/list.jsp", "", tv, null, footer, null, "", true, "Crear carrito");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void postListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        HttpSession session = request.getSession(true);
        Object var = session.getAttribute("carrito");

        if ((var != null) && (var instanceof Carrito)) {
            Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

            int num_ped = (Integer) session.getAttribute("num_ped");
            int id_user = (Integer) session.getAttribute("id_user");  //Esto puede servir para validar que todo va bien, creo xD

            List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();

            ProductoDAO dao = new ProductoDAO(ds);

            double total = 0.0;
            Carrito carr = (Carrito) var;
            
            for (Map.Entry<String, String[]> entry : entrySet) {
                if (Pattern.compile("cantidad\\[[0-9]*\\]").matcher(entry.getKey()).find()) {
                    int key = Integer.parseInt(entry.getKey().substring("cantidad".length() + 1, entry.getKey().length() - 1));
                    try {
                    if (Integer.parseInt(entry.getValue()[0]) > 0) {
                        int value = Integer.parseInt(entry.getValue()[0]);
                        Producto producto = dao.get(key);
                        PedidoProducto pp = new PedidoProducto(producto, value);
                        lpp.add(pp);
                        total += producto.getPrecio() * value;
                        carr.annadirProducto(key, value);
                    }
                    } catch (NumberFormatException nfe) {
                        
                    }
                }
            }
            request.getSession(true).setAttribute("carrito", carr);
            dao.close();

            request.setAttribute("products", lpp);
            request.setAttribute("total", total);
        } else {
            request.setAttribute("error", "No existe ningún carrito asociado. Por favor, inicie de nuevo el pedido.");
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Factura");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("pedido/factura.jsp", "", tv, null, footer, null, "", true, "Factura");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void postFinish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessLogin(request, response);
        HttpSession session = request.getSession(true);
        int id_usu_aux = (Integer) session.getAttribute("id_user");
        Object var = session.getAttribute("carrito");
        if ((var != null) && (var instanceof Carrito)) {
            Carrito car = (Carrito) var;
            Map<Integer, Integer> productos = car.getProductos();

            PedidoDAO pedidos = new PedidoDAO(ds);
            boolean creado = pedidos.create(productos, id_usu_aux);
            if (!creado) {
                request.setAttribute("error", "Error durante la creación");
            } else {
                request.setAttribute("ok", "Pedido creado con éxito");
                session.setAttribute("carrito", null);
            }
            pedidos.close();
        } else {
            request.setAttribute("error", "El pedido ya se ha recibido o se ha expirado el tiempo de compra.");
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add("Carrito");
        ltv.add("Compra finalizada");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("pedido/finish.jsp", "", tv, null, footer, null, "", true, "Compra finalizada");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

}
