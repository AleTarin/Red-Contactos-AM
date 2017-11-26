package itesm.mx.red_contactos_am;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGuardar;
    private EditText etNombre;
    private EditText etTelefono;
    private EditText etPassword;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //Variables globales para las settings
    static final String KEY_NOMBRE_ADMIN = "KEY_NOMBRE_ADMIN";
    static final String KEY_TELEFONO_ADMIN = "KEY_TEL_ADMIN";
    static final String KEY_PASSWORD_ADMIN = "KEY_PASSWORD_ADMIN";
    static final String defaultNombre = "";
    static final String defaultTelefono = "911";
    static final String defaultPassword = "";
    static final String misPreferencias = "PerfilPref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);

        preferences = getSharedPreferences(misPreferencias, MODE_PRIVATE);
        String password = preferences.getString(KEY_PASSWORD_ADMIN,defaultPassword);
        String nombre = preferences.getString(KEY_NOMBRE_ADMIN, defaultNombre);
        String telefono = preferences.getString(KEY_TELEFONO_ADMIN, defaultTelefono);

        etNombre = (EditText) findViewById(R.id.etNombreInfoAdmin);
        etTelefono = (EditText) findViewById(R.id.etTelefonoInfoAdmin);
        etPassword = (EditText) findViewById(R.id.etPasswordInfoAdmin);
        btnGuardar = (Button) findViewById(R.id.btnGuardarAdminInfo);

        etNombre.setText(nombre);
        etTelefono.setText(telefono);
        etPassword.setText(password);

        editor = preferences.edit();

        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String stNombre = etNombre.getText().toString();
        String stTelefono = etTelefono.getText().toString();
        String stPassword = etPassword.getText().toString();

        editor.putString(KEY_NOMBRE_ADMIN, stNombre );
        editor.putString(KEY_TELEFONO_ADMIN, stTelefono);
        editor.putString(KEY_PASSWORD_ADMIN, stPassword);

        editor.commit();

        Toast.makeText(getApplicationContext(),"Datos guardados, recuerdelos bien",Toast.LENGTH_SHORT).show();

    }
}
