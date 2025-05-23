package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.InvokeMainTestCase;
import edu.pdx.cs.joy.UncaughtExceptionInMain;
import edu.pdx.cs.joy.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test0RemoveAllAirlines() throws IOException {
      AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllAirlines();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("ERROR: No command line args given."));
    }

    /*@Test
    void test3UnknownAirlineThrowsAppointmentBookRestException() {
        String airline = "Airline";
        try {
            invokeMain(Project5.class, HOSTNAME, PORT, airline);
            fail("Should have thrown a RestException");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    void test4AddFlight() {
        String airline = "Airline";
        String flightNum = "123";

        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT, airline, flightNum );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.prettyPrintFlight(airline, flightNum)));

        result = invokeMain( Project5.class, HOSTNAME, PORT, airline );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        PrettyPrinter prettyPrinter = new PrettyPrinter(new StringWriter());

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(prettyPrinter.dump(airline)));
    }*/
}