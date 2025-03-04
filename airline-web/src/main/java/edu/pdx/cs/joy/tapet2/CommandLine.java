package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirportNames;

import java.util.Arrays;

public class CommandLine {
    public boolean readMeFlag;
    public boolean printFlag;
    public int searchAirlineLoc;
    public int searchSrcLoc;
    public int searchDestLoc;

    public int hostNameLoc;
    public int portLoc;

    public String airlineName;
    public String[] flightArgs;

    public CommandLine()
    {
        readMeFlag = false;
        printFlag = false;
        searchAirlineLoc = -1;
        searchSrcLoc = -1;
        searchDestLoc = -1;
        hostNameLoc = -1;
        portLoc = -1;
        airlineName = null;
    }

    public void parseArgs(String[] args)
    {
        if(args.length == 0)
        {
            throw new IllegalArgumentException("ERROR: No command line args given.");
        }

        for(int i = 0; i < args.length; ++i)
        {
            if(args[i].charAt(0) == '-')
            {
                switch (args[i])
                {
                    case "-README":
                        readMeFlag = true;
                        break;
                    case "-print":
                        printFlag = true;
                        break;
                    case "-search":
                        searchAirlineLoc = i+1;
                        i += handleSearchOpt(args);
                        break;
                    case "-host":
                        hostNameLoc = i+1;
                        ++i;
                        break;
                    case "-port":
                        portLoc = i+1;
                        ++i;
                        break;
                    default:
                        throw new IllegalArgumentException("\nERROR: Unknown command line option"
                                                            + "\nOccured at argument " + i
                                                            + "\nError Value: " + args[i]
                                                            + "\nValid options include: "
                                                            + "-README, -print, -textFile, -prettyFile, -xmlFile");
                }
            }
            else
            {
                airlineName = args[i];
                flightArgs = Arrays.copyOfRange(args, i+1, i+9);
                i += 9;
            }
        }
        if((hostNameLoc != -1 && portLoc == -1) || (hostNameLoc == -1 && portLoc != -1))
        {
            throw new IllegalArgumentException("ERROR: host or port was designated without the other..."
                                                + "\nYou cannot designate one without the other...");
        }
    }

    //AirportNames.getName(args[1])
    private int handleSearchOpt(String[] args)
    {
        //there is no dest or src because we are at end of args
        if(searchAirlineLoc + 2 > args.length) return 0;
        else
        {
            if(AirportNames.getName(args[searchAirlineLoc+1]) != null
                && AirportNames.getName(args[searchAirlineLoc+2]) != null)
                return 2;
        }
        return 0;
    }
}
