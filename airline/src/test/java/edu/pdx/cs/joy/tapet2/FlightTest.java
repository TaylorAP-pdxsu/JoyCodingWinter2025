package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  @Test
  void returnedValuesEqualConstructedValues() {
    Flight testFlight = new Flight(13, "PDX", "9/8/2025 6:00", "ARN", "9/8/2025 19:00");
    assertThat(testFlight.getNumber(), is(13));
    assertThat(testFlight.getSource(), is("PDX"));
    assertThat(testFlight.getDepartureString(), is("9/8/2025 6:00"));
    assertThat(testFlight.getDestination(), is("ARN"));
    assertThat(testFlight.getArrivalString(), is("9/8/2025 19:00"));
    
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  
  /*@Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
  }*/

  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }
}
