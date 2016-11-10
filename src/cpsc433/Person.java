/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 *
 * @author Micheal
 * An object that represents each person in the sisyphus problem
 */
public class Person extends Entity{
    // Array of projects the person is assigned to
    private Project[] projects;
    // Array of groups the person is associated with
    private Group[] groups; 
    // Role booleans
    private boolean secretary;
    private boolean manager;
    private boolean hacker;
    private boolean researcher;
    // Smoker
    private boolean smoker;

    // Set methods for the private variables of person
    private void setSecretary(boolean b) {
        this.secretary = b;   
    }

    private void setManager(boolean b) {
        this.manager = b;   
    }

    private void setHacker(boolean b) {
        this.hacker = b;   
    }

    private void setResearcher(boolean b) {
        this.researcher = b;   
    }

    private void setSmoker(boolean b) {
        this.smoker = b;   
    }
        
    public Person(String name) {
        super(name);
    }
    	
	
}