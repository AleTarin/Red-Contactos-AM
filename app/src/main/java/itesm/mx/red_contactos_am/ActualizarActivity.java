package itesm.mx.red_contactos_am;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import itesm.mx.red_contactos_am.Modelos.Contacto;
import itesm.mx.red_contactos_am.Modelos.ContactoOperations;

public class ActualizarActivity extends AppCompatActivity  implements View.OnClickListener , AdapterView.OnItemSelectedListener {

    ImageView ivFoto;
    EditText etNombre;
    EditText etTelefono;
    Button btnGuardar;
    Button btnBuscarFoto;
    Spinner spCategoria;
    int PICK_IMAGE_REQUEST=1;
    int REQUEST_IMAGE_CAPTURE = 1;

    byte[] bImage;
    String sNombre;
    String sTelefono;
    String sCategoria;
    ContactoOperations coTool;
    Button btnTomarFoto;
    long idContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Actualizar contacto");

        coTool = new ContactoOperations(this);
        coTool.open();

        ivFoto = (ImageView) findViewById(R.id.image_foto_actualizar);
        etNombre= (EditText) findViewById(R.id.edit_nombre_actualizar);
        etTelefono = (EditText) findViewById(R.id.edit_telefono_actualizar);
        btnGuardar = (Button) findViewById(R.id.button_guardar_actualizar);
        btnBuscarFoto = (Button) findViewById(R.id.button_buscar_foto_actualizar);
        btnTomarFoto = (Button) findViewById(R.id.button_tomar_foto_actualizar);
        spCategoria = (Spinner) findViewById(R.id.spinner_categoria_actualizar);

        // Spinner prueba

        List<String> categories = new ArrayList<String>();
        categories.add("Familia");
        categories.add("Proveedores");
        categories.add("Salud");
        categories.add("Emergencias");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Intent intent = getIntent();

        if (intent.getExtras() != null){
            idContacto = intent.getLongExtra("id", 0);
            sNombre = intent.getStringExtra("name");
            etNombre.setText(sNombre);
            bImage = intent.getByteArrayExtra("foto");
            sTelefono = intent.getStringExtra("telefono");
            etTelefono.setText(sTelefono);
            sCategoria = intent.getStringExtra("categoria");
            selectSpinnerValue(spCategoria, sCategoria);
        }

        if(bImage != null){
            Bitmap bmimage = BitmapFactory.decodeByteArray(bImage, 0 , bImage.length);
            ivFoto.setImageBitmap(bmimage);
        }

        btnTomarFoto.setOnClickListener(this);
        btnBuscarFoto.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        spCategoria.setOnItemSelectedListener(this);

        // attaching data adapter to spinner
        spCategoria.setAdapter(dataAdapter);

    }


    @Override
    protected void onResume() {
        coTool.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        coTool.close();
        super.onPause();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_buscar_foto_actualizar:
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.button_tomar_foto_actualizar:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.button_guardar_actualizar:
                sNombre=etNombre.getText().toString();
                sTelefono=etTelefono.getText().toString();

                if(sNombre.length()==0 || sTelefono.length()<3 || sCategoria==""){
                    Toast.makeText(this,"Falto informacion",Toast.LENGTH_LONG).show();

                }else{
                    Contacto contacto=new Contacto(idContacto,sNombre,sTelefono,bImage,sCategoria);
                    coTool.actualizarContacto(contacto);
                    Toast.makeText(this,"Contacto actualizado",Toast.LENGTH_LONG).show();

                }


                break;



        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ivFoto.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bImage = stream.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            ivFoto.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bImage = stream.toByteArray();

        }
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        sCategoria = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
