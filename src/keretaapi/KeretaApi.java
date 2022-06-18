/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keretaapi;

//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author koyan39
 */
public class KeretaApi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      Date dNow = new Date();
      SimpleDateFormat ft = new SimpleDateFormat (" hh:mm:ss");

      System.out.println("Current Date: " + ft.format(dNow));

    }
    
}
