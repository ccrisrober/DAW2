/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pedido;

import java.sql.Timestamp;

/**
 *
 * @author Cristian
 */
public class Pedido {
    protected int id_pedido;
    protected int id_usu;
    protected Timestamp date;

    public Pedido(int id_pedido, int id_usu, Timestamp date) {
        this.id_pedido = id_pedido;
        this.id_usu = id_usu;
        this.date = date;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}
