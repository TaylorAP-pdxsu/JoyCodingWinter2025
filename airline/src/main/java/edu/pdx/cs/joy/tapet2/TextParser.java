package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineParser;
import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.AirportNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.text.html.HTMLEditorKit.Parser;

import java.time.LocalDateTime;

/**
 * TextParser which takes in input or command line args and parses for correct info
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * parses a series of flights from an input file and inputs them into airline object.
   * @throws ParserException if any inputs are invalid
   */
  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      if(reader == null)
      {
        throw new ParserException("ERROR: Instance of TextParser created without value, reader is NULL.");
      }
      //handle airline name
      String airlineName = br.readLine();
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      Airline newAirline = new Airline(airlineName);

      String lineParse;
      int lineNum = 1;
      while((lineParse = br.readLine()) != null)
      {
        ++lineNum;
        if(lineParse.isEmpty()) continue;
        String[] args = lineParse.split(" ");
        
        //check arg num
        if(args.length != 9) throw new ParserException("Incorrect number of arguments in input file at line " + lineNum);
        
        newAirline.addFlight(parseFlight(args));
      }

      return newAirline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
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
    if(args[1].matches("[a-zA-Z]"))
      throw new ParserException(createExceptStr("Non-alphabetical character found in departure code.", "1", args[1]
                                                      ,  "Departure code must be 3 alphabetical letters."));

    if(AirportNames.getName(args[1]) == null)
      throw new ParserException(createExceptStr("Airport code does not exist.", "1", args[1]
                                                      , "Departure code must be of an existing airport"));
    //check departure date/time
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
    LocalDateTime departTemporal;
    String departureDTFormatted;
    try {
      departTemporal = LocalDateTime.parse(args[2] + " " + args[3] + " " + args[4].toUpperCase(), dtFormatter);
      departureDTFormatted = departTemporal.format(dtFormatter);
    } catch (DateTimeParseException e) {
      throw new ParserException(createExceptStr(e.getMessage() + "\n\nDate and/or Time given in unreadable format.", "2, 3, and 4"
                                                      , args[2] + " " + args[3] + " " + args[4]
                                                      , "Date and Time must be in format: MM/DD/YYYY HH:MM AA"));
    }
    //check arrival code
    if(args[5].length() != 3)
      throw new ParserException(createExceptStr("Incorrect departure code length", "5", args[5]
                                                       , "Departure code must be 3 alphabetical letters."));
    if(args[5].matches("[a-zA-Z]"))
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
                                                      , args[6] + " " + args[7] + " " + args[8]
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
}
