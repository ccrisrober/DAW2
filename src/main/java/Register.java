
import java.io.IOException;
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
import user.UserDAO;

public class Register extends Controller {

    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> ltv = new LinkedList<String>();

        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> header = new LinkedList<String>();

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/index/index.js");

        PageTemplate pt = new PageTemplate("register.jsp", "index", tv, header, footer, null, "", true, "Dashboard");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("userfield");
        String pass = request.getParameter("passfield");
        String pass2 = request.getParameter("passfield2");

        String error = "";
        boolean errorPass = false;
        if (Functions.isEmpty(user)) {
            error += "<li>Usuario está vacío</li>";
        }
        if (Functions.isEmpty(pass)) {
            error += "<li>Password está vacío</li>";
            errorPass = true;
        }
        if (Functions.isEmpty(pass2)) {
            error += "<li>Password Confirm está vacío</li>";
            errorPass = true;
        }
        if (!errorPass) {
            if (!pass.equals(pass2)) {
                error += "<li>Las contraseñas no son iguales</li>";
            }
        }

        request.setAttribute("userfield", user);
        request.setAttribute("passfield", pass);
        request.setAttribute("passfield2", pass2);

        if (!error.isEmpty()) {
            request.setAttribute("error", "<ul>" + error + "</ul>");
        } else {
            UserDAO dao = new UserDAO(ds);
            if (dao.exist(user)) {
                request.setAttribute("error", "El usuario ya existe");
            } else {
                boolean exito = dao.register(user, pass);
                if (exito) {
                    request.setAttribute("ok", "¡Se ha ingresado con éxito!");
                } else {
                    request.setAttribute("error", "Error al registrar. Es posible que el usuario ya exista");
                }
            }
            dao.close();
        }
        doGet(request, response);
    }
}
