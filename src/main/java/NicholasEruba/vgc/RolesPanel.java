/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nicholas
 */
public class RolesPanel extends javax.swing.JPanel {

    /**
     * Creates new form RolesPanel
     */
    Map thisUser;
    UserRole thisUserRole;

    public RolesPanel(HashMap userType) {
        initComponents();
        //further init based on information from login panel
        thisUser = userType;
        String roleAndName = thisUser.get("usertype").toString() + " | " + thisUser.get("firstname").toString() + " " + thisUser.get("lastname").toString();
        setBorder(javax.swing.BorderFactory.createTitledBorder(roleAndName));
        initMenu(thisUser.get("usertype").toString());
        switch(thisUser.get("usertype").toString()){
            case "admin":
                thisUserRole = new AdminRole();
                break;
            case "student":
                thisUserRole = new StudentRole();
                break;
            case "teacher":
                thisUserRole = new TeacherFacultyRole();
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        rolesPanelTextArea = new javax.swing.JTextArea();
        menuComboBox = new javax.swing.JComboBox<>();
        returnToLoginButton = new javax.swing.JButton();
        menuComboBoxLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("roles-panel"));

        rolesPanelTextArea.setColumns(20);
        rolesPanelTextArea.setRows(5);
        jScrollPane1.setViewportView(rolesPanelTextArea);

        menuComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        menuComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                menuComboBoxItemStateChanged(evt);
            }
        });
        menuComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuComboBoxActionPerformed(evt);
            }
        });

        returnToLoginButton.setText("Return to Login");
        returnToLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnToLoginButtonActionPerformed(evt);
            }
        });

        menuComboBoxLabel.setText("Menu:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(menuComboBoxLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(menuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(returnToLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuComboBoxLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(menuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(returnToLoginButton)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void returnToLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnToLoginButtonActionPerformed
        // TODO add your handling code here:
        MainFrame.setPane(new LoginEnrollPanel());
    }//GEN-LAST:event_returnToLoginButtonActionPerformed

    private void menuComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_menuComboBoxItemStateChanged
        // TODO add your handling code here:
        //get selected menu action
//        if(evt.getStateChange() == ItemEvent.SELECTED){
//        System.out.println(menuComboBox.getSelectedItem().toString());
//        }
        
    }//GEN-LAST:event_menuComboBoxItemStateChanged

    private void menuComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuComboBoxActionPerformed
        // TODO add your handling code here:
        thisUserRole.processMenuOption(menuComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_menuComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> menuComboBox;
    private javax.swing.JLabel menuComboBoxLabel;
    private javax.swing.JButton returnToLoginButton;
    private javax.swing.JTextArea rolesPanelTextArea;
    // End of variables declaration//GEN-END:variables

    private void initMenu(String userType) {
        switch (userType) {
            case "admin":
                menuComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"create-new-faculty", "do some shit"}));
                break;
            case "student":
                menuComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"pay-fees"}));
                break;
            case "teacher":
                menuComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"take-attendance", "view-lesson-plan"}));
                break;
        }
    }
}
