/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */
package pp_ac_api_8200591_8200595;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import estg.ma02.core.City;
import estg.ma02.io.ExportChartStatistics;
import estg.ma02.io.Importer;
import java.io.IOException;

public class PP_AC_API_8200591_8200595 {

    public static void main(String[] args) { 
        
        IImporter importer = new Importer();
        City city = new City("123123", "Santa Maria de Feira");

        try {
            IOStatistics statistics = importer.importData(city, "C:\\Users\\Utilizador\\Documents\\GitHub\\Trabalho-PP-2021\\PP_AC_8200591_8200595\\files\\sensorData.json");
            System.out.println("Statistics");
            System.out.println(statistics.toString());
        } catch (CityException | IOException e) {
            System.out.println(e.getMessage());
        }

        for (IStation station : city.getStations()) {
            System.out.println(station.getName());
        }

        IStatistics[] statistics = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);
        ExportChartStatistics exportChartStatistics = new ExportChartStatistics();
        exportChartStatistics.exportStatisticsToBarChart(statistics, "Measurements By Station");

    }

}
