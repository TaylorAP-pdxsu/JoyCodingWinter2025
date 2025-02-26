package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class AirlineXmlTest {

  @Test
  void canDumpAndParseXml(@TempDir File tempDir) throws ParserException {
    // Project4 700 CLE 01/07/2025 7:00 AM EGE 01/17/2025 7:00 PM
    // Project4 800 EVV 01/08/2025 8:00 AM LFT 01/18/2025 8:00 PM

    Airline airline = new Airline("Project4");
    airline.addFlight(new Flight(700, "CLE", "01/07/2025 7:00 AM", "EGE", "01/17/2025 7:00 PM"));
    airline.addFlight(new Flight(800, "EVV", "01/08/2025 8:00 AM", "LFT", "01/18/2025 8:00 PM"));

    File xmlFile = new File(tempDir, "tapet2.xml");
    XmlDumper dumper = new XmlDumper(xmlFile.getPath());
    dumper.dump(airline);

    XmlParser parser = new XmlParser(new File(xmlFile.getPath()));
    Airline parsedAirline = parser.parse();

    assertThat(parsedAirline.getName(), equalTo(airline.getName()));
    assertThat(parsedAirline.getFlights(), hasSize(airline.getFlights().size()));

    // todo - assert that the flights in airline and parsedAirline are the same
  }
}
