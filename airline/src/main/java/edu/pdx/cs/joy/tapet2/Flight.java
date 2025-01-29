package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractFlight;

public class Flight extends AbstractFlight {
  private int flightNum;
  private String sourceAirport;
  private String departTime;
  private String destAirport;
  private String arrivalTime;

  public Flight() {}

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
}
