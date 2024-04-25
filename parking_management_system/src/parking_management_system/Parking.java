
package parking_management_system;

/**
 *
 * @author arnav mehta
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import parking_management_system.connect_DB.*;

public class Parking extends JPanel{
    public int empty=100;
    public int filled  = 0;
    private connect_DB cb = new connect_DB();
    private static Hashtable<Integer, JButton> slots = new Hashtable<>() ;
    private JLabel l1 = new JLabel("Filled Slots: "+filled);
    private JLabel l2 = new JLabel("Empty slots: "+empty);
    private JButton exit ;
    
    Parking(){
        create();
        setLayout(null);
        setBackground(Color.pink);
    }
    
    
    
    private void create(){
        this.add(l1);
        this.add(l2);
        l1.setBounds(400,5, 100, 13);
        l2.setBounds(500,5,100, 13);
        GridLayout g = new GridLayout(2, 16);
        JPanel p1 = new JPanel(g);  p1.setBounds(20, 30, 980, 50);
        JPanel p2 = new JPanel(g);  p2.setBounds(20, 100, 980, 50);
        JPanel p3 = new JPanel(g);  p3.setBounds(20, 170, 980, 50);
        JPanel p4 = new JPanel(g);  p4.setBounds(20, 240, 980, 50);
        JPanel p5 = new JPanel(g);  p5.setBounds(20, 310, 980, 50);
        int i;
        int space = empty;
        
        for (i = 1; i <= space; i++) {
            final int slot_num = i;
            JButton button = new JButton(slot_num+": EMPTY");
            button.setBackground(Color.GREEN);
            slots.put(i, button);
            
            button.addActionListener(e-> createslots(slot_num));
            if(i<=20) p1.add(button);
            else if(i>20 && i<=40) p2.add(button);
            else if(i>40 && i<=60) p3.add(button);
            else if(i>60 && i<=80) p4.add(button);
            else p5.add(button);
        }
        
        check();
        
        JPanel ea = new JPanel();
        exit = new JButton("EXIT");
        exit.addActionListener(e -> ExitCar());
        exit.setPreferredSize(new Dimension(100, 50)); 
        ea.add(exit);
        ea.setBackground(Color.pink);
        ea.setBounds(450, 380, 100,50);
        
        this.add(p1); this.add(p2); this.add(p3); this.add(p4); this.add(p5);
        this.add(ea); 
    }
    
    
    private void check(){
        try{
            PreparedStatement bt = cb.con.prepareStatement("select slot from current_slots");
            ResultSet r = bt.executeQuery();
            
            while(r.next()){
                int q = r.getInt("slot");
                slots.get(q).setBackground(Color.RED);
                slots.get(q).setText(q+": FILLED");
                empty--;
                filled++;
            }
            l1.setText("Filled Slots: "+filled);
            l2.setText("Empty slots: "+empty);
        }
        catch(Exception e){
            System.out.print(e);
        }
    }
    
    private void createslots(int i){
        try{
            PreparedStatement p6 = cb.con.prepareStatement("select * from current_slots where slot =?");
            p6.setInt(1, i);
            ResultSet r = p6.executeQuery();
        
            if (r.next()) {
                CarDetails(i, r);
            } 
            else {
                int option = JOptionPane.showConfirmDialog(null, "Confirm Slot Booking", "Slot Booking ", JOptionPane.YES_NO_OPTION);
                if(option==JOptionPane.YES_OPTION){
                    JPanel customPanel = new JPanel();
                    customPanel.add(new JLabel("Enter Car Number: "));

                    JTextField c_num= new JTextField(10);
                    customPanel.add(c_num);

                    customPanel.add(new JLabel("Enter Name: "));
                    JTextField name = new JTextField(10);
                    customPanel.add(name);


                    int cnl =JOptionPane.showConfirmDialog(null, customPanel, "Details", JOptionPane.OK_CANCEL_OPTION);
                    if(cnl==JOptionPane.OK_OPTION){
                        try{
                            PreparedStatement curr_pst = cb.con.prepareStatement("insert into current_slots values(LOWER(?), LOWER(?), CURRENT_TIME(),NOW(),?)");   
                            //(carnumber, name, intime, date, slot)
                            curr_pst.setString(1,c_num.getText());
                            curr_pst.setString(2,name.getText());
                            curr_pst.setInt(3, i);
                            curr_pst.executeUpdate();

                            PreparedStatement tot_pst = cb.con.prepareStatement("insert into total_data values(LOWER(?), LOWER(?), CURRENT_TIME(), NULL, NOW(), NULL, ?)"); 
                            //(carnumber, name, intime, outtime, date,bill, slot)
                            tot_pst.setString(1, c_num.getText());
                            tot_pst.setString(2, name.getText());
                            tot_pst.setInt(3, i);
                            tot_pst.executeUpdate();

                            System.out.println("data entered");
                        }
                        catch(Exception E){
                            System.out.println(E);
                        }
                        slots.get(i).setBackground(Color.RED); // Change color on click
                        slots.get(i).setText(i+": FILLED");
                        empty--;
                        filled++;
                        l1.setText("Filled Slots: "+filled);
                        l2.setText("Empty slots: "+empty);
                        JOptionPane.showMessageDialog(null, "SLOT BOOKED", "BOOKING", JOptionPane.PLAIN_MESSAGE);
                    } 
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    private void CarDetails(int cd, ResultSet r){
        try{
            JPanel p9 = new JPanel();
            p9.add(new JLabel("Car Number:  "+r.getString("car_number")+"    "));
            p9.add(new JLabel("name:  "+r.getString("full_name")));
            JOptionPane.showMessageDialog(null, p9, "Details", JOptionPane.PLAIN_MESSAGE);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void ExitCar(){
        JPanel e_num = new JPanel();
        e_num.add(new JLabel("Enter Car Number: "));

        JTextField ec_num= new JTextField(10);
        e_num.add(ec_num);
        int c = JOptionPane.showConfirmDialog(null, e_num, "EXIT", JOptionPane.OK_CANCEL_OPTION);

        if(c == JOptionPane.OK_OPTION){

            String ec = ec_num.getText().toLowerCase();
            try{
                PreparedStatement b1 = cb.con.prepareStatement("update total_data set exit_date = NOW(), out_time = CURRENT_TIME() where car_number = ?");
                b1.setString(1, ec);
                b1.executeUpdate();

                PreparedStatement b2 = cb.con.prepareStatement("select slot from current_slots where car_number = ?");
                b2.setString(1, ec);
                ResultSet r = b2.executeQuery();
                if(r.next()){
                    int q = r.getInt("slot");
                    slots.get(q).setBackground(Color.GREEN);
                    slots.get(q).setText(q+": EMPTY");
                    empty++;
                    filled--;
                    l1.setText("Filled Slots: "+filled);
                    l2.setText("Empty slots: "+empty);
                }

                PreparedStatement b3 = cb.con.prepareStatement("delete from current_slots where car_number = ?");
                b3.setString(1, ec);
                b3.executeUpdate();
                JOptionPane.showMessageDialog(null, "CAR EXITED", "OUT", JOptionPane.PLAIN_MESSAGE);
                System.out.println("edited");

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    
    
    public static void main(String[] args){
        new Parking();
    }

}
