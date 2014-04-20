package pedido;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import others.AbstractDAO;

public class PedidoDAO extends AbstractDAO {

    public PedidoDAO(DataSource ds) {
        super(ds);
    }

    synchronized public Pedido get(int id_pedido) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pedido pedido = null;
        PedidoProductoDAO pedprod = null;
        try {
            pedprod = new PedidoProductoDAO(ds);
            List<PedidoProducto> productos = pedprod.getProductos(id_pedido);

            String query = "SELECT * FROM Pedido WHERE id_pedido = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_pedido);
            rs = ps.executeQuery();

            while (rs.next()) {
                pedido = createPedidoFromRs(rs);
                pedido.setPedidoProducto(productos);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            pedprod.close();
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return pedido;
    }

    synchronized public boolean create(Map<Integer, Integer> productos, int id_usu) {
        boolean ok = false;
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        PreparedStatement ps = null;
        ResultSet rs = null;
        PedidoProductoDAO pedprod;
        try {
            String sql = "INSERT INTO Pedido(id_user, date) VALUES (?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);   // Extraemos el último ID

            ps.setInt(1, id_usu);
            ps.setDate(2, date);

            int execute = ps.executeUpdate();
            int num_ped = 0;
            if (execute == 1) {
                rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    num_ped = rs.getInt(1);
                }
            }
            pedprod = new PedidoProductoDAO(ds);
            ok = pedprod.insert(num_ped, id_usu, productos);
            if (ok) {
                double price = pedprod.getPrice(num_ped);
                update(num_ped, date, price); 
            } else {
                delete(num_ped, id_usu);
            }
            pedprod.close();
        } catch (SQLException e) {
            try {
                throw new SQLException(e);
                /*System.out.println(e.getMessage());
                this.close();*/
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.closeResultSet(rs);
            this.closePreparedStatement(ps);
        }
        return ok;
    }

    synchronized public List<Pedido> getAll(int id_user) {
        List<Pedido> lp = new LinkedList<Pedido>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM pedido WHERE id_user=?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_user);
            rs = ps.executeQuery();
            lp = createPedidosFromRS(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return lp;
    }

    synchronized private boolean delete(int num_ped, int id_usu) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM Pedido WHERE id_pedido = ? AND id_user = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, num_ped);
            ps.setInt(2, id_usu);
            int delete = ps.executeUpdate();
            if (delete == 1) {
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

    synchronized private Pedido createPedidoFromRs(ResultSet rs) throws SQLException {
        int id_pedido = rs.getInt("id_pedido");
        int id_usu = rs.getInt("id_user");
        Date date = rs.getDate("date");
        double price = Math.rint(rs.getDouble("price")*100)/100;
        boolean procesado = rs.getBoolean("procesado");
        return new Pedido(id_pedido, id_usu, date, price, procesado);
    }
    
    synchronized private List<Pedido> createPedidosFromRS(ResultSet rs) throws SQLException {
        List<Pedido> lp = new LinkedList<Pedido>();
        while (rs.next()) {
            lp.add(createPedidoFromRs(rs));
        }
        return lp;
    }
    
    synchronized public List<Pedido> getAll() {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Pedido";
            rs = stm.executeQuery(sql);
            pedidos = createPedidosFromRS(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closeResultSet(rs);
            this.closeStatament(stm);
        }
        return pedidos;
    }

    synchronized public boolean haveAccess(int id_ped, int id_user) {
        boolean access = false;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT date FROM Pedido WHERE id_pedido = ? AND id_user = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_ped);
            ps.setInt(2, id_user);
            rs = ps.executeQuery();
            if (rs.next()) {
                access = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closeResultSet(rs);
            this.closePreparedStatement(ps);
        }
        return access;
    }

    synchronized public boolean update(int num_ped, Date date, double price) {
        boolean update = false;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE Pedido SET date = ?, price = ? WHERE id_pedido = ?";
            ps = this.conn.prepareStatement(query);
            ps.setDate(1, date);
            ps.setDouble(2, price);
            ps.setInt(3, num_ped);
            int upd = ps.executeUpdate();
            if (upd > 0) {
                update = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return update;
    }

    synchronized public boolean deleteUser(int id_user) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        PedidoProductoDAO pedprod = null;
        try {
            pedprod = new PedidoProductoDAO(ds);
            pedprod.deletePedidoUser(id_user);
            ps = conn.prepareStatement("DELETE FROM Pedido WHERE id_user = ?");
            ps.setInt(1, id_user);
            //ps.setInt(2, p.getId_pedido());
            if(ps.executeUpdate() > 0) {
                delete_ = true;
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
            pedprod.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return delete_;
    }

    synchronized public boolean deletePedido(int id_user, int id_pedido) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM Pedido WHERE id_pedido = ? AND id_user = ?");
            ps.setInt(1, id_pedido);
            ps.setInt(2, id_user);
            if(ps.executeUpdate() > 0) {
                PedidoProductoDAO pedprod = new PedidoProductoDAO(ds);
                if(pedprod.deletePedido(id_pedido)) {
                    delete_ = true;
                }
                pedprod.close();
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return delete_;
    }
    
    synchronized public boolean validate(int id_ped) {
        boolean update = false;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE Pedido SET procesado = true WHERE id_pedido = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, id_ped);
            int upd = ps.executeUpdate();
            if (upd > 0) {
                update = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return update;
    }

    synchronized public boolean validateAll() {
        boolean update = false;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE Pedido SET procesado = true";
            ps = this.conn.prepareStatement(query);
            int upd = ps.executeUpdate();
            if (upd > 0) {
                update = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return update;
    }
}
