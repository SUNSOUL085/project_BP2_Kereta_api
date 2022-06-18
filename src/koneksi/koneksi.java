/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    public Connection con;
    
    public koneksi(){
        String id, pass, driver, url;
        id="root";//user DBMS MySQL
        pass="";//Password DBMS MySQL
        driver="com.mysql.jdbc.Driver";//driver mysql
        url="jdbc:mysql://localhost:3306/kereta_api2";//nama database : db_kasir
        try{
            Class.forName(driver).newInstance();
            con=DriverManager.getConnection(url,id,pass);
            System.out.println("oke");
        }
        catch(Exception e){
            System.out.println(""+e.getLocalizedMessage());
        }
    }
   
}
