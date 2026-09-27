package com.uth.apputh;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.uth.apputh.Controllers.PersonasAdapter;
import com.uth.apputh.Database.DatabaseHelper;
import com.uth.apputh.Models.Personas;
import com.uth.apputh.Views.ActivityPersonas;

import java.util.ArrayList;
import java.util.List;

public class ActivityDashboard extends AppCompatActivity implements PersonasAdapter.OnPersonaDeleteListener {

    private RecyclerView rvPersonas;
    private LinearLayout layoutEmptyState;
    private TextInputEditText txtBuscar;
    private PersonasAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Personas> listaPersonas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvPersonas = findViewById(R.id.rvPersonas);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        txtBuscar = findViewById(R.id.txtBuscar);
        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregar);

        rvPersonas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonasAdapter(listaPersonas, this);
        rvPersonas.setAdapter(adapter);

        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityDashboard.this, ActivityPersonas.class);
            startActivity(intent);
        });
    }

    private void setupListeners() {
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filtrar(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPersonas();
    }

    private void cargarPersonas() {
        listaPersonas = dbHelper.obtenerTodasLasPersonas();
        adapter.actualizarLista(listaPersonas);

        if (listaPersonas.isEmpty()) {
            rvPersonas.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvPersonas.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
        }

        if (txtBuscar.getText() != null && !txtBuscar.getText().toString().isEmpty()) {
            adapter.filtrar(txtBuscar.getText().toString());
        }
    }

    @Override
    public void onDeletePersona(Personas persona) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Eliminar Registro")
                .setMessage(R.string.msg_confirmar_eliminar)
                .setPositiveButton(R.string.action_delete, (dialog, which) -> {
                    boolean eliminado = dbHelper.eliminarPersona(persona.getId());
                    if (eliminado) {
                        Snackbar.make(findViewById(R.id.main), R.string.msg_exito_eliminar, Snackbar.LENGTH_SHORT).show();
                        cargarPersonas();
                    } else {
                        Snackbar.make(findViewById(R.id.main), "Error al eliminar la persona", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }
}
