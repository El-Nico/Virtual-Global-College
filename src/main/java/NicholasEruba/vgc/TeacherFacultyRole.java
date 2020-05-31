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
public class TeacherFacultyRole extends UserRole {
    
    String facultyName;
    
    public TeacherFacultyRole(String facultyName){
        this.facultyName=facultyName;
    }

    @Override
    public void processMenuOption(String option) {
        switch (option) {
            case "take-attendance":
                MainFrame.popupFrame.display(new TakeAttendanceView(), "Enroll In a Course");
                System.out.println("took attendance here");
                break;
            case "view-lesson-plan":
                System.out.println("viewed lesson plan here");
                //get lesson plan from database
                String lessonPlan=MainFrame.db.getLessonPlanFromDatabase(facultyName);
                //display on the text rolespanel text view
                //make roles panel action button visible
                MainFrame.rolesPanel.setTextArea(lessonPlan);
                break;
        }
    }
}
