package pedido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.sql.DataSource;
import others.AbstractDAO;
import producto.Producto;
import producto.ProductoDAO;

class PedidoProductoDAO extends AbstractDAO {

    public PedidoProductoDAO(DataSource ds) {
        super(ds);
    }
    
    List<Producto> getProductoPedido(int id_pedido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    double getPrice(int num_pedido) {
        double total = 0;
        List<PedidoProducto> productos = getProductos(num_pedido);
        Producto p;
        for(PedidoProducto pp: productos) {
            total += pp.getProd().getPrecio() * pp.getQuantity();
        }
        return total;
    }

    boolean insert(int num_pedido, int id_user, Map<Integer, Integer> productos) {
        boolean finish = false;
        String sql = "INSERT INTO Pedido_Producto (id_prod, id_pedido, id_user, quantity) VALUES ";
        int size = productos.size();
        for (int i = 0; i < size; i++) {
            sql += "(?, ?, ?, ?),";
        }
        if (sql.lastIndexOf(",") == sql.length() - 1) {    // Para eliminar última ","
            sql = sql.substring(0, sql.length() -1);
        }
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
            }
            int executeInsert = ps.executeUpdate();
            if (executeInsert > 0) {
                finish = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return finish;
    }

    List<PedidoProducto> getProductos(int id_pedido) {
        List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM Pedido_Producto WHERE id_pedido=?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();
            lpp = createPedidoProductoFromRS(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return lpp;
    }
    
    private List<PedidoProducto> createPedidoProductoFromRS(ResultSet rs) throws SQLException {
        List<PedidoProducto> lpp = new LinkedList<PedidoProducto>();
        ProductoDAO prodDAO = new ProductoDAO(ds);
        while(rs.next()) {
            int id_prod = rs.getInt("id_prod");
            int id_pedido = rs.getInt("id_pedido");
            int id_user = rs.getInt("id_user");
            int quantity = rs.getInt("quantity");
            lpp.add(new PedidoProducto(id_pedido, id_prod, id_user, quantity, prodDAO.get(id_prod)));
        }
        prodDAO.close();
        return lpp;
    }
    
    synchronized public boolean deletePedidoUser(int id_user) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM PedidoProducto WHERE id_user = ?"; 
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_user);
            if(ps.executeUpdate() > 0) {
                delete_ = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return delete_;
    }
    
    synchronized public boolean deletePedido(int id_pedido) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM PedidoProducto WHERE id_pedido = ?"; 
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_pedido);
            if(ps.executeUpdate() > 0) {
                delete_ = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return delete_;
    }

}
