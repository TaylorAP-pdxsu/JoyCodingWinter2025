package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {
    try (PrintWriter pw = new PrintWriter(writer)) {
        pw.println("Airline: " + airline.getName() + airline.getFlightsText());

        pw.flush();
      }
      
  }

  public void append(Airline airline) {
    try (PrintWriter pw = new PrintWriter(writer)) {
      pw.println(airline.getFlightsText());

      pw.flush();
    }
  }
}
