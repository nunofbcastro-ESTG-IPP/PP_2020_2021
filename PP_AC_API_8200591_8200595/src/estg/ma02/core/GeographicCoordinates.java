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

import edu.ma02.core.interfaces.IGeographicCoordinates;

public class GeographicCoordinates implements IGeographicCoordinates {
    /**
     * The GeographicCoordinates latitude
     */
    private double latitude;
    /**
     * The GeographicCoordinates longitude
     */
    private double longitude;
    
    /**
     * Creates an instance of <code>GeographicCoordinates</code>
     * @param latitude The {@link GeographicCoordinates#latitude latitude}
     * @param longitude The {@link GeographicCoordinates#longitude longitude}
     */
    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }
    
    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read
     * 
     * @return A string representation of the object (<code>GeographicCoordinates</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Latitude:").append(this.latitude).append("\n");
        sb.append("Longitude:").append(this.longitude).append("\n");
        return sb.toString();
    }
    
    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>GeographicCoordinates</code> and his
     * {@link GeographicCoordinates#latitude latitude} and {@link GeographicCoordinates#longitude longitude}
     * are the same of this object
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
        if (!(obj instanceof GeographicCoordinates)) {
            return false;
        }
        final GeographicCoordinates other = (GeographicCoordinates) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }
    
}
