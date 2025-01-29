package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractAirline;

import java.util.*;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private Collection<Flight> flights;

  public Airline(String name) {
    this.name = name;
    flights = new ArrayList<Flight>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }
}
