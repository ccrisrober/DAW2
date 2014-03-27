
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Cristian
 */
public class ProductoDAO {
    
    private Connection conn;
    
    public ProductoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos",e);
        }
    }
    
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public List<Producto> getProductos() {
        return null;
    }
    
    public List<Producto> getAll() {
        return getProductosQuery(null);
    }
    
    public List<Producto> getProductosQuery(String where) {
        List<Producto> productos = new ArrayList<Producto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Producto";    // Aquí va la consulta
            
            if (where != null) {
                
            }
            ResultSet rs = stm.executeQuery(sql);
            productos = createProductosFromRS(rs);
            //cargarAutores(libros);
            rs.close();
            //....
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta",e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return productos;
    }

    private List<Producto> createProductosFromRS(ResultSet rs) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String categoria = rs.getString("categoria");
            double precio = rs.getDouble("precio");
            productos.add(new Producto(id, name, categoria, precio));
        }
        return productos;
    }

    boolean insert(String name, String category, double price) {
        boolean insert_ = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO producto VALUES(?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            int executeUpdate = ps.executeUpdate();
            if(executeUpdate == 1) {
                insert_ = true;     // http://lineadecodigo.com/java/insertar-datos-con-jdbc/
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return insert_;
    }

    boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
