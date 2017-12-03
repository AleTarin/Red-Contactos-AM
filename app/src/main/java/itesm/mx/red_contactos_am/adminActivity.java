package itesm.mx.red_contactos_am;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class adminActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAgregar, btnEliminar, btnActualizar, btnAdminInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("Panel de Administrador");

        btnAgregar = (Button) findViewById(R.id.btn_agregarContacto_admin);
        btnEliminar = (Button) findViewById(R.id.btn_borrarContacto_admin);
        btnActualizar = (Button) findViewById(R.id.btn_ActualizarContacto_admin);
        btnAdminInfo = (Button) findViewById(R.id.btn_datosAdmin_admin);

        btnAgregar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);
        btnAdminInfo.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ActualizarContacto_admin:
                Intent intentActualizar = new Intent(adminActivity.this, contactosActivity.class);
                intentActualizar.putExtra("categoria", "Actualizar contacto");
                startActivity(intentActualizar);
                break;
            case R.id.btn_agregarContacto_admin:
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_borrarContacto_admin:
                Intent intentBorrar = new Intent(adminActivity.this, contactosActivity.class);
                intentBorrar.putExtra("categoria", "Borrar contacto");
                startActivity(intentBorrar);
                break;
            case R.id.btn_datosAdmin_admin:
                Intent intent2 = new Intent(getApplicationContext(), AdminInfoActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
