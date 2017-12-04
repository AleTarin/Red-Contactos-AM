package itesm.mx.red_contactos_am;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import itesm.mx.red_contactos_am.Modelos.Contacto;
import itesm.mx.red_contactos_am.Modelos.ContactoAdapterCelda;
import itesm.mx.red_contactos_am.Modelos.ContactoOperations;
import itesm.mx.red_contactos_am.Utils.SearchUtils;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ContactoOperations dao;
    ImageButton imageButton;
    TextView tvResultadoVoz;
    Button btnContactos,btnEmergencia,btnProvedores,btnPersonal, btnFamilia, btnAdmin;
    AlertDialog dialog;
    AlertDialog.Builder mBuilder;

    //Shared Preferences KEY and defaults
    static final String KEY_NOMBRE_ADMIN = "KEY_NOMBRE_ADMIN";
    static final String KEY_PASSWORD_ADMIN = "KEY_PASSWORD_ADMIN";
    static final String KEY_TELEFONO_ADMIN = "KEY_TEL_ADMIN";
    static final String defaultPassword = "";
    static final String defaultNombre = "";
    static final String defaultTelefono = "911";
    static final String misPreferencias = "PerfilPref";
    String password, nombre;

    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Con-Tacto");

        dao = new ContactoOperations(this);
        dao.open();

        mBuilder = new AlertDialog.Builder(MenuActivity.this);
        dialog = mBuilder.create();


        btnContactos = (Button) findViewById(R.id.btnContactos);
        btnEmergencia = (Button) findViewById(R.id.btnEmergencia);
        btnProvedores = (Button) findViewById(R.id.btnProvedores);
        btnPersonal = (Button) findViewById(R.id.btnSalud);
        imageButton = (ImageButton) findViewById(R.id.btnVoz);
        tvResultadoVoz = (TextView) findViewById(R.id.textView_pruebaVoz);
        btnAdmin = (Button) findViewById(R.id.btn_admin_menu);
        btnFamilia = (Button) findViewById(R.id.btnFamilia);

        btnAdmin.setOnClickListener(this);
        btnContactos.setOnClickListener(this);
        btnEmergencia.setOnClickListener(this);
        btnProvedores.setOnClickListener(this);
        btnPersonal.setOnClickListener(this);
        btnFamilia.setOnClickListener(this);
        imageButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public void onClick(View v){
            String categoria = "";
            Intent intent;
        int vidur = 100;
        Vibrator vi = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

            switch (v.getId()){
                case R.id.btnVoz:
                    vi.vibrate(vidur);
                    intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(intent,10);
                    }else {
                        Toast.makeText(this,"Tu dispositivo no acepta reconocimiento de voz :C",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.btnContactos:
                    vi.vibrate(vidur);
                    categoria = "";
                    intent = new Intent(getApplicationContext(), contactosActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                    break;
                case R.id.btn_admin_menu:
                    vi.vibrate(vidur);
                    preferences = getSharedPreferences(misPreferencias, MODE_PRIVATE);
                    String number = preferences.getString(KEY_TELEFONO_ADMIN, defaultTelefono);
                    intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    startActivity(intent);
                    break;
                case R.id.btnProvedores:
                    vi.vibrate(vidur);
                    categoria = "Proveedores";
                    intent = new Intent(getApplicationContext(), contactosActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                    break;
                case R.id.btnSalud:
                    vi.vibrate(vidur);
                    categoria = "Salud";
                    intent = new Intent(getApplicationContext(), contactosActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                    break;
                case R.id.btnEmergencia:
                    vi.vibrate(vidur);
                    categoria = "Emergencias";
                    intent = new Intent(getApplicationContext(), contactosActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                    break;
                case R.id.btnFamilia:
                    vi.vibrate(vidur);
                    categoria = "Familia";
                    intent = new Intent(getApplicationContext(), contactosActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                    break;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10:
                if(resultCode == RESULT_OK && data != null){
                   ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvResultadoVoz.setText(resultado.get(0));
                    busqueda(resultado.get(0));
                }
                break;
        }
    }

    private void busqueda(String resultado) {

        //Normalizar el resultados para comparar
        resultado = new SearchUtils().normalize(resultado);

        //Variables de apoyo
        boolean swContacto = false;
        boolean swLlamada = false;
        String categoria = null;

        Contacto contactoResultado = null;
        ArrayList<Contacto> ArrayContactos = null;

        //Arreglos con las palabras clave de busqueda por categoria
        String[] proveedoresKey = {"proveedores", "medicina", "comida", "agua", "hambre", "sed", "medicinas", "medicamento", "medicamentos", "pastillas"};
        String[] saludKey = {"salud", "doctor", "doctora", "enfermero", "enfermera", "medico", "medicos", "cirujano", "especialista"};
        String[] emergenciaKey = {"emergencia", "peligro", "ayuda", "ayudenme"};
        String[] familiaKey = {"familia", "hijo", "hija", "hijos", "nietos", "nieto", "nieta", "sobrina", "sobrino", "sobrinos", "hermanos", "hermana", "hermano", "papá", "mamá", "primos", "primo", "prima"};

        //Si contiene las palabras llama o llamar
        if (resultado.contains("llama") || resultado.contains("llamar")){
            swLlamada = true;
        }

        if (resultado.length() == 0) { // Si la palabra es vacia
            Toast.makeText(getApplicationContext(), "No se detecto ninguna palabra, intente de nuevo", Toast.LENGTH_SHORT).show();
            return;
        }else{
            // Sino dividir la palabra para buscar similitudes
            String[] arrayResultados = resultado.split(" ");
            int num = arrayResultados.length;
            // Buscar por cada palabra de la oracion
            for (int i=0; i < arrayResultados.length ; i++){
                if ( (contactoResultado = dao.partialSearch(arrayResultados[i])) != null){
                    swContacto = true;
                }
                //if ((contactoResultado = dao.partialSearch(arrayResultados[i])) != null){ swContacto = true;}
                else{ //Sino encontro resultado en contactos provar con categorias
                    for (String keyword : saludKey)
                        if (resultado.contains(new SearchUtils().normalize(keyword)))
                            categoria = "Salud";
                    for (String keyword : familiaKey)
                        if (resultado.contains(new SearchUtils().normalize(keyword)))
                            categoria = "Familia";
                    for (String keyword : emergenciaKey)
                        if (resultado.contains(new SearchUtils().normalize(keyword)))
                            categoria = "Emergencias";
                    for (String keyword : proveedoresKey)
                        if (resultado.contains(new SearchUtils().normalize(keyword)))
                            categoria = "Proveedores";
                } // Fin else
            } // Fin for
        } // Fin else

        // Realizar acciones de acuerdo a los resultados
        if (swContacto){
            if (swLlamada){
                llamada(contactoResultado);
            }else {
                Intent intent = new Intent(getApplicationContext(), ContactInfoActivity.class);
                intent.putExtra("name", contactoResultado.getsName());
                intent.putExtra("telefono", contactoResultado.getsTelefono());
                intent.putExtra("foto", contactoResultado.getByPicture());
                intent.putExtra("categoria", contactoResultado.getsCategoria());
                startActivity(intent);
            }
        }else if (categoria != null){
            Intent intent = new Intent(getApplicationContext(), contactosActivity.class);
            intent.putExtra("categoria", categoria);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"No se encontro ninguna coincidencia",Toast.LENGTH_SHORT).show();
        }
    }


    private Boolean checkCallPermission(){

        String permission = "android.permission.CALL_PHONE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        switch (id){

            case R.id.admin:
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login,null);
                final EditText mUsuario = (EditText) mView.findViewById(R.id.etUsuario);
                final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
                TextView tvLogin = (TextView) mView.findViewById(R.id.tvLogin);
                Button btnAcceder = (Button) mView.findViewById(R.id.btnLogin);



                btnAcceder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        preferences = getSharedPreferences(misPreferencias, MODE_PRIVATE);
                        password = preferences.getString(KEY_PASSWORD_ADMIN, defaultPassword);
                        nombre = preferences.getString(KEY_NOMBRE_ADMIN, defaultNombre);

                        if(mUsuario.getText().toString().equals(nombre) && mPassword.getText().toString().equals(password)){
                                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Datos equivocados",Toast.LENGTH_SHORT).show();
                            }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                break;
            case R.id.menuInfo:
                //Toast.makeText(getApplicationContext(),"Aqui aparecera la ayuda",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void llamada(Contacto contactoResultado){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+ contactoResultado.getsTelefono()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
            return;
        }else {//have got permission
            try{
                startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
