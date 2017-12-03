package itesm.mx.red_contactos_am;

import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import itesm.mx.red_contactos_am.Modelos.Contacto;
import itesm.mx.red_contactos_am.Modelos.ContactoAdapter;
import itesm.mx.red_contactos_am.Modelos.ContactoAdapterCelda;
import itesm.mx.red_contactos_am.Modelos.ContactoOperations;
import itesm.mx.red_contactos_am.Utils.SearchUtils;

//import itesm.mx.red_contactos_am.itesm.mx.red_contactos_am.Modelos.Contacto;
//import itesm.mx.red_contactos_am.itesm.mx.red_contactos_am.Modelos.ContactoAdapter;

public class contactosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    byte[] byteArray;
    Bitmap bitmap;
    ContactoOperations dao;
    ContactoAdapterCelda contactosAdapter;
    GridView gv;
    String categoria;
    TextView tvMensaje;
    static  final  int ACTUALIZAR_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Todos");
        setContentView(R.layout.activity_contactos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dao = new ContactoOperations(this);
        dao.open();

        gv = (GridView) findViewById(R.id.gridview);
        tvMensaje = (TextView) findViewById(R.id.tv_mensaje_contactos);
        ArrayList<Contacto> listContactos = showProducts();

        contactosAdapter = new ContactoAdapterCelda(this, listContactos);
        contactosAdapter.notifyDataSetChanged();
        gv.setAdapter(contactosAdapter);


        switch (categoria){
            case "Familia":
                gv.setBackgroundColor(ContextCompat.getColor(this,R.color.Naranja));

                break;

            case "Salud":
                gv.setBackgroundColor(ContextCompat.getColor(this,R.color.Pantone));

                break;

            case "Proveedores":
                gv.setBackgroundColor(ContextCompat.getColor(this,R.color.verdeFuerte));

                break;

            case "Emergencias":
                gv.setBackgroundColor(ContextCompat.getColor(this,R.color.rojoBermellon));

                break;
            default:
                gv.setBackgroundColor(ContextCompat.getColor(this,R.color.Azul));
                break;
        }

        gv.setOnItemClickListener(this);
    }

    private ArrayList<Contacto> showProducts() {
        Intent intent = getIntent();
        if (intent.getExtras() != null){
            categoria = intent.getStringExtra("categoria");
        }

        if (!categoria.isEmpty()){
            setTitle(categoria);
        }else{
            setTitle("Todos");
        }
        ArrayList<Contacto> productList;

        if (categoria.isEmpty() || categoria.contains("contacto")){
            productList = dao.getAllContacts();
        }else{
            productList = dao.getAllContactsByCategory(categoria);
        }

        if (productList.isEmpty()){
            tvMensaje.setVisibility(View.VISIBLE);
        }else{
            tvMensaje.setVisibility(View.GONE);
        }

        if (productList != null){
            return productList;
        }else{
            return null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Objects.equals(categoria, "Borrar contacto")){
            final Contacto contacto = contactosAdapter.getItem(position);
            new AlertDialog.Builder(this)
                    .setTitle("Alerta")
                    .setMessage("¿Estás seguro de querer eliminar a este contacto?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dao.deleteContactByID(contacto);
                            Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                            contactosAdapter = new ContactoAdapterCelda(getApplicationContext(), showProducts());
                            contactosAdapter.notifyDataSetChanged();
                            gv.setAdapter(contactosAdapter);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        }else{
            Intent intent;
            if (Objects.equals(categoria, "Actualizar contacto")){
                intent = new Intent(contactosActivity.this, ActualizarActivity.class);
                Contacto contacto = contactosAdapter.getItem(position);
                intent.putExtra("id", contacto.getId());
                intent.putExtra("name", contacto.getsName());
                intent.putExtra("telefono", contacto.getsTelefono());
                intent.putExtra("foto", contacto.getByPicture());
                String category = contacto.getsCategoria();
                intent.putExtra("categoria", category);
                startActivityForResult(intent, ACTUALIZAR_CONTACT_REQUEST);
            }else{
                intent = new Intent(contactosActivity.this, ContactInfoActivity.class);
                Contacto contacto = contactosAdapter.getItem(position);
                intent.putExtra("id", contacto.getId());
                intent.putExtra("name", contacto.getsName());
                intent.putExtra("telefono", contacto.getsTelefono());
                intent.putExtra("foto", contacto.getByPicture());
                String category = contacto.getsCategoria();
                intent.putExtra("categoria", category);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                busqueda(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactosActivity.this.contactosAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
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
                if ( !(ArrayContactos = dao.partialSearchAll(arrayResultados[i])).isEmpty()){
                    if (ArrayContactos.size() == 1) {
                        contactoResultado = ArrayContactos.get(0);
                        swContacto = true;
                    }else{
                        ArrayList<Contacto> listContactos = ArrayContactos;
                        contactosAdapter = new ContactoAdapterCelda(this, ArrayContactos);
                        contactosAdapter.notifyDataSetChanged();
                        gv.setAdapter(contactosAdapter);
                        return;
                    }
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

