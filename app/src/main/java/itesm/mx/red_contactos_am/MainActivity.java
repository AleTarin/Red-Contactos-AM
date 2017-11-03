package itesm.mx.red_contactos_am;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Modelos.Contacto;
import Modelos.ContactoAdapter;
import Modelos.ContactoOperations;

public class MainActivity extends ListActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    byte[] byteArray;
    Bitmap bitmap;
    ContactoOperations dao;


    TextView tvProductID;
    EditText etProductName;
    EditText etQuantity;
    Button btnAdd;
    Button btnFind;
    Button btnDelete;

    Button btnClear;
    Button btnTomarFoto;


    ContactoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dao = new ContactoOperations(this);
        dao.open();

        ArrayList<Contacto> listProduct = showProducts();
        adapter = new ContactoAdapter(getApplicationContext(), listProduct);
        setListAdapter(adapter);


        tvProductID = (TextView) findViewById(R.id.tv_productID);
        etProductName = (EditText) findViewById(R.id.et_productName);
        etQuantity = (EditText) findViewById(R.id.et_productQuantity);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnFind = (Button) findViewById(R.id.btn_find);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnTomarFoto = (Button) findViewById(R.id.btn_tomarFoto);

        btnAdd.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnTomarFoto.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        dao.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        Contacto Contacto;
        switch (view.getId()){
            case R.id.btn_add:
                Contacto = newProduct();
                adapter.add(Contacto);
                adapter.notifyDataSetChanged();
                break;

            case R.id.btn_delete:
                removeProduct();
                adapter.notifyDataSetChanged();
                break;

            case R.id.btn_find:
                searchProduct();
                break;

            case R.id.btn_clear:
                etQuantity.setText("");
                etProductName.setText("");
                tvProductID.setText("");
                break;

            case R.id.btn_tomarFoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }

    private void removeProduct() {
        String name = etProductName.getText().toString();
        boolean result = dao.deleteContact(name);

        if (result){
            tvProductID.setText("Record Deleted");
            etProductName.setText("");
            etQuantity.setText("");
        }else {
            Toast.makeText(getApplicationContext(), "No match found", Toast.LENGTH_SHORT).show();
            tvProductID.setText("No Match Found");
        }

    }

    private void searchProduct() {
        String name = etProductName.getText().toString();
        Contacto product = dao.findContact(name);

        if (product != null) {
            tvProductID.setText(String.valueOf(product.getId()));
            etQuantity.setText(String.valueOf(product.getId()));
        }else {
            Toast.makeText(getApplicationContext(), "No Match Found", Toast.LENGTH_SHORT).show();
            tvProductID.setText("No Match Found");
        }
    }

    private Contacto newProduct() {
        String quantity = etQuantity.getText().toString();
        String name = etProductName.getText().toString();
        Contacto product = new Contacto(name, quantity, byteArray);
        long id = dao.addContact(product);
        product.setId(id);

        Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
        etProductName.setText("");
        etQuantity.setText("");
        return product;
    }

    private ArrayList<Contacto> showProducts() {
        ArrayList<Contacto> productList = dao.getAllContacts();
        if (productList != null){
            return productList;
        }else{
            return null;
        }
    }

}
