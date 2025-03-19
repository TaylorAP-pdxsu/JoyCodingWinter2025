package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineParser;
import edu.pdx.cs.joy.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * TextParser which takes in input or command line args and parses for correct info
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;
  private FlightParser flightParser;

  public TextParser(Reader reader) {
    this.reader = reader;
    flightParser = new FlightParser();
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
        String[] args = lineParse.replaceAll(",", "").replaceAll("\u202F", " ").split(" ");
        //i-2 and i-6 args
        if(args[2].matches("[0-9]*/[0-9]*/[0-9][0-9]"))
        {
          args[2] = args[2].substring(0, args[2].length()-2) 
                    + "20" + args[2].substring(args[2].length()-2, args[2].length());
        }
        if(args[6].matches("[0-9]*/[0-9]*/[0-9][0-9]"))
        {
          args[6] = args[6].substring(0, args[6].length()-2) 
                    + "20" + args[6].substring(args[6].length()-2, args[6].length());
        }
        
        //check arg num
        if(args.length != 9) throw new ParserException("Incorrect number of arguments in input file at line " + lineNum);
        
        newAirline.addFlight(flightParser.parseFlight(args));
      }

      return newAirline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
