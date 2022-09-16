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

import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IExporter;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExportChartSensorsMeasurement implements IExporter {

    /**
     * The ExportChartSensorsMeasurement sensor
     */
    private ISensor sensor = null;
    /**
     * The ExportChartSensorsMeasurement title
     */
    private String title;
    
    /**
     * Creates an instance of <code>ExportChartSensorsMeasurement</code>
     * @param sensor The {@link ExportChartSensorsMeasurement#sensor sensor}
     * @param title The {@link ExportChartSensorsMeasurement#title title}
     */
    public ExportChartSensorsMeasurement(ISensor sensor, String title) {
        this.sensor = sensor;
        this.title = title;
    }
    
    /**
     * Empty constructor
     */
    public ExportChartSensorsMeasurement() {
    }

    /**
     * Exports the sensor to a line chart
     * @param sensor
     * @param title 
     */
    public void exportSensorToLineChart(ISensor sensor, String title) {
        this.sensor = sensor;
        this.title = title;

        String[] vis = {export()};
        Dashboard.render(vis);
    }

    @Override
    public String export() {
        if (sensor != null) {
            JSONObject obj = new JSONObject();
            obj.put("type", "line");
            JSONObject data = new JSONObject();
            JSONObject datasets = new JSONObject();
            JSONArray datasetsArray = new JSONArray();
            JSONArray datasets_data = new JSONArray();

            datasets.put("label", "Teste");

            for (IMeasurement Measurement : sensor.getMeasurements()) {
                if (Measurement != null) {
                    JSONObject values = new JSONObject();
                    values.put("x", Measurement.getTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy, HH:mm")));
                    values.put("y", Measurement.getValue());
                    datasets_data.add(values);
                }
            }

            datasets.put("data", datasets_data);
            datasetsArray.add(datasets);
            data.put("datasets", datasetsArray);
            obj.put("data", data);

            JSONObject options = new JSONObject();
            JSONObject title = new JSONObject();
            JSONObject scales = new JSONObject();
            JSONArray xAxes = new JSONArray();
            JSONObject xAxesObject = new JSONObject();
            JSONObject xAxesTime = new JSONObject();
            JSONObject xAxesTimeDisplayFormats = new JSONObject();

            title.put("display", true);
            title.put("text", this.title);
            title.put("fontSize", 20);
            options.put("title", title);

            xAxesObject.put("type", "time");
            xAxesTime.put("parser", "MM/DD/YYYY, HH:mm");
            xAxesTimeDisplayFormats.put("day", "DD MMM YYYY");
            xAxesTime.put("displayFormats", xAxesTimeDisplayFormats);
            xAxesObject.put("time", xAxesTime);
            xAxes.add(xAxesObject);
            scales.put("xAxes", xAxes);

            options.put("scales", scales);

            obj.put("options", options);
            return obj.toJSONString();
        }
        return "";
    }

}
