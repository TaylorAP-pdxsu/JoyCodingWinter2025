package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractAirline;
import edu.pdx.cs.joy.AirportNames;

import java.util.*;

/**
 * Airline class which contains the name of the airline and
 * a collection of Flight objects.
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> flights;

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
  public ArrayList<Flight> getFlights() {
    return flights;
  }

  public String getFlightsText()
  {
    String output = new String();
    for(Flight flight : flights)
    {
      output += "\n" + flight.getFlightText();
    }
    return output;
  }

  public String getNewFlightTxt()
  {
    return flights.get(flights.size() - 1).getFlightText();
  }

  public String getPrettyText()
  {
    String prettyText = new String("Airline: " + name);
    for(Flight flight: flights)
    {
        prettyText += "\nFlight Number:       " + flight.getNumber();
        prettyText += "\nDeparture Airport:   " + AirportNames.getName(flight.getSource());
        prettyText += "\nDeparture Time:      " + flight.getDepartureString().replaceAll("\u202F", " ");
        prettyText += "\nDestination Airport: " + AirportNames.getName(flight.getDestination());
        prettyText += "\nDestination Time:    " + flight.getArrivalString().replaceAll("\u202F", " ");;
        prettyText += "\nDuration:            " + flight.getDuration() + "min";
        prettyText += "\n";
    }
    return prettyText;
  }

  public void sortFlights()
  {
    Collections.sort(flights);
  }
}
