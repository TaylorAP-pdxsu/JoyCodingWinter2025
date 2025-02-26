package edu.pdx.cs.joy.tapet2;

import java.io.*;

import edu.pdx.cs.joy.ParserException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class Project4 {

    private static class CommandLine {
        private boolean readMeFlag = false;
        private boolean printFlag = false;
        private int filePathLoc;
        private boolean textFileFound = false;
        private int prettyPathLoc;
        private boolean prettyFileFound = false;
        private String[] flightArgs;
        private boolean xmlFileFound = false;
        private int xmlPathLoc;
    }

    private static void checkArgsOpts(String[] args, CommandLine commandLine)
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
                    commandLine.readMeFlag = true;
                    return;
                }
                else if(args[i].equals("-print")) commandLine.printFlag = true;
                else if(args[i].equals("-textFile") && commandLine.textFileFound == false)
                {
                    commandLine.textFileFound = true;
                    commandLine.filePathLoc = i+1;
                    ++i;
                }
                else if(args[i].equals("-pretty"))
                {
                    commandLine.prettyFileFound = true;
                    commandLine.prettyPathLoc = i+1;
                    ++i;
                }
                else if(args[i].equals("-xmlFile"))
                {
                    commandLine.xmlFileFound = true;
                    commandLine.xmlPathLoc = i+1;
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
                if(commandLine.textFileFound == true && commandLine.xmlFileFound == true)
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
        commandLine.flightArgs = holdStr.toArray(new String[holdStr.size()]);
    }

    public static void main(String[] args)
    {
        CommandLine commandLine = new CommandLine();

        //check for opts
        try {
            checkArgsOpts(args, commandLine);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage() + "\n" + helpMsg());
            return;
        }


        //do README
        if(commandLine.readMeFlag == true)
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
        airline = new Airline(commandLine.flightArgs[0]);
        txtParser = new TextParser(null);
        if(commandLine.textFileFound && !commandLine.xmlFileFound)
        {
            try {
                txtParser = new TextParser(new FileReader(new File(args[commandLine.filePathLoc])));
                airline = txtParser.parse();
            } catch (FileNotFoundException e) {
                System.err.println("NOTE: Input file not found..."
                                    + "\nContinuing to read from command line with initialized null parser..."
                                    + "\nCreating new file...");
                txtParser = new TextParser(null);
            } catch (ParserException e) {
                System.err.println("\nERROR: Parser exception..." + "\n--CAUSE-- " + e.getMessage() + "\n");
                return;
            }
        }

        //xml parsing/dumping
        if(commandLine.xmlFileFound && !commandLine.textFileFound)
        {
            try {
                XmlParser xmlParser = new XmlParser(new File(commandLine.flightArgs[commandLine.xmlPathLoc]));
                airline = xmlParser.parse();
            } catch(NullPointerException e) {
                System.err.println("NOTE: Input XML file not found..."
                                    + "\nContinuing to read from command line with initialized null parser..."
                                    + "\nCreating new XML file...");
            } catch (ParserException e) {
                System.err.println("\nERROR: XML Parser exception..." + "\n--CAUSE--\n" + e.getMessage());
                return;
            }
        }

        //command line flight
        try {
            if(airline.getName().equals(commandLine.flightArgs[0]))
            {
                FlightParser flightParser = new FlightParser();
                airline.addFlight(flightParser.parseFlight(Arrays.copyOfRange(commandLine.flightArgs, 1, commandLine.flightArgs.length)));
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
        if(commandLine.textFileFound && !commandLine.xmlFileFound)
        {
            try {
                File file = new File(args[commandLine.filePathLoc]);
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

        if(commandLine.xmlFileFound && !commandLine.textFileFound)
        {
            //add exception handling?
            XmlDumper xmlDumper = new XmlDumper(args[commandLine.xmlPathLoc]);
            xmlDumper.dump(airline);
        }

        //Pretty option
        if(commandLine.prettyFileFound)
        {
            if(args[commandLine.prettyPathLoc].equals("-"))
            {
                System.out.println(airline.getPrettyText());
            }
            else
            {
                try
                {
                    File prettyFile = new File(args[commandLine.prettyPathLoc]);
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
        if(commandLine.printFlag == true)
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
