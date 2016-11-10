/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 *
 * @author Micheal
 * An object that represents each person in the Sisyphus problem
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
    public void setSecretary(boolean b) {
        this.secretary = b;   
    }

    public void setManager(boolean b) {
        this.manager = b;   
    }

    public void setHacker(boolean b) {
        this.hacker = b;   
    }

    public void setResearcher(boolean b) {
        this.researcher = b;   
    }

    public void setSmoker(boolean b) {
        this.smoker = b;   
    }
    
    // Get methods
    public boolean isSecretary(){
        return secretary;
    }
    
    public boolean isManager(){
        return manager;
    }
    
    public boolean isHacker(){
        return hacker;
    }
    
    public boolean isResearcher(){
        return researcher;
    }
    
    public boolean isSmoker(){
        return smoker;
    }
    
        
    public Person(String name) {
        super(name);
    }
    
    
    
    
    
    
	
}