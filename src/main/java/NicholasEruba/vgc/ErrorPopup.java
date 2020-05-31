
package NicholasEruba.vgc;

import javax.swing.JOptionPane;

/**
 *
 * @author Nicholas Chibuike-Eruba 18630
 */
public class ErrorPopup {
    public static void showMessage(boolean error, String title, String message){
        if (error){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
