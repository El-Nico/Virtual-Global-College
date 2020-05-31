/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Nicholas Chibuike-Eruba 18630
 */
public class EnrollInCourse extends JPanel {

    public EnrollInCourse() {
        initComponents();
    }

    ArrayList<String> facultiesEnrolled = new ArrayList();

    private void initComponents() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //get all faculties from database
        ArrayList<String> faculties = MainFrame.db.getFaculties();
        //create a radio button for each faculty
        for (String faculty : faculties) {
            JCheckBox course = new JCheckBox(faculty);
            course.setActionCommand("faculty");
            course.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    facultiesEnrolled.add(course.getText());
                }

            });
            add(course);
        }
        //add the submit button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(MainFrame.db.enrollStudentInCourses(
                        MainFrame.db.getUserType().get("id").toString(),
                         facultiesEnrolled
                )){
                    MainFrame.popupFrame.dispose();
                }
            }

        });
        add(submit);
        setSize(200, 200);
        setVisible(true);
    }

}
