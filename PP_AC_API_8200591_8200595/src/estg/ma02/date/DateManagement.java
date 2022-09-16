/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */
package estg.ma02.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

public class DateManagement {

    /**
     * The DateManagement DATE_FORMAT1
     */
    public static String DATE_FORMAT1="yyyyMMddHHmm";
    /**
     * The DateManagement DATE_FORMAT2
     */
    public static String DATE_FORMAT2="dd/MM/yyyy HH:mm";
    /**
     * The DateManagement DateFormat
     */
    private final String DateFormat;

    /**
     * Creates an instance of <code>DateManagement</code>
     * @param DateFormat {@link DateManagement#DateFormat DateFormat}
     */
    public DateManagement(String DateFormat) {
        this.DateFormat = DateFormat;
    }

    /**
     * Checks if a date is valid
     * @param date The string date to be validated
     * @return True if the date is valid, otherwise false
     */
    private boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    /**
     * Converts a string date to a datetime date
     * @param date The string date
     * @return The date converted into datetime
     * @throws DateManagementException if the date is invalid
     */
    public LocalDateTime getDateTime(String date) throws DateManagementException {
        LocalDateTime dateTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormat);

        if (!isDateValid(date)) {
            throw new DateManagementException("Invalid date");
        }

        try {
            dateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DateManagementException("Invalid date or time");
        }

        return dateTime;
    }

    /**
     * Converts a datetime date to a string formatted date
     * @param dateTime - The datetime to be converted to a string 
     * @return The date in a string format
     */
    public static String dateToString(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }
    
    /**
     * Verify if the startDate is before the endDate
     * @param startDate The startDate which will be used for verification.
     * @param endDate The endDate which will be used for verification.
     * @return True if the the startDate is before the endDate false otherwise
     */
    public static boolean checkDates(LocalDateTime startDate, LocalDateTime endDate) {
        return endDate.isAfter(startDate);
    }

}
