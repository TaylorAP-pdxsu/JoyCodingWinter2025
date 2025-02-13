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
  void returnedValuesEqualConstructedValues() {
    assertThat(testFlight.getNumber(), is(133));
    assertThat(testFlight.getSource(), is("PDX"));
    assertEquals(testFlight.getDepartureString(), "9/8/25, 6:00\u202FAM");
    assertThat(testFlight.getDestination(), is("ARN"));
    assertThat(testFlight.getArrivalString(), is("9/8/25, 7:00\u202FPM"));
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
