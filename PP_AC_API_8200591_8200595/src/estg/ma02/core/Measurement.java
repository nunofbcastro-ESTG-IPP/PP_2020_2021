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
import edu.ma02.core.interfaces.IMeasurement;
import estg.ma02.date.DateManagement;
import java.time.LocalDateTime;
import java.util.Objects;

public class Measurement implements IMeasurement{
    /**
     * The Measurement time that it was registered
     */
    private final LocalDateTime time;
    /**
     * The Measurement value
     */
    private final double value;
    
    /**
     * Creates an instance of <code>Measurement</code>
     * @param time The {@link Measurement#time time}
     * @param value The {@link Measurement#value value}
     */
    public Measurement(LocalDateTime time, double value) {
        this.time = time;
        this.value = value;
    }

    @Override
    public LocalDateTime getTime() {
       return time;
    }

    @Override
    public double getValue() {
        return value;
    }
    
    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>Measurement</code> and his
     * {@link Measurement#time time} and {@link Measurement#value value}
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
        if (!(obj instanceof Measurement)) {
            return false;
        }
        final Measurement other = (Measurement) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read
     * 
     * @return A string representation of the object (<code>Measurement</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Time:").append(DateManagement.dateToString(time)).append("\n");
        sb.append("Value:").append(value).append("\n");
        return sb.toString();
    }
    
}
