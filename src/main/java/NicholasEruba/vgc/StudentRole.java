/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

/**
 *
 * @author Nicholas Chibuike-Eruba 18630
 */
public class StudentRole extends UserRole {

    @Override
    public void processMenuOption(String option) {
        switch (option) {
            case "pay-fees":
                System.out.println("paid fees here");
            case "enroll-in-course":
               MainFrame.popupFrame.display(new EnrollInCourse(), "Enroll In a Course");
                System.out.println("enrolled in course here");
                break;
        }
    }
}
