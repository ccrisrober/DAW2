package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import static org.omg.IOP.CodecPackage.InvalidTypeForEncodingHelper.id;
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
    
    synchronized public boolean exist (String user) {
        boolean exist_ = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT username FROM Usuario WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if(rs != null) {
                System.out.println(rs.getString(1));
                exist_ = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return exist_;
    }
    
    synchronized public boolean register(String user, String pass) {
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
    
    synchronized private User createUsuarioFromRS(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_user");
        String name = rs.getString("username");
        String password = rs.getString("password");
        boolean isadmin = rs.getBoolean("isadmin");
        return new User(id, name, password, isadmin);
    }

    synchronized public User getUser(int id_user) {
        User usuario = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM Usuario WHERE id_user=?"; 
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id_user);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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

    synchronized public boolean editPassword(int id_user, String password) {
        boolean editado = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE Usuario SET password=? WHERE id_user=?");
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

    synchronized public boolean isAdmin(int id) {
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

    synchronized public List<User> getAll() {
        List<User> users = new LinkedList<User>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Usuario WHERE NOT isAdmin");
            rs = ps.executeQuery();
            while(rs.next()) {
                users.add(createUsuarioFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.close();
        } finally {
            this.closePreparedStatement(ps);
            this.closeResultSet(rs);
        }
        return users;
    }

    //http://apache-database.10148.n7.nabble.com/Delete-with-cascade-td107443.html
    synchronized public boolean delete(int id_user) {
        boolean delete_ = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM Usuario WHERE id_user = ?");
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
}