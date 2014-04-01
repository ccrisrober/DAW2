package producto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class Producto {
    private int id;
    private String name;
    private String image;
    private String categoria;
    private double precio;

    public Producto() {
        
    }
    
    public Producto(int id, String name, String image, String categoria, double precio) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", name=" + name + ", image=" + image + ", categoria=" + categoria + ", precio=" + precio + '}';
    }

}
