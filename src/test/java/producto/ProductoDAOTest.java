/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package producto;

import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Cristian
 */
public class ProductoDAOTest {
    
    @Resource(name = "jdbc/tienda_crodriguezbe")
    private DataSource ds;
    
    public ProductoDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class ProductoDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        ProductoDAO instance = new ProductoDAO(ds);
        List<Producto> result = instance.getAll();
        if(result != null) {
            assertEquals(1, 1);
        } else {
            assertEquals(1, 2);
        }
    }

    /**
     * Test of insert method, of class ProductoDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        ProductoDAO instance = new ProductoDAO(ds);
        int size = instance.getAll().size()+1;
        boolean expResult = true;
        instance.insert("Colonia Hello Kitty", "Droguería", "http://rualvadecor.es/5264-6887-large/colonia-hello-kitty.jpg", 12.50);
        assertEquals(instance.getAll().size(), size);
    }

    /**
     * Test of get method, of class ProductoDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 0;
        ProductoDAO instance = new ProductoDAO(ds);
        Producto expResult = null;
        Producto result = instance.get(-8);
        assertEquals(expResult, result);
    }
    
}
