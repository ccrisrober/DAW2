/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author d
 */
public class UserDAO {
    private Connection conn;
    
    public UserDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos " + e);
        }
    }
    
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    synchronized public int validate(String user, String pass) {
        return (user.equals("costa") && (pass.equals("zorra"))) ? 1: 0;
    }

    public boolean register(String user, String pass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User getUser(int id_user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean editPassword(int id_user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isAdmin(int pos) {
        return false;
    }
}
