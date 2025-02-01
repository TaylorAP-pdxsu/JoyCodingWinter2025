package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  void printOptionPrintsFlightToStandardOut() {
    MainMethodResult result = 
                      invokeMain("-print", "Icelandair", "13", "PDX", "9/8/2025", "6:00", "ARN", "9/8/2025", "19:00");
    String expectedString = "Flight 13 departs PDX at 9/8/2025 6:00 arrives ARN at 9/8/2025 19:00";
    assertThat(result.getTextWrittenToStandardOut(), containsString(expectedString));
  }

}