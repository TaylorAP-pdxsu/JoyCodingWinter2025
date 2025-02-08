package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractAirline;

import java.util.*;

/**
 * Airline class which contains the name of the airline and
 * a collection of Flight objects.
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private Collection<Flight> flights;

  /**
   * Default constructor
   */
  public Airline() {
    name = "";
    flights = new ArrayList<Flight>();
  }

  /**
   * Argument constructor
   * @param name name of the airline
   */
  public Airline(String name) {
    this.name = name;
    flights = new ArrayList<Flight>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a Flight object to the flight collection
   * @param flight the flight to be added to flights collection.
   */
  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }

  public String getFlightsText()
  {
    //String output = new String("Airline: " + name);
    String output = new String();
    for(Flight flight : flights)
    {
      output += "\n\n" + flight.getFlightText();
    }
    return output;
  }
}
