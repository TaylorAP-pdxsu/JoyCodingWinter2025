package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 */
public class AirlineRestClientTest {

  @Test
  void getAirlineReturnsExpectedFlight() throws ParserException, IOException {
    String airlineName = "Airline Name";
    Airline airline = new Airline(airlineName);
    int flightNum = 133;
    airline.addFlight(new Flight(flightNum, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM"));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(AirlineServlet.AIRLINE_PARAMETER, airlineName)))).thenReturn(airlineAsText(airline));
    
    AirlineRestClient client = new AirlineRestClient(http);

    Airline airline1 = client.getAirline(airlineName);
    assertThat(airline1.getName(), equalTo(airlineName));
    assertThat(airline1.getFlights().size(), equalTo(1));
    assertThat(airline1.getFlights().iterator().next().getNumber(), equalTo(flightNum));
  }

  private HttpRequestHelper.Response airlineAsText(Airline airline) {
    StringWriter writer = new StringWriter();
    new TextDumper(writer).dump(airline);

    return new HttpRequestHelper.Response(writer.toString());
  }
}
