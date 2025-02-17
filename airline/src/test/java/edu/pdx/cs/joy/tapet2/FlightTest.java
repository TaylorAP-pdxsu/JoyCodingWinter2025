package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  private Flight testFlight;

  @BeforeEach
  void setUp()
  {
    testFlight = new Flight(133, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM");
  }

  @Test
  void returnedValuesEqualConstructedValues()
  {
    assertThat(testFlight.getNumber(), is(133));
    assertThat(testFlight.getSource(), is("PDX"));
    assertThat(testFlight.getDepartureString(), is("9/8/25, 6:00\u202FAM"));
    assertThat(testFlight.getDestination(), is("ARN"));
    assertThat(testFlight.getArrivalString(), is("9/8/25, 7:00\u202FPM"));
  }

  @Test
  void getFlightTextisCorrect() 
  {
    assertThat(testFlight.getFlightText(), is("133 PDX 09/08/2025 06:00 AM ARN 09/08/2025 07:00 PM"));
  }

  @Test
  void flightDurationCorrect()
  {
    assertThat(testFlight.getDuration(), is("780"));
  }

  @Test
  void compareToFlightAreTheSame()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM");
    assertThat(testFlight.compareTo(sameFlight), is(0));
  }

  @Test
  void compareToTestFlightIsLessThanWhenDiffAirport()
  {
    Flight sameFlight = new Flight(133, "LAX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM");
    assertThat(testFlight.compareTo(sameFlight), is(1));
  }

  @Test
  void compareToTestFlightIsMoreThanWhenDiffAirport()
  {
    Flight sameFlight = new Flight(133, "SFO", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:00 PM");
    assertThat(testFlight.compareTo(sameFlight), is(-1));
  }

  @Test
  void compareToTestFlightIsLessThanWhenSameAirport()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 05:00 AM", "ARN", "09/08/2025 07:00 PM");
    assertThat(testFlight.compareTo(sameFlight), is(1));
  }

  @Test
  void compareToTestFlightIsMoreThanWhenSameAirport()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 07:00 AM", "ARN", "09/08/2025 07:00 PM");
    assertThat(testFlight.compareTo(sameFlight), is(-1));
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  
  /*@Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
  }

  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }*/
}
