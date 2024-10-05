package com.example.aplicacion_bbdd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnimalActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private BaseDeDatosSQLite bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        recyclerView = findViewById(R.id.reciclerAnimal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bd = new BaseDeDatosSQLite(this);
        bd.agregarAnimal(getIntent().getExtras().getString("animal"));
        Button buttonVolver = findViewById(R.id.button5);
        Button buttonEliminar = findViewById(R.id.button7);

        ArrayList listaAnimales = bd.verAnimales();

        animalAdapter = new AnimalAdapter(listaAnimales);
        recyclerView.setAdapter(animalAdapter);

        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buttonEliminar.setOnClickListener(v -> {
            executorService.execute(() -> {
                bd.eliminarAnimales();
                runOnUiThread(() -> {
                    Toast.makeText(AnimalActivity.this, "Todos los animales han sido eliminados", Toast.LENGTH_SHORT).show();
                    cargarAnimales();
                });
            });
        });


    }

    private void cargarAnimales() {
        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList animalList = bd.verAnimales();
            runOnUiThread(() -> {
                animalAdapter = new AnimalAdapter(animalList);
                recyclerView.setAdapter(animalAdapter);

            });
        });
    }
}
