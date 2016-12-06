/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 *
 * @author Chris
 */
public class OccupantSwap extends Swap {
    public final Room room1;
    public final Room room2;
    
    public OccupantSwap(Room room1, Room room2) {
        super(SwapType.OCCUPANT);
        
        this.room1 = room1;
        this.room2 = room2;
    }
}
