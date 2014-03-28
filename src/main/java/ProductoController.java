/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import others.NumChar;
import others.Controller;
import others.PageTemplate;
import others.Functions;
import others.TreeView;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Cristian
 */
public class ProductoController extends Controller {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        actionList(request, response);
    }
        
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    
    //Este será por POST, cambiar en la función post y también en lo de SSDD
    public void postEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_aux = request.getParameter("idfield");
        String name = request.getParameter("namefield");
        String category = request.getParameter("categoryfield");
        String price_aux = request.getParameter("pricefield");
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");
        
        // Check correct values
        String error = "";
        if(!Functions.isID(id_aux)) {
            error += "<li>Producto not found.</li>";
        }
        if(Functions.isEmpty(name)) {
            error += "<li>Name not found.</li>";
        }
        if(!Functions.existCategory(category)) {
            error += "<li>Category don´t found or incorrect.</li>";
        }
        if(!Functions.isPrice(price_aux)) {
            error += "<li>Incorrect price.</li>";
        }
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
        NumChar generateSecurity = Functions.generateNumChar(random);
        
        NumChar formSecurity = new NumChar(intSec, charSec);
        
        if(!generateSecurity.equals(formSecurity)) {
            error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
        }*/
        
        
        if(!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_aux);
            double price = Double.parseDouble(price_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            boolean update = dao.update(id, name, category, price);
            if(update) {
                request.setAttribute("ok", "Producto actualizado con éxito");
            } else {
                request.setAttribute("error", "No se ha podido actualizar.");
            }
        }
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/update.js");
        }
        
        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
        
    }
    
    public void postInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = "Producto";//request.getParameter("namefield");
        String category = "Alimentación";//request.getParameter("categoryfield");
        String price_aux = "120.00";//request.getParameter("pricefield");
        //String charSec = request.getParameter("ac");
        //String intSec = request.getParameter("ai");
        
        // Check correct values
        String error = "";
        if(Functions.isEmpty(name)) {
            error += "<li>Name not found.</li>";
        }
        if(!Functions.existCategory(category)) {
            error += "<li>Category don´t found or incorrect.</li>";
        }
        if(!Functions.isPrice(price_aux)) {
            error += "<li>Incorrect price.</li>";
        }
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
        NumChar generateSecurity = Functions.generateNumChar(random);
        
        NumChar formSecurity = new NumChar(intSec, charSec);
        
        if(!generateSecurity.equals(formSecurity)) {
            error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
        }*/
        
        if(!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {     // If not errors
            double price = Double.parseDouble(price_aux);

            ProductoDAO dao = new ProductoDAO(ds);

            boolean insert = dao.insert(name, category, price);
            if(insert) {
                request.setAttribute("ok", insert);
            } else {
                request.setAttribute("error", "No se ha podido ingresar.");
            }
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Nuevo");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/create.js");
        }
        
        PageTemplate pt = new PageTemplate("producto/create.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
        
        
    }
    
    
    
    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        String id_aux = request.getParameter("idfield");
        
        // Check correct values
        String error = "";
        if(!Functions.isID(id_aux)) {
            error += "<li>Producto not found.</li>";
        }
        
        if(!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            Producto p = dao.get(id);
            if(p == null) {
                request.setAttribute("error", "Producto no encontrado");
            } else {
                request.setAttribute("producto", p);
            }
        }
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Actualizar");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        if(!error.isEmpty()) {
            footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
            footer.add("assets/js/producto/update.js");
        }
        
        PageTemplate pt = new PageTemplate("producto/update.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("hola");
        ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> productos = dao.getAll();
        if(productos == null) {
            productos = new ArrayList<Producto>();
        }
        out.println("SIZE: " + productos.size());
        out.println("hola");
        out.close();
        /*ProductoDAO dao = new ProductoDAO(ds);
        List<Producto> products = dao.getAll();
        PrintWriter out = response.getWriter();
        out.println("hola");
        out.println(products);
        if(products == null) {
            products = new ArrayList<Producto>();
        }
        out.println(products.size());
        out.close();*/
        /*request.setAttribute("products", products);
        List<String> ltv = new LinkedList<String>();
        ltv.add("Producto");
        ltv.add("Listado");
        TreeView tv = new TreeView(ltv, "fa-dashboard");
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/producto/list.js");
        
        PageTemplate pt = new PageTemplate("producto/list.jsp", "", tv, null, footer, null, "", true, "Listar productos");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);*/
    }
    
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_aux = request.getParameter("idfield");
        
        // Checks errors
        if(!Functions.isID(id_aux)) {
            request.setAttribute("error", "No se encuentra el producto a borrar.");
        } else {
            int id = Integer.parseInt(id_aux);
            ProductoDAO dao = new ProductoDAO(ds);
            boolean delete = dao.delete(id);
            if(delete) {
                request.setAttribute("ok", "Borrado con éxito");
            } else {
                request.setAttribute("error", "No se encuentra el producto a borrar.");
            }
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
}
