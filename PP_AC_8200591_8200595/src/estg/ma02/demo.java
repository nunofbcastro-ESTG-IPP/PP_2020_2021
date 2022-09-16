/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */
package estg.ma02;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import estg.i.Inputs;
import estg.ma02.core.City;
import estg.ma02.date.DateManagement;
import estg.ma02.io.ExportChartStatistics;
import estg.ma02.io.Importer;
import java.io.IOException;
import java.time.LocalDateTime;

public class demo {
    
    private static final String NODATA="No data";

    /**
     * Imports data from a JSON file to a specific city object
     *
     * @param city The {@link ICity city} where it will be added
     * @return An instance of {@link IOStatistics}
     */
    private static IOStatistics importDataCity(City city) {
        IOStatistics OStatistics = null;
        IImporter importer = new Importer();
        String nameFile = Inputs.readString("Enter the file 1:");
        try {
            OStatistics = importer.importData(city, nameFile);
            System.out.println("Data imported successfully");
        } catch (CityException | IOException e) {
            System.out.println(e.getMessage());
        }
        return OStatistics;
    }

    /**
     * Prints the IOStatistics of the file that was imported
     *
     * @param oStatistics The {@link IOStatistics oStatistics} where it will be
     * added
     */
    private static void printIOStatistics(IOStatistics oStatistics) {
        if (oStatistics == null) {
            System.out.println("Without Statistics");
        } else {
            System.out.println(oStatistics.toString());
        }
    }

    /**
     * Exports the measurements existent in a station
     *
     * @param city The {@link ICity city} where the station is located
     */
    private static void exportMeasurementsByStation(City city) {
        AggregationOperator operator = Inputs.readOperator("Choose the operator: ");
        Parameter parameter = Inputs.readParameter("Choose the parameter: ");

        IStatistics[] statistics = city.getMeasurementsByStation(operator, parameter);

        if (statistics != null && statistics.length > 0) {
            ExportChartStatistics exportChartStatistics = new ExportChartStatistics();
            exportChartStatistics.exportStatisticsToBarChart(statistics, "Measurements By Station");
            System.out.println(exportChartStatistics.export());
        } else {
            System.out.println(NODATA);
        }
    }

    /**
     * Exports the measurements existent in a station between a start and end
     * date
     *
     * @param city The {@link ICity city} where the station is located
     */
    private static void exportMeasurementsByStationBetweenDates(City city) {
        LocalDateTime dateStart = Inputs.readDate("Date start (day/mounth/year hours:minutes)");
        LocalDateTime dateEnd = Inputs.readDate("Date end (day/mounth/year hours:minutes)");

        if (DateManagement.checkDates(dateStart, dateEnd)) {
            AggregationOperator operator = Inputs.readOperator("Choose the operator: ");
            Parameter parameter = Inputs.readParameter("Choose the parameter: ");

            IStatistics[] statistics = city.getMeasurementsByStation(operator, parameter, dateStart, dateEnd);

            if (statistics != null && statistics.length > 0) {
                ExportChartStatistics exportChartStatistics = new ExportChartStatistics();
                exportChartStatistics.exportStatisticsToBarChart(statistics, "Measurements By Station");
                System.out.println(exportChartStatistics.export());
            } else {
                System.out.println(NODATA);
            }
        } else {
            System.out.println("Invalid dates");
        }

    }

    /**
     * Lists all the stations available in a specific {@link ICity city} and
     * reads one of them
     *
     * @param city The {@link ICity city} to get all of its stations
     * @param message The message that will be written when reading from the
     * buffer
     * @return The station name
     */
    private static String readStations(City city, String message) {
        boolean error = true;
        int stationPos = -1;
        if (city.getStations().length > 0) {
            for (int i = 0; i < city.getStations().length; i++) {
                System.out.println(i + 1 + " " + city.getStations()[i].getName());
            }
            do {
                stationPos = Inputs.readInt(message);
                if (stationPos > 0 && stationPos <= city.getStations().length) {
                    error = false;
                }

            } while (error);
        } else {
            System.out.println("No stations available");
        }

        return city.getStations()[stationPos - 1].getName();

    }

