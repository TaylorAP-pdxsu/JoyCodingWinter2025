package edu.pdx.cs.joy.tapet2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.text.html.HTMLEditorKit.Parser;

import edu.pdx.cs.joy.AirportNames;
import edu.pdx.cs.joy.ParserException;

public class FlightParserTest {
    
    private FlightParser testParser;

    @BeforeEach
    void setUp()
    {
        testParser = new FlightParser();
    }

    @Test
    void tooManyArgs()
    {
        String flight = "123 PDX 08/09/2025 12:00 AM ARN 08/09/2025 6:00 PM ZZZZ";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void flightNumNotInt()
    {
        String flight = "ZZZ PDX 08/09/2025 12:00 AM ARN 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void depCodeIncorrectLen()
    {
        String flight = "123 PDXZ 08/09/2025 12:00 AM ARN 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void depCodeNonIntFound()
    {
        String flight = "123 P9X 08/09/2025 12:00 AM ARN 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
                testParser.parseFlight(args);
        });
    }

    @Test
    void depAirportCodeNonExistent()
    {
        String flight = "123 ZZZ 08/09/2025 12:00 AM ARN 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void depDateTimeUnreadableFormat()
    {
        String flight = "123 PDX 08/09Z/2025 12:00 AM ARN 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void arrivCodeIncorrectLen()
    {
        String flight = "123 PDX 08/09/2025 12:00 AM ARNZ 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void arrivCodeNonIntFound()
    {
        String flight = "123 PDX 08/09/2025 12:00 AM A4N 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void arrivAirportCodeNonExistent()
    {
        String flight = "123 PDX 08/09/2025 12:00 AM ZZZ 08/09/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void arrivDateTimeUnreadableFormat()
    {
        String flight = "123 PDX 08/09/2025 12:00 AM ARN 08/09Z/2025 6:00 PM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }

    @Test
    void arrivDateBeforeDepartDate()
    {
        String flight = "123 PDX 08/09/2025 7:00 AM ARN 08/09/2025 6:00 AM";
        String[] args = flight.split(" ");
        assertThrows(ParserException.class, () -> {
            testParser.parseFlight(args);
        });
    }
}
