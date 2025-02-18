package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AbstractFlight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.time.Duration;

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
  private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
  private DateTimeFormatter shortFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.valueOf("SHORT")).withLocale(Locale.US);


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

  @Override
  public String getDepartureString() {
    LocalDateTime dtTemporal = LocalDateTime.parse(departTime, dtFormat);
    return dtTemporal.format(shortFormat);
  }

  public String getDepartDay() {
    return String.valueOf(LocalDateTime.parse(departTime, dtFormat).getDayOfMonth());
  }

  public String getDepartMonth() {
    return String.valueOf(LocalDateTime.parse(departTime, dtFormat).getMonth().getValue());
  }

  public String getDepartYear() {
    return String.valueOf(LocalDateTime.parse(departTime, dtFormat).getYear());
  }

  public String getDepartHour() {
    return String.valueOf(LocalDateTime.parse(departTime, dtFormat).getHour());
  }

  public String getDepartMinute() {
    return String.valueOf(LocalDateTime.parse(departTime, dtFormat).getMinute());
  }

  @Override
  public String getDestination() {
    return destAirport;
  }

  @Override
  public String getArrivalString() {    
    LocalDateTime dtTemporal = LocalDateTime.parse(arrivalTime, dtFormat);
    return dtTemporal.format(shortFormat);
  }

  public String getArrivalDay() {
    return String.valueOf(LocalDateTime.parse(arrivalTime, dtFormat).getDayOfMonth());
  }

  public String getArrivalMonth() {
    return String.valueOf(LocalDateTime.parse(arrivalTime, dtFormat).getMonth().getValue());
  }

  public String getArrivalYear() {
    return String.valueOf(LocalDateTime.parse(arrivalTime, dtFormat).getYear());
  }

  public String getArrivalHour() {
    return String.valueOf(LocalDateTime.parse(arrivalTime, dtFormat).getHour());
  }

  public String getArrivalMinute() {
    return String.valueOf(LocalDateTime.parse(arrivalTime, dtFormat).getMinute());
  }

  public String getFlightText()
  {
    return new String(flightNum + " " + sourceAirport + " " + departTime + " " 
                        + destAirport + " " + arrivalTime);
  }

  public String getDuration() {
    LocalDateTime departAt = LocalDateTime.parse(departTime, dtFormat);
    LocalDateTime arriveAt = LocalDateTime.parse(arrivalTime, dtFormat);
    long duration = Duration.between(departAt, arriveAt).getSeconds();
    return String.valueOf(duration/60);
  }

  @Override
  public int compareTo(Flight other)
  {
    int compAirport = sourceAirport.compareTo(other.sourceAirport);
    //same airport
    if(compAirport < 0) return -1;
    if(compAirport > 0) return 1;

    //neither above conditions trigger, then we are the same airport
    int compDepartTime = LocalDateTime.parse(departTime, dtFormat).compareTo(LocalDateTime.parse(other.departTime, dtFormat));
    //compare departure times
    if(compDepartTime < 0)  return -1;
    if(compDepartTime > 0)  return 1;
    return 0;
  }
}
