package com.example.recetario;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText TextID;
    private EditText TextTitulo;
    private EditText TextDescripcion;
    private CRUDNuevo CRUD;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> listaRecetitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CRUD = new CRUDNuevo(this);



        ListView listaRecetas = findViewById(R.id.listarecetas);
        listaRecetas.setDivider(null);

        TextID = findViewById(R.id.TextID);
        TextTitulo = findViewById(R.id.TextTitulo);
        TextDescripcion = findViewById(R.id.TextDescripcion);

        listaRecetitas = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRecetitas);
        listaRecetas.setAdapter(adaptador);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText = TextID.getText().toString();
                if (!idText.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    CRUD.eliminarReceta(id);
                    actualizarListaRecetas();
                    TextID.setText("");
                }
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = TextTitulo.getText().toString();
                String descripcion = TextDescripcion.getText().toString();

                if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                    CRUD.insertarReceta(titulo, descripcion);
                    actualizarListaRecetas();
                    TextTitulo.setText("");
                    TextDescripcion.setText("");
                }
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText = TextID.getText().toString();
                String titulo = TextTitulo.getText().toString();
                String descripcion = TextDescripcion.getText().toString();

                if (!idText.isEmpty() && !titulo.isEmpty() && !descripcion.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    CRUD.actualizarReceta(id, titulo, descripcion);
                    actualizarListaRecetas();
                    TextID.setText("");
                    TextTitulo.setText("");
                    TextDescripcion.setText("");
                }
            }
        });

        actualizarListaRecetas();
    }

    private void actualizarListaRecetas() {
        listaRecetitas.clear();
        Cursor informacion = CRUD.mostrarRecetas();
        while (informacion.moveToNext()) {
            int id = informacion.getInt(0); // Obtener el ID de la receta
            String titulo = informacion.getString(1);
            String item = id + " - " + titulo; // Combinar ID y título
            listaRecetitas.add(item);
        }
        adaptador.notifyDataSetChanged();
    }
}