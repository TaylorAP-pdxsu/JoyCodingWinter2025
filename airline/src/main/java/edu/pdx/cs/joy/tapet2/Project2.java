package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;

import java.io.*;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.tapet2.Airline;

public class Project2 {
    
    private static boolean readMeFlag = false;
    private static boolean printFlag = false;
    private static int filePathLoc;

    private static void checkArgsOpts(String[] args)
    {
        boolean locFound = false;
        for(int i = 0; i < args.length; ++i)
        {
            if(args[i].equals("-README"))
            {
                readMeFlag = true;
                return;
            }
            if(args[i].equals("-print")) printFlag = true;
            if(args[i].equals("-textFile") && locFound == false)
            {
                locFound = true;
                filePathLoc = i+1;
                ++i;
            }
            else
            {
                throw new IllegalArgumentException("\nERROR: Unknown command line option"
                                                    + "\nOccured at argument " + i
                                                    + "\nError Value: " + args[i]
                                                    + "\nValid options include: -README, -print, and -textFile");
            }
        }
    }

    public static void main(String[] args)
    {
        //check for opts
        checkArgsOpts(args);
        if(readMeFlag == true)
        {
            //do README stuff
        }
        //do parsing
        Airline airline;
        try {
            TextParser txtParser = new TextParser(new FileReader(new File(args[filePathLoc])));
            airline = txtParser.parse();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Input file not found");
            return;
        } catch (ParserException e) {
            System.err.println("\nERROR: Parser exception..." + "\n--CAUSE-- " + e.getMessage() + "\n");
            return;
        }

        //create airline and input file
        try {
            File file = new File("./airOutput.txt");
            TextDumper txtDump = new TextDumper(new PrintWriter(file));
            txtDump.dump(airline);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Output file not found...");
        }

        //output if -print
        if(printFlag == true)
        {
            System.out.println(airline.getFlightsText());
        }
    }

}
