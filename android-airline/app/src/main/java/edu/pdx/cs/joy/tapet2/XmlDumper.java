package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class XmlDumper implements AirlineDumper<Airline>
{
    private AirlineXmlHelper helper;
    private final Writer writer;

    public XmlDumper(Writer writer)
    {
        helper = new AirlineXmlHelper();
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline)
    {
        Document doc = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline"
                                                            , "-//Joy of Coding at PSU//DTD Airline//EN"
                                                            , "airline.dtd");
            doc = dom.createDocument(null, "airline", docType);
        } catch (ParserConfigurationException e) {
            System.err.println("ERROR: Can't create BUILDER. Parser config is invalid or unable to parse properly...\n"
                                + e.getMessage());
            return;
        } catch (DOMException e) {
            System.err.println("ERROR: Can't create DOCUMENT TYPE or DOCUMENT. Incorrect XML qualified name...\n"
                                + e.getMessage());
            return;
        }
        try 
        {
            Element root = doc.getDocumentElement();
            Element airlineName = doc.createElement("name");
            root.appendChild(airlineName);
            airlineName.appendChild(doc.createTextNode(airline.getName()));

            ArrayList<Flight> flights = airline.getFlights();
            for(Flight flight : flights)
            {
                //create flight element
                Element flightElem = doc.createElement("flight");
                root.appendChild(flightElem);

                //create element for doc
                Element flightnum = doc.createElement("number");
                Element source = doc.createElement("src");
                Element departTime = doc.createElement("depart");
                Element depDate = doc.createElement("date");
                Element depTime = doc.createElement("time");
                Element dest = doc.createElement("dest");
                Element arriveTime = doc.createElement("arrive");
                Element arrDate = doc.createElement("date");
                Element arrTime = doc.createElement("time");

                //append elements
                flightElem.appendChild(flightnum);
                flightElem.appendChild(source);
                flightElem.appendChild(departTime);
                departTime.appendChild(depDate);
                departTime.appendChild(depTime);
                flightElem.appendChild(dest);
                flightElem.appendChild(arriveTime);
                arriveTime.appendChild(arrDate);
                arriveTime.appendChild(arrTime);

                //set non-date values
                flightnum.appendChild(doc.createTextNode(String.valueOf(flight.getNumber())));
                source.appendChild(doc.createTextNode(flight.getSource()));
                dest.appendChild(doc.createTextNode(flight.getDestination()));

                //set date values
                depDate.setAttribute("day", flight.getDepartDay());
                depDate.setAttribute("month", flight.getDepartMonth());
                depDate.setAttribute("year", flight.getDepartYear());
                depTime.setAttribute("hour", flight.getDepartHour());
                depTime.setAttribute("minute", flight.getDepartMinute());

                arrDate.setAttribute("day", flight.getArrivalDay());
                arrDate.setAttribute("month", flight.getArrivalMonth());
                arrDate.setAttribute("year", flight.getArrivalYear());
                arrTime.setAttribute("hour", flight.getArrivalHour());
                arrTime.setAttribute("minute", flight.getArrivalMinute());
            }
        } catch (DOMException e) {
            System.err.println("ERROR: DOM exception...\n" + e.getMessage());
            return;
        }
        try (PrintWriter pw = new PrintWriter(writer))
        {
            Source src = new DOMSource(doc);
            Result res = new StreamResult(writer);

            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//Joy of Coding at PSU//DTD Airline//EN");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "airline.dtd");
            xform.transform(src, res);
        } catch (TransformerException e) {
            e.printStackTrace(System.err);
            return;
        }
    }

    public static boolean airlineMatch(Airline airline, File xmlFile) throws FileNotFoundException, IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
            while(reader.readLine() != null)
            {
                //found <name> element and it contains correct flight
                if(reader.readLine().contains("<name>")
                    && reader.readLine().contains(airline.getName()))
                    return true;
            }
            reader.close();
        } catch (FileNotFoundException e) {    
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return true;
    }
}
