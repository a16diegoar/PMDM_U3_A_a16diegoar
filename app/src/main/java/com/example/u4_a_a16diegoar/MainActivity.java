package com.example.u4_a_a16diegoar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "### U4_A_a16diegoar ###";

    private final String FILE = "coches.txt";

    private Calendar cal;
    private File fichSD;
    private File rutaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cal = Calendar.getInstance();

        //comprobar estado da SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "Error lendo a tarxeta SD");
            Toast.makeText(this, "Tarxeta SD non dispoñible!", Toast.LENGTH_LONG).show();
            finish();
        }

        fichSD = getExternalFilesDir(null);
        rutaCompleta = new File(fichSD.getAbsolutePath(), FILE);
        Log.i(TAG, "Ruta establecida a "+rutaCompleta.toString());
    }

    public void escribir(View v) {
        EditText txeCoche = findViewById(R.id.txeCoche);
        String linea = txeCoche.getText().toString();
        linea += " - ";
        linea += cal.getTime();

        txeCoche.setText("");

        //Decidir engadir ou sobrescribir
        RadioGroup rg = findViewById(R.id.rgAdd);
        boolean modo = false;
        if (rg.getCheckedRadioButtonId() == R.id.rbEngadir) {
            modo = true;
        }
        Log.i(TAG, "Establecido modo " + (modo ? "sobreescritura" : "append"));

        //Escribir
        try (
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta, modo))
            ) {

            Log.i(TAG, "Escribindo no ficheiro " + rutaCompleta.toString());
            osw.write(linea+"\n");
            Log.i(TAG, "Escrita línea \"" + linea + "\"");

        } catch (Exception e) {
            Log.e("INTERNA", "Error escribindo ficheiro");
        }
    }

    public void showDatos(View v) {
        Log.d(TAG, "Abrindo dialogo");
        //Amosar dialogo
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle("Ver datos");
        db.setMessage("Queres ver os datos nunha lista ou nun menú desplegable?");
        db.setCancelable(true);
        db.setPositiveButton("Lista", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        db.setNegativeButton("Desplegable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        db.create();
        db.show();
    }
}
