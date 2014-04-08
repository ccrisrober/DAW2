package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import others.AbstractDAO;

public class UserDAO extends AbstractDAO {

    public UserDAO(DataSource ds) {
        super(ds);
    }
    
    synchronized public int validate(String user, String pass) {
        int id = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Usuario WHERE username=? and password = ?");
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if(rs.next()) {
                User u = this.createUsuarioFromRS(rs);
                id = u.getId();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return id;
    }
    
    public boolean register(String user, String pass) {
        boolean insertado = false;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO Usuario (username, password) VALUES(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            int executeUpdate = ps.executeUpdate();
            if(executeUpdate == 1) {
                insertado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }
        return insertado;
    }
    
    private User createUsuarioFromRS(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_user");
        String name = rs.getString("username");;
        String password = rs.getString("password");
        boolean isadmin = rs.getBoolean("isadmin");
        return new User(id, name, password, isadmin);
    }

    public User getUser(int id_user) {
        User usuario = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM Usuario WHERE id_user=?"; 
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_user);
            rs = ps.executeQuery(sql);
            usuario = createUsuarioFromRS(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return usuario;
    }

    public boolean editPassword(int id_user, String password) {
        boolean editado = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE FROM Usuario SET password=? WHERE id_user=?");
            ps.setString(1, password);
            ps.setInt(2, id_user);
            int executeUpdate = ps.executeUpdate();
            if(executeUpdate > 0) {
                editado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
        }        
        return editado;
    }

    public boolean isAdmin(int id) {
        boolean encontrado = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Usuario WHERE id_user=? and isAdmin");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                encontrado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return encontrado;
    }
}