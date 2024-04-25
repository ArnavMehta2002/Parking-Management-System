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
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import parking_management_system.Parking.*;


public class LoginPage extends JFrame{
   
   private JLabel l1 = new JLabel("User Name: ");;
   private JLabel l2 = new JLabel("Password: ");;
   protected JTextField user = new JTextField(10);;
   protected JPasswordField password = new JPasswordField(10);;
   private JButton login;
   protected String admin = "admin";
   protected String admin_password = "1234";
   private JPanel p1 = new JPanel();
   private JPanel p2 = new JPanel();
   
   LoginPage(){
       create();
       setLayout(null);
       setLocation(450, 200);
       setSize(300, 300);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);
    }
    private void create(){
       p1.add(l1); p1.add(user);
       p1.setBounds(30,30,200,50);
       
       p2.add(l2);p2.add(password);   
       p2.setBounds(30,80,200,50);
       
       login = new JButton("login");
       login.addActionListener(e->loginuser());
       login.setBounds(100,150,100,50);
       
       add(p2);
       add(p1);
       
       add(login);
       
    }
    
    private void loginuser(){
        if((user.getText().equals(admin)) && (new String(password.getPassword()).equals(admin_password))){
            start s = new start();
            s.setVisible(true);
            this.setVisible(false);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Wrong UserName or Password", "ERROR",  JOptionPane.ERROR_MESSAGE);    
            user.setText("");
            password.setText("");
        }
    }
    
   public static void main(String[] arnav){
       new LoginPage();
   }
}
