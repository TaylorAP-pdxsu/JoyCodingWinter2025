package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;

import edu.pdx.cs.joy.AirlineParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

public class XmlParser implements AirlineParser<Airline> {

    private AirlineXmlHelper helper;
    private File xmlFile;

    public XmlParser(File xmlFile)
    {
        helper = new AirlineXmlHelper();
        this.xmlFile = xmlFile;
    }

    public Airline parse() throws ParserException
    {

        Airline airline;
        FlightParser flightParser = new FlightParser();
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.setErrorHandler(helper);
            dBuilder.setEntityResolver(helper);
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            airline = new Airline(doc.getElementsByTagName("name").item(0).getTextContent());

            NodeList list = doc.getElementsByTagName("flight");
            for(int i = 0; i < list.getLength(); ++i)
            {
                Node node = list.item(i);
                String lineParse = new String();
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    lineParse += element.getElementsByTagName("number").item(0).getTextContent();
                    
                    lineParse += " " + element.getElementsByTagName("src").item(0).getTextContent();
                    Element eDepart = (Element) element.getElementsByTagName("depart").item(0);
                    lineParse += " " + eDepart.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("month").getTextContent()
                               + "/" + eDepart.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("day").getTextContent()
                               + "/" + eDepart.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("year").getTextContent();
                    
                    int hourDepart = Integer.valueOf(eDepart.getElementsByTagName("time").item(0)
                                                    .getAttributes().getNamedItem("hour").getTextContent());
                    lineParse += " " + String.valueOf(hourDepart > 12 ? hourDepart - 12 : hourDepart)
                               + ":" + eDepart.getElementsByTagName("time").item(0)
                                                    .getAttributes().getNamedItem("minute").getTextContent()
                               + " " + (hourDepart >= 12 ? "PM" : "AM");

                    lineParse += " " + element.getElementsByTagName("dest").item(0).getTextContent();
                    Element eArrival = (Element) element.getElementsByTagName("arrive").item(0);
                    lineParse += " " + eArrival.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("month").getTextContent()
                               + "/" + eArrival.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("day").getTextContent()
                               + "/" + eArrival.getElementsByTagName("date").item(0)
                                                    .getAttributes().getNamedItem("year").getTextContent();

                    int hourArrive = Integer.valueOf(eArrival.getElementsByTagName("time").item(0)
                                                    .getAttributes().getNamedItem("hour").getTextContent());
                    lineParse += " " + String.valueOf(hourArrive > 12 ? hourArrive - 12 : hourArrive)
                               + ":" + eArrival.getElementsByTagName("time").item(0)
                                                    .getAttributes().getNamedItem("minute").getTextContent()
                               + " " + (hourArrive >= 12 ? "PM" : "AM");
                            
                    String[] args = lineParse.split(" ");

                    if(args.length != 9) throw new ParserException("Incorrect number of arguments in xml file at flight " + i);

                    airline.addFlight(flightParser.parseFlight(args));
                }
            }
            return airline;
        } catch (NullPointerException e) {
            throw new ParserException("ERROR: XML file path not found..." + "\n" + e.getMessage());
        } catch (FactoryConfigurationError e) {
            throw new ParserException("ERROR: Factory could not be configured..." + "\n" + e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new ParserException("ERROR: Parser could not be configured..." + "\n" + e.getMessage());
        } catch (IOException e) {
            throw new ParserException("Warning: IO exception on doc builder... Creating new file... " + "\n" + e.getMessage());
        } catch (SAXException e) {
            throw new ParserException("ERROR: SAX exception on doc builder... " + "\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ParserException("ERROR: Illegal arg on doc builder... " + "\n" + e.getMessage());
        }
        
    }
}
