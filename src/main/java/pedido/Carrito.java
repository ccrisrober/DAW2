package pedido;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pedido.Pedido;
import producto.Producto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class Carrito {
    
    protected Map<Integer, Integer> productos;
    protected int id_user;
    
    public Carrito(int id_user) {
        productos = new HashMap<Integer, Integer>();
        this.id_user = id_user;
    }
    
    public int getID_user() {
        return id_user;
    }
    
    synchronized public void annadirProducto(int[] id_producto, int[] quantity) {
        for(int i = 0; i < id_producto.length; i++) {
            annadirProducto(id_producto[i], quantity[i]);
        }
    }
    
    synchronized public void annadirProducto(int id_producto, int quantity) {
        if(productos.containsKey(id_producto)) {
            quantity += productos.get(id_producto);
        }
        productos.put(id_producto, quantity);
    }
    
    public Map<Integer, Integer> getProductos(){
        return productos;
    }
    
    //synchronized public boolean removeProducto()
}
