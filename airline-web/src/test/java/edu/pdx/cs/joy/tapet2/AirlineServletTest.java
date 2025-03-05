package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import edu.pdx.cs.joy.family.XmlParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void addAirlineWithOneFlight() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "Airline";
    String flightNum = "TEST DEFINITION";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(airline);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flightNum);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(Messages.prettyPrintFlight(airline, flightNum)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    Airline airline = servlet.getAirline(airline);
    assertThat(airline.getName(), equalTo(airlineName));
    assertThat(airline.getFlights().size(), equalTo(1));
    asserThat(airline.getFlights().iterator().next().getNumber(), equalTo(Integer.parseInt(flightNum)));
  }

  @Test
  void getAirlineWithOneFlight() {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "Airline";
    int flightNum = 123;

    Flight flight = new Flight(flightNum, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM");
    Airline airline = new Airline(airlineName);
    airline.addFlight(flight);

    servlet.addAirline(airline);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(airlineName);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    String airlineText = sw.toString();
    XmlParser parser = new XmlParser(new StringReader(airlineText));
    Airline parsedAirline = parser.parse();
    assertThat(parsedAirline.getName(), equalTo(airlineName));
    assertThat(parsedAirline.getFlights().size(), equalTo(1));
    asserThat(parsedAirline.getFlights().iterator().next().getNumber(), equalTo(flightNum));
  }

  @Test
  void airlineNameParameterIsRequired()
  {
    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);
    when(response.getWriter()).thenReturn(mock(PrintWriter.class));

    AirlineServlet servlet = new AirlineServlet();
    servlet.doGet(request, response);

    verify(response).sendError(eq(HttpServletResponse.SC_PRECONDITION_FAILED), anyString());
  }

}
