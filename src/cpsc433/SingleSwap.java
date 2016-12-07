/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 * @author Chris
 */
public class SingleSwap extends Swap {
    public final Room room1;
    public final Room room2;

    public final Person person1;
    public final Person person2;

    public SingleSwap(Room room1, Room room2, Person person1, Person person2) {
        super(SwapType.SINGLE);

        this.room1 = room1;
        this.room2 = room2;

        this.person1 = person1;
        this.person2 = person2;
    }
}
