package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        try(PrintWriter pw = new PrintWriter(writer)) {
            pw.println(airline.getPrettyText());

            pw.flush();
        }
    }
}
