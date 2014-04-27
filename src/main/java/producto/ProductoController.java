package producto;

import java.io.File;
import others.NumChar;
import others.Controller;
import others.PageTemplate;
import others.Functions;
import others.TreeView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.util.Streams;

public class ProductoController extends Controller {

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    private static final String ERROR = "error";
    private static final String PRODUCTO = "Producto";
    private static final String FA_DASHBOARD = "fa-dashboard";
    private static final String NUEVO_PRODUCTO = "Nuevo producto";
    private static final String TEMPLATEPAGE = "templatepage";
    private static final String TEMP_TEMP = "/templates/template.jsp";
    private static final String PRODUCTS = "products";
    private static final String separator = System.getProperty("file.separator");

    /*private String UPLOAD_DIRECTORY;

    @Override
    public void init() throws ServletException {
        UPLOAD_DIRECTORY = File.separator + "assets" + File.separator + "img" + File.separator + PRODUCTS;
    }*/

    public void postEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String name = null, category = null, price_aux = null, img_route = "", img_route_old = null, id_aux = null;
        
        // Check correct values
        String error = "";
        List<FileItem> multiparts = (List<FileItem>) request.getAttribute("multiparts");
        for(FileItem item: multiparts) {
            if(item.isFormField()) {        // Esto es un input : D
                if(item.getFieldName().equals("namefield")) {
                    name = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("categoryfield")) {
                    category = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("pricefield")) {
                    price_aux = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("file_hidden")) {
                    img_route_old = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("id_prod")) {
                    id_aux = Streams.asString(item.getInputStream());
                }
            } else {
                File archivo_server = new File(getServletContext().getRealPath("/") + "assets" + separator + "img" + separator + "products" + separator + item.getName());
                try {
                    item.write(archivo_server);
                    img_route = "assets/img/products/" + item.getName();
                } catch (Exception ex) {
                    error += "<li>Error al intentar subir imagen</li>";
                }
                System.out.println("Nombre --> " + item.getName() );
                System.out.println("<br> Tipo --> " + item.getContentType());
                System.out.println("<br> tamaño --> " + (item.getSize()/1240)+ "KB");
            }
        }
        
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");

        if (!Functions.isID(id_aux)) {
            error += "<li>Producto not found.</li>";
        }
        if (Functions.isEmpty(name)) {
            error += "<li>Name not found.</li>";
        }
        if (!Functions.existCategory(category)) {
            error += "<li>Category don´t found or incorrect.</li>";
        }
        if (!Functions.isPrice(price_aux)) {
            error += "<li>Incorrect price.</li>";
        }
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
         NumChar generateSecurity = Functions.generateNumChar(random);
        
         NumChar formSecurity = new NumChar(intSec, charSec);
        
