package com.example.u4_a_a16diegoar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VerDatosSpinner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_spinner);

        Intent i = getIntent();
        ArrayList<String> datos = i.getExtras().getStringArrayList("DATOS");

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spn = findViewById(R.id.spnVerDatos);
        spn.setAdapter((adap));
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Seleccionado o item " + position + " co valor" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
