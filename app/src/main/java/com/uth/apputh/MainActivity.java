package com.uth.apputh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.uth.apputh.Database.DatabaseHelper;
import com.uth.apputh.Views.ActivityPersonas;

public class MainActivity extends AppCompatActivity {

    private TextView txtTotalPersonas;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        txtTotalPersonas = findViewById(R.id.txtTotalPersonas);
        MaterialCardView cardRegistrar = findViewById(R.id.cardRegistrar);
        MaterialCardView cardVerLista = findViewById(R.id.cardVerLista);

        cardRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityPersonas.class);
            startActivity(intent);
        });

        cardVerLista.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityDashboard.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        int total = dbHelper.obtenerCantidadPersonas();
        txtTotalPersonas.setText(getString(R.string.total_registros, total));
    }
}
