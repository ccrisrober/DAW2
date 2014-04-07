
import java.io.File;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class NewClass {
    public static void main(String[] argv) throws IOException {
        System.out.println(new File (".").getCanonicalPath() + File.separator + "assets" + File.separator + "img" + File.separator + "products");
    }
}
