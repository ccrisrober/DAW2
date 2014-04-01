/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import producto.Producto;

/**
 *
 * @author Cristian
 */
public class PedidoDAO {
    private Connection conn;
    private PedidoProductoDAO pedprod;
    
    public PedidoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();
            pedprod = new PedidoProductoDAO(ds);
        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos " + e);
        }
    }
    
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                pedprod.close();
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
}
