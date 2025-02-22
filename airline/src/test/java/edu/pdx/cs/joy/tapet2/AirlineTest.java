package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for the {@link Airline} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class AirlineTest
{
    private Airline testAirline;

  @BeforeEach
  void setUp()
  {
    testAirline = new Airline("TestAir");
  }

  @Test
  void defaultConstructor()
  {
    Airline emptyAirline = new Airline();
    assertThat(emptyAirline.getName(), is(""));
    assertThat(emptyAirline.getFlights().isEmpty(), is(true));
  }

  @Test
  void addFlightCreatesAFlightAndGetFlightsRetrievesProperly()
  {
    testAirline.addFlight(new Flight(133, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM"));
    assertThat(FlightTest.replaceNonBreakingSpaces(testAirline.getFlights().get(0).getFlightText())
                    , is("133 PDX 9/8/25, 6:00 AM ARN 9/8/25, 7:00 PM"));
  }

  /*@Test
  void getNewFlightReturnsLastAddedFlight()
  {
    testAirline.addFlight(new Flight(100, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM"));
    testAirline.addFlight(new Flight(200, "LAX", "09/08/2025 06:00 AM", "SFO", "09/08/2025 08:00 AM"));
    assertThat(testAirline.getNewFlightTxt(), is("200 LAX 09/08/2025 06:00\u202FAM SFO 09/08/2025 08:00\u202FAM"));
  }

  /*@Test
  void getFlightsTestOutputAndFormat()
  {
    testAirline.addFlight(new Flight(100, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM"));
    testAirline.addFlight(new Flight(200, "LAX", "09/08/2025 06:00 AM", "SFO", "09/08/2025 08:00 AM"));
    assertThat(testAirline.getFlightsText(), is("\n100 PDX 09/08/2025 06:00\u202FAM ARN 09/08/2025 07:00\u202FPM"
                                                + "\n200 LAX 09/08/2025 06:00\u202FAM SFO 09/08/2025 08:00\u202FAM"));
  }*/

  @Test
  void getPrettyTextOutputAndFormat()
  {
    testAirline.addFlight(new Flight(100, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM"));
    assertThat(FlightTest.replaceNonBreakingSpaces(testAirline.getPrettyText()), is("Airline: " + "TestAir"
                                              + "\nFlight Number:       " + "100"
                                              + "\nDeparture Airport:   " + "Portland, OR"
                                              + "\nDeparture Time:      " + "9/8/25, 6:00 AM"
                                              + "\nDestination Airport: " + "Stockholm , Sweden"
                                              + "\nDestination Time:    " + "9/8/25, 7:00 PM"
                                              + "\nDuration:            " + "780min"
                                              + "\n"));
  }

  /*@Test
  void flightsSortedWhenCalled()
  {
    testAirline.addFlight(new Flight(100, "PDX", "09/08/2025 06:00\u202FAM", "ARN", "09/08/2025 07:00\u202FPM"));
    testAirline.addFlight(new Flight(200, "LAX", "09/08/2025 06:00\u202FAM", "SFO", "09/08/2025 08:00\u202FAM"));
    testAirline.sortFlights();
    assertThat(testAirline.getFlightsText(), is("\n200 LAX 09/08/2025 06:00\u202FAM SFO 09/08/2025 08:00\u202FAM"
                                                + "\n100 PDX 09/08/2025 06:00\u202FAM ARN 09/08/2025 07:00\u202FPM"));
  }*/
}
