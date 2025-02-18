package edu.pdx.cs.joy.tapet2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        XmlDumper xmlDumper = new XmlDumper(xmlPath);
        xmlDumper.dump(airline);

    }

}
