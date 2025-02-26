package edu.pdx.cs.joy.tapet2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import edu.pdx.cs.joy.ParserException;

public class Converter {
    
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.err.println("ERROR: Incorrect number of args given."
                                + "\nFIX: Should be given a text file and an xml file in order <textFile xmlFile>");
            return;
        }

        String filePath = args[0];
        String xmlPath = args[1];

        Airline airline;
        TextParser txtParser;
        airline = null;
        txtParser = null;

        try {
            txtParser = new TextParser(new FileReader(new File(filePath)));
            airline = txtParser.parse();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Input text file not found...");
            return;
        } catch (ParserException e) {
            System.err.println("\nERROR: Parser exception..." + "\n--CAUSE-- " + e.getMessage() + "\n");
            return;
        }

        if(airline == null)
        {
            System.err.println("ERROR: Airline null at XML dumper...");
            return;
        }
        File xmlFile = new File(xmlPath);
        XmlDumper xmlDumper;
        try {
            if(xmlFile.exists())
            {
                
                if(XmlDumper.airlineMatch(airline, xmlFile))
                {
                    xmlDumper = new XmlDumper(new FileWriter(xmlFile.getPath()));
                    xmlDumper.dump(airline);
                }
                else
                {
                    System.err.println("ERROR: Given airline name from xml input file does not match already existing output file."
                                    + "\nFIX: Check that airline name on command line and input file matches the output file.");
                    return;
                }
            }
            else
            {
                //add exception handling?
                xmlDumper = new XmlDumper(new PrintWriter(xmlFile));
                xmlDumper.dump(airline);
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Output xml file not found...");
        } catch (IOException e) {
            System.err.println("ERROR: IOException on xml dump function.");
        }

    }

}
