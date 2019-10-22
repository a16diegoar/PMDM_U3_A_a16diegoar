package com.example.u4_a_a16diegoar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VerDatosListview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_listview);

        Intent i = getIntent();
        ArrayList<String> datos = i.getExtras().getStringArrayList("DATOS");

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        ListView lv = findViewById(R.id.lvVerDatos);
        lv.setAdapter(adap);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Seleccionado o item " + position + " co valor" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
