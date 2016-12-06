/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 *
 * @author Chris
 * 
 * Dummy class to have common superclass for swap storage classes
 */
public class Swap {
    public enum SwapType {
        OCCUPANT, SINGLE
    }
    
    protected final SwapType type;
    
    protected Swap(SwapType type) {
        this.type = type;
    }
}
