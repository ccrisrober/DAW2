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
        int salida = 0;
        if(user.equals("costa") && (pass.equals("costa"))) {
            salida = 2;
        } else if(user.equals("admin") && (pass.equals("admin"))) {
            salida = 1;
        } else if(user.equals("cristian") && (pass.equals("cristian"))) {
            salida = 3;
        } else if(user.equals("dani") && (pass.equals("dani"))) {
            salida = 4;
        }
        return salida;
    }

    public boolean register(String user, String pass) {
        return true;
    }

    public User getUser(int id_user) {
        User salida = null;
        switch(id_user) {
            case 1:
                salida = new User("admin", "admin");
                break;
            case 2:
                salida = new User("costa", "costa");
                break;
            case 3:
                salida = new User("cristian", "cristian");
                break;
            case 4:
                salida = new User("dani", "dani");
                break;
        }
        return salida;
    }

    public boolean editPassword(int id_user, String password) {
        return true;
    }

    public boolean isAdmin(int pos) {
        return pos == 1;
    }
}
