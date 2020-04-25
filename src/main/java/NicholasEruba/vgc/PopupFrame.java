/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nicholas
 */
public class PopupFrame extends MainFrame{
    protected void display(JPanel contentPane, String title){
        this.setTitle(title);
        this.setContentPane(contentPane);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void dismiss() {
       dispose();
    }
}
