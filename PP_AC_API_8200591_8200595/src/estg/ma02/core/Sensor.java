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

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import static edu.ma02.core.enumerations.Unit.getUnitString;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Sensor implements ISensor {
    /**
     * The sensor INCREMENT variable
     */
    private static final int INCREMENT = 5;
    /**
     * The sensor id
     */
    private final String id;
    /**
     * The sensor type
     */
    private final SensorType type;
    /**
     * The sensor CartesianCoordinates
     */
    private final ICartesianCoordinates cartesianCoordinates;
    /**
     * The sensor geographicCoordinates
     */
    private final IGeographicCoordinates geographicCoordinates;
    /**
     * The sensor measurements
     */
    private IMeasurement[] measurements;
    /**
     * The sensor maxMeasurements
     */
    private int maxMasurements;
    /**
     * The sensor currentMeasurement
     */
    private int currentMeasurement;
    /**
     * The sensor parameter
     */
    private Parameter parameter;

    {
        /**
         * Initialization of the array measurements to an IMeasurement of the INCREMENT size
         */
        measurements = new IMeasurement[INCREMENT];
        /**
         * Initialization of the variable maxMeasurements to the variable INCREMENT
         */
        maxMasurements = INCREMENT;
        /**
         * Initialization of variable currentMeasurement to 0
         */
        currentMeasurement = 0;
    }
    
    /**
     * Creates an instance of <code>Sensor</code>
     * @param id The {@link Sensor#id id}
     * @param type The {@link Sensor#type type}
     * @param cartesianCoordinates The {@link Sensor#cartesianCoordinates cartesianCoordinates}
     * @param geographicCoordinates The {@link Sensor#geographicCoordinates geographicCoordinates}
     */
    public Sensor(String id, SensorType type, ICartesianCoordinates cartesianCoordinates,
            IGeographicCoordinates geographicCoordinates) {
        this.id = id;
        this.type = type;
        this.cartesianCoordinates = cartesianCoordinates;
        this.geographicCoordinates = geographicCoordinates;
        this.parameter = idToParameter(this.id);
    }
    
    /**
     * Gets the {@link Sensor#parameter parameter} of a given sensorid
     * @param id The {@link Sensor#id id}
     * @return NULL if the {@link Sensor#parameter parameter} is invalid, otherwise
     * it returns the parameter
     */
    private Parameter idToParameter(String id) {
        StringBuilder sb;
        Parameter[] parameters;
        if (this.type != null) {
            parameters = this.type.getParameters();
            sb = new StringBuilder();
            if (id.length() == 10) {
                for (int i = 2; i < 6; i++) {
                    if (id.charAt(i) != '0' || i == 5) {
                        sb.append(id.charAt(i));
                    }
                }
            }

            sb = new StringBuilder(sb.toString().toUpperCase());

            if (sb.length() > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].toString().equals(sb.toString())) {
                        return parameters[i];
                    }
                    if (parameters[i] == Parameter.PM2_5 && "PM25".equals(sb.toString())) {
                        return parameters[i];
                    }
                }
            }
        }

        return null;
    }
    
    /**
     * Gets the {@link Sensor#type type} of a given sensorid
     * @param id The {@link Sensor#id id}
     * @return NULL if the {@link Sensor#type type} is invalid, otherwise
     * it returns the parameter
     */
    public static SensorType idToSensorType(String id) {
        StringBuilder type = new StringBuilder();
        if (id.length() == 10) {
            type.append(id.charAt(0));
            type.append(id.charAt(1));
        }

        switch (type.toString().toUpperCase()) {
            case "QA":
                return SensorType.AIR;
            case "RU":
                return SensorType.NOISE;
            case "ME":
                return SensorType.WEATHER;
        }
        return null;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public SensorType getType() {
        return this.type;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return this.cartesianCoordinates;
    }

    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return this.geographicCoordinates;
    }
    
    /**
     * Expands the array of {@link Sensor#IMeasurement[] measurements} incrementing it by
     * {@link Station#INCREMENT INCREMENT} more postions
     */
    private void expandMeasurements() {
        this.maxMasurements = INCREMENT + this.maxMasurements;
        IMeasurement[] tmp = new IMeasurement[maxMasurements];

        for (int i = 0; i < currentMeasurement; i++) {
            tmp[i] = this.measurements[i];
        }

        this.measurements = tmp;
    }
    
    /**
     * Checks if a {@link Sensor#measurement measurement} exists
     * @param id The {@link Sensor#id id}
     * @return -1 if the {@link Sensor#type type} doesn't exists, otherwise
     * it returns the parameter
     */
    private int findMeasurement(IMeasurement measurement) {
        for (int i = 0; i < currentMeasurement; i++) {
            if (this.measurements[i].equals(measurement)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Checks if a unit is equal to the {@link Sensor#parameter parameter} unit
     * @param unit The unit given in the parameter
     * @return false if the unit is not equal to the {@link Sensor#parameter parameter} unit or the unit is invalid 
     * otherwise it returns the parameter
     */
    private boolean verifyUnity(String unit) {

        if (this.parameter == null || unit == null) {
            return false;
        }

        if (unit.equals(getUnitString(parameter.getUnit()))) {
            return true;
        }
        
        if(unit.equals("µg/m3") && getUnitString(parameter.getUnit()).equals("μg/m3")){
            return true;
        }

        return false;

    }

    @Override
    public boolean addMeasurement(double value, LocalDateTime date, String unit)
            throws SensorException, MeasurementException {
        if (value == -99 || date == null || unit == null) {
            throw new MeasurementException("The value is -99 or any parameter is invalid");
        }

        if (!(verifyUnity(unit))) {
            throw new SensorException("The parameter unit is not compatible with the unit pre-defined for the sensor");
        }

        if (this.maxMasurements == this.currentMeasurement) {
            expandMeasurements();
        }
        IMeasurement tmp = new Measurement(date, value);

        int n = findMeasurement(tmp);

        if (n == -1) {
            this.measurements[this.currentMeasurement++] = tmp;
            return true;
        }

        return false;
    }

    @Override
    public int getNumMeasurements() {
        return this.currentMeasurement;
    }

    @Override
    public IMeasurement[] getMeasurements() {
        IMeasurement[] tmpMeasurements = new IMeasurement[this.currentMeasurement];
        for (int i = 0; i < this.currentMeasurement; i++) {
            tmpMeasurements[i] = this.measurements[i];
        }
        return tmpMeasurements;
    }

    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>Sensor</code> and his
     * {@link Sensor#id id}, {@link Sensor#type type}, {@link Sensor#cartesianCoordinates cartesianCoordinates},
     * {@link Sensor#geographicCoordinates geographicCoordinates} and 
     * {@link Sensor#measurements measurements} are the same of this object
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
        if (!(obj instanceof Sensor)) {
            return false;
        }
        final Sensor other = (Sensor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.cartesianCoordinates, other.cartesianCoordinates)) {
            return false;
        }
        if (!Objects.equals(this.geographicCoordinates, other.geographicCoordinates)) {
            return false;
        }
        if (!Arrays.deepEquals(this.measurements, other.measurements)) {
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
     * @return A string representation of the object (<code>Sensor</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID Sensor:").append(id).append("\n");
        sb.append("Sensor type:").append(type).append("\n");
        sb.append("Cartesian Coordinates:").append(cartesianCoordinates.toString()).append("\n");
        sb.append("Geographic Coordinates:").append(geographicCoordinates.toString()).append("\n");
        for (int i = 0; i < currentMeasurement; i++) {
            sb.append(measurements[i].toString()).append("\n");
        }
        return sb.toString();
    }

}
