package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;

import edu.pdx.cs.joy.web.HttpRequestHelper.Response;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_PARAMETER = "airlineName";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNum";
  static final String SRC_AIRPORT = "srcAirport";
  static final String SRC_DATE_TIME = "srcDateTime";
  static final String DEST_AIRPORT = "destAirport";
  static final String DEST_DATE_TIME = "destDateTime";
  static final String SRC = "srcSearch";
  static final String DEST = "destSearch";

  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter( AIRLINE_PARAMETER, request );
      String srcSearch = getParameter(SRC, request);
      String destSearch = getParameter(DEST, request);
      log("airline: " + airlineName + " src: " + srcSearch + " dest: " + destSearch);
      if(airlineName != null && srcSearch != null && destSearch != null)
      {
        log("GET " + "flights with " + airlineName + " from " + srcSearch + " to " + destSearch);
        writeDirectFlights(airlineName, srcSearch, destSearch, response);
      }
      else if (airlineName != null) {
          log("GET " + airlineName);
          writeAirline(airlineName, response);

      } else {
          missingRequiredParameter(response, AIRLINE_PARAMETER);
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_PARAMETER, request );
      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_PARAMETER);
          return;
      }

      String flightNumStr = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      if ( flightNumStr == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER );
          return;
      }

      String srcAirport = getParameter(SRC_AIRPORT, request);
      if(srcAirport == null) {
        missingRequiredParameter(response, SRC_AIRPORT);
        return;
      }

      String srcDateTime = getParameter(SRC_DATE_TIME, request);
      if(srcDateTime == null) {
        missingRequiredParameter(response, SRC_DATE_TIME);
        return;
      }

      String destAirport = getParameter(DEST_AIRPORT, request);
      if(destAirport == null) {
        missingRequiredParameter(response, DEST_AIRPORT);
        return;
      }

      String destDateTime = getParameter(DEST_DATE_TIME, request);
      if(destDateTime == null) {
        missingRequiredParameter(response, DEST_DATE_TIME);
        return;
      }

      log("POST " + airlineName + " -> " + flightNumStr);

      Airline airline = this.airlines.get(airlineName);
      if(airline == null)
      {
        airline = new Airline(airlineName);
      }
      airline.addFlight(new Flight(Integer.parseInt(flightNumStr), srcAirport, srcDateTime, destAirport, destDateTime));

      this.airlines.put(airlineName, airline);

      PrintWriter pw = response.getWriter();
      pw.println(Messages.prettyPrintFlight(airlineName, flightNumStr));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      log("DELETE all dictionary entries");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the definition of the given word to the HTTP response.
   *
   * The text of the message is formatted with {@link XmlDumper}
   */
  private void writeAirline(String airlineName, HttpServletResponse response) throws IOException {
    Airline airline = this.airlines.get(airlineName);

    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    } else {
      PrintWriter pw = response.getWriter();

      XmlDumper dumper = new XmlDumper(pw);
      dumper.dump(airline);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  private void writeDirectFlights(String airlineName
                                  , String src
                                  , String dest
                                  , HttpServletResponse response) throws IOException
  {
    Airline airline = this.airlines.get(airlineName);

    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    } else {
      PrintWriter pw = response.getWriter();

      Airline direct = new Airline(airline.getName());
      direct.setFlights(airline.getFlights()
                              .stream()
                              .filter((Flight flight) -> flight.getSource().equals(src)
                                                                      && flight.getDestination().equals(dest))
                                                                      .collect(Collectors.toCollection(ArrayList<Flight>::new)));

      XmlDumper dumper = new XmlDumper(pw);
      dumper.dump(direct);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the default string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  public Airline getAirline(String airlineName) {
      return this.airlines.get(airlineName);
  }

  @Override
  public void log(String msg) {
    System.out.println(msg);
  }

  @VisibleForTesting
  public void addAirline(Airline airline) {
    this.airlines.put(airline.getName(), airline);
  }

}
