
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class pruena {
    public static void main(String[] argv) throws NamingException, SQLException {
        /*//Usar JNDI
        Context ctx = new InitialContext();           
        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/tienda_c.rodriguezbe");

        //Obtener la conexión del Pool
        Connection cn = ds.getConnection();

        System.out.println("Conexión OK");

        //Devolver la conexión al Pool
        cn.close();*/
        InitialContext initialContext = new InitialContext();
        Context context = (Context) initialContext.lookup("java:comp/env");
        //The JDBC Data source that we just created
        DataSource ds = (DataSource) context.lookup("jdbc/tienda_c.rodriguezbe");
        Connection connection = ds.getConnection();

        if (connection == null) {
            throw new SQLException("Error establishing connection!");
        }
    }
}
