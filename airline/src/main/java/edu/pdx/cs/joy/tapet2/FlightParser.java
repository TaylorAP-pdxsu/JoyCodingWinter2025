package edu.pdx.cs.joy.tapet2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import edu.pdx.cs.joy.AirportNames;
import edu.pdx.cs.joy.ParserException;

public class FlightParser {

    public FlightParser() {}
    
    /**
     * parses a flight from the command line and inputs them into airline object.
     * @throws ParserException if any inputs are invalid
     */
    public Flight parseFlight(String[] args) throws ParserException
    {
        if(args.length > 9)
        throw new ParserException(createExceptStr("Unknown args given after flight information.", ">9", args[7]
                                                        , "Cannot contain more than 9 args after airline name."));
        //check flight num
        if(!args[0].matches("\\d+"))
        throw new ParserException(createExceptStr("Flight number is not an integer.", "0", args[0]
                                                        , "Input must be a positive integer."));
        //check departure code
        if(args[1].length() != 3)
        throw new ParserException(createExceptStr("Incorrect departure code length", "1", args[1]
                                                        , "Departure code must be 3 alphabetical letters."));
        if(!args[1].matches("[^0-9][^0-9][^0-9]"))
        throw new ParserException(createExceptStr("Non-alphabetical character found in departure code.", "1", args[1]
                                                        ,  "Departure code must be 3 alphabetical letters."));

        if(AirportNames.getName(args[1]) == null)
        throw new ParserException(createExceptStr("Airport code does not exist.", "1", args[1]
                                                        , "Departure code must be of an existing airport"));
        //check departure date/time
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        LocalDateTime departTemporal;
        String departureDTFormatted;
        try {
            departTemporal = LocalDateTime.parse(args[2] + " " + args[3] + " " + args[4].toUpperCase(), dtFormatter);
            departureDTFormatted = departTemporal.format(dtFormatter);
        } catch (DateTimeParseException e) {
        throw new ParserException(createExceptStr(e.getMessage() + "\n\nDate and/or Time given in unreadable format.", "2, 3, and 4"
                                                        , args[2] + " " + formatTime(args[3]) + " " + args[4]
                                                        , "Date and Time must be in format: MM/DD/YYYY HH:MM AA"));
        }
        //check arrival code
        if(args[5].length() != 3)
        throw new ParserException(createExceptStr("Incorrect departure code length", "5", args[5]
                                                        , "Departure code must be 3 alphabetical letters."));
        if(!args[5].matches("[^0-9][^0-9][^0-9]"))
        throw new ParserException(createExceptStr("Non-alphabetical character found in departure code.", "5", args[5]
                                                        ,  "Departure code must be 3 alphabetical letters."));

        if(AirportNames.getName(args[5]) == null)
        throw new ParserException(createExceptStr("Airport code does not exist.", "5", args[5]
                                                        , "Arrival code must be of an existing airport"));
        //check departure date/time
        LocalDateTime arrivalTemporal;
        String arrivalDTFormatted;
        try {
            arrivalTemporal = LocalDateTime.parse(args[6] + " " + args[7] + " " + args[8].toUpperCase(), dtFormatter);
            arrivalDTFormatted = arrivalTemporal.format(dtFormatter);
        } catch (DateTimeParseException e) {
        throw new ParserException(createExceptStr(e.getMessage() + "\n\nDate and/or Time given in unreadable format.", "6, 7, and 8"
                                                        , args[6] + " " + formatTime(args[7]) + " " + args[8]
                                                        , "Date and Time must be in format: MM/DD/YYYY HH:MM AA"));
        }

        if(arrivalTemporal.compareTo(departTemporal) < 0)
        throw new ParserException(createExceptStr("Arrival time is before departure time", "2, 3, 4, 6, 7, and 8"
                                                        , "departure: " + args[2] + " " + args[3] + " " + args[4] 
                                                            + " | arrival: " + args[6] + " " + args[7] + " " + args[8]
                                                        , "No time traveling allowed!"));
        //Add parsed and validated flight to airline
        return new Flight(Integer.parseInt(args[0]), args[1].toUpperCase(), departureDTFormatted, args[5].toUpperCase(), arrivalDTFormatted);
    }

    private String formatTime(String time)
    {
        return time.matches("[0-9]:[0-9][0-9]") ? "0" + time : time;
    }

    /**
     * Creates a string for exceptions in the TextParser class.
     * @param error The error which cause the throw
     * @param occured What index the error occured at in args
     * @param value The value of what caused the error
     * @param note Helpful hint or instructions on how to fix error
     * @return returns a String to be output
     */
    private String createExceptStr(String error, String occured, String value, String note)
    {
        return "\nERROR: " + error
            + "\nOccured at argument: " + occured
            + "\nError Value: " + value
            + "\nNOTE: " + note;
    }
}
