package edu.pdx.cs.joy.tapet2;

import edu.pdx.cs.joy.ParserException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.text.html.HTMLEditorKit.Parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.pdx.cs.joy.ParserException;
import java.net.URL;

//included in TextParser.java
import edu.pdx.cs.joy.AirlineParser;
import edu.pdx.cs.joy.ParserException;
import java.io.*;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  //Taylor's tests
  @Test
  void textParserCreatedWithNullValue()
  {
    TextParser textParser = new TextParser(null);
    assertThrows(NullPointerException.class, textParser::parse);
  }

  @Test
  void textParserIncorrectArgNums()
  {
    InputStream resource = getClass().getResourceAsStream("incorrect-args.txt");
    assertThat(resource, notNullValue());
    TextParser textParser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, textParser::parse);
  }
}
