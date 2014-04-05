/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import producto.Producto;
import user.User;

/**
 *
 * @author Cristian
 */
public class PedidoProducto {
    protected int id_pedido;
    protected int id_producto;
    protected int id_user;
    protected int quantity;
    protected User us;
    protected Producto prod;
    protected Pedido ped;

    public PedidoProducto(Producto prod, int quantity) {
        this.prod = prod;
        this.quantity = quantity;
    }
    
    public PedidoProducto(int id_pedido, int id_producto, int quantity) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.quantity = quantity;
    }

    public PedidoProducto(int id_pedido, int id_producto, int id_user, int quantity, Producto prod) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.id_user = id_user;
        this.quantity = quantity;
        this.prod = prod;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public Pedido getPed() {
        return ped;
    }

    public void setPed(Pedido ped) {
        this.ped = ped;
    }

    
}
