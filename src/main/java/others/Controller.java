package others;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller extends HttpServlet {

    private void callFunction(HttpServletRequest request, HttpServletResponse response, String nombre, String type) {
        Class c = this.getClass();
        Object[] args_value = {request, response};
        Class[] args_class = {HttpServletRequest.class, HttpServletResponse.class};
        Method m = null;

        String action = (nombre.charAt(0) + "").toUpperCase();
        nombre = nombre.replaceFirst(nombre.charAt(0) + "", action);

        try {
            m = c.getMethod(type + nombre, args_class);
        } catch (SecurityException se) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, se);
        } catch (NoSuchMethodException nsme) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, nsme);
        }

        if (m != null) {
            try {
                m.invoke(this, args_value);
            } catch (IllegalArgumentException iae) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, iae);
            } catch (IllegalAccessException iae) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, iae);
            } catch (InvocationTargetException ite) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ite);
            }
        } else {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            callFunction(request, response, action, "action");
            //action = "Index";
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Datos: : D");
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for(Map.Entry<String, String[]> entry: entrySet) {
            System.out.print(entry.getKey());
            for(String ss: entry.getValue()) {
                System.out.println(ss);
            }
        }
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            callFunction(request, response, action, "post");
        }
    }
}
