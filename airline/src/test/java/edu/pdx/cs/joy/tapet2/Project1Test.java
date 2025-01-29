package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

//Added by Taylor P
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.*;


/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }

  //Testing Airline class

  @Test
  void getNameReturnsConstructedName() throws IOException
  {
    Airline testAirline = new Airline("Icelandair");
    assertThat(testAirline.getName(), is("Icelandair"));
  }

  @Test
  void addSingleFlightToCollection() throws IOException
  {
    Airline testAirline = new Airline("Icelandair");
    Flight testFlight = new Flight(13, "PDX", "9/8/2025 06:00", "ARN", "9/8/2025 19:00");
    Collection<Flight> compCollection = new ArrayList<Flight>() {{ add(testFlight); }};
    
    testAirline.addFlight(testFlight);
    assertThat(testAirline.getFlights().equals(compCollection), is(true));
  }

}
