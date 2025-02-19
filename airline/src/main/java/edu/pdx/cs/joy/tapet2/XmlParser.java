package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;

import edu.pdx.cs.joy.AirlineParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlParser implements AirlineParser<Airline> {

    private String xmlPath;

    public XmlParser(String xmlPath)
    {
        this.xmlPath = new String(xmlPath);
    }

    public Airline parse() throws ParserException
    {

        Airline airline;
        
        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            airline = new Airline(doc.getElementsByTagName("airline").toString());

            NodeList list = doc.getElementsByTagName("flight");
            for(int i = 0; i < list.getLength(); ++i)
            {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    //element.get stuff
                }
            }
        }
        
    }
}
