/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import java.awt.event.ItemEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Nicholas Chibuike-Eruba 18630
 */
public class TakeAttendanceView extends JPanel {
    
   ArrayList<String> registeredStudents = new ArrayList();
   String attendanceValue=MainFrame.db.getAttendance(MainFrame.db.getUserType().get("faculty").toString());
   public TakeAttendanceView(){
       //get all students enrolled in course
       registeredStudents=MainFrame.db.getStudentsEnrolled(MainFrame.db.getUserType().get("faculty").toString());
       //create gui based on list
       initComponents(registeredStudents);
       //live update gui
   }

    private void initComponents(ArrayList<String> registeredStudents) {
        attendanceValue+=LocalDateTime.now()+"\n";
         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //create a label and radio button group for each student
        for (String student : registeredStudents) {
            JLabel studentLabel = new JLabel(student);
            JRadioButton present = new JRadioButton("present");
            present.setActionCommand(student + " present");
            JRadioButton absent = new JRadioButton("absent");
            absent.setActionCommand(student + " absent");
            ButtonGroup paGroup = new ButtonGroup();
            paGroup.add(absent);
            paGroup.add(present);
            present.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    if(evt.getStateChange() == ItemEvent.SELECTED){
                        //update database
                        attendanceValue+=paGroup.getSelection().getActionCommand()+"\n";
                        System.out.println(paGroup.getSelection().getActionCommand());
                    }
                }

            });
            absent.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    if(evt.getStateChange() == ItemEvent.SELECTED){
                        attendanceValue+=paGroup.getSelection().getActionCommand()+"\n";
                        System.out.println(paGroup.getSelection().getActionCommand());
                    };
                }

            });
            add(studentLabel);
            add(present);
            add(absent);
        }
        //add the submit button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(MainFrame.db.postAttendance(
                        MainFrame.db.getUserType().get("faculty").toString(),
                        attendanceValue
                )){
                    String showAttendance=MainFrame.db.getAttendance( MainFrame.db.getUserType().get("faculty").toString());
                //display on the text rolespanel text view
                //make roles panel clear button visible
                MainFrame.rolesPanel.setTextAreaB(showAttendance);
                    MainFrame.popupFrame.dispose();
                }
            }

        });
        add(submit);
        setSize(200, 200);
        setVisible(true);
    }
}
