package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the Airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  static boolean isValidArgs(String[] args) {
    if(args.length == 0)
    {
      System.err.println("ERROR: No argmuents were entered...");
      return false;
    }
    if(args.length > 8)
    {
      System.err.println("Incorrect number of arguments given...");
      return false;
    }
    if(!args[2].matches("\\d+"))
    {
      System.err.println("ERROR: Flight number is not an integer...");
      return false;
    }
    if(args[3].length() > 3 || args[3].matches("[a-zA-Z]"))
    {
      System.err.println("Departure Airport Code is too long or contains number, please enter 3-letter code...");
      return false;
    }
    if(args[5].length() > 3 || args[5].matches("a-zA-Z"))
    {
      System.err.println("Arrival Airport Code is too long or contains number, please enter 3-letter code...");
      return false;
    }
    //create validation for date/time in project 2
    return true;
  }

  public static void main(String[] args) {
    if(!isValidArgs(args))
    {
      System.err.println("\nPlease enter airline and flight information in format:"
                        + "\n(AIRLINE NAME) (FLIGHT NUMBER) (DEPARTURE AIRPORT CODE)"
                        + " (DEPARTURE DATE & TIME) (ARRIVAL AIRPORT CODE) (ARRIVAL DATE & TIME)"
                        + "\n\nDate should be formatted as MM/DD/YYYY"
                        + "\nTime should be formatted as HR:MM");
    }
    else
    {
      Flight flight = new Flight(Integer.parseInt(args[2]), args[3], args[4], args[5], args[6]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      
      Airline airline = new Airline(args[0]);
      //Will change Airline to have more proper input methodology in future
      airline.getFlights().add(flight);
      System.out.println(flight.toString());
    }
    /*for (String arg : args) {
      System.out.println(arg);
    }*/
  }

}