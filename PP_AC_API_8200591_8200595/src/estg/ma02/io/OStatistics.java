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
import edu.ma02.io.interfaces.IOStatistics;

public class OStatistics implements IOStatistics{
    
    private final int numberOfReadMeasurements;
    private final int numberOfStationsRead;
    private final int numberOfNewStationsRead;
    private final int numberOfSensorsRead;
    private final int numberOfNewSensorsRead;
    private final int numberOfNewMeasurementsRead;
    private final String[] exceptions;

    /**
     * Creates an instance of <code>OStatistics</code>
     * @param numberOfReadMeasurements {@link OStatistics#numberOfReadMeasurements numberOfReadMeasurements}
     * @param numberOfStationsRead {@link OStatistics#numberOfStationsRead numberOfStationsRead}
     * @param numberOfNewStationsRead {@link OStatistics#numberOfNewStationsRead numberOfNewStationsRead}
     * @param numberOfSensorsRead {@link OStatistics#numberOfSensorsRead numberOfSensorsRead}
     * @param numberOfNewSensorsRead {@link OStatistics#numberOfNewSensorsRead numberOfNewSensorsRead}
     * @param numberOfNewMeasurementsRead {@link OStatistics#numberOfNewMeasurementsRead numberOfNewMeasurementsRead}
     * @param exceptions {@link OStatistics#exceptions exceptions}
     */
    public OStatistics(int numberOfReadMeasurements, int numberOfStationsRead, int numberOfNewStationsRead, int numberOfSensorsRead, int numberOfNewSensorsRead, int numberOfNewMeasurementsRead, String[] exceptions) {
        this.numberOfReadMeasurements = numberOfReadMeasurements;
        this.numberOfStationsRead = numberOfStationsRead;
        this.numberOfNewStationsRead = numberOfNewStationsRead;
        this.numberOfSensorsRead = numberOfSensorsRead;
        this.numberOfNewSensorsRead = numberOfNewSensorsRead;
        this.numberOfNewMeasurementsRead = numberOfNewMeasurementsRead;
        this.exceptions = exceptions;
    }

    @Override
    public int getNumberOfReadMeasurements() {
        return this.numberOfReadMeasurements;
    }

    @Override
    public int getNumberOfNewStationsRead() {
        return this.numberOfNewStationsRead;
    }

    @Override
    public int getNumberOfStationsRead() {
        return this.numberOfStationsRead;
    }

    @Override
    public int getNumberOfSensorsRead() {
        return this.numberOfSensorsRead;
    }

    @Override
    public int getNumberOfNewSensorsRead() {
        return this.numberOfNewSensorsRead;
    }

    @Override
    public int getNumberOfNewMeasurementsRead() {
        return this.numberOfNewMeasurementsRead;
    }

    @Override
    public String[] getExceptions() {
        return this.exceptions;
    }

    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read
     * 
     * @return  A string representation of the object (<code>OStatistics</code>)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number Of Read Measurements : ").append(numberOfReadMeasurements).append("\n");
        sb.append("Number Of Stations Read : ").append(numberOfStationsRead).append("\n");
        sb.append("Number Of New Stations Read : ").append(numberOfNewStationsRead).append("\n");
        sb.append("Number Of Sensors Read : ").append(numberOfSensorsRead).append("\n");
        sb.append("Number Of NewSensors Read : ").append(numberOfNewSensorsRead).append("\n");
        sb.append("Number Of New Measurements Read : ").append(numberOfNewMeasurementsRead).append("\n");
        for(int i=0; i<exceptions.length; i++){
            sb.append("Exceptions ").append(i+1).append(" : ").append(exceptions[i]).append("\n");
        }
        return sb.toString();
    }
    
}
