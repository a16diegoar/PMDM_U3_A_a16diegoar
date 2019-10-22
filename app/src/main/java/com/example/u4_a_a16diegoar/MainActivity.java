package com.example.u4_a_a16diegoar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "### U4_A_a16diegoar ###";
    public static final int COD_LV = 11;
    public static final int COD_SPN = 6;

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
        Log.i(TAG, "Establecido modo " + (modo ? "append" : "sobreescritura"));

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
                abrirActiv(COD_LV);
            }
        });
        db.setNegativeButton("Desplegable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirActiv(COD_SPN);
            }
        });
        db.create();
        db.show();
    }

    private void abrirActiv(int vista) {
        //Un pouco innecesario, pero así é mais facil engadir novas formas de visualizar os datos
        Intent i = null;
        if (vista == COD_LV) {
            i = new Intent(this, VerDatosListview.class);
        } else if (vista == COD_SPN) {
            i = new Intent(this, VerDatosSpinner.class);
        }

        Log.d(TAG, "Creando array de datos");
        //Gardar os datos nun array
        ArrayList<String> datos = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(rutaCompleta))
                ) {
            String l = br.readLine();
            while (l != null) {
                Log.d(TAG, "Engadindo \""+l+"\"");
                datos.add(l);
                l = br.readLine();
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        Log.d(TAG, "Array creado con "+datos.size()+" items");
        if (i != null) {
            i.putExtra("DATOS", datos);
            startActivity(i);
        }
    }
}
