/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */

package estg.ma02.io;

import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import estg.ma02.core.CartesianCoordinates;
import estg.ma02.core.GeographicCoordinates;
import estg.ma02.date.DateManagement;
import estg.ma02.date.DateManagementException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Importer implements IImporter {

    @Override
    public IOStatistics importData(ICity city, String file) throws FileNotFoundException, IOException, CityException {

        String[] exceptions = {};
        int numberOfStationsRead = 0;
        int numberOfNewStationsRead = city.getStations().length;
        int numberOfSensorsRead = 0;
        int numberOfNewSensorsRead = getNumberSensors(city);
        int numberOfReadMeasurements = 0;
        int numberOfNewMeasurementsRead = getNumberMeasurement(city);

        JSONArray data;
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(file);

        try {
            data = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            throw new IOException("Ficheiro nao legivel");
        }

        for (int i = 0; i < data.size(); i++) {
            JSONObject resultObject = (JSONObject) data.get(i);
            try {
                addStation(city, resultObject);
                addSensor(city, resultObject);
                addMeasurement(city, resultObject);
            } catch (CityException | StationException | SensorException | MeasurementException e) {
                exceptions = addString(exceptions, e.getMessage());
            }
        }

        numberOfStationsRead = city.getStations().length;
        numberOfNewStationsRead = numberOfStationsRead - numberOfNewStationsRead;
        numberOfSensorsRead = getNumberSensors(city);
        numberOfNewSensorsRead = numberOfSensorsRead - numberOfNewSensorsRead;
        numberOfReadMeasurements = getNumberMeasurement(city);
        numberOfNewMeasurementsRead = numberOfReadMeasurements - numberOfNewMeasurementsRead;

        return new OStatistics(numberOfReadMeasurements, numberOfStationsRead, numberOfNewStationsRead,
                numberOfSensorsRead, numberOfNewSensorsRead, numberOfNewMeasurementsRead, exceptions);
    }

    /**
     * Adds a {@link String value} to the data structure used to store {@link String[] exceptions}.
     * 
     * @param exceptions The {@link String[] exceptions} where it will be added
     * @param value The {@link String value} to be added
     * @return {@link String[] exceptions} with the added value
     */
    private String[] addString(String[] exceptions, String value) {
        if (value != null) {
            String[] tmpStrings = new String[exceptions.length + 1];
            if (exceptions != null) {
                for (int i = 0; i < exceptions.length; i++) {
                    tmpStrings[i] = exceptions[i];
                }
            }
            tmpStrings[exceptions.length] = value;
            return tmpStrings;
        }
        return null;
    }

    /**
     * Adds a {@link IStation} to the data structure used to store {@link ICity city}.
     * 
     * @param city The {@link ICity city} where it will be added
     * @param resultObject The {@link JSONObject resultObject} with the data from the {@link IStation}
     * @throws CityException if the @param resultObject is invalid or {@link IStation} already exists
     */
    private void addStation(ICity city, JSONObject resultObject) throws CityException {
        try {
            String stationName = (String) resultObject.get("address");
            city.addStation(stationName);
        } catch (NullPointerException e) {
            throw new CityException("Information of station is invalid");
        }
    }

    /**
     * Adds a {@link ISensor} to the data structure used to store {@link ICity city}.
     * 
     * @param city The {@link ICity city} where it will be added
     * @param resultObject The {@link JSONObject resultObject} with the data from the {@link ISensor}
     * @throws CityException if the {@link IStation} doesn't exists or if @param resultObject is invalid
     * @throws StationException if the {@link ISensor} in @param resultObject already exists
     * @throws SensorException if the data of the {@link ISensor} in @param resultObject is invalid
     */
    private void addSensor(ICity city, JSONObject resultObject)
            throws CityException, StationException, SensorException {
        try {
            String tmpID = (String) resultObject.get("id");
            String stationName = (String) resultObject.get("address");
            JSONObject resultObjectCoordinates = (JSONObject) resultObject.get("coordinates");

            double tmpX = Double.parseDouble(resultObjectCoordinates.get("x").toString());
            double tmpY = Double.parseDouble(resultObjectCoordinates.get("y").toString());
            double tmpZ = Double.parseDouble(resultObjectCoordinates.get("z").toString());
            double tmpLat = Double.parseDouble(resultObjectCoordinates.get("lat").toString());
            double tmpLng = Double.parseDouble(resultObjectCoordinates.get("lng").toString());
            city.addSensor(stationName, tmpID, new CartesianCoordinates(tmpX, tmpY, tmpZ),
                    new GeographicCoordinates(tmpLat, tmpLng));
        } catch (NullPointerException e) {
            throw new CityException("Information of sensor is invalid");
        }
    }

    /**
     * Adds a {@link IMeasurement} to the data structure used to store {@link ICity city}.
     * 
     * @param city The {@link ICity city} where it will be added
     * @param resultObject The {@link JSONObject resultObject} with the data from the {@link IMeasurement}
     * @throws CityException if the {@link IStation} doesn't exists or if @param resultObject is invalid
     * @throws StationException if the {@link ISensor} doesn't exists or if @param resultObject is invalid
     * @throws SensorException if the {@link IMeasurement} in @param resultObject already exists
     * @throws MeasurementException if the data of the {@link IMeasurement} in @param resultObject is invalid
     */
    private void addMeasurement(ICity city, JSONObject resultObject)
            throws CityException, StationException, SensorException, MeasurementException {
        try {
            String tmpID = (String) resultObject.get("id");
            String stationName = (String) resultObject.get("address");
            String tmpUnit = (String) resultObject.get("unit");
            double tmpValue = Double.parseDouble(resultObject.get("value").toString());

            DateManagement dateManagement = new DateManagement(DateManagement.DATE_FORMAT1);
            
            LocalDateTime dateTime = dateManagement.getDateTime(resultObject.get("date").toString());
            city.addMeasurement(stationName, tmpID, tmpValue, tmpUnit, dateTime);

        } catch (NullPointerException | DateTimeParseException | DateManagementException e) {
            throw new CityException("Date is invalid");
        }
    }

    /**
     * Says how many {@link IMeasurement measurement} exists in the data structure {@link ISensor sensor}
     * 
     * @param city The {@link ISensor sensor} used to count {@link IMeasurement measurement}
     * @return The number of {@link ISensor sensor} exists in the data structure {@link ICity city}
     */
    private int getNumberSensors(ICity city) {
        int numberSensors = 0;
        for (int i = 0; i < city.getStations().length; i++) {
            numberSensors += city.getStations()[i].getSensors().length;
        }
        return numberSensors;
    }

    /**
     * Says how many {@link IMeasurement measurement} exists in the data structure {@link ICity city}
     * 
     * @param city The {@link ICity city} used to count {@link IMeasurement measurement}
     * @return The number of {@link IMeasurement measurement} exists in the data structure {@link ICity city}
     */
    private int getNumberMeasurement(ICity city) {
        int numberMeasurement = 0;
        for (int i = 0; i < city.getStations().length; i++) {
            for (int j = 0; j < city.getStations()[i].getSensors().length; j++) {
                numberMeasurement += city.getStations()[i].getSensors()[j].getMeasurements().length;
            }
        }
        return numberMeasurement;
    }

}