         if(!generateSecurity.equals(formSecurity)) {
         error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
         }*/
        boolean errorB = false;
        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
            errorB = true;
        } else {
            int id = Integer.parseInt(id_aux);
            double price = Double.parseDouble(price_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            if(Functions.isEmpty(img_route)) {
                img_route = img_route_old;
            }
            boolean update = dao.update(id, name, category, price, img_route);
            if (update) {
                request.setAttribute("ok", "Producto actualizado con éxito");
            } else {
                request.setAttribute(ERROR, "No se ha podido actualizar.");
                errorB = true;
            }
            dao.close();
        }
        if(errorB) {
            request.setAttribute("name", name);
            request.setAttribute("category", category);
            request.setAttribute("price", price_aux);
            request.setAttribute("image", img_route_old);
            request.setAttribute("id", id_aux);
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, NUEVO_PRODUCTO);
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);

    }

    public void postInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String name = "";// = request.getParameter("namefield");
        String category = "";// = request.getParameter("categoryfield");
        String price_aux = "";// = request.getParameter("pricefield");
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");

        String img_route = "";
        
        // Check correct values
        String error = "";
        
        List<FileItem> multiparts = (List<FileItem>) request.getAttribute("multiparts");
        for(FileItem item: multiparts) {
            if(item.isFormField()) {        // Esto es un input : D
                if(item.getFieldName().equals("namefield")) {
                    name = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("categoryfield")) {
                    category = Streams.asString(item.getInputStream());
                } else if(item.getFieldName().equals("pricefield")) {
                    price_aux = Streams.asString(item.getInputStream());
                }
            } else {
                File archivo_server = new File(getServletContext().getRealPath("/") + "assets" + separator + "img" + separator + "products" + separator + item.getName());
                try {
                    item.write(archivo_server);
                    img_route = "assets/img/products/" + item.getName();
                } catch (Exception ex) {
                    error += "<li>Error al intentar subir imagen</li>";
                }
                System.out.println("Nombre --> " + item.getName() );
                System.out.println("<br> Tipo --> " + item.getContentType());
                System.out.println("<br> tamaño --> " + (item.getSize()/1240)+ "KB");
            }
        }
        if (Functions.isEmpty(name)) {
            error += "<li>Name not found.</li>";
        }
        if (!Functions.existCategory(category)) {
            error += "<li>Category don´t found or incorrect.</li>";
        }
        if (!Functions.isPrice(price_aux)) {
            error += "<li>Incorrect price.</li>";
        }
        if(Functions.isEmpty(img_route)) {
            error += "<li>Imagen vacía</li>";
        }
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
         NumChar generateSecurity = Functions.generateNumChar(random);
        
         NumChar formSecurity = new NumChar(intSec, charSec);
        
         if(!generateSecurity.equals(formSecurity)) {
         error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
         }*/

        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
        } else {
            double price = Double.parseDouble(price_aux);
            System.out.println("Insertado");
            ProductoDAO dao = new ProductoDAO(ds);

            boolean insert = dao.insert(name, img_route, category, price);
            if (insert) {
                request.setAttribute("ok", "El producto " + name + " se ha insertado correctamente.");
            } else {
                request.setAttribute(ERROR, "No se ha podido ingresar.");
            }
            dao.close();
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Nuevo");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("producto/create.jsp", "", tv, null, footer, null, "", true, NUEVO_PRODUCTO);
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessAdmin(request, response);

        String random = Functions.updateSecurity(request.getSession(true));  // Código seguridad de usuario

        NumChar generateSecurity = Functions.generateNumChar(random);

        request.setAttribute("asociatedchar", generateSecurity.getChar());
        request.setAttribute("asociatedpos", generateSecurity.getPos());

        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Nuevo");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("producto/create.jsp", "", tv, null, footer, null, "", true, NUEVO_PRODUCTO);
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

    public void actionEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String id_aux = request.getParameter("id_prod");

        // Check correct values
        String error = "";
        if (!Functions.isID(id_aux)) {
            error += "<li>Producto not found.</li>";
        }

        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            Producto p = dao.get(id);
            if (p == null) {
                request.setAttribute("error", "Producto no encontrado");
            } else {
                request.setAttribute("category", p.getCategoria());
                request.setAttribute("id", p.getId());
                request.setAttribute("image", p.getImage());
                request.setAttribute("name", p.getName());
                request.setAttribute("price", p.getPrecio());
            }
            dao.close();
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();

        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, NUEVO_PRODUCTO);
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

    public void actionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getAll();
        if (products == null) {
            products = new ArrayList<Producto>();
        }
        request.setAttribute(PRODUCTS, products);
        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Listado");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");

        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Listar productos");
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String id_aux = request.getParameter("id_prod");
        List<Producto> products = null;
        
        // Checks errors
        if (!Functions.isID(id_aux)) {
            request.setAttribute(ERROR, "No se encuentra el producto a borrar.");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            boolean delete = dao.delete(id);
            if (delete) {
                request.setAttribute("ok", "Borrado con éxito");
            } else {
                request.setAttribute(ERROR, "No se encuentra el producto a borrar.");
            }
            
            products = dao.getAll();
            if (products == null) {
                products = new ArrayList<Producto>();
            }
            
            request.setAttribute(PRODUCTS, products);
            
            dao.close();
        }
        request.setAttribute(PRODUCTS, products);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Administrador");
        ltv.add("Listado productos");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");
        
        PageTemplate pt = new PageTemplate("admin/list.jsp", "", tv, null, footer, null, "", true, "Listar productos", true);
        request.getSession().setAttribute(TEMPLATEPAGE, pt);
        
        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

    public void actionLast(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getLast(10);
        if (products == null) {
            products = new ArrayList<Producto>();
        }
        dao.close();
        request.setAttribute(PRODUCTS, products);
        List<String> ltv = new LinkedList<String>();
        ltv.add(PRODUCTO);
        ltv.add("Últimos productos");
        TreeView tv = new TreeView(ltv, FA_DASHBOARD);

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");

        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Listar productos");
        request.getSession().setAttribute(TEMPLATEPAGE, pt);

        getServletContext().getRequestDispatcher(TEMP_TEMP).forward(request, response);
    }

}
