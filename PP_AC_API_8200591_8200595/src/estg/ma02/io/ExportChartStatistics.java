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

import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IExporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExportChartStatistics implements IExporter {
    /**
     * The ExportChartStatistics statistics
     */
    private IStatistics[] statistics;
    /**
     * The ExportChartStatistics title
     */
    private String title;
    
    /**
     * Creates an instance of <code>ExportChartStatistics</code>
     * @param statistics The {@link ExportChartStatistics#statistics statistics}
     * @param title The {@link ExportChartStatistics#title title}
     */
    public ExportChartStatistics(IStatistics[] statistics, String title) {
        this.statistics = statistics;
        this.title = title;
    }
    
    /**
     * Empty constructor
     */
    public ExportChartStatistics() {
    }

    /**
     * Generater a string rgb color
     * @return a string rgb color
     */
    private String randomColor() {
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);

        return "rgb(" + R + " " + G + " " + B + ")";
    }

    /**
     * Exports the statistics to a bar chart
     * @param statistics The {@link ExportChartStatistics#statistics statistics} to be exported
     * @param title The {@link ExportChartStatistics#title title} of the chart
     */
    public void exportStatisticsToBarChart(IStatistics[] statistics, String title) {
        this.statistics = statistics;
        this.title = title;

        String[] vis = {export()};
        Dashboard.render(vis);
    }

    @Override
    public String export() {
        if (statistics.length > 0) {
            JSONObject obj = new JSONObject();

            obj.put("type", "bar");

            JSONArray labels = new JSONArray();
            JSONArray color = new JSONArray();
            JSONArray datasets_data = new JSONArray();
            JSONObject data = new JSONObject();
            JSONObject datasets = new JSONObject();
            JSONArray datasetsArray = new JSONArray();

            for (IStatistics statistic : this.statistics) {
                if (statistic != null) {
                    labels.add(statistic.getDescription());
                    color.add(randomColor());
                    datasets_data.add(statistic.getValue());
                }
            }

            data.put("labels", labels);
            datasets.put("data", datasets_data);
            datasets.put("backgroundColor", color);
            datasetsArray.add(datasets);
            data.put("datasets", datasetsArray);
            obj.put("data", data);

            JSONObject options = new JSONObject();
            JSONObject legend = new JSONObject();
            JSONObject title = new JSONObject();

            legend.put("display", false);
            options.put("legend", legend);

            title.put("display", true);
            title.put("text", this.title);
            title.put("fontSize", 20);
            options.put("title", title);

            obj.put("options", options);
            return obj.toJSONString();
        }
        return "";
    }

}
