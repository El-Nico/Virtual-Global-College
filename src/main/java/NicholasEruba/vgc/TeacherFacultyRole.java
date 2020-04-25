/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

/**
 *
 * @author Nicholas
 */
public class TeacherFacultyRole extends UserRole {

    @Override
    public void processMenuOption(String option) {
        switch (option) {
            case "take-attendance":
                System.out.println("took attendance here");
                break;
            case "view-lesson-plan":
                System.out.println("viewed lesson plan here");
                break;
        }
    }
}
