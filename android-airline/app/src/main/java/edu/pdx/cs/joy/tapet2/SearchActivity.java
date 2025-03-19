package edu.pdx.cs.joy.tapet2;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.pdx.cs.joy.ParserException;

public class SearchActivity extends AppCompatActivity {
    Airline airline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        try {
            TextParser parser = new TextParser(new FileReader(new File(this.getFilesDir(), "airline.txt")));
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
    }

    public void SearchFlights_OnClick(View view) {
        if(!((EditText)findViewById(R.id.editAirline))
                .getText().toString()
                .equals(airline.getName())) {
            new AlertDialog.Builder(this)
                    .setTitle("Airline Error")
                    .setMessage("Airline does not exist!")
                    .setPositiveButton("OK"
                            , (dialog, which) -> dialog.dismiss())
                    .show();
            return;
        }
        String src = ((EditText)findViewById(R.id.editSrcAirport)).getText().toString();
        String dest = ((EditText)findViewById(R.id.editDestAirport)).getText().toString();
        Airline direct = new Airline(airline.getName());
        direct.setFlights(airline.getFlights()
                .stream()
                .filter((Flight flight) -> flight.getSource().equals(src)
                        && flight.getDestination().equals(dest))
                .collect(Collectors.toCollection(ArrayList<Flight>::new)));
        ArrayAdapter<Flight> flightAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, direct.getFlights()) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.WHITE);
                        return view;
                    }
                };
        ListView listView = findViewById(R.id.flightsView);
        listView.setAdapter(flightAdapter);
    }

    public void BackToAddFlightPage_OnButtonClick(View view) {
        finish();
    }
}