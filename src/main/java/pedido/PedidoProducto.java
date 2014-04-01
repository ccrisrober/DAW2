/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import producto.Producto;

/**
 *
 * @author Cristian
 */
public class PedidoProducto {
    protected int id_pedido;
    protected int id_producto;
    protected int quantity;
    protected Producto prod;
    protected Pedido ped;

    public PedidoProducto(int id_pedido, int id_producto, int quantity) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.quantity = quantity;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }

    /*public Pedido getPed() {
        return ped;
    }

    public void setPed(Pedido ped) {
        this.ped = ped;
    }*/

    
}
