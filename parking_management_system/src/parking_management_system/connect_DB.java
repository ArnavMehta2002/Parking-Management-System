/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking_management_system;

/**
 *
 * @author arnav mehta
 */
import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connect_DB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/parking";
    static final String USER = "root";
    static final String PASS = "arnavmehta2002";
    Connection con;
    public connect_DB(){
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            System.out.println("Connected to MySQL database!");
        }
        catch (Exception e) {
            System.out.println("Error connecting to MySQL database: " + e.getMessage());
        }
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {new connect_DB();} );
    }
}
