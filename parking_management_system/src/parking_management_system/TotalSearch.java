/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking_management_system;
import java.util.*;
import javax.swing.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import parking_management_system.connect_DB.*;
/**
 *
 * @author arnav mehta
 */
public class TotalSearch extends JPanel{
    private JTable tb ;
    private connect_DB db = new connect_DB();
    private JTextField cn;
    private JButton allcurrent;
    private JButton search;
    
    TotalSearch(){
        create();
        setLayout(null);
        setBackground(Color.pink);
    }
    private void create(){
        
        tb = new JTable();
        
        JLabel see = new JLabel("Show total database: "); 
        this.add(see);
        see.setBounds(10, 10, 140, 30);
        allcurrent = new JButton("SHOW");
        allcurrent.addActionListener(e->currAll());
        allcurrent.setBounds(140, 10, 80, 30);
        this.add(allcurrent);
        
        JLabel se = new JLabel("Enter car Number: "); 
        this.add(se);
        se.setBounds(10, 50, 150, 30);
        cn = new JTextField(20);
        cn.setBounds(130, 50, 100, 30);
        this.add(cn);
        search = new JButton("Search"); 
        search.setBounds(250, 50, 100, 30);
        this.add(search);
        
        search.addActionListener(e-> Scar());
    }
    
    private void Scar(){
        String cb = cn.getText();
        if(cb.length()==0){
            JOptionPane.showMessageDialog(null, "Car number not entered", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{
        try{
            PreparedStatement q = db.con.prepareStatement("select * from total_data where car_number = ?");
            q.setString(1, cb);
            ResultSet r = q.executeQuery();
            if(!r.next()){
                    JOptionPane.showMessageDialog(null, "No car found", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
            ResultSetMetaData d = r.getMetaData();
            int c = d.getColumnCount();
            DefaultTableModel tm = new DefaultTableModel();

            String[] col_name = new String[c];

            for (int i = 1; i <= c; i++) {
                col_name[i - 1] = d.getColumnLabel(i);
            }
            tm.setColumnIdentifiers(col_name);


            do{
                Object[] row = new Object[c];
                for(int i=1;i<=c;i++){
                    row[i-1] = r.getObject(i);
                }
                tm.addRow(row);
            }while(r.next());
            
            JLabel cl = new JLabel("currently parked car");
            this.add(cl);
            cl.setBounds(70,100,200,20);
            
            tb.setModel(tm);
            JScrollPane tbp =new JScrollPane(tb);
            tbp.setBounds(30,130,400, 100);
            this.add(tbp);
            r.close();
            q.close();
            }
        }
        catch(Exception td){
            System.out.println(td);
        }
        }
    }
    private void currAll(){
            try{
                Statement st = db.con.createStatement();
                ResultSet r = st.executeQuery("select * from total_data");
                if(!r.next()) JOptionPane.showMessageDialog(null, "No Data", "ERROR",  JOptionPane.ERROR_MESSAGE);
                ResultSetMetaData d = r.getMetaData();
                int c = d.getColumnCount();
                DefaultTableModel tm = new DefaultTableModel();
                String[] col_name = new String[c];


                for (int i = 1; i <= c; i++) {
                    col_name[i - 1] = d.getColumnLabel(i);
                }
                tm.setColumnIdentifiers(col_name);


                do{
                    Object[] row = new Object[c];
                    for(int i=1;i<=c;i++){
                        row[i-1] = r.getObject(i);
                    }
                    tm.addRow(row);
                }while(r.next());
                
                JLabel cl = new JLabel("Total Database");
                this.add(cl);
                cl.setBounds(500,3,200,15);
                tb.setModel(tm);
                JScrollPane tbp =new JScrollPane(tb);
                tbp.setBounds(450,25,550, 350);
                this.add(tbp);
                r.close();
                st.close();
            }
            catch(Exception e){
                System.out.print(e);
            }
    }
    
    
    
    
    public static void main(String[] args){
        new TotalSearch();
    }
}
