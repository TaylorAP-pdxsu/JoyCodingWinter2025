package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractFlight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * <p>
 * The Flight class contains the information for a single flight.
 * Members include; flight number, departure airport, departure time,
 *                  destination airport, and arrival time.
 * </p>
 */
public class Flight extends AbstractFlight implements Comparable<Flight>  {
  private int flightNum; //test comment
  private String sourceAirport;
  private String departTime;
  private String destAirport;
  private String arrivalTime;
  private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");


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
    LocalDateTime dtTemporal = LocalDateTime.parse(departTime);
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.valueOf("SHORT")).withLocale(Locale.US);
    return dtTemporal.format(dtFormat);
  }

  @Override
  public String getDestination() {
    return destAirport;
  }

  //ignore
  @Override
  public String getArrivalString() {
    dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    dtFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.valueOf("SHORT"));
    return LocalDateTime.parse(arrivalTime).format(dtFormat);
  }

  public String getFlightText()
  {
    return new String(flightNum + " " + sourceAirport + " " + departTime + " " 
                        + destAirport + " " + arrivalTime);
  }

  @Override
  public int compareTo(Flight other)
  {
    return 0;
  }
}
