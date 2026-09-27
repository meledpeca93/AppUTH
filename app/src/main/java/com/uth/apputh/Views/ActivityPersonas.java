package com.uth.apputh.Views;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.uth.apputh.Database.DatabaseHelper;
import com.uth.apputh.Models.Personas;
import com.uth.apputh.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ActivityPersonas extends AppCompatActivity {

    private TextInputLayout txtLayoutNombres, txtLayoutApellidos, txtLayoutFechanac, txtLayoutDireccion, txtLayoutTelefono, txtLayoutCorreo;
    private TextInputEditText txtNombres, txtApellidos, txtFechanac, txtDireccion, txtTelefono, txtCorreo;
    private DatabaseHelper dbHelper;

    EditText nombres, apellidos, fechanac, direccion, telefono, correo;

    Button btnagregar;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas);




        dbHelper = new DatabaseHelper(this);

        this.initControls();

        initViews();
        setupListeners();
    }


    private void InitControls(){
        nombres = (EditText) findViewById(R.id.txtNombres);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        fechanac = (EditText) findViewById(R.id.txtFechanac);
        direccion = (EditText) findViewById(R.id.txtDireccion);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        correo = (EditText) findViewById(R.id.txtCorreo);
        btnagregar = (Button) findViewById(R.id.btnGuardar);
    }

    private void initViews() {
        txtLayoutNombres = findViewById(R.id.txtLayoutNombres);
        txtLayoutApellidos = findViewById(R.id.txtLayoutApellidos);
        txtLayoutFechanac = findViewById(R.id.txtLayoutFechanac);
        txtLayoutDireccion = findViewById(R.id.txtLayoutDireccion);
        txtLayoutTelefono = findViewById(R.id.txtLayoutTelefono);
        txtLayoutCorreo = findViewById(R.id.txtLayoutCorreo);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtFechanac = findViewById(R.id.txtFechanac);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
    }

    private void setupListeners() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_limpiar) {
                limpiarFormulario();
                return true;
            }
            return false;
        });

        txtFechanac.setOnClickListener(v -> mostrarDatePicker());

        MaterialButton btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> guardarPersona());
    }

    private void mostrarDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar Fecha de Nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            txtFechanac.setText(sdf.format(new Date(selection)));
            txtLayoutFechanac.setError(null);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void guardarPersona() {
        limpiarErrores();

        String nombres = txtNombres.getText() != null ? txtNombres.getText().toString().trim() : "";
        String apellidos = txtApellidos.getText() != null ? txtApellidos.getText().toString().trim() : "";
        String fechanac = txtFechanac.getText() != null ? txtFechanac.getText().toString().trim() : "";
        String direccion = txtDireccion.getText() != null ? txtDireccion.getText().toString().trim() : "";
        String telefono = txtTelefono.getText() != null ? txtTelefono.getText().toString().trim() : "";
        String correo = txtCorreo.getText() != null ? txtCorreo.getText().toString().trim() : "";

        boolean esValido = true;

        if (TextUtils.isEmpty(nombres)) {
            txtLayoutNombres.setError("El nombre es obligatorio");
            esValido = false;
        }

        if (TextUtils.isEmpty(apellidos)) {
            txtLayoutApellidos.setError("El apellido es obligatorio");
            esValido = false;
        }

        if (TextUtils.isEmpty(direccion)) {
            txtLayoutDireccion.setError("La dirección es obligatoria");
            esValido = false;
        }

        if (!TextUtils.isEmpty(correo) && !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            txtLayoutCorreo.setError("Ingrese un correo electrónico válido");
            esValido = false;
        }

        if (!esValido) {
            Snackbar.make(findViewById(R.id.main), R.string.msg_error_campos, Snackbar.LENGTH_SHORT).show();
            return;
        }

        Personas nuevaPersona = new Personas(nombres, apellidos, fechanac, direccion, telefono, correo);
        long resultado = dbHelper.agregarPersona(nuevaPersona);

        if (resultado > 0) {
            Snackbar.make(findViewById(R.id.main), R.string.msg_exito_guardar, Snackbar.LENGTH_LONG).show();
            limpiarFormulario();
        } else {
            Snackbar.make(findViewById(R.id.main), "Error al guardar el registro", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtFechanac.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        limpiarErrores();
        txtNombres.requestFocus();
        Snackbar.make(findViewById(R.id.main), "Formulario limpiado", Snackbar.LENGTH_SHORT).show();
    }

    private void limpiarErrores() {
        txtLayoutNombres.setError(null);
        txtLayoutApellidos.setError(null);
        txtLayoutFechanac.setError(null);
        txtLayoutDireccion.setError(null);
        txtLayoutTelefono.setError(null);
        txtLayoutCorreo.setError(null);
    }
}
