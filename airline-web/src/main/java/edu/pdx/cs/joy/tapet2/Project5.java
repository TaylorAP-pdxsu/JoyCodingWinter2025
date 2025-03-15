package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String[] args) {
        CommandLine cmds = new CommandLine();
        try {
            cmds.parseArgs(args);
        } catch (IllegalArgumentException e) {
            usage(e.getMessage());
        }

        String hostName = (cmds.hostNameLoc == -1 ? null : args[cmds.hostNameLoc]);
        String portString = (cmds.portLoc == -1 ? null : args[cmds.portLoc]);
        String airlineName = cmds.airlineName;

        Airline airline = null;
        Flight flight = null;

        if (hostName == null) {
            usage( MISSING_ARGS );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message;
        try {
            if (airlineName == null) {
                System.err.println("missing airline");
                return;

            } else if (cmds.searchAirlineLoc != -1) {
                // Pretty print the airline
                if(cmds.searchSrcLoc != -1 && cmds.searchDestLoc != -1)
                    airline = client.getDirectFlights(args[cmds.searchAirlineLoc], args[cmds.searchSrcLoc], args[cmds.searchDestLoc]);
                else
                    airline = client.getAirline(airlineName);
                    
                StringWriter sw = new StringWriter();
                new PrettyPrinter(sw).dump(airline);
                message = sw.toString();

            } else {
                // Post the airline/flight
                FlightParser flightParser = new FlightParser();
                flight = flightParser.parseFlight(cmds.flightArgs);
                client.addFlight(airlineName, flight);
                message = Messages.prettyPrintFlight(airlineName, String.valueOf(flight.getNumber()));
            }
            if(cmds.printFlag == true)
            {
                airline = client.getAirline(airlineName);
                if(airline == null)
                {
                    System.out.println("Airline is null...");
                }
                else
                {
                    System.out.println(("\n" + airline.getName() + "\n" + airline.getNewFlightTxt()).replaceAll("\u202F", " "));
                }
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        }

        System.out.println(message);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port airline [flightNumber]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  airline      Name of airline");
        err.println("  flightNum    Flight Number");
        err.println();
        err.println();
    }
}