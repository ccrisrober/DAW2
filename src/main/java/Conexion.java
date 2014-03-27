/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author sin_querer
 */
public class Conexion {
    private static Connection cn = null;
    private static Driver driver = new org.apache.derby.jdbc.ClientDriver();
    private static String URLDerby = "jdbc:derby://localhost:1527/tienda_c.rodriguezbe";
    private static String usuario = "coniopuntoh";
    private static String contrasena = "coniopuntoh";
   
    public static Connection getConexion() {
        try {
            DriverManager.registerDriver(driver);
            cn = DriverManager.getConnection(URLDerby, usuario, contrasena);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cn;
    }
}