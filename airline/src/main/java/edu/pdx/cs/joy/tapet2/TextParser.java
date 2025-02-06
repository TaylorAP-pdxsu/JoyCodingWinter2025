package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineParser;
import edu.pdx.cs.joy.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;
  private final int maxOpts = 2;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

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
        lineNum++;
        String[] args = lineParse.split(" ");
        
        //check arg num
        if(args.length != 7) throw new ParserException("Incorrect number of arguments in input file at line " + lineNum);
        //check flight num
        if(!args[0].matches("\\d+"))
          throw new ParserException(createExceptStr("Flight number is not an integer.", "1", args[0]
                                                           , "Input must be a positive integer."));
        //check departure code
        if(args[1].length() != 3)
          throw new ParserException(createExceptStr("Incorrect departure code length", "2", args[1]
                                                           , "Departure code must be 3 alphabetical letters."));
        if(args[1].matches("[a-zA-Z]"))
          throw new ParserException(createExceptStr("Non-alphabetical character found in departure code.", "2", args[1]
                                                          ,  "Departure code must be 3 alphabetical letters."));
        //check departure date/time
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime dtTemporal;
        String departureDTFormatted;
        try {
          dtTemporal = LocalDateTime.parse(args[2] + " " + args[3], dtFormatter);
          departureDTFormatted = dtTemporal.format(dtFormatter);
        } catch (DateTimeParseException e) {
          throw new ParserException(createExceptStr(e.getMessage() + "\n\nDate and/or Time given in unreadable format.", "3 and 4", args[2] + " " + args[3]
                                                           , "Date and Time must be in format: MM/DD/YYYY HH:MM"));
        }
        //check arrival code
        if(args[4].length() != 3)
          throw new ParserException(createExceptStr("Incorrect departure code length", "4", args[4]
                                                           , "Departure code must be 3 alphabetical letters."));
        if(args[4].matches("[a-zA-Z]"))
          throw new ParserException(createExceptStr("Non-alphabetical character found in departure code.", "4", args[4]
                                                          ,  "Departure code must be 3 alphabetical letters."));
        //check departure date/time
        String arrivalDTFormatted;
        try {
          dtTemporal = LocalDateTime.parse(args[5] + " " + args[6], dtFormatter);
          arrivalDTFormatted = dtTemporal.format(dtFormatter);
        } catch (DateTimeParseException e) {
          throw new ParserException(createExceptStr(e.getMessage() + "\n\nDate and/or Time given in unreadable format.", "6 and 7", args[5] + " " + args[6]
                                                           , "Date and Time must be in format: MM/DD/YYYY HH:MM"));
        }

        //Add parsed and validated flight to airline
        newAirline.addFlight(new Flight(Integer.parseInt(args[0]), args[1], departureDTFormatted, args[4], arrivalDTFormatted));

      }

      return newAirline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }

  private String createExceptStr(String error, String occured, String value, String note)
  {
    return "\nERROR: " + error
         + "\nOccured at argument: " + occured
         + "\nError Value: " + value
         + "\nNOTE: " + note;
  }
}
