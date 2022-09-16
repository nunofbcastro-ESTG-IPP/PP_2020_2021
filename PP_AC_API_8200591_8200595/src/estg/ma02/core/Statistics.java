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

import edu.ma02.core.interfaces.IStatistics;
import java.util.Objects;

public class Statistics implements IStatistics {
    /**
     * The statistics description
     */
    private String description;
    /**
     * The statistics value
     */
    private double value;
    
    /**
     * Creates an instance of <code>Statistics</code>
     *
     * @param description The {@link Statistics#description description}
     * @param value The {@link Statistics#value value}
     */
    public Statistics(String description, double value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getValue() {
        return this.value;
    }
    
    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read
     * 
     * @return A string representation of the object (<code>Statistics</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Description:").append(description).append("\n");
        sb.append("Value:").append(value).append("\n");
        return sb.toString();
    }
    
    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>Statistics</code> and his
     * {@link Statistics#description description} and {@link Statistics#value value}
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
        if (!(obj instanceof Statistics)) {
            return false;
        }
        final Statistics other = (Statistics) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
}
