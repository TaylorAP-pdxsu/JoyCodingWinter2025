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

  static String replaceNonBreakingSpaces(String string) {
    return string.replaceAll("\u202F", " ");
  }

  @BeforeEach
  void setUp()
  {
    testFlight = new Flight(133, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM");
  }

  @Test
  void returnedValuesEqualConstructedValues()
  {
    assertThat(testFlight.getNumber(), is(133));
    assertThat(testFlight.getSource(), is("PDX"));
    assertThat(replaceNonBreakingSpaces(testFlight.getDepartureString()), is("9/8/25, 6:00 AM"));
    assertThat(testFlight.getDestination(), is("ARN"));
    assertThat(replaceNonBreakingSpaces(testFlight.getArrivalString()), is("9/8/25, 7:31 PM"));
  }

  @Test
  void individualDateValuesReturnProperly()
  {
    assertThat(testFlight.getDepartDay(), is("8"));
    assertThat(testFlight.getDepartMonth(), is("9"));
    assertThat(testFlight.getDepartYear(), is("2025"));
    assertThat(testFlight.getDepartHour(), is("6"));
    assertThat(testFlight.getDepartMinute(), is("00"));

    assertThat(testFlight.getArrivalDay(), is("8"));
    assertThat(testFlight.getArrivalMonth(), is("9"));
    assertThat(testFlight.getArrivalYear(), is("2025"));
    assertThat(testFlight.getArrivalHour(), is("19"));
    assertThat(testFlight.getArrivalMinute(), is("31"));
  }

  @Test
  void getFlightTextisCorrect() 
  {
    assertThat(replaceNonBreakingSpaces(testFlight.getFlightText()), is("133 PDX 9/8/25, 6:00 AM ARN 9/8/25, 7:31 PM"));
  }

  @Test
  void flightDurationCorrect()
  {
    assertThat(testFlight.getDuration(), is("811"));
  }

  @Test
  void compareToFlightAreTheSame()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM");
    assertThat(testFlight.compareTo(sameFlight), is(0));
  }

  @Test
  void compareToTestFlightIsLessThanWhenDiffAirport()
  {
    Flight sameFlight = new Flight(133, "LAX", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM");
    assertThat(testFlight.compareTo(sameFlight), is(1));
  }

  @Test
  void compareToTestFlightIsMoreThanWhenDiffAirport()
  {
    Flight sameFlight = new Flight(133, "SFO", "09/08/2025 06:00 AM", "ARN", "09/08/2025 07:31 PM");
    assertThat(testFlight.compareTo(sameFlight), is(-1));
  }

  @Test
  void compareToTestFlightIsLessThanWhenSameAirport()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 05:00 AM", "ARN", "09/08/2025 07:31 PM");
    assertThat(testFlight.compareTo(sameFlight), is(1));
  }

  @Test
  void compareToTestFlightIsMoreThanWhenSameAirport()
  {
    Flight sameFlight = new Flight(133, "PDX", "09/08/2025 07:00 AM", "ARN", "09/08/2025 07:31 PM");
    assertThat(testFlight.compareTo(sameFlight), is(-1));
  }
}
