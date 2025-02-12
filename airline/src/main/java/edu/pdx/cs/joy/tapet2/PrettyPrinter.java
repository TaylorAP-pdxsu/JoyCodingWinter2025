package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;


import javax.print.attribute.standard.PrinterInfo;

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        try(PrintWriter pw = new PrintWriter(writer)) {
            ArrayList<Flight> flights = airline.getFlights();
            pw.println(airline.getName());
            Flight flight = flights.get(flights.size()-1);
            pw.println(flight.getNumber());
            pw.println(flight.getSource());
            pw.println(flight.getDepartureString());
            pw.println(flight.getDestination());
            pw.println(flight.getArrivalString());


            pw.flush();
        }
    }
}
