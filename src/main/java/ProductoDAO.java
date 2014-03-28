
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
            }
        }
    }
    
    synchronized public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<Producto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Producto";    // Aquí va la consulta
            System.out.println("---------------\n" + sql + "\n---------------");
            
            ResultSet rs = stm.executeQuery(sql);
            productos = createProductosFromRS(rs);
            rs.close();
            
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta: " + e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
            }
        }
        return productos;
    }
    
    private List<Producto> createProductosFromRS(ResultSet rs) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String categoria = rs.getString("category");
            double precio = rs.getDouble("price");
            productos.add(new Producto(id, name, categoria, precio));
        }
        return productos;
    }

    synchronized boolean insert(String name, String image, String category, double price) {
        boolean insert_ = false;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO producto (name, image, category, price) VALUES(?, ?, ?, ?)";
                        
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, category);
            ps.setDouble(4, price);
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

    synchronized boolean delete(int id) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM producto WHERE id=?");
            ps.setInt(1, id);
            int executeUpdate = ps.executeUpdate();
            if(executeUpdate == 1) {
                delete_ = true;     // http://lineadecodigo.com/java/insertar-datos-con-jdbc/
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
        return delete_;
    }

    synchronized boolean update(int id, String name, String category, double price) {
        boolean update_ = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE FROM producto SET name=?, category=?, price=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, id);
            int executeUpdate = ps.executeUpdate();
            if(executeUpdate == 1) {
                update_ = true;     // http://lineadecodigo.com/java/insertar-datos-con-jdbc/
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
        return update_;
    }

    synchronized Producto get(int id) {
        Producto p = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT FROM producto WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String name = rs.getString("name");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precio");
                p = new Producto(id, name, categoria, precio);
            }
            rs.close();
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
        return p;
    }
    
}
