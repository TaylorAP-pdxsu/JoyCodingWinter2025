package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Outputs string to be writtern to a file.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {
    try (PrintWriter pw = new PrintWriter(writer)) {
        pw.println(airline.getName() + airline.getFlightsText());

        pw.flush();
      }
      
  }

  /**
   * appends to end of file
   * @param airline
   */
  public void append(Airline airline) {
    try (PrintWriter pw = new PrintWriter(writer)) {
      pw.println(airline.getFlightsText());

      pw.flush();
    }
  }
}
