package parking_management_system;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import parking_management_system.CurrentSearch;
import parking_management_system.LoginPage.*;
import parking_management_system.Parking;
import parking_management_system.TotalSearch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arnav mehta
 */
public class start extends JFrame {
    
    start(){
        ImageIcon icon = new ImageIcon("C:\\Users\\arnav mehta\\Desktop\\JProject\\parking_management_system\\src\\parking_management_system\\green.jpeg"); 

        setIconImage(icon.getImage());


        JTabbedPane tp = new JTabbedPane();
        Parking p = new Parking();
        CurrentSearch cs = new CurrentSearch();
        TotalSearch ts = new TotalSearch();


        tp.add(p, "Car Parking");
        tp.add(cs, "Current parked cars");
        tp.add(ts, "Total database");



        setSize(1050, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        add(tp);
        System.out.println("out");
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new start();

            
    }
}
