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
    
    /*protected class ProductoCantidad {
        protected int id_pedido;
        protected int quantity;

        public ProductoCantidad(int id_pedido, int quantity) {
            this.id_pedido = id_pedido;
            this.quantity = quantity;
        }

        public int getId_pedido() {
            return id_pedido;
        }

        public void setId_pedido(int id_pedido) {
            this.id_pedido = id_pedido;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
    }*/
    
    protected Map<Integer, Integer> productos;
    protected int id_user;
    
    public Carrito(int id_user) {
        productos = new HashMap<Integer, Integer>();
        this.id_user = id_user;
    }
    
    synchronized public void annadirProducto(int[] id_producto, int[] quantity) {
        for(int i = 0; i < id_producto.length; i++) {
            annadirProducto(id_producto[i], quantity[i]);
        }
    }
    
    synchronized public void annadirProducto(int id_producto, int quantity) {
        int quant = 0;
        if(productos.containsKey(id_producto)) {
            quant = productos.get(id_producto);
        }
        quant += quantity;
        productos.put(id_producto, quantity);
    }
    
    public Map<Integer, Integer> getProductos(){
        return productos;
    }
    
    //synchronized public boolean removeProducto()
}
