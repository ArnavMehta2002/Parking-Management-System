/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking_management_system;

import javax.swing.SwingUtilities;

/*
 * @author arnav mehta
 */
public class Parking_management_system {

    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {new LoginPage();} );
    }
    
}
