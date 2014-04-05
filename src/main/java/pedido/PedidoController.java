/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

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
import others.PageTemplate;
import others.TreeView;

/**
 *
 * @author Cristian
 */
public class PedidoController extends Controller {
    
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public void actionGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_ped_aux = request.getParameter("id_ped");
        
        int id_user = 1;        // Esto sería mirar la variable de sesión
        
        
        //Comprobamos errores
        String error = "";
        
        
        
        if(!error.isEmpty()) {
            
        } else {
            int id_ped = Integer.parseInt(id_ped_aux);
            PedidoDAO dao = new PedidoDAO(ds);
            if(dao.haveAccess(id_ped, id_user)) {
                Pedido pedido = dao.get(id_ped);
                if(pedido == null) {
                    request.setAttribute("error", "Interneeeeeeet");
                } else {
                    request.setAttribute("pedido", pedido);
                }
            } else {
                request.setAttribute("error", "No tienes acceso");
            }
            dao.close();
        }
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
        
        PageTemplate pt = new PageTemplate("pedido/show.jsp", "", tv, null, footer, null, "", true, "Nuevo producto");
        request.getSession().setAttribute("templatepage", pt);
        
        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }
    
    public void actionIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("hola");
        out.close();
    }
    
}
