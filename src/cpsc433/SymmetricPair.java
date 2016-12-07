/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 * @author Chris
 *         <p>
 *         Class to hold data of the form (Object1, Object2)
 *         <p>
 *         The symmetry results from computing a hash code of
 *         the constituent objects that is symmetric and defining
 *         equality as equal hash codes.
 *         <p>
 *         Thus
 *         (Object1, Object2).equals( (Object2, Object1) ) is true
 */
public class SymmetricPair<I1, I2> {
    public final I1 left;
    public final I2 right;

    public SymmetricPair(I1 left, I2 right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SymmetricPair)) return false;

        // Equality is defined as equal hash codes, since
        // hash codes are computed as an xor the result
        // is symmetric
        SymmetricPair otherSPair = (SymmetricPair) o;
        return (this.hashCode() == otherSPair.hashCode());
    }
}