    /**
     * Exports the measurements existent in a sensor
     *
     * @param city The {@link ICity city} where the sensor is located
     */
    private static void exportMeasurementsBySensor(City city) {
        AggregationOperator operator = Inputs.readOperator("Choose the operator: ");
        Parameter parameter = Inputs.readParameter("Choose the parameter: ");
        String stationName = readStations(city, "Choose the station: ");

        IStatistics[] statistics = city.getMeasurementsBySensor(stationName, operator, parameter);

        if (statistics != null && statistics.length > 0) {
            ExportChartStatistics exportChartStatistics = new ExportChartStatistics();
            exportChartStatistics.exportStatisticsToBarChart(statistics, "Measurements By Station");
            System.out.println(exportChartStatistics.export());
        } else {
            System.out.println(NODATA);
        }
    }

    /**
     * Exports the measurements existent in a sensor between a start and end
     * date
     *
     * @param city The {@link ICity city} where the sensor is located
     */
    private static void exportMeasurementsBySensorBetweenDates(City city) {
        LocalDateTime dateStart = Inputs.readDate("Date start (day/mounth/year hours:minutes)");
        LocalDateTime dateEnd = Inputs.readDate("Date end (day/mounth/year hours:minutes)");

        if (DateManagement.checkDates(dateStart, dateEnd)) {

            String stationName = readStations(city, "Choose the station: ");

            AggregationOperator operator = Inputs.readOperator("Choose the operator: ");
            Parameter parameter = Inputs.readParameter("Choose the parameter: ");
            IStatistics[] statistics = city.getMeasurementsBySensor(stationName, operator, parameter, dateStart, dateEnd);

            if (statistics != null && statistics.length > 0) {
                ExportChartStatistics exportChartStatistics = new ExportChartStatistics();
                exportChartStatistics.exportStatisticsToBarChart(statistics, "Measurements By Station");
                System.out.println(exportChartStatistics.export());
            } else {
                System.out.println(NODATA);
            }
        } else {
            System.out.println("Invalid dates");
        }

    }

    /**
     * Function that contains a menu with all the available exports
     *
     * @param city The {@link ICity city} where the data will be exported
     */
    private static void exportData(City city) {
        if (city.getStations().length > 0) {
            int option;
            boolean error = false;
            do {
                System.out.println("================================================");
                System.out.println("|               MENU EXPORT DATA               |");
                System.out.println("================================================");
                System.out.println("| Options:                                     |");
                System.out.println("|   1. Measurements by Station                 |");
                System.out.println("|   2. Measurements by Station between dates   |");
                System.out.println("|   3. Measurements by Sensor                  |");
                System.out.println("|   4. Measurements by Sensor between dates    |");
                System.out.println("|   0. Voltar                                  |");
                System.out.println("================================================");
                option = Inputs.readInt("Enter an option:");
                switch (option) {
                    case 0:
                        break;
                    case 1:
                        exportMeasurementsByStation(city);
                        break;
                    case 2:
                        exportMeasurementsByStationBetweenDates(city);
                        break;
                    case 3:
                        exportMeasurementsBySensor(city);
                        break;
                    case 4:
                        exportMeasurementsBySensorBetweenDates(city);
                        break;
                    default:
                        error = true;
                        break;
                }
            } while (error);
        } else {
            System.out.println(NODATA);
        }

    }

    /**
     * Creates a menu
     */
    private static void menu() {
        City city = new City(Inputs.readString("City ID: "), Inputs.readString("Name of the city: "));
        IOStatistics OStatistics = null;
        int option;
        do {
            System.out.println("============================");
            System.out.println("|           MENU           |");
            System.out.println("============================");
            System.out.println("| Options:                 |");
            System.out.println("|   1. Import data         |");
            System.out.println("|   2. Statistics Import   |");
            System.out.println("|   3. Export data         |");
            System.out.println("|   4. Print data City     |");
            System.out.println("|   0. Exit                |");
            System.out.println("============================");
            option = Inputs.readInt("Enter an option:");
            switch (option) {
                case 0:
                    break;
                case 1:
                    OStatistics = importDataCity(city);
                    Inputs.pause();
                    break;
                case 2:
                    printIOStatistics(OStatistics);
                    Inputs.pause();
                    break;
                case 3:
                    exportData(city);
                    Inputs.pause();
                    break;
                case 4:
                    System.out.println(city.toString());
                    Inputs.pause();
                    break;
                default:
                    System.out.println("Sorry, please enter valid Option");
                    break;

            }
        } while (option != 0);
        System.out.println("Thank you, come back often.");
    }

    public static void main(String[] args) {
        menu();
    }

}
