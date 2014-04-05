/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import producto.Producto;
import producto.ProductoDAO;

/**
 *
 * @author Cristian
 */
public class PedidoDAO {
    private Connection conn;
    private PedidoProductoDAO pedprod;
    private ProductoDAO prod;
    
    public PedidoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
            pedprod = new PedidoProductoDAO(ds);
            prod = new ProductoDAO(ds);
        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos " + e);
        }
    }
    
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                pedprod.close();
                prod.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    
    synchronized public Pedido get(int id_pedido, int id_usu) {
        java.util.Date date= new java.util.Date();
        Timestamp time = new Timestamp(date.getTime());
        Pedido pedido = new Pedido(id_pedido, id_usu, time);
        
        
        List<Producto> productos = pedprod.getProductoPedido(id_pedido);
        
        
        
        return pedido;
    }

    public boolean create(Map<Integer, Integer> productos, int id_usu) {
        boolean ok = false;
        // Extraigo fecha actual
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO pedido(id_usu, date) VALUES (?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id_usu);
            ps.setDate(2, sqlDate);
            int execute = ps.executeUpdate();
            int num_ped = 0;
            if(execute == 1) {
                rs = ps.getGeneratedKeys();  
                while(rs.next()) {
                    num_ped = rs.getInt(1);
                }
            }
                  
            ok = pedprod.insert(num_ped, productos);
            
            if(!ok) {
                delete(num_ped, id_usu);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        return ok;

    }

    public List<Pedido> getAll(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    synchronized public int lastID() {
        Statement stm = null;
        ResultSet rs = null;
        int id = -1;
        try {
            stm = this.conn.createStatement();
            rs = stm.executeQuery("values IDENTITY_VAL_LOCAL()");
            while (rs.next()) {
                System.out.println(rs.getRow());
                id = rs.getInt(1);
            }
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
        return id;
    }

    private boolean delete(int num_ped, int id_usu) {
        System.err.println("Borrando");
        return false;
    }
    
}