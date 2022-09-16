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

import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.ICityStatistics;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class City implements ICity, ICityStatistics {
    /**
     * The City INCREMENT variable
     */
    private static final int INCREMENT = 5;
    /**
     * The City id
     */
    private final String id;
    /**
     * The City name
     */
    private final String name;
    /**
     * The City station
     */
    private IStation[] stations;
    /**
     * The City maxStation
     */
    private int maxStations;
    /**
     * The City currentStation
     */
    private int currentStation;

    {
        /**
         * Initialization of the array {@link City#stations stations} to an IStation of the INCREMENT size
         */
        stations = new IStation[INCREMENT];
        /**
         * Initialization of the variable {@link City#maxStations maxStations} to the variable INCREMENT
         */
        maxStations = INCREMENT;
        /**
         * Initialization of variable {@link City#currentStation currentStation} to 0
         */
        currentStation = 0;
    }
    
    /**
     * Creates an instance of <code>City</code>
     * @param id {@link City#id id}
     * @param name {@link City#name name}
     */
    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Expands the array of {@link City#stations stations} incrementing it by
     * {@link City#INCREMENT INCREMENT} more postions
     */
    private void expandStations() {
        this.maxStations = INCREMENT + this.maxStations;
        IStation[] tmp = new IStation[maxStations];

        for (int i = 0; i < currentStation; i++) {
            tmp[i] = this.stations[i];
        }

        this.stations = tmp;
    }
    
    /**
     * Checks if a station exists
     * @param stationName A name of station to be compared
     * @return -1 if the station doesn't exists, otherwise
     * it returns the position where the station is located
     */
    private int findStation(String stationName) {
        for (int i = 0; i < this.currentStation; i++) {
            if (this.stations[i].getName().equals(stationName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean addStation(String name) throws CityException {
        if (name == null) {
            throw new CityException("The param string is null");
        }
        if (this.currentStation == this.maxStations) {
            this.expandStations();
        }
        if (findStation(name) == -1) {
            this.stations[this.currentStation++] = new Station(name);
            return true;
        }
        return false;
    }

    @Override
    public boolean addSensor(String stationName, String sensorId, ICartesianCoordinates cartesianCoordinates,
            IGeographicCoordinates geographicCoordenates) throws CityException, StationException, SensorException {
        int pos = findStation(stationName);
        SensorType sensorType = Sensor.idToSensorType(sensorId);
        
        if (pos == -1 || stationName == null) {
            throw new CityException("The station doesn't exists or the parameter is null");
        }

        ISensor tmpSensor = new Sensor(sensorId, sensorType, cartesianCoordinates, geographicCoordenates);
        this.stations[pos].addSensor(tmpSensor);
        return true;
    }

    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value, String unit, LocalDateTime date)
            throws CityException, StationException, SensorException, MeasurementException {
        int posStation = findStation(stationName);

        if (posStation == -1 || stationName == null) {
            throw new CityException("The station doesn't exist or the param stationName is invalid");
        }

        return this.stations[posStation].addMeasurement(sensorId, value, date, unit);
    }

    @Override
    public IStation[] getStations() {
        IStation[] tmpStations = new IStation[this.currentStation];
        for (int i = 0; i < this.currentStation; i++) {
            tmpStations[i] = this.stations[i];
        }
        return tmpStations;
    }

    @Override
    public IStation getStation(String name) {
        for (int i = 0; i < this.currentStation; i++) {
            if (this.stations[i].getName().equals(name)) {
                return this.stations[i];
            }
        }
        return null;
    }

    @Override
    public ISensor[] getSensorsByStation(String stationName) {
        IStation tmp = getStation(stationName);
        if (tmp != null) {
            return tmp.getSensors();
        }
        return null;
    }

    @Override
    public IMeasurement[] getMeasurementsBySensor(String sensorId) {
        for (int i = 0; i < this.currentStation; i++) {
            ISensor tmp = this.stations[i].getSensor(sensorId);
            if (tmp != null) {
                return tmp.getMeasurements();
            }
        }
        return null;
    }
    
    /**
     * Gets the measurements with a specific parameter of a given station 
     * @param station An IStation instance to be compared
     * @param parameter A parameter instance to be compared
     * @return An array of IMeasurement's, if there are no measurements it is 
     * returned a null array
     */
    private IMeasurement[] getStationMeasurementsByParameter(IStation station, Parameter parameter) {
        ISensor[] stationSensors = station.getSensors();
        IMeasurement[] sensorMeasurements = null;
        int numberMeasurements = 0;
        int currentMeasurement = 0;

        for (int i = 0; i < stationSensors.length; i++) {
            if (stationSensors[i] != null && stationSensors[i].getParameter() == parameter) {
                numberMeasurements += stationSensors[i].getNumMeasurements();
            }
        }

        sensorMeasurements = new IMeasurement[numberMeasurements];

        for (int i = 0; i < stationSensors.length; i++) {
            if (stationSensors[i] != null && stationSensors[i].getParameter() == parameter) {
                IMeasurement[] tmpMeasurement = stationSensors[i].getMeasurements();
                for (int j = 0; j < tmpMeasurement.length; j++) {
                    sensorMeasurements[currentMeasurement++] = tmpMeasurement[j];
                }
            }
        }

        return sensorMeasurements;
    }
    
    /**
     * Calculates the number, maximum, minimum and average values of a given array of
     * IMeasurements
     * @param measurements The IMeasurement array with the values to be calculated
     * @param operator The AggregationOperator
     * @param start The initial time
     * @param end The final time
     * @return The AVG, COUNT, MAX or MIN taking into account the Aggregation Operator
     */
    private double calcMeasurementsByOperator(IMeasurement[] measurements, AggregationOperator operator,
            LocalDateTime start, LocalDateTime end) {
        
        if(measurements == null){
            return 0;
        }
        
        double count = 0;
        double avg = 0;        
        double max = 0;
        double min = 0;
        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] != null) {
                if (start == null || end == null
                        || !measurements[i].getTime().isBefore(start) && !measurements[i].getTime().isAfter(end)) {
                    count++;
                    avg += measurements[i].getValue();
                    if (measurements[i].getValue() < min || count==0) {
                        min = measurements[i].getValue();
                    }
                    if (measurements[i].getValue() > max || count==0) {
                        max = measurements[i].getValue();
                    }
                }
            }
        }
        if (count != 0) {
            avg = avg / count;
        } else {
            avg = 0;
        }
        switch (operator) {
            case AVG:
                return avg;
            case COUNT:
                return count;
            case MAX:
                return max;
            case MIN:
                return min;
        }
        return 0;
    }

    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator operator, Parameter parameter,
            LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }
        IStatistics[] statistics = new IStatistics[this.currentStation];
        for (int i = 0; i < this.currentStation; i++) {
            IMeasurement[] stationMeasurements = getStationMeasurementsByParameter(this.stations[i], parameter);
            statistics[i] = new Statistics(this.stations[i].getName(),
                    calcMeasurementsByOperator(stationMeasurements, operator, start, end));
        }
        return statistics;
    }

    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator operator, Parameter parameter) {
        IStatistics[] statistics = new IStatistics[this.currentStation];
        for (int i = 0; i < this.currentStation; i++) {
            IMeasurement[] stationMeasurements = getStationMeasurementsByParameter(this.stations[i], parameter);
            statistics[i] = new Statistics(this.stations[i].getName() + " "+ operator.toString(),
                    calcMeasurementsByOperator(stationMeasurements, operator, null, null));
        }
        return statistics;
    }

    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator operator, Parameter parameter,
            LocalDateTime start, LocalDateTime end) {

        int pos = findStation(stationName);
        IStatistics[] statistics;
        ISensor[] tmpSensors;

        if (start == null || end == null || pos == -1) {
            return null;
        }

        tmpSensors = this.stations[pos].getSensors();
        statistics = new IStatistics[tmpSensors.length];

        for (int i = 0; i < tmpSensors.length; i++) {
            IMeasurement[] measurements = tmpSensors[i].getMeasurements();
            statistics[i] = new Statistics("Statistics sensor " + tmpSensors[i].getId(),
                    calcMeasurementsByOperator(measurements, operator, start, end));
        }
        return statistics;
    }

    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator operator,
            Parameter parameter) {
        int pos = findStation(stationName);
        IStatistics[] statistics;
        ISensor[] tmpSensors;

        if (pos == -1) {
            return null;
        }

        tmpSensors = this.stations[pos].getSensors();
        statistics = new IStatistics[tmpSensors.length];

        for (int i = 0; i < tmpSensors.length; i++) {
            IMeasurement[] measurements = tmpSensors[i].getMeasurements();
            statistics[i] = new Statistics("Statistics sensor " + tmpSensors[i].getId(),
                    calcMeasurementsByOperator(measurements, operator, null, null));
        }
        return statistics;
    }

    /**
     * Indicates whether some other object is "equal to" this one, that is, if
     * the other object is an instance of <code>City</code> and his
     * {@link City#id id}, {@link City#name name} and {@link City#stations stations}
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
        if (!(obj instanceof ICity)) {
            return false;
        }
        final City other = (City) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.stations, other.stations)) {
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
     * @return A string representation of the object (<code>City</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID City:").append(id).append("\n");
        sb.append("Name City:").append(name).append("\n");
        for (int i = 0; i < currentStation; i++) {
            sb.append(stations[i].toString());
        }
        return sb.toString();
    }

}