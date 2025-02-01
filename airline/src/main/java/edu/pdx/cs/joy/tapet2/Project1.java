package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main class for the Airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  static int isValidArgs(String[] args) {
    //no args given
    int lengthOfArgs = args.length;
    if(lengthOfArgs == 0)
    {
      throw new IllegalArgumentException("ERROR: Missing command line arguments...");
    }

    //validate given args
    int startOfInfo = 0;
    int i;
    for(i = 0; args[i].charAt(0) == '-' && i < lengthOfArgs-1; ++i)
    {
      if(!args[i].equals("-print") && !args[i].equals("-README"))
      {
        System.err.println("\nERROR: Unknown command line option..."
                          + "\nValid options include: -print and -README");
        throw new IllegalArgumentException("ERROR occured at argument " + (startOfInfo+i) + " with value => " + args[i]);
      }
    }

    if(startOfInfo == args.length-1)
      return startOfInfo;

    startOfInfo = i;

    if(args.length-1 < startOfInfo + 7)
    {
      throw new IllegalArgumentException("ERROR: Incorrect number of arguments given...");
    }
    if(!args[startOfInfo+1].matches("\\d+"))
    {
      System.err.println("\nERROR: Flight number is not an integer...");
      throw new IllegalArgumentException("ERROR occured at argument " + (startOfInfo+1)
                                        + " with value => " + args[startOfInfo+1]);
    }
    if(args[startOfInfo+2].length() != 3 || args[startOfInfo+2].matches("[a-zA-Z]"))
    {
      System.err.println("\nERROR: Departure Airport Code is of incorrect length or contains number, please enter 3-letter code...");
      throw new IllegalArgumentException("ERROR occured at argument " + (startOfInfo+2)
                                        + " with value => " + args[startOfInfo+2]);
    }
    if(args[startOfInfo+5].length() != 3 || args[startOfInfo+5].matches("a-zA-Z"))
    {
      System.err.println("\nERROR: Arrival Airport Code is of incorrect length or contains number, please enter 3-letter code...");
      throw new IllegalArgumentException("ERROR occured at argument " + (startOfInfo+5)
                                        + " with value => " + args[startOfInfo+5]);
    }
    //create validation for date/time in project 2
    return startOfInfo;
  }

  /**
   * main launches our parser validation and creates a simple airline.
   * It creates a Flight to add to the Airline object and then handles
   * the options given by the user.
   * @param args takes in input from the command line to be parsed.
   */
  public static void main(String[] args) {
    int startOfInfo;
    try {
      startOfInfo = isValidArgs(args);
    } catch (IllegalArgumentException e) {
      System.err.println(e);
      System.err.println("\nPlease enter airline and flight information in format:"
                        + "\n(AIRLINE NAME) (FLIGHT NUMBER) (DEPARTURE AIRPORT CODE)"
                        + " (DEPARTURE DATE) (TIME) (ARRIVAL AIRPORT CODE) (ARRIVAL DATE) (TIME)"
                        + "\n\nDate should be formatted as MM/DD/YYYY"
                        + "\nTime should be formatted as HR:MM");
      return;
    }
    
    Airline airline;
    Flight flight;

    if(startOfInfo != args.length-1)
    {
      airline = new Airline(args[startOfInfo]);
      flight = new Flight(Integer.parseInt(args[startOfInfo+1]),
                                  args[startOfInfo+2],
                                  args[startOfInfo+3] + args[startOfInfo+4],
                                  args[startOfInfo+5],
                                  args[startOfInfo+6] + args[startOfInfo+7]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //Will change Airline to have more proper input methodology in future
      airline.getFlights().add(flight);

      if(args[0].equals("-print") || args[1].equals("-print"))
        System.out.println(flight.toString());
    }
    
    if(args[0].equals("-README") || args[1].equals("-README"))
    {
      try {
        String resourcePath = Project1.class.getClassLoader().getResource("../resources/README.txt").getPath();
        Path filePath = Paths.get(resourcePath, "README.txt");
        File readme = new File(filePath.toString());
        if(!readme.exists())
        {
          readme.createNewFile();
        }
        String readMeString = "Taylor A. Pettingill"
                              + "\nProject 1"
                              + "\nThis is a project for the class The Joy of Coding with Java."
                              + "\nThis project contains a Airline and Flight class and currently"
                              + "\ntakes in input from the command line and outputs its information."
                              + "\nCommand line formatting:"
                              + "\n(AIRLINE NAME) (FLIGHT NUMBER) (DEPARTURE AIRPORT CODE)"
                              + " (DEPARTURE DATE & TIME) (ARRIVAL AIRPORT CODE) (ARRIVAL DATE & TIME)";
        Files.write(filePath, readMeString.getBytes());
      } catch (IOException e) {
        System.err.println("README not properly read or output...");
      }
    }
    /*for (String arg : args) {
      System.out.println(arg);
    }*/
  }
}