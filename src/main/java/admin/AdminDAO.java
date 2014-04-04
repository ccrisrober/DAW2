package admin;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class AdminDAO {
    
    private Connection conn;
    
    public AdminDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error con la conexión.",e);
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
}