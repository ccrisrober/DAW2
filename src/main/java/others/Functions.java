package others;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class Functions {
    private static final Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
    private static final String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private static Collection<String> categories;
    
    public static boolean existCategory(String category) {
        if(categories == null) {
            categories = new HashSet<String>();
            categories.add("Alimentación");
            categories.add("Droguería");
            categories.add("Prensa");
            categories.add("Ferretería");
        }
        return categories.contains(category);
    }
    
    public static String randomString(String original) {
        String exit = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
            exit = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exit;
    }

    public static NumChar generateNumChar(String randomChar) {
        
        return new NumChar(randomChar);
    }

    public static boolean isID(String id) {
        boolean exit = false;
        if(id != null) {
            try {
                int value = Integer.parseInt(id);
                exit = value >= 0;
            } catch (NumberFormatException cce)  {
                exit = false;
            }
        }
        return exit;
    }
    
    public static String updateSecurity(HttpSession session) {
        String user = (String) session.getAttribute("user_active");
        String random = Functions.randomString(user + new Date());
        session.setAttribute("random_active", random);    // Código seguridad de usuario
        return random;
    }

    public static boolean isPrice(String price) {
        boolean isCorrect = false;
        if(price != null && !price.isEmpty()) {
            try {
                Double.parseDouble(price);
                isCorrect = true;
            } catch(ClassCastException cce) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }
 
    public static boolean setImagenProducto(InputStream input, String fileName)
            throws ServletException {
        FileOutputStream output = null;
        boolean ok = false;
        try {
            output = new FileOutputStream(fileName);
            int leido = 0;
            leido = input.read();
            while (leido != -1) {
                output.write(leido);
                leido = input.read();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            try {
                output.flush();
                output.close();
                input.close();
                ok = true;
            } catch (IOException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, "Error cerrando flujo de salida", ex);
            }
        }
        return ok;
    }
    
    public static java.sql.Date extraerFechaActual() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
}
