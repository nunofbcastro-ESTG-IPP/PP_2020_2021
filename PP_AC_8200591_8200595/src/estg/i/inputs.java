/*
* Nome: Gonçalo André Fontes Oliveira
* Número: 8200595
* Turma: LEI1T2
*
* Nome: Nuno de Figueiredo Brito e Castro
* Número: 8200591
* Turma: LEI1T2
 */
package estg.i;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import estg.ma02.date.DateManagement;
import estg.ma02.date.DateManagementException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class Inputs {

    /**
     * The Inputs errorMessage variable
     */
    private static final String errorMessage = "Sorry, please enter valid value";

    /**
     * Reads an Integer from the buffer
     * @param message The message to be written when reading the data
     * @return The integer that was read
     */
    public static int readInt(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int value = 0;
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = Integer.parseInt(reader.readLine());
                error = false;
            } catch (IOException | NumberFormatException e) {
                System.out.print(errorMessage);
            }
        } while (error);

        return value;
    }

    /**
     * Reads a String from the buffer
     * @param message The message to be written when reading the data
     * @return The String that was read
     */
    public static String readString(String message) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
        } catch (Exception e) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.err.println("Ola");
        }
        String value = "";
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = reader.readLine();
                error = false;
            } catch (IOException e) {
                System.out.print(errorMessage);
            }
        } while (error);

        return value;
    }

    /**
     * Reads a double from the buffer
     * @param message The message to be written when reading the data
     * @return The double that was read
     */
    public static double readDouble(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double value = 0;
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = Double.parseDouble(reader.readLine());
                error = false;
            } catch (IOException | NumberFormatException e) {
                System.out.print(errorMessage);
            }
        } while (error);

        return value;
    }

    /**
     * Reads a string from the buffer and converts it to a date
     * @param message The message to be written when reading the data
     * @return The date that was read
     */
    public static LocalDateTime readDate(String message) {
        LocalDateTime dateTime = null;
        DateManagement dateManagement = new DateManagement(DateManagement.DATE_FORMAT2);
        String dateHour;
        boolean error = true;

        do {
            dateHour = readString(message);

            String[] partsDateHour = dateHour.split(" ");
            if (partsDateHour.length == 2) {
                String[] partsDate = partsDateHour[0].split("/");
                String[] partsHour = partsDateHour[1].split(":");
                if (partsDate.length == 3 && partsHour.length == 2) {
                    if (partsDate[0].length() < 2) {
                        partsDate[0] = "0" + partsDate[0];
                    }
                    if (partsDate[1].length() < 2) {
                        partsDate[1] = "0" + partsDate[1];
                    }
                    if (partsHour[0].length() < 2) {
                        partsHour[0] = "0" + partsHour[0];
                    }
                    if (partsHour[1].length() < 2) {
                        partsHour[1] = partsHour[1] + "0";
                    }
                    dateHour = partsDate[0] + "/" + partsDate[1] + "/" + partsDate[2] + " " + partsHour[0] + ":" + partsHour[1];
                    try {
                        dateTime = dateManagement.getDateTime(dateHour);
                        error = false;
                    } catch (DateManagementException e) {
                        System.out.print(errorMessage);
                    }
                } else {
                    System.out.print(errorMessage);
                }
            } else {
                System.out.print(errorMessage);
            }

        } while (error);

        return dateTime;
    }

    /**
     * Reads a string from the buffer and converts it to an AggregationOperator
     * @param message The message to be written when reading the data
     * @return The AggregationOperator that was read
     */
    public static AggregationOperator readOperator(String message) {
        int operator;
        boolean error = true;
        for (int i = 0; i < AggregationOperator.values().length; i++) {
            System.out.println((i + 1) + " " + AggregationOperator.values()[i].name());
        }

        do {
            operator = readInt(message);
            if (operator > 0 && operator <= AggregationOperator.values().length) {
                error = false;
            }
        } while (error);
        return AggregationOperator.values()[operator - 1];
    }

    /**
     * Reads a string from the buffer and converts it to a Parameter
     * @param message The message to be written when reading the data
     * @return The Parameter that was read
     */
    public static Parameter readParameter(String message) {
        int parameter;
        boolean error = true;
        for (int i = 0; i < Parameter.values().length; i++) {
            System.out.println((i + 1) + " " + Parameter.values()[i].name());
        }

        do {
            parameter = readInt(message);
            if (parameter > 0 && parameter <= Parameter.values().length) {
                error = false;
            }
        } while (error);
        return Parameter.values()[parameter - 1];

    }

    /**
     * Prints a string and "pauses the execution" until the user clicks in the enter key
     */
    public static void pause() {
        readString("Press the enter key to continue . . . ");
    }

}
