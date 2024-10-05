package com.example.aplicacion_bbdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Button botonVolver = findViewById(R.id.button3);
        Button botonEliminar = findViewById(R.id.button6);

        recyclerView = findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = MyDatabase.getDatabase();

        executorService.execute(() -> {
            List<User> userList = db.userDao().getAllUsers();
            runOnUiThread(() -> {
                userAdapter = new UserAdapter(userList);
                recyclerView.setAdapter(userAdapter);
            });
        });

        botonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        botonEliminar.setOnClickListener(v -> {
            executorService.execute(() -> {
                db.userDao().deleteAllUsers();
                runOnUiThread(() -> {
                    Toast.makeText(UserListActivity.this, "Todos los usuarios han sido eliminados", Toast.LENGTH_SHORT).show();
                    cargarUsuarios();
                });
            });
        });
    }
    private void cargarUsuarios() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<User> userList = db.userDao().getAllUsers();
            runOnUiThread(() -> {
                    userAdapter = new UserAdapter(userList);
                    recyclerView.setAdapter(userAdapter);

            });
        });
    }
}
