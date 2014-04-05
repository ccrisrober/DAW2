package producto;

public class Producto {
    private int id_prod;
    private String name;
    private String image;
    private String categoria;
    private double precio;

    public Producto() {
        
    }
    
    public Producto(int id_prod, String name, String image, String categoria, double precio) {
        this.id_prod = id_prod;
        this.name = name;
        this.image = image;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getId() {
        return id_prod;
    }

    public void setId(int id) {
        this.id_prod = id;
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
        return "Producto{" + "id_prod=" + id_prod + ", name=" + name + ", image=" + image + ", categoria=" + categoria + ", precio=" + precio + '}';
    }

}
