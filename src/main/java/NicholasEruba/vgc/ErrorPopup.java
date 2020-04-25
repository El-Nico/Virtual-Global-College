
package NicholasEruba.vgc;

import javax.swing.JOptionPane;

/**
 *
 * @author Nicholas
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
