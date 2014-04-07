package producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import others.AbstractDAO;

public class ProductoDAO extends AbstractDAO {

    public ProductoDAO(DataSource ds) {
        super(ds);
    }
    
    synchronized public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<Producto>();
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Producto";
            rs = stm.executeQuery(sql);
            productos = createProductosFromRS(rs);            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closeResultSet(rs);
            this.closeStatament(stm);
        }
        return productos;
    }
    
    private Producto createProductoFromRS(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_prod");
        String name = rs.getString("name");
        String image = rs.getString("image");
        String categoria = rs.getString("category");
        double precio = rs.getDouble("price");
        return new Producto(id, name, image, categoria, precio);
    }
    
    private List<Producto> createProductosFromRS(ResultSet rs) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        while(rs.next()) {
            productos.add(createProductoFromRS(rs));
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
                insert_ = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
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
                update_ = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return update_;
    }

    public Producto get(int id) {
        Producto p = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM producto WHERE id_prod=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                p = this.createProductoFromRS(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return p;
    }

    List<Producto> getLast(int max) {
        List<Producto> productos = new ArrayList<Producto>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Producto ORDER BY id_prod DESC FETCH FIRST ? ROWS ONLY"; 
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, max);
            rs = ps.executeQuery(sql);
            productos = createProductosFromRS(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return productos;
    }
    
}
