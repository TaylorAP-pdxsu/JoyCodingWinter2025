package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;

import java.io.*;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.tapet2.Airline;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class Project4 {
    
    private static boolean readMeFlag = false;
    private static boolean printFlag = false;
    private static int filePathLoc;
    private static boolean textFileFound = false;
    private static int prettyPathLoc;
    private static boolean prettyFileFound = false;
    private static String[] flightArgs;
    private static boolean xmlFileFound = false;
    private static int xmlPathLoc;

    private static void checkArgsOpts(String[] args)
    {
        if(args.length == 0)
        {
            throw new IllegalArgumentException("ERROR: No command line args given.");
        }
        Vector<String> holdStr = new Vector<String>();
        for(int i = 0, j = 0; i < args.length; ++i)
        {
            if(args[i].charAt(0) == '-')
            {
                if(args[i].equals("-README"))
                {
                    readMeFlag = true;
                    return;
                }
                else if(args[i].equals("-print")) printFlag = true;
                else if(args[i].equals("-textFile") && textFileFound == false)
                {
                    textFileFound = true;
                    filePathLoc = i+1;
                    ++i;
                }
                else if(args[i].equals("-pretty"))
                {
                    prettyFileFound = true;
                    prettyPathLoc = i+1;
                    ++i;
                }
                else if(args[i].equals("-xmlFile"))
                {
                    xmlFileFound = true;
                    xmlPathLoc = i+1;
                    ++i;
                }
                else
                {
                    throw new IllegalArgumentException("\nERROR: Unknown command line option"
                                                        + "\nOccured at argument " + i
                                                        + "\nError Value: " + args[i]
                                                        + "\nValid options include: "
                                                        + "-README, -print, -textFile, -prettyFile, -xmlFile");
                }
                if(textFileFound == true && xmlFileFound == true)
                    throw new IllegalArgumentException("ERROR: Cannot use both -textFile and -xmlFile options"
                                                        + "\nOccured at argument " + i
                                                        + "\nError Value: " + args[i]);
            }
            else
            {
                holdStr.add(args[i]);
                ++j;
            }
        }
        flightArgs = holdStr.toArray(new String[holdStr.size()]);
    }

    public static void main(String[] args)
    {
        //check for opts
        try {
            checkArgsOpts(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage() + "\n" + helpMsg());
            return;
        }

        //do README
        if(readMeFlag == true)
        {
            try {
                InputStream stream = Project4.class.getClassLoader().getResourceAsStream("edu/pdx/cs/joy/tapet2/README.txt");
                Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name());
                System.out.println("\n" + scanner.useDelimiter("\\A").next() + "\n");
                scanner.close();
            } catch (NullPointerException e) {
                System.err.println("ERROR: README path not found.");
            }
            return;
        }
        //do textfile parsing
        Airline airline;
        TextParser txtParser;
        airline = new Airline(flightArgs[0]);
        txtParser = new TextParser(null);
        if(textFileFound && !xmlFileFound)
        {
            try {
                txtParser = new TextParser(new FileReader(new File(args[filePathLoc])));
                airline = txtParser.parse();
            } catch (FileNotFoundException e) {
                System.err.println("NOTE: Input file not found..."
                                    + "\nContinuing to read from command line with initialized null parser..."
                                    + "\nCreating new file...");
                //txtParser = new TextParser(null);
            } catch (ParserException e) {
                System.err.println("\nERROR: Parser exception..." + "\n--CAUSE-- " + e.getMessage() + "\n");
                return;
            }
        }

        //xml parsing/dumping
        if(xmlFileFound && !textFileFound)
        {
            try {
                XmlParser xmlParser = new XmlParser(args[xmlPathLoc]);
                airline = xmlParser.parse();
            } catch (ParserException e) {
                System.err.println("\nERROR: XML Parser exception..." + "\n--CAUSE--\n" + e.getMessage());
                return;
            }
        }

        //command line flight
        try {
            if(airline.getName().equals(flightArgs[0]))
            {
                FlightParser flightParser = new FlightParser();
                airline.addFlight(flightParser.parseFlight(Arrays.copyOfRange(flightArgs, 1, flightArgs.length)));
            }
            else
            {
                System.err.println("ERROR: Given airline name from command line does not match already existing output file."
                                 + "\nFIX: Check that airline name on command line and input file matches the output file.");
                return;
            }
        } catch (ParserException e) {
            System.err.println("\nERROR: Text file Parser exception..." + "\n--CAUSE-- " + e.getMessage() + "\n");
            return;
        }

        //create airline and text output file
        if(textFileFound && !xmlFileFound)
        {
            try {
                File file = new File(args[filePathLoc]);
                TextDumper txtDump;
                if(file.exists())
                {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    if(reader.readLine().equals(airline.getName()))
                    {
                        txtDump = new TextDumper(new FileWriter(file.getPath()));
                        txtDump.dump(airline);
                    }
                    else
                    {
                        System.err.println("ERROR: Given airline name from input file does not match already existing output file."
                                        + "\nFIX: Check that airline name on command line and input file matches the output file.");
                        reader.close();
                        return;
                    }
                    reader.close();
                }
                else
                {
                    txtDump = new TextDumper(new PrintWriter(file));
                    txtDump.dump(airline);
                }
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: Output file not found...");
            } catch (IOException e) {
                System.err.println("ERROR: IOException on dump append() function.");
            }
        }

        if(xmlFileFound && !textFileFound)
        {
            //add exception handling?
            XmlDumper xmlDumper = new XmlDumper(args[xmlPathLoc]);
            xmlDumper.dump(airline);
        }

        //Pretty option
        if(prettyFileFound)
        {
            if(args[prettyPathLoc].equals("-"))
            {
                System.out.println(airline.getPrettyText());
            }
            else
            {
                try
                {
                    File prettyFile = new File(args[prettyPathLoc]);
                    PrettyPrinter prettyPrint;
                    if(prettyFile.exists())
                    {
                        prettyPrint = new PrettyPrinter(new FileWriter(prettyFile.getPath()));
                        prettyPrint.dump(airline);
                    }
                    else
                    { //this else is currently irrelevant
                        prettyPrint = new PrettyPrinter(new PrintWriter(prettyFile));
                        prettyPrint.dump(airline);
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("ERROR: Output file not found...");
                } catch (IOException e) {
                    System.err.println("ERROR: IOException on dump append() function.");
                }
            }
        }
    

        //output if -print
        if(printFlag == true)
        {
            System.out.println("\n" + airline.getName() + "\n" + airline.getNewFlightTxt());
        }
    }

    private static String helpMsg()
    {
        return "--HELP--"
             + "\nPlease enter airline and flight information in format:"
             + "\n(AIRLINE NAME) (FLIGHT NUMBER) (DEPARTURE AIRPORT CODE)"
             + " (DEPARTURE DATE) (TIME) (ARRIVAL AIRPORT CODE) (ARRIVAL DATE) (TIME)"
             + "\n\nDate should be formatted as MM/DD/YYYY"
             + "\nTime should be formatted as HR:MM";
    }

}
