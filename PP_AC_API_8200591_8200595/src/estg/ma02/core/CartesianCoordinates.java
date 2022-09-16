/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */
package estg.ma02.core;

import edu.ma02.core.interfaces.ICartesianCoordinates;

public class CartesianCoordinates implements ICartesianCoordinates {
    /**
     * The CartesianCoordinate X
     */
    private final double x;
    /**
     * The CartesianCoordinate Y
     */
    private final double y;
    /**
     * The CartesianCoordinate Z
     */
    private final double z;
    
    /**
     * Creates an instance of <code>CartesianCoordinates</code>
     * @param x The {@link CartesianCoordinates#x X}
     * @param y The {@link CartesianCoordinates#y Y}
     * @param z The {@link CartesianCoordinates#z Z}
     */
    public CartesianCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public double getX() {
        return this.x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public double getZ() {
        return this.z;
    }
    
    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read
     * 
     * @return A string representation of the object (<code>CartesianCoordinates</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x:").append(this.x).append("\n");
        sb.append("y:").append(this.y).append("\n");
        sb.append("z:").append(this.z).append("\n");
        return sb.toString();
    }
    
    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>CartesianCoordinates</code> and his
     * {@link CartesianCoordinates#x X}, {@link CartesianCoordinates#y Y} and 
     * {@link CartesianCoordinates#z Z} are the same of this object
     * @param obj The reference object with which to compare
     * @return <code>true</code> if this object is "the same" as the
     * <code>obj</code> argument; <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CartesianCoordinates)) {
            return false;
        }
        final CartesianCoordinates other = (CartesianCoordinates) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }
    
}
