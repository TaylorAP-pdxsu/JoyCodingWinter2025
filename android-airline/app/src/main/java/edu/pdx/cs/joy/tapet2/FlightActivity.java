package edu.pdx.cs.joy.tapet2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import edu.pdx.cs.joy.ParserException;

public class FlightActivity extends AppCompatActivity {
    Airline airline;
    FlightParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        airline = null;
        parser = new FlightParser();
    }

    public void GoToSearchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void AddFlight_OnButtonClick(View view) {
        if(airline == null) {
            airline = new Airline(((EditText) findViewById(R.id.editAirline)).getText().toString());
        }

        String[] entries = new String[9];
        entries[0] = ((EditText)findViewById(R.id.editFlightNum)).getText().toString();
        entries[1] = ((EditText)findViewById(R.id.editSrcAirport)).getText().toString();
        //2 = date, 3 = time, 4 = am/pm
        System.arraycopy(
                ((EditText)findViewById(R.id.editDeptTime)).getText().toString().split(" ")
                , 0
                , entries
                , 2
                , 3);
        entries[5] = ((EditText)findViewById(R.id.editDestAirport)).getText().toString();
        //6 = date, 7 = time, 8 = am/pm
        System.arraycopy(
                ((EditText)findViewById(R.id.editArrTime)).getText().toString().split(" ")
                , 0
                , entries
                , 6
                , 3);


        try {
            airline.addFlight(parser.parseFlight(entries));
            new AlertDialog.Builder(this)
                    .setTitle("Successfully added flight!")
                    .setMessage("New Flight:\n" + airline.getFlightsText())
                    .setPositiveButton("OK"
                            , (dialog, which) -> dialog.dismiss())
                    .show();
        } catch (ParserException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Parser Exception")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK"
                            , (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    public void SaveAirline_OnButtonClick(View view) {
        SaveAirline();
    }

    public void ViewAirlinePage_OnButtonClick(View view) {
        SaveAirline();
        Intent intent = new Intent(this, ViewAirlineActivity.class);
        startActivity(intent);
    }

    private void SaveAirline() {
        File dataDir = this.getFilesDir();
        File xmlFile = new File(dataDir, "airline.xml");
        try (PrintWriter pw = new PrintWriter(new FileWriter(xmlFile))) {
            XmlDumper dumper = new XmlDumper(pw);
            dumper.dump(this.airline);
        } catch (IOException e) {
            new AlertDialog.Builder(this)
                    .setTitle("IO Exception")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK"
                            , (dialog, which) -> dialog.dismiss())
                    .show();
        }
        new AlertDialog.Builder(this)
                .setTitle("Successfully Saved!")
                .setPositiveButton("OK"
                        , (dialog, which) -> dialog.dismiss())
                .show();
    }
}