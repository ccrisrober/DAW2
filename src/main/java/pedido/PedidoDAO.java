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
import java.util.LinkedList;
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
    private DataSource ds;
    private Connection conn;
    
    public PedidoDAO(DataSource ds) {
        try {
            this.ds = ds;
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
    
    
    synchronized public Pedido get(int id_pedido) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pedido pedido = null;
        try {
            String query = "SELECTED * FROM Pedido WHERE id_pedido = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();
            
            int id_user = 0;
            Date date = null;
            double price = 0;
            boolean procesado = false;
            
            while(rs.next()) {
                id_user = rs.getInt("id_user");
                date = rs.getDate("date");
                price = rs.getDouble("price");
                procesado = rs.getBoolean("procesado");
            }
            PedidoProductoDAO pedprod = new PedidoProductoDAO(ds);
            List<PedidoProducto> productos = pedprod.getProductos(id_pedido);
            pedprod.close();
            
            pedido = new Pedido(id_pedido, id_user, date, price, procesado, productos);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (rs != null) {
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
        }
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
            String sql = "INSERT INTO pedido(id_user, date) VALUES (?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //ps.setInt(1, num_ped);
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
            PedidoProductoDAO pedprod = new PedidoProductoDAO(ds);
            ok = pedprod.insert(num_ped, id_usu, productos);
            pedprod.close();
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

    public List<Pedido> getAll(int id_user) {
        List<Pedido> lp = new LinkedList<Pedido>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM pedido WHERE id_user=?";
            stm = this.conn.prepareStatement(query);
            stm.setInt(1, id_user);

            rs = stm.executeQuery();
            lp = createPedidosFromRS(rs);
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

        return lp;
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

    private List<Pedido> createPedidosFromRS(ResultSet rs) throws SQLException {
        List<Pedido> lp = new LinkedList<Pedido>();
        while(rs.next()) {
            int id_pedido = rs.getInt("id_pedido");
            int id_usu = rs.getInt("id_user");
            Date date = rs.getDate("date");
            double price = rs.getDouble("price");
            boolean procesado = rs.getBoolean("procesado");
            lp.add(new Pedido(id_pedido, id_usu, date, price, procesado));
        }
        return lp;
    }
    
}