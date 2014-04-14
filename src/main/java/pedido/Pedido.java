package pedido;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Cristian
 */
public class Pedido {
    protected int id_pedido;
    protected int id_usu;
    protected Date date;
    protected double price;
    protected boolean procesado;
    protected List<PedidoProducto> lpp;

    public Pedido(int id_pedido, int id_usu, Date date, double price, boolean procesado) {
        this.id_pedido = id_pedido;
        this.id_usu = id_usu;
        this.date = date;
        this.price = price;
        this.procesado = procesado;
    }

    public Pedido(int id_pedido, int id_usu, double price, Date date) {
        this(id_pedido, id_usu, date, price, false);
    }

    public Pedido(int id_pedido, int id_usu, Date date, double price, boolean procesado, List<PedidoProducto> lpp) {
        this.id_pedido = id_pedido;
        this.id_usu = id_usu;
        this.date = date;
        this.price = price;
        this.procesado = procesado;
        this.lpp = lpp;
    }

    public boolean isProcesado() {
        return procesado;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<PedidoProducto> getPedidoProducto() {
        return lpp;
    }

    public void setPedidoProducto(List<PedidoProducto> lpp) {
        this.lpp = lpp;
    }
    
}
