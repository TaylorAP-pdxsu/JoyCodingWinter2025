package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractFlight;

/**
 * <p>
 * The Flight class contains the information for a single flight.
 * Members include; flight number, departure airport, departure time,
 *                  destination airport, and arrival time.
 * </p>
 */
public class Flight extends AbstractFlight {
  private int flightNum; //test comment
  private String sourceAirport;
  private String departTime;
  private String destAirport;
  private String arrivalTime;

  /**
   * Default constructor
   */
  public Flight() {}

  /**
   * Argument constructor. Sets all members for Flight
   * @param flightNum flight number
   * @param sourceAirport departure airport
   * @param departTime departure time
   * @param destAirport destination airport
   * @param arrivalTime arrival time
   */
  public Flight(int flightNum,
                String sourceAirport, String departTime,
                String destAirport, String arrivalTime)
  {
    this.flightNum = flightNum;
    this.sourceAirport = sourceAirport;
    this.departTime = departTime;
    this.destAirport = destAirport;
    this.arrivalTime = arrivalTime;
  }

  @Override
  public int getNumber() {
    return flightNum;
  }

  @Override
  public String getSource() {
    return sourceAirport;
  }

  //ignore
  @Override
  public String getDepartureString() {
    return departTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() {
    return destAirport;
  }

  //ignore
  @Override
  public String getArrivalString() {
    return arrivalTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  public String getFlightText()
  {
    return new String(
      "Flight Number: " + flightNum
      + "\nDeparture Airport: " + sourceAirport
      + "\nDeparture Time: " + departTime
      + "\nDestination Airport: " + destAirport
      + "\nDestination Time: " + arrivalTime
    );
  }
}
