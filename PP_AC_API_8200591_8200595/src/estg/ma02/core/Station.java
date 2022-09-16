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

import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Station implements IStation {
    /**
     * The station INCREMENT variable
     */
    private static final int INCREMENT = 5;
    /**
     * The station name
     */
    private final String name;
    /**
     * The station sensors
     */
    private ISensor[] sensors;
    /**
     * The station maxSensors
     */
    private int maxSensors;
    /**
     * The station currentSensor
     */
    private int currentSensor;

    {
        /**
         * Initialization of the array {@link Station#sensors sensors} to an ISensor of the INCREMENT size
         */
        sensors = new ISensor[INCREMENT];
        /**
         * Initialization of the variable {@link Station#maxSensors maxSensors} to the variable INCREMENT
         */
        maxSensors = INCREMENT;
        /**
         * Initialization of variable {@link Station#currentSensor currentSensor} to 0
         */
        currentSensor = 0;
    }
    
    /**
     * Creates an instance of <code>Station</code>
     * @param name {@link Station#name name}
     */
    public Station(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Expands the array of {@link Station#sensors sensors} incrementing it by
     * {@link Station#INCREMENT INCREMENT} more postions
     */
    private void expandSensors() {
        this.maxSensors = INCREMENT + this.maxSensors;
        ISensor[] tmp = new ISensor[maxSensors];

        for (int i = 0; i < currentSensor; i++) {
            tmp[i] = this.sensors[i];
        }

        this.sensors = tmp;
    }

    @Override
    public boolean addSensor(ISensor sensor) throws StationException, SensorException {
        if (sensor == null) {
            throw new StationException("The param sensor is null");
        }
        if (sensor.getId().length() != 10) {
            throw new SensorException("The sensorid is invalid");
        }
        if (sensor.getType() == null) {
            throw new SensorException("The SensorType is invalid");
        }
        if (sensor.getParameter() == null) {
            throw new SensorException("The parameter is invalid");
        }
        if (maxSensors == currentSensor) {
            expandSensors();
        }
        if(getSensor(sensor.getId())!=null){
            return false;
        }
        this.sensors[currentSensor++] = sensor;
        return true;
    }

    @Override
    public boolean addMeasurement(String sensorId, double value, LocalDateTime date, String unit)
            throws StationException, SensorException, MeasurementException {
        ISensor tmp = getSensor(sensorId);
        if (this.getSensor(sensorId) == null || sensorId == null || date == null || unit == null || tmp == null) {
            throw new StationException("The sensor doesn't exist or any parameter is null");
        }

        return tmp.addMeasurement(value, date, unit);
    }

    @Override
    public ISensor[] getSensors() {
        ISensor[] tmpSensors = new ISensor[this.currentSensor];
        for (int i = 0; i < this.currentSensor; i++) {
            tmpSensors[i] = this.sensors[i];
        }
        return tmpSensors;
    }

    @Override
    public ISensor getSensor(String id) {
        for (int i = 0; i < this.currentSensor; i++) {
            if (sensors[i].getId().equals(id)) {
                return sensors[i];
            }
        }
        return null;
    }

    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>Station</code> and his
     * {@link Station#name name} and {@link Station#sensors sensors}
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
        if (!(obj instanceof Station)) {
            return false;
        }
        final Station other = (Station) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.sensors, other.sensors)) {
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
     * @return A string representation of the object (<code>Station</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name Station:").append(name);
        for(int i=0; i<currentSensor; i++){
            sb.append(sensors[i].toString());
        }
        return sb.toString();
    }
    
}