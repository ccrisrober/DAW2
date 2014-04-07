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
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class ProductoController extends Controller {

    @Resource(lookup = "jdbc/tienda_crodriguezbe")
    private DataSource ds;

    private String UPLOAD_DIRECTORY;

    @Override
    public void init() throws ServletException {
        try {
            UPLOAD_DIRECTORY = new File(".").getCanonicalPath() + File.separator + "assets" + File.separator + "img" + File.separator + "products";
        } catch (IOException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Este será por POST, cambiar en la función post y también en lo de SSDD
    public void postEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String id_aux = request.getParameter("idfield");
        String name = request.getParameter("namefield");
        String category = request.getParameter("categoryfield");
        String price_aux = request.getParameter("pricefield");
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");

        // Check correct values
        String error = "";
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

        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_aux);
            double price = Double.parseDouble(price_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            boolean update = dao.update(id, name, category, price);
            if (update) {
                request.setAttribute("ok", "Producto actualizado con éxito");
            } else {
                request.setAttribute("error", "No se ha podido actualizar.");
            }
            dao.close();
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        if (!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/update.js");
        }

        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);

    }

    public void postInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String name = ""; //= request.getParameter("namefield");
        String category = ""; //= request.getParameter("categoryfield");
        String price_aux = ""; //= request.getParameter("pricefield");
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");

        String img_route = "";

        
        //http://stackoverflow.com/questions/9625952/java-servlet-file-upload-of-images-is-the-code-efficient-or-allowing-for-memor
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemIterator iterator = new ServletFileUpload().getItemIterator(request);
                while (iterator.hasNext()) {
                    FileItemStream item = iterator.next();
                    if (item.isFormField()) {
                        if(item.getFieldName().equals("namefield")) {
                            name = Streams.asString(item.openStream());
                        } else if(item.getFieldName().equals("categoryfield")) {
                            category = Streams.asString(item.openStream());
                        } else if(item.getFieldName().equals("pricefield")) {
                            price_aux = Streams.asString(item.openStream());
                        }
                    } else {
                        if (item.getContentType().equals("image/jpeg")
                                || item.getContentType().equals("image/jpg")
                                || item.getContentType().equals("image/png")) {
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                            img_route = UPLOAD_DIRECTORY + File.separator + name;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Check correct values
        String error = "";
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

        /*if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (item.isFormField()) {
                        if(item.getFieldName().equals("action")) {
                            namefield = Streams.asString(item.openStream());
                        }
                    } else {
                        
                    }
                }
            } catch (Exception ex) {
                error += "File Upload Failed due to " + ex;
            }
        }*/

        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            double price = Double.parseDouble(price_aux);
            System.out.println("Insertado xD");
            ProductoDAO dao = new ProductoDAO(ds);

            boolean insert = dao.insert(name, img_route, category, price);
            if (insert) {
                request.setAttribute("ok", "El producto " + name + " se ha insertado correctamente.");
            } else {
                request.setAttribute("error", "No se ha podido ingresar.");
            }
            dao.close();
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Nuevo");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        if (!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/create.js");
        }

        PageTemplate pt = new PageTemplate("producto/create.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.checkAccessAdmin(request, response);

        String random = Functions.updateSecurity(request.getSession(true));  // Código seguridad de usuario

        NumChar generateSecurity = Functions.generateNumChar(random);

        request.setAttribute("asociatedchar", generateSecurity.getChar());
        request.setAttribute("asociatedpos", generateSecurity.getPos());

        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Nuevo");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
        footer.add("assets/js/producto/create.js");

        PageTemplate pt = new PageTemplate("producto/create.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void actionUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String id_aux = request.getParameter("idfield");

        // Check correct values
        String error = "";
        if (!Functions.isID(id_aux)) {
            error += "<li>Producto not found.</li>";
        }

        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            Producto p = dao.get(id);
            if (p == null) {
                request.setAttribute("error", "Producto no encontrado");
            } else {
                request.setAttribute("producto", p);
            }
            dao.close();
        }

        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        if (!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/update.js");
        }

        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void actionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getAll();
        if (products == null) {
            products = new ArrayList<Producto>();
        }
        request.setAttribute("products", products);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Listado");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");

        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Listar productos");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.checkAccessAdmin(request, response);

        String id_aux = request.getParameter("idfield");

        // Checks errors
        if (!Functions.isID(id_aux)) {
            request.setAttribute("error", "No se encuentra el producto a borrar.");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            boolean delete = dao.delete(id);
            if (delete) {
                request.setAttribute("ok", "Borrado con éxito");
            } else {
                request.setAttribute("error", "No se encuentra el producto a borrar.");
            }
            dao.close();
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Borrar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/delete.js");

        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Borrar producto");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    public void actionLast(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getLast(15);
        if (products == null) {
            products = new ArrayList<Producto>();
        }
        dao.close();
        request.setAttribute("products", products);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Últimos productos");
        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");

        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Listar productos");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

}
