/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

/**
 *
 * @author Nicholas 18630
 */
public class AdminRole extends UserRole {

    @Override
    public void processMenuOption(String option) {
        switch(option){
        case "create-new-faculty":
            MainFrame.popupFrame.display(new CreateNewFaculty(), "Create New Faculty");
        break;
        }
       
    }
    
}
