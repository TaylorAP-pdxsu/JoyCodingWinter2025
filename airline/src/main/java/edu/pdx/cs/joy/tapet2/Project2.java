package edu.pdx.cs.joy.tapet2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;

import java.io.*;

import edu.pdx.cs.joy.tapet2.Airline;

public class Project2 {
    
    public static void main(String[] args)
    {
    
        Airline airline = new Airline("Icelandair");
      
        try {
            File file = new File("./airOutput.txt");
            TextDumper txtDump = new TextDumper(new PrintWriter(file));
            txtDump.dump(airline);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found...");
        }
    }

}
