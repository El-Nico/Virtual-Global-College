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
public class StudentRole extends UserRole {

    @Override
    public void processMenuOption(String option) {
        switch (option) {
            case "pay-fees":
                System.out.println("paid fees here");
                break;
        }
    }
}
