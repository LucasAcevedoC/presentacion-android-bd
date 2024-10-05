package com.example.aplicacion_bbdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SECCION ROOM
        AppDatabase db = MyDatabase.getDatabase();

        TextView texto = findViewById(R.id.texto);
        Button buttonSave = findViewById(R.id.button);
        EditText editTextName = findViewById(R.id.editTextText);
        Button buttonViewUsers = findViewById(R.id.button2);

        buttonSave.setOnClickListener(v -> {
            String firstName = editTextName.getText().toString();

            User user = new User();
            user.firstName = firstName;
            executorService.execute(() -> {
                long userId = db.userDao().insertUser(user);
                User userFromDb = db.userDao().getUserById((int) userId);

                runOnUiThread(() -> {
                    texto.setText(String.format("Se guardo el nombre \n%s", userFromDb.firstName));
                });
            });

        });

        buttonViewUsers.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // Seccion de animales

        Button buttonAnimal = findViewById(R.id.button4);
        EditText editTextAnimal = findViewById(R.id.editTextText2);

        buttonAnimal.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, AnimalActivity.class);
            intent.putExtra("animal", editTextAnimal.getText().toString());
            startActivity(intent);
        });

    }
}