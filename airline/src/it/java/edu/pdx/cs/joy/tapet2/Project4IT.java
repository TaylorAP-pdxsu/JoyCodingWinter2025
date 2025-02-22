package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.InvokeMainTestCase;
import edu.pdx.cs.joy.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Project4IT extends InvokeMainTestCase {

  @Test
  void test7StartingANewXmlFile(@TempDir File tempDir) throws ParserException {
    File tapet2 = new File(tempDir, "tapet2");
    tapet2.mkdir();

    File xmlFile = new File(tapet2, "tapet2.xml");
    String airlineName = "Project4";
    String commandLine = "-xmlFile " + xmlFile + " " + airlineName + " 700 CLE 01/07/2025 7:00 AM EGE 01/17/2025 7:00 PM";
    MainMethodResult result = invokeMain(Project4.class, commandLine.split(" "));

    assertThat(result.getTextWrittenToStandardError(), xmlFile.exists(), equalTo(true));
    XmlParser parser = new XmlParser(xmlFile.getPath());
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo(airlineName));
  }
}
