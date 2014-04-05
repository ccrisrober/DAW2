/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
class PedidoProductoDAO {
    private Connection conn;
    private DataSource ds;
    
    public PedidoProductoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
            this.ds = ds;
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

    List<Producto> getProductoPedido(int id_pedido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean insert(int num_pedido, int id_user, Map<Integer, Integer> productos) {
        boolean finish = false;
        String sql = "INSERT INTO Pedido_Producto (id_prod, id_pedido, id_user, quantity) VALUES ";
        String aux = sql;
        int size = productos.size();
        for (int i = 0; i < size; i++) {
            sql += "(?, ?, ?, ?),";
        }
        if (sql.lastIndexOf(",") == sql.length() - 1) {    // Para eliminar última ","
            sql = sql.substring(0, sql.length() -1);
        }
        System.err.println(sql);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int i = 1;
            Set<Entry<Integer, Integer>> entrySet = productos.entrySet();
            for (Entry<Integer, Integer> e : entrySet) {
                ps.setInt(i++, e.getKey());
                ps.setInt(i++, num_pedido);
                ps.setInt(i++, id_user);
                ps.setInt(i++, e.getValue());
                aux += "(" + e.getKey() + ", " + num_pedido + ", " + id_user + ", " + e.getValue() + "),";
            }
            
            
            if (aux.lastIndexOf(",") == aux.length() - 1) {    // Para eliminar última ","
                aux = aux.substring(0, aux.length() -1);
            }
            
            System.err.println(aux);
            int executeInsert = ps.executeUpdate();
            System.out.println("Insertado: " + executeInsert);
            System.err.println(executeInsert > 0);
            if (executeInsert > 0) {
                finish = true;
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
        return finish;
    }

    List<PedidoProducto> getProductos(int id_pedido) {
        List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM Pedido_Producto WHERE id_pedido=?";
            stm = this.conn.prepareStatement(query);
            stm.setInt(1, id_pedido);

            rs = stm.executeQuery();
            lpp = createPedidoProductoFromRS(rs);
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
        return lpp;
    }
    
    private List<PedidoProducto> createPedidoProductoFromRS(ResultSet rs) throws SQLException {
        List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();
        while(rs.next()) {
            int id_prod = rs.getInt("id_prod");
            int id_pedido = rs.getInt("id_pedido");
            int id_user = rs.getInt("id_user");
            int quantity = rs.getInt("quatity");
            ProductoDAO dao = new ProductoDAO(ds);
            Producto producto = dao.get(id_prod);
            lpp.add(new PedidoProducto(id_prod, id_pedido, id_user, quantity, producto));
        }
        return lpp;
    }

}
