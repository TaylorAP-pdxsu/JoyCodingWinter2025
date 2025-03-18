package edu.pdx.cs.joy.tapet2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;

import edu.pdx.cs.joy.ParserException;

public class ViewAirlineActivity extends AppCompatActivity {
    Airline airline = null;
    ArrayAdapter<Flight> flightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_airline);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        try {
            XmlParser parser = new XmlParser(new FileReader(new File(this.getFilesDir(), "airline.xml")));
            airline = parser.parse();
        } catch (FileNotFoundException e) {
            new AlertDialog.Builder(this)
                    .setTitle("File Not Found Exception")
                    .setMessage(e.getMessage())
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

        if(airline != null) {
            ((TextView) findViewById(R.id.PageTitle)).setText(airline.getName());
            this.flightAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, airline.getFlights());
            ListView listView = findViewById(R.id.airlineView);
            listView.setAdapter(this.flightAdapter);
        }
    }

    public void BackToAddFlightPage_OnButtonClick(View view) {
        finish();
    }
}