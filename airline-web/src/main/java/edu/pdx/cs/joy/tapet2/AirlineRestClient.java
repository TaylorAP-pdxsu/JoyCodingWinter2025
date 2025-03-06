package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;
import edu.pdx.cs.joy.web.HttpRequestHelper.Response;
import edu.pdx.cs.joy.web.HttpRequestHelper.RestException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs.joy.web.HttpRequestHelper.*;
import static java.net.HttpURLConnection.HTTP_OK;

import edu.pdx.cs.joy.AirportNames;
import java.util.stream.*;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }

  /**
   * Returns the definition for the given word
   */
  public Airline getAirline(String airlineName) throws IOException, ParserException {
    Response response = http.get(Map.of(AirlineServlet.AIRLINE_PARAMETER, airlineName));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();

    XmlParser parser = new XmlParser(new StringReader(content));
    return parser.parse();
  }

  public Map<String, Flight[]> getDirectFlights(String src, String dest) throws IllegalArgumentException
  {
    if(AirportNames.getName(src) == null || AirportNames.getName(dest) == null)
    {
      throw new IllegalArgumentException("Search for depart and/or arrival airports failed..."
                                          + "\nAirport code must be 3 letters and valid, existing, airports...");
    }
    //servlet direct flights
    
  }

  public void addFlight(String airlineName, Flight flight) throws IOException {
    Response response = http.post(Map.of(
                            AirlineServlet.AIRLINE_PARAMETER, airlineName
                          , AirlineServlet.FLIGHT_NUMBER_PARAMETER, String.valueOf(flight.getNumber())
                          , AirlineServlet.SRC_AIRPORT, flight.getSource()
                          , AirlineServlet.SRC_DATE_TIME, flight.getDepartParserFormat()
                          , AirlineServlet.DEST_AIRPORT, flight.getDestination()
                          , AirlineServlet.DEST_DATE_TIME, flight.getArrivalParserFormat()
                          ));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  public void removeAllAirlines() throws IOException {
    Response response = http.delete(Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }

}
